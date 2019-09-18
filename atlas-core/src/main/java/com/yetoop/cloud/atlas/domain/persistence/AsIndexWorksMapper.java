package com.yetoop.cloud.atlas.domain.persistence;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yetoop.cloud.atlas.domain.AsIndexMemberWorks;
import com.yetoop.cloud.atlas.domain.AsIndexWorks;

public interface AsIndexWorksMapper {

	int insertSelective(AsIndexWorks record);

	AsIndexWorks selectByWorksIdTimeId(@Param("worksId") Integer worksId, @Param("timeType") String timeType,
			@Param("timeId") Integer timeId);

	List<AsIndexWorks> selectAllByWorksIdTimeType(@Param("worksId") Integer worksId,
			@Param("timeType") String timeType);

	List<AsIndexWorks> selectByWorksIdTimeType(@Param("worksId") Integer worksId, @Param("timeType") String timeType,
			@Param("beginTime") Integer beginTime, @Param("endTime") Integer endTime);

	AsIndexWorks selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(AsIndexWorks record);

	
	List<AsIndexWorks> selectUnindexLimit(@Param("beginDate") Date beginDate, @Param("timeType") String timeType );

}