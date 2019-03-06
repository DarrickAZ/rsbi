//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConstantsEngine {
    public ConstantsEngine() {
    }

    public static String replace(String var0, String var1) {
        String var2 = "(\\w*)\\$0(.*)";
        Pattern var3 = Pattern.compile(var2);
        Matcher var4 = var3.matcher(var0);
        return var4.replaceAll("$1" + var1 + "$2");
    }

    public static String replace(String var0, String var1, String var2) {
        String var3 = "(\\w*)\\$0(.*)\\$1(.*)";
        Pattern var4 = Pattern.compile(var3);
        Matcher var5 = var4.matcher(var0);
        return var5.replaceAll("$1" + var1 + "$2" + var2 + "$3");
    }

    public static String replace(String var0, String var1, String var2, String var3) {
        String var4 = "(\\w*)\\$0(.*)\\$1(.*)\\$2(.*)";
        Pattern var5 = Pattern.compile(var4);
        Matcher var6 = var5.matcher(var0);
        return var6.replaceAll("$1" + var1 + "$2" + var2 + "$3" + var3 + "$4");
    }
}
