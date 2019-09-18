package com.yetoop.cloud.atlas.domain;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.yetoop.cloud.atlas.busi.IndexBusi;

public class AsIndexLog {

	public void logIndex(IndexBusi from, IndexBusi to) {
		Date now = new Date();
		this.createDate = now;
		this.version = now;
		this.fromName = from.getIndexTableName();
		this.fromId = from.getIndexId();
		this.fromData = JSON.toJSONString(from);
		this.targetData = JSON.toJSONString(to);
		this.targetId = to.getIndexId();
		this.targetName = to.getIndexTableName();

		this.indexType = from.getTimeType()  ;
		 //+ "." + from.getTimeType() + "-" + to.getIndexTableName() + "."				+ to.getTimeType();
	}

	private Integer id;

	private Date createDate;

	private Date version;

	private String indexType;

	private String fromName;

	private Integer fromId;

	private String targetName;

	private Integer targetId;

	private String indexState;

	private Date indexDate;

	private String fromData;

	private String targetData;

	private String indexInfo;

	private String extInfo;

	public String getFromData() {
		return fromData;
	}

	public void setFromData(String fromData) {
		this.fromData = fromData;
	}

	public String getTargetData() {
		return targetData;
	}

	public void setTargetData(String targetData) {
		this.targetData = targetData;
	}

	public String getIndexInfo() {
		return indexInfo;
	}

	public void setIndexInfo(String indexInfo) {
		this.indexInfo = indexInfo;
	}

	public String getExtInfo() {
		return extInfo;
	}

	public void setExtInfo(String extInfo) {
		this.extInfo = extInfo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getIndexType() {
		return indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public Integer getFromId() {
		return fromId;
	}

	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public String getIndexState() {
		return indexState;
	}

	public void setIndexState(String indexState) {
		this.indexState = indexState;
	}

	public Date getIndexDate() {
		return indexDate;
	}

	public void setIndexDate(Date indexDate) {
		this.indexDate = indexDate;
	}

	@Override
	public String toString() {
		return "AsIndexLog [id=" + id + ", createDate=" + createDate + ", version=" + version + ", indexType="
				+ indexType + ", fromName=" + fromName + ", fromId=" + fromId + ", targetName=" + targetName
				+ ", targetId=" + targetId + ", indexState=" + indexState + ", indexDate=" + indexDate + "]";
	}
}