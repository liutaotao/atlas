package com.yetoop.cloud.atlas.dto;

import java.util.List;

import com.yetoop.cloud.atlas.common.CurrencyUtil;
import com.yetoop.cloud.atlas.common.DateUtil;
import com.yetoop.cloud.atlas.common.StringUtil;

public class AsIndexWorksDto {

	public void create(Integer pageViewCount, Integer uniqueVisitorCount, Integer averagDuration, Integer viewRate,
			List<StatisticalChart> uniqueVisitorList, List<StatisticalChart> pageViewCountList,
			List<StatisticalChart> averagDurationList, List<StatisticalChart> viewRateList) {
		this.pageViewCount = pageViewCount;
		this.uniqueVisitorCount = uniqueVisitorCount;
		this.viewRate = viewRate;
		this.averagDuration = averagDuration;
		if (averagDuration == null || averagDuration.intValue() == 0) {
			this.averagDurationStr = "0s";
		} else {
			this.averagDurationStr = DateUtil.formatSec(averagDuration, "", "");
		}
		if (viewRate == null || viewRate.intValue() == 0) {
			this.viewRateStr = "0%";
		} else {
			this.viewRateStr = StringUtil.formatPercentage(CurrencyUtil.div(viewRate, 1000, 3));
		}
		this.uniqueVisitorList = uniqueVisitorList;
		this.pageViewCountList = pageViewCountList;
		this.averagDurationList = averagDurationList;
		this.viewRateList = viewRateList;
	}

	/**
	 * 浏览次数
	 */
	private Integer pageViewCount;

	/**
	 * 浏览数量
	 */
	private Integer uniqueVisitorCount;

	private Integer viewRate;

	private Integer averagDuration;

	/**
	 * 平均浏览时长
	 */
	private String averagDurationStr;

	/**
	 * 展示率
	 */
	private String viewRateStr;
	
	/**
	 * 访问人数趋势图list
	 */
	private List<StatisticalChart> uniqueVisitorList;
	
	/**
	 * 访问次数趋势图list
	 */
	private List<StatisticalChart> pageViewCountList;
	
	/**
	 * 平均浏览时长趋势图list
	 */
	private List<StatisticalChart> averagDurationList;
	
	/**
	 * 作品展示率趋势图list
	 */
	private List<StatisticalChart> viewRateList;

	public Integer getPageViewCount() {
		return pageViewCount;
	}

	public void setPageViewCount(Integer pageViewCount) {
		this.pageViewCount = pageViewCount;
	}

	public Integer getUniqueVisitorCount() {
		return uniqueVisitorCount;
	}

	public void setUniqueVisitorCount(Integer uniqueVisitorCount) {
		this.uniqueVisitorCount = uniqueVisitorCount;
	}

	public Integer getViewRate() {
		return viewRate;
	}

	public void setViewRate(Integer viewRate) {
		this.viewRate = viewRate;
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

	public String getViewRateStr() {
		return viewRateStr;
	}

	public void setViewRateStr(String viewRateStr) {
		this.viewRateStr = viewRateStr;
	}

	public List<StatisticalChart> getUniqueVisitorList() {
		return uniqueVisitorList;
	}

	public void setUniqueVisitorList(List<StatisticalChart> uniqueVisitorList) {
		this.uniqueVisitorList = uniqueVisitorList;
	}

	public List<StatisticalChart> getPageViewCountList() {
		return pageViewCountList;
	}

	public void setPageViewCountList(List<StatisticalChart> pageViewCountList) {
		this.pageViewCountList = pageViewCountList;
	}

	public List<StatisticalChart> getAveragDurationList() {
		return averagDurationList;
	}

	public void setAveragDurationList(List<StatisticalChart> averagDurationList) {
		this.averagDurationList = averagDurationList;
	}

	public List<StatisticalChart> getViewRateList() {
		return viewRateList;
	}

	public void setViewRateList(List<StatisticalChart> viewRateList) {
		this.viewRateList = viewRateList;
	}

	@Override
	public String toString() {
		return "AsIndexWorksDto [pageViewCount=" + pageViewCount + ", uniqueVisitorCount=" + uniqueVisitorCount
				+ ", viewRate=" + viewRate + ", averagDuration=" + averagDuration + ", averagDurationStr="
				+ averagDurationStr + ", viewRateStr=" + viewRateStr + ", uniqueVisitorList=" + uniqueVisitorList
				+ ", pageViewCountList=" + pageViewCountList + ", averagDurationList=" + averagDurationList
				+ ", viewRateList=" + viewRateList + "]";
	}
}
