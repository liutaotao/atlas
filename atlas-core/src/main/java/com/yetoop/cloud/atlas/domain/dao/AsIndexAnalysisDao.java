package com.yetoop.cloud.atlas.domain.dao;

import com.yetoop.cloud.atlas.domain.AsIndexMemberArtwork;
import com.yetoop.cloud.atlas.domain.AsIndexMemberWorks;
import com.zhenyi.common.pager.PagedList;

public interface AsIndexAnalysisDao {
	
	public PagedList<AsIndexMemberArtwork> selectArtworkMemberByArtworkIdNickname(Integer worksId, Integer artworkId,
			String keywords, String currentPageNo, String pageSize);
	
	public PagedList<AsIndexMemberWorks> selectMemberWorksByWorksIdNickname(Integer worksId, String keywords,
			String currentPageNo, String pageSize);

}
