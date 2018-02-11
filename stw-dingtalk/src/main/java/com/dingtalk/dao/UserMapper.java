package com.dingtalk.dao;

import com.dingtalk.bean.User;
import com.dingtalk.bean.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Long userId);
    //按姓名和密码获取user
    public User selectUserByNameAndPassword(User user);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    /**
     * 按管理员id查询该管理员所创建的下级管理员
     * @param createrId 管理员Id
     * @return
     */
    List<User> selectByCreaterId(Long createrId);
}