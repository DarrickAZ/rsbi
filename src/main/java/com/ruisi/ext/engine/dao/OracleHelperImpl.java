//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.dao;

import com.ruisi.ext.engine.view.context.grid.PageInfo;

public class OracleHelperImpl implements DatabaseHelper {
    public OracleHelperImpl() {
    }

    public String getDatabaseType() {
        return "oracle";
    }

    public String getClazz() {
        return "oracle.jdbc.driver.OracleDriver";
    }

    public String getQueryPageSql(String var1, PageInfo var2) {
        String var3 = "select * from (select rownum idd,t.* from (" + var1 + ") t where rownum<=" + (var2.getCurtpage() * (long)var2.getPagesize() + (long)var2.getPagesize()) + " order by rownum) tt where tt.idd>" + var2.getCurtpage() * (long)var2.getPagesize();
        return var3;
    }
}
