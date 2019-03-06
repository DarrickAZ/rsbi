//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.html;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.PageBuilder$JSObject;
import com.ruisi.ext.engine.view.context.AbstractContext;
import com.ruisi.ext.engine.view.context.html.IFContext;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.view.test.TestAdapter;
import java.util.Map;
import org.mozilla.javascript.Function;

public class IFBuilder extends AbstractBuilder {
    private IFContext a;

    public IFBuilder(IFContext var1) {
        this.a = var1;
    }

    @Override
    protected void processEnd() {
    }

    @Override
    protected void processStart() {
        TestAdapter var1 = this.a.getTestAdapter();
        if (var1 != null && !var1.test((Map)null, this.veloContext, this.request)) {
            AbstractContext var2 = (AbstractContext)this.a;
            var2.setIsGoOn(new Boolean(false));
        }

        String var9 = this.a.getJsFunc();
        if (var9 != null && var9.length() > 0) {
            PageBuilder$JSObject var3 = (PageBuilder$JSObject)this.request.getAttribute("ext.script.engine");
            Object var4 = var3.getScope().get(var9, var3.getScope());
            if (var4 == null || !(var4 instanceof Function)) {
                String var10 = ConstantsEngine.replace("定义的 jsFunc 方法 $0 未找到.", var9);
                throw new ExtRuntimeException(var10);
            }

            Function var5 = (Function)var4;
            Object[] var6 = new Object[0];
            Boolean var7 = (Boolean)var5.call(var3.getCt(), var3.getScope(), var3.getScope(), var6);
            AbstractContext var8 = (AbstractContext)this.a;
            var8.setIsGoOn(var7);
        }

    }
}
