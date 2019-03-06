//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.init.rule;

import com.ruisi.ext.engine.init.config.ResultConfig;
import com.ruisi.ext.engine.init.config.ServiceConfig;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ResultConfigRule extends Rule {
    private ResultConfig a = null;

    public ResultConfigRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("method");
        String var5 = var3.getValue("inter-ref");
        this.a = new ResultConfig();
        this.a.setMethod(var4);
        if (var5 != null && var5.length() > 0) {
            this.a.setInteRefs(var5.split(","));
        }

    }

    public void body(String var1, String var2, String var3) {
        this.a.setMvid(var3);
        ServiceConfig var4 = (ServiceConfig)this.digester.peek();
        var4.putResultConfig(this.a.getMethod(), this.a);
    }

    public void end(String var1, String var2) {
    }
}
