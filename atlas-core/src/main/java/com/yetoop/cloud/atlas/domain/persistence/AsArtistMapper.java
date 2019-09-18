package com.yetoop.cloud.atlas.domain.persistence;

import com.yetoop.cloud.atlas.domain.AsArtist;

public interface AsArtistMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AsArtist record);

    int insertSelective(AsArtist record);

    AsArtist selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AsArtist record);

    int updateByPrimaryKey(AsArtist record);
}