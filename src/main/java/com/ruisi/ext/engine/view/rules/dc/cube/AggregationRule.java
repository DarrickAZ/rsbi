//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.cube;

import com.ruisi.ext.engine.view.context.dc.cube.AggregationContext;
import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class AggregationRule extends Rule {
    public AggregationRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        AggregationContext var4 = new AggregationContext();
        var4.setDim(var3.getValue("dim"));
        var4.setType(var3.getValue("type"));
        String var5 = var3.getValue("asExtKpiName");
        var4.setAsExtKpiName(var5);
        DataCenterContext var6 = (DataCenterContext)this.digester.peek();
        var6.getProcess().add(var4);
        this.digester.push(var4);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
