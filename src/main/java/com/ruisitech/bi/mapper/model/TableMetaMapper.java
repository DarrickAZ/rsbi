//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.model;

import com.ruisitech.bi.entity.model.TableMeta;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface TableMetaMapper {
    List<TableMeta> userSubject(TableMeta var1);

    List<TableMeta> listSubject(TableMeta var1);

    Integer tableExist(@Param("sysUser") String var1, @Param("tid") Integer var2);

    int insertTable(TableMeta var1);

    int updateTable(TableMeta var1);

    int deleteTable(@Param("sysUser") String var1, @Param("tid") Integer var2);

    TableMeta getTable(@Param("sysUser") String var1, @Param("tid") Integer var2);

    Integer getDefTid(@Param("sysUser") String var1);

    List<Map<String, Object>> listDs(@Param("sysUser") String var1, @Param("selectDsIds") String var2);

    List<Map<String, Object>> listDsMeta(@Param("sysUser") String var1, @Param("tid") Integer var2);

    List<Map<String, Object>> applistSubject(@Param("sysUser") String var1);
}
