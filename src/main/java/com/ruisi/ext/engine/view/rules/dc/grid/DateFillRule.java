//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.grid;

import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDateFillContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class DateFillRule extends Rule {
    public DateFillRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("column");
        String var5 = var3.getValue("type");
        String var6 = var3.getValue("dataColumn");
        String var7 = var3.getValue("ser");
        String var8 = var3.getValue("dispColumn");
        GridDateFillContext var9 = new GridDateFillContext();
        var9.setColumn(var4);
        var9.setType(var5);
        var9.setDataColumn(var6);
        var9.setSer(var7);
        var9.setDispColumn(var8);
        GridDataCenterContext var10 = (GridDataCenterContext)this.digester.peek();
        var10.getProcess().add(var9);
    }

    public void end(String var1, String var2) {
    }
}
