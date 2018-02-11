package com.dingtalk.dao;

import com.dingtalk.bean.Holiday;
import com.dingtalk.bean.ScheduleTable;
import com.dingtalk.bean.ScheduleTableExample;
import com.dingtalk.vo.EmployeeIdAndSearchdate;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.ibatis.annotations.Param;

public interface ScheduleTableMapper {
    long countByExample(ScheduleTableExample example);

    int deleteByExample(ScheduleTableExample example);

    int deleteByPrimaryKey(Integer scheduleTableId);

    int insert(ScheduleTable record);

    public List<ScheduleTable> ifExist(ScheduleTable scheduleTable);
    
    int insertSelective(ScheduleTable record);
   //增加部门值班员工
  	public int updateByDepartment(ScheduleTable scheduleTable);
  	//删除
  	public int updateByDepartmentOnDuty(ScheduleTable scheduleTable);
    List<ScheduleTable> selectByExample(ScheduleTableExample example);
    //根据部门号查询排班表;
    public List<ScheduleTable> selectScheduleTableByDeptmentId(String deptmentId);
    //根据日期和员工号更新排班表 ;
    public void updateScheduleTableByEmployeeIdAndDate(ScheduleTable scheduleTable);

    //根据排班记录id更新排班表
    public void updateByTableId(ScheduleTable scheduleTable);
    
    //按日期查找排班表
    public List<ScheduleTable> selectByDate(String searchDate);
    
    //初始化排班表
    public int initScheduleTable(ScheduleTable scheduleTable);
    
    //按员工id和时间查询排班表
    public List<ScheduleTable> selectByEmployeeIdAnDate(EmployeeIdAndSearchdate employeeIdAndSearchdate);
    
    ScheduleTable selectByPrimaryKey(Integer scheduleTableId);

    int updateByExampleSelective(@Param("record") ScheduleTable record, @Param("example") ScheduleTableExample example);

    int updateByExample(@Param("record") ScheduleTable record, @Param("example") ScheduleTableExample example);

    int updateByPrimaryKeySelective(ScheduleTable record);

    int updateByPrimaryKey(ScheduleTable record);
    
    JSONArray selectByEmployeeId(String employeeId);
    
    public JSONArray getScheduleTableByEmployeeIdAndSearchdate(@Param("employeeId") String employeeId,@Param("SearchDate") String SearchDate);
    
    /**
     * 按照节假日进行排班
     * @param holiday
     */
    void updateByHoliday(Holiday holiday);
}