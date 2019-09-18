package com.yetoop.cloud.atlas.domain;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.yetoop.cloud.atlas.common.StringUtil;
import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.StateEnum;
import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.WorksFavoriteFlagEnum;
import com.yetoop.cloud.atlas.exception.AppException;

public class AsWorks {
	
	public void createPriviewUrl(String priviewCode, String priviewEffect) {
		Date now = new Date();
		this.priviewCode = priviewCode;
		Date priviewEndDate = DateUtils.addMinutes(now, StringUtil.toInt(priviewEffect, 10));
		this.priviewEndDate = priviewEndDate;
		this.version = now;
		this.lastUpdate = now;
	}
	
	public void worksUpdateTime(){
		Date now = new Date();
		this.version = now;
		this.lastUpdate = now;
	}

	public void updateWorksPermission(String viewUrl, String viewCode, String viewUrlQr, String viewPermission) {
		Date now = new Date();
		this.viewUrl = viewUrl;
		this.viewCode = viewCode;
		this.viewUrlQr = viewUrlQr;
		this.viewPermission = viewPermission;
		this.version = now;
		this.lastUpdate = now;
	}
	
	public void updateWorksName(String worksName) {
		Date now = new Date();
		this.name = worksName;
		this.version = now;
		this.lastUpdate = now;
	}

	public void updateState(Integer memberId, String state) {
		if (memberId == null || this.ownMemberId == null || this.ownMemberId.intValue() != memberId.intValue()) {
			throw new AppException(AppException.BusiAppCode.NO_AUTHORITY_MODIFY_WORKS, "无权删除此作品集");
		}
		this.state = state;
		this.stateDate = new Date();
	}

	public void create(Integer memberId, Integer galleryId, String worksName) {
		Date now = new Date();
		this.name = worksName;
		this.createDate = now;
		this.version = now;
		this.state = StateEnum.NORMAL.getState();
		this.stateDate = now;
		this.ownMemberId = memberId;
		this.artworksNum = 0;
		this.seq = StringUtil.toInt(Long.toString(System.currentTimeMillis()), false);
		this.favoriteFlag = WorksFavoriteFlagEnum.Y.getFlag();
		this.lastUpdate = now;
		this.galleryId = galleryId;
		this.viewPermission = ViewPermissionEnum.PRIVATE.getCode();
	}

	public boolean ifPrivate() {
		if (ViewPermissionEnum.PRIVATE.getCode().equals(this.viewPermission)) {
			return true;
		} else {
			return false;
		}
	}

	public static enum ViewPermissionEnum {
		PUBLIC("1"), LINK("2"), PRIVATE("3");

		private String code;

		private ViewPermissionEnum(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
	}

	private AsGallary gallery;

	private Integer id;

	private String name;

	private String logoUrl;

	private String intro;

	private Date createDate;

	private Date version;

	private String state;

	private Date stateDate;

	private Integer ownMemberId;

	private Integer artworksNum;

	private Integer seq;

	private String favoriteFlag;

	private Date lastUpdate;

	private Integer galleryId;

	private String priviewCode;

	private Date priviewEndDate;

	private String viewPermission;

	private String viewCode;

	private String viewUrl;

	private String viewUrlQr;

	public AsGallary getGallery() {
		return gallery;
	}

	public void setGallery(AsGallary gallery) {
		this.gallery = gallery;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getStateDate() {
		return stateDate;
	}

	public void setStateDate(Date stateDate) {
		this.stateDate = stateDate;
	}

	public Integer getOwnMemberId() {
		return ownMemberId;
	}

	public void setOwnMemberId(Integer ownMemberId) {
		this.ownMemberId = ownMemberId;
	}

	public Integer getArtworksNum() {
		return artworksNum;
	}

	public void setArtworksNum(Integer artworksNum) {
		this.artworksNum = artworksNum;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getFavoriteFlag() {
		return favoriteFlag;
	}

	public void setFavoriteFlag(String favoriteFlag) {
		this.favoriteFlag = favoriteFlag;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Integer getGalleryId() {
		return galleryId;
	}

	public void setGalleryId(Integer galleryId) {
		this.galleryId = galleryId;
	}

	public String getPriviewCode() {
		return priviewCode;
	}

	public void setPriviewCode(String priviewCode) {
		this.priviewCode = priviewCode;
	}

	public Date getPriviewEndDate() {
		return priviewEndDate;
	}

	public void setPriviewEndDate(Date priviewEndDate) {
		this.priviewEndDate = priviewEndDate;
	}

	public String getViewPermission() {
		return viewPermission;
	}

	public void setViewPermission(String viewPermission) {
		this.viewPermission = viewPermission;
	}

	public String getViewCode() {
		return viewCode;
	}

	public void setViewCode(String viewCode) {
		this.viewCode = viewCode;
	}

	public String getViewUrl() {
		return viewUrl;
	}

	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}

	public String getViewUrlQr() {
		return viewUrlQr;
	}

	public void setViewUrlQr(String viewUrlQr) {
		this.viewUrlQr = viewUrlQr;
	}

	@Override
	public String toString() {
		return "AsWorks [gallery=" + gallery + ", id=" + id + ", name=" + name + ", logoUrl=" + logoUrl + ", intro="
				+ intro + ", createDate=" + createDate + ", version=" + version + ", state=" + state + ", stateDate="
				+ stateDate + ", ownMemberId=" + ownMemberId + ", artworksNum=" + artworksNum + ", seq=" + seq
				+ ", favoriteFlag=" + favoriteFlag + ", lastUpdate=" + lastUpdate + ", galleryId=" + galleryId
				+ ", priviewCode=" + priviewCode + ", priviewEndDate=" + priviewEndDate + ", viewPermission="
				+ viewPermission + ", viewCode=" + viewCode + ", viewUrl=" + viewUrl + ", viewUrlQr=" + viewUrlQr + "]";
	}

}