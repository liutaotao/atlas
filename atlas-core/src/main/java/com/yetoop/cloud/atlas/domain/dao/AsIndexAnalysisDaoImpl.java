package com.yetoop.cloud.atlas.domain.dao;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.yetoop.cloud.atlas.common.StringUtil;
import com.yetoop.cloud.atlas.domain.AsIndexMemberArtwork;
import com.yetoop.cloud.atlas.domain.AsIndexMemberWorks;
import com.zhenyi.common.pager.PagedList;

@Service("asIndexAnalysisDao")
public class AsIndexAnalysisDaoImpl extends BasePageDao implements AsIndexAnalysisDao {

	@Override
	public PagedList<AsIndexMemberArtwork> selectArtworkMemberByArtworkIdNickname(Integer worksId, Integer artworkId,
			String keywords, String currentPageNo, String pageSize) {
		Map<String, String> map = this.generateQueryParamMap(currentPageNo, pageSize);
		if (StringUtil.isNullString(keywords)) {
			keywords = "";
		}
		map.put("nickname", keywords);
		map.put("worksId", StringUtil.toString(worksId));
		map.put("artworkId", StringUtil.toString(artworkId));
		String namespace = "com.yetoop.cloud.atlas.domain.persistence.AsIndexMemberArtworkMapper.selectByArtworkId";
		return this.executeQuery(namespace, map);
	}

	@Override
	public PagedList<AsIndexMemberWorks> selectMemberWorksByWorksIdNickname(Integer worksId, String keywords,
			String currentPageNo, String pageSize) {
		Map<String, String> map = this.generateQueryParamMap(currentPageNo, pageSize);
		if (StringUtil.isNullString(keywords)) {
			keywords = "";
		}
		map.put("nickname", keywords);
		map.put("worksId", StringUtil.toString(worksId));
		String namespace = "com.yetoop.cloud.atlas.domain.persistence.AsIndexMemberWorksMapper.selectByWorksId";
		return this.executeQuery(namespace, map);
	}

}
