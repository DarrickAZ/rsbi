//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.frame;

import com.ruisitech.bi.entity.frame.User;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User getUserByStaffId(@Param("staffId") String var1, @Param("sysUser") String var2);

    User getUserById(@Param("userId") Integer var1, @Param("sysUser") String var2);

    void updateuser(User var1);

    void insertuser(User var1);

    List<Map<String, Object>> listUserMenus(@Param("userId") Integer var1, @Param("sysUser") String var2);

    void updateLogDateAndCnt(User var1);

    String checkPsd(@Param("userId") Integer var1, @Param("sysUser") String var2);

    void modPsd(User var1);

    Map<String, Object> appUserinfo(@Param("userId") Integer var1, @Param("sysUser") String var2);

    int updateChannel(User var1);

    int userExist(@Param("sysUser") String var1, @Param("staffId") String var2);

    int maxUserId(@Param("sysUser") String var1);

    List<User> listUsers(@Param("keyword") String var1, @Param("sysUser") String var2);

    void updateErrCnt(User var1);

    List<User> selectLockUsers(@Param("sysUser") String var1);

    void clearErrCnt(User var1);
}
