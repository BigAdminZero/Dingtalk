package com.dingtalk.bean;

import java.util.Date;

public class ScheduleTable {
	private Integer scheduleTableId;

	private String employeeId;

	private Date date;

	private Date startTime;

	private Date endTime;

	private String ifOnduty;

	private String typeOnduty;

	public Integer getScheduleTableId() {
		return scheduleTableId;
	}

	public void setScheduleTableId(Integer scheduleTableId) {
		this.scheduleTableId = scheduleTableId;
	}

	public Date getDate() {
		return date;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getIfOnduty() {
		return ifOnduty;
	}

	public void setIfOnduty(String ifOnduty) {
		this.ifOnduty = ifOnduty == null ? null : ifOnduty.trim();
	}

	public String getTypeOnduty() {
		return typeOnduty;
	}

	public void setTypeOnduty(String typeOnduty) {
		this.typeOnduty = typeOnduty == null ? null : typeOnduty.trim();
	}
}