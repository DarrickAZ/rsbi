//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.cube;

import com.ruisi.ispire.dc.cube.operation.ExtKpiComputer;
import com.ruisi.ispire.dc.cube.operation.Processor;
import java.util.List;

public class ExtKpiComputeContext extends ProcContext {
    private List a;
    private List b;
    private String c;
    private String d;

    public ExtKpiComputeContext() {
    }

    public Processor getProccess() {
        String[] var1 = null;
        String[] var2 = null;
        int var3;
        String var4;
        if (this.a != null && this.a.size() != 0) {
            var1 = new String[this.a.size()];

            for(var3 = 0; var3 < this.a.size(); ++var3) {
                var4 = (String)this.a.get(var3);
                var1[var3] = var4;
            }
        } else {
            var1 = new String[]{this.c};
        }

        if (this.b != null && this.b.size() != 0) {
            var2 = new String[this.b.size()];

            for(var3 = 0; var3 < this.b.size(); ++var3) {
                var4 = (String)this.b.get(var3);
                var2[var3] = var4;
            }
        } else {
            var2 = new String[]{this.d};
        }

        return new ExtKpiComputer(var1, var2);
    }

    public String getFormula() {
        return this.c;
    }

    public void setFormula(String var1) {
        this.c = var1;
    }

    public String getName() {
        return this.d;
    }

    public void setName(String var1) {
        this.d = var1;
    }

    public List getFormulas() {
        return this.a;
    }

    public void setFormulas(List var1) {
        this.a = var1;
    }

    public List getNames() {
        return this.b;
    }

    public void setNames(List var1) {
        this.b = var1;
    }
}
