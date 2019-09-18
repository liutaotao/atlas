package com.yetoop.cloud.atlas.domain.dao;

import java.util.Map;

import com.yetoop.cloud.atlas.domain.AsWorks;
import com.zhenyi.common.pager.PagedList;

public interface AsWorksPageDao {
	
	/**
	 * 查询作品集列表
	 * 	 根据 galleryId、favoriteFlag
	 * 	 根据 seq 倒序排列
	 * @param map
	 * @return
	 */
	public PagedList<AsWorks> selectWorksByGalleryIdOrderFavorite(Map<String,String> map);
	
	/**
	 * 查询作品集列表
	 * 	 根据 galleryId
	 * 	 根据 seq 倒序排列
	 * @param map
	 * @return
	 */
	public PagedList<AsWorks> selectWorksByGalleryIdOrderSeq(Map<String,String> map);
	/**
	 * 查询作品集列表
	 * 	 根据 galleryId
	 * 	 根据 last_update 倒序排列
	 * @param map
	 * @return
	 */
	public PagedList<AsWorks> selectWorksByGalleryIdOrderLastUpdate(Map<String, String> map);
}
