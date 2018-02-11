package com.dingtalk.aop;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dingtalk.bean.Department;
import com.dingtalk.bean.User;
import com.dingtalk.iservice.IDepartmentService;

@Component("AuthorityManager")
public class AuthorityManager {
	@Autowired
	IDepartmentService departmentservice;
	public List<Department> getDepartment(HttpSession session){
		User user = (User) session.getAttribute("nowUser");
		List<Department> dlist=new ArrayList<Department>();
		if (user.getDepartmentId()==0) {
			dlist=departmentservice.getAllDepartment();
		} else {
			dlist=departmentservice.selectDepartmentById(user.getDepartmentId());
		}
		
		return dlist;
	}
}
