//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.grid;

import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class SqlRule extends Rule {
    private String a;

    public SqlRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) throws ExtConfigException {
        String var4 = var3.getValue("id");
        Element var5 = (Element)this.digester.peek();
        if (!(var5 instanceof MVContext)) {
            throw new ExtConfigException("标签 sql 必须配置在xml文件最上级.");
        } else {
            MVContext var6 = (MVContext)var5;
            Map var7 = var6.getSqls();
            if (var7 == null) {
                HashMap var8 = new HashMap();
                var6.setSqls(var8);
            }

            this.a = var4;
        }
    }

    public void body(String var1, String var2, String var3) {
        if (var3 != null && var3.length() != 0) {
            String var4 = TemplateManager.getInstance().createTemplate(var3);
            Element var5 = (Element)this.digester.peek();
            MVContext var6 = (MVContext)var5;
            var6.getSqls().put(this.a, var4);
        }
    }

    public void end(String var1, String var2) {
    }
}
