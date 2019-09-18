package com.yetoop.cloud.atlas.domain;

import java.util.Date;

import com.yetoop.cloud.atlas.busi.IndexBusi;
import com.yetoop.cloud.atlas.common.DateUtil;
import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.StateEnum;
import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.TimeTypeEnum;
import com.yetoop.cloud.atlas.mq.MemberViewWorks;

public class AsTrack implements IndexBusi {
	
	public boolean valid() {
		return this.worksId != null;
	}

	public void create(MemberViewWorks memberViewWorks) {
		Date now = new Date();
		this.createDate = now;
		this.version = now;
		this.state = StateEnum.NORMAL.getState();
		this.stateDate = now;
		this.worksId = memberViewWorks.getWorksId();
		this.memberId = memberViewWorks.getMemberId();
		this.ipAddr = memberViewWorks.getIpAddr();
		this.macAddr = memberViewWorks.getMacAddr();
		this.deviceType = memberViewWorks.getDeviceType();
		this.deviceBand = memberViewWorks.getDeviceBand();
		this.deviceId = memberViewWorks.getDeviceId();
		this.sessionId = memberViewWorks.getSessionId();
		this.transactionId = memberViewWorks.getTransactionId();
		this.beginDate = memberViewWorks.getBeginDate();
		this.endDate = memberViewWorks.getEndDate();
		this.durationTime = memberViewWorks.getDurationTime();
		this.backCount = 0;
		this.leaveCount = 0;
		this.shareCount = 0;
	}

	public void append(MemberViewWorks memberViewWorks) {
		// 目前 队列，消息顺序处理，不考虑乱序情况
		// BEGIN, END, LEAVE, BACK, SHARE, CONTINUE;

		Date now = new Date();
		this.version = now;

		if (MemberViewWorks.TypeCodeEnum.BACK.toString().equals(memberViewWorks.getTypeCode())) {
			this.backCount = this.backCount++;
		}

		if (MemberViewWorks.TypeCodeEnum.END.toString().equals(memberViewWorks.getTypeCode())) {
			this.endDate = memberViewWorks.getEndDate();
			this.durationTime = memberViewWorks.getDurationTime();
		}

		if (MemberViewWorks.TypeCodeEnum.LEAVE.toString().equals(memberViewWorks.getTypeCode())) {
			this.leaveCount = this.leaveCount++;
		}

		if (MemberViewWorks.TypeCodeEnum.SHARE.toString().equals(memberViewWorks.getTypeCode())) {
			this.shareCount = this.shareCount++;
		}

		if (MemberViewWorks.TypeCodeEnum.CONTINUE.toString().equals(memberViewWorks.getTypeCode())) {
			this.endDate = memberViewWorks.getEndDate();
			this.durationTime = memberViewWorks.getDurationTime();
		}

	}

	@Override
	public String toString() {
		return "AsTrack [id=" + id + ", createDate=" + createDate + ", version=" + version + ", state=" + state
				+ ", stateDate=" + stateDate + ", worksId=" + worksId + ", memberId=" + memberId + ", ipAddr=" + ipAddr
				+ ", macAddr=" + macAddr + ", deviceType=" + deviceType + ", deviceBand=" + deviceBand + ", deviceId="
				+ deviceId + ", sessionId=" + sessionId + ", transactionId=" + transactionId + ", extInfo=" + extInfo
				+ ", beginDate=" + beginDate + ", endDate=" + endDate + ", durationTime=" + durationTime
				+ ", leaveCount=" + leaveCount + ", backCount=" + backCount + ", shareCount=" + shareCount + "]";
	}

	private Integer id;

	private Date createDate;

	private Date version;

	private String state;

	private Date stateDate;

	private Integer worksId;

	private Integer memberId;

	private String ipAddr;

	private String macAddr;

	private String deviceType;

	private String deviceBand;

	private String deviceId;

	private String sessionId;

	private String transactionId;

	private String extInfo;

	private Date beginDate;

	private Date endDate;

	private Integer durationTime;

	private Integer leaveCount;

	private Integer backCount;

	private Integer shareCount;

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

	public Integer getWorksId() {
		return worksId;
	}

	public void setWorksId(Integer worksId) {
		this.worksId = worksId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getMacAddr() {
		return macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceBand() {
		return deviceBand;
	}

	public void setDeviceBand(String deviceBand) {
		this.deviceBand = deviceBand;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getExtInfo() {
		return extInfo;
	}

	public void setExtInfo(String extInfo) {
		this.extInfo = extInfo;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(Integer durationTime) {
		this.durationTime = durationTime;
	}

	public Integer getLeaveCount() {
		return leaveCount;
	}

	public void setLeaveCount(Integer leaveCount) {
		this.leaveCount = leaveCount;
	}

	public Integer getBackCount() {
		return backCount;
	}

	public void setBackCount(Integer backCount) {
		this.backCount = backCount;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	@Override
	public String getTimeType() {
		return TimeTypeEnum.HOUR.getType();
	}

	@Override
	public Integer getTimeId() {
		return Integer.valueOf(DateUtil.toString(this.beginDate, "yyyyMMddHH"));
	}

	@Override
	public String getIndexTableName() {
		return "as_track";
	}

	@Override
	public Integer getIndexId() {
		return this.id;
	}

	@Override
	public TimeTypeEnum getNextTimeTypeEnum() {
		return null;
	}
}