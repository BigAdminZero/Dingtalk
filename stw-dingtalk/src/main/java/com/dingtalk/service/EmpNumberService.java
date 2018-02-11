package com.dingtalk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dingtalk.bean.EmpNumber;
import com.dingtalk.dao.EmpNumberMapper;
import com.dingtalk.iservice.IEmpNumberService;

@Service
public class EmpNumberService implements IEmpNumberService {

	@Autowired
	EmpNumberMapper empNumberDao;
	
	@Override
	public List<EmpNumber> getAllEmpNumber() {
		
		return empNumberDao.selectAll();
	}

	@Override
	public void deleteEmpNumberByNum(String number) {

		empNumberDao.deleteByEmpNumber(number);
		
	}

	@Override
	public void addEmployeeNumber(EmpNumber empnumber) {

		empNumberDao.insertEmpNumber(empnumber);
		
	}

}
