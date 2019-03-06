//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.grid;

import com.itextpdf.text.DocumentException;
import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.util.DaoUtils;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.dc.GridDataCenterBuilder;
import com.ruisi.ext.engine.view.builder.dsource.DataSourceBuilder;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisi.ext.engine.view.context.dsource.DataSourceContext;
import com.ruisi.ext.engine.view.context.grid.PageInfo;
import com.ruisi.ext.engine.view.context.gridreport.GridReportContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import jxl.write.WriteException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class GridReportBuilder extends AbstractBuilder {
    private GridReportContext a;

    public GridReportBuilder(GridReportContext var1) {
        this.a = var1;
    }

    @Override
    protected void processEnd() {
    }

    @Override
    protected void processStart() throws InvocationTargetException, NoSuchMethodException, IOException, IllegalAccessException, ExtConfigException, ScriptEnginerException, DocumentException, WriteException {
        GridReportContext var1 = (GridReportContext)this.a.clone();
        String var2 = this.a.getRefDataCenter();
        if (var2 != null && var2.length() > 0) {
            List var12 = this.a(var1);
            var1.setOptions(var12);
            this.emitter.startGridReport(var1);
        } else {
            String var3 = (String)this.request.getAttribute("ext.isExport");
            if (var3 == null) {
                var3 = "";
            }

            List var4 = null;
            String var5 = TemplateManager.buildTempldate(this.a.getTemplateName(), this.request, this.veloContext);
            PageInfo var6 = var1.getPageInfo();
            if (var6 != null && !"true".equals(var3)) {
                PageInfo var13 = new PageInfo();
                var13.setPagesize(var6.getPagesize());
                String var14 = this.request.getParameter("currPage");
                if (var14 != null && var14.length() != 0) {
                    var13.setCurtpage(Long.parseLong(var14));
                } else if (var6.getCurtpage() != 0L) {
                    var13.setCurtpage(var6.getCurtpage());
                }

                if (this.a.getRefDsource() != null && this.a.getRefDsource().length() != 0) {
                    DataSourceBuilder var15 = new DataSourceBuilder();
                    MVContext var10 = RuleUtils.findCurMV(this.veloContext);
                    DataSourceContext var11 = var15.findDataSource(this.a.getRefDsource(), var10);
                    var4 = (new DataSourceBuilder()).queryForList(var5, var11, var13, this.request);
                } else {
                    var4 = DaoUtils.calPage(var5, var13, this.daoHelper);
                    this.request.setAttribute("ext.view.pageInfo", var13);
                }

                var1.setPageInfo(var13);
            } else if (this.a.getRefDsource() != null && this.a.getRefDsource().length() != 0) {
                DataSourceBuilder var7 = new DataSourceBuilder();
                MVContext var8 = RuleUtils.findCurMV(this.veloContext);
                DataSourceContext var9 = var7.findDataSource(this.a.getRefDsource(), var8);
                var4 = (new DataSourceBuilder()).queryForList(var5, var9);
            } else {
                var4 = this.daoHelper.queryForList(var5);
            }

            var1.setOptions(var4);
            this.emitter.startGridReport(var1);
        }
    }

    private List a(GridReportContext var1) throws ExtConfigException, ScriptEnginerException {
        MVContext var2 = RuleUtils.findCurMV(this.veloContext);
        GridDataCenterContext var3 = (GridDataCenterContext)var2.getGridDataCenters().get(this.a.getRefDataCenter());
        GridDataCenterBuilder var4 = new GridDataCenterBuilder(var3, this.request, this.veloContext, this.daoHelper);
        if (this.a.getPageInfo() == null) {
            List var9 = var4.buildByXML((PageInfo)null);
            Map var10 = var4.getExtDatas();
            var1.setExtDatas(var10);
            return var9;
        } else {
            PageInfo var5 = new PageInfo();
            var5.setPagesize(this.a.getPageInfo().getPagesize());
            String var6 = this.request.getParameter("currPage");
            if (var6 != null && var6.length() != 0) {
                var5.setCurtpage(Long.parseLong(var6));
            } else {
                var5.setCurtpage(var1.getPageInfo().getCurtpage());
            }

            List var7 = var4.buildByXML(var5);
            var1.setPageInfo(var5);
            Map var8 = var4.getExtDatas();
            var1.setExtDatas(var8);
            return var7;
        }
    }
}
