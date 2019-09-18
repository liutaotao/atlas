package com.yetoop.cloud.atlas.service;

import java.util.List;

import com.yetoop.cloud.atlas.dto.AsIndexArtworkDto;
import com.yetoop.cloud.atlas.dto.AsIndexMemberWorksDto;
import com.yetoop.cloud.atlas.dto.AsIndexWorksDto;
import com.zhenyi.common.pager.PagedList;

public interface AtalasIndexWorksService {

	
	/**
	 * 获取全部对应作品集的用户喜好分析list
	 * @param worksId
	 * @param memberId
	 * @param encryptKey
	 * @param keywords
	 * @return
	 */
	public List<AsIndexMemberWorksDto> loadAllMemberWorksAnalysis(Integer worksId, Integer memberId, String encryptKey,
			String keywords);
	
	/**
	 * 分页获取对应作品集的用户喜好分析list
	 * @param worksId
	 * @param memberId
	 * @param encryptKey
	 * @param keywords
	 * @param currentPageNo
	 * @param pageSize
	 * @return
	 */
	public PagedList<AsIndexMemberWorksDto> loadMemberWorksAnalysis(Integer worksId, Integer memberId, String encryptKey,
			String keywords, Integer currentPageNo, Integer pageSize);

	/**
	 * 获取对应作品集的作品喜好分析list
	 * @param memberId
	 * @param encryptKey
	 * @param worksId 作品集Id
	 * @return
	 */
	public List<AsIndexArtworkDto> loadArtworkAnalysis(Integer memberId, String encryptKey, Integer worksId);
	
	/**
	 * 查询作品集概况
	 * @param worksId
	 * @param memberId
	 * @param encryptKey
	 * @param type 0为全部,1为今日,2为昨日,3为最近一周,4为最近一月
	 * @return
	 */
	public AsIndexWorksDto loadWorkOverview(Integer worksId, Integer memberId, String encryptKey, Integer type);

}
