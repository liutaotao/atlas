package com.yetoop.cloud.atlas.domain.persistence;

import com.yetoop.cloud.atlas.domain.AsWorksHis;

public interface AsWorksHisMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AsWorksHis record);

    int insertSelective(AsWorksHis record);

    AsWorksHis selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AsWorksHis record);

    int updateByPrimaryKey(AsWorksHis record);
}