package com.dingtalk.vo;

import com.dingtalk.bean.User;

public class UserWithAuthorityLevel extends User{
	private Integer authorityLevel;

	public Integer getAuthorityLevel() {
		return authorityLevel;
	}

	public void setAuthorityLevel(Integer authorityLevel) {
		this.authorityLevel = authorityLevel;
	}
	
}
