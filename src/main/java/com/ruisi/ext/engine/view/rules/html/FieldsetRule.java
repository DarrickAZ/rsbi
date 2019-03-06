//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.html;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.html.FieldsetContextImpl;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class FieldsetRule extends Rule {
    public FieldsetRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("title");
        FieldsetContextImpl var5 = new FieldsetContextImpl();
        var5.setTitle(var4);
        Element var6 = (Element)this.digester.peek();
        if (var6.getChildren() == null) {
            var6.setChildren(new ArrayList());
        }

        var6.getChildren().add(var5);
        var5.setParent(var6);
        this.digester.push(var5);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
