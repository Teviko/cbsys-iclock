package com.cbsys.iclock.attDevice.cmd;

import com.cbsys.iclock.AttendanceConstants;
import com.cbsys.iclock.domain.AttDeviceCMD;

public class ClearPhotoCommand implements DeviceCommand {
	private int commandType = AttendanceConstants.TYPE_CMD_CLEAR_PHOTO;

	public int getCommandType() {
		return commandType;
	}

	public void makeCommand(AttDeviceCMD cmd, CommandParam param) {
		if (cmd == null)
			return;
		String[] values = new String[1];
		values[0] = (param.getStamp() == null) ? "0" : param.getStamp();
		cmd.setCmd(AttendanceDeviceCMDUtils.makeCMD(commandType, values));
	}

	public void processResult(AttDeviceCMD cmd) {

	}
}
