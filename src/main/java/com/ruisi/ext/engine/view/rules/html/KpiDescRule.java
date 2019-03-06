//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.html;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.html.KpiDescContextImpl;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class KpiDescRule extends Rule {
    public KpiDescRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        KpiDescContextImpl var4 = new KpiDescContextImpl();
        String var5 = var3.getValue("impl");
        var4.setImpl(var5);
        Element var6 = (Element)this.digester.peek();
        if (var6.getChildren() == null) {
            var6.setChildren(new ArrayList());
        }

        var6.getChildren().add(var4);
        var4.setParent(var6);
        this.digester.push(var4);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
