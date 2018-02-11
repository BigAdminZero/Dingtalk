package com.dingtalk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("")
@Controller
public class InterceptorController {

	@RequestMapping("AuthorityError")
	public ModelAndView AuthorityErorr(){
		
		ModelAndView mv=new ModelAndView("AuthorityError");
		
		return mv;
	}
	
}
