//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.bireport;

import com.ruisitech.bi.entity.bireport.OlapInfo;
import com.ruisitech.bi.entity.bireport.TableInfoVO;
import com.ruisitech.bi.entity.etl.EtlTableMetaCol;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface OlapMapper {
    OlapInfo getOlap(@Param("pageId") Integer var1, @Param("sysUser") String var2);

    List<OlapInfo> listreport(@Param("keyword") String var1, @Param("userId") Integer var2, @Param("sysUser") String var3);

    void deleteOlap(@Param("pageId") Integer var1, @Param("sysUser") String var2);

    void insertOlap(OlapInfo var1);

    void renameOlap(OlapInfo var1);

    void updateOlap(OlapInfo var1);

    Integer maxOlapId(@Param("sysUser") String var1);

    Integer olapExist(@Param("pageName") String var1, @Param("sysUser") String var2);

    List<Map<String, Object>> listKpiDesc(@Param("cubeId") Integer var1, @Param("sysUser") String var2);

    TableInfoVO getQueryTable(@Param("tid") Integer var1, @Param("sysUser") String var2);

    List<EtlTableMetaCol> getQueryTableCols(@Param("tid") Integer var1, @Param("sysUser") String var2);
}
