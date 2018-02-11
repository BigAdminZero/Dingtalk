package com.dingtalk.iservice;

import java.util.List;

import net.sf.json.JSONArray;

import com.dingtalk.bean.Department;

public interface IDepartmentService {
	public List<Department> getAllDepartment();
	
	//按部门id查询部门
    public List<Department> selectDepartmentById(Long departmentId);
    
    
    /**
     * 根据钉钉上的部门信息进行本地数据库的同步
     * @param departmentArray 钉钉得到的部门Array
     */
    public void synchronizeByDingding(JSONArray departmentArray);

}
