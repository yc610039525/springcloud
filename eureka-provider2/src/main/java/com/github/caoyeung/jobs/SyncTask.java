package com.github.caoyeung.jobs;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
@Lazy(value = false)
public class SyncTask {
	private static Logger LOGGER = LoggerFactory.getLogger(SyncTask.class);
	@PostConstruct
	@Scheduled(cron = "${syncTask.cron}")
	public void syncCassandraToOracle() {
		Runtime runtime = Runtime.getRuntime();
		LOGGER.info("每小时的前0-10分钟的每45秒执行一次定时任务"+System.currentTimeMillis());
		LOGGER.info("最大内存"+(runtime.maxMemory()/(8*1024*1024))+"M");
	}
}
