package com.yetoop.cloud.atlas.domain.persistence;

import com.yetoop.cloud.atlas.domain.AsSubject;

public interface AsSubjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AsSubject record);

    int insertSelective(AsSubject record);

    AsSubject selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AsSubject record);

    int updateByPrimaryKey(AsSubject record);
}