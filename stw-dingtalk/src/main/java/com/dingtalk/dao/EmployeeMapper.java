package com.dingtalk.dao;

import com.dingtalk.bean.Employee;
import com.dingtalk.bean.EmployeeExample;

import java.util.List;
import net.sf.json.JSONArray;
import org.apache.ibatis.annotations.Param;

public interface EmployeeMapper {
    long countByExample(EmployeeExample example);

    int deleteByExample(EmployeeExample example);

    int deleteByPrimaryKey(Long employeeId);

    int insert(Employee record);

    int insertSelective(Employee record);
  //清除员工信息表数据
  	public int delAllEmployee();
  //重置员工信息表数据
  	public int insertEmployee(Employee employee);
    //获取全部员工id
    public List<String> selectAllEmployeeId();
    //根据部门号获取员工Id
    public List<String> selectEmployeeIdBydeptId(Long deptId);
  //根据员工Id获取员工
    public List<Employee> selectByemployeeId(String employeeId);
    
    //通过部门id获取员工信息
    public List<Employee> selectEmployeeByDepartmentId(Long departmentId);
    
  //获取全部employee
    public List<Employee> selectAllEmployee();
    
    List<Employee> selectByExample(EmployeeExample example);

    Employee selectByPrimaryKey(Long employeeId);

    int updateByExampleSelective(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByExample(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
    
    JSONArray selectByDepartmentId(int departmentId);
}