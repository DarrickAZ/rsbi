//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.dashboard;

import com.ruisitech.bi.entity.dashboard.Dashboard;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DashboardMapper {
    int deleteByPrimaryKey(@Param("id") Integer var1, @Param("sysUser") String var2);

    int insert(Dashboard var1);

    Dashboard selectByPrimaryKey(@Param("id") Integer var1, @Param("sysUser") String var2);

    int updateByPrimaryKeySelective(Dashboard var1);

    Integer maxDashboardId(@Param("sysUser") String var1);

    List<Dashboard> list(Dashboard var1);

    int setDefDashboard(@Param("id") Integer var1, @Param("userId") Integer var2, @Param("sysUser") String var3);

    Integer getDefDashboard(@Param("userId") Integer var1, @Param("sysUser") String var2);
}
