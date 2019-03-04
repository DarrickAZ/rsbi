//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.app;

import com.ruisitech.bi.entity.app.OlapSave;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OlapSaveMapper {
    int save(OlapSave var1);

    int update(OlapSave var1);

    List<OlapSave> list(@Param("sysUser") String var1);

    int delete(@Param("sysUser") String var1, @Param("id") Integer var2);

    String getById(@Param("sysUser") String var1, @Param("id") Integer var2);

    int maxId(@Param("sysUser") String var1);
}
