//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.grid;

import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridShiftContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ShiftRule extends Rule {
    public ShiftRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("dateColumn");
        String var5 = var3.getValue("dateType");
        String var6 = var3.getValue("dateFormat");
        String var7 = var3.getValue("kpiColumn");
        String var8 = var3.getValue("computeType");
        String var9 = var3.getValue("keyColumns");
        GridShiftContext var10 = new GridShiftContext();
        var10.setDateColumn(var4);
        var10.setDateType(var5);
        var10.setDateFormat(var6);
        var10.setComputeType(var8);
        if (var9 != null && var9.length() > 0) {
            var10.setKeyColumns(var9.split(","));
        }

        if (var7 != null && var7.length() > 0) {
            var10.setKpiColumn(var7.split(","));
        }

        GridDataCenterContext var11 = (GridDataCenterContext)this.digester.peek();
        var11.getProcess().add(var10);
    }

    public void end(String var1, String var2) {
    }
}
