//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.grid;

import java.util.List;
import java.util.Map;

public class KpiVertical extends GridBaseProcessor {
    private String[] a;
    private String[] b;
    private String c;
    private String d;

    public KpiVertical(String[] var1, String[] var2, String var3, String var4) {
        this.a = var1;
        this.b = var2;
        this.c = var3;
        this.d = var4;
    }

    public List process() {
        int var1 = this.datas.size();

        for(int var2 = 0; var2 < var1; ++var2) {
            Map var3 = (Map)this.datas.get(var2);

            for(int var4 = 0; var4 < this.a.length; ++var4) {
                String var5 = this.a[var4];
                Double var6 = GridDataUtils.getKpiData(var3, var5);
                Map var7 = GridDataUtils.createNewData(var3);
                var7.put(this.c, this.b[var4]);
                var7.put(this.d, var6);
                this.datas.add(var7);
            }
        }

        return this.datas;
    }
}
