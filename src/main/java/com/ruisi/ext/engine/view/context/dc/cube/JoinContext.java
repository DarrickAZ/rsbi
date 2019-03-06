//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.cube;

import com.ruisi.ispire.dc.cube.operation.DataSetJoin;
import com.ruisi.ispire.dc.cube.operation.Processor;

public class JoinContext extends ProcContext {
    private String a;
    private String b;
    private String c;

    public JoinContext() {
    }

    public Processor getProccess() {
        return new DataSetJoin(this.a, this.b, this.c);
    }

    public String getAlias() {
        return this.b;
    }

    public void setAlias(String var1) {
        this.b = var1;
    }

    public String getIgnoreDim() {
        return this.c;
    }

    public void setIgnoreDim(String var1) {
        this.c = var1;
    }

    public String getSrcDsId() {
        return this.a;
    }

    public void setSrcDsId(String var1) {
        this.a = var1;
    }
}
