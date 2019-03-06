//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.cross;

import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.context.cross.CrossRows;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ReportRowsRule extends Rule {
    public ReportRowsRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        CrossRows var4 = new CrossRows();
        var4.setRows(new ArrayList());
        String var5 = var3.getValue("overlapped");
        String var6 = var3.getValue("unmerge");
        if ("true".equals(var5)) {
            var4.setOverlapped(true);
        }

        if ("true".equals(var6)) {
            var4.setUnmerge(true);
        }

        CrossReportContext var7 = (CrossReportContext)this.digester.peek();
        var7.setCrossRows(var4);
        this.digester.push(var4);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
