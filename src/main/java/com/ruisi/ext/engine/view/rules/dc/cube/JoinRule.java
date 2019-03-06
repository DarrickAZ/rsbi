//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.cube;

import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import com.ruisi.ext.engine.view.context.dc.cube.JoinContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class JoinRule extends Rule {
    public JoinRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("src");
        String var5 = var3.getValue("alias");
        String var6 = var3.getValue("ignoreDim");
        JoinContext var7 = new JoinContext();
        var7.setSrcDsId(var4);
        var7.setAlias(var5);
        var7.setIgnoreDim(var6);
        DataCenterContext var8 = (DataCenterContext)this.digester.peek();
        var8.getProcess().add(var7);
    }

    public void end(String var1, String var2) {
    }
}
