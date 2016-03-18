package com.cbsys.iclock.api;

import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cbsys.iclock.exception.ErrorDeviceException;
import com.cbsys.iclock.exception.ErrorParamsException;
import com.cbsys.iclock.service.CMDService;
import com.cbsys.iclock.service.DeviceService;
import com.cbsys.iclock.utils.HttpUtils;
import com.cbsys.iclock.utils.LogUtil;
import com.cbsys.iclock.vo.DeviceInfo;

@RestController
public class DeviceCMDEndpoint {
	private static final Log logger = LogFactory.getLog(DeviceCMDEndpoint.class);
	@Autowired
	private CMDService cmdService;

	@RequestMapping(value = "/devicecmd", method = RequestMethod.POST, consumes = "text/plain", produces = "text/plain")
	@ResponseBody
	public String doPost(@RequestBody String body, @RequestParam("SN") String sn) {
		Timestamp begin = new Timestamp(System.currentTimeMillis());
		logger.info("/iclock/getrequest GET:" + sn + " body: ");
		logger.info(body);
		HttpUtils.loggerRequest(logger);
		try {
			DeviceInfo di = DeviceService.DEVICES.getIfPresent(sn);
			if (di == null)
				throw new ErrorDeviceException();
			cmdService.processDeviceCMD(di, body);
		} catch (Exception e) {
			logger.error(LogUtil.stackTraceToString(e));
			throw new ErrorParamsException();
		}
		logger.info("!!OUT-----" + sn + "processed /iclock/devicecmd  cost : " + (System.currentTimeMillis() - begin.getTime()) + "ms");
		return "OK";
	}
}
