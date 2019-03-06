//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.html;

import com.ruisi.ext.engine.view.context.html.CustomContext;
import com.ruisi.ext.engine.view.context.html.CustomContextImpl;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class CustomRule extends Rule {
    public CustomRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        CustomContextImpl var4 = new CustomContextImpl();
        this.digester.push(var4);
    }

    public void body(String var1, String var2, String var3) {
        CustomContext var4 = (CustomContext)this.digester.peek();
        var4.setJson(var3);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
