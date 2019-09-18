package com.yetoop.cloud.atlas.service;

import java.util.List;

import com.yetoop.cloud.atlas.domain.AsIndexMemberWorks;
import com.yetoop.cloud.atlas.domain.AsIndexWorks;
import com.yetoop.cloud.atlas.domain.AsTrack;
import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.TimeTypeEnum;
import com.yetoop.cloud.atlas.mq.MemberViewWorks;

public interface AtalasIndexService {

	/**
	 * 采集AsTrack数据，形成as_index_member_artwork
	 * 
	 * @param asTrack
	 */
	public void indexAsTrack(AsTrack asTrack);

	/**
	 * 查询尚未采集的AsTrack数据，每次返回500条
	 * 
	 * @return
	 */
	public List<AsTrack> findAsTrack4Index();

	/**
	 * 查询尚未采集处理的as_index_member_works
	 * 
	 * @param timeTypeEnum
	 * @return
	 */
	public List<AsIndexMemberWorks> findAsIndexMemberWorks4Index(TimeTypeEnum timeTypeEnum);

	/**
	 * 
	 * 查询尚未采集处理的as_index_works
	 * 
	 * @param timeTypeEnum
	 * @return
	 */
	public List<AsIndexWorks> findAsIndexWorks4Index(TimeTypeEnum timeTypeEnum);

	/**
	 * 
	 * 采集as_index_works：
	 * <P>
	 * HOUR汇总成DAY、ALL
	 * <P>
	 * DAY汇总成MONTH
	 * <P>
	 * MONTH汇总成YEAR
	 * <P>
	 * 
	 * @param asIndexWorks
	 */
	public void indexAsWorks(AsIndexWorks asIndexWorks);

	/**
	 * 采集as_index_member_works：
	 * <P>
	 * HOUR汇总成DAY、ALL以及as_index_works中HOUR
	 * <P>
	 * DAY汇总成MONTH
	 * <P>
	 * MONTH汇总成YEAR
	 * 
	 * @param asIndexMemberWorks
	 */
	public void indexMemberWorks(AsIndexMemberWorks asIndexMemberWorks);

	/**
	 * 记录MemberViewWorks（写入as_track）
	 * 
	 * @param memberViewWorks
	 */
	public void logMemberViewWorks(MemberViewWorks memberViewWorks);
}
