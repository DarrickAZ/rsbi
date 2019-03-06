//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dc.cube;

import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContextImpl;
import java.util.HashMap;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class DataCenterRule extends Rule {
    public DataCenterRule() {
    }

    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("id");
        DataCenterContextImpl var5 = new DataCenterContextImpl();
        var5.setId(var4);
        Element var6 = (Element)this.digester.peek();
        this.digester.push(var5);
        MVContext var7 = RuleUtils.findMVContext(var6);
        if (var7.getCubeDataCenters() == null) {
            var7.setCubeDataCenters(new HashMap());
        }

        var7.getCubeDataCenters().put(var4, var5);
    }

    public void body(String var1, String var2, String var3) {
        if (var3 != null && var3.length() > 0) {
            DataCenterContext var4 = (DataCenterContext)this.digester.peek();
            var4.setScript(var3);
        }

    }

    public void end(String var1, String var2) {
        Object var3 = this.digester.pop();
    }
}
