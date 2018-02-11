package com.dingtalk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dingtalk.bean.User;
import com.dingtalk.dao.UserMapper;

@Service
public class UserService {
	@Autowired
	UserMapper userMapper;
	public User findUserByNameAndPassword(User user){
	User user2 = userMapper.selectUserByNameAndPassword(user);
	return user2;
	}
}
