package com.yetoop.cloud.atlas.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yetoop.cloud.atlas.common.LoadDataByURL;
import com.yetoop.cloud.atlas.common.QrUtil;
import com.yetoop.cloud.atlas.common.StringUtil;
import com.yetoop.cloud.atlas.domain.AsArtist;
import com.yetoop.cloud.atlas.domain.AsArtwork;
import com.yetoop.cloud.atlas.domain.AsArtwork2WoksKey;
import com.yetoop.cloud.atlas.domain.AsFeedback;
import com.yetoop.cloud.atlas.domain.AsGallary;
import com.yetoop.cloud.atlas.domain.AsGallery2MemberKey;
import com.yetoop.cloud.atlas.domain.AsMaterial;
import com.yetoop.cloud.atlas.domain.AsWorks;
import com.yetoop.cloud.atlas.domain.AsWorks.ViewPermissionEnum;
import com.yetoop.cloud.atlas.domain.EsMember;
import com.yetoop.cloud.atlas.domain.dao.AsArtworkPageDao;
import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.StateEnum;
import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.WxConfigTypeEnum;
import com.yetoop.cloud.atlas.domain.persistence.AsArtistMapper;
import com.yetoop.cloud.atlas.domain.persistence.AsArtwork2WoksMapper;
import com.yetoop.cloud.atlas.domain.persistence.AsArtworkMapper;
import com.yetoop.cloud.atlas.domain.persistence.AsFeedbackMapper;
import com.yetoop.cloud.atlas.domain.persistence.AsGallaryMapper;
import com.yetoop.cloud.atlas.domain.persistence.AsGallery2MemberMapper;
import com.yetoop.cloud.atlas.domain.persistence.AsMaterialMapper;
import com.yetoop.cloud.atlas.domain.persistence.AsWorksMapper;
import com.yetoop.cloud.atlas.domain.persistence.EsMemberMapper;
import com.yetoop.cloud.atlas.dto.AsArtworkDto;
import com.yetoop.cloud.atlas.dto.AsWorksDto;
import com.yetoop.cloud.atlas.dto.EsMemberDto;
import com.yetoop.cloud.atlas.exception.AppException;
import com.yetoop.cloud.atlas.service.AtalasAdminService;
import com.yetoop.cloud.atlas.service.AtlasConfigService;
import com.yetoop.cloud.atlas.upload.IUploader;
import com.yetoop.cloud.atlas.upload.UploadFacatory;
import com.zhenyi.common.pager.PagedList;

@Service("atalasAdminService")
public class AtalasAdminServiceImpl implements AtalasAdminService {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private AsWorksMapper asWorksMapper;
	@Autowired
	private AsGallaryMapper asGallaryMapper;
	@Autowired
	private AsArtworkMapper asArtWorkMapper;
	@Autowired
	private AsArtwork2WoksMapper asArtwork2WoksMapper;
	@Autowired(required=false)
	private EsMemberMapper esMemberMapper;
	@Autowired
	private AsGallery2MemberMapper asGallery2MemberMapper;
	@Autowired
	private AsArtistMapper asArtistMapper;
	
	@Autowired
	private AsArtworkPageDao asArtworkPageDao;
	
	@Autowired
	private AtlasConfigService atlasConfigService;

	@Autowired
	private AsMaterialMapper asMaterialMapper;
	
	@Autowired
	private AsFeedbackMapper asFeedbackMapper;
	
	private final static String DOMAIN = "domain";
	private final static String PRIVIEWEFFECT = "priview_effect";
	private final static String QRNAME = "qr";
	
	@Override
	public void modifyGallaryName(Integer memberId, String encryptKey, String gallaryName) {
		if (logger.isDebugEnabled()) {
			logger.debug("modifyGallaryName-->memberId:" + memberId);
			logger.debug("modifyGallaryName-->encryptKey:" + encryptKey);
			logger.debug("modifyGallaryName-->gallaryName:" + gallaryName);
		}
		if (StringUtil.isNullString(gallaryName)) {
			throw new AppException(AppException.BusiAppCode.NULL_GALLARY_NAME);
		}
		if (gallaryName.length() > 20) {
			throw new AppException(AppException.BusiAppCode.ERROR_GALLARY_NAME, "画廊名称长度不能超过20个字");
		}
		AsGallary gallary = this.queryMemberGallery(memberId, encryptKey);
		if (!gallary.getName().equals(gallaryName)) {
			gallary.updateGallaryName(gallaryName);
			this.asGallaryMapper.updateByPrimaryKey(gallary);
		}
	}
	
	@Override
	public void restoreWorks(Integer memberId, String encryptKey, Integer worksId) {
		if (logger.isDebugEnabled()) {
			logger.debug("restoreWorks-->memberId:" + memberId);
			logger.debug("restoreWorks-->encryptKey:" + encryptKey);
			logger.debug("restoreWorks-->worksId:" + worksId);
		}
		if (worksId == null) {
			throw new AppException(AppException.BusiAppCode.NULL_ASWORKS_ID);
		}
		AsGallary gallary = this.queryMemberGallery(memberId, encryptKey);
		Integer galleryId = gallary.getId();

		AsWorks works = this.queryTrashWorks(memberId, worksId, galleryId);
		if (StateEnum.DESTROY.getState().equals(works.getState())) {
			throw new AppException(AppException.BusiAppCode.NULL_ASWORKS);
		}
		if (!StateEnum.NORMAL.getState().equals(works.getState())) {
			works.updateState(memberId, StateEnum.NORMAL.getState());
			this.asWorksMapper.updateByPrimaryKeySelective(works);
		}
	}
	
	@Override
	public void deleteWorks(Integer memberId, String encryptKey, Integer worksId) {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteWorks-->memberId:" + memberId);
			logger.debug("deleteWorks-->encryptKey:" + encryptKey);
			logger.debug("deleteWorks-->worksId:" + worksId);
		}
		if (worksId == null) {
			throw new AppException(AppException.BusiAppCode.NULL_ASWORKS_ID);
		}
		AsGallary gallary = this.queryMemberGallery(memberId, encryptKey);
		Integer galleryId = gallary.getId();

		AsWorks works = this.queryTrashWorks(memberId, worksId, galleryId);
		if (StateEnum.NORMAL.getState().equals(works.getState())) {
			throw new AppException(AppException.BusiAppCode.ERROR_ASWORKS, "作品集已恢复，不能彻底删除");
		}
		if (!StateEnum.DESTROY.getState().equals(works.getState())) {
			works.updateState(memberId, StateEnum.DESTROY.getState());
			this.asWorksMapper.updateByPrimaryKeySelective(works);
		}
	}
	
	@Override
	public void createReplicaWorks(Integer memberId, String encryptKey, Integer worksId) {
		if (logger.isDebugEnabled()) {
			logger.debug("createReplica-->memberId:" + memberId);
			logger.debug("createReplica-->encryptKey:" + encryptKey);
			logger.debug("createReplica-->worksId:" + worksId);
		}
		if (worksId == null) {
			throw new AppException(AppException.BusiAppCode.NULL_ASWORKS_ID);
		}
		AsGallary gallary = this.queryMemberGallery(memberId, encryptKey);
		Integer galleryId = gallary.getId();

		AsWorks works = this.queryAsWorks(memberId, worksId, galleryId);

		List<AsArtwork> artworkList = this.asArtwork2WoksMapper.selectArtworksByWorksId(worksId);
		if (logger.isDebugEnabled()) {
			logger.debug("createReplicaWorks-->artworkList:" + artworkList);
		}
		int artworksNum = 0;
		if (artworkList != null) {
			artworksNum = artworkList.size();
		}

		// 创建作品集的副本
		String newWorksName = "副本 " + works.getName();
		if (newWorksName.length() >= 20) {
			newWorksName = works.getName();
		}
		AsWorks newWorks = new AsWorks();
		newWorks.create(memberId, galleryId, newWorksName);
		newWorks.setArtworksNum(artworksNum);
		if (logger.isDebugEnabled()) {
			logger.debug("createReplicaWorks-->newWorks:" + newWorks);
		}
		this.asWorksMapper.insertSelective(newWorks);
		if (artworkList != null && !artworkList.isEmpty()) {
			for (int i = 0; i < artworkList.size(); i++) {
				AsArtwork artwork = artworkList.get(i);
				AsArtwork newArtwork = new AsArtwork();
				newArtwork.copyArtwork(artwork);
				this.asArtWorkMapper.insertSelective(newArtwork);
				AsArtwork2WoksKey artwork2WoksKey = new AsArtwork2WoksKey();
				artwork2WoksKey.create(newWorks.getId(), newArtwork.getId());
				this.asArtwork2WoksMapper.insertSelective(artwork2WoksKey);
			}
		}
	}
	
	@Override
	public AsWorks modifyWorksViewPermission(Integer memberId, String encryptKey, Integer worksId,
			String permissionCode) {
		if (StringUtil.isNullString(permissionCode)) {
			throw new AppException(AppException.BusiAppCode.NULL_PERMISSION_CODE);
		}
		AsWorks works = this.queryWorks(memberId, encryptKey, worksId);
		if ("private".equals(permissionCode)) {
			if(ViewPermissionEnum.PRIVATE.getCode().equals(works.getViewPermission())){
				throw new AppException(AppException.BusiAppCode.ERROR_PERMISSION);
			}
			works.updateWorksPermission("", "", "", ViewPermissionEnum.PRIVATE.getCode());
		} else if ("link".equals(permissionCode)) {
			if(ViewPermissionEnum.LINK.getCode().equals(works.getViewPermission())){
				throw new AppException(AppException.BusiAppCode.ERROR_PERMISSION);
			}
			String viewCode = StringUtil.randomString(16);
			String qrUrl = this.createQrUrl("index.html", "viewCode", viewCode);
			String qrImage = this.createQrImage(qrUrl);
			works.updateWorksPermission(qrUrl, viewCode, qrImage, ViewPermissionEnum.LINK.getCode());
		} else {
			throw new AppException(AppException.BusiAppCode.NO_VIEWPERMISSION_CODE);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("modifyWorksViewPermission-->works:" + works);
		}
		this.asWorksMapper.updateByPrimaryKeySelective(works);
		return works;
	}
	
	@Override
	public Map<String, Object> initWxPcBindConfig(HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> domainMap = atlasConfigService.getConfigValueMap("domain");
		StringBuffer sb = new StringBuffer();
		sb.append("http://");
		sb.append(domainMap.get("domain_name"));
		String sitePort = StringUtil.toString(domainMap.get("site_port"));
		if (!StringUtil.isNullString(sitePort) && !"80".equals(sitePort)) {
			sb.append(":" + sitePort);
		}
		sb.append("/");
		sb.append(domainMap.get("admin_site_name"));
		sb.append("/wx-callback.html");
		if (logger.isDebugEnabled()) {
			logger.debug("redirectUri:" + sb.toString());
		}
		Map<String, Object> wxConfigMap = atlasConfigService.getConfigValueMap("wx_config");
		String redirectUri = "";
		try {
			redirectUri = URLEncoder.encode(sb.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String state = WxConfigTypeEnum.PCWXBIND.getType() + "-" + StringUtil.randomString(8);
		if (logger.isDebugEnabled()) {
			logger.debug("state:" + state);
		}
		session.setAttribute("wxBindState", state);
		map.put("state", state);
		map.put("response_type", "code");
		map.put("scope", "snsapi_login");
		map.put("app_id", wxConfigMap.get("app_id"));
		map.put("redirect_uri", redirectUri);
		if (logger.isDebugEnabled()) {
			logger.debug("bindConfigMap:" + map);
		}
		return map;
	}
	
	@Override
	public Map<String, Object> initWxPcLoginConfig(String wxConfigType, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> domainMap = atlasConfigService.getConfigValueMap("domain");
		StringBuffer sb = new StringBuffer();
		sb.append("http://");
		sb.append(domainMap.get("domain_name"));
		String sitePort = StringUtil.toString(domainMap.get("site_port"));
		if (!StringUtil.isNullString(sitePort) && !"80".equals(sitePort)) {
			sb.append(":" + sitePort);
		}
		sb.append("/");
		sb.append(domainMap.get("admin_site_name"));
		sb.append("/wx-callback.html");
		if (logger.isDebugEnabled()) {
			logger.debug("redirectUri:" + sb.toString());
		}
		String state = WxConfigTypeEnum.PCWXLOGIN.getType() + "-" + StringUtil.randomString(8);
		if (logger.isDebugEnabled()) {
			logger.debug("state:" + state);
		}
		Map<String, Object> wxConfigMap = atlasConfigService.getConfigValueMap("wx_config");
		String redirectUri = "";
		try {
			redirectUri = URLEncoder.encode(sb.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		session.setAttribute("wxLoginState", state);
		map.put("state", state);
		map.put("response_type", "code");
		map.put("scope", "snsapi_login");
		map.put("app_id", wxConfigMap.get("app_id"));
		map.put("redirect_uri", redirectUri);
		if (logger.isDebugEnabled()) {
			logger.debug("loginConfigMap:" + map);
		}
		return map;
	}
	
	@Override
	public List<AsWorksDto> queryTrashWorks(Integer memberId, String encryptKey) {
		AsGallary asGallary = this.queryMemberGallery(memberId, encryptKey);
		List<AsWorks> trashWorkList = this.asWorksMapper.selectTrashWorkByGalleryId(asGallary.getId());
		if (logger.isDebugEnabled()) {
			logger.debug("queryTrashWorks-->trashWorkList:" + trashWorkList);
		}
		List<AsWorksDto> worksDtoList = this.convertToWorksDto(trashWorkList);
		if (logger.isDebugEnabled()) {
			logger.debug("queryTrashWorks-->worksDtoList:" + worksDtoList);
		}
		return worksDtoList;
	}
	
	@Override
	public AsWorks modifyWorksName(Integer memberId, String encryptKey, Integer worksId, String worksName) {
		if (logger.isDebugEnabled()) {
			logger.debug("modifyWorksName-->memberId:" + memberId);
			logger.debug("modifyWorksName-->encryptKey:" + encryptKey);
			logger.debug("modifyWorksName-->worksId:" + worksId);
			logger.debug("modifyWorksName-->worksName:" + worksName);
		}
		if (StringUtil.isNullString(worksName)) {
			throw new AppException(AppException.BusiAppCode.NULL_ASWORKS_NAME);
		} else {
			if (worksName.length() >= 20) {
				throw new AppException(AppException.BusiAppCode.NULL_ASWORKS_NAME, "作品集名称长度不能超过20个字");
			}
		}
		AsWorks asWorks = this.queryWorks(memberId, encryptKey, worksId);
		asWorks.updateWorksName(worksName);
		if (logger.isDebugEnabled()) {
			logger.debug("modifyWorksName-->asWorks:" + asWorks);
		}
		this.asWorksMapper.updateByPrimaryKeySelective(asWorks);
		return asWorks;
	}
	
	@Override
	public List<AsMaterial> queryAllMaterial() {
		if (logger.isDebugEnabled()) {
			logger.debug("queryAllMaterial");
		}
		List<AsMaterial> materialList = this.asMaterialMapper.selectAll();
		if(materialList == null){
			materialList = new ArrayList<AsMaterial>();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("queryAllMaterial->materialList:{"+materialList+"}");
		}
		return materialList;
	}
	@Override
	public List<AsArtworkDto> loadAllArtworkDto(Integer memberId, String encryptKey, Integer worksId) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryAllArtworkDto-->worksId:" + worksId);
		}
		AsWorks asWorks = this.queryWorks(memberId, encryptKey, worksId);
		if (asWorks == null) {
			throw new AppException(AppException.BusiAppCode.NULL_ASWORKS);
		}
		List<AsArtworkDto> allArtworkDto = this.queryAllArtworks(memberId, asWorks.getId());
		if (logger.isDebugEnabled()) {
			logger.debug("queryAllArtworkDto-->allArtworkDto:" + allArtworkDto);
		}
		return allArtworkDto;
	}
	
	@Override
	public AsWorks queryWorks(Integer memberId, String encryptKey, Integer worksId) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryWorks-->worksId:" + worksId);
		}
		if (worksId == null) {
			throw new AppException(AppException.BusiAppCode.NULL_ASWORKS_ID);
		}
		AsGallary asGallary = this.queryMemberGallery(memberId, encryptKey);
		AsWorks asWorks = this.queryAsWorks(memberId, worksId, asGallary.getId());
		return asWorks;
	}
	
	@Override
	public Integer createWorks(Integer memberId, String encryptKey) {
		AsGallary asGallary = this.queryMemberGallery(memberId, encryptKey);
		String worksName = "未命名";
		Integer worksId = this.createWorks(memberId, asGallary.getId(), worksName);
		return worksId;
	}
	
	@Override
	public List<AsWorksDto> loadWorksList(Integer memberId, String encryptKey, Integer isOnlyDesk,
			Integer isOrderLastDay) {
		AsGallary asGallary = this.queryMemberGallery(memberId, encryptKey);
		Boolean onlyDesk = false;
		Boolean orderLastDay = false;
		if (isOnlyDesk != null && isOnlyDesk == 1) {
			onlyDesk = true;
		}
		if (isOrderLastDay != null && isOrderLastDay == 1) {
			orderLastDay = true;
		}
		List<AsWorksDto> worksList = this.queryWorks(memberId, asGallary.getId(), onlyDesk, orderLastDay);

		return worksList;
	}
	
	@Override
	public AsGallary queryMemberGallery(Integer memberId, String encryptKey) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryMemberGallery-->memberId:" + memberId);
			logger.debug("queryMemberGallery-->encryptKey:" + encryptKey);
		}
		if (memberId == null || memberId == 0) {
			throw new AppException(AppException.BusiAppCode.NULL_MEMBER_ID);
		}
		EsMember member = this.queryMember(memberId, encryptKey);
		if (logger.isDebugEnabled()) {
			logger.debug("queryMemberGallery-->member:" + member);
		}
		if (member == null || member.isScrapMember()) {
			throw new AppException(AppException.BusiAppCode.NULL_MEMBER);
		}
		AsGallery2MemberKey asGallery2MemberKey = this.asGallery2MemberMapper.selectByMemberId(member.getMemberId());
		if (logger.isDebugEnabled()) {
			logger.debug("queryMemberGallery-->asGallery2MemberKey:" + asGallery2MemberKey);
		}
		if (asGallery2MemberKey == null) {
			throw new AppException(AppException.BusiAppCode.NULL_MEMBER_GALLERY);
		}
		AsGallary asGallary = asGallery2MemberKey.getAsGallary();
		if (asGallary == null) {
			throw new AppException(AppException.BusiAppCode.NULL_GALLERY);
		}
		return asGallary;
	}

	@Override
	public List<AsWorksDto> queryWorks(Integer adminMemberId, Integer galleryId, boolean onlyDesk,
			boolean orderLastDay) {
		AsGallary asGallary = this.getAsGallary(adminMemberId, galleryId);
		if (asGallary == null) {
			throw new AppException(AppException.BusiAppCode.NULL_GALLERY);
		}
		List<AsWorks> worksList = null;
		if (onlyDesk) {
			String isOrderLastDay = "0";
			if (orderLastDay) {
				isOrderLastDay = "1";
			}
			worksList = this.asWorksMapper.selectByGalleryIdOrderFavorite(galleryId, "Y", isOrderLastDay);
		} else {
			worksList = this.asWorksMapper.selectByGalleryIdOrderLastUpdate(galleryId);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("queryWorks-->worksList:" + worksList);
		}
		List<AsWorksDto> worksDtoList = this.convertToWorksDto(worksList);
		if (logger.isDebugEnabled()) {
			logger.debug("queryWorks-->worksDtoList:" + worksDtoList);
		}
		return worksDtoList;
	}

	@Override
	public Integer createWorks(Integer adminMemberId, Integer galleryId, String worksName) {
		AsGallary asGallary = this.getAsGallary(adminMemberId, galleryId);
		AsWorks asWorks = new AsWorks();
		asWorks.create(adminMemberId, asGallary.getId(), worksName);
		if (logger.isDebugEnabled()) {
			logger.debug("createWorks-->asWorks:" + asWorks);
		}
		this.asWorksMapper.insertSelective(asWorks);
		return asWorks.getId();
	}

	@Override
	public void removeWorks(Integer memberId, String encryptKey, Integer worksId) {
		if (logger.isDebugEnabled()) {
			logger.debug("removeAsWorks-->worksId:" + worksId);
		}
		AsGallary gallary = this.queryMemberGallery(memberId, encryptKey);
		if (gallary == null) {
			throw new AppException(AppException.BusiAppCode.NULL_GALLERY);
		}
		AsWorks asWorks = this.queryAsWorks(memberId, worksId, gallary.getId());
		if (asWorks == null) {
			throw new AppException(AppException.BusiAppCode.NULL_ASWORKS);
		}
		asWorks.updateState(memberId, StateEnum.SCRAP.getState());
		this.asWorksMapper.updateByPrimaryKeySelective(asWorks);
	}

	@Override
	public String modifyWorks4Priview(Integer memberId, String encryptKey, Integer worksId) {
		// 生成临时预览连接 生成预览二维码
		// 获取as_config配置中的预览时间 ，并将其设置到作品集的priviewEndDate
		if (logger.isDebugEnabled()) {
			logger.debug("modifyWorks4Priview-->memberId:" + memberId);
			logger.debug("modifyWorks4Priview-->encryptKey:" + encryptKey);
			logger.debug("modifyWorks4Priview-->worksId:" + worksId);
		}
		AsWorks asWorks = this.queryWorks(memberId, encryptKey, worksId);
		if (asWorks == null) {
			throw new AppException(AppException.BusiAppCode.NULL_ASWORKS);
		}
		// 生成32位作品集预览码
		String priviewCode = StringUtil.randomString(32);
		if (logger.isDebugEnabled()) {
			logger.debug("modifyWorks4Priview-->priviewCode:" + priviewCode);
		}
		String priviewEffect = atlasConfigService.getConfigValue(PRIVIEWEFFECT);
		// 写入预览时间 预览码
		asWorks.createPriviewUrl(priviewCode, priviewEffect);
		if (logger.isDebugEnabled()) {
			logger.debug("modifyWorks4Priview-->asWorks:" + asWorks);
		}
		this.asWorksMapper.updateByPrimaryKeySelective(asWorks);
		String qrUrl = this.createQrUrl("preview.html", "previewCode", priviewCode);
		String qrImageUrl = this.createQrImage(qrUrl);
		return qrImageUrl;
	}
	
	@Override
	public List<AsArtworkDto> queryAllArtworks(Integer memberId, Integer worksId) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryAllArtworks->worksId:" + worksId);
		}
		if (worksId == null) {
			throw new AppException(AppException.BusiAppCode.NULL_ASWORKS_ID);
		}
		AsWorks work = this.asWorksMapper.selectWorksByState(worksId, StateEnum.NORMAL.getState());
		if (logger.isDebugEnabled()) {
			logger.debug("queryAllArtworks->work:" + work);
		}
		if (work == null) {
			throw new AppException(AppException.BusiAppCode.NULL_ASWORKS);
		}
		// 判断memberId是作品集管理员
		if (work.getOwnMemberId().intValue() != memberId.intValue()) {
			// 若不是则判断该作品集是否为不可见
			if (!work.ifPrivate()) {
				throw new AppException(AppException.BusiAppCode.PRIVATE_ASWORKS);
			}
		}
		List<AsArtwork> asArtworkList = this.asArtwork2WoksMapper.selectArtworksByWorksId(work.getId());
		if (logger.isDebugEnabled()) {
			logger.debug("queryAllArtworks-->asArtworkList:" + asArtworkList);
		}
		List<AsArtworkDto> allArtworks = this.convertToArtworkDtoPage(asArtworkList);
		if (logger.isDebugEnabled()) {
			logger.debug("queryAllArtworks-->allArtworks:" + allArtworks);
		}

		return allArtworks;
	}

	@Override
	public PagedList<AsArtworkDto> queryArtworks(Integer memberId, Integer worksId, Map<String, String> pageParam) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryArtworks->worksId:" + worksId);
		}
		if (worksId == null) {
			throw new AppException(AppException.BusiAppCode.NULL_ASWORKS_ID);
		}
		AsWorks work = this.asWorksMapper.selectWorksByState(worksId, StateEnum.NORMAL.getState());;
		if (logger.isDebugEnabled()) {
			logger.debug("queryArtworks->work:" + work);
		}
		if (work == null) {
			throw new AppException(AppException.BusiAppCode.NULL_ASWORKS);
		}
		// 判断memberId是作品集管理员
		if (work.getOwnMemberId().intValue() != memberId.intValue()) {
			// 若不是则判断该作品集是否为不可见
			if (!work.ifPrivate()) {
				throw new AppException(AppException.BusiAppCode.PRIVATE_ASWORKS);
			}
		}
		Map<String, String> pageMap = this.getPageMap(null, null, pageParam);

		PagedList<AsArtwork> artworkPage = this.asArtworkPageDao.selectArtworkPageByWorkId(pageMap);
		if (logger.isDebugEnabled()) {
			logger.debug("queryArtworks->artworkPage:" + artworkPage);
		}

		PagedList<AsArtworkDto> artworkDtoPage = this.convertToArtworkDtoPage(artworkPage);
		if (logger.isDebugEnabled()) {
			logger.debug("queryArtworks->artworkDtoPage:" + artworkDtoPage);
		}
		return artworkDtoPage;
	}

	@Override
	public Integer createArtwork(Integer memberId, String encryptKey, Integer worksId,
			Map<String, Object> artworkInfo) {
		if (logger.isDebugEnabled()) {
			logger.debug("createArtwork-->memberId:" + memberId);
			logger.debug("createArtwork-->worksId:" + worksId);
			logger.debug("createArtwork-->artworkInfo:" + artworkInfo);
		}
		Integer artistId = StringUtil.toInt(artworkInfo.get("artistId"), false);
		String artistName = StringUtil.toString(artworkInfo.get("artistName"), false);
		if (artistId != 0 && artistId != null) {
			AsArtist artist = this.queryArtist(artistId);
			if (artist == null) {
				throw new AppException(AppException.BusiAppCode.NULL_ARTIST);
			}
			artistName = artist.getName();
		}
		artworkInfo.put("artistId", artistId);
		artworkInfo.put("artistName", artistName);
		artworkInfo = this.getArtworkInfo(artworkInfo);
		AsWorks asWorks = this.queryWorks(memberId, encryptKey, worksId);
		AsArtwork asArtwork = new AsArtwork();
		int maxSeq = this.asArtwork2WoksMapper.selectMaxSeq(asWorks.getId());
		asArtwork.create(artworkInfo, memberId, maxSeq);
		if (logger.isDebugEnabled()) {
			logger.debug("createArtwork-->asArtwork:" + asArtwork);
		}
		this.asArtWorkMapper.insertSelective(asArtwork);
		if (logger.isDebugEnabled()) {
			logger.debug("createArtwork-->asArtwork:" + asArtwork);
		}
		AsArtwork2WoksKey artwork2WoksKey = new AsArtwork2WoksKey();
		artwork2WoksKey.create(asWorks.getId(), asArtwork.getId());
		if (logger.isDebugEnabled()) {
			logger.debug("createArtwork-->artwork2WoksKey:" + artwork2WoksKey);
		}
		this.asArtwork2WoksMapper.insertSelective(artwork2WoksKey);
		asWorks.worksUpdateTime();
		asWorks.setArtworksNum(asWorks.getArtworksNum().intValue() + 1);
		this.asWorksMapper.updateByPrimaryKeySelective(asWorks);
		return asArtwork.getId();
	}

	@Override
	public void modifyArtwork(Integer memberId, Integer galleryId, Integer worksId,
			Map<String, Object> artworkInfo) {
		if (logger.isDebugEnabled()) {
			logger.debug("modifyArtwork-->memberId:" + memberId);
			logger.debug("modifyArtwork-->galleryId:" + galleryId);
			logger.debug("modifyArtwork-->worksId:" + worksId);
			logger.debug("modifyArtwork-->artworkInfo:" + artworkInfo);
		}
		Integer artWorkId = StringUtil.toInt(artworkInfo.get("artWorkId"), null, false);
		if (artWorkId == null || artWorkId == 0) {
			throw new AppException(AppException.BusiAppCode.NULL_ASARTWORK_ID);
		}
		AsWorks asWorks = this.queryAsWorks(memberId, worksId, galleryId);
		if (asWorks == null) {
			throw new AppException(AppException.BusiAppCode.NULL_ASWORKS);
		}
		
		Integer materialId = StringUtil.toInt(artworkInfo.get("materialId"), false);
		AsMaterial asMaterial = this.asMaterialMapper.selectByPrimaryKey(materialId);
		if(asMaterial==null){
			throw new AppException(AppException.BusiAppCode.NULL_MATERIAL);
		}
		Integer artistId = StringUtil.toInt(artworkInfo.get("artistId"), false);
		String artistName = StringUtil.toString(artworkInfo.get("artistName"),false);
		if (artistId != 0 && artistId != null) {
			AsArtist artist = this.queryArtist(artistId);
			if(artist==null){
				throw new AppException(AppException.BusiAppCode.NULL_ARTIST);
			}
			artistName = artist.getName();
		}
		artworkInfo.put("artistId", artistId);
		artworkInfo.put("artistName", artistName);
		artworkInfo = this.getArtworkInfo(artworkInfo);
		AsArtwork asArtwork = this.queryArtwork(artWorkId,asWorks);
		int maxSeq = this.asArtwork2WoksMapper.selectMaxSeq(asWorks.getId());
		asArtwork.create(artworkInfo, memberId,maxSeq);
		if (logger.isDebugEnabled()) {
			logger.debug("modifyArtwork-->asArtwork:" + asArtwork);
		}
		this.asArtWorkMapper.updateByPrimaryKeySelective(asArtwork);
	}

	@Override
	public void modifyArtworkSeq(Integer memberId, String encryptKey, Integer worksId, Integer[] artworkIds) {
		// 修改作品顺序
		if (logger.isDebugEnabled()) {
			logger.debug("modifyArtworkSeq-->memberId:" + memberId);
			logger.debug("modifyArtworkSeq-->encryptKey:" + encryptKey);
			logger.debug("modifyArtworkSeq-->worksId:" + worksId);
			logger.debug("modifyArtworkSeq-->artworkIds:" + artworkIds);
		}
		AsWorks asWorks = this.queryWorks(memberId, encryptKey, worksId);
		if (artworkIds == null) {
			throw new AppException(AppException.BusiAppCode.NULL_ASARTWORK_ID);
		}
		if (artworkIds.length == 0) {
			throw new AppException(AppException.BusiAppCode.NULL_ASARTWORK_ID);
		}
		int artworkCount = this.asArtwork2WoksMapper.selectArtworkCountByWorksId(worksId);
		if (artworkCount != artworkIds.length) {
			throw new AppException(AppException.BusiAppCode.ERROR_NUM_ASARTWORK);
		}
		List<AsArtwork> artworksList = this.queryArtworksList(artworkIds, asWorks);
		for (int i = 0; i < artworksList.size(); i++) {
			AsArtwork asArtwork = artworksList.get(i);
			asArtwork.updateArtworkSeq(i);
			this.asArtWorkMapper.updateByPrimaryKeySelective(asArtwork);
		}
		asWorks.worksUpdateTime();
		this.asWorksMapper.updateByPrimaryKeySelective(asWorks);
	}
	
	@Override
	public AsArtwork queryArtwork(Integer memberId, String encryptKey, Integer worksId, Integer artworkId) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryArtwork-->memberId:" + memberId);
			logger.debug("queryArtwork-->encryptKey:" + encryptKey);
			logger.debug("queryArtwork-->worksId:" + worksId);
			logger.debug("queryArtwork-->artworkId:" + artworkId);
		}
		if (artworkId == null || artworkId == 0) {
			throw new AppException(AppException.BusiAppCode.NULL_ASARTWORK_ID);
		}
		AsWorks asWorks = this.queryWorks(memberId, encryptKey, worksId);
		AsArtwork asArtwork = this.queryArtwork(artworkId, asWorks);
		return asArtwork;
	}
	
	private AsArtwork queryArtwork(Integer artworkId,AsWorks asWorks){
		if(artworkId==null || artworkId == 0){
			throw new AppException(AppException.BusiAppCode.NULL_ASARTWORK_ID);
		}
		if(asWorks==null){
			throw new AppException(AppException.BusiAppCode.NULL_ASWORKS);
		}
		AsArtwork asArtwork = this.asArtwork2WoksMapper.selectByWorksIdArtworkId(artworkId, asWorks.getId());
		if (logger.isDebugEnabled()) {
			logger.debug("queryArtwork-->asArtwork:" + asArtwork);
		}
		if (asArtwork == null) {
			throw new AppException(AppException.BusiAppCode.NULL_ASARTWORK);
		}
		return asArtwork;
	}
	
	private List<AsArtwork> queryArtworksList(Integer[] artworkIds,AsWorks asWorks){
		if(logger.isDebugEnabled()){
			logger.debug("queryArtWorksList-->artworkIds:"+artworkIds);
		}
		List<AsArtwork> asArtworkList = new ArrayList<AsArtwork>();
		for (int i = 0; i < artworkIds.length; i++) {
			AsArtwork asArtwork = this.queryArtwork(artworkIds[i], asWorks);
			asArtworkList.add(asArtwork);
		}
		if(logger.isDebugEnabled()){
			logger.debug("queryArtWorksList-->asArtworkList:"+asArtworkList);
		}
		return asArtworkList;
	}
	
	@Override
	public void removeArtwork(Integer memberId, String encryptKey, Integer worksId, Integer artworkId) {
		if (logger.isDebugEnabled()) {
			logger.debug("removeArtwork-->memberId:" + memberId);
			logger.debug("removeArtwork-->encryptKey:" + encryptKey);
			logger.debug("removeArtwork-->worksId:" + worksId);
			logger.debug("removeArtwork-->artworkId:" + artworkId);
		}
		AsWorks asWorks = this.queryWorks(memberId, encryptKey, worksId);
		AsArtwork asArtwork = this.queryArtwork(artworkId, asWorks);
		asArtwork.delete(memberId);
		if (logger.isDebugEnabled()) {
			logger.debug("removeArtwork-->asArtwork:" + asArtwork);
		}
		this.asArtWorkMapper.updateByPrimaryKeySelective(asArtwork);
		asWorks.worksUpdateTime();
		Integer artworksNum = asWorks.getArtworksNum() - 1;
		if (artworksNum.intValue() < 0) {
			artworksNum = 0;
		}
		asWorks.setArtworksNum(artworksNum);
		this.asWorksMapper.updateByPrimaryKeySelective(asWorks);
	}
	
	public String createQrUrl(String pageStr, String codeStr, String code) {
		Map<String, Object> domainMap = atlasConfigService.getConfigValueMap(DOMAIN);
		StringBuilder sb = new StringBuilder();
		sb.append("http://");
		sb.append(domainMap.get("domain_name"));
		String sitePort = StringUtil.toString(domainMap.get("site_port"));
		if (!StringUtil.isNullString(sitePort) && !"80".equals(sitePort)) {
			sb.append(":" + sitePort);
		}
		sb.append("/");
		sb.append(domainMap.get("web_site_name"));
		sb.append("/");
		sb.append(pageStr);
		if (!StringUtil.isNullString(codeStr) && !StringUtil.isNullString(code)) {
			sb.append("?" + codeStr + "=");
			sb.append(code);
		}
		String qrUrl = sb.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("qrUrl:" + qrUrl);
		}
		return qrUrl;
	}
	
	public String createQrImage(String url) {
		if (logger.isDebugEnabled()) {
			logger.debug("createQrImage-->url:" + url);
		}
		String genarateQr = QrUtil.genarateQr(url);
		String qrImageUrl = "";
		if (genarateQr != null) {
			try {
				String file = AtalasAdminServiceImpl.class.getClassLoader().getResource("/").getPath() + "../"
						+ genarateQr;
				File genarateFile = new File(file);
				if (genarateFile.exists()) {
					FileInputStream fileInputStream = new FileInputStream(genarateFile);
					IUploader uploader = UploadFacatory.getUploaer();
					qrImageUrl = uploader.upload(fileInputStream, QRNAME, genarateQr);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
		if (logger.isDebugEnabled()) {
			logger.debug("createQrImage-->qrImageUrl:" + qrImageUrl);
		}
		return qrImageUrl;
	}
	
	public AsWorks queryTrashWorks(Integer memberId, Integer worksId, Integer galleryId) {
		if (worksId == null || worksId.intValue() == 0) {
			throw new AppException(AppException.BusiAppCode.NULL_ASWORKS_ID);
		}
		AsGallary asGallary = this.getAsGallary(memberId, galleryId);
		AsWorks works = this.asWorksMapper.selectByPrimaryKey(worksId);
		if (logger.isDebugEnabled()) {
			logger.debug("queryTrashWorks-->works:" + works);
		}
		if (works == null) {
			throw new AppException(AppException.BusiAppCode.NULL_ASWORKS);
		}
		if (works.getGalleryId().intValue() != asGallary.getId().intValue()) {
			throw new AppException(AppException.BusiAppCode.NO_GALLERY_ASWORKS);
		}
		return works;
	}

	/**
	 * 根据作品集Id获取作品集
	 * 
	 * @param adminMemberId管理员Id
	 * @param worksId
	 *            作品集Id
	 * @param galleryId
	 *            画廊Id
	 * @return
	 */
	public AsWorks queryAsWorks(Integer adminMemberId, Integer worksId, Integer galleryId) {
		if (worksId == null) {
			throw new AppException(AppException.BusiAppCode.NULL_ASWORKS_ID);
		}
		AsGallary asGallary = this.getAsGallary(adminMemberId, galleryId);
		AsWorks asWorks = this.asWorksMapper.selectWorksByState(worksId, StateEnum.NORMAL.getState());
		if (logger.isDebugEnabled()) {
			logger.debug("queryAsWorks-->asWorks:" + asWorks);
		}
		if (asWorks == null) {
			throw new AppException(AppException.BusiAppCode.NULL_ASWORKS);
		}
		if (asWorks.getGalleryId().intValue() != asGallary.getId().intValue()) {
			throw new AppException(AppException.BusiAppCode.NO_GALLERY_ASWORKS);
		}
		return asWorks;
	}
	
	private List<AsArtworkDto> convertToArtworkDtoPage(List<AsArtwork> artworkList) {
		List<AsArtworkDto> artworkDtoList = new ArrayList<AsArtworkDto>();
		if (artworkList.size() > 0) {
			for (AsArtwork artwork : artworkList) {
				AsArtworkDto dto = new AsArtworkDto();
				dto.create(artwork);
				artworkDtoList.add(dto);
			}
		}
		return artworkDtoList;
	}
	
	private PagedList<AsArtworkDto> convertToArtworkDtoPage(PagedList<AsArtwork> artworkPage) {
		PagedList<AsArtworkDto> artworkDtoPage = new PagedList<AsArtworkDto>();
		artworkDtoPage.setPageSize(artworkPage.getPageSize());
		artworkDtoPage.setCurrentPage(artworkPage.getCurrentPage());
		artworkDtoPage.setTotalCount(artworkPage.getTotalCount());
		artworkDtoPage.setTotalPage(artworkPage.getTotalPage());

		List<AsArtworkDto> artworkDtoList = new ArrayList<AsArtworkDto>();
		List<AsArtwork> artworkList = artworkPage.getResult();
		if (artworkList.size() > 0) {
			for (AsArtwork artwork : artworkList) {
				AsArtworkDto dto = new AsArtworkDto();
				dto.create(artwork);
				artworkDtoList.add(dto);
			}
		}
		artworkDtoPage.setResult(artworkDtoList);
		return artworkDtoPage;
	}

	private List<AsWorksDto> convertToWorksDto(List<AsWorks> worksList) {
		List<AsWorksDto> worksDtoList = new ArrayList<AsWorksDto>();
		if (worksList.size() > 0) {
			for (AsWorks works : worksList) {
				AsWorksDto dto = new AsWorksDto();
				dto.create(works);
				worksDtoList.add(dto);
			}
		}
		return worksDtoList;
	}

	/**
	 * 得到分页基数Map
	 * 
	 * @param pageSize
	 * @param currentPage
	 * @param pageParam
	 * @return
	 */
	public Map<String, String> getPageMap(Integer pageSize, Integer currentPage, Map<String, String> pageParam) {
		if (pageSize == null) {
			pageSize = 20;
		}
		if (currentPage == null) {
			currentPage = 1;
		}
		if (pageParam != null) {
			if (!pageParam.isEmpty()) {
				pageSize = StringUtil.toInt(pageParam.get("pageSize"), pageSize, false);
				currentPage = StringUtil.toInt(pageParam.get("currentPage"), currentPage, false);
			}
		}
		Map<String, String> pageMap = new HashMap<String, String>();
		pageMap.put("pageSize", pageSize.toString());
		pageMap.put("currentPage", currentPage.toString());

		return pageMap;

	}

	/**
	 * 根据画廊编号查询画廊并判断其权限
	 * 
	 * @param adminMemberId
	 * @param galleryId
	 * @return
	 */
	public AsGallary getAsGallary(Integer adminMemberId, Integer galleryId) {
		if (logger.isDebugEnabled()) {
			logger.debug("getAsGallary-->adminMemberId:" + adminMemberId);
			logger.debug("getAsGallary-->galleryId:" + galleryId);
		}
		if (galleryId == null) {
			throw new AppException(AppException.BusiAppCode.NULL_GALLERY_ID);
		}
		AsGallary gallary = this.asGallaryMapper.selectByPrimaryKey(galleryId);
		if (logger.isDebugEnabled()) {
			logger.debug("getAsGallary-->asGallary:" + gallary);
		}
		if (gallary == null) {
			throw new AppException(AppException.BusiAppCode.NULL_GALLERY);
		}
		// 判断是否其管理员管理的画廊
//		if (gallary.getOwnMemberId().intValue() != adminMemberId.intValue()) {
//			throw new AppException(AppException.BusiAppCode.NO_AUTHORITY_GALLERY);
//		}
		return gallary;
	}
	
	@SuppressWarnings("rawtypes")
	private Map<String, Object> getArtworkInfo(Map<String, Object> artworkInfo){
		try {
			String imageUrl = StringUtil.toString(artworkInfo.get("imageUrl"), false);
			if(StringUtil.isNullString(imageUrl)){
				throw new AppException(AppException.BusiAppCode.ERROR_IMAGE);
			}
			int i = imageUrl.indexOf("?");
			if (i != -1) {
				imageUrl = imageUrl.substring(0, i);
			}
			String data = LoadDataByURL.getAllData(imageUrl+"?x-oss-process=image/info");
			if(logger.isDebugEnabled()){
				logger.debug("getArtWorkImag-->data:"+data);
			}
			JSONObject o =	JSON.parseObject(data);
 			
			String imageLength = o.getJSONObject("ImageWidth").getString("value") ;
 			String imageHeight =o.getJSONObject("ImageHeight").getString("value") ;
			String imageSize =o.getJSONObject("FileSize").getString("value") ;
			artworkInfo.put("imageUrl", imageUrl);
			artworkInfo.put("imageLength", imageLength);
			artworkInfo.put("imageHeight", imageHeight);
			artworkInfo.put("imageSize", imageSize);
			artworkInfo.put("bigImageUrl", imageUrl + "?x-oss-process=image/resize,p_50");
			artworkInfo.put("bigImageLength", imageLength);
			artworkInfo.put("bigImageHeight", imageHeight);
			artworkInfo.put("bigImageSize", imageSize);
			artworkInfo.put("smallImageUrl", imageUrl + "?x-oss-process=image/resize,p_20");
			artworkInfo.put("smallImageLength", imageLength);
			artworkInfo.put("smallImageHeight", imageHeight);
			artworkInfo.put("smallImageSize", imageSize);
			return artworkInfo;
		} catch (Exception e) {
			throw new AppException(AppException.BusiAppCode.ERROR_IMAGE);
		}
	}
	
	private AsArtist queryArtist(Integer artistId) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryArtist-->artistId:" + artistId);
		}
		AsArtist asArtist = this.asArtistMapper.selectByPrimaryKey(artistId);
		if (logger.isDebugEnabled()) {
			logger.debug("queryArtist-->asArtist:" + asArtist);
		}
		return asArtist;
	}

	private EsMember queryMember(Integer memberId, String encryptKey) {
		if (logger.isDebugEnabled()) {
			logger.debug("queryMember-->memberId:" + memberId);
			logger.debug("queryMember-->encryptKey:" + encryptKey);
		}
		if (memberId == null || memberId == 0) {
			throw new AppException(AppException.BusiAppCode.NULL_MEMBER_ID);
		}
		EsMember member = this.esMemberMapper.selectByEncryptKey(memberId, encryptKey);
		if (logger.isDebugEnabled()) {
			logger.debug("queryMember-->member:" + member);
		}
		if (member == null) {
			throw new AppException(AppException.BusiAppCode.NULL_MEMBER);
		}
		if (member.isScrapMember()) {
			throw new AppException(AppException.BusiAppCode.DISABLED_MEMBER);
		}
		AsGallery2MemberKey asGallery2MemberKey = this.asGallery2MemberMapper.selectByMemberId(member.getMemberId());
		if (logger.isDebugEnabled()) {
			logger.debug("queryMember-->asGallery2MemberKey:" + asGallery2MemberKey);
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
	public EsMemberDto queryMemberDto(Integer memberId, String encryptKey) {

		EsMember member = this.queryMember(memberId, encryptKey);
		EsMemberDto memberDto = new EsMemberDto();
		memberDto.create(member);
		
		return memberDto;
	}
	@Override
	public void saveFeedBack(Integer memberId, String encryptKey, String detail) {
		if (logger.isDebugEnabled()) {
			logger.debug("saveFeedBack-->memberId:" + memberId);
			logger.debug("saveFeedBack-->encryptKey:" + encryptKey);
		}
		EsMember member = this.queryMember(memberId, encryptKey);
		AsFeedback feedback = new AsFeedback();
		feedback.create(null, detail, member.getMemberId());
		if (logger.isDebugEnabled()) {
			logger.debug("saveFeedBack-->feedback:" + feedback);
		}
		
		this.asFeedbackMapper.insertSelective(feedback);
	}

}
