package com.dingtalk.iservice;

import java.util.List;

import com.dingtalk.bean.OndutyGroup;

public interface IOndutyGroupService {
	//按部门id查找值班组
	public List<OndutyGroup> findOndutyGroupByDepartmentId(Long departmentId);
	//按值班组id查找值班组
	public OndutyGroup selectOndutyGroupByGroupId(Integer ondutyGroupId);
	//按值班组id更新值班组成员
	public int updateOndutyGroupEmployeeByGroupId(OndutyGroup record);
	//新增值班组
	public int addOndutyGroup(OndutyGroup record);
	//按值班组id删除值班组
	public int deleteOndutyGroup(Integer ondutyGroupId);
}
