package com.yetoop.cloud.atlas.dto;

import java.util.Date;
import java.util.List;

import com.yetoop.cloud.atlas.common.StringUtil;
import com.yetoop.cloud.atlas.domain.AsWorks;

public class AsWorksDto {

	public void create(AsWorks work) {
		this.id = work.getId();
		this.name = work.getName();
		this.state = work.getState();
		this.ownMemberId = work.getOwnMemberId();
		this.artworksNum = work.getArtworksNum();
		this.seq = work.getSeq();
		this.favoriteFlag = work.getFavoriteFlag();
		this.lastUpdate = work.getLastUpdate();
		this.galleryId = work.getGalleryId();
		this.galleryName = work.getGallery() != null ? work.getGallery().getName() : "";
		this.priviewCode = work.getPriviewCode();
		this.priviewEndDate = work.getPriviewEndDate();
		this.viewCode = work.getViewCode();
		this.viewPermission = work.getViewPermission();
		this.transactionId = StringUtil.randomString(16);
	}

	private Integer id;

	private String name;

	private String state;

	private Integer ownMemberId;

	private Integer artworksNum;

	private Integer seq;

	private String favoriteFlag;

	private Date lastUpdate;

	private Integer galleryId;

	private String galleryName;

	private String priviewCode;

	private Date priviewEndDate;

	private String viewPermission;

	private String viewCode;

	private String transactionId;
	
	private List<AsArtworkDto> artworkDtoList;

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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getGalleryName() {
		return galleryName;
	}

	public void setGalleryName(String galleryName) {
		this.galleryName = galleryName;
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

	public List<AsArtworkDto> getArtworkDtoList() {
		return artworkDtoList;
	}

	public void setArtworkDtoList(List<AsArtworkDto> artworkDtoList) {
		this.artworkDtoList = artworkDtoList;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public String toString() {
		return "AsWorksDto [id=" + id + ", name=" + name + ", state=" + state + ", ownMemberId=" + ownMemberId
				+ ", artworksNum=" + artworksNum + ", seq=" + seq + ", favoriteFlag=" + favoriteFlag + ", lastUpdate="
				+ lastUpdate + ", galleryId=" + galleryId + ", galleryName=" + galleryName + ", priviewCode="
				+ priviewCode + ", priviewEndDate=" + priviewEndDate + ", viewPermission=" + viewPermission
				+ ", viewCode=" + viewCode + ", artworkDtoList=" + artworkDtoList + "]";
	}

}
