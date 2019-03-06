//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.util;

public class ColorUtils {
    public ColorUtils() {
    }

    public static int[] HEX2RGB(String var0) {
        String var1 = var0.replaceFirst("#", "");
        int var2 = Integer.parseInt(var1.substring(0, 2), 16);
        int var3 = Integer.parseInt(var1.substring(2, 4), 16);
        int var4 = Integer.parseInt(var1.substring(4, 6), 16);
        return new int[]{var2, var3, var4};
    }
}
