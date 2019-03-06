//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.grid;

import com.ruisi.ispire.dc.grid.GridBaseProcessor;
import com.ruisi.ispire.dc.grid.GridProcContext;
import com.ruisi.ispire.dc.grid.GridTop;

public class GridTopContext extends GridProcContext {
    private Integer a;

    public GridTopContext() {
    }

    public Integer getNumber() {
        return this.a;
    }

    public void setNumber(Integer var1) {
        this.a = var1;
    }

    public GridBaseProcessor getProccess() {
        return new GridTop(this.a);
    }
}
