package com.dingtalk.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dingtalk.aop.AuthorityManager;
import com.dingtalk.bean.Department;
import com.dingtalk.iservice.IDepartmentService;
import com.dingtalk.utils.DingHttps;

@Controller
@RequestMapping("")
public class DepartmentController {

	@Autowired
	IDepartmentService departmentservice;

	@RequestMapping("showDepartmentManager")
	public ModelAndView showDepartmentManager(){
		
		List<Department> departmentlist=departmentservice.getAllDepartment();
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("DepartmentManager");
		mv.addObject("departmentlist", departmentlist);
		return mv;
	}
	
	@RequestMapping(value="getDepartmentListAjax", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getAllDepartment(HttpSession session) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		AuthorityManager am = (AuthorityManager) ac.getBean("AuthorityManager");
		
		List<Department> departmentList = am.getDepartment(session);
		System.out.println(departmentList.get(0).getDepartmentName());
		String departmentString = "[";
		if (departmentList != null) {
			for (int i = 0; i < departmentList.size(); i++) {
				departmentString = departmentString + "{\"departmentId\":";
				departmentString = departmentString
						+ departmentList.get(i).getDepartmentId()
						+ ",\"departmentName\":\""
						+ departmentList.get(i).getDepartmentName() + "\"}";
				if (i<departmentList.size()-1) {
					departmentString = departmentString + ",";
				}
			}
		}
		departmentString = departmentString + "]";
		System.out.println(departmentString);
		return departmentString;

	}
	
	@RequestMapping("updateDepartment")
	public void UpdateDepartment(HttpServletResponse resp) throws IOException{
		
		PrintWriter out=resp.getWriter();
		
		DingHttps dingUtil=new DingHttps();
		
		String corpid = "ding6d831f98a66c8c8535c2f4657eb6378f";
		String corpsecret = "3LLLfu4Xs81XOOpnXY8l4Ta-Yjwbkae47yQgktL5r6zQYBLGHRXahM9y0POJUzkz";
		String access_token=dingUtil.getAccess_token(corpid, corpsecret);
		JSONArray departmentArray = dingUtil.getDepartment(access_token);
		
		departmentservice.synchronizeByDingding(departmentArray);
		
		out.print(1);
		
		
		
		
	}
}
