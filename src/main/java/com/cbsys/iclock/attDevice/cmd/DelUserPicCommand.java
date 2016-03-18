package com.cbsys.iclock.attDevice.cmd;

import com.cbsys.iclock.AttendanceConstants;
import com.cbsys.iclock.domain.AttDeviceCMD;

/**
 * 格式：DATE DELETE USERPIC  PIN=%d\n<br/>
 * 	参数：PIN 用户工号<br/>
 * 返回值说明： 0	成功 -1 	用户不存在 -2  	图片不存在
 * @author albert
 */
public class DelUserPicCommand implements DeviceCommand {
	private int commandType = AttendanceConstants.TYPE_CMD_DEL_USERPIC;

	public int getCommandType() {
		return commandType;
	}

	public void makeCommand(AttDeviceCMD cmd, CommandParam param) {
		if (cmd == null || param.getFp() == null)
			return;
		String[] values = new String[1];
		values[0] = param.getStaff().getPin();
		cmd.setCmd(AttendanceDeviceCMDUtils.makeCMD(commandType, values));
	}

	public void processResult(AttDeviceCMD cmd) {

	}
}
