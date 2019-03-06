//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.cube;

import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import com.ruisi.ext.engine.view.context.dc.cube.SortContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class SortRule extends Rule {
    public SortRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("column");
        String var5 = var3.getValue("type");
        String var6 = var3.getValue("createSortColumn");
        String var7 = var3.getValue("src");
        SortContext var8 = new SortContext();
        var8.setColumn(var4);
        var8.setType(var5);
        var8.setSrc(var7);
        var8.setCreateSortColumn("true".equalsIgnoreCase(var6));
        DataCenterContext var9 = (DataCenterContext)this.digester.peek();
        var9.getProcess().add(var8);
    }

    public void end(String var1, String var2) {
    }
}
