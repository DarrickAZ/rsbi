//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.html;

import com.ruisi.ext.engine.view.context.html.KpiContextImpl;
import com.ruisi.ext.engine.view.context.html.KpiDescContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class KpiRule extends Rule {
    public KpiRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("key");
        KpiContextImpl var5 = new KpiContextImpl();
        var5.setKey(var4);
        KpiDescContext var6 = (KpiDescContext)this.digester.peek();
        var6.getKpis().add(var5);
    }

    public void end(String var1, String var2) {
    }
}
