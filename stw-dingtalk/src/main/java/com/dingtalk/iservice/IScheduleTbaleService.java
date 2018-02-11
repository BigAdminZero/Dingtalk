package com.dingtalk.iservice;

import java.util.List;

import com.dingtalk.bean.ScheduleTable;
import com.dingtalk.vo.EmployeeIdAndSearchdate;

public interface IScheduleTbaleService {
	
	public List<ScheduleTable> findScheduleTableBySearchDate(String SearchDate);
	public List<ScheduleTable> ifExist(ScheduleTable scheduleTable);
	public int initScheduleTable(ScheduleTable scheduleTable);
	
	 //根据部门Id查询排班表信息;
	 public List<ScheduleTable> findScheduleTableByDeptmentId(String DeptmentId);
	//根据员工Id和日期更新排班表;
	 public void modifyScheduleTableByEmployeeId(ScheduleTable scheduleTable);
	//根据id和日期更新值班信息
	 public int updateByDepartment(ScheduleTable scheduleTable);
	 //根据id和日期更新信息
	 public int updateByDepartmentOnDuty(ScheduleTable scheduleTable);
	//按员工id和时间查询排班表
	 public List<ScheduleTable> selectByEmployeeIdAnDate(EmployeeIdAndSearchdate employeeIdAndSearchdate);
}
