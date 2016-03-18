package com.cbsys.iclock.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbsys.iclock.AttendanceConstants;
import com.cbsys.iclock.domain.AttDeviceCMD;
import com.cbsys.iclock.repository.AttDeviceCMDDao;
import com.cbsys.iclock.utils.ClockUtils;
import com.cbsys.iclock.utils.LogUtil;
import com.cbsys.iclock.vo.DeviceInfo;

@Service
@Transactional
public class CMDService {
	private static final Log logger = LogFactory.getLog(CMDService.class);
	private final static int MAX_CMD_COUNT = 200;
	private final static int MAX_CMD_BYTE = 40 * 1024;
	private final static String EQUAL = "=";
	private final static String OPTIONS_DeviceName = "DeviceName";
	@SuppressWarnings("unused")
	private final static String OPTIONS_DeviceID = "DeviceID";

	@Autowired
	private AttDeviceCMDDao attDeviceCMDDao;

	public List<AttDeviceCMD> processGetrequest(DeviceInfo d) {
		if (d == null)
			return null;
		List<AttDeviceCMD> cmds = processAllNewCMDByDevice(d);
		if (cmds == null || cmds.size() == 0) {
			List<AttDeviceCMD> attFaceCmds = attDeviceCMDDao.getCmdsByDeviceAndFlagAndType(d.getSn(), d.getCorpToken(), AttendanceConstants.FLAG_NEW,
					AttendanceConstants.TYPE_CMD_FACE, new PageRequest(0, 12));
			if (CollectionUtils.isNotEmpty(attFaceCmds))
				cmds = makeCMDResult(attFaceCmds, false);
		}
		return cmds;
	}

	private List<AttDeviceCMD> processAllNewCMDByDevice(DeviceInfo device) {
		List<AttDeviceCMD> cmds = attDeviceCMDDao.getCmdsByDeviceAndFlag(device.getSn(), device.getCorpToken(), AttendanceConstants.FLAG_NEW,
				new PageRequest(0, MAX_CMD_COUNT));
		if (CollectionUtils.isNotEmpty(cmds))
			return makeCMDResult(cmds, true);

		List<AttDeviceCMD> temp = attDeviceCMDDao.getCmdsByDeviceAndFlagAndType(device.getSn(), device.getCorpToken(), AttendanceConstants.FLAG_NEW,
				AttendanceConstants.TYPE_CMD_PUT_FILE, new PageRequest(0, 1));
		if (temp != null && temp.size() > 0) {
			cmds = new ArrayList<AttDeviceCMD>();
			cmds.add(temp.get(0));
			return makeCMDResult(cmds, true);
		}
		return null;
	}

	private List<AttDeviceCMD> makeCMDResult(List<AttDeviceCMD> cmds, boolean needControlSize) {
		Timestamp cur = new Timestamp(System.currentTimeMillis());
		if (CollectionUtils.isEmpty(cmds))
			return null;
		int totalSize = 0;
		List<AttDeviceCMD> result = new ArrayList<AttDeviceCMD>();
		int maxByte = MAX_CMD_BYTE;
		Long baseCmdSN = attDeviceCMDDao.getCMDMaxSNByDevice(cmds.get(0).getSerialNumber(), cmds.get(0).getCorpToken());
		for (AttDeviceCMD cmd : cmds) { // 更新命令记录的发送时间和命令状态标志
			baseCmdSN++;
			cmd.setCmdSN(baseCmdSN);
			totalSize += (cmd.getDeviceCMD().getBytes().length + 1);
			if (needControlSize && totalSize >= maxByte) {
				cmd.setCmdSN(0);
				return result;
			}
			cmd.setSendTime(cur);
			cmd.setFlag(AttendanceConstants.FLAG_SENDED);
			attDeviceCMDDao.save(cmd);
			result.add(cmd);
		}
		return result;
	}

	/**
	 * result cmd format：ID=67&Return=0&CMD=DATA
	 */
	public void processDeviceCMD(DeviceInfo d, String body) {
		String result;
		long cmdSN;
		String deviceName = null;
		Timestamp curTime = new Timestamp(System.currentTimeMillis());
		String[] lines = body.split("\n");
		for (String line : lines) {
			try {
				if (line.indexOf("&") >= 0) {
					String[] tokens = line.split("&");
					cmdSN = Long.parseLong(tokens[0].substring(3));
					result = tokens[1].substring(7);
					AttDeviceCMD cmd = attDeviceCMDDao.findOneByCmdSNAndSerialNumberAndCorpToken(cmdSN, d.getSn(), d.getCorpToken());
					if (cmd == null)
						continue;
					cmd.setFlag(AttendanceConstants.FLAG_DONE);
					cmd.setResultTime(curTime);
					cmd.setCmdResult(result);

					if (cmd.getCmdType() == AttendanceConstants.TYPE_CMD_PUT_FILE) {
						//TODO firmware upgrade
					}
					attDeviceCMDDao.save(cmd);
				} else if (line.indexOf(OPTIONS_DeviceName) >= 0) {
					deviceName = line.split(EQUAL)[1];
				} /*else if (line.indexOf(OPTIONS_DeviceID) >= 0) {
					deviceID = line.split(EQUAL)[1];
					}*/
			} catch (Exception e) {
				logger.error(LogUtil.stackTraceToString(e));
			}
		}

		d.setLastOnlineTime(ClockUtils.getLastOnlineTime());
		if (d.getDeviceMode() == null || !d.getDeviceMode().equals(deviceName))
			d.setCmdInfoDeviceMode(deviceName);
		d.getModified().set(true);
	}
}
