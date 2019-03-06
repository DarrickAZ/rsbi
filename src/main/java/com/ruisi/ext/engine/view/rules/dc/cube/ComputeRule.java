//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.cube;

import com.ruisi.ext.engine.view.context.dc.cube.ExtKpiComputeContext;
import com.ruisi.ext.engine.view.context.dc.cube.KpiComputeContext;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ComputeRule extends Rule {
    public ComputeRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("formula");
        String var5 = var3.getValue("name");
        Object var6 = this.digester.peek();
        if (var6 instanceof ExtKpiComputeContext) {
            ExtKpiComputeContext var7 = (ExtKpiComputeContext)var6;
            if (var7.getFormulas() == null) {
                var7.setFormulas(new ArrayList());
            }

            if (var7.getNames() == null) {
                var7.setNames(new ArrayList());
            }

            var7.getFormulas().add(var4);
            var7.getNames().add(var5);
        } else if (var6 instanceof KpiComputeContext) {
            KpiComputeContext var8 = (KpiComputeContext)var6;
            if (var8.getFormulas() == null) {
                var8.setFormulas(new ArrayList());
            }

            if (var8.getNames() == null) {
                var8.setNames(new ArrayList());
            }

            var8.getFormulas().add(var4);
            var8.getNames().add(var5);
        }

    }

    public void end(String var1, String var2) {
    }
}
