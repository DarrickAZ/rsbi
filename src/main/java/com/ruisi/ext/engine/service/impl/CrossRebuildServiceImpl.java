//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.service.impl;

import com.itextpdf.text.DocumentException;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.init.ExtEnvirContextImpl;
import com.ruisi.ext.engine.service.ServiceSupport;
import com.ruisi.ext.engine.view.builder.BuilderManager;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.emitter.html.HTMLEmitter;
import com.ruisi.ext.engine.view.exception.AuthException;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtWriterImpl;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import jxl.write.WriteException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.servlet.ServletException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class CrossRebuildServiceImpl extends ServiceSupport {
    public CrossRebuildServiceImpl() {
    }

    @Override
    public void execute(InputOption var1) {
        var1.getResponse().setContentType("text/html;charset=UTF-8");
        ExtRequest var2 = var1.getRequest();
        String var3 = var2.getParameter("t_from_id");
        var2.setAttribute("ext_rebuild", "y");
        String var4 = var2.getParameter("id");
        MVContext var5 = null;
        try {
            var5 = ExtContext.getInstance().getMVContext(var3);
        } catch (ExtConfigException e) {
            e.printStackTrace();
        }
        CrossReportContext var6 = (CrossReportContext)var5.getCrossReports().get(var4);
        ExtWriterImpl var7 = null;
        try {
            var7 = new ExtWriterImpl(var1.getResponse().getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExtEnvirContextImpl var8 = new ExtEnvirContextImpl(var1);
        HTMLEmitter var9 = new HTMLEmitter();
        var9.initialize(var7, var1.getRequest(), var1.getResponse(), var8, var1, this.servletContext);
        try {
            BuilderManager.processAllowBuilder(var5, var6, var2, var9, this.daoHelper, var8, var1.getResponse(), var1, this.servletContext);
        } catch (AuthException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ExtConfigException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (ScriptEnginerException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        super.setNoResult(var2);
    }
}
