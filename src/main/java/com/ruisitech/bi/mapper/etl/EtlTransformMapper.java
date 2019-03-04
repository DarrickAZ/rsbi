//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.etl;

import com.ruisitech.bi.entity.etl.EtlTransform;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EtlTransformMapper {
    int deleteByPrimaryKey(@Param("sysUser") String var1, @Param("id") Integer var2);

    int insert(EtlTransform var1);

    EtlTransform selectByPrimaryKey(@Param("sysUser") String var1, @Param("id") Integer var2);

    List<EtlTransform> listTf(@Param("sysUser") String var1, @Param("income") String var2);

    List<EtlTransform> listEsTables(@Param("sysUser") String var1);

    int update(EtlTransform var1);

    Integer maxtfid(@Param("sysUser") String var1);

    String getTfConfig(@Param("sysUser") String var1, @Param("id") Integer var2);

    int tfTableExist(@Param("sysUser") String var1, @Param("targetTable") String var2);
}
