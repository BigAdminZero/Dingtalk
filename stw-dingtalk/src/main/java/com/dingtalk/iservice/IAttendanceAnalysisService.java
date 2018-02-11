package com.dingtalk.iservice;

import java.util.List;


public interface IAttendanceAnalysisService {
	public List<String> showAttendance(String employeeId,String starttime,String endtime);
}
