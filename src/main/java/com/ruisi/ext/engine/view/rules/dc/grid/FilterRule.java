//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.grid;

import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridFilterContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class FilterRule extends Rule {
    public FilterRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        GridFilterContext var4 = new GridFilterContext();
        String var5 = var3.getValue("column");
        String var6 = var3.getValue("filterType");
        String var7 = var3.getValue("value");
        String var8 = var3.getValue("value2");
        String var9 = var3.getValue("dateFormat");
        var4.setColumn(var5);
        var4.setFilterType(var6);
        var4.setValue(var7);
        var4.setValue2(var8);
        var4.setDateFormat(var9);
        GridDataCenterContext var10 = (GridDataCenterContext)this.digester.peek();
        var10.getProcess().add(var4);
    }

    public void end(String var1, String var2) {
    }
}
