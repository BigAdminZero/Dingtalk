package com.dingtalk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dingtalk.bean.ScheduleTable;
import com.dingtalk.dao.ScheduleTableMapper;
import com.dingtalk.iservice.IScheduleTbaleService;
import com.dingtalk.vo.EmployeeIdAndSearchdate;

@Service
public class ScheduleTbaleService implements IScheduleTbaleService{
	@Autowired
	ScheduleTableMapper scheduleTableMapper;
	
	public List<ScheduleTable> findScheduleTableBySearchDate(String SearchDate) {
		List<ScheduleTable> scheduleTableByDate = scheduleTableMapper.selectByDate(SearchDate);
		return scheduleTableByDate;
	}
	public int initScheduleTable(ScheduleTable scheduleTable) {
		int result = scheduleTableMapper.initScheduleTable(scheduleTable);
		return result;
	}
	public List<ScheduleTable> findScheduleTableByDeptmentId(String deptmentId) {
		return  scheduleTableMapper.selectScheduleTableByDeptmentId(deptmentId);
	}
	public void modifyScheduleTableByEmployeeId(ScheduleTable scheduleTable) {
		scheduleTableMapper.updateScheduleTableByEmployeeIdAndDate(scheduleTable);
	}
	public int updateByDepartment(ScheduleTable scheduleTable) {
		
		return scheduleTableMapper.updateByDepartment(scheduleTable);
	}
	public int updateByDepartmentOnDuty(ScheduleTable scheduleTable) {
		
		return scheduleTableMapper.updateByDepartmentOnDuty(scheduleTable);
	}
	
	public List<ScheduleTable> selectByEmployeeIdAnDate(EmployeeIdAndSearchdate employeeIdAndSearchdate) {
		
		return scheduleTableMapper.selectByEmployeeIdAnDate(employeeIdAndSearchdate);
	}
	public List<ScheduleTable> ifExist(ScheduleTable scheduleTable) {
		return scheduleTableMapper.ifExist(scheduleTable);
	}

}
