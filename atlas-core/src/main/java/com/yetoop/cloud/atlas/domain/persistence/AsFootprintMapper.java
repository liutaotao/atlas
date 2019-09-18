package com.yetoop.cloud.atlas.domain.persistence;

import com.yetoop.cloud.atlas.domain.AsFootprint;

public interface AsFootprintMapper {

    int insertSelective(AsFootprint record);

    AsFootprint selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AsFootprint record);

}