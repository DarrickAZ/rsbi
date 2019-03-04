//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.frame;

import com.ruisitech.bi.entity.frame.Role;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    List<Map<String, Object>> listRoleMenus(@Param("roleId") Integer var1, @Param("sysUser") String var2);

    List<Map<String, Object>> roledata(@Param("roleId") Integer var1, @Param("sysUser") String var2);

    List<Role> list(@Param("sysUser") String var1, @Param("keyword") String var2);

    List<Role> listUserRole(@Param("sysUser") String var1, @Param("userId") Integer var2);

    Role getById(@Param("sysUser") String var1, @Param("roleId") Integer var2);
}
