package com.cbsys.iclock.attDevice.cmd;

import com.cbsys.iclock.AttendanceConstants;
import com.cbsys.iclock.domain.AttDeviceCMD;

/**
 * <b>删除用户</b><br/> 格式：DATA DEL_USER PIN=%d
 * 
 * @author albert
 */
public class DelUserCommand implements DeviceCommand {
	private int commandType = AttendanceConstants.TYPE_CMD_DEL_USER;

	public int getCommandType() {
		return commandType;
	}

	public void makeCommand(AttDeviceCMD cmd, CommandParam param) {
		if (cmd == null || param.getStaff() == null)
			return;
		String[] values = new String[1];
		values[0] = param.getStaff().getPin();
		cmd.setCmd(AttendanceDeviceCMDUtils.makeCMD(commandType, values));
	}

	public void processResult(AttDeviceCMD cmd) {

	}
}
