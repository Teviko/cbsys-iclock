package com.cbsys.iclock.attDevice.cmd;

import java.util.Map.Entry;

import com.cbsys.iclock.AttendanceConstants;
import com.cbsys.iclock.domain.AttDeviceCMD;

/**
 * <b> 设置机器的选项</b><br/> 格式：SET OPTION ITEM=VALUE，其中 ITEM 为选项的内容，VALUE为选项的值。目前支持的选项只包括没有“~”开头的项目。<br/> 例如：SET OPTION
 * IPAddress=192.168.1.225，将把机器的IP地址设置成192.168.1.225<br/> 返回：POST的内容为 ID=iiii&SN=xxxx&Return=0
 * 
 * @author albert
 */
public class SetOptionCommand implements DeviceCommand {
	private int commandType = AttendanceConstants.TYPE_CMD_SET_OPTION;

	public int getCommandType() {
		return commandType;
	}

	public void makeCommand(AttDeviceCMD cmd, CommandParam param) {
		if (cmd == null || param.getOptions() == null || param.getOptions().size() == 0)
			return;
		String[] values = new String[2];
		Entry<String, String> entry = param.getOptions().entrySet().iterator().next();
		values[0] = entry.getKey();
		values[1] = entry.getValue();
		cmd.setCmd(AttendanceDeviceCMDUtils.makeCMD(commandType, values));
	}

	public void processResult(AttDeviceCMD cmd) {

	}
}
