package com.dingtalk.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.dingtalk.bean.Employee;
import com.dingtalk.bean.Holiday;
import com.dingtalk.bean.OndutyGroup;
import com.dingtalk.bean.ScheduleGroup;
import com.dingtalk.bean.ScheduleTable;
import com.dingtalk.iservice.IDepartmentOnDutyService;
import com.dingtalk.iservice.IEmployeeService;
import com.dingtalk.iservice.IOndutyGroupService;
import com.dingtalk.iservice.IScheduleService;
import com.dingtalk.iservice.IScheduleTbaleService;

/**
 * 值班组编辑以及按个人排班的操作
 * @author yliu
 *
 */
@Controller
@RequestMapping("")
public class SchedualController {
	
	@Autowired
	IOndutyGroupService ondutyGroupService;
	@Autowired
	IScheduleService scheduleservice;
	@Autowired
	IScheduleTbaleService scheduleTbaleService;
	@Autowired
	IEmployeeService employeeService;
	@Autowired
	IDepartmentOnDutyService departmentOnDutyService;
	

	/**
	 * 显示排班组页面
	 * @return
	 */

	@RequestMapping("showScheduleTemplatePage")
	public ModelAndView DutyGroup(HttpSession session){
		
		List<ScheduleGroup> ScheduleGroupList=scheduleservice.getScheduleGroupTemplate();
		
		session.removeAttribute("flag");
		
		ModelAndView mv=new ModelAndView();
		
		mv.setViewName("showscheduletemplate");
		
		mv.addObject("ScheduleGroupList", ScheduleGroupList);
		
		return mv;
	}
	
	/**
	 * 对排班组进行编辑页面 可进行增加或删除
	 * @return
	 */
	@RequestMapping("scheduleTemplateEditing")
	public ModelAndView EditGroup(int scheduleGroupId,HttpSession session){
		
		ModelAndView mv=new ModelAndView();
		
		mv.setViewName("scheduletemplateediting");
		
		if(scheduleGroupId!=0){
			ScheduleGroup schedulegroup=scheduleservice.getScheduleByGroupId(scheduleGroupId);
			mv.addObject("ScheduleGroup", schedulegroup);
			session.setAttribute("flag", "modify");
		}
		
		return mv;
	}
	
	/**
	 * 按个人排班页面
	 * @return
	 */
	@RequestMapping("scheduleByEmployee")
	public ModelAndView GroupBySelf(HttpSession session){
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		AuthorityManager am = (AuthorityManager) ac.getBean("AuthorityManager");
		
		List<Department> departmentlist = am.getDepartment(session);
		ModelAndView mv=new ModelAndView();
		
		mv.setViewName("scheduleByEmployee");
		mv.addObject("DepartmentList", departmentlist);
		
		return mv;
	}
	
	/**
	 * 验证排班组名是否存在
	 * @param scheduleGroupName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="checksgroupName",produces="application/json;charset=UTF-8")
	public String CheckScheGroupName(String scheduleGroupName,HttpSession session){
		
		int flag=scheduleservice.countByGroupName(scheduleGroupName);
		
		String mark=(String) session.getAttribute("flag");
		
		Boolean result=false;
		
		if(flag==0){
			result=true;
		}else if(mark!=null){
			result=true;
		}
		
		JSONObject jsonobject=new JSONObject();
		
		jsonobject.put("valid", result);
		
		return jsonobject.toString();
	}

	
	/**
	 * 新增排班组
	 * @param sgroup
	 * @param starttime
	 * @param endtime
	 * @param weeks
	 * @return
	 */
	@RequestMapping("add_schedulegroup")
	public ModelAndView AddSchedulelGroup(ScheduleGroup sgroup,String starttime,String endtime,String[] weeks){
		
		scheduleservice.addScheduleGroup(sgroup, starttime, endtime, weeks);
		
		List<ScheduleGroup> ScheduleGroupList=scheduleservice.getScheduleGroupTemplate();
		
		ModelAndView mv=new ModelAndView();
		
		mv.setViewName("redirect:showScheduleTemplatePage");
		
		mv.addObject("ScheduleGroupList", ScheduleGroupList);
		
		return mv;
	}
	
	/**
	 * 删除排班组 可进行批量删除 也可以单独删除
	 * @param scheduleGroupId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="deleteScheduleGroup",produces="application/json;charset=UTF-8")
	public String DeleteScheduleGroup(int[] scheduleGroupId){
		
		for(int i=0;i<scheduleGroupId.length;i++){
			scheduleservice.deleteById(scheduleGroupId[i]);
		}
		
		JSONObject jsonobject=new JSONObject();
		
		jsonobject.put("msg", 1);
		
		return jsonobject.toString();
	}
	
	//进入按部门排班页面：
	@RequestMapping("/showScheduleByDepartment")
	public String showScheduleByDepartment(HttpServletRequest request,HttpSession session){
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		AuthorityManager am = (AuthorityManager) ac.getBean("AuthorityManager");
		
		
		List<Department> department = am.getDepartment(session);
		List<ScheduleGroup> scheduleGroup = scheduleservice.findAllScheduleGroup();
		request.setAttribute("departments", department);
		request.setAttribute("scheduleGroups", scheduleGroup);
		return "scheduleByDepartment";
	}
	
	//按部门排班;
	@RequestMapping("/scheduleByDepartment")
	public void ScheduleByDepartment(HttpServletRequest request,HttpServletResponse respoonse,
			String SdeptId,String SgroupId ) throws IOException{
		PrintWriter out=respoonse.getWriter();
		Integer groupId = Integer.parseInt(SgroupId);
		Long deptId = Long.parseLong(SdeptId);
		ScheduleGroup scheduleGroup = scheduleservice.findScheduleGroupById(groupId);//查出排班组信息;
		Date endTime = scheduleGroup.getEndTime();
		Date startTime = scheduleGroup.getStartTime();
		String week = scheduleGroup.getWeek();
		String scheduleGroupNamename = scheduleGroup.getScheduleGroupName();
		//转换数据库星期格式;
		String weekDay="";
		if(week.indexOf("一")!=-1){
			weekDay+="2";
		}
		if(week.indexOf("二")!=-1){
			weekDay+="3";
		}
		if(week.indexOf("三")!=-1){
			weekDay+="4";
		}
		if(week.indexOf("四")!=-1){
			weekDay+="5";
		}
		if(week.indexOf("五")!=-1){
			weekDay+="6";
		}
		if(week.indexOf("六")!=-1){
			weekDay+="7";
		}
		if(week.indexOf("日")!=-1){
			weekDay+="1";
		}
		//employeedId为varchar;
//		List<String> employeeIds = employeeService.findEmployeedIdBydeptId(deptId);//根据部门号查询所有员工Id
//		for (String employeeId : employeeIds) {
			List<ScheduleTable> scheduleTables= scheduleTbaleService.findScheduleTableByDeptmentId(SdeptId);
			int i =0;
			for (ScheduleTable scheduleTable : scheduleTables) {
				i++;
				Date date = scheduleTable.getDate();//获取排班日期;
				Calendar Caldate= Calendar.getInstance();
				Caldate.setTime(date);
  				int weeknumber = Caldate.get(Calendar.DAY_OF_WEEK);//转化为星期;
				String Sweeknumber=weeknumber+"";
				if(weekDay.indexOf(Sweeknumber)!=-1){
					scheduleTable.setStartTime(startTime);
					scheduleTable.setEndTime(endTime);
					scheduleTbaleService.modifyScheduleTableByEmployeeId(scheduleTable);
				}/*else{
					SimpleDateFormat sdf =new SimpleDateFormat("HH:mm");
					Date time = null;
					try {
						time = sdf.parse("00:00");
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					i++;
					scheduleTable.setStartTime(time);
					scheduleTable.setEndTime(time);
					scheduleTbaleService.modifyScheduleTableByEmployeeId(scheduleTable);
				}*/
//			}
		}
			System.out.println(i);
		request.setAttribute("deptId",deptId);
		request.setAttribute("scheduleGroupNamename", scheduleGroupNamename);
		//页面跳转;
		out.print(1);
		out.flush();
		out.close();
	}
	
	
	
	
	/**
	 * 修改排班组 根据id
	 * @param sgroup
	 * @param starttime
	 * @param endtime
	 * @param weeks
	 * @return
	 */
	@RequestMapping("modify_schedulegroup")
	public ModelAndView ModifyScheduleGroup(ScheduleGroup sgroup,String starttime,String endtime,String[] weeks){
		
		scheduleservice.updateScheduleByGroupId(sgroup, starttime, endtime, weeks);
		
		ModelAndView mv=new ModelAndView();
		
		mv.setViewName("redirect:showScheduleTemplatePage");
		
		return mv;
	}
	
	/**
	 * 根据部门id，得到该部门所有的成员
	 * @param departmentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getEmployeeByDepartmentId",produces="application/json;charset=UTF-8")
	public String GetEmployeeByDepartmentId(int departmentId){
		
		JSONArray employees=scheduleservice.getEmployeeByDepartId(departmentId);
		
		return employees.toString();
	}
	
	/**
	 * 根据员工id获取对应的个人排班表
	 * @param employeeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getScheduleTableByEmployeeId",produces="application/json;charset=UTF-8")
	public String GetScheduleTableByEmployeeId(String employeeId){
		
		JSONArray scheduleTable=scheduleservice.getScheduleTableByEmployeeId(employeeId);
		
		return scheduleTable.toString();
	}
	
	/**
	 * 根据员工id进行排班表的更新
	 * @param jsonarray
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("updateTableByEmployeeId")
	public void UpdateScheduleTableByEmployeeId(String jsonarray,HttpServletResponse response) throws IOException{
		
		PrintWriter out=response.getWriter();
		
		if(jsonarray==null){
			out.print(2);
		}else if(jsonarray.length()==123){
			out.print(1);
		}else{
			JSONArray jsonarr=JSONArray.fromObject(jsonarray);
			
			scheduleservice.updateTableByEmployeeId(jsonarr);
			out.print(3);
		}
		
		out.flush();
		out.close();
		
	}

	/**
	 * 按部门排班页面
	 * @return
	 */
	@RequestMapping("showScheduleByCompanyPage")
	public ModelAndView ShowScheduleByCompanyPage(HttpSession session){
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		AuthorityManager am = (AuthorityManager) ac.getBean("AuthorityManager");
		
		
		List<Department> departmentlist = am.getDepartment(session);

		
		ModelAndView mv=new ModelAndView();
		
		mv.setViewName("departmentOnDuty");
		
		mv.addObject("DepartmentList", departmentlist);
		mv.addObject("departmentId", "null");
		return mv;
	}
	
	/**
	 * 根据部门id获取该部门值班情况
	 * @param departmentId 部门id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getDepartmentOnDutyByDepartmentId",produces="application/json;charset=UTF-8")
	public String GetDepartmentOnDutyByDepartmentIdId(String departmentId){
		
		Long departId=Long.parseLong(departmentId);
		
		JSONArray departmentOnDuty=departmentOnDutyService.selectByDepartmentId(departId);
		
		return departmentOnDuty.toString();
	}
	
	/**
	 * 根据部门id得到值班的员工和该部门所有员工
	 * @param departmentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getEmployeeByOnDutyAndAll",produces="application/json;charset=UTF-8")
	public String GetEmployeeByOnDutyAndAll(String departmentId){
		
		Long departId=Long.parseLong(departmentId);
		
		
		JSONArray OndutyEmployeeByGroupAndAll = new JSONArray();
		JSONObject group = new JSONObject();
		//获取全部员工信息
		List<Employee> allEmployee = employeeService.findEmployeeByDepartmentId(departId);
		//按值班组获取员工信息
		List<OndutyGroup> employeeByOndutyGroup = ondutyGroupService.findOndutyGroupByDepartmentId(departId);
		for (int i = 0; i < employeeByOndutyGroup.size(); i++) {
			//JSONArray memberarr = new JSONArray();
			//String employeeId = employeeByOndutyGroup.get(i).getEmployeeId();
			//String[] employIdList = employeeId.substring(1,employeeId.length()).split(",");
			//for (int j = 0; j < employIdList.length; j++) {
				//JSONObject member = new JSONObject();
				//for (int j2 = 0; j2 < allEmployee.size(); j2++) {
					//String employeeid = allEmployee.get(j2).getEmployeeId();
					//if (employeeid.equals(employIdList[j])) {
						//member.put("employeeName", allEmployee.get(j2).getEmployeeName());
						//member.put("employeeId", employeeId);
						//memberarr.add(member);
					//}
				//}
			//}
			group.put("groupName",employeeByOndutyGroup.get(i).getOndutyGroupName());
			group.put("groupId",employeeByOndutyGroup.get(i).getOndutyGroupId());
			OndutyEmployeeByGroupAndAll.add(group);
		}
		JSONArray memberarrForAll = new JSONArray();	
	for (int i = 0; i < allEmployee.size(); i++) {
		JSONObject member = new JSONObject();
		member.put("employeeName", allEmployee.get(i).getEmployeeName());
		member.put("employeeId", allEmployee.get(i).getEmployeeId());
		memberarrForAll.add(member);
	}
	group.put("groupName","all");
	group.put("groupId","all");
	group.put("member",memberarrForAll);
	OndutyEmployeeByGroupAndAll.add(group);
		
		
		return OndutyEmployeeByGroupAndAll.toString();
	}
	
	@RequestMapping("OnDutyEmployeeByDepartmentList")
	public void updateByDepartment(HttpServletResponse response,String departmentId,String date,
			String[] OnDutyEmployeeByDepartmentList,String type,String ondutyType ) throws IOException{
		
		PrintWriter out=response.getWriter();
		
        String[] employeeId = null;
		if("1".equals(type)){
        	Integer ondutyGroupId = Integer.parseInt(OnDutyEmployeeByDepartmentList[0]);
        	OndutyGroup ondutyGroupByGroupId = ondutyGroupService.selectOndutyGroupByGroupId(ondutyGroupId);
        	String employeeIdList = ondutyGroupByGroupId.getEmployeeId();
        	employeeId = employeeIdList.substring(1,employeeIdList.length()-1).split(",");
        }else if ("2".equals(type)) {
			employeeId = OnDutyEmployeeByDepartmentList;
		}
		String newdate = date.substring(0, 4)+"-"+date.substring(4, 6)+"-"+date.substring(6, 8);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date changeDate = null;
		try {
			changeDate = sdf.parse(newdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < employeeId.length; i++) {
			String employeeid = employeeId[i];
			ScheduleTable scheduleTable = new ScheduleTable();
			scheduleTable.setDate(changeDate);
			scheduleTable.setEmployeeId(employeeid);
			scheduleTable.setTypeOnduty(ondutyType);
			scheduleTbaleService.updateByDepartment(scheduleTable);
		}
		
		out.print(1);
		out.flush();
		out.close();
		/*List<Department> departmentlist=scheduleservice.getAllDepaertment();
		
		ModelAndView mv=new ModelAndView();
		
		mv.setViewName("departmentOnDuty");
		mv.addObject("DepartmentList", departmentlist);
		mv.addObject("departmentId", departmentId);
		
		return mv;*/
	}
	
	@ResponseBody
	@RequestMapping(value="updateByDepartmentOnDuty",produces="application/json;charset=UTF-8")
	public String updateByDepartmentOnDuty(String employeeId,String date){
		//System.out.println(date);
		ScheduleTable scheduleTable = new ScheduleTable();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String newdate = date.substring(0, 4)+"-"+date.substring(4, 6)+"-"+date.substring(6, 8);
		//System.out.println(newdate);
		//System.out.println(employeeId);
		Date changeDate = null;
		try {
			changeDate = sdf.parse(newdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scheduleTable.setDate(changeDate);
		scheduleTable.setEmployeeId(employeeId);
		scheduleTbaleService.updateByDepartmentOnDuty(scheduleTable);
		return null;
		
		
	}
	
	/**
	 * 按节假日排班页面
	 * @return
	 */
	@RequestMapping("scheduleByHoliday")
	public ModelAndView scheduleByHoliday(){
		
		ModelAndView mv=new ModelAndView("scheduleByHoliday");
		
		return mv;
	}
	
	@RequestMapping("scheduleByWork")
	public ModelAndView scheduleByWork(){
		
		ModelAndView mv=new ModelAndView("scheduleByWork");
		
		return mv;
	}
	
	/**
	 * 根据前台选择的假期时间，对数据库进行更新
	 * @param startDate 假期开始时间
	 * @param endDate 假期结束时间
	 * @return
	 */
	@RequestMapping("updateScheduleTableByHoliday")
	public String updateScheduleTableByHoliday(String startDate,String endDate ){
		
		Holiday holiday=new Holiday();
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			Date start=sdf.parse(startDate);
			Date end=sdf.parse(endDate);
			
			holiday.setStartDate(start);
			holiday.setEndDate(end);
			holiday.setStartTime("00:00");
			holiday.setEndTime("00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scheduleservice.updateByHoliday(holiday);
		
		return "redirect:/scheduleByHoliday";
	}
	
	
	@RequestMapping("updateScheduleTableByWork")
	public String updateScheduleTableByWork(String startDate,String endDate,String startTime,String endTime){
		
		Holiday holiday=new Holiday();
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			Date start=sdf.parse(startDate);
			Date end=sdf.parse(endDate);
			
			holiday.setStartDate(start);
			holiday.setEndDate(end);
			holiday.setStartTime(startTime);
			holiday.setEndTime(endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scheduleservice.updateByHoliday(holiday);
		
		//System.out.println(startDate+"|"+startTime);
		
		return "redirect:/scheduleByWork";
	}
}
