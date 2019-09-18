package com.yetoop.cloud.atlas.domain.persistence;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yetoop.cloud.atlas.domain.AsIndexMemberWorks;

public interface AsIndexMemberWorksMapper {

	int insertSelective(AsIndexMemberWorks record);

	AsIndexMemberWorks selectByPrimaryKey(Integer id);
	
	List<AsIndexMemberWorks> selectByMemberIdTimeType(@Param("worksId") Integer worksId,
			@Param("memberId") Integer memberId, @Param("timeType") String timeType,
			@Param("beginTime") Integer beginTime, @Param("endTime") Integer endTime);
	
	AsIndexMemberWorks selectByMemberIdTimeId(@Param("worksId") Integer worksId,
			@Param("memberId") Integer memberId, @Param("timeId") Integer timeId,@Param("timeType") String timeType);
	
	List<AsIndexMemberWorks> selectAllByMemberIdTimeType(@Param("worksId") Integer worksId,
			@Param("memberId") Integer memberId, @Param("timeType") String timeType);

	List<AsIndexMemberWorks> selectByWorksId(@Param("nickname") String nickname, @Param("worksId") Integer worksId);
	
	List<AsIndexMemberWorks> selectUnindexLimit(@Param("beginDate") Date beginDate, @Param("timeType") String timeType );

	int updateByPrimaryKeySelective(AsIndexMemberWorks record);

}