package com.dingtalk.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.dingtalk.bean.EmpNumber;
import com.dingtalk.bean.Employee;
import com.dingtalk.bean.ScheduleTable;
import com.dingtalk.iservice.IAttendanceAnalysisService;
import com.dingtalk.iservice.IDepartmentService;
import com.dingtalk.iservice.IEmpNumberService;
import com.dingtalk.iservice.IEmployeeService;
import com.dingtalk.iservice.IScheduleTbaleService;
import com.dingtalk.vo.EmployeeIdAndSearchdate;

@Controller
@RequestMapping("")
public class AttendanceController {

	@Autowired
	IDepartmentService departmentservice;
	@Autowired
	IAttendanceAnalysisService attendanceAnalysisService;
	@Autowired
	IEmployeeService employeeservice;
	@Autowired
	IScheduleTbaleService scheduletableservice;
	@Autowired
	IEmpNumberService empnumberservice;

	@RequestMapping("attendanceRecord")
	public ModelAndView attendanceRecord(HttpSession session) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		AuthorityManager am = (AuthorityManager) ac.getBean("AuthorityManager");
		
		List<Department> allDepartment=am.getDepartment(session);;

		ModelAndView mv = new ModelAndView();
		session.setAttribute("department", allDepartment);
		mv.setViewName("attendanceRecord");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "getAttendanceRecord", produces = "application/json; charset=utf-8")
	public String showAttendanceRecord(String employeeId, String endtime,
			String starttime) {
		List<Employee> employee = employeeservice.selectByemployeeId(employeeId);
		List<String> result = attendanceAnalysisService.showAttendance(
				employeeId, starttime, endtime);
		// System.out.println(result);
		JSONObject attendanceRecord = new JSONObject();
		attendanceRecord.put("employeeId", employeeId);
		attendanceRecord.put("employeeName", employee.get(0).getEmployeeName());
		// 初始化结果jsonArray
		JSONArray recordResult = new JSONArray();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		JSONObject recordResultObjOne = new JSONObject();
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = sdf1.parse(starttime);
			endDate = sdf1.parse(endtime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int days = getDays(startDate, endDate);
		recordResultObjOne.put("workDate", sdf1.format(startDate));
		recordResultObjOne.put("OnDuty", "未打卡");
		recordResultObjOne.put("OffDuty", "未打卡");
		recordResultObjOne.put("workTime", "00:00:00");
		recordResult.add(recordResultObjOne);
		for (int k = 1; k <= days; k++) {
			Calendar startday = Calendar.getInstance();
			startday.setTime(startDate);
			startday.add(Calendar.DAY_OF_YEAR, k);
			JSONObject recordResultObj = new JSONObject();
			recordResultObj.put("workDate", sdf1.format(startday.getTime()));
			recordResultObj.put("OnDuty", "未打卡");
			recordResultObj.put("OffDuty", "未打卡");
			recordResultObj.put("workTime", "00:00:00");
			recordResult.add(recordResultObj);
		}
		for (int i = 0; i < result.size(); i++) {
			JSONObject resultObj = JSONObject.fromObject(result.get(i));
			JSONArray resultArray = resultObj.getJSONArray("recordresult");
			// 遍历结果array
			for (int j = 0; j < resultArray.size(); j++) {
				Date workDate = new Date((long) resultArray.getJSONObject(j)
						.get("workDate"));
				Date userCheckDate = new Date((long) resultArray.getJSONObject(
						j).get("userCheckTime"));
				String workTime = sdf1.format(workDate);
				String userCheckTime = sdf.format(userCheckDate);
				// 遍历初始化array
				for (int j2 = 0; j2 < recordResult.size(); j2++) {
					// 如果结果工作时间==初始化的时间
					if (recordResult.getJSONObject(j2).get("workDate")
							.equals(workTime)) {
						if ("Normal".equals(resultArray.getJSONObject(j).get(
								"timeResult"))) {
							if ("OnDuty".equals(resultArray.getJSONObject(j)
									.get("checkType"))) {
								recordResult.getJSONObject(j2).put("OnDuty",
										userCheckTime);
							} else if ("OffDuty".equals(resultArray
									.getJSONObject(j).get("checkType"))) {
								recordResult.getJSONObject(j2).put("OffDuty",
										userCheckTime);
							}
						} else if ("NotSigned".equals(resultArray
								.getJSONObject(j).get("timeResult"))) {
							if ("OnDuty".equals(resultArray.getJSONObject(j)
									.get("checkType"))) {
								recordResult.getJSONObject(j2).put("OnDuty",
										"缺卡");
							} else if ("OffDuty".equals(resultArray
									.getJSONObject(j).get("checkType"))) {
								recordResult.getJSONObject(j2).put("OffDuty",
										"缺卡");
							}
						} else if ("Early".equals(resultArray.getJSONObject(j)
								.get("timeResult"))) {
							if ("OffDuty".equals(resultArray.getJSONObject(j)
									.get("checkType"))) {
								recordResult.getJSONObject(j2).put("OffDuty",
										userCheckTime + "(早退)");
							}
						} else if ("Late".equals(resultArray.getJSONObject(j)
								.get("timeResult"))) {
							if ("OnDuty".equals(resultArray.getJSONObject(j)
									.get("checkType"))) {
								recordResult.getJSONObject(j2).put("OnDuty",
										userCheckTime + "(迟到)");
							}
						}
					}
				}
			}
		}
		for (int i = 0; i < recordResult.size(); i++) {
			String onDuty = (String) recordResult.getJSONObject(i)
					.get("OnDuty");
			String offDuty = (String) recordResult.getJSONObject(i).get(
					"OffDuty");
			if ("未打卡".equals(onDuty) && "未打卡".equals(offDuty)) {
				recordResult.getJSONObject(i).put("workTime", "未打卡，无法计算");
			} else if ("未打卡".equals(onDuty) && (!"未打卡".equals(offDuty))) {
				recordResult.getJSONObject(i).put("workTime", "上班未打卡，无法计算");
			} else if ((!"未打卡".equals(onDuty)) && "未打卡".equals(offDuty)) {
				recordResult.getJSONObject(i).put("workTime", "下班未打卡，无法计算");
			} else if ("缺卡".equals(onDuty) && "缺卡".equals(offDuty)) {
				recordResult.getJSONObject(i).put("workTime", "缺卡，无法计算");
			} else if ("缺卡".equals(onDuty) && (!"缺卡".equals(offDuty))) {
				recordResult.getJSONObject(i).put("workTime", "上班缺卡，无法计算");
			} else if ((!"缺卡".equals(onDuty)) && "缺卡".equals(offDuty)) {
				recordResult.getJSONObject(i).put("workTime", "下班缺卡，无法计算");
			} else if (Double.parseDouble(onDuty.substring(0, 5).replace(':','.'))>Double.parseDouble(offDuty.substring(0, 5).replace(':','.'))) {
				Calendar onDutyTime = Calendar.getInstance();
				Calendar offDutyTime = Calendar.getInstance();
				try {
					onDutyTime.setTime(sdf.parse(onDuty));
					offDutyTime.setTime(sdf.parse(offDuty));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Date workTimeLong = new Date(offDutyTime.getTimeInMillis()
						- onDutyTime.getTimeInMillis());
				SimpleDateFormat sdf2 = new SimpleDateFormat("HH");
				int workTimeHour = Integer.parseInt(sdf2.format(workTimeLong))+24 - 8;
				String workTime = "";
				if (workTimeHour < 10) {
					workTime = "0" + workTimeHour
							+ sdf.format(workTimeLong).substring(2);
				} else {
					workTime = workTimeHour
							+ sdf.format(workTimeLong).substring(2);
				}
				recordResult.getJSONObject(i).put("workTime", workTime);
			} else {
				Calendar onDutyTime = Calendar.getInstance();
				Calendar offDutyTime = Calendar.getInstance();
				try {
					onDutyTime.setTime(sdf.parse(onDuty));
					offDutyTime.setTime(sdf.parse(offDuty));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Date workTimeLong = new Date(offDutyTime.getTimeInMillis()
						- onDutyTime.getTimeInMillis());
				SimpleDateFormat sdf2 = new SimpleDateFormat("HH");
				int workTimeHour = Integer.parseInt(sdf2.format(workTimeLong)) - 8;
				String workTime = "";
				if (workTimeHour < 10) {
					workTime = "0" + workTimeHour
							+ sdf.format(workTimeLong).substring(2);
				} else {
					workTime = workTimeHour
							+ sdf.format(workTimeLong).substring(2);
				}
				recordResult.getJSONObject(i).put("workTime", workTime);
			}
		}
		attendanceRecord.put("recordResult", recordResult);
		return attendanceRecord.toString();
	}

	public static int getDays(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) // 同一年
		{
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
				{
					timeDistance += 366;
				} else // 不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2 - day1);
		} else // 不同年
		{
			return day2 - day1;
		}
	}

	@RequestMapping("attendanceResult")
	public ModelAndView attendanceResult(HttpSession session) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		AuthorityManager am = (AuthorityManager) ac.getBean("AuthorityManager");
		
		
		List<Department> allDepartment = am.getDepartment(session);

		ModelAndView mv = new ModelAndView();
		session.setAttribute("department", allDepartment);
		mv.setViewName("attendanceResult");
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "getAttendanceResult", produces = "application/json; charset=utf-8")
	public String showAttendanceResult(String departmentId,String date) throws ParseException {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdfmonth = new SimpleDateFormat("yyyy-MM");
		c.setTime(sdfmonth.parse(date));
		int actualMaximum = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		String endtime = null;
		if (actualMaximum<10) {
			endtime = date + "-0" + actualMaximum;
		}else {
			endtime = date + "-" + actualMaximum;
		}
		
		String starttime = date + "-01";
		JSONArray allAttendanceResult = new JSONArray();
		if ("0".equals(departmentId)) {
			List<Department> allDepartment = departmentservice.getAllDepartment();
			for (int iall = 0; iall < allDepartment.size(); iall++) {
				String departmentid = allDepartment.get(iall).getDepartmentId().toString();
				List<Employee> employee = employeeservice
						.findEmployeeByDepartmentId(Long.parseLong(departmentid));
				JSONArray allAttendanceRecord = new JSONArray();
				for (int l = 0; l < employee.size(); l++) {
					List<String> result = attendanceAnalysisService.showAttendance(
							employee.get(l).getEmployeeId(), starttime, endtime);
					// System.out.println(result);
					JSONObject attendanceRecord = new JSONObject();
					attendanceRecord.put("employeeId", employee.get(l).getEmployeeId());
					attendanceRecord.put("employeeName", employee.get(l).getEmployeeName());
					// 初始化结果jsonArray
					JSONArray recordResult = new JSONArray();
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
					JSONObject recordResultObjOne = new JSONObject();
					Date startDate = null;
					Date endDate = null;
					startDate = sdf1.parse(starttime);
					endDate = sdf1.parse(endtime);
					int days = getDays(startDate, endDate);
					recordResultObjOne.put("workDate", sdf1.format(startDate));
					recordResultObjOne.put("OnDuty", "未打卡");
					recordResultObjOne.put("OffDuty", "未打卡");
					recordResultObjOne.put("workTime", "00:00:00");
					recordResult.add(recordResultObjOne);
					for (int k = 1; k <= days; k++) {
						Calendar startday = Calendar.getInstance();
						startday.setTime(startDate);
						startday.add(Calendar.DAY_OF_YEAR, k);
						JSONObject recordResultObj = new JSONObject();
						recordResultObj
								.put("workDate", sdf1.format(startday.getTime()));
						recordResultObj.put("OnDuty", "未打卡");
						recordResultObj.put("OffDuty", "未打卡");
						recordResultObj.put("workTime", "00:00");
						recordResult.add(recordResultObj);
					}
					for (int i = 0; i < result.size(); i++) {
						JSONObject resultObj = JSONObject.fromObject(result.get(i));
						JSONArray resultArray = resultObj.getJSONArray("recordresult");
						// 遍历结果array
						for (int j = 0; j < resultArray.size(); j++) {
							Date workDate = new Date((long) resultArray
									.getJSONObject(j).get("workDate"));
							Date userCheckDate = new Date((long) resultArray
									.getJSONObject(j).get("userCheckTime"));
							String workTime = sdf1.format(workDate);
							String userCheckTime = sdf.format(userCheckDate);
							// 遍历初始化array
							for (int j2 = 0; j2 < recordResult.size(); j2++) {
								// 如果结果工作时间==初始化的时间
								if (recordResult.getJSONObject(j2).get("workDate")
										.equals(workTime)) {
									if ("Normal".equals(resultArray.getJSONObject(j)
											.get("timeResult"))) {
										if ("OnDuty".equals(resultArray
												.getJSONObject(j).get("checkType"))) {
											recordResult.getJSONObject(j2).put(
													"OnDuty", userCheckTime);
										} else if ("OffDuty".equals(resultArray
												.getJSONObject(j).get("checkType"))) {
											recordResult.getJSONObject(j2).put(
													"OffDuty", userCheckTime);
										}
									} else if ("NotSigned".equals(resultArray
											.getJSONObject(j).get("timeResult"))) {
										if ("OnDuty".equals(resultArray
												.getJSONObject(j).get("checkType"))) {
											recordResult.getJSONObject(j2).put(
													"OnDuty", "缺卡");
										} else if ("OffDuty".equals(resultArray
												.getJSONObject(j).get("checkType"))) {
											recordResult.getJSONObject(j2).put(
													"OffDuty", "缺卡");
										}
									} else if ("Early".equals(resultArray
											.getJSONObject(j).get("timeResult"))) {
										if ("OffDuty".equals(resultArray.getJSONObject(
												j).get("checkType"))) {
											recordResult.getJSONObject(j2).put(
													"OffDuty", userCheckTime + "(早退)");
										}
									} else if ("Late".equals(resultArray.getJSONObject(
											j).get("timeResult"))) {
										if ("OnDuty".equals(resultArray
												.getJSONObject(j).get("checkType"))) {
											recordResult.getJSONObject(j2).put(
													"OnDuty", userCheckTime + "(迟到)");
										}
									}
								}
							}
						}
					}
					for (int i = 0; i < recordResult.size(); i++) {
						String onDuty = (String) recordResult.getJSONObject(i).get(
								"OnDuty");
						String offDuty = (String) recordResult.getJSONObject(i).get(
								"OffDuty");
						if ("未打卡".equals(onDuty) && "未打卡".equals(offDuty)) {
							recordResult.getJSONObject(i).put("workTime", "未打卡，无法计算");
						} else if ("未打卡".equals(onDuty) && (!"未打卡".equals(offDuty))) {
							recordResult.getJSONObject(i).put("workTime", "上班未打卡，无法计算");
						} else if ((!"未打卡".equals(onDuty)) && "未打卡".equals(offDuty)) {
							recordResult.getJSONObject(i).put("workTime", "下班未打卡，无法计算");
						} else if ("缺卡".equals(onDuty) && "缺卡".equals(offDuty)) {
							recordResult.getJSONObject(i).put("workTime", "缺卡，无法计算");
						} else if ("缺卡".equals(onDuty) && (!"缺卡".equals(offDuty))) {
							recordResult.getJSONObject(i).put("workTime", "上班缺卡，无法计算");
						} else if ((!"缺卡".equals(onDuty)) && "缺卡".equals(offDuty)) {
							recordResult.getJSONObject(i).put("workTime", "下班缺卡，无法计算");
						} else if (Double.parseDouble(onDuty.substring(0, 5).replace(':','.'))>Double.parseDouble(offDuty.substring(0, 5).replace(':','.'))) {
							Calendar onDutyTime = Calendar.getInstance();
							Calendar offDutyTime = Calendar.getInstance();
							try {
								onDutyTime.setTime(sdf.parse(onDuty));
								offDutyTime.setTime(sdf.parse(offDuty));
							} catch (ParseException e) {
								e.printStackTrace();
							}
							Date workTimeLong = new Date(offDutyTime.getTimeInMillis()
									- onDutyTime.getTimeInMillis());
							SimpleDateFormat sdf2 = new SimpleDateFormat("HH");
							int workTimeHour = Integer.parseInt(sdf2.format(workTimeLong))+24 - 8;
							String workTime = "";
							if (workTimeHour < 10) {
								workTime = "0" + workTimeHour
										+ sdf.format(workTimeLong).substring(2);
							} else {
								workTime = workTimeHour
										+ sdf.format(workTimeLong).substring(2);
							}
							recordResult.getJSONObject(i).put("workTime", workTime);
						} else {
							Calendar onDutyTime = Calendar.getInstance();
							Calendar offDutyTime = Calendar.getInstance();
								onDutyTime.setTime(sdf.parse(onDuty));
								offDutyTime.setTime(sdf.parse(offDuty));
							Date workTimeLong = new Date(offDutyTime.getTimeInMillis()
									- onDutyTime.getTimeInMillis());
							SimpleDateFormat sdf2 = new SimpleDateFormat("HH");
							int workTimeHour = Integer.parseInt(sdf2
									.format(workTimeLong)) - 8;
							String workTime = "";
							if (workTimeHour < 10) {
								workTime = "0" + workTimeHour
										+ sdf.format(workTimeLong).substring(2);
							} else {
								workTime = workTimeHour
										+ sdf.format(workTimeLong).substring(2);
							}
							recordResult.getJSONObject(i).put("workTime", workTime);
						}
					}
					attendanceRecord.put("recordResult", recordResult);
					allAttendanceRecord.add(attendanceRecord);
				}
				List<EmpNumber> allEmpNumber = empnumberservice.getAllEmpNumber();
				for (int i = 0; i < employee.size(); i++) {
					EmployeeIdAndSearchdate employeeIdAndSearchdate = new EmployeeIdAndSearchdate();
					employeeIdAndSearchdate.setEmployeeId(employee.get(i).getEmployeeId());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
					SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
						employeeIdAndSearchdate.setStartDate(sdf.parse(starttime));
						employeeIdAndSearchdate.setEndDate(sdf.parse(endtime));
					List<ScheduleTable> ScheduletableList = scheduletableservice.selectByEmployeeIdAnDate(employeeIdAndSearchdate);
					for (int j = 0; j < allAttendanceRecord.size(); j++) {
						if (allAttendanceRecord.getJSONObject(j).get("employeeId").equals(employee.get(i).getEmployeeId())) {
							JSONObject attendanceResult = new JSONObject();
							attendanceResult.put("employeeId", allAttendanceRecord.getJSONObject(j).get("employeeId"));
							attendanceResult.put("employeeName", allAttendanceRecord.getJSONObject(j).get("employeeName"));
							attendanceResult.put("empNumber", "null");
							for (int k = 0; k < allEmpNumber.size(); k++) {
								if ( allAttendanceRecord.getJSONObject(j).get("employeeName").equals(allEmpNumber.get(k).getEmployeeName())) {
									attendanceResult.put("empNumber", allEmpNumber.get(k).getNumber());
								}
							}
							JSONArray resultArr =new JSONArray();
							for(int k = 0;k < allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").size();k++){
								for (int k2 = 0; k2 < ScheduletableList.size(); k2++) {
									if(sdf.format(ScheduletableList.get(k2).getDate()).equals(allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k).get("workDate"))&&!ScheduletableList.get(k2).getStartTime().equals(ScheduletableList.get(k2).getEndTime())){
										String start = sdf1.format(ScheduletableList.get(k2).getStartTime());
										String end = sdf1.format(ScheduletableList.get(k2).getEndTime());
										if("1".equals(ScheduletableList.get(k2).getIfOnduty())){
											if ("1".equals(ScheduletableList.get(k2).getIfOnduty())) {
												int endHour = Integer.parseInt(sdfHour.format(ScheduletableList.get(k2).getEndTime()))+3;
												if (endHour<10) {
													end = "0" + endHour + sdf1.format(ScheduletableList.get(k2).getEndTime()).substring(2);
												}else {
													end = "" + endHour + sdf1.format(ScheduletableList.get(k2).getEndTime()).substring(2);
												}
											}else if ("2".equals(ScheduletableList.get(k2).getIfOnduty())) {
												start = "09:00";
												end = "18:00";
											}
										}
										String OnDuty = (String) allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k).get("OnDuty");
										String OffDuty = (String) allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k2).get("OffDuty");
										String workTime = (String) allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k).get("workTime");
										if("缺卡".equals(allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k).get("OnDuty"))||"缺卡".equals(allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k).get("OffDuty"))){
											JSONObject resultObj = new JSONObject();
											resultObj.put("workDate", sdf.format(ScheduletableList.get(k2).getDate()));
											resultObj.put("exception", "缺卡");
											resultObj.put("attendanceStart",OnDuty );
											resultObj.put("attendanceEnd", OffDuty);
											resultObj.put("start", start);
											resultObj.put("end", end);
											resultObj.put("worktime",allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k).get("workTime"));
											resultArr.add(resultObj);
										}else if("未打卡".equals(allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k).get("OnDuty"))||"未打卡".equals(allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k).get("OffDuty"))){
											JSONObject resultObj = new JSONObject();
											resultObj.put("workDate", sdf.format(ScheduletableList.get(k2).getDate()));
											resultObj.put("exception", "未打卡");
											resultObj.put("attendanceStart",OnDuty );
											resultObj.put("attendanceEnd", OffDuty);
											resultObj.put("start", start);
											resultObj.put("end", end);
											resultObj.put("worktime",allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k).get("workTime"));
											resultArr.add(resultObj);
										}else{
											
											if (Double.parseDouble(start.replace(':','.'))<Double.parseDouble(OnDuty.substring(0, 5).replace(':', '.'))) {
												JSONObject resultObj = new JSONObject();
												resultObj.put("workDate", sdf.format(ScheduletableList.get(k2).getDate()));
												resultObj.put("exception", "迟到打卡");
												resultObj.put("attendanceStart",OnDuty );
												resultObj.put("attendanceEnd", OffDuty);
												resultObj.put("start", start);
												resultObj.put("end", end);
												resultObj.put("worktime",allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k).get("workTime"));
												resultArr.add(resultObj);
											}else if(Double.parseDouble(start.replace(':','.'))>Double.parseDouble(OnDuty.substring(0, 5).replace(':', '.'))&&(Double.parseDouble(workTime.substring(0, 5).replace(':', '.')))<(Double.parseDouble(end.replace(':', '.'))-Double.parseDouble(start.replace(':', '.')))){
												JSONObject resultObj = new JSONObject();
												resultObj.put("workDate", sdf.format(ScheduletableList.get(k2).getDate()));
												resultObj.put("exception", "早退打卡");
												resultObj.put("attendanceStart",OnDuty );
												resultObj.put("attendanceEnd", OffDuty);
												resultObj.put("start", start);
												resultObj.put("end", end);
												resultObj.put("worktime",allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k).get("workTime"));
												resultArr.add(resultObj);
											}
										}
										
									}
								}
							}
							attendanceResult.put("recordResult", resultArr);
							int exceptionTime = 0;
							int lateTime = 0;
							int earlyTime = 0;
							int noDutyTime = 0;
							for (int l = 0; l < resultArr.size(); l++) {
								if ("迟到打卡".equals(resultArr.getJSONObject(l).get("exception"))) {
									exceptionTime++;
									lateTime++;
								}else if ("早退打卡".equals(resultArr.getJSONObject(l).get("exception"))) {
									exceptionTime++;
									earlyTime++;
								}else if ("缺卡".equals(resultArr.getJSONObject(l).get("exception"))) {
									exceptionTime++;
									noDutyTime++;
								}else if ("未打卡".equals(resultArr.getJSONObject(l).get("exception"))) {
									exceptionTime++;
									noDutyTime++;
								}
							}
							attendanceResult.put("exceptionTime", exceptionTime);
							attendanceResult.put("lateTime", lateTime);
							attendanceResult.put("earlyTime", earlyTime);
							attendanceResult.put("noDutyTime", noDutyTime);
							int workTime=actualMaximum;
							int weekWork = 0;
							for (int l = 1; l <= actualMaximum; l++) {
								String day = null;
								if (l<10) {
									day = date + "-0" + l;
								} else {
									day = date + "-" + l;
								}
								Date dateday = sdf.parse(day);
								//获取dateday的星期数
								for (int m = 0; m < ScheduletableList.size(); m++) {
									if (ScheduletableList.get(m).getDate().equals(dateday)&&"1".equals(ScheduletableList.get(m).getIfOnduty())&&"2".equals(ScheduletableList.get(m).getTypeOnduty())) {
										weekWork++;
									}
								}
								//判断是否是周末&&排班的上班时间和下班时间是否相同&&排版是否值班&&值班类型为周末值班
								attendanceResult.put(day, "√");
								for (int m = 0; m < resultArr.size(); m++) {
									if (day.equals(resultArr.getJSONObject(m).get("workDate"))) {
										attendanceResult.put(day, "×");
									}
								}
								for (int m2 = 0; m2 < ScheduletableList.size(); m2++) {
									if (ScheduletableList.get(m2).getEmployeeId().toString().equals(allAttendanceRecord.getJSONObject(j).get("employeeId"))&&sdf.format(ScheduletableList.get(m2).getDate()).equals(day)&&ScheduletableList.get(m2).getEndTime().equals(ScheduletableList.get(m2).getStartTime())&&"2".equals(ScheduletableList.get(m2).getIfOnduty())) {
										attendanceResult.put(day, null);
										workTime--;
									}
								}
								
							}
							attendanceResult.put("workTime", workTime);
							attendanceResult.put("weekWork", weekWork);
							allAttendanceResult.add(attendanceResult);
						}
					}
					
				}
			}
		} else {
			List<Employee> employee = employeeservice
					.findEmployeeByDepartmentId(Long.parseLong(departmentId));
			JSONArray allAttendanceRecord = new JSONArray();
			for (int l = 0; l < employee.size(); l++) {
				List<String> result = attendanceAnalysisService.showAttendance(
						employee.get(l).getEmployeeId(), starttime, endtime);
				// System.out.println(result);
				JSONObject attendanceRecord = new JSONObject();
				attendanceRecord.put("employeeId", employee.get(l).getEmployeeId());
				attendanceRecord.put("employeeName", employee.get(l).getEmployeeName());
				// 初始化结果jsonArray
				JSONArray recordResult = new JSONArray();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				JSONObject recordResultObjOne = new JSONObject();
				Date startDate = null;
				Date endDate = null;
				startDate = sdf1.parse(starttime);
				endDate = sdf1.parse(endtime);
				int days = getDays(startDate, endDate);
				recordResultObjOne.put("workDate", sdf1.format(startDate));
				recordResultObjOne.put("OnDuty", "未打卡");
				recordResultObjOne.put("OffDuty", "未打卡");
				recordResultObjOne.put("workTime", "00:00:00");
				recordResult.add(recordResultObjOne);
				for (int k = 1; k <= days; k++) {
					Calendar startday = Calendar.getInstance();
					startday.setTime(startDate);
					startday.add(Calendar.DAY_OF_YEAR, k);
					JSONObject recordResultObj = new JSONObject();
					recordResultObj
							.put("workDate", sdf1.format(startday.getTime()));
					recordResultObj.put("OnDuty", "未打卡");
					recordResultObj.put("OffDuty", "未打卡");
					recordResultObj.put("workTime", "00:00");
					recordResult.add(recordResultObj);
				}
				for (int i = 0; i < result.size(); i++) {
					JSONObject resultObj = JSONObject.fromObject(result.get(i));
					JSONArray resultArray = resultObj.getJSONArray("recordresult");
					// 遍历结果array
					for (int j = 0; j < resultArray.size(); j++) {
						Date workDate = new Date((long) resultArray
								.getJSONObject(j).get("workDate"));
						Date userCheckDate = new Date((long) resultArray
								.getJSONObject(j).get("userCheckTime"));
						String workTime = sdf1.format(workDate);
						String userCheckTime = sdf.format(userCheckDate);
						// 遍历初始化array
						for (int j2 = 0; j2 < recordResult.size(); j2++) {
							// 如果结果工作时间==初始化的时间
							if (recordResult.getJSONObject(j2).get("workDate")
									.equals(workTime)) {
								if ("Normal".equals(resultArray.getJSONObject(j)
										.get("timeResult"))) {
									if ("OnDuty".equals(resultArray
											.getJSONObject(j).get("checkType"))) {
										recordResult.getJSONObject(j2).put(
												"OnDuty", userCheckTime);
									} else if ("OffDuty".equals(resultArray
											.getJSONObject(j).get("checkType"))) {
										recordResult.getJSONObject(j2).put(
												"OffDuty", userCheckTime);
									}
								} else if ("NotSigned".equals(resultArray
										.getJSONObject(j).get("timeResult"))) {
									if ("OnDuty".equals(resultArray
											.getJSONObject(j).get("checkType"))) {
										recordResult.getJSONObject(j2).put(
												"OnDuty", "缺卡");
									} else if ("OffDuty".equals(resultArray
											.getJSONObject(j).get("checkType"))) {
										recordResult.getJSONObject(j2).put(
												"OffDuty", "缺卡");
									}
								} else if ("Early".equals(resultArray
										.getJSONObject(j).get("timeResult"))) {
									if ("OffDuty".equals(resultArray.getJSONObject(
											j).get("checkType"))) {
										recordResult.getJSONObject(j2).put(
												"OffDuty", userCheckTime + "(早退)");
									}
								} else if ("Late".equals(resultArray.getJSONObject(
										j).get("timeResult"))) {
									if ("OnDuty".equals(resultArray
											.getJSONObject(j).get("checkType"))) {
										recordResult.getJSONObject(j2).put(
												"OnDuty", userCheckTime + "(迟到)");
									}
								}
							}
						}
					}
				}
				for (int i = 0; i < recordResult.size(); i++) {
					String onDuty = (String) recordResult.getJSONObject(i).get(
							"OnDuty");
					String offDuty = (String) recordResult.getJSONObject(i).get(
							"OffDuty");
					if ("未打卡".equals(onDuty) && "未打卡".equals(offDuty)) {
						recordResult.getJSONObject(i).put("workTime", "未打卡，无法计算");
					} else if ("未打卡".equals(onDuty) && (!"未打卡".equals(offDuty))) {
						recordResult.getJSONObject(i).put("workTime", "上班未打卡，无法计算");
					} else if ((!"未打卡".equals(onDuty)) && "未打卡".equals(offDuty)) {
						recordResult.getJSONObject(i).put("workTime", "下班未打卡，无法计算");
					} else if ("缺卡".equals(onDuty) && "缺卡".equals(offDuty)) {
						recordResult.getJSONObject(i).put("workTime", "缺卡，无法计算");
					} else if ("缺卡".equals(onDuty) && (!"缺卡".equals(offDuty))) {
						recordResult.getJSONObject(i).put("workTime", "上班缺卡，无法计算");
					} else if ((!"缺卡".equals(onDuty)) && "缺卡".equals(offDuty)) {
						recordResult.getJSONObject(i).put("workTime", "下班缺卡，无法计算");
					} else if (Double.parseDouble(onDuty.substring(0, 5).replace(':','.'))>Double.parseDouble(offDuty.substring(0, 5).replace(':','.'))) {
						Calendar onDutyTime = Calendar.getInstance();
						Calendar offDutyTime = Calendar.getInstance();
						try {
							onDutyTime.setTime(sdf.parse(onDuty));
							offDutyTime.setTime(sdf.parse(offDuty));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						Date workTimeLong = new Date(offDutyTime.getTimeInMillis()
								- onDutyTime.getTimeInMillis());
						SimpleDateFormat sdf2 = new SimpleDateFormat("HH");
						int workTimeHour = Integer.parseInt(sdf2.format(workTimeLong))+24 - 8;
						String workTime = "";
						if (workTimeHour < 10) {
							workTime = "0" + workTimeHour
									+ sdf.format(workTimeLong).substring(2);
						} else {
							workTime = workTimeHour
									+ sdf.format(workTimeLong).substring(2);
						}
						recordResult.getJSONObject(i).put("workTime", workTime);
					} else {
						Calendar onDutyTime = Calendar.getInstance();
						Calendar offDutyTime = Calendar.getInstance();
							onDutyTime.setTime(sdf.parse(onDuty));
							offDutyTime.setTime(sdf.parse(offDuty));
						Date workTimeLong = new Date(offDutyTime.getTimeInMillis()
								- onDutyTime.getTimeInMillis());
						SimpleDateFormat sdf2 = new SimpleDateFormat("HH");
						int workTimeHour = Integer.parseInt(sdf2
								.format(workTimeLong)) - 8;
						String workTime = "";
						if (workTimeHour < 10) {
							workTime = "0" + workTimeHour
									+ sdf.format(workTimeLong).substring(2);
						} else {
							workTime = workTimeHour
									+ sdf.format(workTimeLong).substring(2);
						}
						recordResult.getJSONObject(i).put("workTime", workTime);
					}
				}
				attendanceRecord.put("recordResult", recordResult);
				allAttendanceRecord.add(attendanceRecord);
			}
			List<EmpNumber> allEmpNumber = empnumberservice.getAllEmpNumber();
			for (int i = 0; i < employee.size(); i++) {
				EmployeeIdAndSearchdate employeeIdAndSearchdate = new EmployeeIdAndSearchdate();
				employeeIdAndSearchdate.setEmployeeId(employee.get(i).getEmployeeId());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
				SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
					employeeIdAndSearchdate.setStartDate(sdf.parse(starttime));
					employeeIdAndSearchdate.setEndDate(sdf.parse(endtime));
				List<ScheduleTable> ScheduletableList = scheduletableservice.selectByEmployeeIdAnDate(employeeIdAndSearchdate);
				for (int j = 0; j < allAttendanceRecord.size(); j++) {
					if (allAttendanceRecord.getJSONObject(j).get("employeeId").equals(employee.get(i).getEmployeeId())) {
						JSONObject attendanceResult = new JSONObject();
						attendanceResult.put("employeeId", allAttendanceRecord.getJSONObject(j).get("employeeId"));
						attendanceResult.put("employeeName", allAttendanceRecord.getJSONObject(j).get("employeeName"));
						attendanceResult.put("empNumber", "null");
						for (int k = 0; k < allEmpNumber.size(); k++) {
							if ( allAttendanceRecord.getJSONObject(j).get("employeeName").equals(allEmpNumber.get(k).getEmployeeName())) {
								attendanceResult.put("empNumber", allEmpNumber.get(k).getNumber());
							}
						}
						JSONArray resultArr =new JSONArray();
						for(int k = 0;k < allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").size();k++){
							for (int k2 = 0; k2 < ScheduletableList.size(); k2++) {
								if(sdf.format(ScheduletableList.get(k2).getDate()).equals(allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k).get("workDate"))&&!ScheduletableList.get(k2).getStartTime().equals(ScheduletableList.get(k2).getEndTime())){
									String start = sdf1.format(ScheduletableList.get(k2).getStartTime());
									String end = sdf1.format(ScheduletableList.get(k2).getEndTime());
									if("1".equals(ScheduletableList.get(k2).getIfOnduty())){
										if ("1".equals(ScheduletableList.get(k2).getIfOnduty())) {
											int endHour = Integer.parseInt(sdfHour.format(ScheduletableList.get(k2).getEndTime()))+3;
											if (endHour<10) {
												end = "0" + endHour + sdf1.format(ScheduletableList.get(k2).getEndTime()).substring(2);
											}else {
												end = "" + endHour + sdf1.format(ScheduletableList.get(k2).getEndTime()).substring(2);
											}
										}else if ("2".equals(ScheduletableList.get(k2).getIfOnduty())) {
											start = "09:00";
											end = "18:00";
										}
									}
									String OnDuty = (String) allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k).get("OnDuty");
									String OffDuty = (String) allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k2).get("OffDuty");
									String workTime = (String) allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k).get("workTime");
									if("缺卡".equals(allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k).get("OnDuty"))||"缺卡".equals(allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k).get("OffDuty"))){
										JSONObject resultObj = new JSONObject();
										resultObj.put("workDate", sdf.format(ScheduletableList.get(k2).getDate()));
										resultObj.put("exception", "缺卡");
										resultObj.put("attendanceStart",OnDuty );
										resultObj.put("attendanceEnd", OffDuty);
										resultObj.put("start", start);
										resultObj.put("end", end);
										resultObj.put("worktime",allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k).get("workTime"));
										resultArr.add(resultObj);
									}else if("未打卡".equals(allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k).get("OnDuty"))||"未打卡".equals(allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k).get("OffDuty"))){
										JSONObject resultObj = new JSONObject();
										resultObj.put("workDate", sdf.format(ScheduletableList.get(k2).getDate()));
										resultObj.put("exception", "未打卡");
										resultObj.put("attendanceStart",OnDuty );
										resultObj.put("attendanceEnd", OffDuty);
										resultObj.put("start", start);
										resultObj.put("end", end);
										resultObj.put("worktime",allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k).get("workTime"));
										resultArr.add(resultObj);
									}else{
										if (Double.parseDouble(start.replace(':','.'))<Double.parseDouble(OnDuty.substring(0, 5).replace(':', '.'))) {
											JSONObject resultObj = new JSONObject();
											resultObj.put("workDate", sdf.format(ScheduletableList.get(k2).getDate()));
											resultObj.put("exception", "迟到打卡");
											resultObj.put("attendanceStart",OnDuty );
											resultObj.put("attendanceEnd", OffDuty);
											resultObj.put("start", start);
											resultObj.put("end", end);
											resultObj.put("worktime",allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k).get("workTime"));
											resultArr.add(resultObj);
										}else if(Double.parseDouble(start.replace(':','.'))>Double.parseDouble(OnDuty.substring(0, 5).replace(':', '.'))&&(Double.parseDouble(workTime.substring(0, 5).replace(':', '.')))<(Double.parseDouble(end.replace(':', '.'))-Double.parseDouble(start.replace(':', '.')))-1){
											JSONObject resultObj = new JSONObject();
											resultObj.put("workDate", sdf.format(ScheduletableList.get(k2).getDate()));
											resultObj.put("exception", "早退打卡");
											resultObj.put("attendanceStart",OnDuty );
											resultObj.put("attendanceEnd", OffDuty);
											resultObj.put("start", start);
											resultObj.put("end", end);
											resultObj.put("worktime",allAttendanceRecord.getJSONObject(j).getJSONArray("recordResult").getJSONObject(k).get("workTime"));
											resultArr.add(resultObj);
										}
									}
									
								}
							}
						}
						attendanceResult.put("recordResult", resultArr);
						int exceptionTime = 0;
						int lateTime = 0;
						int earlyTime = 0;
						int noDutyTime = 0;
						for (int l = 0; l < resultArr.size(); l++) {
							if ("迟到打卡".equals(resultArr.getJSONObject(l).get("exception"))) {
								exceptionTime++;
								lateTime++;
							}else if ("早退打卡".equals(resultArr.getJSONObject(l).get("exception"))) {
								exceptionTime++;
								earlyTime++;
							}else if ("缺卡".equals(resultArr.getJSONObject(l).get("exception"))) {
								exceptionTime++;
								noDutyTime++;
							}else if ("未打卡".equals(resultArr.getJSONObject(l).get("exception"))) {
								exceptionTime++;
								noDutyTime++;
							}
						}
						attendanceResult.put("exceptionTime", exceptionTime);
						attendanceResult.put("lateTime", lateTime);
						attendanceResult.put("earlyTime", earlyTime);
						attendanceResult.put("noDutyTime", noDutyTime);
						int workTime=actualMaximum;
						int weekWork = 0;
						for (int l = 1; l <= actualMaximum; l++) {
							String day = null;
							if (l<10) {
								day = date + "-0" + l;
							} else {
								day = date + "-" + l;
							}
							Date dateday = sdf.parse(day);
							//获取dateday的星期数
							for (int m = 0; m < ScheduletableList.size(); m++) {
								if (ScheduletableList.get(m).getDate().equals(dateday)&&"1".equals(ScheduletableList.get(m).getIfOnduty())&&"2".equals(ScheduletableList.get(m).getTypeOnduty())) {
									weekWork++;
								}
							}
							//判断是否是周末&&排班的上班时间和下班时间是否相同&&排版是否值班&&值班类型为周末值班
							attendanceResult.put(day, "√");
							for (int m = 0; m < resultArr.size(); m++) {
								if (day.equals(resultArr.getJSONObject(m).get("workDate"))) {
									attendanceResult.put(day, "×");
								}
							}
							int count =0;
							for (int k = 0; k < ScheduletableList.size(); k++) {
								if (ScheduletableList.get(k).getDate().equals(dateday)) {
									count++;
								}
							}
							if (count==0) {
								attendanceResult.put(day, "未排班");
							}
							for (int m2 = 0; m2 < ScheduletableList.size(); m2++) {
								if (ScheduletableList.get(m2).getEmployeeId().toString().equals(allAttendanceRecord.getJSONObject(j).get("employeeId"))&&sdf.format(ScheduletableList.get(m2).getDate()).equals(day)&&ScheduletableList.get(m2).getEndTime().equals(ScheduletableList.get(m2).getStartTime())&&"2".equals(ScheduletableList.get(m2).getIfOnduty())) {
									attendanceResult.put(day, null);
									workTime--;
								}
							}
							
						}
						attendanceResult.put("workTime", workTime);
						attendanceResult.put("weekWork", weekWork);
						allAttendanceResult.add(attendanceResult);
					}
				}
				
			}
		}
		
		
		return allAttendanceResult.toString();
	}
}
