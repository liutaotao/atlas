package com.yetoop.cloud.atlas.domain.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yetoop.cloud.atlas.domain.AsArtwork;
import com.yetoop.cloud.atlas.domain.AsArtwork2WoksKey;

public interface AsArtwork2WoksMapper {
	
	List<AsArtwork> selectArtworksByWorksId(Integer worksId);

	AsArtwork selectByWorksIdArtworkId(@Param("artworkId") Integer artworkId, @Param("worksId") Integer worksId);
	
	int selectArtworkCountByWorksId(Integer worksId);

	int selectMaxSeq(Integer worksId);
	
    int deleteByPrimaryKey(AsArtwork2WoksKey key);

    int insert(AsArtwork2WoksKey record);

    int insertSelective(AsArtwork2WoksKey record);
}