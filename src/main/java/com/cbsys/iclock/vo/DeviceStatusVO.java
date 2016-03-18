package com.cbsys.iclock.vo;

import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;

public class DeviceStatusVO {
	private String deviceInfo;
	private boolean online = true;
	private String stamp;
	private String opStamp;
	private String photoStamp;
	private String deviceName;
	private String deviceID;
	private int deviceType; //考勤机类型:1为指纹打卡机,2为打卡拍照考勤机,3为指纹打卡考勤机,4为人脸指纹考勤机
	private Timestamp curTime;
	private String signalStrength;//信号强度
	private String cellularID;//小区位置
	private String IMSI;//IMSI号
	private String IMEI;//IMEI号

	private boolean updateStamp = false;
	private boolean cmdInfo = false;
	private boolean updateGPRS = false;

	public DeviceStatusVO() {

	}

	public DeviceStatusVO(String deviceInfo, Timestamp curTime) {
		this.deviceInfo = deviceInfo;
		this.curTime = curTime;
	}

	public DeviceStatusVO(String deviceInfo, boolean online, String stamp, String opStamp, String photoStamp, String deviceName, String deviceID,
			int deviceType, Timestamp curTime) {
		this.deviceInfo = deviceInfo;
		this.online = online;
		this.stamp = stamp;
		this.opStamp = opStamp;
		this.photoStamp = photoStamp;
		this.deviceName = deviceName;
		this.deviceID = deviceID;
		this.deviceType = deviceType;
		this.curTime = curTime;
		this.updateStamp = (stamp != null || opStamp != null || photoStamp != null);
		this.cmdInfo = (deviceID != null || deviceName != null);
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

	public boolean getUpdateStamp() {
		return updateStamp;
	}

	public void setUpdateStamp(boolean updateStamp) {
		this.updateStamp = updateStamp;
	}

	public boolean getCmdInfo() {
		return cmdInfo;
	}

	public void setCmdInfo(boolean cmdInfo) {
		this.cmdInfo = cmdInfo;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public boolean getOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public String getStamp() {
		return stamp;
	}

	public void setStamp(String stamp) {
		this.stamp = stamp;
		this.updateStamp = (stamp != null || opStamp != null || photoStamp != null);
	}

	public String getOpStamp() {
		return opStamp;
	}

	public void setOpStamp(String opStamp) {
		this.opStamp = opStamp;
		this.updateStamp = (opStamp != null || stamp != null || photoStamp != null);
	}

	public String getPhotoStamp() {
		return photoStamp;
	}

	public void setPhotoStamp(String photoStamp) {
		this.photoStamp = photoStamp;
		this.updateStamp = (photoStamp != null || stamp != null || opStamp != null);
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
		this.cmdInfo = (deviceName != null || deviceID != null);
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
		this.cmdInfo = (deviceID != null || deviceName != null);
	}

	public Timestamp getCurTime() {
		return curTime;
	}

	public void setCurTime(Timestamp curTime) {
		this.curTime = curTime;
	}

	public String getSignalStrength() {
		return signalStrength;
	}

	public void setSignalStrength(String signalStrength) {
		this.signalStrength = signalStrength;
		this.updateGPRS = (!StringUtils.isBlank(signalStrength) || !StringUtils.isBlank(IMSI) || !StringUtils.isBlank(cellularID)
				|| !StringUtils.isBlank(IMEI));
	}

	public String getCellularID() {
		return cellularID;
	}

	public void setCellularID(String cellularID) {
		this.cellularID = cellularID;
		this.updateGPRS = (!StringUtils.isBlank(cellularID) || !StringUtils.isBlank(signalStrength) || !StringUtils.isBlank(IMSI)
				|| !StringUtils.isBlank(IMEI));
	}

	public String getIMSI() {
		return IMSI;
	}

	public void setIMSI(String iMSI) {
		this.IMSI = iMSI;
		this.updateGPRS = (!StringUtils.isBlank(IMSI) || !StringUtils.isBlank(signalStrength) || !StringUtils.isBlank(cellularID)
				|| !StringUtils.isBlank(IMEI));
	}

	public String getIMEI() {
		return IMEI;
	}

	public void setIMEI(String iMEI) {
		this.IMEI = iMEI;
		this.updateGPRS = (!StringUtils.isBlank(iMEI) || !StringUtils.isBlank(IMSI) || !StringUtils.isBlank(cellularID)
				|| !StringUtils.isBlank(signalStrength));
	}

	public boolean getUpdateGPRS() {
		return updateGPRS;
	}

	public void setUpdateGPRS(boolean updateGPRS) {
		this.updateGPRS = updateGPRS;
	}

}
