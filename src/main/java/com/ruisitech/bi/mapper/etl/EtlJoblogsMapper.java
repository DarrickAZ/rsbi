//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.etl;

import com.ruisitech.bi.entity.etl.EtlJoblogs;
import org.apache.ibatis.annotations.Param;

public interface EtlJoblogsMapper {
    int insert(EtlJoblogs var1);

    int deleteByJobId(@Param("jobId") Integer var1, @Param("sysUser") String var2);
}
