//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.html;

import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ScriptRule extends Rule {
    public ScriptRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) {
    }

    @Override
    public void body(String var1, String var2, String var3) throws ExtConfigException {
        if (var3 != null && var3.length() > 0) {
            Object var4 = this.digester.peek();
            if (!(var4 instanceof MVContext)) {
                throw new ExtConfigException(" script 标签必须配置在xml文件的最顶级.");
            }

            MVContext var5 = (MVContext)var4;
            var5.setScripts(var3);
        }

    }

    @Override
    public void end(String var1, String var2) {
    }
}
