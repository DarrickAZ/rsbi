//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.html.table;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.html.table.TdContextImpl;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class TdRule extends Rule {
    public TdRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("width");
        String var5 = var3.getValue("height");
        String var6 = var3.getValue("style");
        String var7 = var3.getValue("styleClass");
        String var8 = var3.getValue("colspan");
        String var9 = var3.getValue("rowspan");
        TdContextImpl var10 = new TdContextImpl();
        var10.setWidth(var4);
        var10.setHeight(var5);
        var10.setStyle(var6);
        var10.setColspan(var8);
        var10.setStyleClass(var7);
        var10.setRowspan(var9);
        Element var11 = (Element)this.digester.peek();
        if (var11.getChildren() == null) {
            var11.setChildren(new ArrayList());
        }

        var11.getChildren().add(var10);
        var10.setParent(var11);
        this.digester.push(var10);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
