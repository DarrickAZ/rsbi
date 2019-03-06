//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.gridreport;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.util.LabelUtils;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.builder.PageBuilder$JSObject;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.grid.PageInfo;
import com.ruisi.ext.engine.view.context.gridreport.GridCell;
import com.ruisi.ext.engine.view.context.gridreport.GridCellLink;
import com.ruisi.ext.engine.view.context.gridreport.GridReportContext;
import com.ruisi.ext.engine.view.emitter.chart.ChartUtils;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import org.apache.commons.beanutils.PropertyUtils;
import org.mozilla.javascript.Function;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GridWriter2Query implements GridWriter {
    private GridReportContext a;
    private ExtWriter b;
    private ExtEnvirContext c;
    private InputOption d;
    private WriterService e;
    private ExtRequest f;

    public GridWriter2Query(GridReportContext var1, ExtWriter var2, ExtEnvirContext var3, InputOption var4) {
        this.a = var1;
        this.b = var2;
        this.c = var3;
        this.d = var4;
        this.e = new WriterService(var1);
        this.f = var4.getRequest();
    }

    @Override
    public void begin() {
        this.b.println("<table class=\"d_table2\" style=\"width:100%;\" >");
    }

    public void writeDetail() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, ExtConfigException {
        GridCell[][] var1 = this.a.getDetails();
        List var2 = this.a.loadOptions();
        if (var2.size() != 0) {
            this.b.print("<tr>");
            this.b.print("<td>");
            this.b.print("<div id=\"d_kpi\" style=\"width:auto;height:auto;\">");
            this.b.print("<table class=\"grid5\" cellspacing=\"0\" cellpadding=\"0\">");

            for(int var3 = 0; var3 < var1.length; ++var3) {
                GridCell[] var4 = var1[var3];

                for(int var5 = 0; var5 < var2.size(); ++var5) {
                    Map var6 = (Map)var2.get(var5);
                    this.b.print("<tr>");

                    for(int var7 = 0; var7 < var4.length; ++var7) {
                        GridCell var8 = var4[var7];
                        this.b.print("<td colSpan=\"" + var8.getColSpan() + "\" rowSpan=\"" + var8.getRowSpan() + "\"");
                        if (var8.getAlign() != null && var8.getAlign().length() > 0) {
                            this.b.print(" align=\"" + var8.getAlign() + "\"");
                        }

                        this.b.print(" class=\"grid5-td " + (var5 % 2 == 0 ? "kpiData1" : "kpiData2") + "\" ");
                        this.b.print(">");
                        this.b.print("<span class=\"kpiValue\">");
                        String var10;
                        if (var8.getLink() != null) {
                            GridCellLink var9 = var8.getLink();
                            var10 = null;
                            Map var11 = this.d.getParams();
                            StringBuffer var12 = new StringBuffer();
                            if (var11 != null) {
                                Iterator var14 = var11.entrySet().iterator();

                                while(var14.hasNext()) {
                                    Entry var13 = (Entry)var14.next();
                                    String var15 = (String)var13.getKey();
                                    if (!var15.equals(var9.getByAlias())) {
                                        Object var16 = var13.getValue();
                                        if (var16 instanceof String) {
                                            var12.append(var15);
                                            var12.append("=");
                                            var12.append(var16);
                                            var12.append("&");
                                        }
                                    }
                                }

                                var10 = var12.toString();
                            }

                            String[] var22 = new String[var9.getTarget().length];
                            String[] var25 = new String[var9.getTarget().length];
                            MVContext var27 = RuleUtils.findCurMV(this.c);

                            for(int var28 = 0; var28 < var9.getTarget().length; ++var28) {
                                Object var17 = LabelUtils.findObjectByLabel(var27, var9.getTarget()[var28], var9.getType()[var28]);
                                if (var17 != null) {
                                    var22[var28] = RuleUtils.getResPath(this.f) + "control/extControl?serviceid=$0&" + "t_from_id" + "=" + var27.getMvid() + "&id=$1";
                                    String var18 = (String)PropertyUtils.getProperty(var17, "id");
                                    var22[var28] = ConstantsEngine.replace(var22[var28], LabelUtils.findServiceIdByType(var9.getType()[var28]), var18);
                                    var25[var28] = var18;
                                }
                            }

                            this.b.print("<a href=\"javascript:;\" onclick=\"chartComp_Link('" + var9.getByAlias() + "','" + var6.get(var9.getByAlias()) + "'," + ChartUtils.array2string(var22) + ",'" + var10 + "'," + ChartUtils.array2string(var25) + "," + ChartUtils.array2string(var9.getType()) + ");\">");
                        }

                        Object var19 = var6.get(var8.getAlias());
                        var10 = var8.getJsFunc();
                        if (var10 != null && var10.length() > 0) {
                            PageBuilder$JSObject var20 = (PageBuilder$JSObject)this.f.getAttribute("ext.script.engine");
                            Object var21 = var20.getScope().get(var10, var20.getScope());
                            if (var21 == null || !(var21 instanceof Function)) {
                                String var24 = ConstantsEngine.replace("定义的 jsFunc 方法 $0 未找到.", var10);
                                throw new ExtRuntimeException(var24);
                            }

                            Function var23 = (Function)var21;
                            Object[] var26 = new Object[]{var19, var6, var8.getAlias()};
                            var23.call(var20.getCt(), var20.getScope(), var20.getScope(), var26);
                        } else if (var19 != null) {
                            if (var8.getFormatPattern() != null && var8.getFormatPattern().length() > 0) {
                                this.b.print(this.e.format(var19, var8.getFormatPattern()));
                            } else {
                                this.b.print(var19.toString());
                            }
                        }

                        if (var8.getLink() != null) {
                            this.b.print("</a>");
                        }

                        this.b.print("</span>");
                        this.b.print("</td>");
                    }

                    this.b.print("</tr>");
                }
            }

            this.b.print("</table>");
            this.b.print("</div>");
            this.b.print("</td>");
            this.b.print("</tr>");
        }
    }

    @Override
    public void writeFooter() {
    }

    @Override
    public void writeHeader() {
        this.b.print("<tr>");
        this.b.print("<td>");
        this.b.print("<div id=\"d_colDims2\"><div style='padding:3px;color:#999999;'>(将字段拖入表头查询)</div>");
        this.b.print("<table class=\"grid5\" cellspacing=\"0\" cellpadding=\"0\">");
        GridCell[][] var1 = this.a.getHeaders();

        for(int var2 = 0; var2 < var1.length; ++var2) {
            GridCell[] var3 = var1[var2];
            this.b.print("<tr class=\"scrollColThead\">");

            for(int var4 = 0; var4 < var3.length; ++var4) {
                GridCell var5 = var3[var4];
                this.b.print("<th colSpan=\"" + var5.getColSpan() + "\" rowSpan=\"" + var5.getRowSpan() + "\"");
                if (var5.getAlign() != null && var5.getAlign().length() > 0) {
                    this.b.print(" align=\"" + var5.getAlign() + "\"");
                }

                if (var5.getWidth() != null && var5.getWidth().length() > 0) {
                    this.b.print(" width=\"" + var5.getWidth() + "\" ");
                }

                this.b.print(">");
                this.b.print("<span class=\"colkpi\">");
                this.b.print("<span class=\"kpiname\">");
                this.b.print(var5.getDesc());
                if (var5.getDynamicText() != null && var5.getDynamicText()) {
                    Object var6 = this.e.findDynamicTextValue(var5.getAlias());
                    this.b.print(this.e.format(var6, var5.getFormatPattern()));
                }

                this.b.print("</span>");
                this.b.print("<a onclick=\"optTableCol(this,'" + var5.getAlias() + "');\" href=\"javascript:;\" class=\"dimoptbtn set\" style=\"opacity: 0.6;\"> &nbsp; </a>");
                this.b.print("</span>");
                this.b.print("</th>");
            }

            this.b.print("</tr>");
        }

        this.b.print("</table>");
        this.b.print("</div>");
        this.b.print("</td>");
        this.b.print("</tr>");
    }

    @Override
    public void end() throws UnsupportedEncodingException {
        this.b.print("</table>");
        if (this.a.getPageInfo() != null) {
            this.a();
        }

    }

    private void a() throws UnsupportedEncodingException {
        Map var1 = this.d.getParams();
        StringBuffer var2 = new StringBuffer();
        Iterator var4 = var1.entrySet().iterator();

        while(var4.hasNext()) {
            Entry var3 = (Entry)var4.next();
            String var5 = (String)var3.getKey();
            Object var6 = var3.getValue();
            if (var6 instanceof String) {
                var2.append(var5);
                var2.append("=");
                var2.append(URLEncoder.encode((String)var6, "UTF-8"));
                var2.append("&");
            }
        }

        PageInfo var7 = this.a.getPageInfo();
        this.b.print("<div id=\"tablefy\" style=\"background:#efefef;border:1px solid #ccc;margin:3px 0px 0px 0px; width:100%;border-radius:0px;\"></div>");
        this.b.print("<script>$(function(){$('#tablefy').pagination({total:" + var7.getAllsize() + ",pageNumber:" + (var7.getCurtpage() + 1L) + ",pageSize:" + var7.getPagesize() + ",onSelectPage:function(" + "pageNumber, pageSize){" + "tablePagination(pageNumber, pageSize, '" + this.a.getId() + "', '" + var2 + "');" + "}});" + "});</script>");
    }
}
