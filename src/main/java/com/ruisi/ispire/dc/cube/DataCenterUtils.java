//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube;

public class DataCenterUtils {
    public DataCenterUtils() {
    }

    public static String Array2String(String[] var0) {
        StringBuffer var1 = new StringBuffer();

        for(int var2 = 0; var2 < var0.length; ++var2) {
            var1.append("'" + var0[var2] + "'");
            if (var2 != var0.length - 1) {
                var1.append(",");
            }
        }

        return var1.toString();
    }
}
