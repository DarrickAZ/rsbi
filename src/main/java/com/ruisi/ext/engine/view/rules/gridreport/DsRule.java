//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.gridreport;

import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.grid.PageInfo;
import com.ruisi.ext.engine.view.context.gridreport.GridReportContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class DsRule extends Rule {
    public DsRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        GridReportContext var4 = (GridReportContext)this.digester.peek();
        String var5 = var3.getValue("pageSize");
        String var6 = var3.getValue("refDataCenter");
        String var7 = var3.getValue("refDsource");
        if (var5 != null && var5.length() > 0) {
            PageInfo var8 = new PageInfo();
            var8.setPagesize(Integer.parseInt(var5));
            var4.setPageInfo(var8);
            MVContext var9 = RuleUtils.findMVContext(var4);
            var9.setShowForm(true);
        }

        var4.setRefDataCenter(var6);
        var4.setRefDsource(var7);
    }

    public void body(String var1, String var2, String var3) {
        if (var3 != null && var3.length() != 0) {
            GridReportContext var4 = (GridReportContext)this.digester.peek();
            String var5 = TemplateManager.getInstance().createTemplate(var3);
            var4.setTemplateName(var5);
        }
    }
}
