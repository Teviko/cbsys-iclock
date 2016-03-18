package com.cbsys.iclock.api;

import static com.cbsys.iclock.AttendanceConstants.RESP_TEXT_N;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cbsys.iclock.domain.AttDeviceCMD;
import com.cbsys.iclock.exception.ErrorDeviceException;
import com.cbsys.iclock.exception.ErrorParamsException;
import com.cbsys.iclock.service.CMDService;
import com.cbsys.iclock.service.DeviceService;
import com.cbsys.iclock.utils.ClockUtils;
import com.cbsys.iclock.utils.HttpUtils;
import com.cbsys.iclock.utils.LogUtil;
import com.cbsys.iclock.vo.DeviceInfo;

@RestController
public class GetRequestEndpoint {
	private static final Log logger = LogFactory.getLog(GetRequestEndpoint.class);

	@Autowired
	private CMDService cmdService;

	@RequestMapping(value = "/getrequest", produces = "text/plain")
	@ResponseBody
	public String processGetReq(@RequestParam("SN") String sn, @RequestParam(value = "INFO", required = false) String info) {
		long start = System.currentTimeMillis();
		logger.info("/iclock/getrequest GET:" + sn + "  info:" + (info == null ? "" : info));
		HttpUtils.loggerRequest(logger);

		try {
			List<AttDeviceCMD> cmds = null;
			DeviceInfo di = DeviceService.DEVICES.getIfPresent(sn);
			if (di == null)
				throw new ErrorDeviceException();
			if (di != null) {
				if (info != null)
					di.setInfo(info);
				di.setLastOnlineTime(ClockUtils.getLastOnlineTime());
				di.getModified().set(true);
				if (di.getCmds() > 0) {
					logger.info(sn + "total ：" + di.getCmds());
					cmds = cmdService.processGetrequest(di);
				}
			}
			logger.info("-----ATT Device: " + sn + " fetched commands time：" + (System.currentTimeMillis() - start));
			if (CollectionUtils.isNotEmpty(cmds)) {
				String cmd = makeCMDResp(cmds);
				logger.info(cmd);
				return cmd;
			}
		} catch (Throwable e) {
			logger.error(LogUtil.stackTraceToString(e));
			throw new ErrorParamsException();
		}
		logger.info("!!OUT-----" + sn + "processed /iclock/getrequest  cost : " + (System.currentTimeMillis() - start) + "ms");
		return "OK";
	}

	private String makeCMDResp(List<AttDeviceCMD> cmds) {
		StringBuilder cmd = new StringBuilder();
		for (AttDeviceCMD c : cmds) {
			cmd.append(c.getDeviceCMD());
			cmd.append(RESP_TEXT_N);
		}
		return cmd.toString();
	}

}
