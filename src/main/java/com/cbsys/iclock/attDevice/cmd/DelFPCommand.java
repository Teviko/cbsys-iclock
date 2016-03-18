package com.cbsys.iclock.attDevice.cmd;

import com.cbsys.iclock.AttendanceConstants;
import com.cbsys.iclock.domain.AttDeviceCMD;

/**
 * <b>删除用户指纹</b><br/> 格式：DATA DEL_FP PIN=%d\tFID=%d
 * 
 * @author albert
 */
public class DelFPCommand implements DeviceCommand {
	private int commandType = AttendanceConstants.TYPE_CMD_DEL_FP;

	public int getCommandType() {
		return commandType;
	}

	public void makeCommand(AttDeviceCMD cmd, CommandParam param) {
		if (cmd == null || param.getFp() == null)
			return;
		String[] values = new String[2];
		values[0] = param.getFp().getPin();
		values[1] = String.valueOf(param.getFp().getFid());
		cmd.setCmd(AttendanceDeviceCMDUtils.makeCMD(commandType, values));
	}

	public void processResult(AttDeviceCMD cmd) {

	}
}
