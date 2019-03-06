//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.chart;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.util.LabelUtils;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.chart.ChartBuilder;
import com.ruisi.ext.engine.view.context.AbstractContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ChartContextImpl extends AbstractContext implements ChartContext {
    private String a;
    private String b;
    private String c;
    private List d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private String l;
    private Boolean m;
    private String n;
    private String o;
    private String p;
    private String q;
    private String r;
    private String s;
    private String t;
    private String[] u;
    private String[] v;
    private String w;
    private String x;
    private String y;
    private Integer z;
    private Integer A;
    private Integer B;
    private ChartLinkContext C;
    private ChartTitleContext D;
    private ChartDrillContext E;
    private String F;
    private String G;
    private Boolean H;
    private List I;
    private Map J;
    private List K;

    public ChartContextImpl() {
    }

    @Override
    public String getAlign() {
        return this.F;
    }

    @Override
    public void setAlign(String var1) {
        this.F = var1;
    }

    @Override
    public ChartTitleContext getTitle() {
        return this.D;
    }

    @Override
    public void setTitle(ChartTitleContext var1) {
        this.D = var1;
    }

    @Override
    public void check() throws ExtConfigException {
        if (this.C != null) {
            MVContext var1 = RuleUtils.findMVContext(this);

            for(int var2 = 0; var2 < this.C.getTarget().length; ++var2) {
                String var3 = this.C.getTarget()[var2];
                Object var4 = LabelUtils.findObjectByLabel(var1, var3, this.C.getType()[var2]);
                if (var4 == null) {
                    String var5 = ConstantsEngine.replace("图形链接的组件 $0 不存在。", var3);
                    throw new ExtConfigException(var5);
                }
            }
        }

    }

    @Override
    public String getRefDataCenter() {
        return this.x;
    }

    @Override
    public void setRefDataCenter(String var1) {
        this.x = var1;
    }

    @Override
    public String getLabel() {
        return this.w;
    }

    @Override
    public void setLabel(String var1) {
        this.w = var1;
    }

    @Override
    public String serial2XML() {
        StringBuffer var1 = new StringBuffer();
        var1.append("<chart shape=\"" + this.b + "\" xcol=\"" + this.f + "\" ycol=\"" + this.j + "\" scol=\"" + this.o + "\" width=\"" + this.p + "\" height=\"" + this.q + "\" userSet=\"false\">");
        var1.append("<property>");
        Iterator var3 = this.K.iterator();

        while(var3.hasNext()) {
            ChartKeyContext var2 = (ChartKeyContext)var3.next();
            var1.append("<key name=\"" + var2.getName() + "\" value=\"" + var2.getValue() + "\"/>");
        }

        var1.append("</property>");
        var1.append("<style></style>");
        var1.append("<ds > <![CDATA[");
        var1.append(this.c);
        var1.append(" ]]></ds>");
        var1.append("</chart>");
        return var1.toString();
    }

    @Override
    public AbstractBuilder createBuilder() {
        return new ChartBuilder(this);
    }

    @Override
    public List loadOptions() {
        return this.d;
    }

    @Override
    public void setOptions(List var1) {
        this.d = var1;
    }

    @Override
    public String getXcolDesc() {
        return this.g;
    }

    @Override
    public void setXcolDesc(String var1) {
        this.g = var1;
    }

    @Override
    public String getTemplateName() {
        return this.c;
    }

    @Override
    public void setTemplateName(String var1) {
        this.c = var1;
    }

    @Override
    public String getY3col() {
        return this.l;
    }

    @Override
    public void setY3col(String var1) {
        this.l = var1;
    }

    @Override
    public String getY2col() {
        return this.k;
    }

    @Override
    public void setY2col(String var1) {
        this.k = var1;
    }

    @Override
    public String getRef() {
        return this.e;
    }

    @Override
    public void setRef(String var1) {
        this.e = var1;
    }

    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e1) {
            e1.printStackTrace();
        }
        return clone;
    }

    @Override
    public String getXcol() {
        return this.f;
    }

    @Override
    public ChartDrillContext getDrill() {
        return this.E;
    }

    @Override
    public void setDrill(ChartDrillContext var1) {
        this.E = var1;
    }

    @Override
    public void setXcol(String var1) {
        this.f = var1;
    }

    @Override
    public String getYcol() {
        return this.j;
    }

    @Override
    public void setYcol(String var1) {
        this.j = var1;
    }

    @Override
    public String getScol() {
        return this.o;
    }

    @Override
    public void setScol(String var1) {
        this.o = var1;
    }

    @Override
    public String getWidth() {
        return this.p;
    }

    @Override
    public ChartLinkContext getLink() {
        return this.C;
    }

    @Override
    public void setLink(ChartLinkContext var1) {
        this.C = var1;
    }

    @Override
    public Integer getRate2() {
        return this.A;
    }

    @Override
    public Integer getRate3() {
        return this.B;
    }

    @Override
    public void setRate2(Integer var1) {
        this.A = var1;
    }

    @Override
    public void setRate3(Integer var1) {
        this.B = var1;
    }

    @Override
    public Integer getRate() {
        return this.z;
    }

    @Override
    public void setRate(Integer var1) {
        this.z = var1;
    }

    @Override
    public String getRefDsource() {
        return this.y;
    }

    @Override
    public void setRefDsource(String var1) {
        this.y = var1;
    }

    @Override
    public void setWidth(String var1) {
        this.p = var1;
    }

    @Override
    public String getHeight() {
        return this.q;
    }

    @Override
    public void setHeight(String var1) {
        this.q = var1;
    }

    @Override
    public String getProperty() {
        return this.s;
    }

    @Override
    public void setProperty(String var1) {
        this.s = var1;
    }

    @Override
    public String getStyle() {
        return this.t;
    }

    @Override
    public void setStyle(String var1) {
        this.t = var1;
    }

    @Override
    public String getId() {
        return this.a;
    }

    @Override
    public void setId(String var1) {
        this.a = var1;
    }

    @Override
    public String getDtype() {
        return this.r;
    }

    @Override
    public void setDtype(String var1) {
        this.r = var1;
    }

    @Override
    public List getProperties() {
        return this.K;
    }

    @Override
    public void setProperties(List var1) {
        this.K = var1;
    }

    @Override
    public String getShape() {
        return this.b;
    }

    @Override
    public void setShape(String var1) {
        this.b = var1;
    }

    @Override
    public String[] getFormula() {
        return this.u;
    }

    @Override
    public void setFormula(String[] var1) {
        this.u = var1;
    }

    @Override
    public String[] getFormulaName() {
        return this.v;
    }

    @Override
    public void setFormulaName(String[] var1) {
        this.v = var1;
    }

    @Override
    public String getRightSer() {
        return this.G;
    }

    @Override
    public void setRightSer(String var1) {
        this.G = var1;
    }

    @Override
    public String getDateType() {
        return this.h;
    }

    @Override
    public void setDateType(String var1) {
        this.h = var1;
    }

    @Override
    public Boolean getXlsData() {
        return this.H;
    }

    @Override
    public void setXlsData(Boolean var1) {
        this.H = var1;
    }

    @Override
    public String getDateTypeFmt() {
        return this.i;
    }

    @Override
    public void setDateTypeFmt(String var1) {
        this.i = var1;
    }

    @Override
    public Boolean getMergeData() {
        return this.m;
    }

    @Override
    public void setMergeData(Boolean var1) {
        this.m = var1;
    }

    @Override
    public String getY2Aggre() {
        return this.n;
    }

    @Override
    public void setY2Aggre(String var1) {
        this.n = var1;
    }

    @Override
    public List getYcols() {
        return this.I;
    }

    @Override
    public void setYcols(List var1) {
        this.I = var1;
    }

    @Override
    public Map getSeriesColor() {
        return this.J;
    }

    @Override
    public void setSeriesColor(Map var1) {
        this.J = var1;
    }
}
