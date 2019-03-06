//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules;

import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContextImpl;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class MVRule extends Rule {
    public MVRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) throws ExtConfigException {
        MVContextImpl var4 = new MVContextImpl();
        String var5 = var3.getValue("id");
        var4.setMvid(var5);
        String var6 = "f" + IdCreater.create();
        var4.setFormId(var6);
        this.digester.push(var4);
        ExtContext.getInstance().putMVContext(var4);
    }

    @Override
    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
