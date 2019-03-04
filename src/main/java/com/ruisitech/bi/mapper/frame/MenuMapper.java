//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.frame;

import com.ruisitech.bi.entity.frame.Menu;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface MenuMapper {
    List<Menu> listUserMenus(@Param("userId") Integer var1, @Param("sysUser") String var2);

    List<Map<String, Object>> listMenuByPid(@Param("pid") Integer var1, @Param("sysUser") String var2);

    Menu getById(@Param("menuId") Integer var1, @Param("sysUser") String var2);
}
