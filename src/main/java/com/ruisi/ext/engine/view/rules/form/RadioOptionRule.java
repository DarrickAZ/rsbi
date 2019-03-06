//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.form;

import com.ruisi.ext.engine.view.context.form.RadioContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class RadioOptionRule extends Rule {
    private Map a = null;

    public RadioOptionRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("value");
        String var5 = var3.getValue("ref");
        this.a = new HashMap();
        this.a.put("value", var4);
        this.a.put("ref", var5);
        RadioContext var6 = (RadioContext)this.digester.peek();
        Object var7 = var6.loadOptions();
        if (var7 == null) {
            var7 = new ArrayList();
            var6.setOptions((List)var7);
        }

        ((List)var7).add(this.a);
    }

    public void body(String var1, String var2, String var3) {
        this.a.put("text", var3);
    }

    public void end(String var1, String var2) {
    }
}
