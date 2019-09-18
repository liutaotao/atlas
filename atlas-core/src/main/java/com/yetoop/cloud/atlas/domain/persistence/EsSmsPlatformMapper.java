package com.yetoop.cloud.atlas.domain.persistence;

import com.yetoop.cloud.atlas.domain.EsSmsPlatform;

public interface EsSmsPlatformMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EsSmsPlatform record);

    int insertSelective(EsSmsPlatform record);

    EsSmsPlatform selectByPrimaryKey(Integer id);
    
    EsSmsPlatform queryOpenSmsPlatform();

    int updateByPrimaryKeySelective(EsSmsPlatform record);

    int updateByPrimaryKeyWithBLOBs(EsSmsPlatform record);

    int updateByPrimaryKey(EsSmsPlatform record);
}