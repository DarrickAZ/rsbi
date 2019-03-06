//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.grid;

import com.ruisi.ispire.dc.grid.GridAggregation;
import com.ruisi.ispire.dc.grid.GridBaseProcessor;
import com.ruisi.ispire.dc.grid.GridProcContext;

public class GridAggregationContext extends GridProcContext {
    private String[] a;
    private AggreVO[] b;
    private boolean c;

    public GridAggregationContext() {
    }

    public GridBaseProcessor getProccess() {
        return new GridAggregation(this.a, this.b, this.c);
    }

    public boolean isToExt() {
        return this.c;
    }

    public void setToExt(boolean var1) {
        this.c = var1;
    }

    public String[] getColumn() {
        return this.a;
    }

    public void setColumn(String[] var1) {
        this.a = var1;
    }

    public AggreVO[] getAggreVO() {
        return this.b;
    }

    public void setAggreVO(AggreVO[] var1) {
        this.b = var1;
    }
}
