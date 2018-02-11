package com.dingtalk.iservice;
import java.util.List;

import com.dingtalk.bean.Department;

import net.sf.json.JSONArray;

import com.dingtalk.bean.Department;
import com.dingtalk.bean.Employee;
import com.dingtalk.bean.Holiday;
import com.dingtalk.bean.ScheduleGroup;
import com.dingtalk.bean.ScheduleTable;

/**
 * 排班组service层
 * @author yliu
 *
 */
public interface IScheduleService {
	
	/**
	 * 根据排版组名 判断名称是否存在
	 * @param scheduleGroupName
	 * @return 返回1代表存在  返回0代表不存在
	 */
	int countByGroupName(String scheduleGroupName);
	//查找所有部门
	public List<Department> findAllDepartment();
	//查找所有排班组
	 public  List<ScheduleGroup> findAllScheduleGroup();
	//根据Id查询排班组
	 public  ScheduleGroup findScheduleGroupById(Integer groupId);
	
	/**
	 * 新增排班组
	 * @param sgroup
	 * @param starttime
	 * @param endtime
	 * @param weeks
	 */
	void addScheduleGroup(ScheduleGroup sgroup,String starttime,String endtime,String[] weeks);
	
	/**
	 * 查询所有的排版组
	 * @return
	 */
	List<ScheduleGroup> getScheduleGroupTemplate();
	
	/**
	 * 根据id删除排班组
	 * @param scheduleGroupId
	 */
	void deleteById(int scheduleGroupId);
	
	/**
	 * 根据排班组id获取该id对应的排班组信息
	 * @param scheduleGroupId
	 * @return
	 */
	ScheduleGroup getScheduleByGroupId(int scheduleGroupId);
	
	/**
	 * 修改排班组信息
	 * @param sgroup
	 * @param starttime
	 * @param endtime
	 * @param weeks
	 */
	void updateScheduleByGroupId(ScheduleGroup sgroup,String starttime,String endtime,String[] weeks);
	
	/**
	 * 得到所有的部门
	 * @return
	 */
	List<Department> getAllDepaertment();
	
	/**
	 * 根据部门id得到该部门的所有员工
	 * @param departmentId
	 * @return
	 */
	JSONArray getEmployeeByDepartId(int departmentId);
	
	/**
	 * 根据员工id得到个人对应的排班信息
	 * @param employeeId
	 * @return
	 */
	JSONArray getScheduleTableByEmployeeId(String employeeId);
	
	/**
	 * 根据员工进行排班
	 * @param jsonarray 所要修改的日期时间
	 */
	void updateTableByEmployeeId(JSONArray jsonarray);
	
	/**
	 * 按照假期安排进行排班 即将所有人对应的假期值班表置为默认情况
	 * @param startDate
	 * @param endDate
	 */
	void updateByHoliday(Holiday holiday);
	
    public JSONArray getScheduleTableByEmployeeIdAndSearchdate(String employeeId,String SearchDate);
}
