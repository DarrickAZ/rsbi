//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.app;

import com.ruisitech.bi.entity.app.AppPush;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface AppPushMapper {
    List<Map<String, Object>> pushlist(@Param("sysUser") String var1, @Param("userId") Integer var2);

    String getPushCfg(AppPush var1);

    List<Map<String, Object>> pushSubject(@Param("sysUser") String var1, @Param("subjectType") String var2);

    int addPushCfg(AppPush var1);

    int updatePushCfg(AppPush var1);

    int delPush(AppPush var1);

    int maxPushId(@Param("sysUser") String var1);

    int stopPush(AppPush var1);

    int startPush(AppPush var1);

    List<AppPush> listRuningPush(@Param("sysUser") String var1);
}
