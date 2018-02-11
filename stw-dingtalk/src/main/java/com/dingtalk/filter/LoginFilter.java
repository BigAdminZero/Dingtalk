package com.dingtalk.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dingtalk.bean.User;


public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 获得在下面代码中要用的request,response,session对象
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        // 获得用户请求的URI
        String path = req.getRequestURI();
        //System.out.println(path);
        
        // 从session里取user
        User user = (User) session.getAttribute("nowUser");
        // 登陆页面无需过滤
        if(path.indexOf("/index") > -1||path.indexOf("/user/login") > -1||path.indexOf("/assets") > -1||path.indexOf("/static") > -1) {
            chain.doFilter(req, resp);
            return;
        }

        // 判断如果没有取到员工信息,就跳转到登陆页面
        if (user == null) {
            // 跳转到登陆页面
            resp.sendRedirect("/stw-dingtalk/index");
        } else {
            // 已经登陆,继续此次请求
            chain.doFilter(request, response);
        }
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
