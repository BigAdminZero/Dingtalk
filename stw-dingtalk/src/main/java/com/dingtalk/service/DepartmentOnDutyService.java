package com.dingtalk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dingtalk.dao.DepartmentOnDutyMapper;
import com.dingtalk.iservice.IDepartmentOnDutyService;
import com.dingtalk.vo.DepartmentOnDuty;

import net.sf.json.JSONArray;
@Service
public class DepartmentOnDutyService implements IDepartmentOnDutyService {

	@Autowired
	DepartmentOnDutyMapper departmentOnDutyDao;
	
	public JSONArray selectByDepartmentId(Long departmentId) {
		
		JSONArray jsonarray=(JSONArray) departmentOnDutyDao.selectByDepartmentId(departmentId);
		
		return jsonarray;
	}

}
