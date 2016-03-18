package com.cbsys.iclock.api;

import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_Delay;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_Encrypt;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_ErrorDelay;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_OpStamp;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_Realtime;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_SN;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_Stamp;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_TimeZoneclock;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_Time_Zone;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_TransFlag;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_TransFlag_Default;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_TransInterval;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_TransTimes;
import static com.cbsys.iclock.AttendanceConstants.RESP_TEXT_EQUAL;
import static com.cbsys.iclock.AttendanceConstants.RESP_TEXT_N;
import static com.cbsys.iclock.AttendanceConstants.RESP_TEXT_T;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cbsys.iclock.domain.AttDevice;
import com.cbsys.iclock.domain.AttRecord;
import com.cbsys.iclock.exception.ErrorDeviceException;
import com.cbsys.iclock.exception.ErrorParamsException;
import com.cbsys.iclock.service.ClockService;
import com.cbsys.iclock.service.DeviceService;
import com.cbsys.iclock.utils.ClockUtils;
import com.cbsys.iclock.utils.HttpUtils;
import com.cbsys.iclock.utils.LogUtil;
import com.cbsys.iclock.vo.DeviceInfo;
import com.cbsys.iclock.vo.DeviceStatusVO;

@RestController
public class CDataEndpoint {
	private static final Log logger = LogFactory.getLog(CDataEndpoint.class);
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private ClockService clockService;

	@RequestMapping(value = "/cdata", method = RequestMethod.GET, produces = "text/plain")
	@ResponseBody
	public String pushDeviceConfigure(@RequestParam("SN") String sn, @RequestParam(value = "options") String options,
			@RequestParam(value = "pushver") String pushver,
			@RequestParam(value = "language") String language) {
		logger.info("/iclock/cdata GET:" + sn + "  options:" + options);
		HttpUtils.loggerRequest(logger);
		if (!"all".equalsIgnoreCase(options))
			throw new ErrorParamsException();
		DeviceInfo di = DeviceService.DEVICES.getIfPresent(sn);
		if (di == null)
			throw new ErrorDeviceException();
		DeviceStatusVO vo = new DeviceStatusVO();
		vo.setCurTime(new Timestamp(System.currentTimeMillis()));
		AttDevice device = deviceService.getDeviceBySNForInit(sn, vo);
		if (device == null)
			throw new ErrorDeviceException();
		String info = makeDeviceSetting(device);
		logger.info(info);
		return info;
	}

	@RequestMapping(value = "/cdata", method = RequestMethod.POST, consumes = "text/plain", produces = "text/plain")
	@ResponseBody
	public String updateData(@RequestBody String body, @RequestParam("SN") String sn, @RequestParam(value = "table") String table,
			@RequestParam(value = "Stamp") String stamp) {
		Timestamp begin = new Timestamp(System.currentTimeMillis());
		logger.info("/iclock/cdata POST:" + sn + "  table:" + table + "  Stamp:" + stamp);
		HttpUtils.loggerRequest(logger);
		logger.info(body);
		DeviceInfo device = StringUtils.isBlank(sn) ? null : DeviceService.DEVICES.getIfPresent(sn);
		if (device == null)
			throw new ErrorDeviceException();
		try {
			Set<String> userInfos = new HashSet<String>();
			Set<AttRecord> arList = new HashSet<AttRecord>();
			switch (table.toUpperCase()) {
			case "ATTLOG":
				String[] lines = body.split(RESP_TEXT_N);
				for (String record : lines) {
					logger.info(record);
					String[] rd = record.split(RESP_TEXT_T);
					if (rd.length < 2)
						throw new ErrorParamsException();
					AttRecord temp = new AttRecord();
					temp.setDeviceSN(sn);
					temp.setClockId(rd[0]);
					temp.setCorpToken(device.getCorpToken());
					temp.setUtcAttTime(ClockUtils.transToUTC(rd[1], device.getTimeZoneOffset()));
					temp.setStauts(rd.length >= 3 ? Integer.parseInt(rd[2]) : -1);
					temp.setVerifyType(rd.length >= 4 ? Integer.parseInt(rd[3]) : -1);
					temp.setWorkCode(rd.length >= 5 ? Integer.parseInt(rd[4]) : 0);
					temp.setRecordStr(record);
					temp.setCreateTime(new Timestamp(System.currentTimeMillis()));
					temp.setUpdateTime(temp.getCreateTime());
					arList.add(temp);
				}
				clockService.processAttRecords(sn, arList);
				device.setStamp(stamp);
				break;
			case "OPERLOG":
				// 上传用户信息
				lines = body.split(RESP_TEXT_N);
				for (String record : lines) {
					logger.info(record);
					userInfos.add(record);
				}
				clockService.processUserInfos(device, userInfos);
				device.setOpStamp(stamp);
				break;
			default:
				logger.info("UNKNOW TABLE:" + table);
				throw new ErrorParamsException();
			}
			device.setLastOnlineTime(ClockUtils.getLastOnlineTime());
			device.getModified().set(true);
		} catch (Throwable e) {
			logger.error(LogUtil.stackTraceToString(e));
			throw new ErrorParamsException();
		}
		logger.info("!!OUT-----" + sn + "-----begin Time：" + begin.toString() + "。退出/iclock/cdata的doPost");

		return "OK";
	}

	private String makeDeviceSetting(AttDevice device) {
		StringBuilder info = new StringBuilder();
		info.append(DEVICE_INFO_SN).append(device.getSerialNumber()).append(RESP_TEXT_N);
		info.append(DEVICE_INFO_Stamp).append(RESP_TEXT_EQUAL)
				.append(StringUtils.isBlank(device.getTransferSignRecordsStamp()) ? 0 : device.getTransferSignRecordsStamp()).append(RESP_TEXT_N);
		info.append(DEVICE_INFO_OpStamp).append(RESP_TEXT_EQUAL)
				.append(StringUtils.isBlank(device.getTransferUserRecordsStamp()) ? 0 : device.getTransferUserRecordsStamp()).append(RESP_TEXT_N);
		info.append(DEVICE_INFO_ErrorDelay).append(RESP_TEXT_EQUAL).append(device.getErrorDelay()).append(RESP_TEXT_N);
		info.append(DEVICE_INFO_Delay).append(RESP_TEXT_EQUAL).append(device.getDelay()).append(RESP_TEXT_N);
		info.append(DEVICE_INFO_TransTimes).append(RESP_TEXT_EQUAL).append(device.getTransTimes()).append(RESP_TEXT_N);
		info.append(DEVICE_INFO_TransInterval).append(RESP_TEXT_EQUAL).append(device.getTransInterval()).append(RESP_TEXT_N);
		info.append(DEVICE_INFO_TransFlag).append(RESP_TEXT_EQUAL).append(DEVICE_INFO_TransFlag_Default).append(RESP_TEXT_N);
		info.append(DEVICE_INFO_Realtime).append(RESP_TEXT_EQUAL).append(device.getRealtime()).append(RESP_TEXT_N);
		info.append(DEVICE_INFO_Encrypt).append(RESP_TEXT_EQUAL).append(0).append(RESP_TEXT_N);
		info.append(DEVICE_INFO_TimeZoneclock).append(RESP_TEXT_EQUAL).append(1).append(RESP_TEXT_N);
		info.append(DEVICE_INFO_Time_Zone).append(RESP_TEXT_EQUAL).append(1);
		return info.toString();
	}

}
