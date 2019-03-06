//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.cross;

import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ExtDsRule extends Rule {
    private String a = "";

    public ExtDsRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        this.a = var3.getValue("id");
    }

    public void body(String var1, String var2, String var3) {
        if (var3 != null && var3.length() != 0) {
            CrossReportContext var4 = (CrossReportContext)this.digester.peek();
            if (var4.getRef() == null || var4.getRef().length() == 0) {
                String var5 = TemplateManager.getInstance().createTemplate(var3);
                Object var6 = var4.getExtSqlTemplates();
                if (var6 == null) {
                    HashMap var7 = new HashMap();
                    var4.setExtSqlTemplates(var7);
                    var6 = var7;
                }

                ((Map)var6).put(this.a, var5);
            }

        }
    }
}
