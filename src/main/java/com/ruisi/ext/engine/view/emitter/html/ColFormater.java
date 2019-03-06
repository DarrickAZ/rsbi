//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.html;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ColFormater {
    public ColFormater() {
    }

    public static String format(Object var0, String var1) {
        if (var0 != null && !"".equals(var0)) {
            if (var0 instanceof String) {
                return (String)var0;
            } else {
                if (var1 != null && var1.length() > 0) {
                    if (var0 instanceof BigDecimal || var0 instanceof Double || var0 instanceof Integer || var0 instanceof Long || var0 instanceof Float) {
                        DecimalFormat var4 = new DecimalFormat(var1);
                        return var4.format(var0);
                    }

                    SimpleDateFormat var2;
                    if (var0 instanceof Date) {
                        var2 = new SimpleDateFormat(var1);
                        return var2.format((Date)var0);
                    }

                    if (var0 instanceof Timestamp) {
                        var2 = new SimpleDateFormat(var1);
                        Timestamp var3 = (Timestamp)var0;
                        return var2.format(new Date(var3.getTime()));
                    }
                }

                return String.valueOf(var0);
            }
        } else {
            return " &nbsp; ";
        }
    }
}
