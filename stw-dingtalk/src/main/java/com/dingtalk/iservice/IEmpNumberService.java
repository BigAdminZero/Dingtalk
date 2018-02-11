package com.dingtalk.iservice;

import java.util.List;

import com.dingtalk.bean.EmpNumber;

public interface IEmpNumberService {
	
	/**
	 * 得到所有的员工编号与其对应的员工姓名
	 * @return
	 */
	public List<EmpNumber> getAllEmpNumber();
	
	/**
	 * 根据员工编号删除员工
	 * @param number
	 */
	public void deleteEmpNumberByNum(String number);
	
	
	public void addEmployeeNumber(EmpNumber empnumber);
	
}
