package com.dingtalk.controller;

import java.lang.ProcessBuilder.Redirect;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dingtalk.bean.Department;
import com.dingtalk.bean.User;
import com.dingtalk.iservice.IAuthorityService;
import com.dingtalk.iservice.IScheduleService;

@RequestMapping("")
@Controller
public class AuthorityController {

	@Autowired
	IAuthorityService authority;
	@Autowired
	IScheduleService scheduleservice;
	
	@RequestMapping("showAuthorityPage")
	public ModelAndView showAuthorityPage(HttpSession session){
		
		User user=(User) session.getAttribute("nowUser");
		
		List<Department> departmentlist=scheduleservice.getAllDepaertment();
		
		Long createrId=user.getUserId();
		
		//Long createrId=(long) 2;
		
		List<User> createUserList=authority.selectByCreaterId(createrId);
		
		ModelAndView mv=new ModelAndView("authorityManage");
		
		mv.addObject("userlist", createUserList);
		mv.addObject("DepartmentList", departmentlist);
		
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="getAuthorityName",produces="application/json;charset=UTF-8")
	public String getAuthorityName(){
		
		JSONArray authorityNames=authority.selectAllAuthorityName();
		
		return authorityNames.toString();
	}
	
	
	@RequestMapping("adduser")
	public String addUser(User user,HttpSession session){
		
		User nowUser=(User) session.getAttribute("nowUser");
		
		user.setCreaterId(nowUser.getUserId());
		
		if(user.getDepartmentId()==null){
			user.setDepartmentId((long) 0);
		}
		
		authority.insertUser(user);
		
		return "redirect:/showAuthorityPage";
	}
	
	@RequestMapping("modifyPwd")
	public String modifyPwd(User user){
		
		authority.updateUserByUserID(user);
		
		return "redirect:/showAuthorityPage";
	}
	
	@RequestMapping("editAuthority")
	public String editAuthority(User user){
		System.out.println(user.getDepartmentId());
		
		if(user.getDepartmentId()==null){
			user.setDepartmentId((long) 0);
		}
		authority.updateUserByUserID(user);
		
		return "redirect:/showAuthorityPage";
	}
	
	@RequestMapping("deleteUser")
	public void deleteUser(String[] chk_vals){
		
		for(int i=0;i<chk_vals.length;i++){
			Long userId=Long.parseLong(chk_vals[i]);
			authority.deleteUserByUserId(userId);
		}
		
	}
	
}
