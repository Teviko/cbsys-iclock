package com.cbsys.iclock.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_att_oplog")
public class Oplog extends BaseEntity {
	private String serialNumber;//sn
	private String corpToken;
	private Integer opCode;
	private Integer adminId;
	private String opObject1;//reserve1
	private String opObject2;//reserve2
	private String opObject3;//reserve3
	private String opObject4;//reserve4

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

	public Integer getOpCode() {
		return opCode;
	}

	public void setOpCode(Integer opCode) {
		this.opCode = opCode;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getOpObject1() {
		return opObject1;
	}

	public void setOpObject1(String opObject1) {
		this.opObject1 = opObject1;
	}

	public String getOpObject2() {
		return opObject2;
	}

	public void setOpObject2(String opObject2) {
		this.opObject2 = opObject2;
	}

	public String getOpObject3() {
		return opObject3;
	}

	public void setOpObject3(String opObject3) {
		this.opObject3 = opObject3;
	}

	public String getOpObject4() {
		return opObject4;
	}

	public void setOpObject4(String opObject4) {
		this.opObject4 = opObject4;
	}

}
