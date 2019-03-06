//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.init.rule;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.init.config.InterceptorConfig;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import org.apache.commons.digester.Rule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;

public class InterceptorRule extends Rule {
    private static Log a = LogFactory.getLog(InterceptorRule.class);

    public InterceptorRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) throws ExtConfigException {
        String var4 = var3.getValue("class");
        String var5 = var3.getValue("id");
        InterceptorConfig var6 = new InterceptorConfig();
        var6.setId(var5);
        var6.setClazz(var4);

        try {
            Class.forName(var4);
        } catch (Exception var9) {
            String var8 = ConstantsEngine.replace("配置的拦截器: $0 不存在.", var4);
            a.error(var8, var9);
            throw new ExtConfigException(var8, var9);
        }

        ExtContext.getInstance().putInterceptor(var5, var6);
    }

    @Override
    public void end(String var1, String var2) {
    }
}
