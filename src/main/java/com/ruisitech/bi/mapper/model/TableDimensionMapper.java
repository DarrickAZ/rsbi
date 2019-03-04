//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.model;

import com.ruisitech.bi.entity.model.TableDimension;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TableDimensionMapper {
    void insertDim(TableDimension var1);

    void updateDim(TableDimension var1);

    void deleteDim(TableDimension var1);

    void insertGroup(TableDimension var1);

    Integer getMaxDimId(@Param("sysUser") String var1);

    void deleteGroupById(@Param("sysUser") String var1, @Param("groupId") String var2);

    void deleteGroupByTid(@Param("sysUser") String var1, @Param("tid") Integer var2);

    List<String> listGroup(@Param("sysUser") String var1, @Param("tid") Integer var2);

    List<TableDimension> getTableDims(@Param("sysUser") String var1, @Param("tid") Integer var2);

    TableDimension queryDimCol(@Param("sysUser") String var1, @Param("dimId") Integer var2, @Param("tid") Integer var3);

    TableDimension findDimensionByAlias(@Param("sysUser") String var1, @Param("alias") String var2, @Param("tid") Integer var3);
}
