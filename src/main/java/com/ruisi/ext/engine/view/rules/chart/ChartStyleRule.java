//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.chart;

import com.ruisi.ext.engine.view.context.chart.ChartContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ChartStyleRule extends Rule {
    public ChartStyleRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
    }

    public void end(String var1, String var2) {
    }

    public void body(String var1, String var2, String var3) {
        ChartContext var4 = (ChartContext)this.digester.peek();
        var4.setStyle(var3);
    }
}
