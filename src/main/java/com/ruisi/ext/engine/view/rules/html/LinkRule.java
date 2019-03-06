//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.html;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.html.LinkContextImpl;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class LinkRule extends Rule {
    public LinkRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("action");
        String var5 = var3.getValue("method");
        String var6 = var3.getValue("title");
        String var7 = var3.getValue("onClick");
        String var8 = var3.getValue("styleClass");
        String var9 = var3.getValue("allowParam");
        String var10 = var3.getValue("windowState");
        String var11 = var3.getValue("otherParam");
        String var12 = var3.getValue("src");
        LinkContextImpl var13 = new LinkContextImpl();
        var13.setAction(var4);
        var13.setMethod(var5);
        var13.setText(var6);
        var13.setOnClick(var7);
        var13.setWindowState(var10);
        var13.setStyleClass(var8);
        var13.setOtherParam(var11);
        var13.setAllowParam("true".equalsIgnoreCase(var9));
        var13.setSrc(var12);
        Element var14 = (Element)this.digester.peek();
        var13.setParent(var14);
        if (var14.getChildren() == null) {
            var14.setChildren(new ArrayList());
        }

        var14.getChildren().add(var13);
        this.digester.push(var13);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
