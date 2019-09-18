package com.yetoop.cloud.atlas.domain.persistence;

import com.yetoop.cloud.atlas.domain.AsFeedback;

public interface AsFeedbackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AsFeedback record);

    int insertSelective(AsFeedback record);

    AsFeedback selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AsFeedback record);

    int updateByPrimaryKeyWithBLOBs(AsFeedback record);

    int updateByPrimaryKey(AsFeedback record);
}