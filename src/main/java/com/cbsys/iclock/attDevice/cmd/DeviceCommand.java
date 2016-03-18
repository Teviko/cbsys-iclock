package com.cbsys.iclock.attDevice.cmd;

import com.cbsys.iclock.domain.AttDeviceCMD;

/**
 * 设备命令处理接口
 * @author albert
 *
 */
public interface DeviceCommand {
	public int getCommandType();

	public void makeCommand(AttDeviceCMD cmd, CommandParam param);

	public void processResult(AttDeviceCMD cmd);
}
