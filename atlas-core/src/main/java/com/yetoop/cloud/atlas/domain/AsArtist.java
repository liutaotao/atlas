package com.yetoop.cloud.atlas.domain;

import java.util.Date;

public class AsArtist {
    private Integer id;

    private String name;

    private String pinyin;

    private String gender;

    private String intro;

    private Date createDate;

    private Date version;

    private String state;

    private Date stateDate;

    private String faceUrl;

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

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
	}

	@Override
	public String toString() {
		return "AsArtist [id=" + id + ", name=" + name + ", pinyin=" + pinyin + ", gender=" + gender + ", intro="
				+ intro + ", createDate=" + createDate + ", version=" + version + ", state=" + state + ", stateDate="
				+ stateDate + ", faceUrl=" + faceUrl + "]";
	}
}