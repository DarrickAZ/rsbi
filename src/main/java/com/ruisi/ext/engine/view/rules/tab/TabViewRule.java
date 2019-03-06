//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.tab;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.tab.TabViewContextImpl;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class TabViewRule extends Rule {
    public TabViewRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("ajax");
        String var5 = var3.getValue("outParameterName");
        String var6 = var3.getValue("out");
        TabViewContextImpl var7 = new TabViewContextImpl();
        var7.setOutParameterName(var5);
        var7.setAjax("true".equalsIgnoreCase(var4));
        var7.setOut(var6);
        Element var8 = (Element)this.digester.peek();
        if (var8.getChildren() == null) {
            var8.setChildren(new ArrayList());
        }

        var8.getChildren().add(var7);
        var7.setParent(var8);
        this.digester.push(var7);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
