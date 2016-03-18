package com.cbsys.iclock;

import java.util.HashMap;
import java.util.Map;

/**
 * 考勤通用到的常量
 * @author albert
 *
 */
public class AttendanceConstants {

	public static final long TimesOfDay = 24 * 60 * 60 * 1000;


	public static final int MAX_LOGIN_TIMES_PER_MIN = 5;


	public static int PRI_SUPERADMIN = 14;
	public static int PRI_ADMIN = 6;
	public static int PRI_INPUTER = 2;
	public static int PRI_USER = 0;

	/**
	 * 上传用户记录的标志User
	 */
	public static final String OPSTAMP_USER = "USER";

	/**
	 * 上传指纹记录的标志FP
	 */
	public static final String OPSTAM_FP = "FP";

	/**
	 * 上传操作日志的标志OPLOG
	 */
	public static final String OPSTAM_OPLOG = "OPLOG";
	/**
	 * 上传人脸记录的标志FACE
	 */
	public static final String OPSTAM_FACE = "FACE";
	/**
	 * 上传用户头像的标志FACE
	 */
	public static final String OPSTAM_USERPIC = "USERPIC";

	/**
	 * 一天最晚时间，判断刷卡时间用
	 */
	public static final String MIDNIGHT1 = "23:59:59";
	/**
	 * 一天最早时间，判断刷卡时间用
	 */
	public static final String MIDNIGHT2 = "00:00:00";
	/**
	 * 设备命令前缀
	 */
	public static final String CMD_PREFIX = "C";
	/**
	 * 设备命令分割
	 */
	public static final String CMD_SEP = ":";
	/**
	 * <b>执行系统命令</b><br/>
	 * 格式：SHELL CMD_String。<br/>
	 * 返回：POST的内容为 ID=iiii&SN=xxxx&Return=vvvv&Content=ssss 其中vvvv为系统命令的返回值；ssss 为命令执行后的输出内容
	 */
	public static final int TYPE_CMD_SHELL = 1;
	/**
	 * <b>检查数据更新</b><br/>
	 * 功能： 从服务器读取机器的配置信息,然后检查机器内数据的更新情况,若有新的数据，立即传送到服务器；
	 * 格式：CHECK<br/>
	 * 返回：POST的内容为 ID=iiii&SN=xxxx&Return=1
	 */
	public static final int TYPE_CMD_CHECK = 2;
	/**
	 * <b>清除考勤记录</b><br/>
	 * 格式：CLEAR LOG<br/>
	 */
	public static final int TYPE_CMD_CLEAR_LOG = 3;
	/**
	 * <b>清除全部数据</b><br/>
	 * 格式：CLEAR DATA<br/>
	 */
	public static final int TYPE_CMD_CLEAR_DATA = 4;
	/**
	 *<b> 发送机器的信息到服务器上</b><br/>
	 * 格式：INFO<br/>
	 * 返回：POST的内容为系统所有配置和选项的有效内容
	 */
	public static final int TYPE_CMD_INFO = 5;
	/**
	 *<b> 设置机器的选项</b><br/>
	 * 格式：SET OPTION ITEM=VALUE，其中 ITEM 为选项的内容，VALUE为选项的值。目前支持的选项只包括没有“~”开头的项目。<br/>
	 * 例如：SET OPTION IPAddress=192.168.1.225，将把机器的IP地址设置成192.168.1.225<br/>
	 * 返回：POST的内容为 ID=iiii&SN=xxxx&Return=0
	 */
	public static final int TYPE_CMD_SET_OPTION = 6;
	/**
	 * <b>重新启动</b><br/>
	 * 格式：REBOOT<br/>
	 *注意：该命令必须是返回设备的最后一条命令。
	 */
	public static final int TYPE_CMD_REBOOT = 7;
	/**
	 * <b>新增或修改用户信息</b><br/>
	 * 格式：DATA USER PIN=%d\tName=%s\tPri=%d\tPasswd=%s\tCard=[%02x%02x%02x%02x%02x]\tGrp=%d\tTZ=%d
	 * 该命令中只有PIN字段是必须的，其他的字段可以没有。
	 */
	public static final int TYPE_CMD_DATA_USER = 8;
	/**
	 * <b>新增或修改用户指纹</b><br/>
	 * 格式：DATA FP PIN=%d\tFID=%d\tSize=%d\tValid=%d\tTMP=%s
	 */
	public static final int TYPE_CMD_DATA_FP = 9;
	/**
	 * <b>删除用户</b><br/>
	 * 格式：DATA DEL_USER PIN=%d
	 */
	public static final int TYPE_CMD_DEL_USER = 10;
	/**
	 * <b>删除用户指纹</b><br/>
	 * 格式：DATA DEL_FP PIN=%d\tFID=%d
	 */
	public static final int TYPE_CMD_DEL_FP = 11;
	/**
	 * <b>重新载入系统选项</b><br/>
	 * 格式：RELOAD OPTIONS<br/>
	 * 功能：要求设备重新载入系统配置和选项，这样修改后的系统选项才能生效。<br/>
	 * 返回：POST的内容为 ID=iiii&SN=xxxx&Return=0
	 */
	public static final int TYPE_CMD_RELOAD_OPTIONS = 12;

	public static final int TYPE_FOR_USER_PASSWD = 13;

	public static final int TYPE_CMD_PUT_FILE = 14;

	public static final int TYPE_CMD_CLEAR_PHOTO = 15;

	/**
	 * <b>上传指定时间段内照片</b><br/>
	 * 格式：GET TZ PHOTO 20110407-20110410
	 */
	public static final int TYPE_CMD_UPLOAD_PHOTOS = 16;

	/**
	 * <b>新增或编辑人脸模版</b><br/>
	 * 格式：DATA UPDATE FACE PIN=%s\tFID=%d\tSIZE=%d\tVALID=%d\tTMP=%s\n
	 */
	public static final int TYPE_CMD_FACE = 17;

	/**
	 * <b>删除用户人脸</b><br/>
	 * 格式：DATA DEL_USER PIN=%d
	 */
	public static final int TYPE_CMD_DEL_FACE = 18;

	/**
	 * <b>新增工作代码</b><br/>
	 * 格式：DATA UPDATE WORKCODE Code=%s\tName=%s\n
	 */
	public static final int TYPE_CMD_WOKRCODE = 19;


	/**
	 * <b>删除工作代码</b><br/>
	 * 格式：DATA DELETE WORKCODE Code=%s\n
	 */
	public static final int TYPE_CMD_DEL_WOKRCODE = 20;
	/**
	 * <b>新增或编辑员工头像</b><br/>
	 * 格式：DATA UPDATE USERPIC PIN=%d\tSize=%d\tContent=%s\t<br/>
	 * 其中各个字段的解释：USERPIC：数据表名称；PIN:用户工号；	Size: 图片文件的base64字符串长度；Content: 图片文件的base64编码数据<br/>
	 * 	返回值说明：0	命令支持成功；-1	参数错误；-2  Size大小指示错误；-3	存取错误
	 */
	public static final int TYPE_CMD_USERPIC = 21;

	/**
	 * <b>删除员工头像</b><br/>
	 * 格式：DELETE USERPIC  PIN=%d\n<br/>
	 * 	参数：PIN 用户工号<br/>
	 * 返回值说明： 0	成功 -1 	用户不存在 -2  	图片不存在
	 */
	public static final int TYPE_CMD_DEL_USERPIC = 22;

	/**
	 * <b>通用命令类型串，用于下发命令时候检索用</b><br/>
	 * 格式：DATA DELETE WORKCODE Code=%s\n
	 */
	public static String COMMON_CMD_TYPES_FOR_QUERY = "(1,2,3,4,5,6,7,8,9,10,11,12,13,15,16,18,19,20)";

	public static final String NO_CARD = "0000000000";

	public static final String SET_OPTION_CMD_CapturePic = "CapturePic";//拍照模式
	public static final String SET_OPTION_CMD_CapturevfTimes = "CapturevfTimes";//不通过保存模式下的不通过次数
	public static final String SET_OPTION_CMD_AlarmReRec = "AlarmReRec";//重复确认时间(分钟数)
	public static final String SET_OPTION_CMD_AutoPowerOn = "AutoPowerOn";//自动开机时间
	public static final String SET_OPTION_CMD_AutoPowerOff = "AutoPowerOff";//自动关机时间
	public static final String SET_OPTION_CMD_ExtAttPhoto = "ExtAttPhoto";//通过验证方式控制拍照:0代表FP/PW/RF，1代表FP 2代表PW 3代表RF 4代表 FP/PW  5代表FP/RF  6代表pW/RF
	public static final String SET_OPTION_CMD_HeartBeatServer = "HeartBeatServer";

	/**
	 * 设备命令
	 */
	public static final Map<Integer, String> CMDMap = new HashMap<Integer, String>();
	static {
		CMDMap.put(TYPE_CMD_SHELL, "SHELL %1%");
		CMDMap.put(TYPE_CMD_CHECK, "CHECK");
		CMDMap.put(TYPE_CMD_CLEAR_LOG, "CLEAR LOG");
		CMDMap.put(TYPE_CMD_CLEAR_DATA, "CLEAR DATA");
		CMDMap.put(TYPE_CMD_INFO, "INFO");
		CMDMap.put(TYPE_CMD_SET_OPTION, "SET OPTION %1%=%2%");
		CMDMap.put(TYPE_CMD_REBOOT, "REBOOT");
		CMDMap.put(TYPE_CMD_DATA_USER, "DATA USER PIN=%1%\tName=%2%\tPri=%3%\tCard=[%4%]");//\tPasswd=%4%\tCard=[%5%]\tGrp=%6%\tTZ=%7%;
		CMDMap.put(TYPE_FOR_USER_PASSWD, "DATA USER PIN=%1%\tName=%2%\tPri=%3%\tPasswd=%4%\tCard=[%5%]");
		CMDMap.put(TYPE_CMD_DATA_FP, "DATA FP PIN=%1%\tFID=%2%\tSize=%3%\tValid=%4%\tTMP=%5%");
		CMDMap.put(TYPE_CMD_DEL_USER, "DATA DEL_USER PIN=%1%");
		CMDMap.put(TYPE_CMD_DEL_FP, "DATA DEL_FP PIN=%1%\tFID=%2%");
		CMDMap.put(TYPE_CMD_RELOAD_OPTIONS, "RELOAD OPTIONS");
		CMDMap.put(TYPE_CMD_PUT_FILE, "PutFile %1%\t%2%");
		CMDMap.put(TYPE_CMD_CLEAR_PHOTO, "CLEAR PHOTO PhotoStamp=%1%");
		CMDMap.put(TYPE_CMD_UPLOAD_PHOTOS, "GET TZ PHOTO %1%-%2%");
		CMDMap.put(TYPE_CMD_FACE, "DATA UPDATE FACE PIN=%1%\tFID=%2%\tSIZE=%3%\tValid=%4%\tTMP=%5%");
		CMDMap.put(TYPE_CMD_DEL_FACE, "DATA  DELETE FACE PIN=%1%");
		CMDMap.put(TYPE_CMD_WOKRCODE, "DATA UPDATE WORKCODE Code=%1%\tName=%2%");
		CMDMap.put(TYPE_CMD_DEL_WOKRCODE, "DATA DELETE WORKCODE Code=%1%");
		CMDMap.put(TYPE_CMD_USERPIC, "DATA UPDATE USERPIC PIN=%1%\tSize=%2%\tContent=%3%");
		CMDMap.put(TYPE_CMD_DEL_USERPIC, "DATA  DELETE USERPIC PIN=%1%");
	}

	public static final Map<Integer, String> CMDTitleMap = new HashMap<Integer, String>();
	static {
		CMDTitleMap.put(TYPE_CMD_SHELL, "执行系统命令");
		CMDTitleMap.put(TYPE_CMD_CHECK, "检查数据更新");
		CMDTitleMap.put(TYPE_CMD_CLEAR_LOG, "清除设备考勤记录");
		CMDTitleMap.put(TYPE_CMD_CLEAR_DATA, "清除设备全部数据");
		CMDTitleMap.put(TYPE_CMD_INFO, "获取机器信息");
		CMDTitleMap.put(TYPE_CMD_SET_OPTION, "设置设备的选项");
		CMDTitleMap.put(TYPE_CMD_REBOOT, "重启设备");
		CMDTitleMap.put(TYPE_CMD_DATA_USER, "新增或修改用户信息");
		CMDTitleMap.put(TYPE_FOR_USER_PASSWD, "新增或修改用户信息");
		CMDTitleMap.put(TYPE_CMD_DATA_FP, "新增或修改用户指纹");
		CMDTitleMap.put(TYPE_CMD_DEL_USER, "删除用户");
		CMDTitleMap.put(TYPE_CMD_DEL_FP, "删除用户指纹");
		CMDTitleMap.put(TYPE_CMD_RELOAD_OPTIONS, "重新载入系统选项");
		CMDTitleMap.put(TYPE_CMD_PUT_FILE, "发送文件");
		CMDTitleMap.put(TYPE_CMD_CLEAR_PHOTO, "清除设备照片");
		CMDTitleMap.put(TYPE_CMD_UPLOAD_PHOTOS, "回传指定时间段内照片");
		CMDTitleMap.put(TYPE_CMD_FACE, "新增或修改用户人脸");
		CMDTitleMap.put(TYPE_CMD_DEL_FACE, "删除用户人脸");
		CMDTitleMap.put(TYPE_CMD_WOKRCODE, "新增工作代码");
		CMDTitleMap.put(TYPE_CMD_DEL_WOKRCODE, "删除工作代码");
		CMDTitleMap.put(TYPE_CMD_USERPIC, "新增或修改员工照片");
		CMDTitleMap.put(TYPE_CMD_DEL_USERPIC, "删除员工照片");
	}

	public static final String RESP_TEXT_EQUAL = "=";
	public static final String RESP_TEXT_COMMA = ",";
	public static final String RESP_TEXT_T = "\t";
	public static final String RESP_TEXT_R = "\r";
	public static final String RESP_TEXT_N = "\n";

	public static String PARAM_Stamp = "Stamp";
	public static String PARAM_OpStamp = "OpStamp";
	public static String PARAM_options = "options";
	public static String PARAM_IMSI = "IMSI";
	public static String PARAM_SigIntensity = "SigIntensity";
	public static String PARAM_CellularID = "CellularID";
	public static String PARAM_IMEI = "IMEI";

	public static String DEVICE_INFO_SN = "GET OPTION FROM: ";
	public static String DEVICE_INFO_Stamp = "Stamp";
	public static String DEVICE_INFO_OpStamp = "OpStamp";
	public static String DEVICE_INFO_PhotoStamp = "PhotoStamp";
	public static String DEVICE_INFO_ErrorDelay = "ErrorDelay";
	public static String DEVICE_INFO_Delay = "Delay";
	public static String DEVICE_INFO_TransTimes = "TransTimes";
	public static String DEVICE_INFO_TransInterval = "TransInterval";
	public static String DEVICE_INFO_TransFlag = "TransFlag";
	public static String DEVICE_INFO_Realtime = "Realtime";
	public static String DEVICE_INFO_Encrypt = "Encrypt";
	public static String DEVICE_INFO_TimeZoneclock = "TimeZoneclock";
	public static String DEVICE_INFO_Time_Zone = "Time_Zone";
	public static String DEVICE_INFO_TransFlag_Default = "111111111";


	public static final int FLAG_NEW = 1;


	public static final int FLAG_SENDED = 2;


	public static final int FLAG_DONE = 3;


	public static final int ORDER_COMMON = 100;


	public static final int ORDER_RELOAD_OPTIONS = 120;


	public static final int ORDER_USER_COMMON = 98;


	public static final int ORDER_FP_COMMON = 99;


	public static final int ORDER_PUTFILE = 200;


	public static final int ORDER_REBOOT = 500;
}
