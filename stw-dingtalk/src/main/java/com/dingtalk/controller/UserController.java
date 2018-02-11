package com.dingtalk.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dingtalk.bean.Authority;
import com.dingtalk.bean.ScheduleTable;
import com.dingtalk.bean.User;
import com.dingtalk.iservice.IAuthorityService;
import com.dingtalk.iservice.IEmployeeService;
import com.dingtalk.iservice.IScheduleTbaleService;
import com.dingtalk.service.UserService;
import com.dingtalk.vo.UserWithAuthorityLevel;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	IScheduleTbaleService scheduletableservice;

	@Autowired
	IEmployeeService employeeservice;
	
	@Autowired
	IAuthorityService authorityservice;

	/**
	 * 每月第一次登陆成功生成所有员工的默认值班情况，方便后面的排班
	 * @return
	 */
	@RequestMapping("/addALLSchedule")
	public ModelAndView AddALLSchedule(){
		
		ModelAndView mv=new ModelAndView();
		
		Calendar nowDate = Calendar.getInstance();
		

		//nowDate.set(2017, 11, 1);
		nowDate.setTime(new Date());

		int date = nowDate.get(Calendar.DAY_OF_MONTH); 
		Date todayDate = nowDate.getTime();
		SimpleDateFormat sdfNowYear = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfNowMonth = new SimpleDateFormat("yyyy-MM-");
		String searchNowDate = sdfNowYear.format(todayDate);
		String SearchNowDateMonth = sdfNowMonth.format(todayDate);
		List<ScheduleTable> ScheduleTableBySearchNowDate = scheduletableservice
				.findScheduleTableBySearchDate(searchNowDate);
		List<String> allEmployeeId = employeeservice.findAllEmployeeId();

		if (ScheduleTableBySearchNowDate.size() == 0) {
			for (int i = date; i <= getDaysByYearMonth(nowDate); i++) {
				String nextMDate = null;
				if (i < 10) {
					nextMDate = SearchNowDateMonth + "0" + i;
				} else {
					nextMDate = SearchNowDateMonth + i;
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date nextMonthAllDate = null;
				try {
					nextMonthAllDate = sdf.parse(nextMDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Calendar nextMonthAllTime = Calendar.getInstance();
				nextMonthAllTime.setTime(nextMonthAllDate);
				System.out.println(nextMDate);
				System.out.println(nextMonthAllTime.get(Calendar.DAY_OF_WEEK));
				int weeknumber = nextMonthAllTime.get(Calendar.DAY_OF_WEEK);

				if (weeknumber == 2 || weeknumber == 3 || weeknumber == 4
						|| weeknumber == 5 || weeknumber == 6) {
					for (int j = 0; j < allEmployeeId.size(); j++) {
						ScheduleTable scheduleTable = new ScheduleTable();
						scheduleTable.setDate(nextMonthAllDate);

						Calendar startTime = Calendar.getInstance();
						Calendar endTime = Calendar.getInstance();
						startTime.set(nextMonthAllTime.YEAR,
								nextMonthAllTime.MONTH, nextMonthAllTime.DATE,
								9, 0, 0);
						endTime.set(nextMonthAllTime.YEAR,
								nextMonthAllTime.MONTH, nextMonthAllTime.DATE,
								18, 0, 0);
						scheduleTable.setStartTime(startTime.getTime());
						scheduleTable.setEndTime(endTime.getTime());
						scheduleTable.setEmployeeId(allEmployeeId.get(j));
						if (!(scheduletableservice.ifExist(scheduleTable).size()>0)) {
							scheduletableservice.initScheduleTable(scheduleTable);
						}
					}
				} else if (weeknumber == 7 || weeknumber == 1) {
					for (int j = 0; j < allEmployeeId.size(); j++) {
						ScheduleTable scheduleTable = new ScheduleTable();
						scheduleTable.setDate(nextMonthAllDate);

						Calendar startTime = Calendar.getInstance();
						Calendar endTime = Calendar.getInstance();
						startTime.set(nextMonthAllTime.YEAR,
								nextMonthAllTime.MONTH, nextMonthAllTime.DATE,
								0, 0, 0);
						endTime.set(nextMonthAllTime.YEAR,
								nextMonthAllTime.MONTH, nextMonthAllTime.DATE,
								0, 0, 0);
						scheduleTable.setStartTime(startTime.getTime());
						scheduleTable.setEndTime(endTime.getTime());
						scheduleTable.setEmployeeId(allEmployeeId.get(j));
						if (!(scheduletableservice.ifExist(scheduleTable).size()>0)) {
							scheduletableservice.initScheduleTable(scheduleTable);
						}						// System.out.println("secess");
					}
				}

			}
		}

		Calendar nextmonth = Calendar.getInstance();

		nextmonth.setTime(new Date());

		//nextmonth.set(2018, 0, 1);
		nextmonth.add(Calendar.MONTH, +1);


		Date nextMonthDate = nextmonth.getTime();

		SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM-");
		String searchDate = sdfYear.format(nextMonthDate);
		// System.out.println(searchDate);
		String SearchDateMonth = sdfMonth.format(nextMonthDate);
		// System.out.println(SearchDateMonth);
		List<ScheduleTable> ScheduleTableBySearchDate = scheduletableservice
				.findScheduleTableBySearchDate(searchDate);
		//List<String> allEmployeeId = employeeservice.findAllEmployeeId();

		if (ScheduleTableBySearchDate.size() == 0) {
			for (int i = 1; i <= getDaysByYearMonth(nextmonth); i++) {
				String nextMDate = null;
				if (i < 10) {
					nextMDate = SearchDateMonth + "0" + i;
				} else {
					nextMDate = SearchDateMonth + i;
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date nextMonthAllDate = null;
				try {
					nextMonthAllDate = sdf.parse(nextMDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Calendar nextMonthAllTime = Calendar.getInstance();
				nextMonthAllTime.setTime(nextMonthAllDate);
				System.out.println(nextMDate);
				System.out.println(nextMonthAllTime.get(Calendar.DAY_OF_WEEK));
				int weeknumber = nextMonthAllTime.get(Calendar.DAY_OF_WEEK);

				if (weeknumber == 2 || weeknumber == 3 || weeknumber == 4
						|| weeknumber == 5 || weeknumber == 6) {
					for (int j = 0; j < allEmployeeId.size(); j++) {
						ScheduleTable scheduleTable = new ScheduleTable();
						scheduleTable.setDate(nextMonthAllDate);

						Calendar startTime = Calendar.getInstance();
						Calendar endTime = Calendar.getInstance();
						startTime.set(nextMonthAllTime.YEAR,
								nextMonthAllTime.MONTH, nextMonthAllTime.DATE,
								9, 0, 0);
						endTime.set(nextMonthAllTime.YEAR,
								nextMonthAllTime.MONTH, nextMonthAllTime.DATE,
								18, 0, 0);
						scheduleTable.setStartTime(startTime.getTime());
						scheduleTable.setEndTime(endTime.getTime());
						scheduleTable.setEmployeeId(allEmployeeId.get(j));
						if (!(scheduletableservice.ifExist(scheduleTable).size()>0)) {
							scheduletableservice.initScheduleTable(scheduleTable);
						}						// System.out.println("secess");
					}
				} else if (weeknumber == 7 || weeknumber == 1) {
					for (int j = 0; j < allEmployeeId.size(); j++) {
						ScheduleTable scheduleTable = new ScheduleTable();
						scheduleTable.setDate(nextMonthAllDate);

						Calendar startTime = Calendar.getInstance();
						Calendar endTime = Calendar.getInstance();
						startTime.set(nextMonthAllTime.YEAR,
								nextMonthAllTime.MONTH, nextMonthAllTime.DATE,
								0, 0, 0);
						endTime.set(nextMonthAllTime.YEAR,
								nextMonthAllTime.MONTH, nextMonthAllTime.DATE,
								0, 0, 0);
						scheduleTable.setStartTime(startTime.getTime());
						scheduleTable.setEndTime(endTime.getTime());
						scheduleTable.setEmployeeId(allEmployeeId.get(j));
						if (!(scheduletableservice.ifExist(scheduleTable).size()>0)) {
							scheduletableservice.initScheduleTable(scheduleTable);
						}						// System.out.println("secess");
					}
				}

			}
		}
		
		mv.setViewName("backManage");
		
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/login")
	public void userLogin(User user,HttpServletRequest req, HttpSession session,HttpServletResponse response) throws IOException {
		User user2 = userService.findUserByNameAndPassword(user);
		PrintWriter out=response.getWriter();
		
		if (user2 == null) {
			out.print(2);
			
		}else{
			Authority authority = authorityservice.selectByAuthorityId(user2.getAuthorityId());
			UserWithAuthorityLevel userWithLevel = new UserWithAuthorityLevel();
			userWithLevel.setAuthorityId(user2.getAuthorityId());
			userWithLevel.setDepartmentId(user2.getDepartmentId());
			userWithLevel.setAuthorityLevel(authority.getAuthorityLevel());
			userWithLevel.setAuthorityName(user2.getAuthorityName());
			userWithLevel.setCreaterId(user2.getCreaterId());
			userWithLevel.setPassword(user2.getPassword());
			userWithLevel.setUserId(user2.getUserId());
			userWithLevel.setUserName(user2.getUserName());
			session.setAttribute("nowUser", userWithLevel);
			out.print(1);
		}
		out.flush();
		out.close();
		
		
	}

	/**
	 * 根据年 月 获取对应的月份 天数
	 */
	public static int getDaysByYearMonth(Calendar ca) {
		ca.set(Calendar.DATE, 1);
		ca.roll(Calendar.DATE, -1);
		int maxDate = ca.get(Calendar.DATE);
		return maxDate;
	}

	@RequestMapping("/logout")
	public String userLogout(HttpSession session) {
		session.invalidate();
		return "login";
	}
}
