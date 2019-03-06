//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.cube;

import com.ruisi.ispire.dc.cube.operation.DataSetFilter;
import com.ruisi.ispire.dc.cube.operation.Processor;

public class FilterContext extends ProcContext {
    private String a;

    public FilterContext() {
    }

    public Processor getProccess() {
        return new DataSetFilter(this.a);
    }

    public String getExpress() {
        return this.a;
    }

    public void setExpress(String var1) {
        this.a = var1;
    }
}
