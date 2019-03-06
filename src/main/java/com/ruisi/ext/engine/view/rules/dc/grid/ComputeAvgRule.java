//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.grid;

import com.ruisi.ext.engine.view.context.dc.grid.ComputeAvgContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ComputeAvgRule extends Rule {
    public ComputeAvgRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("column");
        ComputeAvgContext var5 = new ComputeAvgContext();
        var5.setColumn(var4);
        GridDataCenterContext var6 = (GridDataCenterContext)this.digester.peek();
        var6.getProcess().add(var5);
    }

    public void end(String var1, String var2) {
    }
}
