package com.cbsys.iclock.attDevice.cmd;

import com.cbsys.iclock.AttendanceConstants;
import com.cbsys.iclock.domain.AttDeviceCMD;

public class DataFaceCommand implements DeviceCommand {

	private int commandType = AttendanceConstants.TYPE_CMD_FACE;

	public int getCommandType() {
		return commandType;
	}

	public void makeCommand(AttDeviceCMD cmd, CommandParam param) {
		if (cmd == null || param.getSfp() == null)
			return;
		String[] values = new String[5];
		values[0] = param.getSfp().getPin();
		values[1] = String.valueOf(param.getSfp().getFid());
		values[2] = String.valueOf(param.getSfp().getSize());
		values[3] = String.valueOf(param.getSfp().getValid());
		values[4] = param.getSfp().getTmp();
		cmd.setCmd(AttendanceDeviceCMDUtils.makeCMD(commandType, values));
	}

	public void processResult(AttDeviceCMD cmd) {

	}

}
