package com.yetoop.cloud.atlas.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yetoop.cloud.atlas.common.DateUtil;
import com.yetoop.cloud.atlas.common.StringUtil;
import com.yetoop.cloud.atlas.common.WeiXinUtil;
import com.yetoop.cloud.atlas.domain.AsArtwork;
import com.yetoop.cloud.atlas.domain.AsWorks;
import com.yetoop.cloud.atlas.domain.EsMember;
import com.yetoop.cloud.atlas.domain.persistence.AsArtwork2WoksMapper;
import com.yetoop.cloud.atlas.domain.persistence.AsWorksMapper;
import com.yetoop.cloud.atlas.domain.persistence.EsMemberMapper;
import com.yetoop.cloud.atlas.dto.AsArtworkDto;
import com.yetoop.cloud.atlas.dto.AsWorksDto;
import com.yetoop.cloud.atlas.dto.EsMemberDto;
import com.yetoop.cloud.atlas.exception.AppException;
import com.yetoop.cloud.atlas.service.AtlasConfigService;
import com.yetoop.cloud.atlas.service.AtlasUserService;

@Component
public class AtlasUserServiceImpl implements AtlasUserService {

	private static final Logger log = LoggerFactory.getLogger(AtlasUserServiceImpl.class);
	@Autowired
	private AsWorksMapper asWorksMapper;
	@Autowired
	private AsArtwork2WoksMapper artwork2WorksMapper;
	@Autowired
	private EsMemberMapper esMemberMapper;
	@Autowired
	private EsMemberMapper memberMapper;
	@Autowired
	private AtlasConfigService atlasConfigService;
	
	@Override
	public Map<String, String> initWeixinConfig(String url) {
		if (log.isDebugEnabled()) {
			log.debug("initWxconfig:{" + url + "}");
		}
		Map<String, String> params = new HashMap<String, String>();
		Map<String, Object> wxConfigMap = atlasConfigService.getConfigValueMap("wx_config");
		String appId = StringUtil.toString(wxConfigMap.get("app_id"));
		String appSecret = StringUtil.toString(wxConfigMap.get("app_secret"));
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		String nonceStr = RandomStringUtils.randomAlphanumeric(12);
		String jsapi_ticket = WeiXinUtil.getJsapiTicket(appId, appSecret).getAccessToken();
		params.put("jsapi_ticket", jsapi_ticket);
		params.put("timestamp", timestamp);
		params.put("noncestr", nonceStr);
		params.put("url", url);
		String signature = WeiXinUtil.sha1Sign(params);
		Map<String, String> resutlMap = new HashMap<String, String>();
		resutlMap.put("appId", appId);
		resutlMap.put("timestamp", timestamp);
		resutlMap.put("nonceStr", nonceStr);
		resutlMap.put("signature", signature);
		if (log.isDebugEnabled()) {
			log.debug("resutlMap:{" + resutlMap + "}");
		}
		return resutlMap;
	}
	
	@Override
	public EsMemberDto wxLoginCallBack(HttpServletRequest request, String code, String state) {
		if (log.isDebugEnabled()) {
			log.debug("wxLoginCallBack-->code:{},state:{}", code, state);
		}
		Map<String, Object> wxConfigMap = atlasConfigService.getConfigValueMap("wx_config");
		String appId = StringUtil.toString(wxConfigMap.get("app_id"));
		String appSecret = StringUtil.toString(wxConfigMap.get("app_secret"));
		if (StringUtil.isNullString(code)) {
			throw new AppException(AppException.BusiAppCode.MEMBER_PROHIBITE_AUTHORIZE);
		}
		if (StringUtil.isNullString(state)) {
			throw new AppException(AppException.BusiAppCode.INVALID_WX_PARAM);
		}
		HttpSession session = request.getSession();
		String wxLoginState = StringUtil.toString(session.getAttribute("wxWapLoginState"));
		if (StringUtil.isNullString(wxLoginState)) {
			throw new AppException(AppException.BusiAppCode.INVALID_WX_PARAM);
		}
		if (!state.equals(wxLoginState)) {
			throw new AppException(AppException.BusiAppCode.INVALID_WX_PARAM);
		}
		Map<String, Object> userInfo = WeiXinUtil.queryWxUserInfo(code, appId, appSecret);
		String unionId = StringUtil.toString(userInfo.get("unionid"));
		String openid = StringUtil.toString(userInfo.get("openid"));
		if (StringUtil.isNullString(unionId)) {
			throw new AppException(AppException.BusiAppCode.NULL_WX_UNIONID);
		}
		// 查询是否存在该unionId的member
		EsMember member = this.memberMapper.selectByUnionid(unionId);
		if (log.isDebugEnabled()) {
			log.debug("wxLoginCallBack-->member:{}", member);
		}
		if (member == null) {
			member = new EsMember();
			member.create("", "");
			member.wxBind(unionId, openid);
			this.memberMapper.insertSelective(member);
		}
		EsMemberDto memberDto = this.memberLogin(member);
		return memberDto;
	}
	
	@Override
	public EsMemberDto memberLogin(EsMember member) {
		if (log.isDebugEnabled()) {
			log.debug("memberLogin-->member:{}", member);
		}
		if (member == null) {
			throw new AppException(AppException.BusiAppCode.NULL_MEMBER);
		}
		if (member.isScrapMember()) {
			throw new AppException(AppException.BusiAppCode.DISABLED_MEMBER);
		}
		int logincount = member.getLogincount();
		long ldate = ((long) member.getLastlogin()) * 1000;
		Date date = new Date(ldate);
		Date today = new Date();
		if (DateUtil.toString(date, "yyyy-MM").equals(DateUtil.toString(today, "yyyy-MM"))) {// 与上次登录在同一月内
			logincount++;
		} else {
			logincount = 1;
		}
		member.setLastlogin(DateUtil.getDateline());
		member.setLogincount(logincount);
		if (log.isDebugEnabled()) {
			log.debug("memberLogin-->member:{}", member);
		}
		memberMapper.updateByPrimaryKeySelective(member);
		EsMemberDto memberDto = new EsMemberDto();
		memberDto.create(member);
		if (log.isDebugEnabled()) {
			log.debug("memberLogin-->memberDto:{}", memberDto);
		}
		return memberDto;
	}
	
	@Override
	public Map<String, Object> wxLogin(HttpSession session, HttpServletRequest request) {
		Map<String, Object> wxConfigMap = atlasConfigService.getConfigValueMap("wx_config");
		String appId = StringUtil.toString(wxConfigMap.get("app_id"));
		String state = StringUtil.randomString(8);
		if (log.isDebugEnabled()) {
			log.debug("wxLogin-->state:{}", state);
		}
		session.setAttribute("wxWapLoginState", state);
		Map<String, Object> domainMap = atlasConfigService.getConfigValueMap("domain");
		StringBuffer sb = new StringBuffer();
		sb.append("http://");
		sb.append(domainMap.get("domain_name"));
		sb.append("/");
		sb.append(domainMap.get("web_site_name"));
		sb.append("/wx-callback.html");
		String redirectUrl = "";
		try {
			redirectUrl = URLEncoder.encode(sb.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Map<String, Object> wxWapLoginMap = WeiXinUtil.wxWapLogin(appId, redirectUrl, state);
		return wxWapLoginMap;
	}
	
	@Override
	public AsWorksDto queryWorksDetailByPriviewCode(Integer memberId, String encryptKey, String priviewCode) {
		if (log.isDebugEnabled()) {
			log.debug("queryWorksDetailByPriviewCode-->memberId:{},encryptKey:{},priviewCode:{}", memberId, encryptKey,
					priviewCode);
		}
		if (StringUtil.isNullString(priviewCode)) {
			throw new AppException(AppException.BusiAppCode.NULL_PRIVIEWCODE);
		}
		this.queryMember(memberId, encryptKey);
		List<AsWorks> worksList = this.asWorksMapper.selectByPriviewCode(priviewCode);
		if (log.isDebugEnabled()) {
			log.debug("queryWorksDetailByPriviewCode-->worksList:{}", worksList);
		}
		if (worksList == null || worksList.isEmpty()) {
			throw new AppException(AppException.BusiAppCode.ERROR_PRIVIEWCODE);
		}
		AsWorks work = worksList.get(0);
		AsWorksDto worksDto = new AsWorksDto();
		worksDto.create(work);
		if (log.isDebugEnabled()) {
			log.debug("queryWorksDetailByPriviewCode->worksDto:{}", worksDto);
		}
		return worksDto;
	}
	
	@Override
	public AsWorksDto queryWorksDetailByViewCode(Integer memberId, String encryptKey, String viewCode) {
		if (log.isDebugEnabled()) {
			log.debug("queryWorksDetailByViewCode-->memberId:{},encryptKey:{},viewCode:{}", memberId, encryptKey,
					viewCode);
		}
		if (StringUtil.isNullString(viewCode)) {
			throw new AppException(AppException.BusiAppCode.NULL_VIEWCODE);
		}
		this.queryMember(memberId, encryptKey);
		List<AsWorks> worksList = this.asWorksMapper.selectByViewCode(viewCode);
		if (log.isDebugEnabled()) {
			log.debug("queryWorksDetailByViewCode-->worksList:{}", worksList);
		}
		if (worksList == null || worksList.isEmpty()) {
			throw new AppException(AppException.BusiAppCode.ERROR_VIEWCODE);
		}
		AsWorks work = worksList.get(0);
		if (work.ifPrivate()) {
			throw new AppException(AppException.BusiAppCode.PRIVATE_ASWORKS);
		}
		AsWorksDto worksDto = new AsWorksDto();
		worksDto.create(work);
		if (log.isDebugEnabled()) {
			log.debug("queryWorksDetailByViewCode->worksDto:{}", worksDto);
		}
		return worksDto;
	}
	
	@Override
	public AsWorksDto queryArtworksByPriviewCode(Integer memberId, String encryptKey, String priviewCode) {
		if (log.isDebugEnabled()) {
			log.debug("queryWorksDetailByPriviewCode-->memberId:{},encryptKey:{},priviewCode:{}", memberId, encryptKey,
					priviewCode);
		}
		if (StringUtil.isNullString(priviewCode)) {
			throw new AppException(AppException.BusiAppCode.NULL_PRIVIEWCODE);
		}
		AsWorksDto worksDto = this.queryWorksDetailByPriviewCode(memberId, encryptKey, priviewCode);
		Date now = new Date();
		Date priviewEndDate = worksDto.getPriviewEndDate();
		if (priviewEndDate == null || now.after(priviewEndDate)) {
			throw new AppException(AppException.BusiAppCode.INVALID_WORKS_LINK);
		}
		List<AsArtwork> artworkList = this.artwork2WorksMapper.selectArtworksByWorksId(worksDto.getId());
		List<AsArtworkDto> artworkDtoList = new ArrayList<AsArtworkDto>();
		if (artworkList != null && artworkList.size() > 0) {
			for (AsArtwork artwork : artworkList) {
				AsArtworkDto dto = new AsArtworkDto();
				dto.create(artwork);
				artworkDtoList.add(dto);
			}
		}
		worksDto.setArtworkDtoList(artworkDtoList);
		if (log.isDebugEnabled()) {
			log.debug("queryWorksDetailByPriviewCode->worksDto:{}", worksDto);
		}
		return worksDto;
	}
	
	@Override
	public AsWorksDto queryArtworksByViewCode(Integer memberId, String encryptKey, String viewCode) {
		if (log.isDebugEnabled()) {
			log.debug("queryArtworksByViewCode-->memberId:{},encryptKey:{},viewCode:{}", memberId, encryptKey,
					viewCode);
		}
		AsWorksDto worksDto = this.queryWorksDetailByViewCode(memberId, encryptKey, viewCode);
		List<AsArtwork> artworkList = this.artwork2WorksMapper.selectArtworksByWorksId(worksDto.getId());
		List<AsArtworkDto> artworkDtoList = new ArrayList<AsArtworkDto>();
		if (artworkList != null && artworkList.size() > 0) {
			for (AsArtwork artwork : artworkList) {
				AsArtworkDto dto = new AsArtworkDto();
				dto.create(artwork);
				artworkDtoList.add(dto);
			}
		}
		worksDto.setArtworkDtoList(artworkDtoList);
		return worksDto;
	}
	

	private EsMember queryMember(Integer memberId, String encryptKey) {
		if (log.isDebugEnabled()) {
			log.debug("queryMember-->memberId:{},encryptKey:{}", memberId, encryptKey);
		}
		if (memberId == null || memberId == 0) {
			throw new AppException(AppException.BusiAppCode.NULL_MEMBER_ID);
		}
		EsMember member = this.esMemberMapper.selectByEncryptKey(memberId, encryptKey);
		if (log.isDebugEnabled()) {
			log.debug("queryMember-->member:{}", member);
		}
		if (member == null) {
			throw new AppException(AppException.BusiAppCode.NULL_MEMBER);
		}
		if (member.isScrapMember()) {
			throw new AppException(AppException.BusiAppCode.DISABLED_MEMBER);
		}
		return member;
	}
	
	@Override
	public boolean isMemberPhoneExist(String phone) {
		EsMember esMember = this.esMemberMapper.selectByPhone(phone);
		if (log.isDebugEnabled()) {
			log.debug("esMember:{}", esMember);
		}
		return esMember == null;
	}
}
