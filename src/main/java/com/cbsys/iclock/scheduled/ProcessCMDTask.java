package com.cbsys.iclock.scheduled;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cbsys.iclock.service.CMDService;
import com.cbsys.iclock.utils.LogUtil;

@Component
public class ProcessCMDTask {
	@Autowired
	private CMDService cmdService;
	private static final Log logger = LogFactory.getLog(DevicesCacheTask.class);

	@Scheduled(initialDelay = 10000, fixedDelay = 20000)
	public void processCMDTask() {
		logger.info("########scan CMD Tasks (Beginning)--------------------");
		try {
			cmdService.scanStaffUploadTask();
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
		}
		logger.info("########scan CMD Tasks (Done!)--------------------");
	}
}
