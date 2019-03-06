//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.grid;

import com.ruisi.ispire.dc.grid.GridBaseProcessor;
import com.ruisi.ispire.dc.grid.GridCompute;
import com.ruisi.ispire.dc.grid.GridProcContext;

public class GridComputeContext extends GridProcContext {
    private String a;
    private String b;

    public GridComputeContext() {
    }

    public String getFormula() {
        return this.a;
    }

    public void setFormula(String var1) {
        this.a = var1;
    }

    public String getName() {
        return this.b;
    }

    public void setName(String var1) {
        this.b = var1;
    }

    public GridBaseProcessor getProccess() {
        return new GridCompute(this.a, this.b);
    }
}
