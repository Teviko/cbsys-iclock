package com.cbsys.iclock.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbsys.iclock.AttendanceConstants;
import com.cbsys.iclock.attDevice.cmd.AttendanceDeviceCMDUtils;
import com.cbsys.iclock.attDevice.cmd.CommandParam;
import com.cbsys.iclock.attDevice.cmd.DeviceCommand;
import com.cbsys.iclock.domain.AttDevice;
import com.cbsys.iclock.domain.AttDeviceCMD;
import com.cbsys.iclock.repository.AttDeviceCMDDao;
import com.cbsys.iclock.repository.AttDeviceDao;
import com.cbsys.iclock.utils.ClockUtils;
import com.cbsys.iclock.utils.LogUtil;
import com.cbsys.iclock.vo.DeviceInfo;
import com.cbsys.iclock.vo.DeviceStatusVO;
import com.cbsys.iclock.vo.SimpleDeviceInfo;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Service
@Transactional
public class DeviceService {
	private static final Log logger = LogFactory.getLog(DeviceService.class);
	@Autowired
	private AttDeviceDao attDeviceDao;
	@Autowired
	private AttDeviceCMDDao attDeviceCMDDao;

	public static final Cache<String, DeviceInfo> DEVICES = CacheBuilder.newBuilder().initialCapacity(200).maximumSize(10000).build();

	public AttDevice getDeviceBySNForInit(String sn, DeviceStatusVO vo, DeviceInfo di) {
		AttDevice device = attDeviceDao.findBySerialNumber(sn);
		if (device == null)
			return null;
		boolean needInfoCMD = false;
		if (StringUtils.isBlank(device.getDeviceVersion()))
			needInfoCMD = true;
		processUpdateDevice(device, vo, null);
		checkTimeZone(di, device, true);
		if (needInfoCMD) {
			try {
				logger.info("New Device is online.  Fetching Device INFO!");
				saveNewCMDWithoutParam(device, AttendanceConstants.TYPE_CMD_INFO); //fetch device info
			} catch (Exception e) {
				logger.error(LogUtil.stackTraceToString(e));
			}
		}
		return device;
	}

	public void checkTimeZone(DeviceInfo di, AttDevice device, boolean needResetOption) {
		int tzOffset = ClockUtils.getClockTZOffset(di.getTzId());
		if (tzOffset == 99) {
			logger.info("Check TimeZone Offset Wrong: " + tzOffset + " tzId: " + di.getTzId());
			if (needResetOption)
				makeOptionsCMD(device, "TZAdj", "");
		}
		if (di.getTimeZoneOffset() == tzOffset)
			return;
		if (device == null)
			device = attDeviceDao.findBySerialNumber(di.getSn());
		makeOptionsCMD(device, "TZAdj", String.valueOf(tzOffset)); //set device to local time-zone
		device.setTimeZoneOffset(tzOffset);
		di.setTimeZoneOffset(tzOffset);
		di.setCmds(di.getCmds() + 2);
	}

	public AttDeviceCMD makeOptionsCMD(AttDevice device, String key, String value) {
		if (device == null || StringUtils.isBlank(key) || StringUtils.isBlank(value))
			return null;
		int cmdType = AttendanceConstants.TYPE_CMD_SET_OPTION;
		DeviceCommand processor = AttendanceDeviceCMDUtils.getCMDProcessor(cmdType);
		AttDeviceCMD cmd = new AttDeviceCMD();
		cmd.setCmdType(cmdType);
		cmd.setCreateTime(new Timestamp(System.currentTimeMillis()));
		cmd.setUpdateTime(cmd.getCreateTime());
		cmd.setSerialNumber(device.getSerialNumber());
		cmd.setCorpToken(device.getCorpToken());
		cmd.setFlag(AttendanceConstants.FLAG_NEW);
		CommandParam commandParam = new CommandParam();
		Map<String, String> options = new HashMap<String, String>();
		options.put(key, value);
		commandParam.setOptions(options);
		processor.makeCommand(cmd, commandParam);
		attDeviceCMDDao.save(cmd);
		saveNewCMDWithoutParam(device, AttendanceConstants.TYPE_CMD_REBOOT);
		return cmd;
	}

	public void saveNewCMDWithoutParam(AttDevice device, int cmdType) {
		if (checkExistCMD(cmdType, device.getSerialNumber()))
			return;
		DeviceCommand processor = AttendanceDeviceCMDUtils.getCMDProcessor(cmdType);
		AttDeviceCMD cmd = new AttDeviceCMD();
		if (cmdType == AttendanceConstants.TYPE_CMD_REBOOT)
			cmd.setCmdOrder(AttendanceConstants.ORDER_REBOOT);
		if (cmdType == AttendanceConstants.TYPE_CMD_RELOAD_OPTIONS)
			cmd.setCmdOrder(AttendanceConstants.ORDER_RELOAD_OPTIONS);
		cmd.setCmdType(cmdType);
		cmd.setCreateTime(new Timestamp(System.currentTimeMillis()));
		cmd.setUpdateTime(cmd.getCreateTime());
		cmd.setSerialNumber(device.getSerialNumber());
		cmd.setCorpToken(device.getCorpToken());
		cmd.setFlag(AttendanceConstants.FLAG_NEW);
		processor.makeCommand(cmd, null);
		attDeviceCMDDao.save(cmd);
	}

	private boolean checkExistCMD(int cmdType, String sn) {
		if (cmdType == AttendanceConstants.TYPE_CMD_INFO || cmdType == AttendanceConstants.TYPE_CMD_REBOOT
				|| cmdType == AttendanceConstants.TYPE_CMD_PUT_FILE) {
			List<AttDeviceCMD> cmds = attDeviceCMDDao.findByCmdTypeAndSerialNumberAndFlagNot(cmdType, sn, AttendanceConstants.FLAG_DONE);
			return CollectionUtils.isNotEmpty(cmds);
		}
		return false;
	}

	public void scanAllDevices() {
		List<Object[]> devices = attDeviceDao.findAllDevices();
		logger.info(" Devices from DB ======= Total Count: " + devices.size());
		Set<String> sn = new HashSet<String>();
		sn.addAll(DEVICES.asMap().keySet());
		List<Object[]> devicesCMD = attDeviceCMDDao.findAllCMDStats();
		Map<String, Long> cmdCounts = new HashMap<String, Long>();
		for (Object[] cmd : devicesCMD) {
			if (cmd.length < 2)
				continue;
			cmdCounts.put(cmd[0].toString(), new Long(cmd[1].toString()));
		}
		for (Object[] obj : devices) {
			if (obj == null || obj.length != 10)
				continue;
			DeviceInfo d = new DeviceInfo(obj);
			sn.remove(d.getSn());

			DeviceInfo di = DEVICES.getIfPresent(d.getSn());
			if (di == null) {
				d.setLastOnlineTime(d.getDbLastOnlineTime());
				DEVICES.put(d.getSn(), d);
				logger.info("put a device into CACHE：" + d.getSn()
						+ (StringUtils.isNotBlank(d.getDefaultWorkCode()) ? ("        default_work_code: " + d.getDefaultWorkCode()) : ""));
				continue;
			}
			if ((di.getDefaultWorkCode() == null && StringUtils.isNotBlank(d.getDefaultWorkCode()))
					|| (di.getDefaultWorkCode() != null && !di.getDefaultWorkCode().equals(d.getDefaultWorkCode())))
				logger.info("update  device  into CACHE：" + d.getSn()	+ (StringUtils.isNotBlank(d.getDefaultWorkCode()) ? ("        default_work_code: " + d.getDefaultWorkCode()) : ""));
			di.setDefaultWorkCode(d.getDefaultWorkCode());
			di.setOffsiteAtt(d.getOffsiteAtt());
			di.setDbLastOnlineTime(d.getDbLastOnlineTime());
			di.setDeviceMode(d.getDeviceMode());
			di.setDeviceType(d.getDeviceType());
			di.setTimeZoneOffset(d.getTimeZoneOffset());
			Long count = cmdCounts.get(d.getSn());
			if (count == null)
				count = 0l;
			di.setId(d.getId());
			di.setCmds(count);
		}

		//清除已经删除的设备
		for (String key : sn) {
			logger.info("rmeove Device From CACHE: " + key);
			DEVICES.invalidate(key);
		}
	}

	public Map<String, SimpleDeviceInfo> updateDeviceInfoToDB() {
		Map<String, SimpleDeviceInfo> updateDevices = new HashMap<String, SimpleDeviceInfo>();
		Map<String, DeviceInfo> map = DEVICES.asMap();
		Set<Entry<String, DeviceInfo>> entries = map.entrySet();
		for (Entry<String, DeviceInfo> entry : entries) {
			DeviceInfo d = entry.getValue();
			if (!d.getModified().compareAndSet(true, false))
				continue;
			AttDevice device = attDeviceDao.findOne(d.getId());
			if (device == null)
				continue;
			long time = device.getLastOnlineTime() == null ? 0 : device.getLastOnlineTime().getTime();
			if (time < d.getLastOnlineTime())
				time = d.getLastOnlineTime();
			DeviceStatusVO vo = new DeviceStatusVO(d.getInfoV(), new Timestamp(time));
			if (d.getCmdInfoDeviceMode() != null) {
				vo.setDeviceName(d.getCmdInfoDeviceMode());
				d.setCmdInfoDeviceMode(null);
			}
			vo.setOpStamp(d.getOpStamp());
			if (vo.getOpStamp() != null)
				d.resetOpStamp(vo.getOpStamp());
			vo.setStamp(d.getStamp());
			if (vo.getStamp() != null)
				d.resetStamp(vo.getStamp());
			vo.setPhotoStamp(d.getPhotoStamp());
			if (vo.getPhotoStamp() != null)
				d.resetPhotoStamp(vo.getPhotoStamp());

			String[] infos = StringUtils.isBlank(vo.getDeviceInfo()) ? null : vo.getDeviceInfo().split(",");
			processUpdateDevice(device, vo, infos);
			attDeviceDao.save(device);
			updateDevices.put(d.getSn(), new SimpleDeviceInfo(d.getSn(), vo.getDeviceInfo(), time));
		}
		return updateDevices;
	}

	private void processUpdateDevice(AttDevice device, DeviceStatusVO vo, String[] infos) {
		device.setLastOnlineTime(vo.getCurTime());
		if (infos != null && infos.length >= 4) {
			try {
				device.setDeviceVersion(infos[0]);
				device.setStaffNumber(new Integer(infos[1]));
				device.setFingerprintNumber(new Integer(infos[2]));
				device.setAttendanceRecordsNumber(new Integer(infos[3]));
				if (infos.length >= 5)
					device.setDeviceIP(infos[4]);
				if (infos.length >= 6)
					device.setFingerAlgorithmVersion(infos[5]);
				if (infos.length >= 9) {//AttendanceDeviceService.DEVICE_MODEL_FACE.equals(device.getDeviceModel()) ||
					device.setFaceAlgorithmVersion(infos[6]);
					device.setFacePrintTemplateNum(new Integer(infos[7]));
					int fpNum = new Integer(infos[8]);
					if (fpNum >= 0)
						device.setFacePrintNum(fpNum);
				}
			} catch (Exception e) {
				logger.error(LogUtil.stackTraceToString(e));
			}
		}

		if (vo.getUpdateStamp()) {
			if (StringUtils.isNotBlank(vo.getOpStamp()))
				device.setTransferUserRecordsStamp(vo.getOpStamp());
			if (StringUtils.isNotBlank(vo.getStamp()))
				device.setTransferSignRecordsStamp(vo.getStamp());
		}
		if (vo.getUpdateGPRS()) {
			if (StringUtils.isNotBlank(vo.getIMSI()))
				device.setImsi(vo.getIMSI());
			if (StringUtils.isNotBlank(vo.getIMEI()))
				device.setImei(vo.getIMEI());
			if (StringUtils.isNotBlank(vo.getSignalStrength()))
				device.setSignalStrength(vo.getSignalStrength());
			if (StringUtils.isNotBlank(vo.getCellularID()))
				device.setCellularID(vo.getCellularID());
		}
	}
}
