package com.cbsys.iclock.attDevice.cmd;

import com.cbsys.iclock.AttendanceConstants;
import com.cbsys.iclock.domain.AttDeviceCMD;

public class PutFileCommand implements DeviceCommand {
	private int commandType = AttendanceConstants.TYPE_CMD_PUT_FILE;

	public int getCommandType() {
		return commandType;
	}

	public void makeCommand(AttDeviceCMD cmd, CommandParam param) {
		if (cmd == null)
			return;
		String[] values = new String[2];
		values[0] = param.getUrl();
		values[1] = param.getFileName();
		cmd.setCmd(AttendanceDeviceCMDUtils.makeCMD(commandType, values));
	}

	public void processResult(AttDeviceCMD cmd) {

	}

}
