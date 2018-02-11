package com.dingtalk.service;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dingtalk.bean.Department;
import com.dingtalk.bean.Holiday;
import com.dingtalk.bean.ScheduleGroup;
import com.dingtalk.bean.ScheduleTable;
import com.dingtalk.dao.DepartmentMapper;
import com.dingtalk.dao.EmployeeMapper;
import com.dingtalk.dao.ScheduleGroupMapper;
import com.dingtalk.dao.ScheduleTableMapper;
import com.dingtalk.iservice.IScheduleService;

@Service
public class ScheduleService implements IScheduleService {

	@Autowired
	ScheduleGroupMapper schedueldao;
	@Autowired
	DepartmentMapper departmentdao;
	@Autowired
	EmployeeMapper employeedao;
	@Autowired
	ScheduleTableMapper sTabledao;
	@Autowired
	DepartmentMapper departmentMapper;
	
	public int countByGroupName(String scheduleGroupName) {
		
		return schedueldao.countByGroupName(scheduleGroupName);
	}
	//查询所有部门
	public List<Department> findAllDepartment() {
		return departmentMapper.selectAllDepartment();
	}
	//查询所有排班组
	public List<ScheduleGroup> findAllScheduleGroup() {
		return schedueldao.selectAllScheduleGroup();
	}
	
	//根据ID查询排班组信息;
	public ScheduleGroup findScheduleGroupById(Integer groupId){
		return schedueldao.SelectScheduleGroupById(groupId);
	}
	
	public void addScheduleGroup(ScheduleGroup sgroup, String starttime,
			String endtime, String[] weeks) {
		
		String week="";
		Date startTime=new Date();
		Date endTime=new Date();
		int scheduleGroupId = (int)(Math.random()*(9999-1000+1))+1000;
		
		for(int i=0;i<weeks.length;i++){
			week+=weeks[i];
		}
		
		week=week.trim();
		
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
		
		try {
			startTime=sdf.parse(starttime);
			endTime=sdf.parse(endtime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sgroup.setStartTime(startTime);
		sgroup.setEndTime(endTime);
		sgroup.setWeek(week);
		sgroup.setScheduleGroupId(scheduleGroupId);
		
		schedueldao.insert(sgroup);
		
	}

	public List<ScheduleGroup> getScheduleGroupTemplate() {
		
		List<ScheduleGroup> sgrouplist=schedueldao.selectByExample(null);
		
		return sgrouplist;
	}

	public void deleteById(int scheduleGroupId) {

		schedueldao.deleteByPrimaryKey(scheduleGroupId);
		
	}

	public ScheduleGroup getScheduleByGroupId(int scheduleGroupId) {
		
		return schedueldao.selectByPrimaryKey(scheduleGroupId);
	}

	public void updateScheduleByGroupId(ScheduleGroup sgroup,String starttime,String endtime,String[] weeks) {

		String week="";
		Date startTime=new Date();
		Date endTime=new Date();
		
		for(int i=0;i<weeks.length;i++){
			week+=weeks[i];
		}
		
		week=week.trim();
		
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
		
		try {
			startTime=sdf.parse(starttime);
			endTime=sdf.parse(endtime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sgroup.setStartTime(startTime);
		sgroup.setEndTime(endTime);
		sgroup.setWeek(week);
		
		schedueldao.updateByPrimaryKeySelective(sgroup);
		
	}

	public List<Department> getAllDepaertment() {
		
		List<Department> departmentlist=departmentdao.selectByExample(null);
		
		return departmentlist;
	}

	public JSONArray getEmployeeByDepartId(int departmentId) {

		JSONArray employees=employeedao.selectByDepartmentId(departmentId);
		
		return employees;
	}

	public JSONArray getScheduleTableByEmployeeId(String employeeId) {
		
		JSONArray scheduletable=sTabledao.selectByEmployeeId(employeeId);
		
		return scheduletable;
	}
public JSONArray getScheduleTableByEmployeeIdAndSearchdate(String employeeId,String SearchDate) {
		
		JSONArray scheduletable=sTabledao.getScheduleTableByEmployeeIdAndSearchdate(employeeId,SearchDate);
		
		return scheduletable;
	}
	
	public void updateTableByEmployeeId(JSONArray jsonarray) {

		for(int i=0;i<jsonarray.size();i++){
			ScheduleTable scheduleTable=new ScheduleTable();
			JSONObject job=jsonarray.getJSONObject(i);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm");
			try {
				
				Date date=sdf.parse(job.getString("cordate"));
				Date startTime=sdf1.parse(job.getString("startTime"));
				Date endTime=sdf1.parse(job.getString("endTime"));
				
				scheduleTable.setDate(date);
				scheduleTable.setEmployeeId(job.getString("employeeId"));
				scheduleTable.setEndTime(endTime);
				scheduleTable.setIfOnduty(job.getString("ifOnduty"));
				scheduleTable.setStartTime(startTime);
				scheduleTable.setTypeOnduty(job.getString("typeOnduty"));
				scheduleTable.setScheduleTableId(job.getInt("scheduleTableId"));
				
				sTabledao.updateByTableId(scheduleTable);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//scheduleTable.setDate(jsonarray[i].cordate);
		}
		
	}
	@Override
	public void updateByHoliday(Holiday holiday) {

		sTabledao.updateByHoliday(holiday);
		
	}

}
