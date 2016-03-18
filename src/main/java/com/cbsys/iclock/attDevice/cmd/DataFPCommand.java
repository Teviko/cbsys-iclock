package com.cbsys.iclock.attDevice.cmd;

import com.cbsys.iclock.AttendanceConstants;
import com.cbsys.iclock.domain.AttDeviceCMD;

/**
 * <b>新增或修改用户指纹</b><br/> 格式：DATA FP PIN=%d\tFID=%d\tSize=%d\tValid=%d\tTMP=%s
 * 
 * @author albert
 */
public class DataFPCommand implements DeviceCommand {
	private int commandType = AttendanceConstants.TYPE_CMD_DATA_FP;

	public int getCommandType() {
		return commandType;
	}

	public void makeCommand(AttDeviceCMD cmd, CommandParam param) {
		if (cmd == null || param.getFp() == null)
			return;
		String[] values = new String[5];
		values[0] = param.getFp().getPin();
		values[1] = String.valueOf(param.getFp().getFid());
		values[2] = String.valueOf(param.getFp().getSize());
		values[3] = String.valueOf(param.getFp().getValid());
		values[4] = param.getFp().getTmp();
		cmd.setCmd(AttendanceDeviceCMDUtils.makeCMD(commandType, values));
	}

	public void processResult(AttDeviceCMD cmd) {

	}
}
