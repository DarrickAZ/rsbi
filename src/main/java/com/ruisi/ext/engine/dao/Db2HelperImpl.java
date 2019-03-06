//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.dao;

import com.ruisi.ext.engine.view.context.grid.PageInfo;

public class Db2HelperImpl implements DatabaseHelper {
    public Db2HelperImpl() {
    }

    public String getDatabaseType() {
        return "db2";
    }

    public String getClazz() {
        return "com.ibm.db2.jcc.DB2Driver";
    }

    public String getQueryPageSql(String var1, PageInfo var2) {
        String var3 = "select * from (select row_number() over() row, tmp.* from (" + var1 + ") tmp " + " ) where row>" + var2.getCurtpage() * (long)var2.getPagesize() + " and row <= " + (var2.getCurtpage() * (long)var2.getPagesize() + (long)var2.getPagesize());
        return var3;
    }
}
