package com.yetoop.cloud.atlas.domain.dao;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.yetoop.cloud.atlas.domain.AsArtwork;
import com.zhenyi.common.pager.PagedList;

@Service("asArtworkPageDao")
public class AsArtworkPageDaoImpl extends BasePageDao implements
		AsArtworkPageDao {

	@Override
	public PagedList<AsArtwork> selectArtworkPageByWorkId(
			Map<String, String> map) {
		String namespace = "com.yetoop.cloud.atlas.domain.persistence.AsArtwork2WoksMapper.selectArtworksByWorksId";
		return this.executeQuery(namespace, map);
	}

	
	
}
