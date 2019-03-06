//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.dao;

import com.ruisi.ext.engine.view.context.grid.PageInfo;

public interface DatabaseHelper {
    String getDatabaseType();

    String getClazz();

    String getQueryPageSql(String var1, PageInfo var2);
}
