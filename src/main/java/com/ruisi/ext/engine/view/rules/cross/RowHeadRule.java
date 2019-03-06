//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.cross;

import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.context.cross.RowHeadContext;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class RowHeadRule extends Rule {
    public RowHeadRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("desc");
        String var5 = var3.getValue("styleClass");
        String var6 = var3.getValue("width");
        RowHeadContext var7 = new RowHeadContext();
        var7.setDesc(var4);
        var7.setStyleClass(var5);
        var7.setWidth(var6);
        CrossReportContext var8 = (CrossReportContext)this.digester.peek();
        if (var8.getRowHeads() == null) {
            var8.setRowHeads(new ArrayList());
        }

        var8.getRowHeads().add(var7);
    }

    public void end(String var1, String var2) {
    }
}
