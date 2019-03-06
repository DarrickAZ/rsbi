//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.html;

import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.html.BoxContextImpl;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class BoxRule extends Rule {
    public BoxRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        BoxContextImpl var4 = new BoxContextImpl();
        String var5 = var3.getValue("title");
        String var6 = var3.getValue("closeIcon");
        String var7 = var3.getValue("state");
        var4.setTitle(var5);
        var4.setCloseIcon(var6);
        var4.setState(var7);
        String var8 = "b" + IdCreater.create();
        var4.setId(var8);
        Element var9 = (Element)this.digester.peek();
        if (var9.getChildren() == null) {
            var9.setChildren(new ArrayList());
        }

        var9.getChildren().add(var4);
        var4.setParent(var9);
        this.digester.push(var4);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
