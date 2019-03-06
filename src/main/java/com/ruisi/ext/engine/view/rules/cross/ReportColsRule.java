//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.cross;

import com.ruisi.ext.engine.view.context.cross.CrossCols;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ReportColsRule extends Rule {
    public ReportColsRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        CrossCols var4 = new CrossCols();
        var4.setCols(new ArrayList());
        CrossReportContext var5 = (CrossReportContext)this.digester.peek();
        var5.setCrossCols(var4);
        this.digester.push(var4);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
