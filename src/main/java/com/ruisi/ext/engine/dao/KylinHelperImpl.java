//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.dao;

import com.ruisi.ext.engine.view.context.grid.PageInfo;

public class KylinHelperImpl implements DatabaseHelper {
    public KylinHelperImpl() {
    }

    public String getDatabaseType() {
        return "kylin";
    }

    public String getClazz() {
        return "org.apache.kylin.jdbc.Driver";
    }

    public String getQueryPageSql(String var1, PageInfo var2) {
        String var3 = "select * from ( " + var1 + " ) tt limit " + var2.getPagesize() + " offset " + var2.getCurtpage() * (long)var2.getPagesize();
        return var3;
    }
}
