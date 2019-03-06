//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.grid;

import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class DsRule extends Rule {
    public DsRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        GridDataCenterContext var4 = (GridDataCenterContext)this.digester.peek();
        var4.getConf().setRefDsource(var3.getValue("refDsource"));
    }

    public void body(String var1, String var2, String var3) {
        if (var3 != null && var3.length() != 0) {
            GridDataCenterContext var4 = (GridDataCenterContext)this.digester.peek();
            String var5 = TemplateManager.getInstance().createTemplate(var3);
            var4.getConf().setTemplateName(var5);
        }
    }
}
