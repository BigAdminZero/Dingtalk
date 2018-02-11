package com.dingtalk.bean;

import java.util.Date;

public class ScheduleGroup {
    private Integer scheduleGroupId;

    private String scheduleGroupName;

    private Date startTime;

    private Date endTime;

    private String week;

    public Integer getScheduleGroupId() {
        return scheduleGroupId;
    }

    public void setScheduleGroupId(Integer scheduleGroupId) {
        this.scheduleGroupId = scheduleGroupId;
    }

    public String getScheduleGroupName() {
        return scheduleGroupName;
    }

    public void setScheduleGroupName(String scheduleGroupName) {
        this.scheduleGroupName = scheduleGroupName == null ? null : scheduleGroupName.trim();
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week == null ? null : week.trim();
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

    
}