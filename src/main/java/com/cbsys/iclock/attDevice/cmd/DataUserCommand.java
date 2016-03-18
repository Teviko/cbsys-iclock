package com.cbsys.iclock.attDevice.cmd;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.cbsys.iclock.AttendanceConstants;
import com.cbsys.iclock.domain.AttDeviceCMD;

/**
 * <b>新增或修改用户信息</b><br/> 格式：DATA USER PIN=%d\tName=%s\tPri=%d\tPasswd=%s\tCard=[%02x%02x%02x%02x%02x]\tGrp=%d\tTZ=%d
 * 该命令中只有PIN字段是必须的，其他的字段可以没有。
 * Pri表示用户权限 0, 普通人员；2,登记员；6,管理员；14,超级管理员
 * @author albert
 * 
 */
public class DataUserCommand implements DeviceCommand {
	private int commandType = AttendanceConstants.TYPE_CMD_DATA_USER;

	public int getCommandType() {
		return commandType;
	}

	public void makeCommand(AttDeviceCMD cmd, CommandParam param) {
		if (cmd == null || param.getStaff() == null)
			return;
		String[] values = new String[4];
		values[0] = param.getStaff().getPin();
		values[1] = replaySpecialChinese(param.getStaff().getName());
		values[2] = String.valueOf(param.getPri());
		String card = param.getStaff().getCard();
		values[3] = StringUtils.isBlank(card) ? AttendanceConstants.NO_CARD : card;
		cmd.setCmd(AttendanceDeviceCMDUtils.makeCMD(commandType, values));
	}

	public void processResult(AttDeviceCMD cmd) {

	}

	public static final Map<String, String> CharReplaceMap = new HashMap<String, String>();
	static {
		CharReplaceMap.put("競", "竞竞");
		CharReplaceMap.put("玥", "王月");
	}

	public static String replaySpecialChinese(String name) {
		for (String key : CharReplaceMap.keySet())
			name = name.replaceAll(key, CharReplaceMap.get(key));
		return name;
	}
}
