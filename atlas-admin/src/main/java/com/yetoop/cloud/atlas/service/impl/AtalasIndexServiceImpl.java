package com.yetoop.cloud.atlas.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yetoop.cloud.atlas.busi.IndexBusi;
import com.yetoop.cloud.atlas.common.DateUtil;
import com.yetoop.cloud.atlas.domain.AsIndexLog;
import com.yetoop.cloud.atlas.domain.AsIndexMemberWorks;
import com.yetoop.cloud.atlas.domain.AsIndexWorks;
import com.yetoop.cloud.atlas.domain.AsTrack;
import com.yetoop.cloud.atlas.domain.AsWorks;
import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.TimeTypeEnum;
import com.yetoop.cloud.atlas.domain.persistence.AsIndexLogMapper;
import com.yetoop.cloud.atlas.domain.persistence.AsIndexMemberWorksMapper;
import com.yetoop.cloud.atlas.domain.persistence.AsIndexWorksMapper;
import com.yetoop.cloud.atlas.domain.persistence.AsTrackMapper;
import com.yetoop.cloud.atlas.domain.persistence.AsWorksMapper;
import com.yetoop.cloud.atlas.mq.MemberViewWorks;
import com.yetoop.cloud.atlas.mq.MemberViewWorks.TypeCodeEnum;
import com.yetoop.cloud.atlas.service.AtalasIndexService;

@Service("atalasIndexService")
public class AtalasIndexServiceImpl implements AtalasIndexService {
	@Override
	public List<AsIndexMemberWorks> findAsIndexMemberWorks4Index(TimeTypeEnum timeTypeEnum) {
		if (log.isDebugEnabled()) {
			log.debug("findAsIndexMemberWorks4Index:{}", timeTypeEnum);
		}
		Date now = new Date();
		Date beginDate = null;

		if (timeTypeEnum.equals(TimeTypeEnum.HOUR)) {
			beginDate = DateUtil.trunHour(now);
		}
		if (timeTypeEnum.equals(TimeTypeEnum.DAY)) {
			beginDate = DateUtil.trunDay(now);
		}
		if (timeTypeEnum.equals(TimeTypeEnum.MONTH)) {
			beginDate = DateUtil.trunMonth(now);
		}
		if (log.isDebugEnabled()) {
			log.debug("beginDate:{}", beginDate);
		}
		List<AsIndexMemberWorks> result = this.asIndexMemberWorksMapper.selectUnindexLimit(beginDate,
				timeTypeEnum.getType());
		if (log.isDebugEnabled()) {
			log.debug("findAsIndexMemberWorks4Index:{}", result.size());
		}
		return result;
	}

	@Override
	public List<AsIndexWorks> findAsIndexWorks4Index(TimeTypeEnum timeTypeEnum) {
		if (log.isDebugEnabled()) {
			log.debug("findAsIndexWorks4Index:{}", timeTypeEnum);
		}
		Date now = new Date();
		Date beginDate = null;

		if (timeTypeEnum.equals(TimeTypeEnum.HOUR)) {
			beginDate = DateUtil.trunHour(now);
		}
		if (timeTypeEnum.equals(TimeTypeEnum.DAY)) {
			beginDate = DateUtil.trunDay(now);
		}
		if (timeTypeEnum.equals(TimeTypeEnum.MONTH)) {
			beginDate = DateUtil.trunMonth(now);
		}
		if (log.isDebugEnabled()) {
			log.debug("beginDate:{}", beginDate);
		}
		List<AsIndexWorks> result = this.asIndexWorksMapper.selectUnindexLimit(beginDate, timeTypeEnum.getType());
		if (log.isDebugEnabled()) {
			log.debug("findAsIndexMemberWorks4Index:{}", result.size());
		}
		return result;

	}

	@Override
	public void indexAsWorks(AsIndexWorks asIndexWorks) {
		if (log.isDebugEnabled()) {
			log.debug("asIndexWorks:{}", asIndexWorks);
		}
		this.indexAsIndexWorks2AsIndexWorks(asIndexWorks);
	}

	@Override
	public void indexMemberWorks(AsIndexMemberWorks asIndexMemberWorks) {
		if (log.isDebugEnabled()) {
			log.debug("asIndexMemberWorks:{}", asIndexMemberWorks);
		}
		this.indexMemberWorks2AsIndexWorks(asIndexMemberWorks);
		this.indexMemberWorks2AsIndexMemberWorks(asIndexMemberWorks);

	}

	private void indexAsIndexWorks2AsIndexWorks(AsIndexWorks asIndexWorks) {
		TimeTypeEnum nextTimeTypeEnum = asIndexWorks.getNextTimeTypeEnum();
		if (nextTimeTypeEnum != null) {
			Integer timeId = asIndexWorks.getTimeId() / 100;
			if (log.isDebugEnabled()) {
				log.debug("timeId:{}", timeId);
			}

			// to next
			AsIndexWorks targetAsIndexWorks = this.asIndexWorksMapper.selectByWorksIdTimeId(asIndexWorks.getWorksId(),
					nextTimeTypeEnum.getType(), timeId);
			if (log.isDebugEnabled()) {
				log.debug("targetAsIndexWorks:{}", targetAsIndexWorks);
			}

			if (targetAsIndexWorks == null) {
				targetAsIndexWorks = new AsIndexWorks();
				targetAsIndexWorks.create(asIndexWorks, nextTimeTypeEnum);
				this.asIndexWorksMapper.insertSelective(targetAsIndexWorks);

			} else {
				targetAsIndexWorks.append(asIndexWorks);
				this.asIndexWorksMapper.updateByPrimaryKeySelective(targetAsIndexWorks);
			}
			if (log.isDebugEnabled()) {
				log.debug("targetAsIndexWorks:{}", targetAsIndexWorks);
			}
			this.logIndex(asIndexWorks, targetAsIndexWorks);

			// to all
			AsIndexWorks allAsIndexWorks = this.asIndexWorksMapper.selectByWorksIdTimeId(asIndexWorks.getWorksId(),
					TimeTypeEnum.ALL.getType(), 0);
			if (log.isDebugEnabled()) {
				log.debug("allAsIndexWorks:{}", allAsIndexWorks);
			}

			if (allAsIndexWorks == null) {
				allAsIndexWorks = new AsIndexWorks();
				allAsIndexWorks.create(asIndexWorks, nextTimeTypeEnum);
				this.asIndexWorksMapper.insertSelective(allAsIndexWorks);

			} else {
				allAsIndexWorks.append(asIndexWorks);
				this.asIndexWorksMapper.updateByPrimaryKeySelective(allAsIndexWorks);
			}
			if (log.isDebugEnabled()) {
				log.debug("allAsIndexWorks:{}", allAsIndexWorks);
			}
			this.logIndex(asIndexWorks, allAsIndexWorks);
		}

	}

	private void indexMemberWorks2AsIndexMemberWorks(AsIndexMemberWorks asIndexMemberWorks) {
		TimeTypeEnum nextTimeTypeEnum = asIndexMemberWorks.getNextTimeTypeEnum();
		if (nextTimeTypeEnum != null) {
			Integer timeId = asIndexMemberWorks.getTimeId() / 100;
			if (log.isDebugEnabled()) {
				log.debug("timeId:{}", timeId);
			}

			// to next
			AsIndexMemberWorks targetAsIndexMemberWorks = this.asIndexMemberWorksMapper.selectByMemberIdTimeId(
					asIndexMemberWorks.getWorksId(), asIndexMemberWorks.getMemberId(), timeId,
					nextTimeTypeEnum.getType());
			if (log.isDebugEnabled()) {
				log.debug("targetAsIndexMemberWorks:{}", targetAsIndexMemberWorks);
			}

			if (targetAsIndexMemberWorks == null) {
				targetAsIndexMemberWorks = new AsIndexMemberWorks();
				targetAsIndexMemberWorks.create(asIndexMemberWorks, nextTimeTypeEnum);
				this.asIndexMemberWorksMapper.insertSelective(targetAsIndexMemberWorks);

			} else {
				targetAsIndexMemberWorks.append(asIndexMemberWorks);
				this.asIndexMemberWorksMapper.updateByPrimaryKeySelective(targetAsIndexMemberWorks);
			}
			if (log.isDebugEnabled()) {
				log.debug("targetAsIndexMemberWorks:{}", targetAsIndexMemberWorks);
			}
			this.logIndex(asIndexMemberWorks, targetAsIndexMemberWorks);

			// to all
			AsIndexMemberWorks allAsIndexMemberWorks = this.asIndexMemberWorksMapper.selectByMemberIdTimeId(
					asIndexMemberWorks.getWorksId(), asIndexMemberWorks.getMemberId(), 0, TimeTypeEnum.ALL.getType());
			if (log.isDebugEnabled()) {
				log.debug("allAsIndexMemberWorks:{}", allAsIndexMemberWorks);
			}

			if (allAsIndexMemberWorks == null) {
				allAsIndexMemberWorks = new AsIndexMemberWorks();
				allAsIndexMemberWorks.create(asIndexMemberWorks, TimeTypeEnum.ALL);
				this.asIndexMemberWorksMapper.insertSelective(allAsIndexMemberWorks);

			} else {
				allAsIndexMemberWorks.append(asIndexMemberWorks);
				this.asIndexMemberWorksMapper.updateByPrimaryKeySelective(allAsIndexMemberWorks);
			}
			if (log.isDebugEnabled()) {
				log.debug("allAsIndexMemberWorks:{}", allAsIndexMemberWorks);
			}
			this.logIndex(asIndexMemberWorks, allAsIndexMemberWorks);
		}
	}

	/*
	 * 每小时的AsIndexMemberWorks数据 采集生成AsIndexWorks（每小时）
	 */
	private void indexMemberWorks2AsIndexWorks(AsIndexMemberWorks asIndexMemberWorks) {
		if (TimeTypeEnum.HOUR.getType().equals(asIndexMemberWorks.getTimeType())) {
			AsIndexWorks asIndexWorks = this.asIndexWorksMapper.selectByWorksIdTimeId(asIndexMemberWorks.getWorksId(),
					asIndexMemberWorks.getTimeType(), asIndexMemberWorks.getTimeId());
			if (log.isDebugEnabled()) {
				log.debug("asIndexWorks:{}", asIndexWorks);
			}
			if (asIndexWorks == null) {
				asIndexWorks = new AsIndexWorks();
				asIndexWorks.create(asIndexMemberWorks);
				this.asIndexWorksMapper.insertSelective(asIndexWorks);

				if (log.isDebugEnabled()) {
					log.debug("asIndexWorks.new:{}", asIndexWorks);
				}
			} else {
				asIndexWorks.append(asIndexMemberWorks);
				this.asIndexWorksMapper.updateByPrimaryKeySelective(asIndexWorks);

				if (log.isDebugEnabled()) {
					log.debug("asIndexWorks.append:{}", asIndexWorks);
				}
			}
			this.logIndex(asIndexMemberWorks, asIndexWorks);

		}

	}

	private void logIndex(IndexBusi from, IndexBusi to) {
		AsIndexLog asIndexLog = new AsIndexLog();
		asIndexLog.logIndex(from, to);
		this.asIndexLogMapper.insertSelective(asIndexLog);
	}

	public void indexAsTrack(AsTrack asTrack) {
		if (log.isDebugEnabled()) {
			log.debug("indexAsTrack-->asTrack:{}", asTrack);
		}

		AsWorks asWorks = this.asWorksMapper.selectByPrimaryKey(asTrack.getWorksId());

		AsIndexMemberWorks asIndexMemberWorks = this.asIndexMemberWorksMapper.selectByMemberIdTimeId(
				asTrack.getWorksId(), asTrack.getMemberId(), asTrack.getTimeId(), TimeTypeEnum.HOUR.getType());
		if (log.isDebugEnabled()) {
			log.debug("asIndexMemberWorks:{}", asIndexMemberWorks);
		}
		if (asIndexMemberWorks == null) {
			asIndexMemberWorks = new AsIndexMemberWorks();
			asIndexMemberWorks.create(asTrack, asWorks);
			this.asIndexMemberWorksMapper.insertSelective(asIndexMemberWorks);
		} else {
			asIndexMemberWorks.append(asTrack, asWorks);
			this.asIndexMemberWorksMapper.updateByPrimaryKeySelective(asIndexMemberWorks);
		}
		if (log.isDebugEnabled()) {
			log.debug("indexAsTrack-->asIndexMemberWorks:{}", asIndexMemberWorks);
		}

		this.logIndex(asTrack, asIndexMemberWorks);

	}

	public List<AsTrack> findAsTrack4Index() {
		String hourStr = DateUtil.toString(new Date(), "yyyyMMddHH");

		return this.asTrackMapper.selectUnindexLimit(hourStr);
	}

	@Override
	public void logMemberViewWorks(MemberViewWorks memberViewWorks) {
		if (log.isDebugEnabled()) {
			log.debug("logMemberViewWorks-->memberViewWorks:{}", memberViewWorks);
		}

		AsTrack asTrack = this.loadAsTrack(memberViewWorks);
		if (log.isDebugEnabled()) {
			log.debug("logMemberViewWorks-->asTrack:{}", asTrack);
		}
	}

	private AsTrack loadAsTrack(MemberViewWorks memberViewWorks) {
		AsTrack asTrack = this.asTrackMapper.selectByTransactionId(memberViewWorks.getTransactionId());
		if (log.isDebugEnabled()) {
			log.debug("asTrack.selectByTransactionId:{}", asTrack);
		}

		if (asTrack == null) {
			// 只有新打开页面才记录，如果数据丢失，后续不记录
			if (TypeCodeEnum.BEGIN.toString().equals(memberViewWorks.getTypeCode())) {
				asTrack = new AsTrack();
				asTrack.create(memberViewWorks);

				this.asTrackMapper.insertSelective(asTrack);
				if (log.isDebugEnabled()) {
					log.debug("asTrack.new:{}", asTrack);
				}
			}

		} else {
			asTrack.append(memberViewWorks);
			this.asTrackMapper.updateByPrimaryKeySelective(asTrack);
			if (log.isDebugEnabled()) {
				log.debug("asTrack.append:{}", asTrack);
			}
		}

		return asTrack;
	}

	@Autowired
	private AsTrackMapper asTrackMapper;

	@Autowired
	private AsWorksMapper asWorksMapper;

	@Autowired
	private AsIndexLogMapper asIndexLogMapper;

	@Autowired
	private AsIndexMemberWorksMapper asIndexMemberWorksMapper;

	@Autowired
	private AsIndexWorksMapper asIndexWorksMapper;

	private static final Logger log = LoggerFactory.getLogger(AtalasIndexServiceImpl.class);

}
