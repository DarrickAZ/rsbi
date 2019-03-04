//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.dashboard;

import com.ruisitech.bi.entity.dashboard.CompShare;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CompShareMapper {
    int deleteByPrimaryKey(@Param("id") String var1, @Param("sysUser") String var2);

    int insert(CompShare var1);

    CompShare selectByPrimaryKey(@Param("id") String var1, @Param("sysUser") String var2);

    int updateByPrimaryKeySelective(CompShare var1);

    List<CompShare> list(@Param("keyword") String var1, @Param("sysUser") String var2);
}
