//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.grid;

import com.ruisi.ispire.dc.grid.GridBaseProcessor;
import com.ruisi.ispire.dc.grid.GridDateFill;
import com.ruisi.ispire.dc.grid.GridProcContext;

public class GridDateFillContext extends GridProcContext {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;

    public GridDateFillContext() {
    }

    public String getColumn() {
        return this.a;
    }

    public void setColumn(String var1) {
        this.a = var1;
    }

    public String getType() {
        return this.b;
    }

    public void setType(String var1) {
        this.b = var1;
    }

    public GridBaseProcessor getProccess() {
        return new GridDateFill(this);
    }

    public String getDataColumn() {
        return this.c;
    }

    public void setDataColumn(String var1) {
        this.c = var1;
    }

    public String getSer() {
        return this.d;
    }

    public void setSer(String var1) {
        this.d = var1;
    }

    public String getDispColumn() {
        return this.e;
    }

    public void setDispColumn(String var1) {
        this.e = var1;
    }
}
