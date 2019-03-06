//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.init.rule;

import com.ruisi.ext.engine.init.config.PageConfig;
import java.util.List;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class PageConfigRule extends Rule {
    private List a;

    public PageConfigRule(List var1) {
        this.a = var1;
    }

    public void begin(String var1, String var2, Attributes var3) {
        PageConfig var4 = new PageConfig();
        String var5 = var3.getValue("resource");
        var4.setResource(var5);
        this.a.add(var4);
    }

    public void end(String var1, String var2) {
    }
}
