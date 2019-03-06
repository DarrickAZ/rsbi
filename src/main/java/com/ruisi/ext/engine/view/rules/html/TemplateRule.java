//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.html;

import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.view.context.html.TextContext;
import org.apache.commons.digester.Rule;

public class TemplateRule extends Rule {
    public TemplateRule() {
    }

    public void body(String var1, String var2, String var3) {
        if (var3 != null && var3.length() != 0) {
            TextContext var4 = (TextContext)this.digester.peek();
            String var5 = TemplateManager.getInstance().createTemplate(var3);
            var4.setTemplateName(var5);
        }
    }
}
