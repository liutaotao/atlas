package com.yetoop.cloud.atlas.job;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yetoop.cloud.atlas.common.ListUtil;
import com.yetoop.cloud.atlas.domain.AsIndexMemberWorks;
import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.TimeTypeEnum;
import com.yetoop.cloud.atlas.service.AtalasIndexService;

public class IndexMemberWorksTask implements Serializable, Callable<String> {
	private static final Logger log = LoggerFactory.getLogger(IndexMemberWorksTask.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -7143049358930805415L;

	private AtalasIndexService atalasIndexService;

	private TimeTypeEnum timeTypeEnum;

	@Override
	public String call() throws Exception {
		boolean hasMore = true;
		int indexCount = 0;
		while (hasMore) {
			List<AsIndexMemberWorks> list = this.atalasIndexService.findAsIndexMemberWorks4Index(timeTypeEnum);
			log.info("findAsIndexMemberWorks4Index:" + list.size());

			if (ListUtil.isEmpty(list)) {
				hasMore = false;
			} else {
				for (AsIndexMemberWorks asIndexMemberWorks : list) {
					try {
						this.atalasIndexService.indexMemberWorks(asIndexMemberWorks);
						indexCount++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println("indexMemberWorks done." + indexCount);
		return String.valueOf(indexCount);
	}

	public void prepare(AtalasIndexService atalasIndexService, TimeTypeEnum timeTypeEnum) {
		this.atalasIndexService = atalasIndexService;
		this.timeTypeEnum = timeTypeEnum;
	}

}
