//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.rules.html;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.html.SubmitCheckContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

public class SubmitCheckRule extends Rule {
    public SubmitCheckRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) {
    }

    @Override
    public void body(String var1, String var2, String var3) throws ExtConfigException {
        Element var4 = (Element)this.digester.peek();
        if (!(var4 instanceof MVContext)) {
            throw new ExtConfigException("标签 submitCheck 必须配置在xml文件最上级.");
        } else {
            MVContext var5 = (MVContext)var4;
            SubmitCheckContext var6 = new SubmitCheckContext();
            var6.setText(var3);
            var5.setSubmitCheck(var6);
        }
    }

    @Override
    public void end(String var1, String var2) {
    }
}
