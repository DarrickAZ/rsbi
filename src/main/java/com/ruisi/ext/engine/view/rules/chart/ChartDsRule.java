//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.chart;

import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.view.context.chart.ChartContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ChartDsRule extends Rule {
    public ChartDsRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        ChartContext var4 = (ChartContext)this.digester.peek();
        String var5 = var3.getValue("ref");
        String var6 = var3.getValue("refDataCenter");
        String var7 = var3.getValue("refDsource");
        var4.setRef(var5);
        var4.setRefDsource(var7);
        var4.setRefDataCenter(var6);
    }

    public void end(String var1, String var2) {
    }

    public void body(String var1, String var2, String var3) {
        if (var3 != null && var3.length() != 0) {
            ChartContext var4 = (ChartContext)this.digester.peek();
            if (var4.getRef() == null || var4.getRef().length() == 0) {
                String var5 = TemplateManager.getInstance().createTemplate(var3);
                var4.setTemplateName(var5);
            }

        }
    }
}
