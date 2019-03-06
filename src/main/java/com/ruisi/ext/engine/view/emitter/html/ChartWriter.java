//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.html;

import com.ruisi.ext.engine.util.ExtChartUtils;
import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ChartWriter {
    private ExtRequest a;
    private ExtWriter b;

    public ChartWriter(ExtRequest var1, ExtWriter var2) {
        this.a = var1;
        this.b = var2;
    }

    public static String createChartModel(ChartContext var0) {
        StringBuffer var1 = new StringBuffer();
        var1.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><XML type=\"default\">");
        List var2 = ExtChartUtils.getScol(var0, var0.loadOptions());
        List var3 = ExtChartUtils.getOneScolDatas((String)var2.get(0), var0, var0.loadOptions());
        Iterator var5 = var3.iterator();

        while(var5.hasNext()) {
            Map var4 = (Map)var5.next();
            Object var6 = var4.get(var0.getXcol());
            var1.append("<COL>");
            var1.append(var6);
            var1.append("</COL>");
        }

        for(int var10 = 0; var10 < var2.size(); ++var10) {
            String var11 = (String)var2.get(var10);
            List var12 = ExtChartUtils.getOneScolDatas(var11, var0, var0.loadOptions());
            var1.append("<ROW ");

            for(int var7 = 0; var7 < var12.size(); ++var7) {
                Map var8 = (Map)var12.get(var7);
                Object var9 = var8.get(var0.getYcol());
                var1.append("col");
                var1.append(var7);
                var1.append("=\"");
                var1.append(var9);
                var1.append("\" ");
            }

            var1.append(">");
            var1.append(var11);
            var1.append("</ROW>");
        }

        var1.append("</XML>");
        return var1.toString();
    }

    public void writerUserSet(ChartContext var1) {
    }
}
