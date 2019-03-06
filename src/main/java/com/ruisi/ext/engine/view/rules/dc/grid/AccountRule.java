//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.grid;

import com.ruisi.ext.engine.view.context.dc.grid.GridAccountContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class AccountRule extends Rule {
    public AccountRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("column");
        String var5 = var3.getValue("groupDim");
        GridAccountContext var6 = new GridAccountContext();
        var6.setColumn(var4);
        if (var5 != null && var5.length() > 0) {
            var6.setGroupDim(var5.split(","));
        }

        GridDataCenterContext var7 = (GridDataCenterContext)this.digester.peek();
        var7.getProcess().add(var6);
    }

    public void end(String var1, String var2) {
    }
}
