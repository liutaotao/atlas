package com.yetoop.cloud.atlas.domain.persistence;

import com.yetoop.cloud.atlas.domain.AsAdminTheme;

public interface AsAdminThemeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AsAdminTheme record);

    int insertSelective(AsAdminTheme record);

    AsAdminTheme selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AsAdminTheme record);

    int updateByPrimaryKey(AsAdminTheme record);
}