//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.grid;

import com.ruisi.ispire.dc.grid.GridBaseProcessor;
import com.ruisi.ispire.dc.grid.GridProcContext;
import com.ruisi.ispire.dc.grid.GridShift;

public class GridShiftContext extends GridProcContext {
    private String a;
    private String b;
    private String c;
    private String[] d;
    private String e;
    private String[] f;

    public GridShiftContext() {
    }

    public GridBaseProcessor getProccess() {
        return new GridShift(this.a, this.b, this.c, this.d, this.e, this.f);
    }

    public String getDateColumn() {
        return this.a;
    }

    public String getDateType() {
        return this.b;
    }

    public String[] getKpiColumn() {
        return this.d;
    }

    public String[] getKeyColumns() {
        return this.f;
    }

    public void setDateColumn(String var1) {
        this.a = var1;
    }

    public void setDateType(String var1) {
        this.b = var1;
    }

    public void setKpiColumn(String[] var1) {
        this.d = var1;
    }

    public void setKeyColumns(String[] var1) {
        this.f = var1;
    }

    public String getComputeType() {
        return this.e;
    }

    public void setComputeType(String var1) {
        this.e = var1;
    }

    public String getDateFormat() {
        return this.c;
    }

    public void setDateFormat(String var1) {
        this.c = var1;
    }
}
