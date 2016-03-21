package com.cbsys.iclock.vo;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class DeviceInfo {
	private String sn;
	private String deviceMode;
	private int deviceType;
	private String corpToken;
	private Long id;
	private volatile long lastOnlineTime;
	private volatile long dbLastOnlineTime;
	private long cmds;
	private int offsiteAtt;
	private int timeZoneOffset;
	private String cmdInfoDeviceMode;
	private AtomicReference<String> stamp = new AtomicReference<String>(null);;
	private AtomicReference<String> opStamp = new AtomicReference<String>(null);;
	private AtomicReference<String> photoStamp = new AtomicReference<String>(null);;
	private AtomicReference<String> info = new AtomicReference<String>(null);
	private AtomicBoolean modified = new AtomicBoolean(false);

	public DeviceInfo() {

	}

	public DeviceInfo(Object[] fromDB) {
		if (fromDB == null || fromDB.length < 8)
			return;
		this.id = fromDB[0] == null ? 0 : new Long(fromDB[0].toString());
		this.sn = fromDB[1] == null ? null : fromDB[1].toString();
		this.deviceMode = fromDB[2] == null ? null : fromDB[2].toString();
		this.dbLastOnlineTime = Long.parseLong(fromDB[3].toString());
		this.offsiteAtt = Integer.parseInt(fromDB[4].toString());
		this.deviceType = Integer.parseInt(fromDB[5].toString());
		this.corpToken = fromDB[6].toString();
		this.timeZoneOffset = Integer.parseInt(fromDB[7].toString());
	}

	public String getCorpToken() {
		return corpToken;
	}

	public void setCorpToken(String corpToken) {
		this.corpToken = corpToken;
	}

	public String getDeviceMode() {
		return deviceMode;
	}

	public void setDeviceMode(String deviceMode) {
		this.deviceMode = deviceMode;
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getLastOnlineTime() {
		return lastOnlineTime;
	}

	public void setLastOnlineTime(long lastOnlineTime) {
		this.lastOnlineTime = lastOnlineTime;
	}

	public long getDbLastOnlineTime() {
		return dbLastOnlineTime;
	}

	public void setDbLastOnlineTime(long dbLastOnlineTime) {
		this.dbLastOnlineTime = dbLastOnlineTime;
	}

	public long getCmds() {
		return cmds;
	}

	public void setCmds(long cmds) {
		this.cmds = cmds;
	}

	public int getTimeZoneOffset() {
		return timeZoneOffset;
	}

	public void setTimeZoneOffset(int timeZoneOffset) {
		this.timeZoneOffset = timeZoneOffset;
	}

	public AtomicReference<String> getInfo() {
		return info;
	}

	public String getInfoV() {
		return info.get();
	}

	public void setInfo(String info) {
		this.info.set(info);
	}

	public void setInfo(AtomicReference<String> info) {
		this.info = info;
	}

	public boolean isModified() {
		return modified.get();
	}

	public AtomicBoolean getModified() {
		return modified;
	}

	public int getOffsiteAtt() {
		return offsiteAtt;
	}

	public void setOffsiteAtt(int offsiteAtt) {
		this.offsiteAtt = offsiteAtt;
	}

	public String getCmdInfoDeviceMode() {
		return cmdInfoDeviceMode;
	}

	public void setCmdInfoDeviceMode(String cmdInfoDeviceMode) {
		this.cmdInfoDeviceMode = cmdInfoDeviceMode;
	}

	public String getStamp() {
		return stamp.get();
	}

	public void setStamp(String stamp) {
		this.stamp.set(stamp);
	}

	public String getOpStamp() {
		return opStamp.get();
	}

	public void setOpStamp(String opStamp) {
		this.opStamp.set(opStamp);
	}

	public String getPhotoStamp() {
		return photoStamp.get();
	}

	public void setPhotoStamp(String photoStamp) {
		this.photoStamp.set(photoStamp);
	}

	public void resetStamp(String expect) {
		stamp.compareAndSet(expect, null);
	}

	public void resetOpStamp(String expect) {
		opStamp.compareAndSet(expect, null);
	}

	public void resetPhotoStamp(String expect) {
		photoStamp.compareAndSet(expect, null);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(sn);
		str.append("：lastOnlineTime = ").append(lastOnlineTime).append("；dbLastOnlineTime = ").append(dbLastOnlineTime);
		str.append("：info = ").append(info.get()).append("；cmds = ").append(cmds);
		return str.toString();

	}

}
