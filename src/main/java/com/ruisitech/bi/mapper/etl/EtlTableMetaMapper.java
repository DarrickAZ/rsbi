//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.etl;

import com.ruisitech.bi.entity.etl.EtlTableMeta;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EtlTableMetaMapper {
    List<EtlTableMeta> selectTables(@Param("income") String var1, @Param("sysUser") String var2);

    List<EtlTableMeta> selectByIncomes(@Param("incomes") List<String> var1, @Param("sysUser") String var2);

    Integer tableExist(EtlTableMeta var1);

    Integer maxTableId(@Param("sysUser") String var1);

    void insertMetaTable(EtlTableMeta var1);

    void updateMetaTable(EtlTableMeta var1);

    EtlTableMeta getTable(@Param("tableId") Integer var1, @Param("sysUser") String var2);

    EtlTableMeta getTableByTname(@Param("tableName") String var1, @Param("sysUser") String var2);

    void deleteTable(EtlTableMeta var1);

    String getTableSql(@Param("tableId") Integer var1, @Param("sysUser") String var2);

    int updateEsEnable(EtlTableMeta var1);

    int updateUseEs(EtlTableMeta var1);

    List<EtlTableMeta> dwselectTables(@Param("sysUser") String var1);

    Integer selectTableIdByTabId(@Param("tbId")String tbId, @Param("sysUser")String sysUser);

    void insertTableIdMapping(@Param("tbId")String tbId, @Param("sysUser")String sysUser);

    List<EtlTableMeta> selectTablesFromDubhe(@Param("dubhe") String dubhe);

    String selectTabIdByTableId(@Param("tableId")Integer tbId, @Param("sysUser")String sysUser);
}
