//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.grid;

import java.util.List;
import java.util.Map;

public class ComputeAvg extends GridBaseProcessor {
    private String a;

    public ComputeAvg(String var1) {
        this.a = var1;
    }

    public List process() {
        double var1 = 0.0D;
        int var3 = 0;

        for(int var4 = 0; var4 < this.datas.size(); ++var4) {
            Map var5 = (Map)this.datas.get(var4);
            Double var6 = GridDataUtils.getKpiData(var5, this.a);
            if (var6 != null) {
                var1 += var6;
                ++var3;
            }
        }

        double var8 = var1 / (double)var3;

        for(int var9 = 0; var9 < this.datas.size(); ++var9) {
            Map var7 = (Map)this.datas.get(var9);
            var7.put(this.a + "_avg", var8);
        }

        ColumnInfo var10 = new ColumnInfo();
        var10.setName(this.a + "_avg".toUpperCase());
        var10.setType("Number");
        this.metaData.getQueryColumns().put(var10.getName(), var10);
        return this.datas;
    }
}
