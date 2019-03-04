//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.bireport;

import com.ruisitech.bi.entity.bireport.Area;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AreaMapper {
    List<Area> listCityByProvCode(@Param("sysUser") String var1, @Param("code") String var2);

    List<Area> listProvs(@Param("sysUser") String var1);

    Area getProvByName(@Param("sysUser") String var1, @Param("name") String var2);
}
