//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.cube;

import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import com.ruisi.ext.engine.view.context.dc.cube.TopContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class TopRule extends Rule {
    public TopRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("number");
        String var5 = var3.getValue("kpi");
        TopContext var6 = new TopContext();
        var6.setNumber(Integer.parseInt(var4));
        var6.setKpi(var5);
        DataCenterContext var7 = (DataCenterContext)this.digester.peek();
        var7.getProcess().add(var6);
    }

    public void end(String var1, String var2) {
    }
}
