//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.dsource;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.dsource.DataSourceContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.util.HashMap;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class DataSourceRule extends Rule {
    public DataSourceRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) {
        DataSourceContext var4 = new DataSourceContext();
        this.digester.push(var4);
    }

    @Override
    public void end(String var1, String var2) throws ExtConfigException {
        DataSourceContext var3 = (DataSourceContext)this.digester.pop();
        MVContext var4 = RuleUtils.findMVContext((Element)this.digester.peek());
        if (var4.getDsources() == null) {
            var4.setDsources(new HashMap());
        }

        if (var4.getDsources().containsKey(var3.getId())) {
            String var5 = ConstantsEngine.replace("配置的dataSource id = $0 存在重复.", var3.getId());
            throw new ExtConfigException(var5);
        } else {
            var4.getDsources().put(var3.getId(), var3);
        }
    }
}
