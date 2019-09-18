package com.yetoop.cloud.atlas.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yetoop.cloud.atlas.common.SmsTypeKeyEnum;
import com.yetoop.cloud.atlas.common.StringUtil;
import com.yetoop.cloud.atlas.common.WeiXinUtil;
import com.yetoop.cloud.atlas.controller.ValidCodeFrontServlet;
import com.yetoop.cloud.atlas.domain.AsGallary;
import com.yetoop.cloud.atlas.domain.AsGallery2MemberKey;
import com.yetoop.cloud.atlas.domain.EsMember;
import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.WxConfigTypeEnum;
import com.yetoop.cloud.atlas.domain.persistence.AsGallery2MemberMapper;
import com.yetoop.cloud.atlas.domain.persistence.EsMemberMapper;
import com.yetoop.cloud.atlas.dto.EsMemberDto;
import com.yetoop.cloud.atlas.exception.AppException;
import com.yetoop.cloud.atlas.service.AtlasConfigService;
import com.yetoop.cloud.atlas.service.AtalasMemberService;
import com.yetoop.cloud.atlas.service.AtlasUserService;
import com.yetoop.cloud.atlas.service.SmsMessageService;

@Service("atalasMemberService")
public class AtlasMemberServiceImpl implements AtalasMemberService {

	private static final Logger log = LoggerFactory.getLogger(AtlasMemberServiceImpl.class);

	@Autowired
	private EsMemberMapper memberMapper;
	@Autowired
	private SmsMessageService smsMessageService;
	@Autowired
	private AsGallery2MemberMapper asGallery2MemberMapper;
	@Autowired
	private AtlasConfigService atlasConfigService;
	@Autowired
	private AtlasUserService atlasUserService;
	
	private final static String REG = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
	
	@Override
	public Map<String, Object> wxCallBack(HttpServletRequest request, String code, String state, Integer memberId,
			String encryptKey) {
		if (log.isDebugEnabled()) {
			log.debug("wxCallBack-->code:{},state:{},memberId:{},encryptKey:{}", code, state, memberId, encryptKey);
		}
		if (StringUtil.isNullString(code)) {
			throw new AppException(AppException.BusiAppCode.MEMBER_PROHIBITE_AUTHORIZE);
		}
		if (StringUtil.isNullString(state)) {
			throw new AppException(AppException.BusiAppCode.INVALID_WX_PARAM);
		}
		int index = state.indexOf("-");
		if (index < 0) {
			throw new AppException(AppException.BusiAppCode.INVALID_WX_PARAM);
		}
		state = state.substring(0, index);
		if (log.isDebugEnabled()) {
			log.debug("wxCallBack-->state:{}", state);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (WxConfigTypeEnum.PCWXLOGIN.getType().equals(state)) {
			EsMemberDto memberDto = this.memberWxLogin(request, code, state);
			map.put("type", 1);
			map.put("member", memberDto);
			return map;
		} else if (WxConfigTypeEnum.PCWXBIND.getType().equals(state)) {
			EsMemberDto memberDto = this.memberBindWx(request, code, state, memberId, encryptKey);
			map.put("type", 2);
			map.put("member", memberDto);
			return map;
		} else {
			throw new AppException(AppException.BusiAppCode.INVALID_WX_PARAM);
		}
	}
	
	@Override
	public void updateMemberFace(Integer memberId, String encryptKey, String memberFace) {
		if (log.isDebugEnabled()) {
			log.debug("updateMemberFace-->memberId:{},encryptKey:{},memberFace:{}", memberId, encryptKey, memberFace);
		}
		if (StringUtil.isNullString(memberFace)) {
			throw new AppException(AppException.BusiAppCode.NULL_MEMBER_FACE);
		}
		EsMember member = this.queryMember(memberId, encryptKey);
		member.updateMemberFace(memberFace);
		if (log.isDebugEnabled()) {
			log.debug("updateMemberFace-->member:{}", member);
		}
		this.memberMapper.updateByPrimaryKeySelective(member);
	}
	
	@Override
	public void updateMemberNickName(Integer memberId, String encryptKey, String nickName) {
		if (log.isDebugEnabled()) {
			log.debug("updateMemberNickName-->memberId:{},encryptKey:{},nickName:{}", memberId, encryptKey, nickName);
		}
		if (StringUtil.isNullString(nickName)) {
			throw new AppException(AppException.IntfAppCode.NULL_NICKNAME);
		} else {
			if (nickName.length() >= 20) {
				throw new AppException(AppException.IntfAppCode.ERROR_FORMAT_NICKNAME, "昵称长度不能超过20个字");
			}
		}
		EsMember member = this.queryMember(memberId, encryptKey);
		member.setNickname(nickName);
		this.memberMapper.updateByPrimaryKeySelective(member);
	}
	
	@Override
	public EsMemberDto getMemberInfo(Integer memberId, String encryptKey) {
		EsMember member = this.queryMember(memberId, encryptKey);
		AsGallery2MemberKey asGallery2MemberKey = this.asGallery2MemberMapper.selectByMemberId(member.getMemberId());
		if (log.isDebugEnabled()) {
			log.debug("getMemberInfo-->asGallery2MemberKey:{}", asGallery2MemberKey);
		}
		if (asGallery2MemberKey == null) {
			throw new AppException(AppException.BusiAppCode.NULL_MEMBER_GALLERY);
		}
		AsGallary asGallary = asGallery2MemberKey.getAsGallary();
		if (asGallary == null) {
			throw new AppException(AppException.BusiAppCode.NULL_GALLERY);
		}
		EsMemberDto memberDto = new EsMemberDto();
		memberDto.create(member);
		memberDto.setGalleryName(asGallary.getName());
		if (log.isDebugEnabled()) {
			log.debug("getMemberInfo-->memberDto:{}", memberDto);
		}
		return memberDto;
	}
	
	@Override
	public EsMemberDto memberBindWx(HttpServletRequest request, String code, String state, Integer memberId,
			String encryptKey) {
		if (log.isDebugEnabled()) {
			log.debug("memberBindWx-->code:{},state:{}", code, state);
		}
		HttpSession session = request.getSession();
		String wxBindState = StringUtil.toString(session.getAttribute("wxBindState"));
		if (StringUtil.isNullString(wxBindState)) {
			throw new AppException(AppException.BusiAppCode.INVALID_WX_PARAM);
		}
		if (!state.equals(wxBindState)) {
			throw new AppException(AppException.BusiAppCode.INVALID_WX_PARAM);
		}
		EsMember esMember = this.queryMember(memberId, encryptKey);
		Map<String, Object> wxConfigMap = atlasConfigService.getConfigValueMap("wx_config");
		String appId = StringUtil.toString(wxConfigMap.get("app_id"));
		String appSecret = StringUtil.toString(wxConfigMap.get("app_secret"));
		Map<String, Object> userInfo = WeiXinUtil.queryWxUserInfo(code, appId, appSecret);
		String unionId = StringUtil.toString(userInfo.get("unionid"));
		String openid = StringUtil.toString(userInfo.get("openid"));
		EsMember member = this.queryMemberByUnionId(unionId);
		if (member != null) {
			member.loosenWxBind();
			this.memberMapper.updateByPrimaryKeySelective(member);
		}
		esMember.wxBind(unionId, openid);
		if (log.isDebugEnabled()) {
			log.debug("memberBindWx-->esMember:{}", esMember);
		}
		this.memberMapper.updateByPrimaryKeySelective(esMember);
		EsMemberDto memberDto = new EsMemberDto();
		memberDto.create(esMember);
		return memberDto;
	}
	
	@Override
	public EsMemberDto memberWxLogin(HttpServletRequest request, String code, String state) {
		if (log.isDebugEnabled()) {
			log.debug("memberWxLogin-->code:{},state:{}", code, state);
		}
		HttpSession session = request.getSession();
		String wxLoginState = StringUtil.toString(session.getAttribute("wxLoginState"));
		if (StringUtil.isNullString(wxLoginState)) {
			throw new AppException(AppException.BusiAppCode.INVALID_WX_PARAM);
		}
		if (!state.equals(wxLoginState)) {
			throw new AppException(AppException.BusiAppCode.INVALID_WX_PARAM);
		}
		Map<String, Object> wxConfigMap = atlasConfigService.getConfigValueMap("wx_config");
		String appId = StringUtil.toString(wxConfigMap.get("app_id"));
		String appSecret = StringUtil.toString(wxConfigMap.get("app_secret"));
		Map<String, Object> userInfo = WeiXinUtil.queryWxUserInfo(code, appId, appSecret);
		String unionId = StringUtil.toString(userInfo.get("unionid"));
		EsMember member = this.queryMemberByUnionId(unionId);
		EsMemberDto memberDto = this.login(member);
		return memberDto;
	}
	
	private EsMemberDto login(EsMember member) {
		if (log.isDebugEnabled()) {
			log.debug("login-->member:{}", member);
		}
		if (member == null) {
			throw new AppException(AppException.BusiAppCode.NULL_MEMBER);
		}
		if (member.isScrapMember()) {
			throw new AppException(AppException.BusiAppCode.DISABLED_MEMBER);
		}
		AsGallery2MemberKey asGallery2MemberKey = this.asGallery2MemberMapper.selectByMemberId(member.getMemberId());
		if (log.isDebugEnabled()) {
			log.debug("login-->asGallery2MemberKey:{}", asGallery2MemberKey);
		}
		if (asGallery2MemberKey == null) {
			throw new AppException(AppException.BusiAppCode.NULL_MEMBER_GALLERY);
		}
		AsGallary asGallary = asGallery2MemberKey.getAsGallary();
		if (asGallary == null) {
			throw new AppException(AppException.BusiAppCode.NULL_GALLERY);
		}
		EsMemberDto memberDto = this.atlasUserService.memberLogin(member);
		return memberDto;
	}
	
	private EsMember queryMemberByUnionId(String unionId) {
		if (log.isDebugEnabled()) {
			log.debug("queryMemberByUnionId-->unionId:{}", unionId);
		}
		if(StringUtil.isNullString(unionId)){
			throw new AppException(AppException.BusiAppCode.NULL_WX_UNIONID);
		}
		EsMember member = this.memberMapper.selectByUnionid(unionId);
		if (log.isDebugEnabled()) {
			log.debug("queryMemberByUnionId-->member:{}", member);
		}
		return member;
	}
	
	@Override
	public void loosenWxBind(Integer memberId, String encryptKey) {
		if (log.isDebugEnabled()) {
			log.debug("loosenWxBind-->memberId:{},encryptKey:{}", memberId, encryptKey);
		}
		EsMember member = this.queryMember(memberId, encryptKey);
		member.loosenWxBind();
		if (log.isDebugEnabled()) {
			log.debug("loosenWxBind-->member:{}", member);
		}
		this.memberMapper.updateByPrimaryKeySelective(member);
	}
	
	@Override
	public EsMemberDto changeMemberPwd(Integer memberId, String encryptKey, String oldPwd, String pwd,
			String rePwd) {
		if (log.isDebugEnabled()) {
			log.debug("changeMemberPwd-->memberId:{},encryptKey:{},oldPwd:{},pwd:{},rePwd:{}", memberId, encryptKey,
					oldPwd, pwd, rePwd);
		}
		if (StringUtil.isNullString(oldPwd) || StringUtil.isNullString(pwd) || StringUtil.isNullString(rePwd)) {
			throw new AppException(AppException.IntfAppCode.NULL_PASSWORD);
		}
		if (!StringUtil.isMatcher(REG, oldPwd) || !StringUtil.isMatcher(REG, pwd)
				|| !StringUtil.isMatcher(REG, rePwd)) {
			throw new AppException(AppException.IntfAppCode.ERROR_FORMAT_PASSWORD);
		}
		if (!pwd.equals(rePwd)) {
			throw new AppException(AppException.IntfAppCode.INCONFORMITY_PASSWORD);
		}
		EsMember member = this.queryMember(memberId, encryptKey);
		if (!member.getPassword().equals(StringUtil.md5(oldPwd))) {
			throw new AppException(AppException.IntfAppCode.ERROR_OLD_PWD);
		}
		member.updatePassword(pwd);
		this.memberMapper.updateByPrimaryKeySelective(member);
		EsMemberDto memberDto = new EsMemberDto();
		memberDto.create(member);
		return memberDto;
	}
	
	@Override
	public EsMemberDto changeMemberPhone(HttpSession session, Integer memberId, String encryptKey, String phone,
			String smsCode) {
		if (log.isDebugEnabled()) {
			log.debug("changeMemberPhone-->memberId:{},encryptKey:{},phone:{},smsCode:{}", memberId, encryptKey, phone,
					smsCode);
		}
		if (StringUtil.isNullString(smsCode)) {
			throw new AppException(AppException.IntfAppCode.NULL_VERIFY_CODE);
		}
		if (!smsMessageService.validSmsCode(smsCode, phone, SmsTypeKeyEnum.BINDING.toString(), session)) {
			throw new AppException(AppException.IntfAppCode.ERROR_VERIFY_CODE);
		}
		EsMember member = this.queryMember(memberId, encryptKey);
		int state = this.checkMemberPhone(phone);
		if (member.getMobile().equals(phone)) {
			throw new AppException(AppException.IntfAppCode.SAME_NEWOLD_PHONE);
		}
		if (state == 1) {
			throw new AppException(AppException.BusiAppCode.MEMBER_EXIST);
		}
		member.updatePhone(phone);
		this.memberMapper.updateByPrimaryKeySelective(member);
		EsMemberDto memberDto = new EsMemberDto();
		memberDto.create(member);
		return memberDto;
	}
	
	@Override
	public EsMemberDto retrievePassword(HttpSession session, String phone, String password, String rePasswd,
			String smsCode) {
		if (log.isDebugEnabled()) {
			log.debug("retrievePassword-->phone:{},password:{},rePasswd:{},smsCode:{}", phone, password, rePasswd,
					smsCode);
		}
		if (StringUtil.isNullString(smsCode)) {
			throw new AppException(AppException.IntfAppCode.NULL_VERIFY_CODE);
		}
		if (StringUtil.isNullString(phone)) {
			throw new AppException(AppException.IntfAppCode.NULL_PHONE);
		}
		if (!StringUtil.isMobilePhone(phone)) {
			throw new AppException(AppException.IntfAppCode.ERROR_PHONE);
		}
		if (StringUtil.isNullString(password)) {
			throw new AppException(AppException.IntfAppCode.NULL_PASSWORD);
		}
		if(!StringUtil.isMatcher(REG, password)){
			throw new AppException(AppException.IntfAppCode.ERROR_FORMAT_PASSWORD);
		}
		if (StringUtil.isNullString(rePasswd)) {
			throw new AppException(AppException.IntfAppCode.NULL_REPASSWD);
		}
		if(!StringUtil.isMatcher(REG, rePasswd)){
			throw new AppException(AppException.IntfAppCode.ERROR_FORMAT_PASSWORD);
		}
		if (!smsMessageService.validSmsCode(smsCode, phone, SmsTypeKeyEnum.UPDATE_PASSWORD.toString(), session)) {
			throw new AppException(AppException.IntfAppCode.ERROR_VERIFY_CODE);
		}
		if (!password.equals(rePasswd)) {
			throw new AppException(AppException.IntfAppCode.INCONFORMITY_PASSWORD);
		}
		EsMember member = this.memberMapper.selectByPhone(phone);
		if (member == null || member.isScrapMember()) {
			throw new AppException(AppException.BusiAppCode.NULL_MEMBER);
		}
		if (member.getPassword().equals(StringUtil.md5(password))) {
			throw new AppException(AppException.IntfAppCode.SAME_NEW_OLD_PWD);
		}
		member.updatePassword(password);
		this.memberMapper.updateByPrimaryKeySelective(member);
		return this.memberLogin(phone, password);
	}
	
	@Override
	public EsMemberDto memberRegister(String phone, String password, String license, String smsCode, HttpSession session) {
		if (StringUtil.isNullString(smsCode)) {
			throw new AppException(AppException.IntfAppCode.NULL_VERIFY_CODE);
		}
		if (!smsMessageService.validSmsCode(smsCode, phone, SmsTypeKeyEnum.CHECK.toString(), session)
				&& !smsMessageService.validSmsCode(smsCode, phone, SmsTypeKeyEnum.REGISTER.toString(), session)) {
			throw new AppException(AppException.IntfAppCode.ERROR_VERIFY_CODE);
		}
		int state = this.checkMemberPhone(phone);
		if (state == 1) {
			throw new AppException(AppException.BusiAppCode.MEMBER_EXIST);
		}
		if (!"agree".equals(license)) {
			throw new AppException(AppException.IntfAppCode.ERROR_LICENSE);
		}
		if (StringUtil.isNullString(password)) {
			throw new AppException(AppException.IntfAppCode.NULL_PASSWORD);
		}
		if(!StringUtil.isMatcher(REG, password)){
			throw new AppException(AppException.IntfAppCode.ERROR_FORMAT_PASSWORD);
		}
		EsMember member = new EsMember();
		member.create(phone, password);
		if (log.isDebugEnabled()) {
			log.debug("memberRegister-->member:{}", member);
		}
		this.memberMapper.insertSelective(member);
		EsMemberDto memberDto = new EsMemberDto();
		memberDto.create(member);
		return memberDto;
	}

	@Override
	public Map<String, Object> sendSmsCode(String phone, String key, Integer isCheckRegister, String vCode, HttpSession session,
			HttpServletRequest request) {
		if (log.isDebugEnabled()) {
			log.debug("sendSmsCode-->phone:{},key:{},isCheckRegister:{},vCode:{}", phone, key, isCheckRegister, vCode);
		}
		if (StringUtil.isNullString(phone)) {
			throw new AppException(AppException.IntfAppCode.NULL_PHONE);
		}
		if (!StringUtil.isMobilePhone(phone)) {
			throw new AppException(AppException.IntfAppCode.ERROR_PHONE);
		}
		if (StringUtil.isNullString(vCode)) {
			throw new AppException(AppException.IntfAppCode.NULL_VERIFY_CODE);
		}
		if (this.validcode(vCode, null, session) == 0) {
			throw new AppException(AppException.IntfAppCode.ERROR_VERIFY_CODE);
		}
		if (isCheckRegister == null) {
			isCheckRegister = 0;
		}
		Map<String, Object> sendResult = smsMessageService.sendMobileSms(phone, key, isCheckRegister, session, request);
		if (log.isDebugEnabled()) {
			log.debug("sendSmsCode-->sendResult:{}", sendResult);
		}
		return sendResult;
	}

	/**
	 * 校验验证码
	 * 
	 * @param validcode
	 * @param name
	 *            (1、memberlogin:会员登录 2、memberreg:会员注册 3、membervalid:会员手机验证)
	 * @return 1成功 0失败
	 */
	private int validcode(String validcode, String name, HttpSession session) {
		if (validcode == null) {
			return 0;
		}
		String code = (String) session
				.getAttribute(ValidCodeFrontServlet.SESSION_VALID_CODE + (name != null ? name : ""));
		if (code == null) {
			return 0;
		} else {
			if (!code.equalsIgnoreCase(validcode)) {
				return 0;
			}
		}
		return 1;
	}
	
	private EsMember queryMember(Integer memberId, String encryptKey) {
		if (log.isDebugEnabled()) {
			log.debug("queryMember-->memberId:{},encryptKey:{}", memberId, encryptKey);
		}
		if (memberId == null || memberId == 0) {
			throw new AppException(AppException.BusiAppCode.NULL_MEMBER_ID);
		}
		EsMember member = this.memberMapper.selectByEncryptKey(memberId, encryptKey);
		if (log.isDebugEnabled()) {
			log.debug("queryMember-->member:{}", member);
		}
		if (member == null) {
			throw new AppException(AppException.BusiAppCode.NULL_MEMBER);
		}
		if (member.isScrapMember()) {
			throw new AppException(AppException.BusiAppCode.DISABLED_MEMBER);
		}
		AsGallery2MemberKey asGallery2MemberKey = this.asGallery2MemberMapper.selectByMemberId(member.getMemberId());
		if (log.isDebugEnabled()) {
			log.debug("getMemberInfo-->asGallery2MemberKey:{}", asGallery2MemberKey);
		}
		if (asGallery2MemberKey == null) {
			throw new AppException(AppException.BusiAppCode.NULL_MEMBER_GALLERY);
		}
		AsGallary asGallary = asGallery2MemberKey.getAsGallary();
		if (asGallary == null) {
			throw new AppException(AppException.BusiAppCode.NULL_GALLERY);
		}
		return member;
	}

	@Override
	public EsMemberDto memberLogin(String phone, String password) {
		if (log.isDebugEnabled()) {
			log.debug("memberLogin-->phone:{},password:{}", phone, password);
		}
		int state = this.checkMemberPhone(phone);
		if (state == 0) {
			throw new AppException(AppException.BusiAppCode.NO_MEMBER_REGISTER);
		}
		if (StringUtil.isNullString(password)) {
			throw new AppException(AppException.IntfAppCode.NULL_PASSWORD);
		}
		String passwordMd5 = StringUtil.md5(password);
		EsMember esMember = this.memberMapper.selectByPhone(phone);
		if (esMember == null || esMember.isScrapMember()) {
			throw new AppException(AppException.BusiAppCode.NULL_MEMBER);
		}
		if (!esMember.getPassword().equals(passwordMd5)) {
			throw new AppException(AppException.IntfAppCode.ERROR_PASSWORD);
		}
		EsMember member = this.memberMapper.queryMember(phone, passwordMd5);
		EsMemberDto memberDto = this.login(member);
		return memberDto;
	}

	@Override
	public int checkMemberPhone(String phone) {
		if (log.isDebugEnabled()) {
			log.debug("queryMemberPhone-->phone:{}", phone);
		}
		if (StringUtil.isNullString(phone)) {
			throw new AppException(AppException.IntfAppCode.NULL_PHONE);
		}
		if (!StringUtil.isMobilePhone(phone)) {
			throw new AppException(AppException.IntfAppCode.ERROR_PHONE);
		}
		int count = this.memberMapper.checkMobile(phone);
		if (log.isDebugEnabled()) {
			log.debug("queryMemberPhone-->count:{}", count);
		}
		count = count > 0 ? 1 : 0;
		return count;
	}
	
}
