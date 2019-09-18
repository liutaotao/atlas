package com.yetoop.cloud.atlas.domain;

import java.util.Date;

public class AsGallary {
	
	public void updateGallaryName(String gallaryName){
		Date now = new Date();
		this.name = gallaryName;
		this.version = now;
	}
	
	private Integer id;

	private String name;

	private String logoUrl;

	private String intro;

	private String domainUrl;

	private String backImgUrl;

	private Date createDate;

	private Date version;

	private String state;

	private Date stateDate;

	private Integer ownMemberId;

	private Integer adminThemeId;

	private Integer worksNum;

	private Integer artworksNum;

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

	public String getDomainUrl() {
		return domainUrl;
	}

	public void setDomainUrl(String domainUrl) {
		this.domainUrl = domainUrl;
	}

	public String getBackImgUrl() {
		return backImgUrl;
	}

	public void setBackImgUrl(String backImgUrl) {
		this.backImgUrl = backImgUrl;
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

	public Integer getAdminThemeId() {
		return adminThemeId;
	}

	public void setAdminThemeId(Integer adminThemeId) {
		this.adminThemeId = adminThemeId;
	}

	public Integer getWorksNum() {
		return worksNum;
	}

	public void setWorksNum(Integer worksNum) {
		this.worksNum = worksNum;
	}

	public Integer getArtworksNum() {
		return artworksNum;
	}

	public void setArtworksNum(Integer artworksNum) {
		this.artworksNum = artworksNum;
	}

	@Override
	public String toString() {
		return "AsGallary [id=" + id + ", name=" + name + ", logoUrl=" + logoUrl + ", intro=" + intro + ", domainUrl="
				+ domainUrl + ", backImgUrl=" + backImgUrl + ", createDate=" + createDate + ", version=" + version
				+ ", state=" + state + ", stateDate=" + stateDate + ", ownMemberId=" + ownMemberId + ", adminThemeId="
				+ adminThemeId + ", worksNum=" + worksNum + ", artworksNum=" + artworksNum + "]";
	}
}