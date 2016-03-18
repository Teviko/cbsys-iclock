package com.cbsys.iclock.attDevice.cmd;

import org.apache.commons.lang3.StringUtils;

import com.cbsys.iclock.AttendanceConstants;
import com.cbsys.iclock.domain.AttDeviceCMD;

/**
 * <b>删除工作代码</b><br/> 格式：DATA DELETE WORKCODE Code=%s\n
 * 
 * @author albert
 */
public class DelWorkCodeCommand implements DeviceCommand {
	private int commandType = AttendanceConstants.TYPE_CMD_DEL_WOKRCODE;

	public int getCommandType() {
		return commandType;
	}

	public void makeCommand(AttDeviceCMD cmd, CommandParam param) {
		if (cmd == null || StringUtils.isBlank(param.getWorkCode()))
			return;
		String[] values = new String[1];
		values[0] = param.getWorkCode();
		cmd.setCmd(AttendanceDeviceCMDUtils.makeCMD(commandType, values));
	}

	public void processResult(AttDeviceCMD cmd) {

	}
}
