package com.dingtalk.bean;

public class OndutyGroup {
    private Integer ondutyGroupId;

    private Long departmentId;
    
    public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	private String ondutyGroupName;

    private String employeeId;

    public Integer getOndutyGroupId() {
        return ondutyGroupId;
    }

    public void setOndutyGroupId(Integer ondutyGroupId) {
        this.ondutyGroupId = ondutyGroupId;
    }

    public String getOndutyGroupName() {
        return ondutyGroupName;
    }

    public void setOndutyGroupName(String ondutyGroupName) {
        this.ondutyGroupName = ondutyGroupName == null ? null : ondutyGroupName.trim();
    }

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

   
}