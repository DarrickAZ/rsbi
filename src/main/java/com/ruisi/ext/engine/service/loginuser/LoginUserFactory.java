//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.service.loginuser;

import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtResponse;

public abstract class LoginUserFactory {
    private static LoginUserFactory a;

    static {
        Class var0 = LoginUserFactory.class;
        synchronized(LoginUserFactory.class) {
            if (a == null) {
                a = new LoginUserFactorySessionImpl();
            }

        }
    }

    public LoginUserFactory() {
    }

    public static LoginUserFactory getInstance() {
        if (a == null) {
            throw new ExtRuntimeException("LoginUserFactory instance 未初始化.");
        } else {
            return a;
        }
    }

    public abstract LoginUser getLoginUser(ExtRequest var1);

    public abstract LoginUser createLoginUser(ExtRequest var1, ExtResponse var2, DaoHelper var3, LoginUserInfoLoader var4);
}
