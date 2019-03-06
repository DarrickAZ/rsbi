//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.init.rule;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class ConstantConfigRule extends Rule {
    public ConstantConfigRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) throws ExtConfigException {
        String var4 = var3.getValue("name");
        String var5 = var3.getValue("value");
        if (ExtContext.getInstance().getConstant(var4) != null) {
            String var6 = ConstantsEngine.replace("constant 配置存在重复， name = $0 . ", var4);
            throw new ExtConfigException(var6);
        } else {
            ExtContext.getInstance().putConstants(var4, var5);
        }
    }

    public void end(String var1, String var2) {
    }
}
