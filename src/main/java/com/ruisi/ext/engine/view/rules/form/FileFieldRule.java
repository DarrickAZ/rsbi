//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.form;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.form.FileFieldContextImpl;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.util.ArrayList;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class FileFieldRule extends Rule {
    public FileFieldRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) throws ExtConfigException {
        String var4 = var3.getValue("id");
        String var5 = var3.getValue("desc");
        String var6 = var3.getValue("require");
        String var7 = var3.getValue("size");
        if (var4 != null && var4.length() != 0) {
            FileFieldContextImpl var11 = new FileFieldContextImpl();
            var11.setDesc(var5);
            var11.setId(var4);
            var11.setRequire("true".equalsIgnoreCase(var6));
            var11.setSize(var7);
            Element var9 = (Element)this.digester.peek();
            if (var9.getChildren() == null) {
                var9.setChildren(new ArrayList());
            }

            var9.getChildren().add(var11);
            var11.setParent(var9);
            MVContext var10 = RuleUtils.findMVContext(var9);
            var10.setMvParam(var4, var11);
            var10.setShowForm(true);
            var10.setUpload(true);
        } else {
            String var8 = ConstantsEngine.replace(" $0 输入框未配置id.", "fileField");
            throw new ExtConfigException(var8);
        }
    }

    @Override
    public void end(String var1, String var2) {
    }
}
