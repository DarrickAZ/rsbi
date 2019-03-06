//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.chart;

import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.view.context.chart.ChartTitleContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ChartTitleRule extends Rule {
    public ChartTitleRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
    }

    public void body(String var1, String var2, String var3) {
        if (var3 != null && var3.length() > 0) {
            ChartTitleContext var4 = new ChartTitleContext();
            if (var3.indexOf("$") >= 0) {
                String var5 = TemplateManager.getInstance().createTemplate(var3);
                var4.setTemplateName(var5);
                var4.setType("template");
            } else {
                var4.setText(var3);
                var4.setType("text");
            }

            ChartContext var6 = (ChartContext)this.digester.peek();
            var6.setTitle(var4);
        }

    }

    public void end(String var1, String var2) {
    }
}
