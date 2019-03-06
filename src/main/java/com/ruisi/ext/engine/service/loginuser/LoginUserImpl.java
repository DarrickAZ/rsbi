//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.service.loginuser;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LoginUserImpl implements LoginUser, Serializable {
    private Map a = new HashMap();
    public String userId;

    public LoginUserImpl() {
    }

    public Map getUserProperties() {
        return this.a;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String var1) {
        this.userId = var1;
    }

    public Object getUserProperty(String var1) {
        return this.a.get(var1);
    }
}
