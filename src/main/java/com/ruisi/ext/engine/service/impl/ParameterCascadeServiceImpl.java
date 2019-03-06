//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.service.impl;

import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.init.ExtEnvirContextImpl;
import com.ruisi.ext.engine.service.ServiceSupport;
import com.ruisi.ext.engine.view.builder.BuilderManager;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.form.InputField;
import com.ruisi.ext.engine.view.emitter.html.HTMLEmitter;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.wrapper.ByteArrayWriterImpl;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class ParameterCascadeServiceImpl extends ServiceSupport {
    public ParameterCascadeServiceImpl() {
    }

    @Override
    public void execute(InputOption var1) throws IOException, ExtConfigException {
        var1.getResponse().setContentType("text/html;charset=UTF-8");
        String var2 = var1.getRequest().getParameter("t_from_id");
        String var3 = var1.getRequest().getParameter("cascade");
        Map var4 = ExtContext.getInstance().getMVContext(var2).getMvParams();
        InputField var5 = (InputField)var4.get(var3);
        ByteArrayOutputStream var6 = new ByteArrayOutputStream();
        ByteArrayWriterImpl var7 = new ByteArrayWriterImpl(var6);
        HTMLEmitter var8 = new HTMLEmitter();
        ExtEnvirContextImpl var9 = new ExtEnvirContextImpl(var1);
        var8.initialize(var7, var1.getRequest(), var1.getResponse(), var9, var1, this.servletContext);
        BuilderManager.rebuild(var5, var1.getRequest(), var8, this.daoHelper, var9, var1.getResponse(), var1, this.servletContext);
        String var10 = new String(var6.toByteArray());
        var1.getResponse().getWriter().print(var10);
        var6.close();
        super.setNoResult(var1.getRequest());
    }
}
