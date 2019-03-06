//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.cross;

import java.math.BigDecimal;
import java.util.List;

public class CrossFieldOther implements CrossKpi {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private Boolean i;
    private BigDecimal j;
    private boolean k;
    private String l;
    private String m;
    private List n;
    private String o;
    private Boolean p;
    private String q;
    private CrossField r;

    public CrossFieldOther() {
    }

    public String getRemoveKey() {
        return this.m;
    }

    public void setRemoveKey(String var1) {
        this.m = var1;
    }

    @Override
    public boolean isFinanceFmt() {
        return this.k;
    }

    @Override
    public void setFinanceFmt(boolean var1) {
        this.k = var1;
    }

    @Override
    public BigDecimal getKpiRate() {
        return this.j;
    }

    @Override
    public void setKpiRate(BigDecimal var1) {
        this.j = var1;
    }

    @Override
    public String getFormula() {
        return null;
    }

    @Override
    public void setFormula(String var1) {
    }

    public Boolean getUse() {
        return this.p;
    }

    public void setUse(Boolean var1) {
        this.p = var1;
    }

    @Override
    public String getJsFunc() {
        return this.h;
    }

    @Override
    public void setJsFunc(String var1) {
        this.h = var1;
    }

    @Override
    public String getFormatPattern() {
        return this.f;
    }

    @Override
    public void setFormatPattern(String var1) {
        this.f = var1;
    }

    @Override
    public String getAggregation() {
        return this.g;
    }

    @Override
    public void setAggregation(String var1) {
        this.g = var1;
    }

    @Override
    public String getDesc() {
        return this.e;
    }

    @Override
    public void setDesc(String var1) {
        this.e = var1;
    }

    public String getRetValue() {
        return this.q;
    }

    public void setRetValue(String var1) {
        this.q = var1;
    }

    @Override
    public CrossFieldOther clone() {
        CrossFieldOther clone = null;
        try {
            clone = (CrossFieldOther) super.clone();
        } catch (CloneNotSupportedException e1) {
            e1.printStackTrace();
        }
        return clone;
    }

    @Override
    public String getType() {
        return this.a;
    }

    @Override
    public void setType(String var1) {
        this.a = var1;
    }

    public String getId() {
        return this.b;
    }

    public void setId(String var1) {
        this.b = var1;
    }

    @Override
    public List getOther() {
        return this.n;
    }

    @Override
    public String getDataClass() {
        return this.o;
    }

    @Override
    public void setDataClass(String var1) {
        this.o = var1;
    }

    @Override
    public void setOther(List var1) {
        this.n = var1;
    }

    @Override
    public String getAlias() {
        return this.c;
    }

    @Override
    public void setAlias(String var1) {
        this.c = var1;
    }

    @Override
    public String getValue() {
        return this.d;
    }

    @Override
    public void setValue(String var1) {
        this.d = var1;
    }

    public CrossField getParent() {
        return this.r;
    }

    public void setParent(CrossField var1) {
        this.r = var1;
    }

    @Override
    public Boolean getTypeChg2Kpi() {
        return this.i;
    }

    @Override
    public void setTypeChg2Kpi(Boolean var1) {
        this.i = var1;
    }

    @Override
    public String getExtDs() {
        return this.l;
    }

    @Override
    public void setExtDs(String var1) {
        this.l = var1;
    }
}
