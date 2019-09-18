package com.yetoop.cloud.atlas.job;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yetoop.cloud.atlas.common.ListUtil;
import com.yetoop.cloud.atlas.domain.AsTrack;
import com.yetoop.cloud.atlas.service.AtalasIndexService;

public class IndexAsTrackHourTask implements Serializable, Callable<String> {
	private static final Logger log = LoggerFactory.getLogger(IndexAsTrackHourTask.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -8755720102517500923L;

	private AtalasIndexService atalasIndexService;

	public void prepare(AtalasIndexService atalasIndexService) {
		this.atalasIndexService = atalasIndexService;
	}

	@Override
	public String call() throws Exception {
		boolean hasMore = true;
		int indexCount = 0;
		while (hasMore) {
			List<AsTrack> list = this.atalasIndexService.findAsTrack4Index();
			log.info("findAsTrack4Index:" + list.size());

			if (ListUtil.isEmpty(list)) {
				hasMore = false;
			} else {
				for (AsTrack asTrack : list) {
					try {
						this.atalasIndexService.indexAsTrack(asTrack);
						indexCount++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println("indexAsTrack done." + indexCount);
		return String.valueOf(indexCount);
	}

}
