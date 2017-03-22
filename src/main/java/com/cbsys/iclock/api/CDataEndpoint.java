package com.cbsys.iclock.api;

import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_Delay;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_Encrypt;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_ErrorDelay;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_OpStamp;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_Realtime;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_SN;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_Stamp;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_Time_Zone;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_TransFlag;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_TransFlag_Default;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_TransInterval;
import static com.cbsys.iclock.AttendanceConstants.DEVICE_INFO_TransTimes;
import static com.cbsys.iclock.AttendanceConstants.RESP_TEXT_EQUAL;
import static com.cbsys.iclock.AttendanceConstants.RESP_TEXT_N;
import static com.cbsys.iclock.AttendanceConstants.RESP_TEXT_T;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cbsys.iclock.domain.AttDevice;
import com.cbsys.iclock.domain.AttRecord;
import com.cbsys.iclock.exception.ErrorDataFormatException;
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

	@RequestMapping(value = "/cdata", method = RequestMethod.GET)
	public String pushDeviceConfigure(@RequestParam("SN") String sn, @RequestParam(value = "options") String options,
			@RequestParam(value = "pushver", required = false) String pushver, @RequestParam(value = "language", required = false) String language) {
		logger.info("/iclock/cdata GET:" + sn + "  options:" + options);
		HttpUtils.loggerRequest(logger);
		if (!"all".equalsIgnoreCase(options))
			throw new ErrorParamsException();
		DeviceInfo di = DeviceService.DEVICES.getIfPresent(sn);
		if (di == null)
			throw new ErrorDeviceException();
		DeviceStatusVO vo = new DeviceStatusVO();
		vo.setCurTime(new Timestamp(System.currentTimeMillis()));
		AttDevice device = deviceService.getDeviceBySNForInit(sn, vo, di);
		if (device == null)
			throw new ErrorDeviceException();
		String info = makeDeviceSetting(device);
		logger.info(info);
		return info;
	}

	@RequestMapping(value = "/cdata", method = RequestMethod.POST)
	public String updateData(@RequestBody(required = false) String body, @RequestParam("SN") String sn,
			@RequestParam(value = "table", required = false) String table, @RequestParam(value = "OpStamp", required = false) String opStamp,
			@RequestParam(value = "Stamp", required = false) String stamp) {
		Timestamp begin = new Timestamp(System.currentTimeMillis());
		String ts = (opStamp == null) ? stamp : opStamp;
		logger.info("/iclock/cdata POST:" + sn + "  table:" + table + "  Stamp:" + ts);
		HttpUtils.loggerRequest(logger);
		if (StringUtils.isEmpty(body)) {
			logger.error(sn + " Failed to read HTTP message:  Could not read document: null!!!!");
			logger.info("!!OUT-----" + sn + "-----begin Time：" + begin.toString() + "。退出/iclock/cdata的doPost");
			return "OK";
		}

		logger.info(body);
		DeviceInfo device = StringUtils.isBlank(sn) ? null : DeviceService.DEVICES.getIfPresent(sn);
		if (device == null)
			throw new ErrorDeviceException();
		try {
			List<String> userInfos = new ArrayList<String>();
			List<AttRecord> arList = new ArrayList<AttRecord>();
			if (table == null) {
				if (opStamp != null)
					table = "OPERLOG";
				else if (stamp != null)
					table = "ATTLOG";
				else
					throw new ErrorParamsException();
			}
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
					long[] times = ClockUtils.transToUTC(rd[1], device.getTzId());
					temp.setUtcAttTime(times[0]);
					temp.setTimezoneoffset((int) times[1]);
					temp.setStauts(rd.length >= 3 ? Integer.parseInt(rd[2]) : -1);
					temp.setVerifyType(rd.length >= 4 ? Integer.parseInt(rd[3]) : -1);
					temp.setWorkCode(rd.length >= 5 ? rd[4] : "0");
					if ("0".equals(temp.getWorkCode()) && StringUtils.isNotBlank(device.getDefaultWorkCode())) {
						temp.setWorkCode(device.getDefaultWorkCode());
						logger.info("setting default_work_code for ar : " + device.getDefaultWorkCode());
					}
					temp.setRecordStr(record);
					temp.setCreateTime(new Timestamp(System.currentTimeMillis()));
					temp.setUpdateTime(temp.getCreateTime());
					arList.add(temp);
				}
				clockService.processAttRecords(sn, arList);
				device.setStamp(ts);
				break;
			case "OPERLOG":
				// 上传用户信息
				lines = body.split(RESP_TEXT_N);
				for (String record : lines) {
					logger.info(record);
					userInfos.add(record);
				}
				clockService.processUserInfos(device, userInfos);
				device.setOpStamp(ts);
				break;
			default:
				logger.info("UNKNOW TABLE:" + table);
				throw new ErrorParamsException();
			}
			device.setLastOnlineTime(ClockUtils.getLastOnlineTime());
			device.getModified().set(true);
		} catch (ErrorDataFormatException e) {
			throw e;
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
		info.append(DEVICE_INFO_Time_Zone).append(RESP_TEXT_EQUAL).append(device.getTimeZoneOffset()).append(RESP_TEXT_N);
		return info.toString();
	}

}
