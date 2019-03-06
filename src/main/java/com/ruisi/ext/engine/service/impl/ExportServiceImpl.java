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
import com.ruisi.ext.engine.view.context.grid.DataGridContext;
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

public class ExportServiceImpl extends ServiceSupport {
    public ExportServiceImpl() {
    }

    @Override
    public void execute(InputOption var1) throws ExtConfigException {
        ExtRequest var2 = var1.getRequest();
        ExtResponse var3 = var1.getResponse();
        String var4 = var2.getParameter("t_from_id");
        MVContext var5 = ExtContext.getInstance().getMVContext(var4);
        String var6 = var2.getParameter("dgexport");
        var2.setAttribute("ext.isExport", "true");
        if (var2.getMethod().equalsIgnoreCase("GET")) {
            var1 = InputOptionFactory.createInputOption(var2, var3, var5.getMvParams());
        }

        var3.setContentType("application/x-msdownload");
        String var7 = "attachment; filename=\"export.xls\"";
        var3.setHeader("Content-Disposition", var7);
        DataGridContext var8 = LabelUtils.findDataGridBylabel(var5, var6);
        if (var8 == null) {
            var8 = (DataGridContext)var5.getDataGrids().get(var6);
        }

        ExtEnvirContextImpl var9 = new ExtEnvirContextImpl(var1);
        ByteArrayOutputStream var10 = new ByteArrayOutputStream();
        ByteArrayWriterImpl var11 = new ByteArrayWriterImpl(var10);
        ExcelEmitter var12 = new ExcelEmitter();
        var12.initialize(var11, var2, var3, var9, var1, this.servletContext);
        try {
            BuilderManager.processAllowBuilder(var5, var8, var2, var12, this.daoHelper, var9, var1.getResponse(), var1, this.servletContext);
        } catch (AuthException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
