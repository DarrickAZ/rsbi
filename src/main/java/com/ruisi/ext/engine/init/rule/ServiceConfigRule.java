//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.init.rule;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.init.config.ResultConfig;
import com.ruisi.ext.engine.init.config.ServiceConfig;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import org.apache.commons.digester.Rule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;

public class ServiceConfigRule extends Rule {
    private static Log a = LogFactory.getLog(ServiceConfigRule.class);

    public ServiceConfigRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) throws ExtConfigException {
        String var4 = var3.getValue("id");
        String var5 = var3.getValue("class");
        String var6 = var3.getValue("result");

        try {
            Class var7 = Class.forName(var5);
            ServiceConfig var11 = new ServiceConfig();
            ExtContext.getInstance().putServiceConfig(var4, var11);
            var11.setServiceId(var4);
            var11.setService(var7);
            if (var6 != null && var6.length() > 0) {
                ResultConfig var9 = new ResultConfig();
                var9.setMethod("execute");
                var9.setMvid(var6);
                var11.putResultConfig("execute", var9);
            }

            this.digester.push(var11);
        } catch (Exception var10) {
            String var8 = ConstantsEngine.replace("配置的service: $0 不存在.", var5);
            a.error(var8, var10);
            throw new ExtConfigException(var8, var10);
        }
    }

    @Override
    public void end(String var1, String var2) {
        this.digester.pop();
    }
}
