//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.grid;

import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.grid.DataGridContext;
import com.ruisi.ext.engine.view.context.grid.PageInfo;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class DsRule extends Rule {
    public DsRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("data");
        String var5 = var3.getValue("refDsource");
        DataGridContext var6 = (DataGridContext)this.digester.peek();
        var6.setDataId(var4);
        var6.setRefDsource(var5);
        String var7 = var3.getValue("pageSize");
        String var10;
        if (var7 != null && var7.length() > 0) {
            PageInfo var8 = new PageInfo();
            var8.setPagesize(Integer.parseInt(var7));
            var6.setPageInfo(var8);
            MVContext var9 = RuleUtils.findMVContext(var6);
            var9.setShowForm(true);
            var10 = var3.getValue("ajax");
            var6.setAjax("true".equalsIgnoreCase(var10));
            String var11 = var3.getValue("init");
            if (var11 == null || var11.length() == 0 || var11.equals("true")) {
                var6.setInit(true);
            }
        }

        String var12 = var3.getValue("ref");
        String var13 = var3.getValue("dataRef");
        var6.setDataRef(var13);
        var6.setRef(var12);
        var10 = var3.getValue("pageInputName");
        var6.setPageInputName(var10);
    }

    public void body(String var1, String var2, String var3) {
        if (var3 != null && var3.length() != 0) {
            DataGridContext var4 = (DataGridContext)this.digester.peek();
            if (var4.getRef() == null || var4.getRef().length() == 0 || var4.getDataRef() == null || var4.getDataRef().length() == 0) {
                String var5 = TemplateManager.getInstance().createTemplate(var3);
                var4.setTemplateName(var5);
            }

        }
    }
}
