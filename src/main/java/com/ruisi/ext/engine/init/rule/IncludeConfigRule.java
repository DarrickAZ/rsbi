//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.init.rule;

import java.util.List;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class IncludeConfigRule extends Rule {
    private List a;

    public IncludeConfigRule(List var1) {
        this.a = var1;
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("file");
        this.a.add(var4);
    }

    public void end(String var1, String var2) {
    }
}
