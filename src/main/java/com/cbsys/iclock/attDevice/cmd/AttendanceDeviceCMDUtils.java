package com.cbsys.iclock.attDevice.cmd;

import org.apache.commons.lang3.StringUtils;

import com.cbsys.iclock.AttendanceConstants;

/**
 * 设备命令的工具类，负责拼装命令
 * @author albert
 *
 */
public class AttendanceDeviceCMDUtils {
	/**
	 * 拼装命令中的占位符
	 */
	public static final String MASK_CHAR = "%";

	private AttendanceDeviceCMDUtils() {

	}

	/**
	 * 拼装命令
	 * @param cmdType  命令类型
	 * @param values  命令的参数（有的命令不需要）
	 * @return
	 */
	public static String makeCMD(int cmdType, String... values) {
		String cmd = AttendanceConstants.CMDMap.get(cmdType);
		if (StringUtils.isBlank(cmd))
			return null;
		else if ((values == null || values.length == 0) && cmd.indexOf(MASK_CHAR) != -1)
			return null;

		String maskStr;
		int i = 1;
		while (cmd.indexOf(MASK_CHAR) != -1) {
			if (i > values.length)
				return null;
			maskStr = MASK_CHAR + i + MASK_CHAR;
			cmd = cmd.replaceAll(maskStr, values[i - 1]);
			i++;
		}
		return cmd;
	}

	public static DeviceCommand getCMDProcessor(int cmdType) {
		DeviceCommand processor = null;
		switch (cmdType) {
		case AttendanceConstants.TYPE_CMD_CHECK:
			processor = new CommonCommand(cmdType);
			break;
		case AttendanceConstants.TYPE_CMD_CLEAR_DATA:
			processor = new CommonCommand(cmdType);
			break;
		case AttendanceConstants.TYPE_CMD_CLEAR_LOG:
			processor = new CommonCommand(cmdType);
			break;
		case AttendanceConstants.TYPE_CMD_DATA_FP:
			processor = new DataFPCommand();
			break;
		case AttendanceConstants.TYPE_CMD_DATA_USER:
			processor = new DataUserCommand();
			break;
		case AttendanceConstants.TYPE_FOR_USER_PASSWD:
			processor = new DataUserPasswdCommand();
			break;
		case AttendanceConstants.TYPE_CMD_DEL_FP:
			processor = new DelFPCommand();
			break;
		case AttendanceConstants.TYPE_CMD_DEL_USER:
			processor = new DelUserCommand();
			break;
		case AttendanceConstants.TYPE_CMD_INFO:
			processor = new InfoCommand();
			break;
		case AttendanceConstants.TYPE_CMD_REBOOT:
			processor = new CommonCommand(cmdType);
			break;
		case AttendanceConstants.TYPE_CMD_RELOAD_OPTIONS:
			processor = new CommonCommand(cmdType);
			break;
		case AttendanceConstants.TYPE_CMD_SET_OPTION:
			processor = new SetOptionCommand();
			break;
		case AttendanceConstants.TYPE_CMD_SHELL:
			processor = new ShellCommand();
			break;
		case AttendanceConstants.TYPE_CMD_PUT_FILE:
			processor = new PutFileCommand();
			break;
		case AttendanceConstants.TYPE_CMD_CLEAR_PHOTO:
			processor = new ClearPhotoCommand();
			break;
		case AttendanceConstants.TYPE_CMD_FACE:
			processor = new DataFaceCommand();
			break;
		case AttendanceConstants.TYPE_CMD_WOKRCODE:
			processor = new DataWorkCodeCommand();
			break;
		case AttendanceConstants.TYPE_CMD_DEL_WOKRCODE:
			processor = new DelWorkCodeCommand();
			break;
		case AttendanceConstants.TYPE_CMD_DEL_USERPIC:
			processor = new DelUserPicCommand();
			break;
		default:
			break;
		}
		return processor;
	}
}
