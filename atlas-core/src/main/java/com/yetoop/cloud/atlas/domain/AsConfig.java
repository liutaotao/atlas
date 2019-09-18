package com.yetoop.cloud.atlas.domain;

import java.util.Date;

public class AsConfig {
	private Integer id;

	private String name;

	private String code;

	private Date createDate;

	private Date version;

	private String state;

	private Date stateDate;

	private String configValue;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	@Override
	public String toString() {
		return "AsConfig [id=" + id + ", name=" + name + ", code=" + code + ", createDate=" + createDate + ", version="
				+ version + ", state=" + state + ", stateDate=" + stateDate + ", configValue=" + configValue + "]";
	}
}