//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.frame;

import com.ruisitech.bi.entity.frame.Department;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DepartmentMapper {
    int deleteByPrimaryKey(@Param("sysUser") String var1, @Param("id") Integer var2);

    int insertSelective(Department var1);

    Department selectByPrimaryKey(@Param("sysUser") String var1, @Param("id") Integer var2);

    int updateByPrimaryKeySelective(Department var1);

    List<Department> list(@Param("sysUser") String var1);

    Integer maxDeptId(@Param("sysUser") String var1);

    List<Department> tree(@Param("sysUser") String var1, @Param("pid") Integer var2);
}
