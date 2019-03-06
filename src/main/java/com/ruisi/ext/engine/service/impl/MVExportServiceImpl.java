//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.service.impl;

import com.itextpdf.text.DocumentException;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.control.InputOptionFactory;
import com.ruisi.ext.engine.init.ExtEnvirContextImpl;
import com.ruisi.ext.engine.service.ServiceSupport;
import com.ruisi.ext.engine.view.builder.BuilderManager;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.emitter.excel.ExcelEmitter;
import com.ruisi.ext.engine.view.exception.AuthException;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.wrapper.ByteArrayWriterImpl;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import jxl.write.WriteException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.servlet.ServletException;

public class MVExportServiceImpl extends ServiceSupport {
    private static Log a = LogFactory.getLog(MVExportServiceImpl.class);

    public MVExportServiceImpl() {
    }

    @Override
    public void execute(InputOption var1) {
        ExtRequest var2 = var1.getRequest();
        String var3 = var2.getParameter("t_from_id");
        MVContext var4 = null;
        try {
            var4 = ExtContext.getInstance().getMVContext(var3, true);
        } catch (ExtConfigException e) {
            e.printStackTrace();
        }
        Map var5 = var4.getMvParams();
        InputOption var6 = InputOptionFactory.createInputOption(var2, var1.getResponse(), var5);
        var2.setAttribute("ext.isExport", "true");
        if (a.isDebugEnabled()) {
            a.debug("option 对象已经替换、params = " + var6.getParams());
        }

        ExtResponse var7 = var1.getResponse();
        var7.setContentType("application/x-msdownload");
        String var8 = "attachment; filename=\"report.xls\"";
        var7.setHeader("Content-Disposition", var8);
        ByteArrayOutputStream var9 = new ByteArrayOutputStream();
        ByteArrayWriterImpl var10 = new ByteArrayWriterImpl(var9);
        ExtEnvirContextImpl var11 = new ExtEnvirContextImpl(var6);
        ExcelEmitter var12 = new ExcelEmitter();
        var12.initialize(var10, var1.getRequest(), var1.getResponse(), var11, var6, this.servletContext);
        try {
            BuilderManager.buildContextByEmitter(var4, var2, var11, var1.getResponse(), this.daoHelper, var12, var6, this.servletContext);
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
