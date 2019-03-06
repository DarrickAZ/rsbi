//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules;

import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.MVContextImpl;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import org.apache.commons.digester.Digester;

public class MVAutoRule {
    private Digester a;
    private String b;

    public MVAutoRule(Digester var1) {
        this.a = var1;
    }

    public MVContext start() {
        MVContextImpl var1 = new MVContextImpl();
        var1.setMvid(this.b);
        String var2 = "f" + IdCreater.create();
        var1.setFormId(var2);
        this.a.push(var1);
        try {
            ExtContext.getInstance().putMVContext(var1);
        } catch (ExtConfigException e) {
            e.printStackTrace();
        }
        return var1;
    }

    public void end() {
        this.a.pop();
    }

    public String getMvid() {
        return this.b;
    }

    public void setMvid(String var1) {
        this.b = var1;
    }
}
