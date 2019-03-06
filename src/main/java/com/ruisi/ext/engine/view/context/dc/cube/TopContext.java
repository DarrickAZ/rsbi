//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.cube;

import com.ruisi.ispire.dc.cube.operation.DataSetTop;
import com.ruisi.ispire.dc.cube.operation.Processor;

public class TopContext extends ProcContext {
    private int a;
    private String b;

    public TopContext() {
    }

    public Processor getProccess() {
        return new DataSetTop(this.a, this.b);
    }

    public String getKpi() {
        return this.b;
    }

    public void setKpi(String var1) {
        this.b = var1;
    }

    public int getNumber() {
        return this.a;
    }

    public void setNumber(int var1) {
        this.a = var1;
    }
}
