//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube;

import java.util.Iterator;

public class QueryExtKpi {
    private String a;
    private String b;
    private String c;
    private String d;

    public QueryExtKpi() {
    }

    public boolean isCV() {
        return this.c.equals("CV");
    }

    public boolean isBaseExtKpi(DataCenter var1) {
        boolean var2 = false;
        Iterator var4 = var1.baseExtKpi.iterator();

        while(var4.hasNext()) {
            String var3 = (String)var4.next();
            if (var3.equals(this.c)) {
                var2 = true;
                break;
            }
        }

        return var2;
    }

    public String getExpress() {
        return this.c;
    }

    public void setExpress(String var1) {
        this.c = var1;
    }

    public String getExtKpiCol() {
        return this.a;
    }

    public void setExtKpiCol(String var1) {
        this.a = var1;
    }

    public String getExtKpiDescCol() {
        return this.b;
    }

    public void setExtKpiDescCol(String var1) {
        this.b = var1;
    }

    public String getAggregation() {
        return this.d;
    }

    public void setAggregation(String var1) {
        this.d = var1;
    }
}
