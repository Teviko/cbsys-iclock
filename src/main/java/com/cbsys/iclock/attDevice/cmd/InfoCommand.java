package com.cbsys.iclock.attDevice.cmd;

import com.cbsys.iclock.AttendanceConstants;
import com.cbsys.iclock.domain.AttDeviceCMD;

/**
 *<b> 发送机器的信息到服务器上</b><br/> 格式：INFO<br/> 返回：POST的内容为系统所有配置和选项的有效内容
 */
public class InfoCommand implements DeviceCommand {
	private int commandType = AttendanceConstants.TYPE_CMD_INFO;

	public int getCommandType() {
		return commandType;
	}

	public void makeCommand(AttDeviceCMD cmd, CommandParam param) {
		if (cmd == null)
			return;
		String cmdTemple = AttendanceConstants.CMDMap.get(commandType);
		cmd.setCmd(cmdTemple);
	}

	public void processResult(AttDeviceCMD cmd) {
		// TODO:处理
	}

}
