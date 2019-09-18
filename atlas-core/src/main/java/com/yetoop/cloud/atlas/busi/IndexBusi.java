package com.yetoop.cloud.atlas.busi;

import java.util.Date;

import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.TimeTypeEnum;

public interface IndexBusi {

	public String getTimeType();
	
	public TimeTypeEnum getNextTimeTypeEnum();
	
	public Integer getTimeId();
	
	public Date getBeginDate() ;
	
	public Date getEndDate();
	
	public String getIndexTableName();
	
	public Integer getIndexId();
	
	public Integer getWorksId();
}
