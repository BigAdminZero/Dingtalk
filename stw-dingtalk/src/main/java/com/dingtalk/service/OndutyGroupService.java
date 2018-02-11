package com.dingtalk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dingtalk.bean.OndutyGroup;
import com.dingtalk.dao.OndutyGroupMapper;
import com.dingtalk.iservice.IOndutyGroupService;

@Service
public class OndutyGroupService implements IOndutyGroupService {
	@Autowired
	OndutyGroupMapper ondutyGroupMapper;

	public List<OndutyGroup> findOndutyGroupByDepartmentId(Long departmentId) {
		List<OndutyGroup> onDutyGroup = ondutyGroupMapper
				.selectOndutyGroupByDepartmentId(departmentId);
		return onDutyGroup;
	}

	// 按值班组id更新值班组成员
	public int updateOndutyGroupEmployeeByGroupId(OndutyGroup record) {
		return ondutyGroupMapper.updateOndutyGroupEmployeeByGroupId(record);
	}

	// 按值班组id更新值班组成员
	public int addOndutyGroup(OndutyGroup record) {
		return ondutyGroupMapper.insertOndutyGroup(record);
	}

	//按值班组id删除值班组
	public int deleteOndutyGroup(Integer ondutyGroupId) {
		return ondutyGroupMapper.deleteOndutyGroup(ondutyGroupId);
	}

	public OndutyGroup selectOndutyGroupByGroupId(Integer ondutyGroupId) {
		OndutyGroup onDutyGroup = ondutyGroupMapper
				.selectOndutyGroupByGroupId(ondutyGroupId);
		return onDutyGroup;
	}
}
