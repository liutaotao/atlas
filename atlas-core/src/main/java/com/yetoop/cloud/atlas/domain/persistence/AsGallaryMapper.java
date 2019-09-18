package com.yetoop.cloud.atlas.domain.persistence;

import com.yetoop.cloud.atlas.domain.AsGallary;

public interface AsGallaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AsGallary record);

    int insertSelective(AsGallary record);

    AsGallary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AsGallary record);

    int updateByPrimaryKey(AsGallary record);
}