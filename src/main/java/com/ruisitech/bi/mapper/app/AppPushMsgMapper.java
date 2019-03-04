//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.app;

import com.ruisitech.bi.entity.app.AppPushMsg;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface AppPushMsgMapper {
    int addPushMsg(AppPushMsg var1);

    Map<String, Object> getPushDateDim(Map<String, Object> var1);

    List<Map<String, Object>> listMsg(@Param("sysUser") String var1, @Param("userId") Integer var2);

    String getMsg(AppPushMsg var1);

    int msg2Read(AppPushMsg var1);

    int deletePush(AppPushMsg var1);
}
