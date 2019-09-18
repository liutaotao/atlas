package com.yetoop.cloud.atlas.domain.persistence;

import com.yetoop.cloud.atlas.domain.AsGallery2MemberKey;

public interface AsGallery2MemberMapper {
    int deleteByPrimaryKey(AsGallery2MemberKey key);

    int insert(AsGallery2MemberKey record);

    int insertSelective(AsGallery2MemberKey record);
    
    AsGallery2MemberKey selectByMemberId(Integer memberId);
}