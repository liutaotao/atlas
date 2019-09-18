package com.yetoop.cloud.atlas.domain;

import java.util.Date;

import com.yetoop.cloud.atlas.busi.IndexBusi;
import com.yetoop.cloud.atlas.common.DateUtil;
import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.StateEnum;
import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.TimeTypeEnum;

public class AsIndexMemberWorks implements IndexBusi {
	@Override
	public TimeTypeEnum getNextTimeTypeEnum() {
		return TimeTypeEnum.getByType(this.timeType).getNextType();
	}

	public void create(AsIndexMemberWorks asIndexMemberWorks, TimeTypeEnum timeTypeEnum) {
		Date now = new Date();
		this.createDate = now;
		this.version = now;
		this.state = StateEnum.NORMAL.getState();
		this.stateDate = now;
		this.worksId = asIndexMemberWorks.getWorksId();
		this.memberId = asIndexMemberWorks.getMemberId();

		this.timeType = timeTypeEnum.getType();
		if (timeTypeEnum.equals(TimeTypeEnum.ALL)) {
			this.beginDate = null;
			this.endDate = null;
			this.timeId = 0;
		} else {
			this.timeId = asIndexMemberWorks.getTimeId() / 100;
			if (timeTypeEnum.equals(TimeTypeEnum.DAY)) {
				this.beginDate = DateUtil.toDate(this.timeId.toString(), "yyyyMMdd");
				this.endDate = DateUtil.addDays(this.beginDate, 1);
			}
			if (timeTypeEnum.equals(TimeTypeEnum.MONTH)) {
				this.beginDate = DateUtil.toDate(this.timeId.toString(), "yyyyMM");
				this.endDate = DateUtil.addMonths(this.beginDate, 1);
			}
			if (timeTypeEnum.equals(TimeTypeEnum.YEAR)) {
				this.beginDate = DateUtil.toDate(this.timeId.toString(), "yyyy");				
				this.endDate = DateUtil.addYears(this.beginDate, 1);
			}

		}
		this.viewCount = asIndexMemberWorks.getViewCount();
		this.totalDuration = asIndexMemberWorks.getTotalDuration();
		this.leaveCount = asIndexMemberWorks.getLeaveCount();
		this.backCount = asIndexMemberWorks.getBackCount();
		this.shareCount = asIndexMemberWorks.getShareCount();
		this.artworkNum = asIndexMemberWorks.getArtworkNum();
		this.viewArtworkCount = asIndexMemberWorks.getViewArtworkCount();
		this.viewRate = asIndexMemberWorks.getViewRate();
		this.averagDuration = asIndexMemberWorks.getViewRate();

		this.likeRate = asIndexMemberWorks.getLikeRate();
	}

	public void append(AsIndexMemberWorks asIndexMemberWorks) {
		Date now = new Date();
		this.version = now;
		this.viewCount = this.viewCount + 1;
		this.totalDuration = this.totalDuration + asIndexMemberWorks.getTotalDuration();
		this.leaveCount = this.leaveCount + asIndexMemberWorks.getLeaveCount();
		this.backCount = this.backCount + asIndexMemberWorks.getBackCount();
		this.shareCount = this.shareCount + asIndexMemberWorks.getShareCount();
		this.shareCount = this.shareCount + asIndexMemberWorks.getShareCount();

		if (this.artworkNum == 0) {
			this.viewRate = 0;
		} else {
			this.viewRate = this.viewArtworkCount * 1000 / artworkNum;
		}
		this.averagDuration = this.totalDuration / this.viewCount;

		this.likeRate = 0;
	}

	public void create(AsTrack asTrack, AsWorks asWorks) {
		Date now = new Date();
		this.createDate = now;
		this.version = now;
		this.state = StateEnum.NORMAL.getState();
		this.stateDate = now;
		this.worksId = asTrack.getWorksId();
		this.memberId = asTrack.getMemberId();
		this.beginDate = DateUtil.toDate(asTrack.getTimeId().toString(), "yyyyMMddHH");
		this.endDate = DateUtil.addHours(this.beginDate, 1);
		this.timeType = asTrack.getTimeType();
		this.timeId = asTrack.getTimeId();
		this.viewCount = 1;
		this.totalDuration = asTrack.getDurationTime();
		this.leaveCount = asTrack.getLeaveCount();
		this.backCount = asTrack.getBackCount();
		this.shareCount = asTrack.getShareCount();
		this.artworkNum = asWorks.getArtworksNum();
		// TODO
		this.viewArtworkCount = asWorks.getArtworksNum();
		if (this.artworkNum == 0) {
			this.viewRate = 0;
		} else {
			this.viewRate = this.viewArtworkCount * 1000 / artworkNum;
		}
		this.averagDuration = this.totalDuration / this.viewCount;

		this.likeRate = 0;

	}

	public void append(AsTrack asTrack, AsWorks asWorks) {
		Date now = new Date();
		this.version = now;
		this.viewCount = this.viewCount + 1;
		this.totalDuration = this.totalDuration + asTrack.getDurationTime();
		this.leaveCount = this.leaveCount + asTrack.getLeaveCount();
		this.backCount = this.backCount + asTrack.getBackCount();
		this.shareCount = this.shareCount + asTrack.getShareCount();

		if (this.artworkNum == 0) {
			this.viewRate = 0;
		} else {
			this.viewRate = this.viewArtworkCount * 1000 / artworkNum;
		}
		this.averagDuration = this.totalDuration / this.viewCount;

		this.likeRate = 0;
	}

	private Integer id;

	private Date createDate;

	private Date version;

	private String state;

	private Date stateDate;

	private Integer worksId;

	private Integer memberId;

	private Date beginDate;

	private Date endDate;

	private String timeType;

	private Integer timeId;

	private Integer viewCount;

	private Integer viewArtworkCount;

	private Integer totalDuration;

	private Integer averagDuration;

	private Integer leaveCount;

	private Integer backCount;

	private Integer shareCount;

	private Integer likeRate;

	private Integer viewRate;

	private Integer artworkNum;

	private EsMember member;

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

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Integer getViewArtworkCount() {
		return viewArtworkCount;
	}

	public void setViewArtworkCount(Integer viewArtworkCount) {
		this.viewArtworkCount = viewArtworkCount;
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

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	public Integer getLikeRate() {
		return likeRate;
	}

	public void setLikeRate(Integer likeRate) {
		this.likeRate = likeRate;
	}

	public Integer getViewRate() {
		return viewRate;
	}

	public void setViewRate(Integer viewRate) {
		this.viewRate = viewRate;
	}

	public EsMember getMember() {
		return member;
	}

	public void setMember(EsMember member) {
		this.member = member;
	}

	public Integer getArtworkNum() {
		return artworkNum;
	}

	public void setArtworkNum(Integer artworkNum) {
		this.artworkNum = artworkNum;
	}

	@Override
	public String toString() {
		return "AsIndexMemberWorks [id=" + id + ", createDate=" + createDate + ", version=" + version + ", state="
				+ state + ", stateDate=" + stateDate + ", worksId=" + worksId + ", memberId=" + memberId
				+ ", beginDate=" + beginDate + ", endDate=" + endDate + ", timeType=" + timeType + ", timeId=" + timeId
				+ ", viewCount=" + viewCount + ", viewArtworkCount=" + viewArtworkCount + ", totalDuration="
				+ totalDuration + ", averagDuration=" + averagDuration + ", leaveCount=" + leaveCount + ", backCount="
				+ backCount + ", shareCount=" + shareCount + ", likeRate=" + likeRate + ", viewRate=" + viewRate + "]";
	}

	@Override
	public String getIndexTableName() {
		return "as_index_member_works";
	}

	@Override
	public Integer getIndexId() {
		return this.id;
	}
}