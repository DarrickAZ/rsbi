//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.util;

import com.ruisi.ext.engine.view.context.ExtContext;
import java.util.ArrayList;
import java.util.List;

public class NoSendParamUtils {
    private static List a;

    static {
        if (a == null) {
            a = new ArrayList();
            String var0 = ExtContext.getInstance().getConstant("noSendParam");
            if (var0 != null) {
                String[] var1 = var0.split(",");

                for(int var2 = 0; var2 < var1.length; ++var2) {
                    a.add(var1[var2]);
                }
            }
        }

    }

    public static List getParams() {
        return a;
    }

    private NoSendParamUtils() {
    }
}
