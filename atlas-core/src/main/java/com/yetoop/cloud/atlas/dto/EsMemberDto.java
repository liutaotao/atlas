package com.yetoop.cloud.atlas.dto;

import com.yetoop.cloud.atlas.common.StringUtil;
import com.yetoop.cloud.atlas.domain.EsMember;

public class EsMemberDto {

	public void create(EsMember member) {
		this.memberId = member.getMemberId();
		this.face = member.getFace();
		this.nickname = member.getNickname();
		this.mobile = member.getMobile();
		this.disabled = member.getDisabled();
		this.encryptKey = member.getEncryptKey();
		ifBindWX = false;
		if (!StringUtil.isNullString(member.getWxOpenid())) {
			ifBindWX = true;
		}
	}

	private Integer memberId;

	private String face;

	private String nickname;

	private String mobile;

	private Short disabled;

	private boolean ifBindWX;

	private String encryptKey;

	private String galleryName;

	public boolean isIfBindWX() {
		return ifBindWX;
	}

	public void setIfBindWX(boolean ifBindWX) {
		this.ifBindWX = ifBindWX;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Short getDisabled() {
		return disabled;
	}

	public void setDisabled(Short disabled) {
		this.disabled = disabled;
	}

	public String getEncryptKey() {
		return encryptKey;
	}

	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}

	public String getGalleryName() {
		return galleryName;
	}

	public void setGalleryName(String galleryName) {
		this.galleryName = galleryName;
	}

	@Override
	public String toString() {
		return "EsMemberDto [memberId=" + memberId + ", face=" + face + ", nickname=" + nickname + ", mobile=" + mobile
				+ ", disabled=" + disabled + ", ifBindWX=" + ifBindWX + ", encryptKey=" + encryptKey + ", galleryName="
				+ galleryName + "]";
	}

}
