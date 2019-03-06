//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.html.table;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.html.table.TableContextImpl;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class TableRule extends Rule {
    public TableRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        TableContextImpl var4 = new TableContextImpl();
        String var5 = var3.getValue("class");
        String var6 = var3.getValue("style");
        String var7 = var3.getValue("align");
        var4.setStyleClass(var5);
        var4.setStyle(var6);
        var4.setAlign(var7);
        Element var8 = (Element)this.digester.peek();
        if (var8.getChildren() == null) {
            var8.setChildren(new ArrayList());
        }

        var8.getChildren().add(var4);
        var4.setParent(var8);
        this.digester.push(var4);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
