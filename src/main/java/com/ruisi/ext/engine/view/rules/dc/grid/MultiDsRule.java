//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.grid;

import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisi.ext.engine.view.context.dc.grid.MultiDsContext;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class MultiDsRule extends Rule {
    private MultiDsContext a;

    public MultiDsRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        this.a = new MultiDsContext();
        String var4 = var3.getValue("refDsource");
        this.a.setRefDsource(var4);
    }

    public void body(String var1, String var2, String var3) {
        if (var3 != null && var3.length() != 0) {
            String var4 = TemplateManager.getInstance().createTemplate(var3);
            this.a.setTemplateName(var4);
            GridDataCenterContext var5 = (GridDataCenterContext)this.digester.peek();
            if (var5.getConf().getMultiDsContext() == null) {
                var5.getConf().setMultiDsContext(new ArrayList());
            }

            List var6 = var5.getConf().getMultiDsContext();
            var6.add(this.a);
        }
    }
}
