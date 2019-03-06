//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.grid;

import java.util.List;
import java.util.Map;

public class ComputeMoveAvg extends GridBaseProcessor {
    private String a;
    private int b;
    private String c;

    public ComputeMoveAvg(String var1, int var2, String var3) {
        this.a = var1;
        this.b = var2;
        this.c = var3;
    }

    public List process() {
        for(int var1 = this.b; var1 < this.datas.size(); ++var1) {
            Map var2 = (Map)this.datas.get(var1);
            double var3 = 0.0D;

            for(int var5 = var1; var5 > var1 - this.b; --var5) {
                Map var6 = (Map)this.datas.get(var5);
                Double var7 = GridDataUtils.getKpiData(var6, this.a);
                if (var7 != null) {
                    var3 += var7;
                }
            }

            double var8 = var3 / (double)this.b;
            var2.put(this.c, var8);
        }

        ColumnInfo var9 = new ColumnInfo();
        var9.setName(this.c.toUpperCase());
        var9.setType("Number");
        this.metaData.getQueryColumns().put(this.c.toUpperCase(), var9);
        return this.datas;
    }
}
