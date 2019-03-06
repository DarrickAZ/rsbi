//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.grid;

import com.ruisi.ext.engine.view.context.dc.grid.GridComputeContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ComputeRule extends Rule {
    public ComputeRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("formula");
        String var5 = var3.getValue("name");
        GridComputeContext var6 = new GridComputeContext();
        var6.setFormula(var4);
        var6.setName(var5);
        GridDataCenterContext var7 = (GridDataCenterContext)this.digester.peek();
        var7.getProcess().add(var6);
    }

    public void end(String var1, String var2) {
    }
}
