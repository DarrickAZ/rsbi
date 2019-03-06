//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.cube;

import com.ruisi.ispire.dc.cube.operation.DataSetRef;
import com.ruisi.ispire.dc.cube.operation.Processor;

public class RefContext extends ProcContext {
    private String a;
    private int b;

    public RefContext() {
    }

    public String getDataSetId() {
        return this.a;
    }

    public void setDataSetId(String var1) {
        this.a = var1;
    }

    public int getStep() {
        return this.b;
    }

    public void setStep(int var1) {
        this.b = var1;
    }

    public Processor getProccess() {
        return new DataSetRef(this.b, this.a);
    }
}
