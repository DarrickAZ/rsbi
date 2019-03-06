//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.tab;

import com.ruisi.ext.engine.view.context.tab.TabContextImpl;
import com.ruisi.ext.engine.view.context.tab.TabViewContext;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class TabRule extends Rule {
    public TabRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        TabContextImpl var4 = new TabContextImpl();
        String var5 = var3.getValue("title");
        String var6 = var3.getValue("active");
        String var7 = var3.getValue("ref");
        String var8 = var3.getValue("value");
        String var9 = var3.getValue("action");
        String var10 = var3.getValue("method");
        var4.setTitle(var5);
        var4.setActive("true".equalsIgnoreCase(var6));
        var4.setRef(var7);
        var4.setValue(var8);
        var4.setAction(var9);
        var4.setMethod(var10);
        TabViewContext var11 = (TabViewContext)this.digester.peek();
        if (var11.getChildren() == null) {
            var11.setChildren(new ArrayList());
        }

        var11.getChildren().add(var4);
        var4.setParent(var11);
        this.digester.push(var4);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
