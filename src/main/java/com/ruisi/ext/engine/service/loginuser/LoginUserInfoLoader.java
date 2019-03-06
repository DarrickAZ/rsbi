//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.service.loginuser;

import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import java.util.Map;

public interface LoginUserInfoLoader {
    Map loadUserInfo(ExtRequest var1, DaoHelper var2);

    String getUserId();
}
