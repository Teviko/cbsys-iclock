package com.cbsys.iclock.attDevice.cmd;

import com.cbsys.iclock.AttendanceConstants;
import com.cbsys.iclock.domain.AttDeviceCMD;

/**
 * 一般命令处理（不需要传送任何参数的命令），包括命令如下：
 * <ul>
 * <li><b>重新启动</b><br/> 格式：REBOOT<br/> 注意：该命令必须是返回设备的最后一条命令。</li>
 * <li><b>重新载入系统选项</b><br/> 格式：RELOAD OPTIONS<br/> 功能：要求设备重新载入系统配置和选项，这样修改后的系统选项才能生效。<br/> 返回：POST的内容为 ID=iiii&SN=xxxx&Return=0</li>
 * <li><b>检查数据更新</b><br/> 功能： 从服务器读取机器的配置信息,然后检查机器内数据的更新情况,若有新的数据，立即传送到服务器； 格式：CHECK<br/> 返回：POST的内容为 ID=iiii&SN=xxxx&Return=1</li>
 * <li><b>清除考勤记录</b><br/> 格式：CLEAR LOG<br/></li>
 * <li><b>清除全部数据</b><br/> 格式：CLEAR DATA<br/></li>
 * <li></li>
 * </ul>
 * 
 * @author albert
 */
public class CommonCommand implements DeviceCommand {
	private int commandType;

	public CommonCommand(int cmdType) {
		commandType = cmdType;
	}

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

	}

}
