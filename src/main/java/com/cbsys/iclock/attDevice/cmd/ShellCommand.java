package com.cbsys.iclock.attDevice.cmd;

import org.hibernate.annotations.common.util.StringHelper;

import com.cbsys.iclock.AttendanceConstants;
import com.cbsys.iclock.domain.AttDeviceCMD;

/**
 * <b>执行系统命令</b><br/> 格式：SHELL CMD_String。<br/> 返回：POST的内容为 ID=iiii&SN=xxxx&Return=vvvv&Content=ssss 其中vvvv为系统命令的返回值；ssss 为命令执行后的输出内容
 * 
 * @author albert
 */
public class ShellCommand implements DeviceCommand {
	private int commandType = AttendanceConstants.TYPE_CMD_SHELL;

	public int getCommandType() {
		return commandType;
	}

	public void makeCommand(AttDeviceCMD cmd, CommandParam param) {
		if (cmd == null || StringHelper.isEmpty(param.getShell()))
			return;
		String[] values = new String[1];
		values[0] = param.getShell();
		cmd.setCmd(AttendanceDeviceCMDUtils.makeCMD(commandType, values));
	}

	public void processResult(AttDeviceCMD cmd) {

	}
}
