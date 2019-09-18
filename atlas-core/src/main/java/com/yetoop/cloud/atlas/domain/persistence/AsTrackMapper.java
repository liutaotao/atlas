package com.yetoop.cloud.atlas.domain.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yetoop.cloud.atlas.domain.AsTrack;

public interface AsTrackMapper {
	
	Integer selectUnindexCount();
	
	List<AsTrack> selectUnindexLimit(String hourStr);

	int insertSelective(AsTrack record);

	AsTrack selectByPrimaryKey(Integer id);

	AsTrack selectByTransactionId(String transactionId);

	AsTrack selectByMemberSession(@Param("sessionId") String sessionId, @Param("memberId") String memberId,
			@Param("worksId") String worksId);

	int updateByPrimaryKeySelective(AsTrack record);

}