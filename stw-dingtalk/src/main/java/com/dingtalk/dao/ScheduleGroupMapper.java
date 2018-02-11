package com.dingtalk.dao;

import com.dingtalk.bean.ScheduleGroup;
import com.dingtalk.bean.ScheduleGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ScheduleGroupMapper {
    long countByExample(ScheduleGroupExample example);
    
    int countByGroupName(String scheduleGroupName);

    int deleteByExample(ScheduleGroupExample example);

    int deleteByPrimaryKey(Integer scheduleGroupId);

    int insert(ScheduleGroup record);

    int insertSelective(ScheduleGroup record);

    List<ScheduleGroup> selectByExample(ScheduleGroupExample example);
    
    //查询所有排班组；
    List<ScheduleGroup> selectAllScheduleGroup();
    //根据id查询排班组;
    ScheduleGroup SelectScheduleGroupById(Integer groupId);
    
    ScheduleGroup selectByPrimaryKey(Integer scheduleGroupId);

    int updateByExampleSelective(@Param("record") ScheduleGroup record, @Param("example") ScheduleGroupExample example);

    int updateByExample(@Param("record") ScheduleGroup record, @Param("example") ScheduleGroupExample example);

    int updateByPrimaryKeySelective(ScheduleGroup record);

    int updateByPrimaryKey(ScheduleGroup record);
}