//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.etl;

import com.ruisitech.bi.entity.etl.Config;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConfigMapper {
    void updateConfig(Config var1);

    String getConfig(@Param("cfgid") Integer var1, @Param("sysUser") String var2);

    List<Config> listConfig(@Param("sysUser") String var1);

    void insertCfg(Config var1);

    void updateCfgInfo(Config var1);

    Integer maxCfgid(@Param("sysUser") String var1);

    void delCfg(Config var1);
}
