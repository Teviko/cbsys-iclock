package com.cbsys.iclock.attDevice.cmd;

import com.cbsys.iclock.AttendanceConstants;
import com.cbsys.iclock.domain.AttDeviceCMD;

/**
 * <b>删除用户人脸</b><br/> 格式：DATA  DELETE FACE PIN=%d
 * 
 * @author albert
 */
public class DelFaceCommand implements DeviceCommand {
	private int commandType = AttendanceConstants.TYPE_CMD_DEL_FACE;

	public int getCommandType() {
		return commandType;
	}

	public void makeCommand(AttDeviceCMD cmd, CommandParam param) {
		if (cmd == null || param.getSfp() == null)
			return;
		String[] values = new String[1];
		values[0] = param.getSfp().getPin();
		cmd.setCmd(AttendanceDeviceCMDUtils.makeCMD(commandType, values));
	}

	public void processResult(AttDeviceCMD cmd) {

	}
}
