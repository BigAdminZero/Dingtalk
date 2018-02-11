package com.dingtalk.dao;

import com.dingtalk.bean.OndutyGroup;
import com.dingtalk.bean.OndutyGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OndutyGroupMapper {
    long countByExample(OndutyGroupExample example);

    int deleteByExample(OndutyGroupExample example);

    int deleteByPrimaryKey(Integer ondutyGroupId);

    int insert(OndutyGroup record);

    int insertSelective(OndutyGroup record);

    List<OndutyGroup> selectByExample(OndutyGroupExample example);
    
    //按部门id查找值班组
    public List<OndutyGroup> selectOndutyGroupByDepartmentId(Long departmentId);
    
    //按值班组id查找值班组
    public OndutyGroup selectOndutyGroupByGroupId(Integer ondutyGroupId);

    
    //按值班组id更新值班组成员
    public int updateOndutyGroupEmployeeByGroupId(OndutyGroup record);
    
    //新增值班组
    public int insertOndutyGroup(OndutyGroup record);
    
    //按值班组id删除值班组
    public int deleteOndutyGroup(Integer ondutyGroupId);

    OndutyGroup selectByPrimaryKey(Integer ondutyGroupId);

    int updateByExampleSelective(@Param("record") OndutyGroup record, @Param("example") OndutyGroupExample example);

    int updateByExample(@Param("record") OndutyGroup record, @Param("example") OndutyGroupExample example);

    int updateByPrimaryKeySelective(OndutyGroup record);

    int updateByPrimaryKey(OndutyGroup record);
}