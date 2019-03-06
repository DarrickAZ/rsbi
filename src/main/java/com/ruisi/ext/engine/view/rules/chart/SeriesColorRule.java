//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.chart;

import com.ruisi.ext.engine.view.context.chart.ChartContext;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class SeriesColorRule extends Rule {
    public SeriesColorRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("seriesName");
        String var5 = var3.getValue("colorIndex");
        ChartContext var6 = (ChartContext)this.digester.peek();
        Object var7 = var6.getSeriesColor();
        if (var7 == null) {
            var7 = new HashMap();
            var6.setSeriesColor((Map)var7);
        }

        ((Map)var7).put(var4, new Integer(var5));
    }
}
