//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.cross;

import com.ruisi.ext.engine.view.context.cross.BaseKpiField;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class BaseKpiRule extends Rule {
    public BaseKpiRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("alias");
        String var5 = var3.getValue("aggregation");
        String var6 = var3.getValue("formatPattern");
        BaseKpiField var7 = new BaseKpiField();
        var7.setAggregation(var5);
        var7.setAlias(var4);
        var7.setFormatPattern(var6);
        CrossReportContext var8 = (CrossReportContext)this.digester.peek();
        var8.setBaseKpi(var7);
    }

    public void end(String var1, String var2) {
    }
}
