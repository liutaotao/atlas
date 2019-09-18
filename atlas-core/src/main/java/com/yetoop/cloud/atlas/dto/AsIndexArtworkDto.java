package com.yetoop.cloud.atlas.dto;

import java.util.Date;

import com.yetoop.cloud.atlas.common.CurrencyUtil;
import com.yetoop.cloud.atlas.common.DateUtil;
import com.yetoop.cloud.atlas.common.StringUtil;
import com.yetoop.cloud.atlas.domain.AsArtwork;
import com.yetoop.cloud.atlas.domain.AsIndexArtwork;

public class AsIndexArtworkDto {

	public void create(AsIndexArtwork indexArtwork) {
		this.id = indexArtwork.getId();
		this.endDate = indexArtwork.getEndDate();
		this.worksId = indexArtwork.getWorksId();
		this.artworkId = indexArtwork.getArtworkId();
		this.pageView = indexArtwork.getPageView();
		this.uniqueVisitor = indexArtwork.getUniqueVisitor();
		this.totalDuration = indexArtwork.getTotalDuration();
		this.averagDuration = indexArtwork.getAveragDuration();
		this.leaveCount = indexArtwork.getLeaveCount();
		this.backCount = indexArtwork.getBackCount();
		this.shareCount = indexArtwork.getShareCount();
		this.viewRate = indexArtwork.getViewRate();
		this.likeRate = indexArtwork.getLikeRate();
		AsArtwork artwork = indexArtwork.getAsArtwork();
		if (artwork != null) {
			this.artistName = artwork.getArtistName();
			this.artworkImg = artwork.getImageUrl();
			this.artworkName = artwork.getName();
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
		Integer mss = this.averagDuration;
		if (mss == null || mss == 0) {
			this.averagDurationStr = "0";
		} else {
			this.averagDurationStr = DateUtil.formatSec(mss, "", "");
		}
	}

	private Integer id;

	private Date endDate;

	private Integer worksId;

	private Integer artworkId;

	/**
	 * 浏览次数
	 */
	private Integer pageView;

	/**
	 * 浏览人数
	 */
	private Integer uniqueVisitor;

	/**
	 * 总浏览时长
	 */
	private Integer totalDuration;

	private Integer averagDuration;

	/**
	 * 平均浏览时长
	 */
	private String averagDurationStr;

	private Integer leaveCount;

	private Integer backCount;

	private Integer shareCount;

	private Integer viewRate;

	/**
	 * 展示率
	 */
	private String viewRateStr;

	private Integer likeRate;

	/**
	 * 受欢迎程度比例
	 */
	private String likeRateStr;

	private String artworkName;

	private String artworkImg;

	private String artistName;

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

	public Integer getArtworkId() {
		return artworkId;
	}

	public void setArtworkId(Integer artworkId) {
		this.artworkId = artworkId;
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

	public Integer getLikeRate() {
		return likeRate;
	}

	public void setLikeRate(Integer likeRate) {
		this.likeRate = likeRate;
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

	public String getAveragDurationStr() {
		return averagDurationStr;
	}

	public void setAveragDurationStr(String averagDurationStr) {
		this.averagDurationStr = averagDurationStr;
	}

	public String getViewRateStr() {
		return viewRateStr;
	}

	public void setViewRateStr(String viewRateStr) {
		this.viewRateStr = viewRateStr;
	}

	public String getLikeRateStr() {
		return likeRateStr;
	}

	public void setLikeRateStr(String likeRateStr) {
		this.likeRateStr = likeRateStr;
	}

	@Override
	public String toString() {
		return "AsIndexArtworkDto [id=" + id + ", endDate=" + endDate + ", worksId=" + worksId + ", artworkId="
				+ artworkId + ", pageView=" + pageView + ", uniqueVisitor=" + uniqueVisitor + ", totalDuration="
				+ totalDuration + ", averagDuration=" + averagDuration + ", averagDurationStr=" + averagDurationStr
				+ ", leaveCount=" + leaveCount + ", backCount=" + backCount + ", shareCount=" + shareCount
				+ ", viewRate=" + viewRate + ", viewRateStr=" + viewRateStr + ", likeRate=" + likeRate
				+ ", likeRateStr=" + likeRateStr + ", artworkName=" + artworkName + ", artworkImg=" + artworkImg
				+ ", artistName=" + artistName + "]";
	}

}
