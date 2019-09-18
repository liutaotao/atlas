package com.yetoop.cloud.atlas.job;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yetoop.cloud.atlas.common.ListUtil;
import com.yetoop.cloud.atlas.domain.AsIndexMemberWorks;
import com.yetoop.cloud.atlas.domain.AsIndexWorks;
import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.TimeTypeEnum;
import com.yetoop.cloud.atlas.service.AtalasIndexService;

public class IndexWorksTask  implements Serializable, Callable<String> {
	private static final Logger log = LoggerFactory.getLogger(IndexWorksTask.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -1340683969205376425L;

	private AtalasIndexService atalasIndexService;

	private TimeTypeEnum timeTypeEnum;

	@Override
	public String call() throws Exception {
		boolean hasMore = true;
		int indexCount = 0;
		while (hasMore) {
			List<AsIndexWorks> list = this.atalasIndexService.findAsIndexWorks4Index(timeTypeEnum);
			log.info("findAsIndexMemberWorks4Index:" + list.size());

			if (ListUtil.isEmpty(list)) {
				hasMore = false;
			} else {
				for (AsIndexWorks asIndexWorks : list) {
					try {
						this.atalasIndexService.indexAsWorks(asIndexWorks);
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
