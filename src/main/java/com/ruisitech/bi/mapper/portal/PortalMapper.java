//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.portal;

import com.ruisitech.bi.entity.portal.Portal;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface PortalMapper {
    List<Portal> listPortal(@Param("cataId") Integer var1, @Param("sysUser") String var2);

    String getPortalCfg(@Param("pageId") String var1, @Param("sysUser") String var2);

    List<Portal> list3g(@Param("cataId") Integer var1, @Param("sysUser") String var2);

    void insertPortal(Portal var1);

    Portal getPortal(@Param("pageId") String var1, @Param("sysUser") String var2);

    void updatePortal(Portal var1);

    void deletePortal(@Param("pageId") String var1, @Param("sysUser") String var2);

    void renamePortal(Portal var1);

    List<Map<String, Object>> listAppReport(@Param("userId") Integer var1, @Param("cataId") Integer var2, @Param("sysUser") String var3);
}
