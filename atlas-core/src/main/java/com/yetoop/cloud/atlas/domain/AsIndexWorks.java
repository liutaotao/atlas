package com.yetoop.cloud.atlas.domain;

import java.util.Date;

import com.yetoop.cloud.atlas.busi.IndexBusi;
import com.yetoop.cloud.atlas.common.DateUtil;
import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.StateEnum;
import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.TimeTypeEnum;

public class AsIndexWorks implements IndexBusi {
	@Override
	public TimeTypeEnum getNextTimeTypeEnum() {
		return TimeTypeEnum.getByType(this.timeType).getNextType();
	}

	public void create(AsIndexWorks asIndexWorks, TimeTypeEnum timeTypeEnum) {

		Date now = new Date();
		this.createDate = now;
		this.version = now;
		this.state = StateEnum.NORMAL.getState();
		this.stateDate = now;
		this.worksId = asIndexWorks.getWorksId();

		this.timeType = timeTypeEnum.getType();
		if (timeTypeEnum.equals(TimeTypeEnum.ALL)) {
			this.beginDate = null;
			this.endDate = null;
			this.timeId = 0;
		} else {
			this.timeId = asIndexWorks.getTimeId() / 100;
			if (timeTypeEnum.equals(TimeTypeEnum.DAY)) {
				this.beginDate = DateUtil.toDate(this.timeId.toString(), "yyyyMMdd");
				this.endDate = DateUtil.addDays(this.beginDate, 1);
			}
			if (timeTypeEnum.equals(TimeTypeEnum.MONTH)) {
				this.beginDate = DateUtil.toDate(this.timeId.toString(), "yyyyMM");
				this.endDate = DateUtil.addMonths(this.beginDate, 1);
			}
			if (timeTypeEnum.equals(TimeTypeEnum.YEAR)) {
				this.beginDate = DateUtil.toDate(this.timeId.toString(), "yyyyMM");
				this.endDate = DateUtil.addYears(this.beginDate, 1);
			}

		}

		this.pageView = asIndexWorks.getPageView();
		this.totalDuration = asIndexWorks.getTotalDuration();

		this.leaveCount = asIndexWorks.getLeaveCount();
		this.backCount = asIndexWorks.getBackCount();
		this.artworkNum = asIndexWorks.getArtworkNum();
		this.shareCount = asIndexWorks.getShareCount();

		this.uniqueVisitor = asIndexWorks.getUniqueVisitor();
		this.averagDuration = asIndexWorks.getAveragDuration();
		this.viewRate = asIndexWorks.getViewRate();

	}

	public void append(AsIndexWorks asIndexWorks) {
		// TODO
		Date now = new Date();
		this.version = now;
		this.stateDate = now;

		this.pageView = this.pageView + asIndexWorks.getPageView();
		this.totalDuration = this.totalDuration + asIndexWorks.getTotalDuration();

		this.leaveCount = this.leaveCount + asIndexWorks.getLeaveCount();
		this.backCount = this.backCount + asIndexWorks.getBackCount();
		this.artworkNum = this.artworkNum + asIndexWorks.getArtworkNum();
		this.shareCount = this.shareCount + asIndexWorks.getShareCount();

		// TODO
		this.uniqueVisitor = asIndexWorks.getUniqueVisitor();
		this.averagDuration = asIndexWorks.getAveragDuration();
		this.viewRate = asIndexWorks.getViewRate();
	}

	public void create(AsIndexMemberWorks asIndexMemberWorks) {
		Date now = new Date();
		this.createDate = now;
		this.version = now;
		this.state = StateEnum.NORMAL.getState();
		this.stateDate = now;
		this.worksId = asIndexMemberWorks.getWorksId();
		this.beginDate = asIndexMemberWorks.getBeginDate();
		this.endDate = asIndexMemberWorks.getEndDate();
		this.timeId = asIndexMemberWorks.getTimeId();
		this.timeType = asIndexMemberWorks.getTimeType();
		this.pageView = asIndexMemberWorks.getViewCount();
		this.totalDuration = asIndexMemberWorks.getTotalDuration();

		this.leaveCount = asIndexMemberWorks.getLeaveCount();
		this.backCount = asIndexMemberWorks.getBackCount();
		this.artworkNum = asIndexMemberWorks.getArtworkNum();
		this.shareCount = asIndexMemberWorks.getShareCount();

		// TODO
		this.uniqueVisitor = 1;
		this.averagDuration = 0;
		this.viewRate = 0;

	}

	public void append(AsIndexMemberWorks asIndexMemberWorks) {
		Date now = new Date();
		this.version = now;
		this.pageView = this.pageView + asIndexMemberWorks.getViewCount();
		this.totalDuration = this.totalDuration + asIndexMemberWorks.getTotalDuration();
		this.leaveCount = this.leaveCount + asIndexMemberWorks.getLeaveCount();
		this.backCount = this.backCount + asIndexMemberWorks.getBackCount();
		this.artworkNum = this.artworkNum + asIndexMemberWorks.getArtworkNum();
		this.shareCount = this.shareCount + asIndexMemberWorks.getShareCount();
	}

	@Override
	public String toString() {
		return "AsIndexWorks [id=" + id + ", createDate=" + createDate + ", version=" + version + ", state=" + state
				+ ", stateDate=" + stateDate + ", worksId=" + worksId + ", beginDate=" + beginDate + ", endDate="
				+ endDate + ", timeType=" + timeType + ", timeId=" + timeId + ", pageView=" + pageView
				+ ", uniqueVisitor=" + uniqueVisitor + ", totalDuration=" + totalDuration + ", averagDuration="
				+ averagDuration + ", leaveCount=" + leaveCount + ", backCount=" + backCount + ", artworkNum="
				+ artworkNum + ", shareCount=" + shareCount + ", viewRate=" + viewRate + "]";
	}

	private Integer id;

	private Date createDate;

	private Date version;

	private String state;

	private Date stateDate;

	private Integer worksId;

	private Date beginDate;

	private Date endDate;

	private String timeType;

	private Integer timeId;

	private Integer pageView;

	private Integer uniqueVisitor;

	private Integer totalDuration;

	private Integer averagDuration;

	private Integer leaveCount;

	private Integer backCount;

	private Integer artworkNum;

	private Integer shareCount;

	private Integer viewRate;

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

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public Integer getTimeId() {
		return timeId;
	}

	public void setTimeId(Integer timeId) {
		this.timeId = timeId;
	}

	public Integer getPageView() {
		return pageView;
	}

	public void setPageView(Integer pageView) {
		this.pageView = pageView;
	}

	public Integer getUniqueVisitor() {
		return uniqueVisitor;
	}

	public void setUniqueVisitor(Integer uniqueVisitor) {
		this.uniqueVisitor = uniqueVisitor;
	}

	public Integer getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(Integer totalDuration) {
		this.totalDuration = totalDuration;
	}

	public Integer getAveragDuration() {
		return averagDuration;
	}

	public void setAveragDuration(Integer averagDuration) {
		this.averagDuration = averagDuration;
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

	public Integer getArtworkNum() {
		return artworkNum;
	}

	public void setArtworkNum(Integer artworkNum) {
		this.artworkNum = artworkNum;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	public Integer getViewRate() {
		return viewRate;
	}

	public void setViewRate(Integer viewRate) {
		this.viewRate = viewRate;
	}

	@Override
	public String getIndexTableName() {
		return "as_index_artwork";
	}

	@Override
	public Integer getIndexId() {
		return this.id;
	}
}