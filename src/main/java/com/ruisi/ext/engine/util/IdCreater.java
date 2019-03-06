//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.util;

public class IdCreater {
    private static int a = 0;

    public IdCreater() {
    }

    public static synchronized int create() {
        if (a == 2147483647) {
            a = 0;
        } else {
            ++a;
        }

        return a;
    }
}
