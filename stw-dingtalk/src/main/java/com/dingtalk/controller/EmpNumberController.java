package com.dingtalk.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dingtalk.bean.Department;
import com.dingtalk.bean.EmpNumber;
import com.dingtalk.bean.Employee;
import com.dingtalk.bean.ScheduleTable;
import com.dingtalk.iservice.IDepartmentService;
import com.dingtalk.iservice.IEmpNumberService;
import com.dingtalk.iservice.IEmployeeService;
import com.dingtalk.iservice.IScheduleService;
import com.dingtalk.iservice.IScheduleTbaleService;

@RequestMapping("")
@Controller
public class EmpNumberController {

	@Autowired
	IEmpNumberService empnumberservice;
	@Autowired 
	IEmployeeService employeeeservice;
	@Autowired
	IDepartmentService departmentservice;
	@Autowired
	IScheduleService scheduleservice;
	@Autowired
	IScheduleTbaleService scheduletableservice;
	
	@RequestMapping("showEmpNumberEditing")
	public ModelAndView showEmpNumberEditing(){
		
		
		
		ModelAndView mv=new ModelAndView("employeeNumberEditing");
		
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "getEmployeeNumber", produces = "application/json; charset=utf-8")
	public String getEmployeeNumber() {
		List<EmpNumber> empNumberList=empnumberservice.getAllEmpNumber();
		JSONArray empNumberArr = new JSONArray();
		for (int i = 0; i < empNumberList.size(); i++) {
			JSONObject empNumberObj = new JSONObject();
			empNumberObj.put("empNumber", empNumberList.get(i).getNumber());
			empNumberObj.put("name", empNumberList.get(i).getEmployeeName());
			empNumberArr.add(empNumberObj);
		}
		
		return empNumberArr.toString();
	
	}
	
	
	@RequestMapping("deleteEmpNumber")
	public void deleteEmpNumber(String[] EmpNumbers,HttpServletResponse response) throws IOException{
		
		PrintWriter out=response.getWriter();
		
		for(int i=0;i<EmpNumbers.length;i++){
			empnumberservice.deleteEmpNumberByNum(EmpNumbers[i]);
		}
		
		out.print(1);
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
	@ResponseBody
	@RequestMapping(value = "getNoNumberEmployeeId", produces = "application/json; charset=utf-8")
	public String getNoNumberEmployeeId() {
		List<Employee> selectAllEmployee = employeeeservice.selectAllEmployee();
		List<Department> departmentList = departmentservice.getAllDepartment();
		List<EmpNumber> allEmpNumber = empnumberservice.getAllEmpNumber();
		JSONArray empolyeeArray = new JSONArray();
		if (allEmpNumber.size() != 0) {
			for (int i = 0; i < selectAllEmployee.size(); i++) {
				int temp = 0;
				for (int k = 0; k < allEmpNumber.size(); k++) {
					if (!allEmpNumber.get(k).getEmployeeId()
							.equals(selectAllEmployee.get(i).getEmployeeId())) {
						temp++;
					}
				}
				JSONObject employeeObject = new JSONObject();
				employeeObject.put("employeeId", selectAllEmployee.get(i)
						.getEmployeeId());
				employeeObject.put("employeeName", ""
						+ selectAllEmployee.get(i).getEmployeeName());
				for (int j = 0; j < departmentList.size(); j++) {
					Long departmId = departmentList.get(j).getDepartmentId();
					if (departmId.equals(selectAllEmployee.get(i)
							.getDepartmentId())) {
						employeeObject.put("departmentName", ""
								+ departmentList.get(j).getDepartmentName());
					}
				}
				if (temp == allEmpNumber.size()) {
					empolyeeArray.add(employeeObject);
				}

			}
		} else {

			for (int i = 0; i < selectAllEmployee.size(); i++) {
				JSONObject employeeObject = new JSONObject();
				employeeObject.put("employeeId", selectAllEmployee.get(i)
						.getEmployeeId());
				employeeObject.put("employeeName", ""
						+ selectAllEmployee.get(i).getEmployeeName());
				for (int j = 0; j < departmentList.size(); j++) {
					Long departmId = departmentList.get(j).getDepartmentId();
					if (departmId.equals(selectAllEmployee.get(i)
							.getDepartmentId())) {
						employeeObject.put("departmentName", ""
								+ departmentList.get(j).getDepartmentName());
					}
				}

				empolyeeArray.add(employeeObject);
			}
		}

		return empolyeeArray.toString();

	}
	
	@RequestMapping("addEmpNumber")
	public String addEmpNumber(String number,String noNumberEmployee){
		List<Employee> selectByemployeeId = employeeeservice.selectByemployeeId(noNumberEmployee);
		EmpNumber empNumber = new EmpNumber();
		empNumber.setEmployeeId(selectByemployeeId.get(0).getEmployeeId());
		empNumber.setEmployeeName(selectByemployeeId.get(0).getEmployeeName());
		empNumber.setNumber(number);
		empnumberservice.addEmployeeNumber(empNumber);
		Calendar nowDate = Calendar.getInstance();
		nowDate.setTime(new Date());

		int date = nowDate.get(Calendar.DAY_OF_MONTH); 
		Date todayDate = nowDate.getTime();
		SimpleDateFormat sdfNowYear = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfNowMonth = new SimpleDateFormat("yyyy-MM-");
		String searchNowDate = sdfNowYear.format(todayDate);
		String SearchNowDateMonth = sdfNowMonth.format(todayDate);
		//根据员工id查询排班表
		JSONArray ScheduleTableBySearchNowDate = scheduleservice.getScheduleTableByEmployeeIdAndSearchdate(selectByemployeeId.get(0).getEmployeeId(),searchNowDate);
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
						scheduleTable.setEmployeeId(selectByemployeeId.get(0).getEmployeeId());
						if (!(scheduletableservice.ifExist(scheduleTable).size()>0)) {
							scheduletableservice.initScheduleTable(scheduleTable);
						}
					
				} else if (weeknumber == 7 || weeknumber == 1) {
				
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
						scheduleTable.setEmployeeId(selectByemployeeId.get(0).getEmployeeId());
						if (!(scheduletableservice.ifExist(scheduleTable).size()>0)) {
							scheduletableservice.initScheduleTable(scheduleTable);
						}						// System.out.println("secess");
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
		
		JSONArray ScheduleTableBySearchDate = scheduleservice.getScheduleTableByEmployeeIdAndSearchdate(selectByemployeeId.get(0).getEmployeeId(), searchDate);
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
						scheduleTable.setEmployeeId(selectByemployeeId.get(0).getEmployeeId());
						if (!(scheduletableservice.ifExist(scheduleTable).size()>0)) {
							scheduletableservice.initScheduleTable(scheduleTable);
						}						// System.out.println("secess");
				} else if (weeknumber == 7 || weeknumber == 1) {
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
						scheduleTable.setEmployeeId(selectByemployeeId.get(0).getEmployeeId());
						if (!(scheduletableservice.ifExist(scheduleTable).size()>0)) {
							scheduletableservice.initScheduleTable(scheduleTable);
						}						// System.out.println("secess");
				}

			}
		}
		return "redirect:/showEmpNumberEditing";
	}
	
}
