//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.init.rule;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.emitter.chart.ChartEmitter;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import org.apache.commons.digester.Rule;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;

public class ChartEmitterRule extends Rule {
    private Logger a = Logger.getLogger(ChartEmitterRule.class);

    public ChartEmitterRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) throws ExtConfigException {
        String var4 = var3.getValue("shape");
        String var5 = var3.getValue("class");
        if (ExtContext.getInstance().getChartEmitter(var4) != null) {
            String var11 = ConstantsEngine.replace("chart 配置存在重复， name = $0 . ", var4);
            throw new ExtConfigException(var11);
        } else {
            try {
                ChartEmitter var6 = (ChartEmitter)Class.forName(var5).newInstance();
                ExtContext.getInstance().putChartEmitter(var4, var6);
            } catch (InstantiationException var8) {
                var8.printStackTrace();
            } catch (IllegalAccessException var9) {
                var9.printStackTrace();
            } catch (ClassNotFoundException var10) {
                this.a.error("类找不到 " + var5, var10);
            }

        }
    }
}
