//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.grid;

import com.ruisi.ispire.dc.grid.GridBaseProcessor;
import com.ruisi.ispire.dc.grid.GridProcContext;
import com.ruisi.ispire.dc.grid.GridSort;

public class GridSortContext extends GridProcContext {
    private String[] a;
    private String[] b;
    private boolean c;
    private boolean d;

    public GridSortContext() {
    }

    public GridBaseProcessor getProccess() {
        return new GridSort(this.a, this.b, this.c, this.d);
    }

    public boolean isChangeOldOrder() {
        return this.d;
    }

    public void setChangeOldOrder(boolean var1) {
        this.d = var1;
    }

    public boolean isAppendOrder() {
        return this.c;
    }

    public void setAppendOrder(boolean var1) {
        this.c = var1;
    }

    public String[] getColumn() {
        return this.a;
    }

    public String[] getType() {
        return this.b;
    }

    public void setColumn(String var1) {
        this.a = new String[]{var1};
    }

    public void setColumn(String[] var1) {
        this.a = var1;
    }

    public void setType(String var1) {
        this.b = new String[]{var1};
    }

    public void setType(String[] var1) {
        this.b = var1;
    }
}
