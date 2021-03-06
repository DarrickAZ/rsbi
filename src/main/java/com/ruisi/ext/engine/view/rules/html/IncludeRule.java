//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.html;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.html.IncludeContextImpl;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class IncludeRule extends Rule {
    public IncludeRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        IncludeContextImpl var4 = new IncludeContextImpl();
        var4.setPage(var3.getValue("page"));
        var4.setMvid(var3.getValue("mvid"));
        Element var5 = (Element)this.digester.peek();
        var4.setParent(var5);
        if (var5.getChildren() == null) {
            var5.setChildren(new ArrayList());
        }

        var5.getChildren().add(var4);
    }

    @Override
    public void end(String var1, String var2) throws Exception {
        super.end(var1, var2);
    }
}
