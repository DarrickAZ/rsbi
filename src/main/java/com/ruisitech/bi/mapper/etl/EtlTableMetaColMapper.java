//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.etl;

import com.ruisitech.bi.entity.common.DSColumn;
import com.ruisitech.bi.entity.etl.EtlTableMeta;
import com.ruisitech.bi.entity.etl.EtlTableMetaCol;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EtlTableMetaColMapper {
    List<EtlTableMetaCol> queryTableColumns(@Param("tableId") Integer var1, @Param("colId") Integer var2, @Param("sysUser") String var3);

    List<EtlTableMetaCol> queryTableColumnsNotExpress(@Param("tableId") Integer var1, @Param("sysUser") String var2);

    List<EtlTableMetaCol> queryTableColumnsOnlyExpress(@Param("tableId") Integer var1, @Param("sysUser") String var2);

    Integer maxColId(@Param("sysUser") String var1);

    void insertMetaTableCol(EtlTableMetaCol var1);

    void delTableColByTableId(EtlTableMeta var1);

    void updateTableCol(EtlTableMetaCol var1);

    void delTableColById(@Param("colId") Integer var1, @Param("sysUser") String var2);

    EtlTableMetaCol getComboCol(EtlTableMetaCol var1);

    List<DSColumn> queryColumnsRetDsColumn(@Param("tableId") Integer var1, @Param("sysUser") String var2);

    int updDimExpressByFromCol(EtlTableMetaCol var1);

    int updKpiExpressByFromCol(EtlTableMetaCol var1);
}
