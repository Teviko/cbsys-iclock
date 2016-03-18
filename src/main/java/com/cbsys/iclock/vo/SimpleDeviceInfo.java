package com.cbsys.iclock.vo;

public class SimpleDeviceInfo {
	private String sn;
	private long lastOnlineTime;
	private String info;

	public SimpleDeviceInfo(String sn, String info, long lastOnlineTime) {
		this.sn = sn;
		this.lastOnlineTime = lastOnlineTime;
		this.info = info;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public long getLastOnlineTime() {
		return lastOnlineTime;
	}

	public void setLastOnlineTime(long lastOnlineTime) {
		this.lastOnlineTime = lastOnlineTime;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
