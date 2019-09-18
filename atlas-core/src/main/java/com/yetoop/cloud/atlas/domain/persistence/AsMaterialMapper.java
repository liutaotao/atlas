package com.yetoop.cloud.atlas.domain.persistence;

import java.util.List;

import com.yetoop.cloud.atlas.domain.AsMaterial;

public interface AsMaterialMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AsMaterial record);

    int insertSelective(AsMaterial record);

    AsMaterial selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AsMaterial record);

    int updateByPrimaryKey(AsMaterial record);
    
    List<AsMaterial> selectAll();
}