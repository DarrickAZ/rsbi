//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.grid;

import com.ruisi.ispire.dc.grid.GridAccount;
import com.ruisi.ispire.dc.grid.GridBaseProcessor;
import com.ruisi.ispire.dc.grid.GridProcContext;

public class GridAccountContext extends GridProcContext {
    private String a;
    private String[] b;

    public GridAccountContext() {
    }

    public GridBaseProcessor getProccess() {
        return new GridAccount(this.a, this.b);
    }

    public String[] getGroupDim() {
        return this.b;
    }

    public void setGroupDim(String[] var1) {
        this.b = var1;
    }

    public String getColumn() {
        return this.a;
    }

    public void setColumn(String var1) {
        this.a = var1;
    }
}
