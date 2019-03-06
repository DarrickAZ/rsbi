//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.grid;

import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridSortContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class SortRule extends Rule {
    public SortRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("column");
        String var5 = var3.getValue("type");
        String var6 = var3.getValue("appendOrder");
        String var7 = var3.getValue("changeOldOrder");
        GridSortContext var8 = new GridSortContext();
        var8.setColumn(var4.split(","));
        var8.setType(var5.split(","));
        if (var6 != null) {
            var8.setAppendOrder("true".equalsIgnoreCase(var6));
        }

        if (var7 != null) {
            var8.setChangeOldOrder("true".equalsIgnoreCase(var7));
        }

        GridDataCenterContext var9 = (GridDataCenterContext)this.digester.peek();
        var9.getProcess().add(var8);
    }

    public void end(String var1, String var2) {
    }
}
