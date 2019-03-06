//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dsource;

import com.ruisi.ext.engine.view.context.dsource.DataSourceContext;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class PropertyRule extends Rule {
    private String a = null;
    private String b = null;

    public PropertyRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        this.a = var3.getValue("name");
    }

    public void body(String var1, String var2, String var3) {
        this.b = var3;
    }

    public void end(String var1, String var2) {
        DataSourceContext var3 = (DataSourceContext)this.digester.peek();
        var3.putProperty(this.a, this.b);
    }
}
