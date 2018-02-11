package com.dingtalk.dao;

import java.util.List;

import com.dingtalk.bean.EmpNumber;

public interface EmpNumberMapper {

	public List<EmpNumber> selectAll();
	
	public void deleteByEmpNumber(String number);
	
	public void insertEmpNumber(EmpNumber empnumber);
	
}
