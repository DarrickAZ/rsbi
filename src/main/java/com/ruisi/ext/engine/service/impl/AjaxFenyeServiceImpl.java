//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.service.impl;

import com.itextpdf.text.DocumentException;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.init.ExtEnvirContextImpl;
import com.ruisi.ext.engine.service.ServiceSupport;
import com.ruisi.ext.engine.util.LabelUtils;
import com.ruisi.ext.engine.view.builder.BuilderManager;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.ExtContext;
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

public class AjaxFenyeServiceImpl extends ServiceSupport {
    public AjaxFenyeServiceImpl() {
    }

    @Override
    public void execute(InputOption var1) throws ExtConfigException, IOException {
        var1.getResponse().setContentType("text/html;charset=UTF-8");
        ExtRequest var2 = var1.getRequest();
        String var3 = var2.getParameter("id");
        var1.getRequest().setAttribute("ext_fromAjax", "true");
        String var4 = var2.getParameter("t_from_id");
        MVContext var5 = ExtContext.getInstance().getMVContext(var4);
        Object var6 = null;
        if (var5.getDataGrids() != null) {
            var6 = (Element)var5.getDataGrids().get(var3);
        }

        if (var6 == null) {
            var6 = LabelUtils.findDataGridBylabel(var5, var3);
        }

        if (var6 == null && var5.getCrossReports() != null) {
            var6 = (Element)var5.getCrossReports().get(var3);
        }

        if (var6 == null) {
            var6 = LabelUtils.findCrossBylabel(var5, var3);
        }

        if (var6 == null && var5.getGridReports() != null) {
            var6 = (Element)var5.getGridReports().get(var3);
        }

        if (var6 == null) {
            var6 = LabelUtils.findGridReportBylabel(var5, var3);
        }

        ExtWriterImpl var7 = new ExtWriterImpl(var1.getResponse().getWriter());
        ExtEnvirContextImpl var8 = new ExtEnvirContextImpl(var1);
        HTMLEmitter var9 = new HTMLEmitter();
        var9.initialize(var7, var1.getRequest(), var1.getResponse(), var8, var1, this.servletContext);
        try {
            BuilderManager.processAllowBuilder(var5, (Element)var6, var2, var9, this.daoHelper, var8, var1.getResponse(), var1, this.servletContext);
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
        super.setNoResult(var1.getRequest());
    }
}
