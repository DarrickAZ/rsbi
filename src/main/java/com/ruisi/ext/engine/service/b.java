//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.service;

import org.springframework.jdbc.core.PreparedStatementCallback;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

class b implements PreparedStatementCallback {

    private final String[] a;
    private final Map b;
    private final String c;

    b(String[] var1, Map var2, String var3) {
        this.a = var1;
        this.b = var2;
        this.c = var3;
    }

    @Override
    public Object doInPreparedStatement(PreparedStatement var1) throws SQLException {
        for(int var2 = 0; var2 < this.a.length; ++var2) {
            Object var3 = this.b.get(this.a[var2]);
            if (var3 instanceof Integer) {
                var1.setInt(var2 + 1, (Integer)var3);
            } else if (var3 instanceof Double) {
                var1.setDouble(var2 + 1, (Double)var3);
            } else {
                var1.setString(var2 + 1, (String)var3);
            }
        }

        var1.setString(this.a.length + 1, this.b.get(this.c).toString());
        var1.executeUpdate();
        return null;
    }
}
