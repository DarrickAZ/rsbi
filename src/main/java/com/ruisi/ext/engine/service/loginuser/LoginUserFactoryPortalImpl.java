//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.service.loginuser;

import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtResponse;
import com.ruisi.ext.engine.wrapper.ExtSession;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class LoginUserFactoryPortalImpl extends LoginUserFactory {
    LoginUserFactoryPortalImpl() {
    }

    public LoginUser getLoginUser(ExtRequest var1) {
        ExtSession var2 = var1.getSession();
        LoginUser var3 = (LoginUser)var2.getAttribute("ext.service.loginUser");
        return var3;
    }

    public LoginUser createLoginUser(ExtRequest var1, ExtResponse var2, DaoHelper var3, LoginUserInfoLoader var4) {
        ExtSession var5 = var1.getSession();
        Object var6 = (LoginUser)var5.getAttribute("ext.service.loginUser");
        if (var6 == null) {
            var6 = new LoginUserImpl();
            Map var7 = var4.loadUserInfo(var1, var3);
            Iterator var9 = var7.entrySet().iterator();

            while(var9.hasNext()) {
                Entry var8 = (Entry)var9.next();
                ((LoginUser)var6).getUserProperties().put((String)var8.getKey(), var8.getValue());
            }

            ((LoginUserImpl)var6).setUserId(var4.getUserId());
            var5.setAttribute("ext.service.loginUser", var6);
        }

        return (LoginUser)var6;
    }
}
