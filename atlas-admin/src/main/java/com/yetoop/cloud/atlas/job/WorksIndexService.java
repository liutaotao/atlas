package com.yetoop.cloud.atlas.job;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.yetoop.cloud.atlas.domain.enumType.EnumTypeEnum.TimeTypeEnum;
import com.yetoop.cloud.atlas.service.AtalasIndexService;

@Service("worksIndexService")
public class WorksIndexService {
	private static final Logger log = LoggerFactory.getLogger(WorksIndexService.class);

	@Autowired
	private ThreadPoolTaskExecutor poolTaskExecutor;

	@Autowired
	private AtalasIndexService atalasIndexService;

	@Value("${admin_pool_corePoolSize}")
	private String poolSize;

	@Scheduled(cron = "10 02 * * * ?")
	public void indexWorksHour() {

		log.info("indexHour begin..." + this);

		// for (int i = 1; i < 998; i = i + 100) {
		// IndexWorksHourTask indexWorksHourTask = new IndexWorksHourTask();
		// indexWorksHourTask.setBegin(i);
		// Future<String> s = poolTaskExecutor.submit(indexWorksHourTask);
		//
		// }

		IndexAsTrackHourTask indexWorksHourTask = new IndexAsTrackHourTask();
		indexWorksHourTask.prepare(atalasIndexService);
		Future<String> s = poolTaskExecutor.submit(indexWorksHourTask);

		try {
			log.info("indexHour done.resut:" + s.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}

	@Scheduled(cron = "10 08 * * * ?")
	public void indexMemberWorksHour() {
		log.info("indexMemberWorksHour begin..." + this);

		IndexMemberWorksTask task = new IndexMemberWorksTask();
		task.prepare(atalasIndexService, TimeTypeEnum.HOUR);
		Future<String> s = poolTaskExecutor.submit(task);

		try {
			log.info("indexMemberWorksHour done.resut:" + s.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "10 23 00 * * ?")
	public void indexMemberWorksDay() {
		log.info("indexMemberWorksDay begin..." + this);

		IndexMemberWorksTask task = new IndexMemberWorksTask();
		task.prepare(atalasIndexService, TimeTypeEnum.DAY);
		Future<String> s = poolTaskExecutor.submit(task);

		try {
			log.info("indexMemberWorksDay done.resut:" + s.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}

	@Scheduled(cron = "10 23 00 01 * ?")
	public void indexMemberWorksMonth() {

		log.info("indexMemberWorksMonth begin..." + this);

		IndexMemberWorksTask task = new IndexMemberWorksTask();
		task.prepare(atalasIndexService, TimeTypeEnum.MONTH);
		Future<String> s = poolTaskExecutor.submit(task);

		try {
			log.info("indexMemberWorksMonth done.resut:" + s.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}

	@Scheduled(cron = "10 07 * * * ?")
	public void indexAsIndexWorksHour() {

		log.info("indexAsIndexWorksHour begin..." + this);

		IndexWorksTask task = new IndexWorksTask();
		task.prepare(atalasIndexService, TimeTypeEnum.HOUR);
		Future<String> s = poolTaskExecutor.submit(task);

		try {
			log.info("indexAsIndexWorksHour done.resut:" + s.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "10 23 00 * * ?")
	public void indexAsIndexWorksDay() {

		log.info("indexAsIndexWorksDay begin..." + this);

		IndexWorksTask task = new IndexWorksTask();
		task.prepare(atalasIndexService, TimeTypeEnum.DAY);
		Future<String> s = poolTaskExecutor.submit(task);

		try {
			log.info("indexAsIndexWorksDay done.resut:" + s.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "10 23 00 01 * ?")
	public void indexAsIndexWorksMonth() {

		log.info("indexAsIndexWorksMonth begin..." + this);

		IndexWorksTask task = new IndexWorksTask();
		task.prepare(atalasIndexService, TimeTypeEnum.MONTH);
		Future<String> s = poolTaskExecutor.submit(task);

		try {
			log.info("indexAsIndexWorksMonth done.resut:" + s.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

}
