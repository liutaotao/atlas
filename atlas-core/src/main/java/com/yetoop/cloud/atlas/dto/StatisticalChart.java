package com.yetoop.cloud.atlas.dto;

import java.util.Date;

import com.yetoop.cloud.atlas.domain.AsIndexArtwork;
import com.yetoop.cloud.atlas.domain.AsIndexMemberWorks;
import com.yetoop.cloud.atlas.domain.AsIndexWorks;

public class StatisticalChart {
	
	public void createByIndexArtwork(AsIndexArtwork indexArtwork, String value) {
		this.timeId = indexArtwork.getTimeId();
		this.timeType = indexArtwork.getTimeType();
		this.startTime = indexArtwork.getStateDate();
		this.endTime = indexArtwork.getEndDate();
		this.value = value;
	}
	
	public void createByIndexMemberWorks(AsIndexMemberWorks indexMemberWorks, String value) {
		this.timeId = indexMemberWorks.getTimeId();
		this.timeType = indexMemberWorks.getTimeType();
		this.startTime = indexMemberWorks.getStateDate();
		this.endTime = indexMemberWorks.getEndDate();
		this.value = value;
	}

	public void create(AsIndexWorks indexWorks, String value) {
		this.timeId = indexWorks.getTimeId();
		this.timeType = indexWorks.getTimeType();
		this.startTime = indexWorks.getStateDate();
		this.endTime = indexWorks.getEndDate();
		this.value = value;
	}

	private Integer timeId;

	private String timeType;

	private Date startTime;

	private Date endTime;

	private String value;

	public Integer getTimeId() {
		return timeId;
	}

	public void setTimeId(Integer timeId) {
		this.timeId = timeId;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
