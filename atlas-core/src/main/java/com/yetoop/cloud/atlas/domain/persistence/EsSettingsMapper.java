package com.yetoop.cloud.atlas.domain.persistence;

import com.yetoop.cloud.atlas.domain.EsSettings;

public interface EsSettingsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EsSettings record);

    int insertSelective(EsSettings record);

    EsSettings selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EsSettings record);

    int updateByPrimaryKey(EsSettings record);
}