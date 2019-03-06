//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.grid;

import com.ruisi.ext.engine.view.context.grid.ColConfigContext;
import com.ruisi.ext.engine.view.context.grid.DataGridContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.view.test.TestUtils;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ColConfigRule extends Rule {
    public ColConfigRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) throws ExtConfigException {
        DataGridContext var4 = (DataGridContext)this.digester.peek();
        ColConfigContext var5 = new ColConfigContext(var4);
        var4.setColConfigContext(var5);
        String var6 = var3.getValue("test");
        if (var6 != null && var6.length() > 0) {
            var5.setTestAdapter(TestUtils.createTest(var6));
        }

        this.digester.push(var5);
    }

    @Override
    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
