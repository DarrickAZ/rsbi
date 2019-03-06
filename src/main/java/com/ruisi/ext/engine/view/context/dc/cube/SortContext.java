//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.cube;

import com.ruisi.ispire.dc.cube.operation.DataSetSort;
import com.ruisi.ispire.dc.cube.operation.Processor;

public class SortContext extends ProcContext {
    private String a;
    private String b;
    private boolean c;
    private String d;

    public SortContext() {
    }

    public String getSrc() {
        return this.d;
    }

    public void setSrc(String var1) {
        this.d = var1;
    }

    public Processor getProccess() {
        return new DataSetSort(this.d, this.a, this.b, this.c);
    }

    public String getColumn() {
        return this.a;
    }

    public void setColumn(String var1) {
        this.a = var1;
    }

    public String getType() {
        return this.b;
    }

    public void setType(String var1) {
        this.b = var1;
    }

    public boolean isCreateSortColumn() {
        return this.c;
    }

    public void setCreateSortColumn(boolean var1) {
        this.c = var1;
    }
}
