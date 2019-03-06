//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.html;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.html.TextContext;
import com.ruisi.ext.engine.view.context.html.TextContextImpl;
import com.ruisi.ext.engine.view.context.html.TextProperty;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class TextRule extends Rule {
    public TextRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        TextContextImpl var4 = new TextContextImpl();
        String var5 = var3.getValue("size");
        String var6 = var3.getValue("align");
        String var7 = var3.getValue("height");
        String var8 = var3.getValue("weight");
        String var9 = var3.getValue("color");
        String var10 = var3.getValue("id");
        String var11 = var3.getValue("formatHtml");
        String var12 = var3.getValue("formatEnter");
        String var13 = var3.getValue("class");
        TextProperty var14 = new TextProperty();
        var14.setSize(var5);
        var14.setAlign(var6);
        var14.setHeight(var7);
        var14.setWeight(var8);
        var14.setColor(var9);
        var14.setId(var10);
        var14.setStyleClass(var13);
        if (var11 != null && var11.length() > 0) {
            var4.setFormatHtml("true".equalsIgnoreCase(var11));
        }

        if (var12 != null && var12.length() > 0) {
            var4.setFormatEnter("true".equalsIgnoreCase(var12));
        }

        if (!var14.isEmpty()) {
            var4.setTextProperty(var14);
        }

        Element var15 = (Element)this.digester.peek();
        if (var15.getChildren() == null) {
            var15.setChildren(new ArrayList());
        }

        var15.getChildren().add(var4);
        var4.setParent(var15);
        this.digester.push(var4);
    }

    public void body(String var1, String var2, String var3) {
        TextContext var4 = (TextContext)this.digester.peek();
        var4.setText(var3);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
