//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.service.impl;

import com.itextpdf.text.DocumentException;
import com.ruisi.ext.engine.control.ActionProcess;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.control.InputOptionFactory;
import com.ruisi.ext.engine.init.ExtEnvirContextImpl;
import com.ruisi.ext.engine.service.ServiceSupport;
import com.ruisi.ext.engine.view.builder.BuilderManager;
import com.ruisi.ext.engine.view.context.MVContext;
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
import java.util.Map;

public class TabAjaxService extends ServiceSupport {
    public TabAjaxService() {
    }

    @Override
    public void execute(InputOption var1) throws ExtConfigException, IOException {
        var1.getResponse().setContentType("text/html;charset=UTF-8");
        ExtRequest var2 = var1.getRequest();
        String var3 = var2.getParameter("mvid");
        MVContext var4 = ActionProcess.findMVContextById(var3, false);
        Map var5 = var4.getMvParams();
        if (var5 != null) {
            InputOption var6 = InputOptionFactory.createInputOption(var2, var1.getResponse(), var5);
            var1.getParams().putAll(var6.getParams());
        }

        var2.setAttribute("ext.view.fromId", var3);
        var2.setAttribute("ext_rebuild", "y");
        ExtEnvirContextImpl var9 = new ExtEnvirContextImpl(var1);
        ExtWriterImpl var7 = new ExtWriterImpl(var1.getResponse().getWriter());
        HTMLEmitter var8 = new HTMLEmitter();
        var8.initialize(var7, var1.getRequest(), var1.getResponse(), var9, var1, this.servletContext);
        try {
            BuilderManager.buildContext(var4, var2, var7, this.daoHelper, var9, var1.getResponse(), var1, this.servletContext);
        } catch (AuthException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
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
