//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.cube;

import com.ruisi.ispire.dc.cube.operation.KpiComputer;
import com.ruisi.ispire.dc.cube.operation.Processor;
import java.util.List;

public class KpiComputeContext extends ProcContext {
    private String a;
    private String b;
    private String c;
    private List d;
    private List e;

    public KpiComputeContext() {
    }

    public List getFormulas() {
        return this.d;
    }

    public void setFormulas(List var1) {
        this.d = var1;
    }

    public List getNames() {
        return this.e;
    }

    public void setNames(List var1) {
        this.e = var1;
    }

    public String getSrc() {
        return this.c;
    }

    public void setSrc(String var1) {
        this.c = var1;
    }

    public Processor getProccess() {
        String[] var1 = null;
        String[] var2 = null;
        int var3;
        String var4;
        if (this.d != null && this.d.size() != 0) {
            var1 = new String[this.d.size()];

            for(var3 = 0; var3 < this.d.size(); ++var3) {
                var4 = (String)this.d.get(var3);
                var1[var3] = var4;
            }
        } else {
            var1 = new String[]{this.a};
        }

        if (this.e != null && this.e.size() != 0) {
            var2 = new String[this.e.size()];

            for(var3 = 0; var3 < this.e.size(); ++var3) {
                var4 = (String)this.e.get(var3);
                var2[var3] = var4;
            }
        } else {
            var2 = new String[]{this.b};
        }

        return new KpiComputer(this.c, var1, var2);
    }

    public String getFormula() {
        return this.a;
    }

    public void setFormula(String var1) {
        this.a = var1;
    }

    public String getName() {
        return this.b;
    }

    public void setName(String var1) {
        this.b = var1;
    }
}
