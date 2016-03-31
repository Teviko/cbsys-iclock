package com.cbsys.iclock.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_staff")
public class Staff extends BaseEntity {
	private String pin;
	private String password;
	private String name;
	private String card;
	private String grp;
	private String tz;
	private int pri;
	private String serialNumber;
	private String corpToken;

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getGrp() {
		return grp;
	}

	public void setGrp(String grp) {
		this.grp = grp;
	}

	public String getTz() {
		return tz;
	}

	public void setTz(String tz) {
		this.tz = tz;
	}

	public int getPri() {
		return pri;
	}

	public void setPri(int pri) {
		this.pri = pri;
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
