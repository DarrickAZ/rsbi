//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.html;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.html.DivContextImpl;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class DivRule extends Rule {
    public DivRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("id");
        String var5 = var3.getValue("styleClass");
        String var6 = var3.getValue("style");
        String var7 = var3.getValue("align");
        DivContextImpl var8 = new DivContextImpl();
        var8.setId(var4);
        var8.setStyleClass(var5);
        var8.setStyle(var6);
        var8.setAlign(var7);
        Element var9 = (Element)this.digester.peek();
        if (var9.getChildren() == null) {
            var9.setChildren(new ArrayList());
        }

        var9.getChildren().add(var8);
        var8.setParent(var9);
        this.digester.push(var8);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
