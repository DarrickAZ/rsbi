//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.cross;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CrossField implements CrossKpi {
    private CrossField a;
    private List b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private int h;
    private Boolean i;
    private boolean j;
    private String k;
    private Integer l;
    private String m;
    private String n;
    private String o;
    private String p;
    private int q;
    private int r;
    private int s;
    private String t;
    private String u;
    private Boolean v;
    private Boolean w;
    private Boolean x;
    private String y;
    private Boolean z;
    private String A;
    private List B;
    private Boolean C;
    private Boolean D;
    private String E;
    private List F;
    private String G;
    private Integer H;
    private String I;
    private String J;
    private String K;
    private String L;
    private String M;
    private String N;
    private BigDecimal O;
    private Boolean P;
    private String Q;
    private String R;
    private Integer S;
    private String T;
    private String U;
    private Boolean V;

    @Override
    public String toString() {
        return "desc=" + this.getDesc() + ", value=" + this.getValue() + ", type=" + this.getType() + ", alias=" + this.getAlias();
    }

    public String getTestFunc() {
        return this.N;
    }

    public String getDimAggre() {
        return this.p;
    }

    public void setDimAggre(String var1) {
        this.p = var1;
    }

    public void setTestFunc(String var1) {
        this.N = var1;
    }

    public String getAliasAggregation() {
        return this.M;
    }

    public void setAliasAggregation(String var1) {
        this.M = var1;
    }

    public CrossField() {
    }

    public CrossField(CrossFieldOther var1) {
        this.m = var1.getType();
        this.d = var1.getAlias();
        this.e = var1.getValue();
        this.c = var1.getDesc();
        this.n = var1.getFormatPattern();
        this.o = var1.getAggregation();
        this.t = var1.getId();
    }

    public String getAliasFmt() {
        return this.L;
    }

    public void setAliasFmt(String var1) {
        this.L = var1;
    }

    public String getStyleClass() {
        return this.U;
    }

    public void setStyleClass(String var1) {
        this.U = var1;
    }

    public String getAliasDesc() {
        return this.K;
    }

    public void setAliasDesc(String var1) {
        this.K = var1;
    }

    public String getAlt() {
        return this.y;
    }

    public void setAlt(String var1) {
        this.y = var1;
    }

    public CrossField getParent() {
        return this.a;
    }

    public void setParent(CrossField var1) {
        this.a = var1;
    }

    public List getSubs() {
        return this.b;
    }

    public void setSubs(List var1) {
        this.b = var1;
    }

    @Override
    public String getDataClass() {
        return this.G;
    }

    @Override
    public void setDataClass(String var1) {
        this.G = var1;
    }

    @Override
    public String getExtDs() {
        return this.E;
    }

    @Override
    public void setExtDs(String var1) {
        this.E = var1;
    }

    @Override
    public String getDesc() {
        return this.c;
    }

    @Override
    public void setDesc(String var1) {
        this.c = var1;
    }

    public int getLevel() {
        return this.q;
    }

    public String getTopType() {
        return this.T;
    }

    public void setTopType(String var1) {
        this.T = var1;
    }

    public void setLevel(int var1) {
        this.q = var1;
    }

    public int getSpan() {
        return this.s;
    }

    public Integer getTop() {
        return this.S;
    }

    public void setTop(Integer var1) {
        this.S = var1;
    }

    public Boolean getShowWeek() {
        return this.P;
    }

    public void setShowWeek(Boolean var1) {
        this.P = var1;
    }

    public String getDateType() {
        return this.Q;
    }

    public void setDateType(String var1) {
        this.Q = var1;
    }

    public void setSpan(int var1) {
        this.s = var1;
    }

    @Override
    public String getAlias() {
        return this.d;
    }

    @Override
    public void setAlias(String var1) {
        this.d = var1;
    }

    @Override
    public String getValue() {
        return this.e;
    }

    @Override
    public void setValue(String var1) {
        this.e = var1;
    }

    @Override
    public String getType() {
        return this.m;
    }

    @Override
    public void setType(String var1) {
        this.m = var1;
    }

    public int getLastLevel() {
        return this.r;
    }

    public void setLastLevel(int var1) {
        this.r = var1;
    }

    @Override
    public String getFormatPattern() {
        return this.n;
    }

    @Override
    public void setFormatPattern(String var1) {
        this.n = var1;
    }

    @Override
    public String getAggregation() {
        return this.o;
    }

    @Override
    public void setAggregation(String var1) {
        this.o = var1;
    }

    public int getSize() {
        return this.h;
    }

    public void setSize(int var1) {
        this.h = var1;
    }

    @Override
    public CrossField clone() {
        CrossField var1 = null;
        try {
            var1 = (CrossField)super.clone();
        } catch (CloneNotSupportedException e1) {
            e1.printStackTrace();
        }
        var1.setSubs((List)null);
        var1.setParent((CrossField)null);
        if (this.getOther() != null && this.getOther().size() > 0) {
            ArrayList var2 = new ArrayList();
            Iterator var4 = this.F.iterator();

            while(var4.hasNext()) {
                CrossFieldOther var3 = (CrossFieldOther)var4.next();
                CrossFieldOther var5 = var3.clone();
                var2.add(var5);
            }

            var1.setOther(var2);
        }

        return var1;
    }

    public Boolean getMulti() {
        return this.i;
    }

    public void setMulti(Boolean var1) {
        this.i = var1;
    }

    @Override
    public List getOther() {
        return this.F;
    }

    @Override
    public void setOther(List var1) {
        this.F = var1;
    }

    public String getId() {
        return this.t;
    }

    public void setId(String var1) {
        this.t = var1;
    }

    @Override
    public String getFormula() {
        return this.I;
    }

    @Override
    public void setFormula(String var1) {
        this.I = var1;
    }

    @Override
    public boolean isFinanceFmt() {
        return this.j;
    }

    @Override
    public void setFinanceFmt(boolean var1) {
        this.j = var1;
    }

    public String getJsonId() {
        return this.u;
    }

    public void setJsonId(String var1) {
        this.u = var1;
    }

    public String getStart() {
        return this.k;
    }

    public void setStart(String var1) {
        this.k = var1;
    }

    public String getWidth() {
        return this.g;
    }

    public void setWidth(String var1) {
        this.g = var1;
    }

    @Override
    public String getJsFunc() {
        return this.J;
    }

    @Override
    public void setJsFunc(String var1) {
        this.J = var1;
    }

    public Boolean getCasParent() {
        return this.w;
    }

    public void setCasParent(Boolean var1) {
        this.w = var1;
    }

    public Boolean getNote() {
        return this.v;
    }

    public void setNote(Boolean var1) {
        this.v = var1;
    }

    public Boolean getUselink() {
        return this.x;
    }

    public void setUselink(Boolean var1) {
        this.x = var1;
    }

    public Boolean getOrder() {
        return this.z;
    }

    public void setOrder(Boolean var1) {
        this.z = var1;
    }

    public List getCalcCols() {
        return this.B;
    }

    public void setCalcCols(List var1) {
        this.B = var1;
    }

    public Boolean getTypeChg2Kpi() {
        return this.C;
    }

    public void setTypeChg2Kpi(Boolean var1) {
        this.C = var1;
    }

    public Integer getSpaceNum() {
        return this.l;
    }

    public void setSpaceNum(Integer var1) {
        this.l = var1;
    }

    @Override
    public BigDecimal getKpiRate() {
        return this.O;
    }

    @Override
    public void setKpiRate(BigDecimal var1) {
        this.O = var1;
    }

    public Boolean getStyleToLine() {
        return this.V;
    }

    public void setStyleToLine(Boolean var1) {
        this.V = var1;
    }

    public Boolean getNotCondition() {
        return this.D;
    }

    public void setNotCondition(Boolean var1) {
        this.D = var1;
    }

    public Integer getBrstep() {
        return this.H;
    }

    public void setBrstep(Integer var1) {
        this.H = var1;
    }

    public String getDateTypeFmt() {
        return this.R;
    }

    public void setDateTypeFmt(String var1) {
        this.R = var1;
    }

    public String getHeader() {
        return this.f;
    }

    public void setHeader(String var1) {
        this.f = var1;
    }

    public String getSort() {
        return this.A;
    }

    public void setSort(String var1) {
        this.A = var1;
    }
}
