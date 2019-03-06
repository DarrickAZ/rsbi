//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.grid;

import com.ruisi.ispire.dc.grid.ComputeMoveAvg;
import com.ruisi.ispire.dc.grid.GridBaseProcessor;
import com.ruisi.ispire.dc.grid.GridProcContext;

public class ComputeMoveAvgContext extends GridProcContext {
    private String a;
    private int b;
    private String c;

    public ComputeMoveAvgContext() {
    }

    public GridBaseProcessor getProccess() {
        return new ComputeMoveAvg(this.a, this.b, this.c);
    }

    public String getColumn() {
        return this.a;
    }

    public int getStep() {
        return this.b;
    }

    public String getAlias() {
        return this.c;
    }

    public void setColumn(String var1) {
        this.a = var1;
    }

    public void setStep(int var1) {
        this.b = var1;
    }

    public void setAlias(String var1) {
        this.c = var1;
    }
}
