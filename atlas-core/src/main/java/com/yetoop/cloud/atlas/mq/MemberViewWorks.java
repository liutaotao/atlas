package com.yetoop.cloud.atlas.mq;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.yetoop.cloud.atlas.dto.AsWorksDto;

public class MemberViewWorks {

	@Override
	public String toString() {
		return "MemberViewWorks [typeCode=" + typeCode + ", memberId=" + memberId + ", viewCode=" + viewCode
				+ ", worksId=" + worksId + ", beginDate=" + beginDate + ", endDate=" + endDate + ", durationTime="
				+ durationTime + ", durationCount=" + durationCount + ", ipAddr=" + ipAddr + ", macAddr=" + macAddr
				+ ", deviceType=" + deviceType + ", deviceBand=" + deviceBand + ", deviceId=" + deviceId
				+ ", sessionId=" + sessionId + ", transactionId=" + transactionId + "]";
	}

	public void createBeginView(Integer memberId, String viewCode, AsWorksDto worksDto, HttpSession session) {
		this.typeCode = TypeCodeEnum.BEGIN.toString();
		this.memberId = memberId;
		this.worksId = worksDto.getId();
		this.viewCode = viewCode;
		Date now = new Date();
		this.beginDate = now;
		this.endDate = now;
		this.durationCount = 0;
		this.durationTime = 0;
		this.sessionId = session.getId();
		this.deviceBand = (String) session.getAttribute("os");
		this.deviceType = (String) session.getAttribute("model");
		this.ipAddr = (String) session.getAttribute("cip");
		this.transactionId = worksDto.getTransactionId();
	}

	public void createLeaveView(Integer memberId, String viewCode, String transactionId, HttpSession session) {
		this.typeCode = TypeCodeEnum.LEAVE.toString();
		this.memberId = memberId;
		this.viewCode = viewCode;
		Date now = new Date();
		this.endDate = now;

		this.sessionId = session.getId();
		this.deviceBand = (String) session.getAttribute("os");
		this.deviceType = (String) session.getAttribute("model");
		this.ipAddr = (String) session.getAttribute("cip");
		this.transactionId = transactionId;
	}

	public void createReactiveView(Integer memberId, String viewCode, String transactionId, HttpSession session) {
		this.typeCode = TypeCodeEnum.BACK.toString();
		this.memberId = memberId;
		this.viewCode = viewCode;

		this.sessionId = session.getId();
		this.deviceBand = (String) session.getAttribute("os");
		this.deviceType = (String) session.getAttribute("model");
		this.ipAddr = (String) session.getAttribute("cip");
		this.transactionId = transactionId;
	}

	public void createEndView(Integer memberId, String viewCode, String transactionId, HttpSession session) {
		this.typeCode = TypeCodeEnum.END.toString();
		this.memberId = memberId;
		this.viewCode = viewCode;
		Date now = new Date();
		this.endDate = now;

		this.sessionId = session.getId();
		this.deviceBand = (String) session.getAttribute("os");
		this.deviceType = (String) session.getAttribute("model");
		this.ipAddr = (String) session.getAttribute("cip");
		this.transactionId = transactionId;
	}

	public void createContinueView(Integer memberId, String viewCode, Integer count, Integer viewSeconds,
			String transactionId, HttpSession session) {
		this.typeCode = TypeCodeEnum.CONTINUE.toString();
		this.memberId = memberId;
		this.viewCode = viewCode;
		this.durationCount = count;
		this.durationTime = viewSeconds;
		Date now = new Date();
		this.endDate = now;

		this.sessionId = session.getId();
		this.deviceBand = (String) session.getAttribute("os");
		this.deviceType = (String) session.getAttribute("model");
		this.ipAddr = (String) session.getAttribute("cip");
		this.transactionId = transactionId;
	}

	public void createShareView(Integer memberId, String viewCode, String transactionId, HttpSession session) {
		this.typeCode = TypeCodeEnum.SHARE.toString();
		this.memberId = memberId;
		this.viewCode = viewCode;
		this.sessionId = session.getId();
		this.deviceBand = (String) session.getAttribute("os");
		this.deviceType = (String) session.getAttribute("model");
		this.ipAddr = (String) session.getAttribute("cip");
		this.transactionId = transactionId;
	}

	public static enum TypeCodeEnum {
		BEGIN, END, LEAVE, BACK, SHARE, CONTINUE;
	}

	private String typeCode;

	private Integer memberId;

	private String viewCode;

	private Integer worksId;

	private Date beginDate;

	private Date endDate;

	private Integer durationTime;

	private Integer durationCount;

	private String ipAddr;

	private String macAddr;

	private String deviceType;

	private String deviceBand;

	private String deviceId;

	private String sessionId;

	private String transactionId;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getViewCode() {
		return viewCode;
	}

	public void setViewCode(String viewCode) {
		this.viewCode = viewCode;
	}

	public Integer getWorksId() {
		return worksId;
	}

	public void setWorksId(Integer worksId) {
		this.worksId = worksId;
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

	public Integer getDurationCount() {
		return durationCount;
	}

	public void setDurationCount(Integer durationCount) {
		this.durationCount = durationCount;
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
}
