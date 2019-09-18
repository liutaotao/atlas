package com.yetoop.cloud.atlas.domain.dao;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.yetoop.cloud.atlas.domain.AsWorks;
import com.zhenyi.common.pager.PagedList;

@Service("asWorksPageDao")
public class AsWorksPageDaoImpl extends BasePageDao implements
		AsWorksPageDao {
	
	@Override
	public PagedList<AsWorks> selectWorksByGalleryIdOrderFavorite(Map<String, String> map) {
		String namespace = "com.yetoop.cloud.atlas.domain.persistence.AsWorksMapper.selectByGalleryIdOrderFavorite";
		return this.executeQuery(namespace, map);
	}

	@Override
	public PagedList<AsWorks> selectWorksByGalleryIdOrderSeq(Map<String, String> map) {
		String namespace = "com.yetoop.cloud.atlas.domain.persistence.AsWorksMapper.selectByGalleryIdOrderSeq";
		return this.executeQuery(namespace, map);
	}

	@Override
	public PagedList<AsWorks> selectWorksByGalleryIdOrderLastUpdate(Map<String, String> map) {
		String namespace = "com.yetoop.cloud.atlas.domain.persistence.AsWorksMapper.selectByGalleryIdOrderSeq";
		return this.executeQuery(namespace, map);
	}

}
