//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.service.loginuser;

import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import java.util.HashMap;
import java.util.Map;

public class LoginUserInfoLoaderImpl implements LoginUserInfoLoader {
    public LoginUserInfoLoaderImpl() {
    }

    public Map loadUserInfo(ExtRequest var1, DaoHelper var2) {
        HashMap var3 = new HashMap();
        var3.put("login_id", "admin");
        return var3;
    }

    public String getUserId() {
        return "admin";
    }
}
