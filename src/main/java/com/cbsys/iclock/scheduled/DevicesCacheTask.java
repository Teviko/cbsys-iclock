package com.cbsys.iclock.scheduled;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cbsys.iclock.service.DeviceService;
import com.cbsys.iclock.utils.LogUtil;
import com.cbsys.iclock.vo.DeviceInfo;
import com.cbsys.iclock.vo.SimpleDeviceInfo;

@Component
public class DevicesCacheTask {
	@Autowired
	private DeviceService deviceService;
	private static final Log logger = LogFactory.getLog(DevicesCacheTask.class);
	private final static Object Running = new Object();

	@Scheduled(initialDelay = 10000, fixedDelay = 20000)
	public void scanDevicesFromDB() {
		synchronized (Running) {
			logger.info("======================scan all devices into Cache (Beginning)--------------------");
			try {
				deviceService.scanAllDevices();
			} catch (Exception e) {
				logger.error(LogUtil.stackTraceToString(e));
			}
			logger.info("======================scan all devices into Cache(Done!)--------------------");
		}
	}

	@Scheduled(initialDelay = 30000, fixedDelay = 67000)
	public void updateDevicesToDB() {
		synchronized (Running) {
			logger.info("======================Flush new status into  DB (Beginning)--------------------");
			try {
				Map<String, SimpleDeviceInfo> updateDevices = deviceService.updateDeviceInfoToDB();
				for (Entry<String, SimpleDeviceInfo> e : updateDevices.entrySet()) {
					DeviceInfo d = DeviceService.DEVICES.getIfPresent(e.getKey());
					d.setDbLastOnlineTime(e.getValue().getLastOnlineTime());
					d.setLastOnlineTime(e.getValue().getLastOnlineTime());
					if (e.getValue().getInfo() != null)
						d.getInfo().compareAndSet(e.getValue().getInfo(), null);
				}
				logger.info("====" + DeviceService.DEVICES.size() + " Devices in Cache!");
			} catch (Exception e) {
				logger.error(LogUtil.stackTraceToString(e));
			}
			logger.info("======================Flush new status into  DB(Done!)--------------------");
		}
	}
}
