package com.yetoop.cloud.atlas.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yetoop.cloud.atlas.common.CurrencyUtil;
import com.yetoop.cloud.atlas.common.DateUtil;
import com.yetoop.cloud.atlas.common.StringUtil;
import com.yetoop.cloud.atlas.domain.AsArtwork;
import com.yetoop.cloud.atlas.domain.AsIndexArtwork;
import com.yetoop.cloud.atlas.domain.AsIndexMemberArtwork;
import com.yetoop.cloud.atlas.domain.AsIndexMemberWorks;
import com.yetoop.cloud.atlas.domain.AsWorks;
import com.yetoop.cloud.atlas.domain.EsMember;
import com.yetoop.cloud.atlas.domain.dao.AsIndexAnalysisDao;
import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.TimeTypeEnum;
import com.yetoop.cloud.atlas.domain.persistence.AsIndexArtworkMapper;
import com.yetoop.cloud.atlas.domain.persistence.AsIndexMemberArtworkMapper;
import com.yetoop.cloud.atlas.domain.persistence.AsIndexMemberWorksMapper;
import com.yetoop.cloud.atlas.domain.persistence.EsMemberMapper;
import com.yetoop.cloud.atlas.dto.AsIndexMemberArtworkDto;
import com.yetoop.cloud.atlas.dto.StatisticalChart;
import com.yetoop.cloud.atlas.exception.AppException;
import com.yetoop.cloud.atlas.service.AtalasAdminService;
import com.yetoop.cloud.atlas.service.AtalasIndexArtworkService;
import com.zhenyi.common.pager.PagedList;

@Service("atalasIndexArtworkService")
public class AtalasIndexArtworkServiceImpl implements AtalasIndexArtworkService {

	private final static Logger log = LoggerFactory.getLogger(AtalasIndexArtworkServiceImpl.class);

	@Autowired
	private AtalasAdminService atlasAdminService;

	@Autowired
	private AsIndexMemberArtworkMapper indexMemberArtworkMapper;
	@Autowired
	private AsIndexMemberWorksMapper indexMemberWorksMapper;
	@Autowired
	private AsIndexArtworkMapper indexArtworkMapper;
	@Autowired
	private EsMemberMapper memberMapper;

	@Autowired
	private AsIndexAnalysisDao asIndexAnalysisDao;

	@Override
	public Map<String, Object> loadMemberOverview(Integer memberId, String encryptKey, Integer worksId, Integer userId,
			Integer type) {
		if (log.isDebugEnabled()) {
			log.debug("loadMemberOverview-->memberId:{},encryptKey:{},worksId:{},userId:{},type:{}", memberId,
					encryptKey, worksId, userId, type);
		}
		if (userId == null || userId.intValue() == 0) {
			throw new AppException(AppException.BusiAppCode.INVALID_USER);
		}
		EsMember user = this.memberMapper.selectByPrimaryKey(userId);
		if (user == null) {
			throw new AppException(AppException.BusiAppCode.NULL_USER);
		}
		AsWorks works = this.atlasAdminService.queryWorks(memberId, encryptKey, worksId);
		if (log.isDebugEnabled()) {
			log.debug("loadMemberOverview-->works:{}", works);
		}
		String timeType = TimeTypeEnum.ALL.getType();
		Date now = new Date();
		Integer beginTime = 0;
		Integer endTime = 0;
		if (type != null) {
			switch (type) {
			case 0:
				timeType = TimeTypeEnum.ALL.getType();
				break;
			case 1:
				timeType = TimeTypeEnum.HOUR.getType();// 今日
				beginTime = StringUtil.toInt(DateUtil.toString(now, "yyyyMMdd") + "00", false);
				endTime = StringUtil.toInt(DateUtil.toString(now, "yyyyMMddHH"), false);
				break;
			case 2:
				timeType = TimeTypeEnum.DAY.getType();// 昨日
				beginTime = StringUtil.toInt(DateUtil.toString(DateUtils.addDays(now, -1), "yyyyMMdd"), false);
				endTime = StringUtil.toInt(DateUtil.toString(now, "yyyyMMdd"), false);
				break;
			case 3:
				timeType = TimeTypeEnum.DAY.getType();// 近一周
				beginTime = StringUtil.toInt(DateUtil.toString(DateUtils.addDays(now, -7), "yyyyMMdd"), false);
				endTime = StringUtil.toInt(DateUtil.toString(now, "yyyyMMdd"), false);
				break;
			case 4:
				timeType = TimeTypeEnum.DAY.getType();// 近一月
				beginTime = StringUtil.toInt(DateUtil.toString(DateUtils.addDays(now, -30), "yyyyMMdd"), false);
				endTime = StringUtil.toInt(DateUtil.toString(now, "yyyyMMdd"), false);
				break;
			default:
				timeType = TimeTypeEnum.ALL.getType();
				break;
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("loadMemberOverview-->timeType:{}, beginTime:{}, endTime:{}", timeType, beginTime, endTime);
		}
		List<AsIndexMemberWorks> indexMemberWorksList = this.indexMemberWorksMapper.selectByMemberIdTimeType(worksId,
				userId, timeType, beginTime, endTime);
		if (log.isDebugEnabled()) {
			log.debug("loadMemberOverview-->indexMemberWorksList:{}", indexMemberWorksList);
		}

		Map<String, Object> map = this.calcMemberOverview(indexMemberWorksList, worksId, userId, timeType);
		if (log.isDebugEnabled()) {
			log.debug("loadMemberOverview-->map:{}", map);
		}
		return map;
	}

	@Override
	public Map<String, Object> loadArtworkOverview(Integer memberId, String encryptKey, Integer worksId,
			Integer artworkId, Integer type) {
		if (log.isDebugEnabled()) {
			log.debug("loadArtworkOverview-->memberId:{},encryptKey:{},worksId:{},artworkId:{},type:{}", memberId,
					encryptKey, worksId, artworkId, type);
		}
		AsArtwork artwork = this.atlasAdminService.queryArtwork(memberId, encryptKey, worksId, artworkId);
		if (log.isDebugEnabled()) {
			log.debug("loadArtworkOverview-->artwork:{}", artwork);
		}
		String timeType = TimeTypeEnum.ALL.getType();
		Date now = new Date();
		Integer beginTime = 0;
		Integer endTime = 0;
		if (type != null) {
			switch (type) {
			case 0:
				timeType = TimeTypeEnum.ALL.getType();
				break;
			case 1:
				timeType = TimeTypeEnum.HOUR.getType();// 今日
				beginTime = StringUtil.toInt(DateUtil.toString(now, "yyyyMMdd") + "00", false);
				endTime = StringUtil.toInt(DateUtil.toString(now, "yyyyMMddHH"), false);
				break;
			case 2:
				timeType = TimeTypeEnum.DAY.getType();// 昨日
				beginTime = StringUtil.toInt(DateUtil.toString(DateUtils.addDays(now, -1), "yyyyMMdd"), false);
				endTime = StringUtil.toInt(DateUtil.toString(now, "yyyyMMdd"), false);
				break;
			case 3:
				timeType = TimeTypeEnum.DAY.getType();// 近一周
				beginTime = StringUtil.toInt(DateUtil.toString(DateUtils.addDays(now, -7), "yyyyMMdd"), false);
				endTime = StringUtil.toInt(DateUtil.toString(now, "yyyyMMdd"), false);
				break;
			case 4:
				timeType = TimeTypeEnum.DAY.getType();// 近一月
				beginTime = StringUtil.toInt(DateUtil.toString(DateUtils.addDays(now, -30), "yyyyMMdd"), false);
				endTime = StringUtil.toInt(DateUtil.toString(now, "yyyyMMdd"), false);
				break;
			default:
				timeType = TimeTypeEnum.ALL.getType();
				break;
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("loadMemberOverview-->timeType:{}, beginTime:{}, endTime:{}", timeType, beginTime, endTime);
		}
		List<AsIndexArtwork> indexArtworkList = this.indexArtworkMapper.selectByArtworkIdTimeType(worksId, artworkId,
				timeType, beginTime, endTime);
		if (log.isDebugEnabled()) {
			log.debug("loadArtworkOverview-->indexArtworkList:{}", indexArtworkList);
		}
		Map<String, Object> map = this.calcArtworkOverview(indexArtworkList,worksId, artworkId,
				timeType);
		if (log.isDebugEnabled()) {
			log.debug("loadArtworkOverview-->map:{}", map);
		}
		return map;
	}

	/**
	 * 计算用户概况
	 */
	private Map<String, Object> calcMemberOverview(List<AsIndexMemberWorks> indexMemberWorksList, Integer worksId,
			Integer userId, String timeType) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer averagDurationCount = 0;
		// 总访问次数
		int totalViewCount = 0;
		// 总浏览作品数量
		int totalViewArtworkCount = 0;
		int viewRateCount = 0;
		int viewRate = 0;
		// 展示率
		String viewRateStr = "0%";
		int averagDuration = 0;
		// 平均浏览时长5min3s 30s
		String averagDurationStr = "0s";
		
		//访问次数list
		List<StatisticalChart> viewCountList = new ArrayList<StatisticalChart>();
		//浏览作品数量list
		List<StatisticalChart> viewArtworkList = new ArrayList<StatisticalChart>();
		//平均浏览时长list(单位s)
		List<StatisticalChart> averagDurationList = new ArrayList<StatisticalChart>();
		//展示率list
		List<StatisticalChart> viewRateList = new ArrayList<StatisticalChart>();
		
		if (indexMemberWorksList != null && !indexMemberWorksList.isEmpty()) {
			int overviewSize = indexMemberWorksList.size();
			for (AsIndexMemberWorks indexMemberWorks : indexMemberWorksList) {
				Integer viewCount = indexMemberWorks.getViewCount();
				Integer viewArtworkCount = indexMemberWorks.getViewArtworkCount();
				Integer duration = indexMemberWorks.getAveragDuration();
				Integer rate = indexMemberWorks.getViewRate();
				if (!TimeTypeEnum.ALL.getType().equals(timeType)) {
					this.statisticalMemberChartList(viewCountList, viewArtworkList, averagDurationList, viewRateList,
							viewCount, viewArtworkCount, duration, rate, indexMemberWorks);
				}
				if (viewCount != null && viewCount.intValue() != 0) {
					totalViewCount += viewCount.intValue();
				}
				if (viewArtworkCount != null && viewArtworkCount.intValue() != 0) {
					totalViewArtworkCount += viewArtworkCount.intValue();
				}
				if (duration != null && duration.intValue() != 0) {
					averagDurationCount += duration.intValue();
				}
				if (rate != null && rate.intValue() != 0) {
					viewRateCount += rate.intValue();
				}
			}
			averagDuration = Double.valueOf(CurrencyUtil.div(averagDurationCount, overviewSize)).intValue();
			viewRate = Double.valueOf(CurrencyUtil.div(viewRateCount, overviewSize)).intValue();
		}
		if (TimeTypeEnum.ALL.getType().equals(timeType)) {
			indexMemberWorksList = this.indexMemberWorksMapper.selectAllByMemberIdTimeType(worksId, userId,
					TimeTypeEnum.DAY.getType());
			if (indexMemberWorksList != null && !indexMemberWorksList.isEmpty()) {
				for (AsIndexMemberWorks indexMemberWorks : indexMemberWorksList) {
					Integer viewCount = indexMemberWorks.getViewCount();
					Integer viewArtworkCount = indexMemberWorks.getViewArtworkCount();
					Integer duration = indexMemberWorks.getAveragDuration();
					Integer rate = indexMemberWorks.getViewRate();
					this.statisticalMemberChartList(viewCountList, viewArtworkList, averagDurationList, viewRateList,
							viewCount, viewArtworkCount, duration, rate, indexMemberWorks);
				}
			}
		}
		if (viewRateCount != 0) {
			viewRateStr = StringUtil.formatPercentage(CurrencyUtil.div(viewRate, 1000, 3));
		}
		if (averagDuration != 0) {
			averagDurationStr = DateUtil.formatSec(averagDuration, "", "");
		}
		map.put("totalViewCount", totalViewCount);
		map.put("totalViewArtworkCount", totalViewArtworkCount);
		map.put("viewRate", viewRate);
		map.put("viewRateStr", viewRateStr);
		map.put("averagDuration", averagDuration);
		map.put("averagDurationStr", averagDurationStr);
		map.put("viewCountList", viewCountList);
		map.put("viewArtworkList", viewArtworkList);
		map.put("averagDurationList", averagDurationList);
		map.put("viewRateList", viewRateList);
		return map;
	}
	
	/**
	 * 统计用户趋势图表list
	 */
	private void statisticalMemberChartList(List<StatisticalChart> viewCountList,
			List<StatisticalChart> viewArtworkList, List<StatisticalChart> averagDurationList,
			List<StatisticalChart> viewRateList, Integer viewCount, Integer viewArtworkCount, Integer duration,
			Integer rate, AsIndexMemberWorks indexMemberWorks) {
		// 访问次数list
		StatisticalChart viewCountSc = new StatisticalChart();
		viewCountSc.createByIndexMemberWorks(indexMemberWorks, StringUtil.toString(viewCount));
		viewCountList.add(viewCountSc);

		// 浏览作品数量list
		StatisticalChart viewArtworkSc = new StatisticalChart();
		viewArtworkSc.createByIndexMemberWorks(indexMemberWorks, StringUtil.toString(viewArtworkCount));
		viewArtworkList.add(viewArtworkSc);

		// 平均浏览时长统计 (单位s)
		StatisticalChart averagDurationSc = new StatisticalChart();
		averagDurationSc.createByIndexMemberWorks(indexMemberWorks, StringUtil.toString(duration));
		averagDurationList.add(averagDurationSc);

		// 作品展示率统计
		StatisticalChart viewRateSc = new StatisticalChart();
		String viewRateStr = "0%";
		if (rate == null || rate.intValue() == 0) {
			viewRateStr = "0%";
		} else {
			viewRateStr = StringUtil.formatPercentage(CurrencyUtil.div(rate, 1000, 3));
		}
		viewRateSc.createByIndexMemberWorks(indexMemberWorks, viewRateStr);
		viewRateList.add(viewRateSc);
	}

	/**
	 * 计算作品概况
	 * @param indexArtworkList
	 * @return
	 */
	private Map<String, Object> calcArtworkOverview(List<AsIndexArtwork> indexArtworkList, Integer worksId,
			Integer artworkId, String timeType) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 总浏览次数
		int pageViewCount = 0;
		// 总浏览人数
		int uniqueVisitorCount = 0;
		int averagDurationCount = 0;
		int viewRateCount = 0;
		int viewRate = 0;
		// 展示率
		String viewRateStr = "0%";
		int averagDuration = 0;
		// 平均浏览时长
		String averagDurationStr = "0s";
		
		// 浏览次数list
		List<StatisticalChart> pageViewCountList = new ArrayList<StatisticalChart>();
		// 浏览人数list
		List<StatisticalChart> uniqueVisitorList = new ArrayList<StatisticalChart>();
		// 平均浏览时长list(单位s)
		List<StatisticalChart> averagDurationList = new ArrayList<StatisticalChart>();
		// 展示率list
		List<StatisticalChart> viewRateList = new ArrayList<StatisticalChart>();
		
		if (indexArtworkList != null && !indexArtworkList.isEmpty()) {
			int overviewSize = indexArtworkList.size();
			for (AsIndexArtwork indexArtwork : indexArtworkList) {
				Integer pageView = indexArtwork.getPageView();
				Integer uniqueVisitor = indexArtwork.getUniqueVisitor();
				Integer duration = indexArtwork.getAveragDuration();
				Integer rate = indexArtwork.getViewRate();
				if(!TimeTypeEnum.ALL.getType().equals(timeType)){
					this.statisticalArtworkChartList(pageViewCountList, uniqueVisitorList, averagDurationList,
							viewRateList, pageView, uniqueVisitor, duration, rate, indexArtwork);
				}
				if (pageView != null && pageView.intValue() != 0) {
					pageViewCount += pageView.intValue();
				}
				if (uniqueVisitor != null && uniqueVisitor.intValue() != 0) {
					uniqueVisitorCount += uniqueVisitor.intValue();
				}
				if (duration != null && duration.intValue() != 0) {
					averagDurationCount += duration.intValue();
				}
				if (rate != null && rate.intValue() != 0) {
					viewRateCount += rate.intValue();
				}
			}
			averagDuration = Double.valueOf(CurrencyUtil.div(averagDurationCount, overviewSize)).intValue();
			viewRate = Double.valueOf(CurrencyUtil.div(viewRateCount, overviewSize)).intValue();
		}
		if(TimeTypeEnum.ALL.getType().equals(timeType)){
			indexArtworkList = this.indexArtworkMapper.selectAllByArtworkIdTimeType(worksId, artworkId, TimeTypeEnum.DAY.getType());
			if (indexArtworkList != null && !indexArtworkList.isEmpty()) {
				for (AsIndexArtwork indexArtwork : indexArtworkList) {
					Integer pageView = indexArtwork.getPageView();
					Integer uniqueVisitor = indexArtwork.getUniqueVisitor();
					Integer duration = indexArtwork.getAveragDuration();
					Integer rate = indexArtwork.getViewRate();
					this.statisticalArtworkChartList(pageViewCountList, uniqueVisitorList, averagDurationList,
							viewRateList, pageView, uniqueVisitor, duration, rate, indexArtwork);
				}
			}
		}
		if (viewRateCount != 0) {
			viewRateStr = StringUtil.formatPercentage(CurrencyUtil.div(viewRate, 1000, 3));
		}
		if ( averagDuration != 0) {
			averagDurationStr = DateUtil.formatSec(averagDuration, "", "");
		}
		map.put("pageViewCount", pageViewCount);
		map.put("uniqueVisitorCount", uniqueVisitorCount);
		map.put("viewRate", viewRate);
		map.put("viewRateStr", viewRateStr);
		map.put("averagDuration", averagDuration);
		map.put("averagDurationStr", averagDurationStr);
		map.put("pageViewCountList", pageViewCountList);
		map.put("uniqueVisitorList", uniqueVisitorList);
		map.put("averagDurationList", averagDurationList);
		map.put("viewRateList", viewRateList);
		return map;
	}
	
	/**
	 * 统计作品趋势图表list
	 */
	private void statisticalArtworkChartList(List<StatisticalChart> pageViewCountList,
			List<StatisticalChart> uniqueVisitorList, List<StatisticalChart> averagDurationList,
			List<StatisticalChart> viewRateList, Integer pageView, Integer uniqueVisitor, Integer duration,
			Integer rate, AsIndexArtwork indexArtwork) {
		// 浏览次数list
		StatisticalChart viewCountSc = new StatisticalChart();
		viewCountSc.createByIndexArtwork(indexArtwork, StringUtil.toString(pageView));
		pageViewCountList.add(viewCountSc);

		// 浏览人数list
		StatisticalChart viewArtworkSc = new StatisticalChart();
		viewArtworkSc.createByIndexArtwork(indexArtwork, StringUtil.toString(uniqueVisitor));
		uniqueVisitorList.add(viewArtworkSc);

		// 平均浏览时长统计 (单位s)
		StatisticalChart averagDurationSc = new StatisticalChart();
		averagDurationSc.createByIndexArtwork(indexArtwork, StringUtil.toString(duration));
		averagDurationList.add(averagDurationSc);

		// 作品展示率统计
		StatisticalChart viewRateSc = new StatisticalChart();
		String viewRateStr = "0%";
		if (rate == null || rate.intValue() == 0) {
			viewRateStr = "0%";
		} else {
			viewRateStr = StringUtil.formatPercentage(CurrencyUtil.div(rate, 1000, 3));
		}
		viewRateSc.createByIndexArtwork(indexArtwork, viewRateStr);
		viewRateList.add(viewRateSc);
	}

	@Override
	public List<AsIndexMemberArtworkDto> loadMemberArtworkAnalysis(Integer memberId, String encryptKey, Integer worksId,
			Integer userId) {
		if (log.isDebugEnabled()) {
			log.debug("loadMemberArtworkAnalysis-->memberId:{},encryptKey:{},worksId:{},userId:{}", memberId,
					encryptKey, worksId, userId);
		}
		if (userId == null || userId.intValue() == 0) {
			throw new AppException(AppException.BusiAppCode.INVALID_USER);
		}
		//获取并判断用户
		EsMember user = this.memberMapper.selectByPrimaryKey(userId);
		if (user == null) {
			throw new AppException(AppException.BusiAppCode.NULL_USER);
		}
		AsWorks works = this.atlasAdminService.queryWorks(memberId, encryptKey, worksId);
		if (log.isDebugEnabled()) {
			log.debug("loadMemberArtworkAnalysis-->works:{}", works);
		}
		List<AsIndexMemberArtwork> indexMemberArtworkList = this.indexMemberArtworkMapper.selectByMemberId(worksId,
				userId);
		if (log.isDebugEnabled()) {
			log.debug("loadMemberArtworkAnalysis-->indexMemberArtworkList:{}", indexMemberArtworkList);
		}
		List<AsIndexMemberArtworkDto> indexMemberArtworkDtoList = this
				.convertToIndexMemberArtworkDtoList(indexMemberArtworkList);
		if (log.isDebugEnabled()) {
			log.debug("loadMemberArtworkAnalysis-->indexMemberArtworkDtoList:{}", indexMemberArtworkDtoList);
		}

		return indexMemberArtworkDtoList;
	}

	@Override
	public PagedList<AsIndexMemberArtworkDto> loadArtworkMemberAnalysisPage(Integer memberId, String encryptKey,
			Integer worksId, Integer artworkId, String keywords, Integer currentPageNo, Integer pageSize) {
		if (log.isDebugEnabled()) {
			log.debug(
					"loadArtworkMemberAnalysisPage-->memberId:{},encryptKey:{},worksId:{},artworkId:{},keywords:{},currentPageNo:{},pageSize:{}",
					memberId, encryptKey, worksId, artworkId, keywords, currentPageNo, pageSize);
		}
		AsArtwork artwork = this.atlasAdminService.queryArtwork(memberId, encryptKey, worksId, artworkId);
		if (log.isDebugEnabled()) {
			log.debug("loadArtworkMemberAnalysisPage-->artwork:{}", artwork);
		}
		String pageNo = "1";
		String size = "10";
		if (currentPageNo != null && currentPageNo.intValue() != 0) {
			pageNo = StringUtil.toString(currentPageNo);
		}
		if (pageSize != null && pageSize.intValue() != 0) {
			size = StringUtil.toString(pageSize);
		}
		PagedList<AsIndexMemberArtwork> indexMemberArtworkPage = this.asIndexAnalysisDao
				.selectArtworkMemberByArtworkIdNickname(worksId, artworkId, keywords, pageNo, size);
		if (log.isDebugEnabled()) {
			log.debug("loadArtworkMemberAnalysisPage-->indexMemberArtworkPage:{}", indexMemberArtworkPage);
		}
		PagedList<AsIndexMemberArtworkDto> indexMemberArtworkDtoPagedList = this
				.convertToIndexMemberArtworkDtoPagedList(indexMemberArtworkPage);
		if (log.isDebugEnabled()) {
			log.debug("loadArtworkMemberAnalysisPage-->indexMemberArtworkDtoPagedList:{}",
					indexMemberArtworkDtoPagedList);
		}
		return indexMemberArtworkDtoPagedList;
	}

	@Override
	public List<AsIndexMemberArtworkDto> loadArtworkMemberAnalysis(Integer memberId, String encryptKey, Integer worksId,
			Integer artworkId, String keywords) {
		if (log.isDebugEnabled()) {
			log.debug("loadArtworkMemberAnalysis-->memberId:{},encryptKey:{},worksId:{},artworkId:{},keywords:{}",
					memberId, encryptKey, worksId, artworkId, keywords);
		}
		AsArtwork artwork = this.atlasAdminService.queryArtwork(memberId, encryptKey, worksId, artworkId);
		if (log.isDebugEnabled()) {
			log.debug("loadArtworkMemberAnalysis-->artwork:{}", artwork);
		}
		if (StringUtil.isNullString(keywords)) {
			keywords = "";
		}
		List<AsIndexMemberArtwork> indexMemberArtworkList = this.indexMemberArtworkMapper.selectByArtworkId(keywords,
				worksId, artworkId);
		if (log.isDebugEnabled()) {
			log.debug("loadArtworkMemberAnalysis-->indexMemberArtworkList:{}", indexMemberArtworkList);
		}
		List<AsIndexMemberArtworkDto> indexMemberArtworkDtoList = this
				.convertToIndexMemberArtworkDtoList(indexMemberArtworkList);
		if (log.isDebugEnabled()) {
			log.debug("loadArtworkMemberAnalysis-->indexMemberArtworkDtoList:{}", indexMemberArtworkDtoList);
		}
		return indexMemberArtworkDtoList;
	}

	private PagedList<AsIndexMemberArtworkDto> convertToIndexMemberArtworkDtoPagedList(
			PagedList<AsIndexMemberArtwork> indexMemberArtworkPage) {
		PagedList<AsIndexMemberArtworkDto> indexMemberArtworkDtoPage = new PagedList<AsIndexMemberArtworkDto>();
		indexMemberArtworkDtoPage.setPageSize(indexMemberArtworkPage.getPageSize());
		indexMemberArtworkDtoPage.setCurrentPage(indexMemberArtworkPage.getCurrentPage());
		indexMemberArtworkDtoPage.setTotalCount(indexMemberArtworkPage.getTotalCount());
		indexMemberArtworkDtoPage.setTotalPage(indexMemberArtworkPage.getTotalPage());

		List<AsIndexMemberArtwork> indexMemberWorksList = indexMemberArtworkPage.getResult();
		List<AsIndexMemberArtworkDto> indexMemberWorksDtoList = this
				.convertToIndexMemberArtworkDtoList(indexMemberWorksList);
		indexMemberArtworkDtoPage.setResult(indexMemberWorksDtoList);
		return indexMemberArtworkDtoPage;
	}

	private List<AsIndexMemberArtworkDto> convertToIndexMemberArtworkDtoList(
			List<AsIndexMemberArtwork> indexMemberArtworkList) {
		List<AsIndexMemberArtworkDto> indexMemberArtworkDtoList = new ArrayList<AsIndexMemberArtworkDto>();
		if (indexMemberArtworkList != null && !indexMemberArtworkList.isEmpty()) {
			for (AsIndexMemberArtwork indexMemberArtwork : indexMemberArtworkList) {
				AsIndexMemberArtworkDto indexMemberArtworkDto = new AsIndexMemberArtworkDto();
				indexMemberArtworkDto.create(indexMemberArtwork);
				indexMemberArtworkDtoList.add(indexMemberArtworkDto);
			}
		}
		return indexMemberArtworkDtoList;
	}
}
