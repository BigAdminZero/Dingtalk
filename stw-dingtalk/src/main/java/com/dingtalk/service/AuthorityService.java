package com.dingtalk.service;

import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dingtalk.bean.Authority;
import com.dingtalk.bean.User;
import com.dingtalk.dao.AuthorityMapper;
import com.dingtalk.dao.UserMapper;
import com.dingtalk.iservice.IAuthorityService;

@Service
public class AuthorityService implements IAuthorityService {

	@Autowired
	UserMapper userDao;
	@Autowired
	AuthorityMapper authorityDao;
	
	@Override
	public List<User> selectByCreaterId(Long createrId) {
		// TODO Auto-generated method stub
		return userDao.selectByCreaterId(createrId);
	}

	@Override
	public JSONArray selectAllAuthorityName() {
		
		return authorityDao.selectAllAuthorityName();
	}

	@Override
	public void insertUser(User user) {

		userDao.insert(user);
		
	}

	@Override
	public void updateUserByUserID(User user) {

		userDao.updateByPrimaryKeySelective(user);
		
	}

	@Override
	public void deleteUserByUserId(Long userId) {

		userDao.deleteByPrimaryKey(userId);
		
	}

	@Override
	public Authority selectByAuthorityId(Integer authorityId) {

		return authorityDao.selectByAuthorityId(authorityId);
	}

}
