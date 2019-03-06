//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.chart;

import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.view.context.chart.ChartKeyContext;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ChartKeyRule extends Rule {
    public ChartKeyRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        ChartKeyContext var4 = new ChartKeyContext();
        var4.setName(var3.getValue("name"));
        var4.setValue(var3.getValue("value"));
        ChartContext var5 = (ChartContext)this.digester.peek();
        if (var5.getProperties() == null) {
            ArrayList var6 = new ArrayList();
            var5.setProperties(var6);
        }

        var5.getProperties().add(var4);
    }

    public void end(String var1, String var2) {
    }
}
