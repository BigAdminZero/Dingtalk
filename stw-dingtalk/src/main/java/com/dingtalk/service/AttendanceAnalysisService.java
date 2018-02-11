package com.dingtalk.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dingtalk.iservice.IAttendanceAnalysisService;
import com.dingtalk.utils.DingHttps;

@Service
public class AttendanceAnalysisService implements IAttendanceAnalysisService{
	/*
	 * 
	 * 考勤比对
	 * 
	 */
		private String corpid = "ding6d831f98a66c8c8535c2f4657eb6378f";
		private String corpsecret = "3LLLfu4Xs81XOOpnXY8l4Ta-Yjwbkae47yQgktL5r6zQYBLGHRXahM9y0POJUzkz";
		public List<String> showAttendance(String employeeId,String starttime,String endtime){
			DingHttps dh =new DingHttps();
			List<String> attendanceRecord = new ArrayList<String>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = null;
			Date endDate = null;
			try {
				startDate = sdf.parse(starttime);
				endDate = sdf.parse(endtime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int days = getDays(startDate, endDate);
			if(days>7){
			Calendar startday = Calendar.getInstance();
			startday.setTime(startDate);
			Calendar endday = Calendar.getInstance();
			endday.setTime(startDate);
			endday.add(Calendar.DAY_OF_YEAR, 6);
			attendanceRecord.add(dh.getAttendanceRecord(dh.getAccess_token(corpid, corpsecret), employeeId, sdf.format(startday.getTime()), sdf.format(endday.getTime())));
			for (int i= days-7; i >= 0;i=i-7) {
				if(i>7){
				startday.add(Calendar.DAY_OF_YEAR, 7);
				endday.add(Calendar.DAY_OF_YEAR, 7);
				attendanceRecord.add(dh.getAttendanceRecord(dh.getAccess_token(corpid, corpsecret), employeeId, sdf.format(startday.getTime()), sdf.format(endday.getTime())));
				}else{
					startday.add(Calendar.DAY_OF_YEAR, 7);
					endday.add(Calendar.DAY_OF_YEAR, i+1);
					attendanceRecord.add(dh.getAttendanceRecord(dh.getAccess_token(corpid, corpsecret), employeeId, sdf.format(startday.getTime()), sdf.format(endday.getTime())));
					
				}
			}
			}else{
				Calendar startday = Calendar.getInstance();
				startday.setTime(startDate);
				Calendar endday = Calendar.getInstance();
				endday.setTime(startDate);
				endday.add(Calendar.DAY_OF_YEAR, days);
				attendanceRecord.add(dh.getAttendanceRecord(dh.getAccess_token(corpid, corpsecret), employeeId, sdf.format(startday.getTime()), sdf.format(endday.getTime())));
			}
			
			return attendanceRecord;
		}
		
		 public static int getDays(Date date1,Date date2)
		    {
		        Calendar cal1 = Calendar.getInstance();
		        cal1.setTime(date1);
		        
		        Calendar cal2 = Calendar.getInstance();
		        cal2.setTime(date2);
		       int day1= cal1.get(Calendar.DAY_OF_YEAR);
		        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
		        
		        int year1 = cal1.get(Calendar.YEAR);
		        int year2 = cal2.get(Calendar.YEAR);
		        if(year1 != year2)   //同一年
		        {
		            int timeDistance = 0 ;
		            for(int i = year1 ; i < year2 ; i ++)
		            {
		                if(i%4==0 && i%100!=0 || i%400==0)    //闰年            
		                {
		                    timeDistance += 366;
		                }
		                else    //不是闰年
		                {
		                    timeDistance += 365;
		                }
		            }
		            
		            return timeDistance + (day2-day1) ;
		        }
		        else    //不同年
		        {
		            return day2-day1;
		        }
		    }
	
}
