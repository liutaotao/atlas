package com.yetoop.cloud.atlas.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yetoop.cloud.atlas.common.CurrencyUtil;
import com.yetoop.cloud.atlas.common.DateUtil;
import com.yetoop.cloud.atlas.common.StringUtil;
import com.yetoop.cloud.atlas.domain.AsIndexArtwork;
import com.yetoop.cloud.atlas.domain.AsIndexMemberWorks;
import com.yetoop.cloud.atlas.domain.AsIndexWorks;
import com.yetoop.cloud.atlas.domain.AsWorks;
import com.yetoop.cloud.atlas.domain.dao.AsIndexAnalysisDao;
import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.TimeTypeEnum;
import com.yetoop.cloud.atlas.domain.persistence.AsIndexArtworkMapper;
import com.yetoop.cloud.atlas.domain.persistence.AsIndexMemberWorksMapper;
import com.yetoop.cloud.atlas.domain.persistence.AsIndexWorksMapper;
import com.yetoop.cloud.atlas.dto.AsIndexArtworkDto;
import com.yetoop.cloud.atlas.dto.AsIndexMemberWorksDto;
import com.yetoop.cloud.atlas.dto.AsIndexWorksDto;
import com.yetoop.cloud.atlas.dto.StatisticalChart;
import com.yetoop.cloud.atlas.service.AtalasAdminService;
import com.yetoop.cloud.atlas.service.AtalasIndexWorksService;
import com.zhenyi.common.pager.PagedList;

@Service("atalasIndexWorksService")
public class AtalasIndexWorksServiceImpl implements AtalasIndexWorksService {

	private static final Logger log = LoggerFactory.getLogger(AtalasIndexWorksServiceImpl.class);

	@Autowired
	private AsIndexWorksMapper indexWorksMapper;

	@Autowired
	private AsIndexArtworkMapper indexArtworkMapper;

	@Autowired
	private AsIndexMemberWorksMapper indexMemberWorksMapper;

	@Autowired
	private AsIndexAnalysisDao asIndexAnalysisDao;

	@Autowired
	private AtalasAdminService atlasAdminService;

	@Override
	public List<AsIndexMemberWorksDto> loadAllMemberWorksAnalysis(Integer worksId, Integer memberId, String encryptKey,
			String keywords) {
		if (log.isDebugEnabled()) {
			log.debug("loadMemberWorksAnalysis-->memberId:{},encryptKey:{}worksId:{},keywords:{}", memberId, encryptKey,
					worksId, keywords);
		}
		AsWorks works = this.atlasAdminService.queryWorks(memberId, encryptKey, worksId);
		List<AsIndexMemberWorks> indexMemberWorksList = this.indexMemberWorksMapper.selectByWorksId(keywords,
				works.getId());
		if (log.isDebugEnabled()) {
			log.debug("loadAllMemberWorksAnalysis-->indexMemberWorksList:{}", indexMemberWorksList);
		}
		List<AsIndexMemberWorksDto> indexMemberWorksDtoList = this
				.convertToIndexMemberWorksDtoList(indexMemberWorksList);
		if (log.isDebugEnabled()) {
			log.debug("loadAllMemberWorksAnalysis-->indexMemberWorksDtoList:{}", indexMemberWorksDtoList);
		}
		return indexMemberWorksDtoList;
	}

	@Override
	public PagedList<AsIndexMemberWorksDto> loadMemberWorksAnalysis(Integer worksId, Integer memberId,
			String encryptKey, String keywords, Integer currentPageNo, Integer pageSize) {
		if (log.isDebugEnabled()) {
			log.debug(
					"loadMemberWorksAnalysis-->memberId:{},encryptKey:{}worksId:{},keywords:{},currentPageNo:{},pageSize:{}",
					memberId, encryptKey, worksId, keywords, currentPageNo, pageSize);
		}
		AsWorks works = this.atlasAdminService.queryWorks(memberId, encryptKey, worksId);
		String pageNo = "1";
		String size = "10";
		if (currentPageNo != null && currentPageNo.intValue() != 0) {
			pageNo = StringUtil.toString(currentPageNo);
		}
		if (pageSize != null && pageSize.intValue() != 0) {
			size = StringUtil.toString(pageSize);
		}
		PagedList<AsIndexMemberWorks> indexMemberWorksPage = this.asIndexAnalysisDao
				.selectMemberWorksByWorksIdNickname(works.getId(), keywords, pageNo, size);
		if (log.isDebugEnabled()) {
			log.debug("loadMemberWorksAnalysis-->indexMemberWorksPage:{}", indexMemberWorksPage);
		}
		PagedList<AsIndexMemberWorksDto> indexMemberWorksDtoPage = this
				.convertToIndexMemberWorksDtoPagedList(indexMemberWorksPage);
		if (log.isDebugEnabled()) {
			log.debug("loadMemberWorksAnalysis-->indexMemberWorksDtoPage:{}", indexMemberWorksDtoPage);
		}
		return indexMemberWorksDtoPage;
	}

	@Override
	public List<AsIndexArtworkDto> loadArtworkAnalysis(Integer memberId, String encryptKey, Integer worksId) {
		if (log.isDebugEnabled()) {
			log.debug("loadArtworkAnalysis-->memberId:{},encryptKey:{}worksId:{}", memberId, encryptKey, worksId);
		}
		AsWorks works = this.atlasAdminService.queryWorks(memberId, encryptKey, worksId);
		List<AsIndexArtwork> indexArtworkList = this.indexArtworkMapper.selectByWorkId(works.getId());
		if (log.isDebugEnabled()) {
			log.debug("loadArtworkAnalysis-->indexArtworkList:{}", indexArtworkList);
		}
		List<AsIndexArtworkDto> indexArtworkDtoList = this.convertToIndexArtworkDtoList(indexArtworkList);
		if (log.isDebugEnabled()) {
			log.debug("loadArtworkAnalysis-->indexArtworkDtoList:{}", indexArtworkDtoList);
		}
		return indexArtworkDtoList;
	}

	private PagedList<AsIndexMemberWorksDto> convertToIndexMemberWorksDtoPagedList(
			PagedList<AsIndexMemberWorks> indexMemberWorksPage) {
		PagedList<AsIndexMemberWorksDto> indexMemberWorksDtoPage = new PagedList<AsIndexMemberWorksDto>();
		indexMemberWorksDtoPage.setPageSize(indexMemberWorksPage.getPageSize());
		indexMemberWorksDtoPage.setCurrentPage(indexMemberWorksPage.getCurrentPage());
		indexMemberWorksDtoPage.setTotalCount(indexMemberWorksPage.getTotalCount());
		indexMemberWorksDtoPage.setTotalPage(indexMemberWorksPage.getTotalPage());

		List<AsIndexMemberWorks> indexMemberWorksList = indexMemberWorksPage.getResult();
		List<AsIndexMemberWorksDto> indexMemberWorksDtoList = this
				.convertToIndexMemberWorksDtoList(indexMemberWorksList);
		indexMemberWorksDtoPage.setResult(indexMemberWorksDtoList);
		return indexMemberWorksDtoPage;
	}

	private List<AsIndexMemberWorksDto> convertToIndexMemberWorksDtoList(
			List<AsIndexMemberWorks> indexMemberWorksList) {
		List<AsIndexMemberWorksDto> indexMemberWorksDtoList = new ArrayList<AsIndexMemberWorksDto>();
		if (indexMemberWorksList != null && !indexMemberWorksList.isEmpty()) {
			for (AsIndexMemberWorks indexMemberWorks : indexMemberWorksList) {
				AsIndexMemberWorksDto indexMemberWorksDto = new AsIndexMemberWorksDto();
				indexMemberWorksDto.create(indexMemberWorks);
				indexMemberWorksDtoList.add(indexMemberWorksDto);
			}
		}
		return indexMemberWorksDtoList;
	}

	private List<AsIndexArtworkDto> convertToIndexArtworkDtoList(List<AsIndexArtwork> indexArtworkList) {
		List<AsIndexArtworkDto> indexArtworkDtoList = new ArrayList<AsIndexArtworkDto>();
		if (indexArtworkList != null && !indexArtworkList.isEmpty()) {
			for (AsIndexArtwork indexArtwork : indexArtworkList) {
				AsIndexArtworkDto indexArtworkDto = new AsIndexArtworkDto();
				indexArtworkDto.create(indexArtwork);
				indexArtworkDtoList.add(indexArtworkDto);
			}
		}
		return indexArtworkDtoList;
	}

	@Override
	public AsIndexWorksDto loadWorkOverview(Integer worksId, Integer memberId, String encryptKey, Integer type) {
		// 查询作品集概况
		if (log.isDebugEnabled()) {
			log.debug("loadWorkOverview-->worksId:{},memberId:{},encryptKey:{},type:{}", worksId, memberId, encryptKey,
					type);
		}
		AsWorks works = this.atlasAdminService.queryWorks(memberId, encryptKey, worksId);
		if (log.isDebugEnabled()) {
			log.debug("loadWorkOverview-->works:{}", works);
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
		List<AsIndexWorks> indexWorksList = this.indexWorksMapper.selectByWorksIdTimeType(worksId, timeType, beginTime,
				endTime);

		AsIndexWorksDto indexWorksDto = this.calcWorksOverview(indexWorksList, timeType, worksId);
		return indexWorksDto;
	}

 

	private AsIndexWorksDto calcWorksOverview(List<AsIndexWorks> indexWorksList, String timeType,Integer worksId) {
		AsIndexWorksDto indexWorksDto = new AsIndexWorksDto();
		int pageViewCount = 0;
		int uniqueVisitorCount = 0;
		int averagDurationCount = 0;
		int viewRateCount = 0;
		int viewRate = 0;
		int averagDuration = 0;
		//访问人数统计
		List<StatisticalChart> uniqueVisitorList = new ArrayList<StatisticalChart>();
		//访问次数统计
		List<StatisticalChart> pageViewCountList = new ArrayList<StatisticalChart>();
		//平均浏览时长统计  (单位s)
		List<StatisticalChart> averagDurationList = new ArrayList<StatisticalChart>();
		//作品展示率统计
		List<StatisticalChart> viewRateList = new ArrayList<StatisticalChart>();
		if (indexWorksList != null && !indexWorksList.isEmpty()) {
			int overviewSize = indexWorksList.size();
			for (AsIndexWorks indexWorks : indexWorksList) {
				Integer pageView = indexWorks.getPageView();
				Integer uniqueVisitor = indexWorks.getUniqueVisitor();
				Integer duration = indexWorks.getAveragDuration();
				Integer rate = indexWorks.getViewRate();
				if (!TimeTypeEnum.ALL.getType().equals(timeType)) {
					this.statisticalChartList(uniqueVisitorList, pageViewCountList, averagDurationList, viewRateList,
							pageView, uniqueVisitor, duration, rate, indexWorks);
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
		if (TimeTypeEnum.ALL.getType().equals(timeType)) {
			indexWorksList = this.indexWorksMapper.selectAllByWorksIdTimeType(worksId, TimeTypeEnum.DAY.getType());
			if (indexWorksList != null && !indexWorksList.isEmpty()) {
				for (AsIndexWorks indexWorks : indexWorksList) {
					Integer pageView = indexWorks.getPageView();
					Integer uniqueVisitor = indexWorks.getUniqueVisitor();
					Integer duration = indexWorks.getAveragDuration();
					Integer rate = indexWorks.getViewRate();
					this.statisticalChartList(uniqueVisitorList, pageViewCountList, averagDurationList, viewRateList,
							pageView, uniqueVisitor, duration, rate, indexWorks);

				}
			}
		}
		indexWorksDto.create(pageViewCount, uniqueVisitorCount, averagDuration, viewRate, uniqueVisitorList,
				pageViewCountList, averagDurationList, viewRateList);
		return indexWorksDto;
	}


	/**
	 * 统计趋势图表list
	 */
	private void statisticalChartList(List<StatisticalChart> uniqueVisitorList,
			List<StatisticalChart> pageViewCountList, List<StatisticalChart> averagDurationList,
			List<StatisticalChart> viewRateList, Integer pageView, Integer uniqueVisitor, Integer duration,
			Integer rate, AsIndexWorks indexWorks) {

		//访问人数统计
		StatisticalChart uniqueVisitorSc = new StatisticalChart();
		uniqueVisitorSc.create(indexWorks, StringUtil.toString(uniqueVisitor));
		uniqueVisitorList.add(uniqueVisitorSc);
		
		//访问次数统计
		StatisticalChart pageViewCountSc = new StatisticalChart();
		pageViewCountSc.create(indexWorks, StringUtil.toString(pageView));
		pageViewCountList.add(pageViewCountSc);
		
		//平均浏览时长统计  (单位s)
		StatisticalChart averagDurationSc = new StatisticalChart();
		averagDurationSc.create(indexWorks, StringUtil.toString(duration));
		averagDurationList.add(averagDurationSc);
		
		//作品展示率统计
		StatisticalChart viewRateSc = new StatisticalChart();
		String viewRateStr = "0%";
		if (rate == null || rate.intValue() == 0) {
			viewRateStr = "0%";
		} else {
			viewRateStr = StringUtil.formatPercentage(CurrencyUtil.div(rate, 1000, 3));
		}
		viewRateSc.create(indexWorks, viewRateStr);
		viewRateList.add(viewRateSc);

	}

}
