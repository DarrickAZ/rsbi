//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.dao;

import com.ruisi.ext.engine.view.context.grid.PageInfo;

public class HiveHelperImpl implements DatabaseHelper {
    public HiveHelperImpl() {
    }

    public String getDatabaseType() {
        return "hive";
    }

    public String getClazz() {
        return "org.apache.hive.jdbc.HiveDriver";
    }

    public String getQueryPageSql(String var1, PageInfo var2) {
        return var1;
    }
}
