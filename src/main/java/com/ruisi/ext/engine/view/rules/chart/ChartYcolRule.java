//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.chart;

import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.view.context.chart.ChartYcolContext;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ChartYcolRule extends Rule {
    public ChartYcolRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("name");
        String var5 = var3.getValue("desc");
        String var6 = var3.getValue("format");
        String var7 = var3.getValue("unit");
        ChartYcolContext var8 = new ChartYcolContext();
        var8.setDesc(var5);
        var8.setFormat(var6);
        var8.setName(var4);
        var8.setUnit(var7);
        ChartContext var9 = (ChartContext)this.digester.peek();
        if (var9.getYcols() == null) {
            var9.setYcols(new ArrayList());
        }

        var9.getYcols().add(var8);
    }
}
