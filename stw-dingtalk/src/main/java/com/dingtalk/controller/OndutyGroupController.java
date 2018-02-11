package com.dingtalk.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dingtalk.aop.AuthorityManager;
import com.dingtalk.bean.Department;
import com.dingtalk.bean.Employee;
import com.dingtalk.bean.OndutyGroup;
import com.dingtalk.iservice.IEmployeeService;
import com.dingtalk.iservice.IOndutyGroupService;
import com.dingtalk.vo.OndutyGroupWIithEmployeeName;

@Controller
@RequestMapping("")
public class OndutyGroupController {

	@Autowired
	IOndutyGroupService ondutyGroupService;
	@Autowired
	IEmployeeService employeeservice;

	@RequestMapping("showOndutyGroup")
	public ModelAndView DutyGroup(HttpSession session) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		AuthorityManager am = (AuthorityManager) ac.getBean("AuthorityManager");
		List<OndutyGroupWIithEmployeeName> ondutyGroupWithName = new ArrayList<OndutyGroupWIithEmployeeName>();
		
		List<Department> departmentList = am.getDepartment(session);
		for (int k = 0; k < departmentList.size(); k++) {
			Long departmentId = departmentList.get(k).getDepartmentId();
			List<OndutyGroup> ondutyGroup = ondutyGroupService
					.findOndutyGroupByDepartmentId(departmentId);
			List<Employee> employeeList = employeeservice
					.findEmployeeByDepartmentId(departmentId);
			for (int i = 0; i < ondutyGroup.size(); i++) {
				String employeeIdList = ondutyGroup.get(i).getEmployeeId();
				String employeeSubstring = employeeIdList.substring(1,
						employeeIdList.length() - 1);
				String[] employeeId = employeeSubstring.split(",");
				String employeeName = "";
				for (int j = 0; j < employeeId.length; j++) {
					for (int j2 = 0; j2 < employeeList.size(); j2++) {
						if (employeeId[j].equals(employeeList.get(j2)
								.getEmployeeId())) {
							employeeName = employeeName
									+ employeeList.get(j2).getEmployeeName() + "；";
						}
					}
				}
				OndutyGroupWIithEmployeeName ondutyGroupWIithEmployeeName = new OndutyGroupWIithEmployeeName();
				ondutyGroupWIithEmployeeName.setDepartmentId(ondutyGroup.get(i)
						.getDepartmentId());
				ondutyGroupWIithEmployeeName.setEmployeeName(employeeName);
				ondutyGroupWIithEmployeeName.setOndutyGroupId(ondutyGroup.get(i)
						.getOndutyGroupId());
				ondutyGroupWIithEmployeeName.setOndutyGroupName(ondutyGroup.get(i)
						.getOndutyGroupName());
				ondutyGroupWithName.add(ondutyGroupWIithEmployeeName);
			}
		}
		
		

		ModelAndView mv = new ModelAndView();

		mv.setViewName("ondutygroupediting");

		session.setAttribute("ondutyGroupList", ondutyGroupWithName);

		return mv;
	}

	@RequestMapping("updateOndutyGroup")
	public ModelAndView updateOndutyGroup(HttpSession session,
			String[] employeeList, String ondutyGroupId) {
		String employeeId = "[";

		if (employeeList != null) {
			for (int i = 0; i < employeeList.length - 1; i++) {
				employeeId = employeeId + employeeList[i] + ",";
			}
			employeeId = employeeId + employeeList[employeeList.length - 1]
					+ "]";
		} else {
			employeeId = employeeId + "]";
		}

		OndutyGroup record = new OndutyGroup();

		record.setOndutyGroupId(Integer.parseInt(ondutyGroupId));
		record.setEmployeeId(employeeId);

		ondutyGroupService.updateOndutyGroupEmployeeByGroupId(record);

		return DutyGroup(session);
	}

	@RequestMapping("addOndutyGroup")
	public ModelAndView addOndutyGroup(HttpSession session, String groupName,
			String departmentList, String[] employeeByDepartmentList) {
		Long departmentId = Long.parseLong(departmentList);

		String employeeId = "[";

		if (employeeByDepartmentList != null) {
			for (int i = 0; i < employeeByDepartmentList.length - 1; i++) {
				employeeId = employeeId + employeeByDepartmentList[i] + ",";
			}
			employeeId = employeeId
					+ employeeByDepartmentList[employeeByDepartmentList.length - 1]
					+ "]";
		} else {
			employeeId = employeeId + "]";
		}
		
		OndutyGroup record = new OndutyGroup();
		record.setDepartmentId(departmentId);
		record.setEmployeeId(employeeId);
		record.setOndutyGroupName(groupName);
		
		ondutyGroupService.addOndutyGroup(record);
		
		return DutyGroup(session);
	}

	

	@RequestMapping(value="deleteOndutyGroup", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String deleteOndutyGroup(String ondutyGroupIdString){
		
		String[] ondutyGroupIdArr = ondutyGroupIdString.split(",");
		for (int i = 0; i < ondutyGroupIdArr.length; i++) {
			Integer ondutyGroupId = Integer.parseInt(ondutyGroupIdArr[i]);
			ondutyGroupService.deleteOndutyGroup(ondutyGroupId);
		}
		
		return null;
	}
}
