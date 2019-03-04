//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.model;

import com.ruisitech.bi.entity.model.TableMetaCol;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface TableMetaColMapper {
    void insertMeta(TableMetaCol var1);

    Integer getMaxRid(@Param("sysUser") String var1);

    void deleteKpiMeta(@Param("sysUser") String var1, @Param("tid") Integer var2);

    void deleteDimMeta(@Param("sysUser") String var1, @Param("tid") Integer var2);

    void deleteByTid(@Param("sysUser") String var1, @Param("tid") Integer var2);

    List<Map<String, Object>> queryMetaCols(@Param("sysUser") String var1, @Param("tid") Integer var2);
}
