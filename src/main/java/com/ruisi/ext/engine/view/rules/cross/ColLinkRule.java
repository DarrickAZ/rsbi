//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.cross;

import com.ruisi.ext.engine.view.context.cross.ColLinkContext;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ColLinkRule extends Rule {
    public ColLinkRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("mvId");
        String var5 = var3.getValue("target");
        String var6 = var3.getValue("type");
        String var7 = var3.getValue("url");
        String var8 = var3.getValue("use");
        ColLinkContext var9 = new ColLinkContext();
        var9.setMvId(var4);
        if (var8 != null && "false".equalsIgnoreCase(var8)) {
            var9.setUse(false);
        }

        if (var5 != null && var5.length() > 0) {
            var9.setTarget(var5.split(","));
        }

        if (var6 != null && var6.length() > 0) {
            var9.setType(var6.split(","));
        }

        var9.setUrl(var7);
        CrossReportContext var10 = (CrossReportContext)this.digester.peek();
        var10.getCrossCols().setColLink(var9);
    }

    public void end(String var1, String var2) {
    }
}
