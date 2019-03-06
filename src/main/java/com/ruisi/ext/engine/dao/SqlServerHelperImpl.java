//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.dao;

import com.ruisi.ext.engine.view.context.grid.PageInfo;

public class SqlServerHelperImpl implements DatabaseHelper {
    public SqlServerHelperImpl() {
    }

    public String getDatabaseType() {
        return "sqlser";
    }

    public String getClazz() {
        return "net.sourceforge.jtds.jdbc.Driver";
    }

    public String getQueryPageSql(String var1, PageInfo var2) {
        String var3 = "select * from ( select row_number()over(order by tempColumn)tempRowNumber,* from (select top " + (var2.getCurtpage() * (long)var2.getPagesize() + (long)var2.getPagesize()) + " 0 tempColumn," + var1.replaceFirst("select", "") + " " + ")t " + " )tt where tempRowNumber>" + var2.getCurtpage() * (long)var2.getPagesize();
        return var3;
    }
}
