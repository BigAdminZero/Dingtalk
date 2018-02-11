package com.dingtalk.iservice;

import java.util.List;

import net.sf.json.JSONArray;

import com.dingtalk.bean.Authority;
import com.dingtalk.bean.User;

public interface IAuthorityService {
	
	/**
	 * 按管理员id查询该管理员所创建的下级管理员
	 * @param createrId 管理员id
	 * @return
	 */
	List<User> selectByCreaterId(Long createrId);
	
	/**
	 * 得到所有的权限名 用于在下拉框中显示
	 * @return
	 */
	JSONArray selectAllAuthorityName();
	
	/**
	 * 管理员新增用户
	 * @param user
	 */
	void insertUser(User user);
	
	/**
	 * 根据当前登录用户得到其所创建的所有用户 并对其进行更新
	 * @param user
	 */
	void updateUserByUserID(User user);
	
	/**
	 * 根据当前登录用户得到其所创建的所有用户  删除对应的用户
	 * @param user
	 */
	void deleteUserByUserId(Long userId);
	/**
	 * 
	 * @param authorityId
	 * @return
	 */
	public Authority selectByAuthorityId(Integer authorityId);
}
