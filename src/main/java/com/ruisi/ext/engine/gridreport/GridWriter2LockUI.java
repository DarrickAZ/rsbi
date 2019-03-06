//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.gridreport;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.util.DaoUtils;
import com.ruisi.ext.engine.util.LabelUtils;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.builder.PageBuilder$JSObject;
import com.ruisi.ext.engine.view.context.MVContext;
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

public class GridWriter2LockUI implements GridWriter {
    private GridReportContext a;
    private ExtWriter b;
    private ExtEnvirContext c;
    private InputOption d;
    private WriterService e;
    private ExtRequest f;
    private boolean g;

    public GridWriter2LockUI(GridReportContext var1, ExtWriter var2, ExtEnvirContext var3, InputOption var4) {
        this.a = var1;
        this.b = var2;
        this.c = var3;
        this.d = var4;
        this.e = new WriterService(var1);
        this.f = var4.getRequest();
        this.g = "true".equalsIgnoreCase((String)this.f.getAttribute("ext_fromAjax"));
    }

    @Override
    public void begin() {
        String var1 = this.a.getWidth();
        if (!this.g) {
            this.b.print("<div id=\"" + this.a.getId() + "\" class=\"lock-dg\" style=\"" + (var1 != null && var1.length() > 0 ? "width:" + var1 + "px;" : "") + "\">");
        }

        this.b.print("<div class=\"lock-dg-header\">");
        this.b.println("<table class=\"lockgrid " + this.a.getStyle() + "\" id=\"head-" + this.a.getId() + "\" cellpadding=\"0\" cellspacing=\"0\">");
    }

    public void writeDetail() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String var1 = this.a.getHeight();
        this.b.print("<div class=\"lock-dg-body\" style=\"" + (var1 != null && var1.length() > 0 ? "height:" + var1 + "px;" : "") + "\">");
        this.b.println("<table class=\"lockgrid " + this.a.getStyle() + "\" id=\"body-" + this.a.getId() + "\" cellpadding=\"0\" cellspacing=\"0\">");
        GridCell[][] var2 = this.a.getDetails();
        List var3 = this.a.loadOptions();

        for(int var4 = 0; var4 < var2.length; ++var4) {
            GridCell[] var5 = var2[var4];

            for(int var6 = 0; var6 < var3.size(); ++var6) {
                this.b.print("<tr class=\"tr-row" + (var6 % 2 == 0 ? "1" : "2") + "\">");

                for(int var7 = 0; var7 < var5.length; ++var7) {
                    GridCell var8 = var5[var7];
                    this.b.print("<td colSpan=\"" + var8.getColSpan() + "\" rowSpan=\"" + var8.getRowSpan() + "\"");
                    if (var8.getAlign() != null && var8.getAlign().length() > 0) {
                        this.b.print(" align=\"" + var8.getAlign() + "\"");
                    }

                    if (var8.getWidth() != null && var8.getWidth().length() > 0) {
                        this.b.print(" width=\"" + var8.getWidth() + "\" ");
                    }

                    if (var8.getStyleClass() != null && var8.getStyleClass().length() > 0) {
                        this.b.print(" class=\"lockgrid-td " + var8.getStyleClass() + "\" ");
                    } else {
                        this.b.print(" class=\"lockgrid-td\" ");
                    }

                    this.b.print(">");
                    this.b.print("<div class=\"dg-cell\" ");
                    if (var8.getWidth() != null && var8.getWidth().length() > 0) {
                        this.b.print("style=\"width:" + var8.getWidth() + "px;\"");
                    }

                    this.b.print(">");
                    Map var9 = (Map)var3.get(var6);
                    String var11;
                    if (var8.getLink() != null) {
                        GridCellLink var10 = var8.getLink();
                        var11 = null;
                        Map var12 = this.d.getParams();
                        StringBuffer var13 = new StringBuffer();
                        if (var12 != null) {
                            Iterator var15 = var12.entrySet().iterator();

                            while(var15.hasNext()) {
                                Entry var14 = (Entry)var15.next();
                                String var16 = (String)var14.getKey();
                                if (!var16.equals(var10.getByAlias())) {
                                    Object var17 = var14.getValue();
                                    if (var17 instanceof String) {
                                        var13.append(var16);
                                        var13.append("=");
                                        var13.append(var17);
                                        var13.append("&");
                                    }
                                }
                            }

                            var11 = var13.toString();
                        }

                        String[] var23 = new String[var10.getTarget().length];
                        String[] var26 = new String[var10.getTarget().length];
                        MVContext var28 = RuleUtils.findCurMV(this.c);

                        for(int var29 = 0; var29 < var10.getTarget().length; ++var29) {
                            Object var18 = null;
                            try {
                                var18 = LabelUtils.findObjectByLabel(var28, var10.getTarget()[var29], var10.getType()[var29]);
                            } catch (ExtConfigException e1) {
                                e1.printStackTrace();
                            }
                            if (var18 != null) {
                                var23[var29] = RuleUtils.getResPath(this.f) + "control/extControl?serviceid=$0&" + "t_from_id" + "=" + var28.getMvid() + "&id=$1";
                                String var19 = (String)PropertyUtils.getProperty(var18, "id");
                                try {
                                    var23[var29] = ConstantsEngine.replace(var23[var29], LabelUtils.findServiceIdByType(var10.getType()[var29]), var19);
                                } catch (ExtConfigException e1) {
                                    e1.printStackTrace();
                                }
                                var26[var29] = var19;
                            }
                        }

                        this.b.print("<a href=\"javascript:;\" onclick=\"chartComp_Link('" + var10.getByAlias() + "','" + var9.get(var10.getByAlias()) + "'," + ChartUtils.array2string(var23) + ",'" + var11 + "'," + ChartUtils.array2string(var26) + "," + ChartUtils.array2string(var10.getType()) + ");\">");
                    }

                    Object var20 = var9.get(var8.getAlias());
                    var11 = var8.getJsFunc();
                    if (var11 != null && var11.length() > 0) {
                        PageBuilder$JSObject var21 = (PageBuilder$JSObject)this.f.getAttribute("ext.script.engine");
                        Object var22 = var21.getScope().get(var11, var21.getScope());
                        if (var22 == null || !(var22 instanceof Function)) {
                            String var25 = ConstantsEngine.replace("定义的 jsFunc 方法 $0 未找到.", var11);
                            throw new ExtRuntimeException(var25);
                        }

                        Function var24 = (Function)var22;
                        Object[] var27 = new Object[]{var20, var9, var8.getAlias()};
                        var24.call(var21.getCt(), var21.getScope(), var21.getScope(), var27);
                    } else if (var20 != null) {
                        if (var8.getFormatPattern() != null && var8.getFormatPattern().length() > 0) {
                            this.b.print(this.e.format(var20, var8.getFormatPattern()));
                        } else {
                            this.b.print(var20.toString());
                        }
                    }

                    if (var8.getLink() != null) {
                        this.b.print("</a>");
                    }

                    this.b.print("</div>");
                    this.b.print("</td>");
                }

                this.b.print("</tr>");
            }
        }

    }

    @Override
    public void writeFooter() {
        GridCell[][] var1 = this.a.getFooters();
        if (var1 != null && var1.length != 0) {
            for(int var2 = 0; var2 < var1.length; ++var2) {
                GridCell[] var3 = var1[var2];
                this.b.print("<tr>");

                for(int var4 = 0; var4 < var3.length; ++var4) {
                    GridCell var5 = var3[var4];
                    this.b.print("<td colSpan=\"" + var5.getColSpan() + "\" rowSpan=\"" + var5.getRowSpan() + "\"");
                    if (var5.getAlign() != null && var5.getAlign().length() > 0) {
                        this.b.print(" align=\"" + var5.getAlign() + "\"");
                    }

                    if (var5.getWidth() != null && var5.getWidth().length() > 0) {
                        this.b.print(" width=\"" + var5.getWidth() + "\" ");
                    }

                    if (var5.getStyleClass() != null && var5.getStyleClass().length() > 0) {
                        this.b.print(" class=\"grid3-foot " + var5.getStyleClass() + "\" ");
                    } else {
                        this.b.print(" class=\"grid3-foot\" ");
                    }

                    this.b.print(">");
                    this.b.print(var5.getDesc());
                    if (var5.getDynamicText() != null && var5.getDynamicText()) {
                        Object var6 = this.e.findDynamicTextValue(var5.getAlias());
                        this.b.print(this.e.format(var6, var5.getFormatPattern()));
                    }

                    this.b.print("</td>");
                }

                this.b.print("</tr>");
            }

        }
    }

    @Override
    public void writeHeader() {
        this.b.println("<thead>");
        GridCell[][] var1 = this.a.getHeaders();

        for(int var2 = 0; var2 < var1.length; ++var2) {
            GridCell[] var3 = var1[var2];
            this.b.print("<tr>");

            for(int var4 = 0; var4 < var3.length; ++var4) {
                GridCell var5 = var3[var4];
                this.b.print("<th colSpan=\"" + var5.getColSpan() + "\" rowSpan=\"" + var5.getRowSpan() + "\"");
                if (var5.getAlign() != null && var5.getAlign().length() > 0) {
                    this.b.print(" align=\"" + var5.getAlign() + "\"");
                }

                if (var5.getWidth() != null && var5.getWidth().length() > 0) {
                    this.b.print(" width=\"" + var5.getWidth() + "\" ");
                }

                if (var5.getStyleClass() != null && var5.getStyleClass().length() > 0) {
                    this.b.print(" class=\"grid3-td " + var5.getStyleClass() + "\" ");
                } else {
                    this.b.print(" class=\"grid3-td\" ");
                }

                this.b.print(">");
                this.b.print("<div class=\"dg-cell\" ");
                if (var5.getWidth() != null && var5.getWidth().length() > 0) {
                    this.b.print("style=\"width:" + var5.getWidth() + "px;\"");
                }

                this.b.print(">");
                this.b.print(var5.getDesc());
                if (var5.getDynamicText() != null && var5.getDynamicText()) {
                    Object var6 = this.e.findDynamicTextValue(var5.getAlias());
                    this.b.print(this.e.format(var6, var5.getFormatPattern()));
                }

                this.b.print("</div>");
                this.b.print("</th>");
            }

            this.b.print("</tr>");
        }

        this.b.println("</thead>");
        this.b.print("</table>");
        this.b.println("</div>");
    }

    @Override
    public void end() throws UnsupportedEncodingException {
        this.b.print("</table>");
        this.b.println("</div>");
        if (this.a.getPageInfo() != null) {
            this.b();
        }

        if (!this.g) {
            this.b.print("</div>");
        }

        this.b.println("<script language=\"javascript\">");
        this.b.println("$(function(){");
        this.b.println("tableBodyscroll(\"" + this.a.getId() + "\");");
        this.b.println("})");
        this.b.println("</script>");
        this.a();
    }

    private void a() {
        if (this.a.getDetails() != null && this.a.getDetails().length != 0 && this.a.loadOptions().size() != 0) {
            if (this.a.getHeaders() != null && this.a.getHeaders().length != 0) {
                this.b.println("<script>");
                this.b.println("jQuery(document).ready(function(){");
                this.b.print("new SortableTable(document.getElementById('head-" + this.a.getId() + "'),document.getElementById('body-" + this.a.getId() + "'), [");
                GridCell[] var1 = this.a.getHeaders()[this.a.getHeaders().length - 1];

                for(int var2 = 0; var2 < var1.length; ++var2) {
                    GridCell var3 = var1[var2];
                    if (var3.isOrder()) {
                        this.b.print("'" + this.e.getDataType(var2) + "'");
                    } else {
                        this.b.print("'None'");
                    }

                    if (var2 != var1.length - 1) {
                        this.b.print(",");
                    }
                }

                this.b.print("], false);");
                this.b.println("});");
                this.b.println("</script>");
            }
        }
    }

    private void b() throws UnsupportedEncodingException {
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

        this.b.print("<div class=\"pagesizeinfo pg-" + this.a.getStyle() + "\">");
        DaoUtils.printPageInfoByAjax(this.a.getPageInfo(), this.b, this.a.getId(), var2.toString(), this.c);
        this.b.print("</div>");
    }
}
