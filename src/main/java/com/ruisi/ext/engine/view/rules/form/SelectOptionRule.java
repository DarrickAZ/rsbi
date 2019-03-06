//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.form;

import com.ruisi.ext.engine.view.context.form.SelectContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class SelectOptionRule extends Rule {
    private Map a = null;

    public SelectOptionRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("value");
        this.a = new HashMap();
        this.a.put("value", var4);
        SelectContext var5 = (SelectContext)this.digester.peek();
        Object var6 = var5.loadOptions();
        if (var6 == null) {
            var6 = new ArrayList();
            var5.setOptions((List)var6);
        }

        ((List)var6).add(this.a);
    }

    public void body(String var1, String var2, String var3) {
        this.a.put("text", var3);
    }

    public void end(String var1, String var2) {
    }
}
