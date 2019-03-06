//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.service.impl;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementCallback;

class b implements PreparedStatementCallback {
    private final UpdateServiceImpl a;
    private final String[] b;
    private final String[] c;
    private final InputOption d;

    b(UpdateServiceImpl var1, String[] var2, String[] var3, InputOption var4) {
        this.a = var1;
        this.b = var2;
        this.c = var3;
        this.d = var4;
    }

    @Override
    public Object doInPreparedStatement(PreparedStatement var1) throws SQLException {
        for(int var2 = 0; var2 < this.b.length; ++var2) {
            String var3 = this.c[var2];
            if (var3.equalsIgnoreCase("int")) {
                var1.setInt(var2, Integer.parseInt(this.d.getParamValue(this.b[var2])));
            } else if (var3.equalsIgnoreCase("double")) {
                var1.setDouble(var2, Double.parseDouble(this.d.getParamValue(this.b[var2])));
            } else {
                if (!var3.equalsIgnoreCase("string")) {
                    String var4 = ConstantsEngine.replace("type 为 $0 的类型不支持，现只支持int,double,string", var3);
                    throw new ExtRuntimeException(var4);
                }

                var1.setString(var2, this.d.getParamValue(this.b[var2]));
            }
        }

        var1.executeUpdate();
        return null;
    }
}
