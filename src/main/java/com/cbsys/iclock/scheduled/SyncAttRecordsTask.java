package com.cbsys.iclock.scheduled;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cbsys.iclock.service.ClockService;
import com.cbsys.iclock.utils.LogUtil;

@Component
public class SyncAttRecordsTask {
	@Autowired
	private ClockService clockService;
	private static final Log logger = LogFactory.getLog(SyncAttRecordsTask.class);
	private final static Object Running = new Object();

	@Scheduled(initialDelay = 0, fixedDelay = 60000)
	public void syncAttRecords() {
		synchronized (Running) {
			logger.info("======================sync att records to TMS  (Beginning)--------------------");
			try {
				clockService.syncAttRecords();
			} catch (Exception e) {
				logger.error(LogUtil.stackTraceToString(e));
			}
			logger.info("======================finish sync att records to TMS (Done!)--------------------");
		}
	}
}
