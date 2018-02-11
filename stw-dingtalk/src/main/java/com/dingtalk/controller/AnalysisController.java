package com.dingtalk.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dingtalk.aop.AuthorityManager;
import com.dingtalk.bean.Department;
import com.dingtalk.iservice.IScheduleService;

@Controller
public class AnalysisController {
	
	
	@Autowired
	IScheduleService scheduleservice;
	
	
	/**
	 * 考勤统计管理页面
	 * @return
	 */
	@RequestMapping("showAnalysis")
	public ModelAndView showAnalysisPage(HttpSession session){
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		AuthorityManager am = (AuthorityManager) ac.getBean("AuthorityManager");
		
		List<Department> departmentlist=am.getDepartment(session);;
		ModelAndView mv=new ModelAndView();
		
		mv.setViewName("showAnalysis");
		
		mv.addObject("DepartmentList", departmentlist);
		
		return mv;
	}
	
}
