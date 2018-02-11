package com.dingtalk.iservice;

import java.util.List;

import com.dingtalk.bean.Department;
import com.dingtalk.bean.Employee;

public interface IEmployeeService {

	public List<String> findAllEmployeeId();

	//钉钉接口获取员工信息
	public List<Employee> getEmployeeFromDingTalk(List<Department> department);
	//清除员工信息表数据
	public int delAllEmployee();
	//重置员工信息表数据
	public int insertEmployee(Employee employee);
    //通过部门id获取员工信息
	public List<Employee> findEmployeeByDepartmentId(Long departmentId);

	//根据部门号获取员工Id
	public List<String> findEmployeedIdBydeptId(Long deptId);
	
	//根据员工Id获取员工
    public List<Employee> selectByemployeeId(String employeeId);
    //获取全部employee
    public List<Employee> selectAllEmployee();
	
}
