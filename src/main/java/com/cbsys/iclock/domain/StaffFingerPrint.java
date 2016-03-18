package com.cbsys.iclock.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_staff_fingerprint")
public class StaffFingerPrint extends BaseEntity {

	private String pin;
	private int fid;
	private int size;
	private int valid;
	private String tmp;
	private String serialNumber;
	private Long staffid;
	private String corpToken;

	public Long getStaffid() {
		return staffid;
	}

	public void setStaffid(Long staffid) {
		this.staffid = staffid;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}

	public String getTmp() {
		return tmp;
	}

	public void setTmp(String tmp) {
		this.tmp = tmp;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getCorpToken() {
		return corpToken;
	}

	public void setCorpToken(String corpToken) {
		this.corpToken = corpToken;
	}

}
