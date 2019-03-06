//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.grid;

import com.ruisi.ispire.dc.grid.ComputeAvg;
import com.ruisi.ispire.dc.grid.GridBaseProcessor;
import com.ruisi.ispire.dc.grid.GridProcContext;

public class ComputeAvgContext extends GridProcContext {
    private String a;

    public ComputeAvgContext() {
    }

    public String getColumn() {
        return this.a;
    }

    public void setColumn(String var1) {
        this.a = var1;
    }

    public GridBaseProcessor getProccess() {
        return new ComputeAvg(this.a);
    }
}
