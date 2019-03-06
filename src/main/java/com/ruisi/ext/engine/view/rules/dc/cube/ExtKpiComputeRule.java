//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.cube;

import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import com.ruisi.ext.engine.view.context.dc.cube.ExtKpiComputeContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ExtKpiComputeRule extends Rule {
    public ExtKpiComputeRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("formula");
        String var5 = var3.getValue("name");
        ExtKpiComputeContext var6 = new ExtKpiComputeContext();
        var6.setFormula(var4);
        var6.setName(var5);
        DataCenterContext var7 = (DataCenterContext)this.digester.peek();
        var7.getProcess().add(var6);
        this.digester.push(var6);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
