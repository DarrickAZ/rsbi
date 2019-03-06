//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.grid;

import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GridAccount extends GridBaseProcessor {
    private String a;
    private String[] b;
    private Map c = new HashMap();

    public GridAccount(String var1, String[] var2) {
        this.a = var1;
        this.b = var2;
    }

    public List process() {
        for(int var1 = 0; var1 < this.datas.size(); ++var1) {
            Map var2 = (Map)this.datas.get(var1);
            Map var3 = this.b(var2);
            String var4 = var3 == null ? "" : var3.toString();
            Double var5 = (Double)this.c.get(var4);
            if (var5 == null) {
                var5 = this.a(var3);
                this.c.put(var4, var5);
            }

            Double var6 = this.a(var2, this.a);
            var2.put(this.a + "_zb", var6 != null ? var6 / var5 : null);
        }

        return this.datas;
    }

    private Double a(Map var1, String var2) {
        Object var3 = var1.get(var2);
        if (var3 == null) {
            return null;
        } else if (var3 instanceof BigDecimal) {
            return ((BigDecimal)var3).doubleValue();
        } else if (var3 instanceof Double) {
            return (Double)var3;
        } else if (var3 instanceof Long) {
            return ((Long)var3).doubleValue();
        } else {
            throw new ExtRuntimeException("类型不支持。");
        }
    }

    private double a(Map var1) {
        double var2 = 0.0D;

        for(int var4 = 0; var4 < this.datas.size(); ++var4) {
            Map var5 = (Map)this.datas.get(var4);
            if (var1 == null || this.a(var5, var1)) {
                Double var6 = this.a(var5, this.a);
                if (var6 != null) {
                    var2 += var6;
                }
            }
        }

        return var2;
    }

    private boolean a(Map var1, Map var2) {
        boolean var3 = true;
        Iterator var5 = var2.entrySet().iterator();

        while(var5.hasNext()) {
            Entry var4 = (Entry)var5.next();
            if (var4.getValue() != null && !var4.getValue().equals(var1.get(var4.getKey()))) {
                var3 = false;
                break;
            }
        }

        return var3;
    }

    private Map b(Map var1) {
        if (this.b != null && this.b.length > 0) {
            HashMap var2 = new HashMap();
            String[] var6;
            int var5 = (var6 = this.b).length;

            for(int var4 = 0; var4 < var5; ++var4) {
                String var3 = var6[var4];
                var2.put(var3, var1.get(var3));
            }

            return var2;
        } else {
            return null;
        }
    }
}
