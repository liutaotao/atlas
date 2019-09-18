package com.yetoop.cloud.atlas.domain.dao;

import java.util.Map;

import com.yetoop.cloud.atlas.domain.AsArtwork;
import com.zhenyi.common.pager.PagedList;

public interface AsArtworkPageDao {

	public PagedList<AsArtwork> selectArtworkPageByWorkId(Map<String,String> map);
	
}
