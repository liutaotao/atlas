package com.yetoop.cloud.atlas.dto;

import java.util.Date;

import com.yetoop.cloud.atlas.common.CurrencyUtil;
import com.yetoop.cloud.atlas.common.DateUtil;
import com.yetoop.cloud.atlas.common.StringUtil;
import com.yetoop.cloud.atlas.domain.AsArtwork;
import com.yetoop.cloud.atlas.domain.AsIndexMemberArtwork;
import com.yetoop.cloud.atlas.domain.EsMember;

public class AsIndexMemberArtworkDto {

	public void create(AsIndexMemberArtwork indexMemberArtwork) {
		this.id = indexMemberArtwork.getId();
		this.worksId = indexMemberArtwork.getWorksId();
		this.memberId = indexMemberArtwork.getMemberId();
		this.artworkId = indexMemberArtwork.getArtworkId();
		this.endDate = indexMemberArtwork.getEndDate();
		this.viewCount = indexMemberArtwork.getViewCount();
		this.totalDuration = indexMemberArtwork.getTotalDuration();
		this.averagDuration = indexMemberArtwork.getAveragDuration();
		this.leaveCount = indexMemberArtwork.getLeaveCount();
		this.backCount = indexMemberArtwork.getBackCount();
		this.shareCount = indexMemberArtwork.getShareCount();
		this.likeRate = indexMemberArtwork.getLikeRate();
		if (this.averagDuration == null || this.averagDuration.intValue() == 0) {
			this.averagDurationStr = "0s";
		} else {
			this.averagDurationStr = DateUtil.formatSec(this.averagDuration, "", "");
		}
		if (this.likeRate == null || this.likeRate.intValue() == 0) {
			this.likeRateStr = "0%";
		} else {
			this.likeRateStr = StringUtil.formatPercentage(CurrencyUtil.div(this.likeRate, 1000, 3));
		}
		EsMember member = indexMemberArtwork.getMember();
		AsArtwork artwork = indexMemberArtwork.getArtwork();
		if (member != null) {
			this.memberFace = member.getFace();
			this.memberNickname = member.getNickname();
		}
		if (artwork != null) {
			this.artistName = artwork.getArtistName();
			this.artworkImg = artwork.getImageUrl();
			this.artworkName = artwork.getName();
		}
	}

	private Integer id;

	private Integer worksId;

	private Integer artworkId;

	private Integer memberId;

	private Date endDate;

	private Integer viewCount;

	private Integer totalDuration;

	private Integer averagDuration;

	private Integer leaveCount;

	private Integer backCount;

	private Integer shareCount;

	private Integer likeRate;

	/**
	 * 平均浏览时长
	 */
	private String averagDurationStr;

	/**
	 * 喜好特征比例
	 */
	private String likeRateStr;

	private String memberNickname;

	private String memberFace;

	private String artworkName;

	private String artworkImg;

	private String artistName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public String getAveragDurationStr() {
		return averagDurationStr;
	}

	public void setAveragDurationStr(String averagDurationStr) {
		this.averagDurationStr = averagDurationStr;
	}

	public String getLikeRateStr() {
		return likeRateStr;
	}

	public void setLikeRateStr(String likeRateStr) {
		this.likeRateStr = likeRateStr;
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

	public String getArtworkName() {
		return artworkName;
	}

	public void setArtworkName(String artworkName) {
		this.artworkName = artworkName;
	}

	public String getArtworkImg() {
		return artworkImg;
	}

	public void setArtworkImg(String artworkImg) {
		this.artworkImg = artworkImg;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	@Override
	public String toString() {
		return "AsIndexMemberArtworkDto [id=" + id + ", worksId=" + worksId + ", artworkId=" + artworkId + ", memberId="
				+ memberId + ", endDate=" + endDate + ", viewCount=" + viewCount + ", totalDuration=" + totalDuration
				+ ", averagDuration=" + averagDuration + ", leaveCount=" + leaveCount + ", backCount=" + backCount
				+ ", shareCount=" + shareCount + ", likeRate=" + likeRate + ", averagDurationStr=" + averagDurationStr
				+ ", likeRateStr=" + likeRateStr + ", memberNickname=" + memberNickname + ", memberFace=" + memberFace
				+ ", artworkName=" + artworkName + ", artworkImg=" + artworkImg + ", artistName=" + artistName + "]";
	}

}
