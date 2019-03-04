//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.report;

import com.ruisitech.bi.entity.report.MbReportType;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MbReportTypeMapper {
    List<MbReportType> listcataTree(@Param("sysUser") String var1, @Param("income") String var2);

    void insertType(MbReportType var1);

    void updateType(MbReportType var1);

    void deleleType(@Param("id") Integer var1, @Param("sysUser") String var2);

    MbReportType getType(@Param("id") Integer var1, @Param("sysUser") String var2);

    Integer cntReport(@Param("id") Integer var1, @Param("sysUser") String var2);

    Integer cntMobileReport(@Param("id") Integer var1, @Param("sysUser") String var2);

    Integer maxTypeId(@Param("sysUser") String var1);
}
