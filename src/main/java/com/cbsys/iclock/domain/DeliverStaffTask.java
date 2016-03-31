package com.cbsys.iclock.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_deliver_staff_task")
public class DeliverStaffTask extends BaseEntity {
	private String clockId;
	private String corpToken;
	private Long staffId;
	private String fromDevice;
	private String toDevice;
	private int opFlag = 1; //1:新增，2:删除

	public String getClockId() {
		return clockId;
	}

	public void setClockId(String clockId) {
		this.clockId = clockId;
	}

	public String getCorpToken() {
		return corpToken;
	}

	public void setCorpToken(String corpToken) {
		this.corpToken = corpToken;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public String getToDevice() {
		return toDevice;
	}

	public void setToDevice(String toDevice) {
		this.toDevice = toDevice;
	}

	public String getFromDevice() {
		return fromDevice;
	}

	public void setFromDevice(String fromDevice) {
		this.fromDevice = fromDevice;
	}

	public int getOpFlag() {
		return opFlag;
	}

	public void setOpFlag(int opFlag) {
		this.opFlag = opFlag;
	}

}
