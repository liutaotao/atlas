package com.yetoop.cloud.atlas.domain.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yetoop.cloud.atlas.domain.AsIndexArtwork;

public interface AsIndexArtworkMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(AsIndexArtwork record);

	int insertSelective(AsIndexArtwork record);

	AsIndexArtwork selectByPrimaryKey(Integer id);
	
	List<AsIndexArtwork> selectByArtworkIdTimeType(@Param("worksId") Integer worksId,
			@Param("artworkId") Integer artworkId, @Param("timeType") String timeType,
			@Param("beginTime") Integer beginTime, @Param("endTime") Integer endTime);
	
	List<AsIndexArtwork> selectAllByArtworkIdTimeType(@Param("worksId") Integer worksId,
			@Param("artworkId") Integer artworkId, @Param("timeType") String timeType);

	List<AsIndexArtwork> selectByWorkId(Integer worksId);

	int updateByPrimaryKeySelective(AsIndexArtwork record);

	int updateByPrimaryKey(AsIndexArtwork record);
}