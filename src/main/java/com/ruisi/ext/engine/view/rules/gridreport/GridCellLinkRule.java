//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.gridreport;

import com.ruisi.ext.engine.view.context.gridreport.GridCell;
import com.ruisi.ext.engine.view.context.gridreport.GridCellLink;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class GridCellLinkRule extends Rule {
    public GridCellLinkRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("byAlias");
        String var5 = var3.getValue("target");
        String var6 = var3.getValue("type");
        GridCellLink var7 = new GridCellLink();
        var7.setByAlias(var4);
        var7.setTarget(var5.split(","));
        var7.setType(var6.split(","));
        GridCell var8 = (GridCell)this.digester.peek();
        var8.setLink(var7);
    }

    public void end(String var1, String var2) {
    }
}
