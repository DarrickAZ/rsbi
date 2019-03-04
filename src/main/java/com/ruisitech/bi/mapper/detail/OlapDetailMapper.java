//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.detail;

import com.ruisitech.bi.entity.detail.OlapDetail;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OlapDetailMapper {
    int deleteByPrimaryKey(@Param("sysUser") String var1, @Param("pageId") Integer var2);

    int insert(OlapDetail var1);

    OlapDetail selectByPrimaryKey(@Param("sysUser") String var1, @Param("pageId") Integer var2);

    List<OlapDetail> listOlapDetail(@Param("sysUser") String var1, @Param("userId") Integer var2, @Param("keyword") String var3);

    int updateByPrimaryKey(OlapDetail var1);

    int queryMaxId(@Param("sysUser") String var1);
}
