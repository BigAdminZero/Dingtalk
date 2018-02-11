package com.dingtalk.vo;

import java.util.Date;

public class DepartmentOnDuty {
	private String employeeId;
	
	private String typeOnduty;
	
	private String employeeName;
	
	private Date date;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getTypeOnduty() {
		return typeOnduty;
	}

	public void setTypeOnduty(String typeOnduty) {
		this.typeOnduty = typeOnduty;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
