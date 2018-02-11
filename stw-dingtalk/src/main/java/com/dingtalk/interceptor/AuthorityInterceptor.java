package com.dingtalk.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.dingtalk.vo.UserWithAuthorityLevel;

public class AuthorityInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
			Object obj) throws Exception {

		String path = req.getRequestURI();
		
		//System.out.println("进入拦截器："+path);
//	        //return false表示拦截，不向下执行
//	         //return true表示放行
//	        System.out.println(request.getServletPath());
//	        HttpSession session = request.getSession();
		 UserWithAuthorityLevel user=(UserWithAuthorityLevel)req.getSession().getAttribute("nowUser");
//	        if(u!=null){
//	            return true;
//	        }else{
//	            return false;
//	        }
		 
		 
		 
		 
		 
		 
		 if(user!=null&&user.getAuthorityLevel()==2){
			if (path.indexOf("/attendanceRecord")>-1) {
				req.getRequestDispatcher("/AuthorityError").forward(req,resp);
				return false;
			}else if (path.indexOf("/attendanceResult")>-1) {
				req.getRequestDispatcher("/AuthorityError").forward(req,resp);
				return false;
			}else if (path.indexOf("/showScheduleTemplatePage")>-1) {
				req.getRequestDispatcher("/AuthorityError").forward(req,resp);
				return false;
			}else if (path.indexOf("/scheduleByEmployee")>-1) {
				req.getRequestDispatcher("/AuthorityError").forward(req,resp);
				return false;
			}else if (path.indexOf("/scheduleByHoliday")>-1) {
				req.getRequestDispatcher("/AuthorityError").forward(req,resp);
				return false;
			}else if (path.indexOf("/scheduleByWork")>-1) {
				req.getRequestDispatcher("/AuthorityError").forward(req,resp);
				return false;
			}else if (path.indexOf("/showScheduleByDepartment")>-1) {
				req.getRequestDispatcher("/AuthorityError").forward(req,resp);
				return false;
			}else if (path.indexOf("/showDepartmentManager")>-1) {
				req.getRequestDispatcher("/AuthorityError").forward(req,resp);
				return false;
			}else if (path.indexOf("/showEmployeeManager")>-1) {
				req.getRequestDispatcher("/AuthorityError").forward(req,resp);
				return false;
			}else if (path.indexOf("/showEmpNumberEditing")>-1) {
				req.getRequestDispatcher("/AuthorityError").forward(req,resp);
				return false;
			}else  {
				return true;
			}
		}
		 
		return true;
	}

}
