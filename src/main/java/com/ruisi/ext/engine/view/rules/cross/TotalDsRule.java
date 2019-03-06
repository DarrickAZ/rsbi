//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.cross;

import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import org.apache.commons.digester.Rule;

public class TotalDsRule extends Rule {
    public TotalDsRule() {
    }

    public void body(String var1, String var2, String var3) {
        if (var3 != null && var3.length() != 0) {
            CrossReportContext var4 = (CrossReportContext)this.digester.peek();
            if (var4.getRef() == null || var4.getRef().length() == 0) {
                String var5 = TemplateManager.getInstance().createTemplate(var3);
                var4.setTotalTemplateName(var5);
            }

        }
    }
}
