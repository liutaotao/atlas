package com.yetoop.cloud.atlas.domain.persistence;

import com.yetoop.cloud.atlas.domain.AsIndexLog;

public interface AsIndexLogMapper {

    int insertSelective(AsIndexLog record);

    AsIndexLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AsIndexLog record);


}