package com.yetoop.cloud.atlas.domain.persistence;

import com.yetoop.cloud.atlas.domain.AsArtwork;

public interface AsArtworkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AsArtwork record);

    int insertSelective(AsArtwork record);

    AsArtwork selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(AsArtwork record);

    int updateByPrimaryKey(AsArtwork record);
}