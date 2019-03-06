//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.util;

public class ListUtils {
    public ListUtils() {
    }

    public static boolean isExist(String var0, String[] var1) {
        boolean var2 = false;
        String[] var6 = var1;
        int var5 = var1.length;

        for(int var4 = 0; var4 < var5; ++var4) {
            String var3 = var6[var4];
            if (var0.equals(var3)) {
                var2 = true;
                break;
            }
        }

        return var2;
    }
}
