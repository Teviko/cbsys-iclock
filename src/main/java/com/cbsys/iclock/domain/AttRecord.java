package com.cbsys.iclock.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.cbsys.iclock.utils.ClockUtils;

@Entity
@Table(name = "t_att_records")
public class AttRecord extends BaseEntity {
	private String deviceSN;
	private String corpToken;
	private String clockId;
	private long utcAttTime;
	private int timezoneoffset;
	private int stauts;
	private int verifyType = -1;
	private String workCode;
	private String recordStr;//保存考勤机发来的整体记录
	private String reserve1;
	private String reserve2;
	private int syncFlag = 0;

	public String getDeviceSN() {
		return deviceSN;
	}

	public void setDeviceSN(String deviceSN) {
		this.deviceSN = deviceSN;
	}

	public String getCorpToken() {
		return corpToken;
	}

	public void setCorpToken(String corpToken) {
		this.corpToken = corpToken;
	}

	public String getClockId() {
		return clockId;
	}

	public void setClockId(String clockId) {
		this.clockId = clockId;
	}

	public int getTimezoneoffset() {
		return timezoneoffset;
	}

	public void setTimezoneoffset(int timezoneoffset) {
		this.timezoneoffset = timezoneoffset;
	}

	public long getUtcAttTime() {
		return utcAttTime;
	}

	public void setUtcAttTime(long utcAttTime) {
		this.utcAttTime = utcAttTime;
	}

	public int getStauts() {
		return stauts;
	}

	public void setStauts(int stauts) {
		this.stauts = stauts;
	}

	public int getVerifyType() {
		return verifyType;
	}

	public void setVerifyType(int verifyType) {
		this.verifyType = verifyType;
	}

	public String getWorkCode() {
		return workCode;
	}

	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}

	public String getRecordStr() {
		return recordStr;
	}

	public void setRecordStr(String recordStr) {
		this.recordStr = recordStr;
	}

	public String getReserve1() {
		return reserve1;
	}

	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}

	public String getReserve2() {
		return reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	public int getSyncFlag() {
		return syncFlag;
	}

	public void setSyncFlag(int syncFlag) {
		this.syncFlag = syncFlag;
	}

	@Override
	public boolean equals(Object another) {
		if (another instanceof AttRecord) {
			if (id == null)
				return false;
			else
				return id.equals(((AttRecord) another).getId());
		} else
			return false;
	}

	public String makeSyncMsg() {
		StringBuilder msg = new StringBuilder(clockId);
		msg.append(",").append(ClockUtils.transToOffsetDatetime(utcAttTime, timezoneoffset)).append(",");
		msg.append(stauts).append(",").append(verifyType).append(",").append(workCode);
		return msg.toString();
	}
}
