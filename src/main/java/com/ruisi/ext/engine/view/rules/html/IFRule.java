//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.html;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.html.IFContextImpl;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.view.test.TestUtils;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class IFRule extends Rule {
    public IFRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) throws ExtConfigException {
        IFContextImpl var4 = new IFContextImpl();
        String var5 = var3.getValue("test");
        String var6 = var3.getValue("jsFunc");
        if (var5 != null && var5.length() > 0) {
            var4.setTestAdapter(TestUtils.createTest(var5));
        }

        var4.setJsFunc(var6);
        Element var7 = (Element)this.digester.peek();
        if (var7.getChildren() == null) {
            var7.setChildren(new ArrayList());
        }

        var7.getChildren().add(var4);
        var4.setParent(var7);
        this.digester.push(var4);
    }

    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
