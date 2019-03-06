//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.cross;

import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.context.cross.RowLinkContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class RowLinkRule extends Rule {
    public RowLinkRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("mvId");
        String var5 = var3.getValue("target");
        String var6 = var3.getValue("type");
        String var7 = var3.getValue("use");
        String var8 = var3.getValue("url");
        String var9 = var3.getValue("action");
        RowLinkContext var10 = new RowLinkContext();
        var10.setMvId(var4);
        var10.setUrl(var8);
        var10.setAction(var9);
        if (var7 != null && "false".equalsIgnoreCase(var7)) {
            var10.setUse(false);
        }

        if (var5 != null && var5.length() > 0) {
            var10.setTarget(var5.split(","));
        }

        if (var6 != null && var6.length() > 0) {
            var10.setType(var6.split(","));
        }

        CrossReportContext var11 = (CrossReportContext)this.digester.peek();
        var11.getCrossRows().setLink(var10);
    }

    public void end(String var1, String var2) {
    }
}
