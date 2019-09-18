package com.yetoop.cloud.atlas.domain;

import java.util.Date;

import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.StateEnum;

public class AsFeedback {
	
	public void create(String title,String detail,Integer memberId){
		this.title = title;
		this.detail = detail;
		this.memberId = memberId;
		this.state = StateEnum.NORMAL.getState();
		Date now = new Date();
		this.createDate = now;
		this.version = now;
		this.stateDate = now;
	}
	
    private Integer id;

    private String title;

    private Date createDate;

    private Date version;

    private String state;

    private Date stateDate;

    private Integer memberId;

    private String detail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}