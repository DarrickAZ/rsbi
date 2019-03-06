//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.grid;

import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridJoinContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class JoinRule extends Rule {
    public JoinRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("dataKey");
        String var5 = var3.getValue("masterCol");
        String var6 = var3.getValue("slaveCol");
        String var7 = var3.getValue("appendCol");
        String var8 = var3.getValue("appendColAlias");
        GridJoinContext var9 = new GridJoinContext();
        var9.setDataKey(var4);
        var9.setMasterCol(var5);
        var9.setSlaveCol(var6);
        if (var7 != null && var7.length() > 0) {
            var9.setAppendCol(var7.split(","));
        }

        if (var8 != null && var8.length() != 0) {
            var9.setAppendColAlias(var8.split(","));
        }

        GridDataCenterContext var10 = (GridDataCenterContext)this.digester.peek();
        var10.getProcess().add(var9);
    }

    public void end(String var1, String var2) {
    }
}
