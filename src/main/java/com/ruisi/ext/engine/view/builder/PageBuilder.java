//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder;

import com.itextpdf.text.DocumentException;
import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import java.io.IOException;

public class PageBuilder extends AbstractBuilder {
    private Element a;
    private Context b;
    private BuilderInterceptor c;

    public PageBuilder(Element var1) throws InstantiationException, IllegalAccessException, ExtConfigException {
        this.a = var1;
        String var2 = ExtContext.getInstance().getConstant("buildInter");
        if (var2 != null && var2.length() > 0) {
            try {
                this.c = (BuilderInterceptor)Class.forName(var2).newInstance();
            } catch (InstantiationException var5) {
                throw var5;
            } catch (IllegalAccessException var6) {
                throw var6;
            } catch (ClassNotFoundException var7) {
                String var4 = ConstantsEngine.replace("builder 拦截器类 $0 不存在.", var2);
                throw new ExtConfigException(var4, var7);
            }
        }

    }

    @Override
    protected void processEnd() throws IOException {
        this.emitter.end(this.a);
        if (this.c != null) {
            this.c.end(this.a, this.request, this.daoHelper);
        }

    }

    @Override
    protected void processStart() throws DocumentException, IOException {
        if (this.c != null) {
            this.c.start(this.a, this.request, this.daoHelper);
        }

        this.emitter.start(this.a);
        if (this.a instanceof MVContext) {
            this.veloContext.put("mvInfo", this.a);
            this.request.setAttribute("mvInfo", this.a);
            MVContext var1 = (MVContext)this.a;
            String var2 = var1.getScripts();
            if (var2 != null && var2.length() > 0) {
                this.b = Context.enter();
                ScriptableObject var3 = this.b.initStandardObjects();
                Object var4 = Context.javaToJS(this.emitter.getWriter(), var3);
                ScriptableObject.putProperty(var3, "out", var4);
                Object var5 = Context.javaToJS(this.request, var3);
                ScriptableObject.putProperty(var3, "request", var5);
                Object var6 = Context.javaToJS(this.veloContext, var3);
                ScriptableObject.putProperty(var3, "extContext", var6);
                this.b.evaluateString(var3, var2, (String)null, 1, (Object)null);
                PageBuilder$JSObject var7 = new PageBuilder$JSObject(this.b, var3);
                this.request.setAttribute("ext.script.engine", var7);
            }
        }

    }

    public Context getCt() {
        return this.b;
    }
}
