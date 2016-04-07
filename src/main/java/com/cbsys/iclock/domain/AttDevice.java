package com.cbsys.iclock.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_att_devices")
public class AttDevice extends BaseEntity {
	private String serialNumber; // 机器序列号
	private String deviceName; // 机器名称
	private String deviceModel; // 机器型号
	private String corpToken;
	private int deviceType; //考勤机类型:1为指纹打卡机,2为打卡拍照考勤机,3为指纹打卡考勤机,4为人脸指纹考勤机,10WIFI考勤终端，11 定位考勤
	private String deviceLocation; // 设备所在地
	private Timestamp lastOnlineTime; // 最新联机时间
	private String deviceNumber; // 机号
	private int refreshInterval = 1; // 刷新间隔时间（分）
	private String transferSignRecordsStamp = "0"; //ATTLOGS
	private String transferUserRecordsStamp = "0"; // OPLOGS
	private Integer alarmReRec; //重复确认时间,单位分钟
	private Integer staffNumber; // 员工数
	private Integer fingerprintNumber; // 指纹记录数
	private Integer attendanceRecordsNumber; // 考勤记录数
	private String deviceVersion; //固件版本号
	private String deviceIP; //设备IP地址
	private int errorDelay;
	private int delay;
	private String transTimes;
	private int transInterval;
	private int realtime;
	private int timeZoneOffset;
	private String timeZone;
	private String timeZoneId;
	private String autoPowerontime;
	private String autoPowerofftime;
	private String faceAlgorithmVersion;
	private String fingerAlgorithmVersion;
	private Integer facePrintTemplateNum;
	private Integer facePrintNum;
	private String signalStrength;
	private String cellularID;
	private String imsi;
	private String imei;
	private int offsiteAtt = 0;
	private String comments;

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getCorpToken() {
		return corpToken;
	}

	public void setCorpToken(String corpToken) {
		this.corpToken = corpToken;
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceLocation() {
		return deviceLocation;
	}

	public void setDeviceLocation(String deviceLocation) {
		this.deviceLocation = deviceLocation;
	}

	public Timestamp getLastOnlineTime() {
		return lastOnlineTime;
	}

	public void setLastOnlineTime(Timestamp lastOnlineTime) {
		this.lastOnlineTime = lastOnlineTime;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public int getRefreshInterval() {
		return refreshInterval;
	}

	public void setRefreshInterval(int refreshInterval) {
		this.refreshInterval = refreshInterval;
	}

	public String getTransferSignRecordsStamp() {
		return transferSignRecordsStamp;
	}

	public void setTransferSignRecordsStamp(String transferSignRecordsStamp) {
		this.transferSignRecordsStamp = transferSignRecordsStamp;
	}

	public String getTransferUserRecordsStamp() {
		return transferUserRecordsStamp;
	}

	public void setTransferUserRecordsStamp(String transferUserRecordsStamp) {
		this.transferUserRecordsStamp = transferUserRecordsStamp;
	}

	public Integer getAlarmReRec() {
		return alarmReRec;
	}

	public void setAlarmReRec(Integer alarmReRec) {
		this.alarmReRec = alarmReRec;
	}

	public Integer getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(Integer staffNumber) {
		this.staffNumber = staffNumber;
	}

	public Integer getFingerprintNumber() {
		return fingerprintNumber;
	}

	public void setFingerprintNumber(Integer fingerprintNumber) {
		this.fingerprintNumber = fingerprintNumber;
	}

	public Integer getAttendanceRecordsNumber() {
		return attendanceRecordsNumber;
	}

	public void setAttendanceRecordsNumber(Integer attendanceRecordsNumber) {
		this.attendanceRecordsNumber = attendanceRecordsNumber;
	}

	public String getDeviceVersion() {
		return deviceVersion;
	}

	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}

	public String getDeviceIP() {
		return deviceIP;
	}

	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}

	public String getAutoPowerontime() {
		return autoPowerontime;
	}

	public void setAutoPowerontime(String autoPowerontime) {
		this.autoPowerontime = autoPowerontime;
	}

	public String getAutoPowerofftime() {
		return autoPowerofftime;
	}

	public void setAutoPowerofftime(String autoPowerofftime) {
		this.autoPowerofftime = autoPowerofftime;
	}

	public String getFaceAlgorithmVersion() {
		return faceAlgorithmVersion;
	}

	public void setFaceAlgorithmVersion(String faceAlgorithmVersion) {
		this.faceAlgorithmVersion = faceAlgorithmVersion;
	}

	public String getFingerAlgorithmVersion() {
		return fingerAlgorithmVersion;
	}

	public void setFingerAlgorithmVersion(String fingerAlgorithmVersion) {
		this.fingerAlgorithmVersion = fingerAlgorithmVersion;
	}

	public Integer getFacePrintTemplateNum() {
		return facePrintTemplateNum;
	}

	public void setFacePrintTemplateNum(Integer facePrintTemplateNum) {
		this.facePrintTemplateNum = facePrintTemplateNum;
	}

	public Integer getFacePrintNum() {
		return facePrintNum;
	}

	public void setFacePrintNum(Integer facePrintNum) {
		this.facePrintNum = facePrintNum;
	}

	public String getSignalStrength() {
		return signalStrength;
	}

	public void setSignalStrength(String signalStrength) {
		this.signalStrength = signalStrength;
	}

	public String getCellularID() {
		return cellularID;
	}

	public void setCellularID(String cellularID) {
		this.cellularID = cellularID;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public int getOffsiteAtt() {
		return offsiteAtt;
	}

	public void setOffsiteAtt(int offsiteAtt) {
		this.offsiteAtt = offsiteAtt;
	}

	public int getErrorDelay() {
		return errorDelay;
	}

	public void setErrorDelay(int errorDelay) {
		this.errorDelay = errorDelay;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public String getTransTimes() {
		return transTimes;
	}

	public void setTransTimes(String transTimes) {
		this.transTimes = transTimes;
	}

	public int getTransInterval() {
		return transInterval;
	}

	public void setTransInterval(int transInterval) {
		this.transInterval = transInterval;
	}

	public int getRealtime() {
		return realtime;
	}

	public void setRealtime(int realtime) {
		this.realtime = realtime;
	}

	public int getTimeZoneOffset() {
		return timeZoneOffset;
	}

	public void setTimeZoneOffset(int timeZoneOffset) {
		this.timeZoneOffset = timeZoneOffset;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getTimeZoneId() {
		return timeZoneId;
	}

	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}

}
