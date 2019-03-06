//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube.operation;

import com.ruisi.ispire.dc.cube.QueryExtKpi;
import com.ruisi.ispire.dc.cube.QueryKpi;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BenchmarkPerc extends BaseProcessor implements Processor {
    public BenchmarkPerc() {
    }

    public void process() {
        List var1 = this.dsMetaData.getQueryKpis();
        Iterator var3 = var1.iterator();

        while(true) {
            List var4;
            do {
                if (!var3.hasNext()) {
                    return;
                }

                QueryKpi var2 = (QueryKpi)var3.next();
                var4 = this.dataOper.queryDataByKpiCode(var2.getKpiCol());
            } while(var4.size() <= 1);

            Map var5 = (Map)var4.get(0);

            for(int var6 = 1; var6 < var4.size(); ++var6) {
                Map var7 = (Map)var4.get(var6);
                this.a(var7, var5);
            }

            this.a(var5);
        }
    }

    private void a(Map var1) {
        var1.put(this.dsMetaData.getZbKpiValueCol(), new BigDecimal(1));
        if (this.dsMetaData.getQueryExtKpis() != null) {
            Iterator var3 = this.dsMetaData.getQueryExtKpis().iterator();

            while(var3.hasNext()) {
                QueryExtKpi var2 = (QueryExtKpi)var3.next();
                var1.put(var2.getExtKpiCol(), new BigDecimal(1));
            }

        }
    }

    private void a(Map var1, Map var2) {
        BigDecimal var3 = (BigDecimal)var2.get(this.dsMetaData.getZbKpiValueCol());
        BigDecimal var4 = (BigDecimal)var1.get(this.dsMetaData.getZbKpiValueCol());
        if (var4 != null && var3 != null) {
            var1.put(this.dsMetaData.getZbKpiValueCol(), var4.divide(var3, 8, 6));
        } else {
            var1.put(this.dsMetaData.getZbKpiValueCol(), (Object)null);
        }

        if (this.dsMetaData.getQueryExtKpis() != null) {
            Iterator var6 = this.dsMetaData.getQueryExtKpis().iterator();

            while(true) {
                while(var6.hasNext()) {
                    QueryExtKpi var5 = (QueryExtKpi)var6.next();
                    BigDecimal var7 = (BigDecimal)var1.get(var5.getExtKpiCol());
                    BigDecimal var8 = (BigDecimal)var2.get(var5.getExtKpiCol());
                    if (var8 != null && var7 != null) {
                        var1.put(var5.getExtKpiCol(), var7.divide(var8, 8, 6));
                    } else {
                        var1.put(var5.getExtKpiCol(), (Object)null);
                    }
                }

                return;
            }
        }
    }
}
