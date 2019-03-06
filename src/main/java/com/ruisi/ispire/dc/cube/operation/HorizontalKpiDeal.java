//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube.operation;

import com.ruisi.ispire.dc.cube.DataSet;
import com.ruisi.ispire.dc.cube.DataSetMetaData;
import com.ruisi.ispire.dc.cube.QueryExtKpi;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HorizontalKpiDeal {
    private DataSet a;
    private DataOperUtils b;
    private DataSetMetaData c;
    private List d;

    public HorizontalKpiDeal(DataSet var1, List var2) {
        this.a = var1;
        this.b = new DataOperUtils(var1.getDatas(), var1.getDataSetMetaData());
        this.c = var1.getDataSetMetaData();
        this.d = var2;
    }

    public void process() {
        if (this.d != null && this.d.size() != 0) {
            ArrayList var1 = new ArrayList();
            ArrayList var2 = new ArrayList();
            Iterator var4 = this.a.getDatas().iterator();

            Map var3;
            String var5;
            while(var4.hasNext()) {
                var3 = (Map)var4.next();
                var5 = (String)var3.get(this.c.getZbKpiCol());
                if (this.d.contains(var5)) {
                    var1.add(var3);
                } else {
                    var2.add(var3);
                }
            }

            var4 = var2.iterator();

            String var6;
            Iterator var7;
            Map var8;
            while(var4.hasNext()) {
                var3 = (Map)var4.next();
                Map var12 = this.b.createQueryKey(var3);
                var7 = this.d.iterator();

                while(var7.hasNext()) {
                    var6 = (String)var7.next();
                    var8 = this.b.getDataByKeysAndKpiCode(var1, var12, var6);
                    Object var9 = var8.get(this.c.getZbKpiValueCol());
                    var3.put(var6, var9);
                }
            }

            this.a.setDatas(var2);
            var4 = var1.iterator();

            while(true) {
                do {
                    if (!var4.hasNext()) {
                        var4 = this.d.iterator();

                        while(var4.hasNext()) {
                            String var11 = (String)var4.next();
                            QueryExtKpi var13 = new QueryExtKpi();
                            var13.setExtKpiCol(var11);
                            var13.setExpress(var11);
                            this.c.getQueryExtKpis().add(var13);
                        }

                        return;
                    }

                    var3 = (Map)var4.next();
                    var5 = (String)var3.get(this.c.getZbKpiCol());
                } while(!this.c.isKpi(var5));

                var7 = this.d.iterator();

                while(var7.hasNext()) {
                    var6 = (String)var7.next();
                    var8 = this.b.createQueryKey(var3);
                    Map var14 = this.b.getDataByKeysAndKpiCode(var1, var8, var6);
                    Object var10 = var14.get(this.c.getZbKpiValueCol());
                    var3.put(var6, var10);
                }

                this.a.getDatas().add(var3);
            }
        }
    }
}
