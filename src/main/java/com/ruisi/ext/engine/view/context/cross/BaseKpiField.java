//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.cross;

import java.math.BigDecimal;
import java.util.List;

public class BaseKpiField implements CrossKpi {
    private String a;
    private String b;
    private String c;
    private String d = "kpiOther";
    private String e;
    private String f;
    private Boolean g;
    private String h;
    private String i;
    private BigDecimal j;
    private boolean k;
    private String l;
    private List m;
    private String n;

    public BaseKpiField() {
    }

    public CrossKpi clone() {
        CrossKpi clone = null;
        try {
            clone = (CrossKpi) super.clone();
        } catch (CloneNotSupportedException e1) {
            e1.printStackTrace();
        }
        return clone;
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
    public Boolean getTypeChg2Kpi() {
        return this.g;
    }

    @Override
    public void setTypeChg2Kpi(Boolean var1) {
        this.g = var1;
    }

    @Override
    public String getDesc() {
        return this.a;
    }

    @Override
    public void setDesc(String var1) {
        this.a = var1;
    }

    @Override
    public String getFormatPattern() {
        return this.b;
    }

    @Override
    public void setFormatPattern(String var1) {
        this.b = var1;
    }

    @Override
    public String getAggregation() {
        return this.c;
    }

    @Override
    public void setAggregation(String var1) {
        this.c = var1;
    }

    public String getType() {
        return this.d;
    }

    public void setType(String var1) {
        this.d = var1;
    }

    public List getOther() {
        return this.m;
    }

    public void setOther(List var1) {
        this.m = var1;
    }

    @Override
    public String getAlias() {
        return this.e;
    }

    public void setAlias(String var1) {
        this.e = var1;
    }

    public String getJsFunc() {
        return this.f;
    }

    public void setJsFunc(String var1) {
        this.f = var1;
    }

    public String getFormula() {
        return this.h;
    }

    public void setFormula(String var1) {
        this.h = var1;
    }

    public String getValue() {
        return this.i;
    }

    public void setValue(String var1) {
        this.i = var1;
    }

    public BigDecimal getKpiRate() {
        return this.j;
    }

    public void setKpiRate(BigDecimal var1) {
        this.j = var1;
    }

    public String getExtDs() {
        return this.l;
    }

    public void setExtDs(String var1) {
        this.l = var1;
    }

    public String getDataClass() {
        return this.n;
    }

    public void setDataClass(String var1) {
        this.n = var1;
    }
}
