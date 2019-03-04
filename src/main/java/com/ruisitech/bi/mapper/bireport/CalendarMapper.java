//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.bireport;

import com.ruisitech.bi.entity.bireport.Calendar;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CalendarMapper {
    void insertFestival(Calendar var1);

    void removeFestival(Calendar var1);

    Integer maxFestivalId(@Param("sysUser") String var1);

    List<Calendar> listFestival(@Param("sysUser") String var1);
}
