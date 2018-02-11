package com.dingtalk.dao;



import net.sf.json.JSONArray;

public interface DepartmentOnDutyMapper {
   //查询按部门值班的员工信息
	public JSONArray selectByDepartmentId(Long departmentId);
	
}
