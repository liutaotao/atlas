package com.yetoop.cloud.atlas.dto;

import java.util.Date;

import com.yetoop.cloud.atlas.common.CurrencyUtil;
import com.yetoop.cloud.atlas.common.DateUtil;
import com.yetoop.cloud.atlas.common.StringUtil;
import com.yetoop.cloud.atlas.domain.AsIndexMemberWorks;
import com.yetoop.cloud.atlas.domain.EsMember;

public class AsIndexMemberWorksDto {

	public void create(AsIndexMemberWorks indexMemberWorks) {
		this.id = indexMemberWorks.getId();
		this.endDate = indexMemberWorks.getEndDate();
		this.worksId = indexMemberWorks.getWorksId();
		this.memberId = indexMemberWorks.getMemberId();
		this.viewCount = indexMemberWorks.getViewCount();
		this.viewArtworkCount = indexMemberWorks.getViewArtworkCount();
		this.totalDuration = indexMemberWorks.getTotalDuration();
		this.averagDuration = indexMemberWorks.getAveragDuration();
		this.leaveCount = indexMemberWorks.getLeaveCount();
		this.backCount = indexMemberWorks.getBackCount();
		this.shareCount = indexMemberWorks.getShareCount();
		this.likeRate = indexMemberWorks.getLikeRate();
		this.viewRate = indexMemberWorks.getViewRate();
		EsMember member = indexMemberWorks.getMember();
		if (member != null) {
			this.memberFace = member.getFace();
			this.memberNickname = member.getNickname();
		}
		Integer mss = this.averagDuration;
		if (mss == null || mss.intValue() == 0) {
			this.averagDurationStr = "0";
		} else {
			this.averagDurationStr = DateUtil.formatSec(mss, "", "");
		}
		if (this.viewRate == null || this.viewRate.intValue() == 0) {
			this.viewRateStr = "0%";
		} else {
			this.viewRateStr = StringUtil.formatPercentage(CurrencyUtil.div(this.viewRate, 1000, 3));
		}
		if (this.likeRate == null || this.likeRate.intValue() == 0) {
			this.likeRateStr = "0%";
		} else {
			this.likeRateStr = StringUtil.formatPercentage(CurrencyUtil.div(this.likeRate, 1000, 3));
		}
	}

	private Integer id;

	private Date endDate;

	private Integer worksId;

	private Integer memberId;

	private Integer viewCount;

	private Integer viewArtworkCount;

	private Integer totalDuration;

	private Integer averagDuration;

	/**
	 * 平均浏览时长
	 */
	private String averagDurationStr;

	private Integer leaveCount;

	private Integer backCount;

	private Integer shareCount;

	private Integer likeRate;

	/**
	 * 喜好特征强弱
	 */
	private String likeRateStr;

	private Integer viewRate;

	/**
	 * 平均展示率
	 */
	private String viewRateStr;

	private String memberNickname;

	private String memberFace;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public String getAveragDurationStr() {
		return averagDurationStr;
	}

	public void setAveragDurationStr(String averagDurationStr) {
		this.averagDurationStr = averagDurationStr;
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

	public String getLikeRateStr() {
		return likeRateStr;
	}

	public void setLikeRateStr(String likeRateStr) {
		this.likeRateStr = likeRateStr;
	}

	public Integer getViewRate() {
		return viewRate;
	}

	public void setViewRate(Integer viewRate) {
		this.viewRate = viewRate;
	}

	public String getViewRateStr() {
		return viewRateStr;
	}

	public void setViewRateStr(String viewRateStr) {
		this.viewRateStr = viewRateStr;
	}

	public String getMemberNickname() {
		return memberNickname;
	}

	public void setMemberNickname(String memberNickname) {
		this.memberNickname = memberNickname;
	}

	public String getMemberFace() {
		return memberFace;
	}

	public void setMemberFace(String memberFace) {
		this.memberFace = memberFace;
	}

	@Override
	public String toString() {
		return "AsIndexMemberWorksDto [id=" + id + ", endDate=" + endDate + ", worksId=" + worksId + ", memberId="
				+ memberId + ", viewCount=" + viewCount + ", viewArtworkCount=" + viewArtworkCount + ", totalDuration="
				+ totalDuration + ", averagDuration=" + averagDuration + ", averagDurationStr=" + averagDurationStr
				+ ", leaveCount=" + leaveCount + ", backCount=" + backCount + ", shareCount=" + shareCount
				+ ", likeRate=" + likeRate + ", likeRateStr=" + likeRateStr + ", viewRate=" + viewRate
				+ ", viewRateStr=" + viewRateStr + ", memberNickname=" + memberNickname + ", memberFace=" + memberFace
				+ "]";
	}

}
