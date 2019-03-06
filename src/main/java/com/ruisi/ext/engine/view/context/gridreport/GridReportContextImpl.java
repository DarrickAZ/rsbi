//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.gridreport;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.grid.GridReportBuilder;
import com.ruisi.ext.engine.view.context.AbstractContext;
import com.ruisi.ext.engine.view.context.grid.PageInfo;

import java.util.List;
import java.util.Map;

public class GridReportContextImpl extends AbstractContext implements GridReportContext {
    private List a;
    private Map b;
    private GridCell[][] c;
    private GridCell[][] d;
    private GridCell[][] e;
    private String f;
    private String g;
    private String h;
    private PageInfo i;
    private String j;
    private String k;
    private String l = "html";
    private String m;
    private String n;
    private Boolean o;
    private String p = "def";

    public GridReportContextImpl() {
    }

    @Override
    public String getLabel() {
        return this.j;
    }

    @Override
    public void setLabel(String var1) {
        this.j = var1;
    }

    @Override
    public PageInfo getPageInfo() {
        return this.i;
    }

    @Override
    public void setPageInfo(PageInfo var1) {
        this.i = var1;
    }

    @Override
    public String getTemplateName() {
        return this.g;
    }

    @Override
    public void setTemplateName(String var1) {
        this.g = var1;
    }

    @Override
    public GridReportContext clone() {
        GridReportContext clone = null;
        try {
            clone = (GridReportContext) super.clone();
        } catch (CloneNotSupportedException e1) {
            e1.printStackTrace();
        }
        return clone;
    }

    @Override
    public AbstractBuilder createBuilder() {
        return new GridReportBuilder(this);
    }

    @Override
    public List loadOptions() {
        return this.a;
    }

    @Override
    public void setOptions(List var1) {
        this.a = var1;
    }

    @Override
    public GridCell[][] getHeaders() {
        return this.c;
    }

    @Override
    public void setHeaders(GridCell[][] var1) {
        this.c = var1;
    }

    @Override
    public GridCell[][] getDetails() {
        return this.d;
    }

    @Override
    public void setDetails(GridCell[][] var1) {
        this.d = var1;
    }

    @Override
    public GridCell[][] getFooters() {
        return this.e;
    }

    @Override
    public void setFooters(GridCell[][] var1) {
        this.e = var1;
    }

    @Override
    public String getId() {
        return this.h;
    }

    @Override
    public void setId(String var1) {
        this.h = var1;
    }

    @Override
    public String getRefDataCenter() {
        return this.f;
    }

    @Override
    public void setRefDataCenter(String var1) {
        this.f = var1;
    }

    @Override
    public Map getExtDatas() {
        return this.b;
    }

    @Override
    public void setExtDatas(Map var1) {
        this.b = var1;
    }

    @Override
    public String getRefDsource() {
        return this.k;
    }

    @Override
    public void setRefDsource(String var1) {
        this.k = var1;
    }

    @Override
    public String getWidth() {
        return this.m;
    }

    @Override
    public String getHeight() {
        return this.n;
    }

    @Override
    public void setWidth(String var1) {
        this.m = var1;
    }

    @Override
    public void setHeight(String var1) {
        this.n = var1;
    }

    public List getOptions() {
        return this.a;
    }

    @Override
    public Boolean getTrMenu() {
        return this.o;
    }

    @Override
    public void setTrMenu(Boolean var1) {
        this.o = var1;
    }

    @Override
    public String getOut() {
        return this.l;
    }

    @Override
    public void setOut(String var1) {
        this.l = var1;
    }

    @Override
    public String getStyle() {
        return this.p;
    }

    @Override
    public void setStyle(String var1) {
        this.p = var1;
    }
}
