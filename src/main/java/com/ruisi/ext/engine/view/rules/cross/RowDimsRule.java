//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.cross;

import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class RowDimsRule extends Rule {
    public RowDimsRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("orderDrill");
        if (var4 != null && var4.length() > 0) {
            CrossReportContext var5 = (CrossReportContext)this.digester.peek();
            var5.setOrderDrill("true".equalsIgnoreCase(var4));
        }

    }

    public void end(String var1, String var2) {
    }
}
