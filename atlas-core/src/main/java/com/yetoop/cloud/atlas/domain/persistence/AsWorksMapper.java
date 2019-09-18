package com.yetoop.cloud.atlas.domain.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yetoop.cloud.atlas.domain.AsWorks;

public interface AsWorksMapper {

	int insertSelective(AsWorks record);

	AsWorks selectByPrimaryKey(Integer id);

	AsWorks selectWorksByState(@Param("worksId") Integer worksId, @Param("state") String state);

	int updateByPrimaryKeySelective(AsWorks record);

	List<AsWorks> selectByPriviewCode(String priviewCode);

	List<AsWorks> selectByViewCode(String viewCode);

	List<AsWorks> selectTrashWorkByGalleryId(Integer galleryId);

	/**
	 * 查询作品集列表
	 * 
	 * @param galleryId
	 *            画廊ID
	 * @param favoriteFlag
	 *            Y为我的桌面
	 * @param orderLastDay
	 *            1(lastUpdate 倒序排列) 0(seq 倒序排列)
	 * @return
	 */
	List<AsWorks> selectByGalleryIdOrderFavorite(@Param("galleryId") Integer galleryId,
			@Param("favoriteFlag") String favoriteFlag, @Param("orderLastDay") String orderLastDay);

	/**
	 * 查询作品集列表(seq 倒序排列) galleryId 画廊ID
	 */
	List<AsWorks> selectByGalleryIdOrderSeq(Integer galleryId);

	/**
	 * 查询作品集列表(lastUpdate 倒序排列) galleryId 画廊ID
	 */
	List<AsWorks> selectByGalleryIdOrderLastUpdate(Integer galleryId);
}