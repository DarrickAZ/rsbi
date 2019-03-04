//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.app;

import com.ruisitech.bi.entity.app.Collect;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CollectMapper {
    List<Collect> listCollect(@Param("sysUser") String var1, @Param("userId") Integer var2);

    Integer collectExist(Collect var1);

    void addCollect(Collect var1);

    void delCollect(Collect var1);
}
