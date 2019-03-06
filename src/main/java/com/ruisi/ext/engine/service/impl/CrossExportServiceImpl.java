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
import com.ruisi.ext.engine.util.LabelUtils;
import com.ruisi.ext.engine.view.builder.BuilderManager;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.emitter.excel.ExcelEmitter;
import com.ruisi.ext.engine.view.exception.AuthException;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.wrapper.ByteArrayWriterImpl;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtResponse;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import jxl.write.WriteException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.servlet.ServletException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class CrossExportServiceImpl extends ServiceSupport {
    public CrossExportServiceImpl() {
    }

    @Override
    public void execute(InputOption var1) {
        ExtResponse var2 = var1.getResponse();
        ExtRequest var3 = var1.getRequest();
        String var4 = var3.getParameter("t_from_id");
        MVContext var5 = null;
        try {
            var5 = ExtContext.getInstance().getMVContext(var4, false);
        } catch (ExtConfigException e) {
            e.printStackTrace();
        }
        String var6 = var3.getParameter("reportId");
        CrossReportContext var7 = null;
        var7 = (CrossReportContext)var5.getCrossReports().get(var6);
        if (var7 == null) {
            var7 = LabelUtils.findCrossBylabel(var5, var6);
        }

        Map var8 = var5.getMvParams();
        InputOption var9 = InputOptionFactory.createInputOption(var3, var1.getResponse(), var8);
        var3.setAttribute("ext.isExport", "true");
        ByteArrayOutputStream var10 = new ByteArrayOutputStream();
        ByteArrayWriterImpl var11 = new ByteArrayWriterImpl(var10);
        ExtEnvirContextImpl var12 = new ExtEnvirContextImpl(var9);
        ExcelEmitter var13 = new ExcelEmitter();
        var13.initialize(var11, var1.getRequest(), var1.getResponse(), var12, var9, this.servletContext);
        try {
            BuilderManager.processAllowBuilder(var5, var7, var3, var13, this.daoHelper, var12, var2, var9, this.servletContext);
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
        super.setNoResult(var3);
    }
}
