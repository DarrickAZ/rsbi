//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.report;

import com.ruisitech.bi.entity.report.ShareUrl;
import org.apache.ibatis.annotations.Param;

public interface ShareUrlMapper {
    int deleteByPrimaryKey(@Param("sysUser") String var1, @Param("token") String var2);

    int insert(ShareUrl var1);

    ShareUrl selectByPrimaryKey(@Param("sysUser") String var1, @Param("token") String var2);

    int updateByPrimaryKey(ShareUrl var1);
}
