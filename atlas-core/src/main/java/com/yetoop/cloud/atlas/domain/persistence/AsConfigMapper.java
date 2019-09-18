package com.yetoop.cloud.atlas.domain.persistence;

import com.yetoop.cloud.atlas.domain.AsConfig;

public interface AsConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AsConfig record);

    int insertSelective(AsConfig record);

    AsConfig selectByPrimaryKey(Integer id);
    
    AsConfig selectByCode(String code);

    int updateByPrimaryKeySelective(AsConfig record);

    int updateByPrimaryKeyWithBLOBs(AsConfig record);

    int updateByPrimaryKey(AsConfig record);
}