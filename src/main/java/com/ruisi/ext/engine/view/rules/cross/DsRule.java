//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.cross;

import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.context.grid.PageInfo;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class DsRule extends Rule {
    public DsRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        CrossReportContext var4 = (CrossReportContext)this.digester.peek();
        String var5 = var3.getValue("ref");
        String var6 = var3.getValue("refDataCenter");
        String var7 = var3.getValue("refDsource");
        var4.setRef(var5);
        var4.setRefDataCetner(var6);
        var4.setRefDsource(var7);
        String var8 = var3.getValue("pageSize");
        if (var8 != null && var8.length() > 0) {
            PageInfo var9 = new PageInfo();
            var9.setPagesize(Integer.parseInt(var8));
            var4.setPageInfo(var9);
        }

    }

    public void body(String var1, String var2, String var3) {
        if (var3 != null && var3.length() != 0) {
            CrossReportContext var4 = (CrossReportContext)this.digester.peek();
            if (var4.getRef() == null || var4.getRef().length() == 0) {
                String var5 = TemplateManager.getInstance().createTemplate(var3);
                var4.setTemplateName(var5);
            }

        }
    }
}
