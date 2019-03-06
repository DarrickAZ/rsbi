//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.service.loginuser;

import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtResponse;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.Cookie;

public class LoginUserFactoryCookieImpl extends LoginUserFactory {
    LoginUserFactoryCookieImpl() {
    }

    public LoginUser getLoginUser(ExtRequest var1) {
        LoginUser var2 = (LoginUser)var1.getAttribute("ext.service.loginUser");
        return var2;
    }

    public LoginUser createLoginUser(ExtRequest var1, ExtResponse var2, DaoHelper var3, LoginUserInfoLoader var4) {
        LoginUserImpl var5 = new LoginUserImpl();
        Cookie[] var6 = var1.getCookies();
        boolean var7 = false;
        if (var6 != null) {
            Cookie[] var11 = var6;
            int var10 = var6.length;

            for(int var9 = 0; var9 < var10; ++var9) {
                Cookie var8 = var11[var9];
                String var12 = var8.getName();
                String var13;
                if (var12 != null && var12.startsWith("ext.service.loginUser")) {
                    var13 = var8.getValue();
                    String var14 = var12.substring(var12.lastIndexOf(".") + 1);
                    var5.getUserProperties().put(var14, var13);
                }

                if (var12 != null && var12.equals("ext.control.cookie.change")) {
                    var13 = var8.getValue();
                    if ("y".equalsIgnoreCase(var13)) {
                        var7 = true;
                    }
                }
            }
        }

        if (var5.getUserProperties().isEmpty() || var7) {
            Map var15 = var4.loadUserInfo(var1, var3);
            Iterator var18 = var15.entrySet().iterator();

            while(var18.hasNext()) {
                Entry var16 = (Entry)var18.next();
                String var19 = (String)var16.getKey();
                var5.getUserProperties().put(var19, var16.getValue());
                Cookie var20 = new Cookie("ext.service.loginUser." + var19, (String)var16.getValue());
                var2.addCookie(var20);
            }

            ((LoginUserImpl)var5).setUserId(var4.getUserId());
            Cookie var17 = new Cookie("ext.control.cookie.change", (String)null);
            var17.setPath("/");
            var2.addCookie(var17);
        }

        var1.setAttribute("ext.service.loginUser", var5);
        return var5;
    }
}
