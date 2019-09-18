package com.yetoop.cloud.atlas.domain.persistence;

import com.yetoop.cloud.atlas.domain.AsArtworkHis;

public interface AsArtworkHisMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AsArtworkHis record);

    int insertSelective(AsArtworkHis record);

    AsArtworkHis selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AsArtworkHis record);

    int updateByPrimaryKey(AsArtworkHis record);
}