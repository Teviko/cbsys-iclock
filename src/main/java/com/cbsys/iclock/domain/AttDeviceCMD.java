package com.cbsys.iclock.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.common.util.StringHelper;

import com.cbsys.iclock.AttendanceConstants;

@Entity
@Table(name = "t_att_device_cmd")
public class AttDeviceCMD extends BaseEntity {

	private long cmdSN;
	private String cmd;
	private int cmdType;
	private int flag = AttendanceConstants.FLAG_NEW;
	private int cmdOrder = AttendanceConstants.ORDER_COMMON;
	private String cmdResult;
	private String serialNumber;
	private String corpToken;
	private int isHasException = 0;//是否为异常命令 0为无异常 1为有异常
	private int cmdErrorCount = 0;//命令超时次数,默认为0
	private Timestamp sendTime; // 命令发送时间
	private Timestamp resultTime; // 命令返回时间，如果有返回的

	public long getCmdSN() {
		return cmdSN;
	}

	public void setCmdSN(long cmdSN) {
		this.cmdSN = cmdSN;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public int getCmdType() {
		return cmdType;
	}

	public void setCmdType(int cmdType) {
		this.cmdType = cmdType;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getCmdOrder() {
		return cmdOrder;
	}

	public void setCmdOrder(int order) {
		this.cmdOrder = order;
	}

	public String getCmdResult() {
		return cmdResult;
	}

	public void setCmdResult(String cmdResult) {
		this.cmdResult = cmdResult;
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

	public int getIsHasException() {
		return isHasException;
	}

	public void setIsHasException(int isHasException) {
		this.isHasException = isHasException;
	}

	public int getCmdErrorCount() {
		return cmdErrorCount;
	}

	public void setCmdErrorCount(int cmdErrorCount) {
		this.cmdErrorCount = cmdErrorCount;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public Timestamp getResultTime() {
		return resultTime;
	}

	public void setResultTime(Timestamp resultTime) {
		this.resultTime = resultTime;
	}

	public String getDeviceCMD() {
		if (StringHelper.isEmpty(cmd))
			return null;
		StringBuilder command = new StringBuilder();
		command.append(AttendanceConstants.CMD_PREFIX).append(AttendanceConstants.CMD_SEP).append(cmdSN).append(AttendanceConstants.CMD_SEP).append(cmd);
		return command.toString();
	}
}
