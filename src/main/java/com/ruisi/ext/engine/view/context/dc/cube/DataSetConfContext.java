//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.cube;

import java.util.List;
import java.util.Map;

public class DataSetConfContext {
    private List a;
    private List b;
    private boolean c;
    private List d;
    private List e;
    private Map f;
    private List g;

    public DataSetConfContext() {
    }

    public Map getKpiFilters() {
        return this.f;
    }

    public void setKpiFilters(Map var1) {
        this.f = var1;
    }

    public boolean isKpiExist(String var1) {
        boolean var2 = false;

        for(int var3 = 0; var3 < this.a.size(); ++var3) {
            if (var1.equals(this.a.get(var3))) {
                var2 = true;
                break;
            }
        }

        return var2;
    }

    public boolean isKpiOrder() {
        return this.c;
    }

    public void setKpiOrder(boolean var1) {
        this.c = var1;
    }

    public List getExtKpis() {
        return this.b;
    }

    public void setExtKpis(List var1) {
        this.b = var1;
    }

    public List getKpis() {
        return this.a;
    }

    public void setKpis(List var1) {
        this.a = var1;
    }

    public List getDims() {
        return this.g;
    }

    public void setDims(List var1) {
        this.g = var1;
    }

    public List getHorizontalKpis() {
        return this.d;
    }

    public void setHorizontalKpis(List var1) {
        this.d = var1;
    }

    public List getComputeKpis() {
        return this.e;
    }

    public void setComputeKpis(List var1) {
        this.e = var1;
    }
}
