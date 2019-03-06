//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.grid;

import com.ruisi.ext.engine.view.context.grid.ColConfigContext;
import com.ruisi.ext.engine.view.context.grid.ColsContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ColsRule extends Rule {
    public ColsRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("width");
        String var5 = var3.getValue("alias");
        String var6 = var3.getValue("desc");
        String var7 = var3.getValue("dataRef");
        String var8 = var3.getValue("class");
        String var9 = var3.getValue("align");
        ColsContext var10 = new ColsContext();
        var10.setWidth(var4);
        var10.setAllAlias(var5);
        var10.setAllDescs(var6);
        var10.setDataRef(var7);
        var10.setAlign(var9);
        var10.setStyleClass(var8);
        ColConfigContext var11 = (ColConfigContext)this.digester.peek();
        var11.setColsContext(var10);
    }

    @Override
    public void end(String var1, String var2) throws Exception {
        super.end(var1, var2);
    }
}
