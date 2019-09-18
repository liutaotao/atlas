package com.yetoop.cloud.atlas.domain.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yetoop.cloud.atlas.domain.AsIndexMemberArtwork;

public interface AsIndexMemberArtworkMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(AsIndexMemberArtwork record);

	int insertSelective(AsIndexMemberArtwork record);

	List<AsIndexMemberArtwork> selectByMemberId(@Param("worksId") Integer worksId,
			@Param("memberId") Integer memberId);

	List<AsIndexMemberArtwork> selectByArtworkId(@Param("nickname") String nickname, @Param("worksId") Integer worksId,
			@Param("artworkId") Integer artworkId);

	AsIndexMemberArtwork selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(AsIndexMemberArtwork record);

	int updateByPrimaryKey(AsIndexMemberArtwork record);
}