//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube.operation;

import com.ruisi.ispire.dc.cube.DataSetMetaData;
import com.ruisi.ispire.dc.cube.QueryDim;
import com.ruisi.ispire.dc.cube.QueryExtKpi;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;

public class DataSetSortImpl implements Comparator {
    public static final String ASC = "asc";
    public static final String DESC = "desc";
    private String a;
    private String b;
    private String c;
    private DataSetMetaData d;
    private QueryDim e;
    private QueryExtKpi f;

    public DataSetSortImpl(String var1, String var2, String var3, DataSetMetaData var4) {
        this.a = var1;
        this.c = var3;
        this.b = var2;
        this.d = var4;
        if (this.b.equals("dim")) {
            this.e = var4.getQueryDim(this.a);
        }

        if (this.b.equals("extKpi")) {
            this.f = var4.getQueryExtKpi(this.a);
        }

    }

    public int compare(Map var1, Map var2) {
        int var3 = 0;
        BigDecimal var4;
        BigDecimal var5;
        if (this.b.equals("kpi")) {
            var4 = (BigDecimal)var1.get(this.d.getZbKpiValueCol());
            var5 = (BigDecimal)var2.get(this.d.getZbKpiValueCol());
            var3 = var4.compareTo(var5);
        } else if (this.b.equals("extKpi")) {
            var4 = (BigDecimal)var1.get(this.f.getExtKpiCol());
            var5 = (BigDecimal)var2.get(this.f.getExtKpiCol());
            var3 = var4.compareTo(var5);
        } else {
            String var6;
            String var7;
            if (this.b.equals("dim")) {
                var6 = (String)var1.get(this.e.getOrderCol());
                var7 = (String)var2.get(this.e.getOrderCol());
                var3 = var6.compareTo(var7);
            } else if (this.b.equals("CKP_CODE_COLUMN")) {
                var6 = (String)var1.get(this.d.getZbKpiCol());
                var7 = (String)var2.get(this.d.getZbKpiCol());
                var3 = var6.compareTo(var7);
            }
        }

        if ("desc".equalsIgnoreCase(this.c)) {
            var3 = -var3;
        }

        return var3;
    }

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }
}
