package com.dingtalk.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dingtalk.bean.Department;
import com.dingtalk.bean.Employee;
import com.dingtalk.iservice.IDepartmentService;
import com.dingtalk.iservice.IEmployeeService;
import com.dingtalk.iservice.IScheduleService;


@Controller
@RequestMapping("")
public class EmployeeController {
	
	@Autowired
	IEmployeeService employeeservice;
	
	@Autowired
	IScheduleService scheduleservice;
	
	@Autowired
	IDepartmentService departmentservice;
	
	@RequestMapping("showEmployeeManager")
	public ModelAndView showEmployeeManager(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("EmployeeManager");
		return mv;
	}

	@RequestMapping(value="getDepartmentListByDepartmentId", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getDepartmentListByDepartmentId(Department department,HttpServletRequest req){
		
		Long departmentId =Long.parseLong(req.getParameter("departmentId"));
		//String nameString = req.getParameter("nameString");
		//System.out.println(departmentId);
		JSONArray empolyeeArray = new JSONArray();
		
		List<Employee> employeeList = employeeservice.findEmployeeByDepartmentId(departmentId);
		List<Department> departmentList = scheduleservice.findAllDepartment();
		//System.out.println(employeeList.get(0).getEmployeeId());
		for (int i = 0; i < employeeList.size(); i++) {
			JSONObject employeeObject = new JSONObject();
			Employee employee = new Employee();
			employee = employeeList.get(i);
			employeeObject.put("employeeId", employee.getEmployeeId());
			employeeObject.put("employeeName", ""+employee.getEmployeeName());
			//System.out.println(employee.getEmployeeName());
			for (int j = 0; j < departmentList.size(); j++) {
				Long departmId = departmentList.get(j).getDepartmentId();
				
				
				if (departmId.equals(employee.getDepartmentId())) {
					employeeObject.put("departmentName",""+departmentList.get(j).getDepartmentName());
				}
			}
			
			empolyeeArray.add(employeeObject);
		}
		//System.out.println(empolyeeArray.toString());
		return empolyeeArray.toString();
	}
	
	@RequestMapping(value="updateEmployee", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateEmployee() {
		//获取全部部门id
		List<Department> allDepartment = departmentservice.getAllDepartment();
		
		//通过钉钉接口获取部门员工信息
		List<Employee> employeeFromDingTalk = employeeservice.getEmployeeFromDingTalk(allDepartment);
		
		//清除员工信息表数据
		employeeservice.delAllEmployee();
		
		//重置员工信息表数据
		for (int i = 0; i < employeeFromDingTalk.size(); i++) {
			if (!"051433023137639891".equals(employeeFromDingTalk.get(i).getEmployeeId())) {
				employeeservice.insertEmployee(employeeFromDingTalk.get(i));
			}
		}
		
		
		return null;
	}
	
	@RequestMapping(value="getEmployee", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getEmployee() {
		List<Employee> selectAllEmployee = employeeservice.selectAllEmployee();
		JSONArray employeeArr = new JSONArray();
		for (int i = 0; i < selectAllEmployee.size(); i++) {
			JSONObject employeeObj = new JSONObject();
			List<Department> allDepartment = departmentservice.getAllDepartment();
			for (int j = 0; j < allDepartment.size(); j++) {
				if (allDepartment.get(j).getDepartmentId().equals(selectAllEmployee.get(i).getDepartmentId())) {
					employeeObj.put("departmentName", allDepartment.get(j).getDepartmentName());
				}
			}
			
			employeeObj.put("employeeId", selectAllEmployee.get(i).getEmployeeId());
			employeeObj.put("employeeName", selectAllEmployee.get(i).getEmployeeName());
			
			employeeArr.add(employeeObj);
			
		}
		return employeeArr.toString();
	}
	
}

