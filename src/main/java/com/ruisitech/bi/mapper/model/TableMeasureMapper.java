//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.model;

import com.ruisitech.bi.entity.model.TableMeasure;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface TableMeasureMapper {
    void insertKpi(TableMeasure var1);

    void updateKpi(TableMeasure var1);

    void deleteKpi(TableMeasure var1);

    int getMaxKpiId(@Param("sysUser") String var1);

    List<TableMeasure> getTableKpis(@Param("sysUser") String var1, @Param("tid") Integer var2);

    List<Map<String, Object>> listKpiDesc(@Param("sysUser") String var1, @Param("selectDsIds") String var2);

    void insertKpiGroup(TableMeasure var1);

    List<String> listGroup(@Param("sysUser") String var1, @Param("tid") Integer var2);

    void deleteKpiGroup(@Param("sysUser") String var1, @Param("kpiGroup") String var2);

    void deleteKpiGroupByTid(@Param("sysUser") String var1, @Param("tid") Integer var2);
}
