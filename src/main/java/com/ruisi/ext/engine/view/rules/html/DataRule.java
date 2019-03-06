//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.html;

import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.html.DataContext;
import com.ruisi.ext.engine.view.context.html.DataContextImpl;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class DataRule extends Rule {
    public DataRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) throws ExtConfigException {
        String var4 = var3.getValue("key");
        String var5 = var3.getValue("multi");
        String var6 = var3.getValue("refDataCenter");
        String var7 = var3.getValue("refDsource");
        String var8 = var3.getValue("outKey");
        String var9 = var3.getValue("outVal");
        if (var4 != null && var4.length() != 0) {
            DataContextImpl var10 = new DataContextImpl();
            var10.setKey(var4);
            var10.setMulti("true".equalsIgnoreCase(var5));
            var10.setRefDataCenter(var6);
            var10.setRefDsource(var7);
            if (var8 != null && var8.length() > 0) {
                var10.setOutKey(var8.split(","));
            }

            if (var9 != null && var9.length() > 0) {
                var10.setOutVal(var9.split(","));
            }

            Element var11 = (Element)this.digester.peek();
            if (var11.getChildren() == null) {
                var11.setChildren(new ArrayList());
            }

            var11.getChildren().add(var10);
            var10.setParent(var11);
            this.digester.push(var10);
        } else {
            throw new ExtConfigException("data 标签的 key 必须配置.");
        }
    }

    @Override
    public void end(String var1, String var2) {
        this.digester.pop();
    }

    @Override
    public void body(String var1, String var2, String var3) {
        if (var3 != null && var3.length() != 0) {
            DataContext var4 = (DataContext)this.digester.peek();
            String var5 = TemplateManager.getInstance().createTemplate(var3);
            var4.setTemplateName(var5);
        }
    }
}
