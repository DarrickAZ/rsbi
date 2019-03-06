//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.grid;

import com.ruisi.ispire.dc.grid.GridBaseProcessor;
import com.ruisi.ispire.dc.grid.GridFilter;
import com.ruisi.ispire.dc.grid.GridProcContext;

public class GridFilterContext extends GridProcContext {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;

    public GridFilterContext() {
    }

    public GridBaseProcessor getProccess() {
        return new GridFilter(this.a, this.b, this.e, this.c, this.d);
    }

    public String getColumn() {
        return this.a;
    }

    public String getFilterType() {
        return this.b;
    }

    public String getValue() {
        return this.c;
    }

    public String getValue2() {
        return this.d;
    }

    public void setColumn(String var1) {
        this.a = var1;
    }

    public void setFilterType(String var1) {
        this.b = var1;
    }

    public void setValue(String var1) {
        this.c = var1;
    }

    public void setValue2(String var1) {
        this.d = var1;
    }

    public String getDateFormat() {
        return this.e;
    }

    public void setDateFormat(String var1) {
        this.e = var1;
    }
}
