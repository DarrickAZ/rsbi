//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.grid;

import com.ruisi.ext.engine.view.context.dc.grid.ComputeMoveAvgContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ComputeMoveAvgRule extends Rule {
    public ComputeMoveAvgRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("column");
        String var5 = var3.getValue("step");
        String var6 = var3.getValue("alias");
        ComputeMoveAvgContext var7 = new ComputeMoveAvgContext();
        var7.setAlias(var6);
        var7.setColumn(var4);
        var7.setStep(Integer.parseInt(var5));
        GridDataCenterContext var8 = (GridDataCenterContext)this.digester.peek();
        var8.getProcess().add(var7);
    }

    @Override
    public void end(String var1, String var2) {
        try {
            super.end(var1, var2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
