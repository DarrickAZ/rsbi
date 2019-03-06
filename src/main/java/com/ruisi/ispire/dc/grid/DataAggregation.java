//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.grid;

import com.ruisi.ext.engine.view.context.dc.grid.AggreVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.collections.map.CaseInsensitiveMap;

public class DataAggregation {
    private String[] a;
    private AggreVO[] b;
    private List c;

    public DataAggregation(String[] var1, AggreVO[] var2, List var3) {
        this.a = var1;
        this.b = var2;
        this.c = var3;
    }

    public List process() {
        LinkedHashMap var1 = new LinkedHashMap();

        for(int var2 = 0; var2 < this.c.size(); ++var2) {
            Map var3 = (Map)this.c.get(var2);
            StringBuffer var4 = new StringBuffer("");
            if (this.a != null) {
                for(int var5 = 0; var5 < this.a.length; ++var5) {
                    String var6 = this.a[var5];
                    if (var6 != null) {
                        Object var7 = var3.get(var6);
                        var4.append(var7);
                        if (var5 != this.a.length - 1) {
                            var4.append("_");
                        }
                    }
                }
            }

            String var14 = var4.toString();
            if (!var1.containsKey(var14)) {
                var1.put(var14, new ArrayList());
            }

            ((List)var1.get(var14)).add(var3);
        }

        ArrayList var11 = new ArrayList();
        Iterator var13 = var1.entrySet().iterator();

        while(var13.hasNext()) {
            Entry var12 = (Entry)var13.next();
            CaseInsensitiveMap var15 = new CaseInsensitiveMap();
            if (this.a != null) {
                Map var16 = (Map)((List)var12.getValue()).get(0);
                String[] var10;
                int var9 = (var10 = this.a).length;

                for(int var8 = 0; var8 < var9; ++var8) {
                    String var17 = var10[var8];
                    var15.put(var17, var16.get(var17));
                }
            }

            this.a((List)var12.getValue(), var15);
            var11.add(var15);
        }

        return var11;
    }

    private void a(List var1, Map var2) {
        int var3 = 0;
        AggreVO[] var7;
        int var6 = (var7 = this.b).length;

        int var5;
        for(var5 = 0; var5 < var6; ++var5) {
            AggreVO var10000 = var7[var5];
            ++var3;
        }

        List[] var4 = new List[var3];

        for(var5 = 0; var5 < var4.length; ++var5) {
            var4[var5] = new ArrayList();
        }

        Iterator var12 = var1.iterator();

        while(var12.hasNext()) {
            Map var13 = (Map)var12.next();
            int var14 = 0;

            for(int var8 = 0; var8 < this.b.length; ++var8) {
                List var9 = var4[var14];
                String var10 = this.b[var8].getName();
                Double var11 = GridDataUtils.getKpiData(var13, var10);
                if (var11 != null) {
                    var9.add(var11);
                }

                ++var14;
            }
        }

        var5 = 0;

        for(var6 = 0; var6 < this.b.length; ++var6) {
            List var15 = var4[var5];
            this.a(var15, this.b[var6].getType(), this.b[var6].getName(), this.b[var6].getAlias(), var2);
            ++var5;
        }

    }

    private void a(List var1, String var2, String var3, String var4, Map var5) {
        if (var2.equalsIgnoreCase("count")) {
            var5.put(var4 != null && var4.length() != 0 ? var4 : var3, new Double((double)var1.size()));
        } else {
            Double var6 = null;
            if (var1.size() != 0) {
                var6 = new Double(0.0D);
            }

            if (var2.equalsIgnoreCase("sum") || var2.equalsIgnoreCase("avg")) {
                Double var7;
                for(Iterator var8 = var1.iterator(); var8.hasNext(); var6 = var6 + var7) {
                    var7 = (Double)var8.next();
                }

                if (var2.equalsIgnoreCase("avg")) {
                    var6 = var6 / (double)var1.size();
                }
            }

            if (var2.equalsIgnoreCase("max")) {
                var6 = (Double)Collections.max(var1);
            }

            if (var2.equalsIgnoreCase("min")) {
                var6 = (Double)Collections.min(var1);
            }

            var5.put(var4 != null && var4.length() != 0 ? var4 : var3, var6);
        }

    }
}
