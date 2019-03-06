//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.chart;

import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.view.context.chart.ChartDrillContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ChartDrillRule extends Rule {
    public ChartDrillRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("refDataCenter");
        String var5 = var3.getValue("mapCol");
        ChartDrillContext var6 = new ChartDrillContext();
        var6.setRefDataCenter(var4);
        var6.setMapCol(var5);
        ChartContext var7 = (ChartContext)this.digester.peek();
        var7.setDrill(var6);
    }

    @Override
    public void end(String var1, String var2) throws Exception {
        super.end(var1, var2);
    }
}
