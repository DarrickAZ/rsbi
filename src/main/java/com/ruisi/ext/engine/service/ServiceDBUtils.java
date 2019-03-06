//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.service;

import com.ruisi.ext.engine.dao.DaoHelper;
import java.util.Map;

public class ServiceDBUtils {
    public ServiceDBUtils() {
    }

    public static void insert(Map var0, String var1, String[] var2, DaoHelper var3) {
        StringBuffer var4 = new StringBuffer();
        var4.append("insert into ");
        var4.append(var1);
        var4.append("(");

        int var5;
        for(var5 = 0; var5 < var2.length; ++var5) {
            var4.append(var2[var5]);
            if (var5 != var2.length - 1) {
                var4.append(",");
            }
        }

        var4.append(") values (");

        for(var5 = 0; var5 < var2.length; ++var5) {
            var4.append("?");
            if (var5 != var2.length - 1) {
                var4.append(",");
            }
        }

        var4.append(")");
        var3.execute(var4.toString(), new a(var2, var0));
    }

    public static void update(Map var0, String var1, String[] var2, String var3, DaoHelper var4) {
        StringBuffer var5 = new StringBuffer();
        var5.append("update ");
        var5.append(var1);
        var5.append(" set ");

        for(int var6 = 0; var6 < var2.length; ++var6) {
            var5.append(var2[var6] + "= ? ");
            if (var6 != var2.length - 1) {
                var5.append(",");
            }
        }

        var5.append(" where " + var3 + " = ?");
        var4.execute(var5.toString(), new b(var2, var0, var3));
    }
}
