package com.cbsys.iclock.attDevice.cmd;

import org.apache.commons.lang3.StringUtils;

import com.cbsys.iclock.AttendanceConstants;
import com.cbsys.iclock.domain.AttDeviceCMD;

/**
 * @author albert
 *
 */
public class DataUserPasswdCommand implements DeviceCommand {
	private int commandType = AttendanceConstants.TYPE_FOR_USER_PASSWD;

	public int getCommandType() {
		return commandType;
	}

	public void makeCommand(AttDeviceCMD cmd, CommandParam param) {
		if (cmd == null || param.getStaff() == null)
			return;
		String[] values = new String[5];
		values[0] = param.getStaff().getPin();
		values[1] = DataUserCommand.replaySpecialChinese(param.getStaff().getName());
		values[2] = String.valueOf(param.getPri());
		if (StringUtils.isNotBlank(param.getStaff().getPassword())) {
			values[3] = param.getStaff().getPassword();
		} else {
			values[3] = "";
		}
		String card = param.getStaff().getCard();
		values[4] = StringUtils.isBlank(card) ? AttendanceConstants.NO_CARD : card;
		cmd.setCmd(AttendanceDeviceCMDUtils.makeCMD(commandType, values));
	}

	public void processResult(AttDeviceCMD cmd) {

	}
}
