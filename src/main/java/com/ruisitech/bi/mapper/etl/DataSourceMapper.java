//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.etl;

import com.ruisitech.bi.entity.etl.DataSource;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DataSourceMapper {
    List<DataSource> listDataSource(@Param("sysUser") String var1);

    void insertDataSource(DataSource var1);

    void delDataSource(DataSource var1);

    Integer maxDataSourceid(@Param("sysUser") String var1);
}
