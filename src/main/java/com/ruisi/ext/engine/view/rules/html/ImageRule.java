//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.html;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.html.ImageContextImpl;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ImageRule extends Rule {
    public ImageRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) {
        ImageContextImpl var4 = new ImageContextImpl();
        String var5 = var3.getValue("url");
        var4.setUrl(var5);
        String var6 = var3.getValue("align");
        var4.setAlign(var6);
        String var7 = var3.getValue("width");
        String var8 = var3.getValue("height");
        if (var7 != null && var7.length() > 0) {
            var4.setWidth(new Integer(var7));
        }

        if (var8 != null && var8.length() > 0) {
            var4.setHeight(new Integer(var8));
        }

        String var9 = var3.getValue("type");
        String var10 = var3.getValue("path");
        var4.setType(var9);
        var4.setPath(var10);
        Element var11 = (Element)this.digester.peek();
        var11.getChildren().add(var4);
        var4.setParent(var11);
    }

    @Override
    public void end(String var1, String var2) throws Exception {
        super.end(var1, var2);
    }
}
