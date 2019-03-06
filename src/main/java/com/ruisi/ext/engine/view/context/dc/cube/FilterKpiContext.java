//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.cube;

import com.ruisi.ispire.dc.cube.operation.DataSetKpiFilter;
import com.ruisi.ispire.dc.cube.operation.Processor;

public class FilterKpiContext extends ProcContext {
    private String a;
    private String b;
    private String c;

    public FilterKpiContext() {
    }

    public String getSrc() {
        return this.c;
    }

    public void setSrc(String var1) {
        this.c = var1;
    }

    public Processor getProccess() {
        return new DataSetKpiFilter(this.a, this.b);
    }

    public String getIn() {
        return this.a;
    }

    public void setIn(String var1) {
        this.a = var1;
    }

    public String getNotIn() {
        return this.b;
    }

    public void setNotIn(String var1) {
        this.b = var1;
    }
}
