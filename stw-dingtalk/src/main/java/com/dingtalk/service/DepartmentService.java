package com.dingtalk.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dingtalk.bean.Department;
import com.dingtalk.dao.DepartmentMapper;
import com.dingtalk.iservice.IDepartmentService;

@Service
public class DepartmentService implements IDepartmentService{

	@Autowired
	DepartmentMapper departmentMapper;
	
	public List<Department> getAllDepartment() {
		return departmentMapper.selectAllDepartment();
	}

	
	public List<Department> selectDepartmentById(Long departmentId) {
		// TODO Auto-generated method stub
		return departmentMapper.selectDepartmentById(departmentId);
	}


	@Override
	public void synchronizeByDingding(JSONArray departmentArray) {
		
		departmentMapper.deleteAllDepartment();

		for(int i=0;i<departmentArray.size();i++){
			
			JSONObject obj=departmentArray.getJSONObject(i);
			if(obj.getLong("id")!=1){
				Long departmentId=obj.getLong("id");
				String departmentName=obj.getString("name");
				
				Department department=new Department();
				department.setDepartmentId(departmentId);
				department.setDepartmentName(departmentName);
				
				departmentMapper.insert(department);
			}
			
		}
		
		
	}


	

}
