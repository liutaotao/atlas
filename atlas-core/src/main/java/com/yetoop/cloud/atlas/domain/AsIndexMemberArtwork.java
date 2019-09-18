package com.yetoop.cloud.atlas.domain;

import java.util.Date;

import com.yetoop.cloud.atlas.busi.IndexBusi;
import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.TimeTypeEnum;

public class AsIndexMemberArtwork implements IndexBusi {
	@Override
	public TimeTypeEnum getNextTimeTypeEnum() {
		return TimeTypeEnum.getByType(this.timeType).getNextType();
	}

	private Integer id;

	private Date createDate;

	private Date version;

	private String state;

	private Date stateDate;

	private Integer worksId;

	private Integer artworkId;

	private Integer memberId;

	private Date beginDate;

	private Date endDate;

	private String timeType;

	private Integer timeId;

	private Integer viewCount;

	private Integer totalDuration;

	private Integer averagDuration;

	private Integer leaveCount;

	private Integer backCount;

	private Integer shareCount;

	private Integer likeRate;

	private EsMember member;

	private AsArtwork artwork;

	public EsMember getMember() {
		return member;
	}

	public void setMember(EsMember member) {
		this.member = member;
	}

	public AsArtwork getArtwork() {
		return artwork;
	}

	public void setArtwork(AsArtwork artwork) {
		this.artwork = artwork;
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

	public Integer getArtworkId() {
		return artworkId;
	}

	public void setArtworkId(Integer artworkId) {
		this.artworkId = artworkId;
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

	@Override
	public String getIndexTableName() {
		return "as_index_member_artwork";
	}

	@Override
	public Integer getIndexId() {
		return this.id;
	}
}