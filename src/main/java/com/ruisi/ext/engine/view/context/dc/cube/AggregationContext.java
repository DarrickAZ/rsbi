//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.cube;

import com.ruisi.ispire.dc.cube.operation.DataSetAggregation;
import com.ruisi.ispire.dc.cube.operation.Processor;
import java.util.List;

public class AggregationContext extends ProcContext {
    private String a;
    private List b;
    private String c;
    private String d;

    public AggregationContext() {
    }

    public Processor getProccess() {
        return this.a != null && this.a.length() != 0 ? new DataSetAggregation(this.a, this.c, this.d) : new DataSetAggregation(this.b, this.c);
    }

    public String getAsExtKpiName() {
        return this.d;
    }

    public void setAsExtKpiName(String var1) {
        this.d = var1;
    }

    public String getType() {
        return this.a;
    }

    public void setType(String var1) {
        this.a = var1;
    }

    public String getDim() {
        return this.c;
    }

    public void setDim(String var1) {
        this.c = var1;
    }

    public List getAggreTypes() {
        return this.b;
    }

    public void setAggreTypes(List var1) {
        this.b = var1;
    }
}
