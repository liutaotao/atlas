package com.yetoop.cloud.atlas.service;

import java.util.List;
import java.util.Map;

import com.yetoop.cloud.atlas.dto.AsIndexMemberArtworkDto;
import com.zhenyi.common.pager.PagedList;

public interface AtalasIndexArtworkService {
	
	/**
	 * 获取对应用户的概况
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 * @param userId
	 * @param type
	 * @return
	 */
	public Map<String, Object> loadMemberOverview(Integer memberId, String encryptKey, Integer worksId, Integer userId,
			Integer type);
	
	/**
	 * 获取对应作品的概况
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 * @param artworkId
	 * @param type
	 * @return
	 */
	public Map<String, Object> loadArtworkOverview(Integer memberId, String encryptKey, Integer worksId,
			Integer artworkId, Integer type);
	
	/**
	 * 获取全部对应用户的作品喜好分析list
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 * @param userId
	 * @return
	 */
	public List<AsIndexMemberArtworkDto> loadMemberArtworkAnalysis(Integer memberId, String encryptKey, Integer worksId,
			Integer userId);
	
	/**
	 * 分页获取对应作品的用户喜好分析list
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 * @param artworkId
	 * @param keywords
	 * @param currentPageNo
	 * @param pageSize
	 * @return
	 */
	public PagedList<AsIndexMemberArtworkDto> loadArtworkMemberAnalysisPage(Integer memberId, String encryptKey, Integer worksId, Integer artworkId,
			String keywords, Integer currentPageNo, Integer pageSize);
	
	/**
	 * 获取全部对应作品的用户喜好分析list
	 * @param memberId
	 * @param encryptKey
	 * @param worksId
	 * @param artworkId
	 * @param keywords
	 * @return
	 */
	public List<AsIndexMemberArtworkDto> loadArtworkMemberAnalysis(Integer memberId, String encryptKey, Integer worksId,
			Integer artworkId, String keywords);
	
}
