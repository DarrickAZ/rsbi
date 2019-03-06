//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.grid;

import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContextImpl;
import com.ruisi.ext.engine.view.context.dc.grid.GridSetConfContext;
import java.util.HashMap;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class GridDataCenterRule extends Rule {
    public GridDataCenterRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        GridDataCenterContextImpl var4 = new GridDataCenterContextImpl();
        GridSetConfContext var5 = new GridSetConfContext();
        var4.setConf(var5);
        String var6 = var3.getValue("id");
        var4.setId(var6);
        Element var7 = (Element)this.digester.peek();
        MVContext var8 = RuleUtils.findMVContext(var7);
        if (var8.getGridDataCenters() == null) {
            var8.setGridDataCenters(new HashMap());
        }

        var8.getGridDataCenters().put(var6, var4);
        this.digester.push(var4);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
