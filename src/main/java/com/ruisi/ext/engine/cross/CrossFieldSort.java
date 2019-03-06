//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.cross;

import com.ruisi.ext.engine.view.context.cross.CrossField;
import java.util.Comparator;

public class CrossFieldSort implements Comparator {
    private String a;
    private int b = 1;

    public CrossFieldSort(String var1, int var2) {
        this.a = var1;
        this.b = var2;
    }
    public int compare(CrossField var1, CrossField var2) {
        boolean var3 = false;
        int var6;
        if (var1.getValue() == null) {
            var6 = 1;
        } else if (var2.getValue() == null) {
            var6 = -1;
        } else if (this.b == 1) {
            var6 = var1.getValue().compareTo(var2.getValue());
        } else {
            Double var4 = Double.parseDouble(var1.getValue());
            Double var5 = Double.parseDouble(var2.getValue());
            var6 = var4.compareTo(var5);
        }

        if ("desc".equalsIgnoreCase(this.a)) {
            var6 = -var6;
        }

        return var6;
    }

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }
}
