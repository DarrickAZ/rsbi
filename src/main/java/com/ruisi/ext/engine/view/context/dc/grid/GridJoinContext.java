//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.grid;

import com.ruisi.ispire.dc.grid.GridBaseProcessor;
import com.ruisi.ispire.dc.grid.GridJoin;
import com.ruisi.ispire.dc.grid.GridProcContext;

public class GridJoinContext extends GridProcContext {
    private String a;
    private String b;
    private String c;
    private String[] d;
    private String[] e;

    public GridJoinContext() {
    }

    public GridBaseProcessor getProccess() {
        return new GridJoin(this.a, this.b, this.c, this.d, this.e);
    }

    public String[] getAppendCol() {
        return this.d;
    }

    public String[] getAppendColAlias() {
        return this.e;
    }

    public void setAppendCol(String[] var1) {
        this.d = var1;
    }

    public void setAppendColAlias(String[] var1) {
        this.e = var1;
    }

    public String getDataKey() {
        return this.a;
    }

    public String getMasterCol() {
        return this.b;
    }

    public String getSlaveCol() {
        return this.c;
    }

    public void setDataKey(String var1) {
        this.a = var1;
    }

    public void setMasterCol(String var1) {
        this.b = var1;
    }

    public void setSlaveCol(String var1) {
        this.c = var1;
    }
}
