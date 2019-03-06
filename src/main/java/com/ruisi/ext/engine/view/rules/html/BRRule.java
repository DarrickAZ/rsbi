//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.html;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.html.BRContextImpl;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class BRRule extends Rule {
    public BRRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        BRContextImpl var4 = new BRContextImpl();
        Element var5 = (Element)this.digester.peek();
        if (var5.getChildren() == null) {
            var5.setChildren(new ArrayList());
        }

        var5.getChildren().add(var4);
        var4.setParent(var5);
    }

    public void end(String var1, String var2) {
    }
}
