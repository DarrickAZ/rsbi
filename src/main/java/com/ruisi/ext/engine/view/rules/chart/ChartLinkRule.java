//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.chart;

import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.view.context.chart.ChartLinkContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ChartLinkRule extends Rule {
    public ChartLinkRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("target");
        String var5 = var3.getValue("type");
        String var6 = var3.getValue("url");
        ChartLinkContext var7 = new ChartLinkContext();
        var7.setTarget(var4.split(","));
        var7.setType(var5.split(","));
        var7.setLinkUrl(var6);
        ChartContext var8 = (ChartContext)this.digester.peek();
        var8.setLink(var7);
    }

    public void end(String var1, String var2) {
    }
}
