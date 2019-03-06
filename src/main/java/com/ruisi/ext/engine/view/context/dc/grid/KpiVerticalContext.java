//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.grid;

import com.ruisi.ispire.dc.grid.GridBaseProcessor;
import com.ruisi.ispire.dc.grid.GridProcContext;
import com.ruisi.ispire.dc.grid.KpiVertical;

public class KpiVerticalContext extends GridProcContext {
    private String[] a;
    private String[] b;
    private String c;
    private String d;

    public KpiVerticalContext() {
    }

    public GridBaseProcessor getProccess() {
        return new KpiVertical(this.a, this.b, this.c, this.d);
    }

    public String[] getColumn() {
        return this.a;
    }

    public String[] getAlias() {
        return this.b;
    }

    public String getTargetCol() {
        return this.c;
    }

    public void setColumn(String[] var1) {
        this.a = var1;
    }

    public void setAlias(String[] var1) {
        this.b = var1;
    }

    public void setTargetCol(String var1) {
        this.c = var1;
    }

    public String getTargetKpiCol() {
        return this.d;
    }

    public void setTargetKpiCol(String var1) {
        this.d = var1;
    }
}
