//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.grid;

import com.ruisi.ext.engine.view.context.dc.grid.AggreVO;
import java.util.List;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class AggreConfigRule extends Rule {
    public AggreConfigRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("type");
        String var5 = var3.getValue("name");
        String var6 = var3.getValue("expression");
        String var7 = var3.getValue("alias");
        AggreVO var8 = new AggreVO();
        var8.setType(var4);
        var8.setName(var5);
        var8.setAlias(var7);
        var8.setExpression("true".equals(var6));
        List var9 = (List)this.digester.peek();
        var9.add(var8);
    }

    public void end(String var1, String var2) {
    }
}
