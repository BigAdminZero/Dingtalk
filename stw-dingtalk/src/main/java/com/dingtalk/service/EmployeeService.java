package com.dingtalk.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dingtalk.bean.Department;
import com.dingtalk.bean.Employee;
import com.dingtalk.dao.EmployeeMapper;
import com.dingtalk.iservice.IEmployeeService;
import com.dingtalk.utils.DingHttps;

@Service
public class EmployeeService implements IEmployeeService{
	@Autowired
	EmployeeMapper employeeMapper;
	
	//获取全部员工的id
	public List<String> findAllEmployeeId() {
		List<String> allEmployeeId = employeeMapper.selectAllEmployeeId();
		return allEmployeeId;
	}
	
	public List<String> findEmployeedIdBydeptId(Long deptId) {
		List<String> ids = employeeMapper.selectEmployeeIdBydeptId(deptId);
		return ids;
	}

	 //通过部门id获取员工信息
	public List<Employee> findEmployeeByDepartmentId(Long departmentId) {
		
		List<Employee> employeeByDepartmentId = employeeMapper.selectEmployeeByDepartmentId(departmentId);
		
		return employeeByDepartmentId;
	}
	
	//根据员工Id获取员工
	public List<Employee> selectByemployeeId(String employeeId) {
		return employeeMapper.selectByemployeeId(employeeId);
	}

	//钉钉接口获取员工信息
	public List<Employee> getEmployeeFromDingTalk(List<Department> department) {
		DingHttps dh = new DingHttps();
		String corpid = "ding6d831f98a66c8c8535c2f4657eb6378f";
		String corpsecret = "3LLLfu4Xs81XOOpnXY8l4Ta-Yjwbkae47yQgktL5r6zQYBLGHRXahM9y0POJUzkz";
		List<Employee> employeeList = new ArrayList<Employee>();
		for (int i = 0; i < department.size(); i++) {
			JSONArray userArr = dh.getUser(dh.getAccess_token(corpid, corpsecret), department.get(i).getDepartmentId().toString());
			for (int j = 0; j < userArr.size(); j++) {
				Employee employee = new Employee();
				employee.setEmployeeName((String)userArr.getJSONObject(j).get("name"));
				employee.setEmployeeId((String)userArr.getJSONObject(j).get("userid"));
				employee.setDepartmentId(department.get(i).getDepartmentId());
				employeeList.add(employee);
			}
		}
		
		return employeeList;
	}

	public int delAllEmployee() {
		employeeMapper.delAllEmployee();
		return 0;
	}

	public int insertEmployee(Employee employee) {
		employeeMapper.insertEmployee(employee);
		return 0;
	}

	public List<Employee> selectAllEmployee() {
		return employeeMapper.selectAllEmployee();
	}
}
