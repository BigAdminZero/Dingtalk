package com.dingtalk.iservice;



import net.sf.json.JSONArray;

public interface IDepartmentOnDutyService {
	   //查询按部门值班的员工信息
		public JSONArray selectByDepartmentId(Long departmentId);
}
