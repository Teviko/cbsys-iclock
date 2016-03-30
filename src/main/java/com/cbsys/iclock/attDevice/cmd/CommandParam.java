package com.cbsys.iclock.attDevice.cmd;

import java.util.HashMap;
import java.util.Map;

import com.cbsys.iclock.AttendanceConstants;
import com.cbsys.iclock.domain.Staff;
import com.cbsys.iclock.domain.StaffFacePrint;
import com.cbsys.iclock.domain.StaffFingerPrint;

/**
 * 
 * 设备命令需要的参数，部分命令
 * 
 * @author albert
 * 
 */
public class CommandParam {
	private Staff staff;
	private int pri = AttendanceConstants.PRI_USER;
	private StaffFingerPrint fp;
	private StaffFacePrint sfp;
	private Map<String, String> options = new HashMap<String, String>();
	private String shell;
	private String deviceSN;
	private String url; //用于PutFile命令
	private String fileName; //用于PutFile命令
	private String stamp;
	private String beginTime;
	private String endTime;
	private String workCode;
	private String workTurnName;

	public String getStamp() {
		return stamp;
	}

	public void setStamp(String stamp) {
		this.stamp = stamp;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getPri() {
		return pri;
	}

	public void setPri(int pri) {
		this.pri = pri;
	}

	public String getDeviceSN() {
		return deviceSN;
	}

	public void setDeviceSN(String deviceSN) {
		this.deviceSN = deviceSN;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public StaffFingerPrint getFp() {
		return fp;
	}

	public void setFp(StaffFingerPrint fp) {
		this.fp = fp;
	}

	public Map<String, String> getOptions() {
		return options;
	}

	public void setOptions(Map<String, String> options) {
		this.options = options;
	}

	public String getShell() {
		return shell;
	}

	public void setShell(String shell) {
		this.shell = shell;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public StaffFacePrint getSfp() {
		return sfp;
	}

	public void setSfp(StaffFacePrint sfp) {
		this.sfp = sfp;
	}

	public String getWorkCode() {
		return workCode;
	}

	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}

	public String getWorkTurnName() {
		return workTurnName;
	}

	public void setWorkTurnName(String workTurnName) {
		this.workTurnName = workTurnName;
	}

}
