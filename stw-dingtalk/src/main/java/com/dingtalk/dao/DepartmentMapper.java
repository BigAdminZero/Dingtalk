package com.dingtalk.dao;

import com.dingtalk.bean.Department;
import com.dingtalk.bean.DepartmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DepartmentMapper {
    long countByExample(DepartmentExample example);

    int deleteByExample(DepartmentExample example);

    int deleteByPrimaryKey(Long departmentId);

    int insert(Department record);

    int insertSelective(Department record);

    List<Department> selectByExample(DepartmentExample example);

    Department selectByPrimaryKey(Long departmentId);
    
    //查询所有部门;
    List<Department> selectAllDepartment();
    //按部门id查询部门
    public List<Department> selectDepartmentById(Long departmentId);

    int updateByExampleSelective(@Param("record") Department record, @Param("example") DepartmentExample example);

    int updateByExample(@Param("record") Department record, @Param("example") DepartmentExample example);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
    
    /**
     * 删除所有部门
     */
    void deleteAllDepartment();
}