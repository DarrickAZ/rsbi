//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.model;

import com.ruisitech.bi.entity.model.SubjectType;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface SubjectTypeMapper {
    int deleteByPrimaryKey(@Param("sysUser") String var1, @Param("dsId") Integer var2);

    int insert(SubjectType var1);

    SubjectType selectByPrimaryKey(@Param("sysUser") String var1, @Param("dsId") Integer var2);

    int updateByPrimaryKey(SubjectType var1);

    List<Map<String, Object>> selectByTree(@Param("sysUser") String var1);

    int cntTables(@Param("sysUser") String var1, @Param("typeId") Integer var2);

    List<SubjectType> list(@Param("sysUser") String var1);

    int maxTypeid(@Param("sysUser") String var1);
}
