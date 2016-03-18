package com.cbsys.iclock.attDevice.cmd;

import org.apache.commons.lang3.StringUtils;

import com.cbsys.iclock.AttendanceConstants;
import com.cbsys.iclock.domain.AttDeviceCMD;

/**
 * <b>新增工作代码</b><br/> 格式：DATA UPDATE WORKCODE Code=%s\tName=%s\n
 * 
 * @author albert
 */
public class DataWorkCodeCommand implements DeviceCommand {
	private int commandType = AttendanceConstants.TYPE_CMD_WOKRCODE;

	public int getCommandType() {
		return commandType;
	}

	public void makeCommand(AttDeviceCMD cmd, CommandParam param) {
		if (cmd == null || StringUtils.isBlank(param.getWorkCode()) || StringUtils.isBlank(param.getWorkTurnName()))
			return;
		String[] values = new String[2];
		values[0] = param.getWorkCode();
		values[1] = param.getWorkTurnName();
		cmd.setCmd(AttendanceDeviceCMDUtils.makeCMD(commandType, values));
	}

	public void processResult(AttDeviceCMD cmd) {

	}
}
