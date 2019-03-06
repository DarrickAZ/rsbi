//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.grid;

import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisi.ext.engine.view.context.dc.grid.KpiVerticalContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class KpiVerticalRule extends Rule {
    public KpiVerticalRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("column");
        String var5 = var3.getValue("alias");
        String var6 = var3.getValue("targetCol");
        String var7 = var3.getValue("targetKpiCol");
        KpiVerticalContext var8 = new KpiVerticalContext();
        var8.setAlias(var5.split(","));
        var8.setColumn(var4.split(","));
        var8.setTargetCol(var6);
        var8.setTargetKpiCol(var7);
        GridDataCenterContext var9 = (GridDataCenterContext)this.digester.peek();
        var9.getProcess().add(var8);
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
