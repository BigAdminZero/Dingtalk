package com.dingtalk.dao;

import com.dingtalk.bean.Authority;
import com.dingtalk.bean.AuthorityExample;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.ibatis.annotations.Param;

public interface AuthorityMapper {
    long countByExample(AuthorityExample example);

    int deleteByExample(AuthorityExample example);

    int deleteByPrimaryKey(Integer authorityId);

    int insert(Authority record);

    int insertSelective(Authority record);

    List<Authority> selectByExample(AuthorityExample example);

    public Authority selectByAuthorityId(Integer authorityId);
    
    Authority selectByPrimaryKey(Integer authorityId);

    int updateByExampleSelective(@Param("record") Authority record, @Param("example") AuthorityExample example);

    int updateByExample(@Param("record") Authority record, @Param("example") AuthorityExample example);

    int updateByPrimaryKeySelective(Authority record);

    int updateByPrimaryKey(Authority record);
    
    JSONArray selectAllAuthorityName();
}