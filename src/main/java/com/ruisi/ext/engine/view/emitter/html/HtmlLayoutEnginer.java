//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.html;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.util.DaoUtils;
import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.builder.PageBuilder$JSObject;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.view.context.chart.ChartTitleContext;
import com.ruisi.ext.engine.view.context.face.MoreValue;
import com.ruisi.ext.engine.view.context.form.InputField;
import com.ruisi.ext.engine.view.context.form.MultiSelectContext;
import com.ruisi.ext.engine.view.context.form.RadioContext;
import com.ruisi.ext.engine.view.context.grid.*;
import com.ruisi.ext.engine.view.context.html.*;
import com.ruisi.ext.engine.view.emitter.chart.ShowJSCharts;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.view.test.TestAdapter;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import org.mozilla.javascript.Function;

import javax.servlet.ServletContext;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public class HtmlLayoutEnginer {
    private ExtWriter a;
    private ExtRequest b;
    private InputOption c;
    private ExtEnvirContext d;
    private ServletContext e;

    public HtmlLayoutEnginer(HTMLEmitter var1) {
        this.a = var1.getOut();
        this.b = var1.getRequest();
        this.c = var1.getOption();
        this.d = var1.getCtx();
        this.e = var1.getServletContext();
    }

    public void buildTableJson(DataGridContext var1) {
        this.a.println("<div id=\"" + var1.getId() + "\" align='left'></div>");
        this.a.println("<script>");
        this.a.println("Ext.onReady(function(){var " + var1.getId() + "cfg = {");
        this.a.println("id : '" + var1.getId() + "',");
        this.a.println("header: [");
        List var2 = var1.getColConfigContext().getColContexts();

        for(int var3 = 0; var3 < var2.size(); ++var3) {
            ColContext var4 = (ColContext)var2.get(var3);
            this.a.print("{id:'h" + var3 + "', header:'" + var4.getDesc() + "',");
            this.a.print(" width:" + (var4.getWidth() != null && var4.getWidth().length() != 0 ? var4.getWidth() : "120") + ",");
            if (var3 == 0) {
                this.a.print("locked:true,action:'drill',");
            }

            if (var4.getFormatPattern() != null && var4.getFormatPattern().length() != 0) {
                this.a.print("align: 'right', renderer : Ext.util.Format.extNumberFmt('h" + var3 + "', false), type:'number', ");
            } else {
                this.a.print("align: 'left',");
            }

            this.a.print(" sortable:true, hidden:false, dataIndex:'h" + var3 + "'}");
            if (var3 != var2.size() - 1) {
                this.a.print(",\n");
            }
        }

        this.a.println("],");
        this.a.println("changeDims: false,");
        this.a.println("fullView : true,");
        this.a.println("gridDims:[],");
        this.a.println("text : null,");
        this.a.println("dataUrl: '',");
        this.a.println("customize : [],");
        this.a.println("kpiPos : 0,");
        this.a.println("kpiCustomizeUrl:'',");
        this.a.print("fieldFmts:{");
        StringBuffer var10 = new StringBuffer();

        for(int var11 = 0; var11 < var2.size(); ++var11) {
            ColContext var5 = (ColContext)var2.get(var11);
            if (var5.getFormatPattern() != null && var5.getFormatPattern().length() > 0) {
                var10.append("'h" + var11 + "': '" + var5.getFormatPattern().replaceAll("#", "0") + "',");
            }
        }

        this.a.print(var10.substring(0, var10.length() - 1));
        this.a.println("},");
        this.a.println("jsonData: {");
        this.a.println("totalCount:" + var1.getPageInfo().getAllsize() + " ,");
        this.a.println("data:[");
        List var12 = var1.loadOptions();

        for(int var13 = 0; var13 < var12.size(); ++var13) {
            Map var6 = (Map)var12.get(var13);
            this.a.print("{");

            for(int var7 = 0; var7 < var2.size(); ++var7) {
                ColContext var8 = (ColContext)var2.get(var7);
                Object var9 = var6.get(var8.getAlias());
                if (var8.getFormatPattern() != null && var8.getFormatPattern().length() != 0) {
                    this.a.print("'h" + var7 + "':" + var9);
                } else {
                    this.a.print("'h" + var7 + "': '" + var9 + "'");
                }

                if (var7 != var2.size() - 1) {
                    this.a.print(",");
                }
            }

            this.a.print("}");
            if (var13 != var12.size() - 1) {
                this.a.print(",\n");
            }
        }

        this.a.println("]");
        this.a.println("}");
        this.a.println("};");
        this.a.println("new BoncDssExt.grid.Grid(" + var1.getId() + "cfg);");
        this.a.println("});");
        this.a.println("</script>");
    }

    public void buildTableStart(DataGridContext var1) {
        if (var1.getExport() != null && var1.getExport()) {
            StringBuffer var2 = new StringBuffer();
            Map var3 = this.c.getParams();
            Iterator var5 = var3.entrySet().iterator();

            while(var5.hasNext()) {
                Entry var4 = (Entry)var5.next();
                String var6 = (String)var4.getKey();
                Object var7 = var4.getValue();
                if (var7 instanceof String) {
                    var2.append(var6);
                    var2.append("=");
                    var2.append(var7);
                    var2.append("&");
                }
            }

            String var8 = RuleUtils.findCurMV(this.d).getMvid();
            this.a.print("<div align='right' class='expbtn'><a id=\"crsexport\" href='extControl?serviceid=ext.sys.export&t_from_id=" + var8 + "&" + var2 + "dgexport=" + var1.getId() + "'>导出</a></div>");
            this.a.print("<script>$(function(){$(\"#crsexport\").linkbutton({iconCls:'icon-export',plain:true})})</script>");
        }

        this.a.println("<div id=\"" + var1.getId() + "\">");
        this.a.println("<table class=\"grid3\" cellpadding=\"0\" cellspacing=\"0\">");
    }

    public void buildTabelAjax(DataGridContext var1) throws UnsupportedEncodingException {
        MVContext var2 = RuleUtils.findCurMV(this.d);
        String var3 = var1.getId();
        if (var1.getLabel() != null && var1.getLabel().length() > 0) {
            var3 = var1.getLabel();
        }

        this.a.print("<div id=\"" + var3 + "\">");
        this.a.println("</div>");
        Map var4 = this.c.getParams();
        StringBuffer var5 = new StringBuffer();
        Iterator var7 = var4.entrySet().iterator();

        while(var7.hasNext()) {
            Entry var6 = (Entry)var7.next();
            String var8 = (String)var6.getKey();
            Object var9 = var6.getValue();
            if (var9 instanceof String) {
                var5.append(var8);
                var5.append("=");
                var5.append(URLEncoder.encode((String)var9, "UTF-8"));
                var5.append("&");
            }
        }

        String var10 = "v" + var2.getFormId();
        String var11 = var2.getMvid();
        boolean var12 = var2.isShowForm();
        if (var1.isInit()) {
            this.a.print("<script>gotobyajax(" + (var12 ? var10 : null) + ",'" + var3 + "'," + var1.getPageInfo().getPagesize() + ",0,'" + var5 + "','" + var11 + "')</script>");
        } else if (this.b.getMethod().equalsIgnoreCase("POST")) {
            this.a.print("<script>gotobyajax(" + (var12 ? var10 : null) + ",'" + var3 + "'," + var1.getPageInfo().getPagesize() + ",0,'" + var5 + "','" + var11 + "')</script>");
        } else {
            this.a.print("<script>waitQuery('" + var3 + "');</script>");
        }

    }

    public void buildTableHead(ColConfigContext var1) {
        this.a.println("<thead>");
        this.a.print("<tr>");
        List var3 = var1.getColContexts();
        String var4 = RuleUtils.findCurMV(this.d).getMvid();
        String var5 = this.b.getParameter("ext_order_state");
        String var6 = this.b.getParameter("ext_col_order");

        String var9;
        for(Iterator var8 = var3.iterator(); var8.hasNext(); this.a.print("</th>")) {
            ColContext var7 = (ColContext)var8.next();
            var9 = var7.getWidth();
            this.a.print("<th class=\"" + (var7.getStyleClass() == null ? "" : var7.getStyleClass()) + "\" ");
            if (var9 != null && var9.length() > 0) {
                this.a.print("width=" + var9);
            }

            if (var7.isOrder()) {
                Map var10 = this.c.getParams();
                StringBuffer var11 = new StringBuffer();
                Iterator var13 = var10.entrySet().iterator();

                while(var13.hasNext()) {
                    Entry var12 = (Entry)var13.next();
                    String var14 = (String)var12.getKey();
                    Object var15 = var12.getValue();
                    if (var15 instanceof String) {
                        var11.append(var14);
                        var11.append("=");
                        var11.append(var15);
                        var11.append("&");
                    }
                }

                this.a.print(" title=\"点击排序\" onclick=\"extColOrder('" + var7.getDataGridContext().getId() + "','" + var7.getAlias() + "','" + var4 + "','" + var11 + "')\" style=\"cursor:pointer\"");
            }

            this.a.print(">");
            String var18 = var7.getType();
            if ("checkbox".equalsIgnoreCase(var18)) {
                this.a.print(var7.getDesc());
                this.a.print("<input type='checkbox' id='selall' onclick='selectAll(this,\"" + var7.getId() + "\")'>");
            } else if ("radio".equalsIgnoreCase(var18)) {
                this.a.print(var7.getDesc());
            } else {
                this.a.print(var7.getDesc());
            }

            if (var7.getAlias() != null && var7.getAlias().equals(var6)) {
                if ("a".equals(var5)) {
                    this.a.print("<img src='" + RuleUtils.getResPath(this.b) + "ext-res/image/sort_asc.gif'>");
                }

                if ("d".equals(var5)) {
                    this.a.print("<img src='" + RuleUtils.getResPath(this.b) + "ext-res/image/sort_desc.gif'>");
                }
            }
        }

        ColsContext var16 = var1.getColsContext();
        if (var16 != null) {
            String[] var17 = var1.getColsContext().getDescArray();
            if (var17 != null) {
                String[] var21 = var17;
                int var20 = var17.length;

                for(int var19 = 0; var19 < var20; ++var19) {
                    var9 = var21[var19];
                    this.a.print("<th class=\"" + (var16.getStyleClass() == null ? "" : var16.getStyleClass()) + "\"");
                    if (var16.getWidth() != null && var16.getWidth().length() > 0) {
                        this.a.print(" width='" + var16.getWidth() + "'");
                    }

                    this.a.print(">");
                    this.a.print(var9);
                    this.a.print("</th>");
                }
            }
        }

        this.a.println("</tr>");
        this.a.println("</thead>");
    }

    public void buildTableBody(List var1, DataGridContext var2, ColConfigContext var3) throws ExtConfigException {
        String var4 = (String)this.b.getAttribute("ext.view.mvid");
        String var5 = (String)this.b.getAttribute("ext.view.fromId");
        if (var1.size() == 0) {
            this.a.print("<tr><td align=\"center\" colspan=\"" + var3.getColContexts().size() + "\" class=\"grid3-td\">无数据。</td></tr>");
        } else {
            ArrayList var6 = new ArrayList();

            for(int var7 = 0; var7 < var1.size(); ++var7) {
                Map var8 = (Map)var1.get(var7);
                TestAdapter var9 = var3.getTestAdapter();
                if (var9 == null || var9.test(var8, this.d, this.b)) {
                    String var10 = var7 % 2 == 0 ? "tr-row1" : "tr-row2";
                    this.a.print("<tr ");
                    if (var2.getRightMenuFunc() != null && var2.getRightMenuFunc().length() > 0) {
                        this.a.print(" rid=\"" + var8.get("id") + "\" onclick=\"showtask(this, '" + var2.getTargetDiv() + "')\" oncontextmenu=\"" + var2.getRightMenuFunc() + "(event, this, '" + var2.getTargetDiv() + "')\"");
                    }

                    this.a.print(" class=\"" + var10 + "\">");

                    String var14;
                    for(int var11 = 0; var11 < var3.getColContexts().size(); ++var11) {
                        ColContext var12 = (ColContext)var3.getColContexts().get(var11);
                        Object var13 = var8.get(var12.getAlias());
                        var14 = var12.getAlign();
                        this.a.print("<td class=\"" + (var12.getStyleClass() == null ? "" : var12.getStyleClass()) + " grid3-td\" ");
                        if (var14 != null && var14.length() > 0) {
                            this.a.print(" align=\"" + var14 + "\"");
                        }

                        this.a.print(">");
                        TestAdapter var15 = var12.getTestAdapter();
                        if (var15 != null && !var15.test(var8, this.d, this.b)) {
                            this.a.print("</td>");
                        } else {
                            String var16 = var12.getAlt();
                            String var17;
                            if (var16 != null && var16.length() > 0) {
                                var17 = "a" + IdCreater.create();
                                var6.add(var17);
                                Object var18 = var8.get(var16);
                                if (var18 == null) {
                                    var18 = var16;
                                }

                                this.a.print("<p id=\"" + var17 + "\" text=\"" + var18 + "\">");
                            }

                            var17 = var12.getType();
                            if ("checkbox".equalsIgnoreCase(var17)) {
                                this.a.print("<div class=\"checkbox checkbox-info checkbox-inline\"><input id='" + var12.getId() + var7 + "' type='checkbox' name='" + var12.getId() + "' value='" + var13 + "'><label for=\"" + var12.getId() + var7 + "\"> </label></div>");
                            } else if ("radio".equalsIgnoreCase(var17)) {
                                this.a.print("<div class=\"radio radio-info radio-inline\"><input id='" + var12.getId() + var7 + "' type='radio' name='" + var12.getId() + "' value='" + var13 + "'><label for=\"" + var12.getId() + var7 + "\"> </label></div>");
                            } else if (var13 != null) {
                                if (var12.getLinkCtxs().size() > 0) {
                                    Iterator var19 = var12.getLinkCtxs().iterator();

                                    label238:
                                    while(true) {
                                        TestAdapter var20;
                                        ColLinkContext var36;
                                        do {
                                            if (!var19.hasNext()) {
                                                break label238;
                                            }

                                            var36 = (ColLinkContext)var19.next();
                                            var20 = var36.getTestAdapter();
                                        } while(var20 != null && !var20.test(var8, this.d, this.b));

                                        if (var36.isAjax()) {
                                            this.a.print("<a onclick=\"linkbycol('");
                                        } else {
                                            this.a.print("<a onclick=\"location.href='");
                                        }

                                        this.a.print("extControl?");
                                        this.a.print("serviceid=" + var36.getAction() + "&");
                                        this.a.print("methodId=" + (var36.getMethod() == null ? "" : var36.getMethod()) + "&");
                                        if ("POST".equalsIgnoreCase(this.b.getMethod())) {
                                            var4 = var5;
                                        }

                                        Map var21 = ExtContext.getInstance().getMVContext(var4).getMvParams();
                                        List var22 = var36.getParams();
                                        Map var23 = this.c.getParams();
                                        String var44;
                                        if (var23 != null) {
                                            Iterator var25 = var23.entrySet().iterator();

                                            label224:
                                            while(true) {
                                                while(true) {
                                                    String var26;
                                                    Object var27;
                                                    do {
                                                        do {
                                                            if (!var25.hasNext()) {
                                                                if (var36.getByAlias() != null) {
                                                                    String[] var47;
                                                                    int var46 = (var47 = var36.getByAlias()).length;

                                                                    for(int var45 = 0; var45 < var46; ++var45) {
                                                                        var44 = var47[var45];
                                                                        this.a.print(var44 + "=" + var8.get(var44));
                                                                        this.a.print("&");
                                                                    }
                                                                }

                                                                if (var36.getOtherParams() != null && var36.getOtherParams().length() > 0) {
                                                                    this.a.print(var36.getOtherParams());
                                                                }
                                                                break label224;
                                                            }

                                                            Entry var24 = (Entry)var25.next();
                                                            var26 = (String)var24.getKey();
                                                            var27 = var24.getValue();
                                                        } while(!(var27 instanceof String));
                                                    } while(var21.get(var26) instanceof RadioContext);

                                                    if (var22 != null && var22.size() != 0) {
                                                        if (var22.contains(var26)) {
                                                            this.a.print(var26 + "=" + var27 + "&");
                                                        }
                                                    } else {
                                                        this.a.print(var26 + "=" + var27 + "&");
                                                    }
                                                }
                                            }
                                        }

                                        if (var36.isAjax()) {
                                            this.a.print("', '" + var36.getHtmlTarget() + "')");
                                        }

                                        var44 = var36.getTarget();
                                        if (var44 == null || var44.length() == 0) {
                                            var44 = "_self";
                                        }

                                        this.a.print("'\" target=\"" + var44 + "\" class='" + var36.getStyleClass() + "' type=\"button\">");
                                        if (var36.getDesc() != null && var36.getDesc().length() != 0) {
                                            this.a.print(var36.getDesc());
                                        } else {
                                            this.a.print(ColFormater.format(var13, var12.getFormatPattern()));
                                        }

                                        this.a.print("</a> ");
                                    }
                                } else {
                                    String var37 = var12.getJsFunc();
                                    if (var37 != null && var37.length() > 0) {
                                        PageBuilder$JSObject var40 = (PageBuilder$JSObject)this.b.getAttribute("ext.script.engine");
                                        Object var39 = var40.getScope().get(var37, var40.getScope());
                                        if (var39 == null || !(var39 instanceof Function)) {
                                            String var42 = ConstantsEngine.replace("定义的 jsFunc 方法 $0 未找到.", var37);
                                            throw new ExtRuntimeException(var42);
                                        }

                                        Function var41 = (Function)var39;
                                        Object[] var43 = new Object[]{var8, var12.getAlias(), "html"};
                                        var41.call(var40.getCt(), var40.getScope(), var40.getScope(), var43);
                                    } else {
                                        String var38 = ColFormater.format(var13, var12.getFormatPattern());
                                        if (var38 != null && var12.getLength() > 0 && var38.length() > var12.getLength()) {
                                            var38 = var38.substring(0, var12.getLength());
                                            var38 = var38 + "...";
                                        }

                                        this.a.print(var38);
                                    }
                                }
                            } else {
                                this.a.print("&nbsp;");
                            }

                            if (var16 != null && var16.length() > 0) {
                                this.a.print("</p>");
                            }

                            this.a.print("</td>");
                        }
                    }

                    ColsContext var30 = var3.getColsContext();
                    if (var30 != null) {
                        String[] var31 = var3.getColsContext().getAliasArray();
                        String var32 = var3.getColsContext().getAlign();
                        if (var31 != null) {
                            String[] var35 = var31;
                            int var34 = var31.length;

                            for(int var33 = 0; var33 < var34; ++var33) {
                                var14 = var35[var33];
                                this.a.print("<td ");
                                if (var32 != null && var32.length() > 0) {
                                    this.a.print(" align=\"" + var32 + "\" ");
                                }

                                this.a.print(" class=\"" + (var30.getStyleClass() == null ? "" : var30.getStyleClass()) + " grid3-td\">");
                                this.a.print(var8.get(var14) == null ? " " : var8.get(var14).toString());
                                this.a.print("</td>");
                            }
                        }
                    }

                    this.a.println("</tr>");
                }
            }

            this.a.println("<script>");
            Iterator var29 = var6.iterator();

            while(var29.hasNext()) {
                String var28 = (String)var29.next();
                this.a.println("new YAHOO.widget.Tooltip(\"tt1\", { context:\"" + var28 + "\", text:jQuery('#" + var28 + "').attr('text') });");
            }

            this.a.println("</script>");
        }
    }

    public void buildTableEnd(DataGridContext var1) throws UnsupportedEncodingException {
        this.a.println("</table>");
        PageInfo var2 = var1.getPageInfo();
        if (var2 != null) {
            String var3 = RuleUtils.findCurMV(this.d).getFormId();
            String var4 = "currpage_";
            if (var1.getPageInputName() != null && var1.getPageInputName().length() > 0) {
                var4 = var1.getPageInputName();
            }

            this.a.println("<div class='pagesizeinfo'>");
            if (!var1.isAjax()) {
                DaoUtils.printPageInfo(var2, var3, this.a, var4, this.d);
            } else {
                Map var5 = this.c.getParams();
                StringBuffer var6 = new StringBuffer();
                Iterator var8 = var5.entrySet().iterator();

                while(var8.hasNext()) {
                    Entry var7 = (Entry)var8.next();
                    String var9 = (String)var7.getKey();
                    Object var10 = var7.getValue();
                    if (var10 instanceof String) {
                        var6.append(var9);
                        var6.append("=");
                        var6.append(URLEncoder.encode((String)var10, "UTF-8"));
                        var6.append("&");
                    }
                }

                String var11 = var1.getId();
                if (var1.getLabel() != null && var1.getLabel().length() > 0) {
                    var11 = var1.getLabel();
                }

                DaoUtils.printPageInfoByAjax(var2, this.a, var11, var6.toString(), this.d);
            }

            this.a.println("</div>");
            this.a.println("<input type='hidden' name='" + var4 + "' value='" + var2.getCurtpage() + "'>");
        }

        this.a.println("</div>");
    }

    public void buildBoxStart(BoxContext var1) {
        this.a.println("<div class=\"wrapper wrapper-content animated fadeInDown\">");
        this.a.println("<div class=\"row\">");
        this.a.println("<div class=\"col-sm-12\">");
        this.a.println("<div class=\"ibox\">");
        this.a.println("<div class=\"ibox-title\"><h5>" + var1.getTitle() + "</h5></div>");
        this.a.println("<div class=\"ibox-content\">");
    }

    public void buildBoxEnd(BoxContext var1) {
        this.a.println("</div>");
        this.a.println("</div>");
        this.a.println("</div>");
        this.a.println("</div>");
        this.a.println("</div>");
    }

    public void buildBRStart(BRContext var1) {
        this.a.println("<br>");
    }

    public void buildLink(LinkContext var1) throws UnsupportedEncodingException {
        String var2 = var1.getMethod();
        if (var2 == null) {
            var2 = "";
        }

        String var3 = var1.getOnClick();
        this.a.print("<button type=\"button\" onclick=\"");
        if (var3 != null && var3.length() != 0) {
            this.a.print(var3);
        } else {
            this.a.print("location.href='");
            this.a.print("extControl?");
            this.a.print("serviceid=" + var1.getAction() + "&");
            this.a.print("methodId=" + var2 + "&");
            if (var1.isAllowParam()) {
                Map var4 = this.c.getParams();
                Iterator var6 = var4.entrySet().iterator();

                while(var6.hasNext()) {
                    Entry var5 = (Entry)var6.next();
                    String var7 = (String)var5.getKey();
                    Object var8 = var5.getValue();
                    if (var8 instanceof String) {
                        this.a.print(var7 + "=" + URLEncoder.encode((String)var8, "UTF-8") + "&");
                    }
                }
            }

            this.a.print("'");
        }

        this.a.print("\"");
        this.a.print(" class='" + (var1.getStyleClass() == null ? "btn btn-default" : var1.getStyleClass()) + "'");
        this.a.print(">");
        String var9 = var1.getSrc();
        if (var9 != null && var9.length() != 0) {
            this.a.print("<img src='" + var9 + "' border='0'>");
        } else {
            this.a.print(var1.getText());
        }

        this.a.println("</button>");
    }

    public void buildStartMV(MVContext var1) {
        String var2 = (String)this.b.getAttribute("ext.view.serviceId");
        String var3 = (String)this.b.getAttribute("ext.view.methodId");
        if (!"true".equals(this.b.getParameter("_hideMVDiv")) && (var1.getHideMV() == null || !var1.getHideMV())) {
            this.a.println("<div class=\"mv_main mv_main2\" id=\"" + var1.getMvid() + "\">");
        }

        if (var3 == null) {
            var3 = "";
        }

        com.ruisi.ext.engine.view.emitter.html.a.a(this.e);
        com.ruisi.ext.engine.view.emitter.html.a.a();
        if (var1.isFromRef()) {
            var1.setShowForm(false);
        } else if (var1.isShowForm()) {
            if ("false".equalsIgnoreCase(this.b.getParameter("_showForm"))) {
                var1.setShowForm(false);
            } else {
                this.a.println("<script>");
                this.a.println("var v" + var1.getFormId() + " = {");
                this.a.println("extAction : 'extControl',");
                this.a.println("sidKey : 'serviceid',");
                this.a.println("midKey : 'methodId',");
                this.a.println("formId : '" + var1.getFormId() + "',");
                this.a.println("fromId : 't_from_id',");
                this.a.println("extMvId : '" + this.b.getAttribute("ext.view.mvid") + "',");
                this.a.println("sidValue : '" + var2 + "',");
                this.a.println("midValue : '" + var3 + "',");
                this.a.println("exportKey : 'dgexport',");
                this.a.println("orderKey : 'ext_col_order',");
                this.a.println("returnJspFlag : 'returnJsp',");
                this.a.println("needConfirm : false, ");
                this.a.println("resPath: '" + RuleUtils.getResPath(this.b) + "'");
                this.a.println("}");
                this.a.println("</script>");
                this.a.print("<form id=\"" + var1.getFormId() + "\" name=\"" + var1.getFormId() + "\" method=\"post\" action=\"" + "extControl" + "\" onsubmit=\"return checkRequire(this)\" ");
                if (var1.isUpload()) {
                    this.a.print(" enctype=\"multipart/form-data\"");
                }

                this.a.println(">");
                String var4 = (String)this.b.getAttribute("ext.view.fromId");
                if (var4 == null) {
                    var4 = "";
                }

                String var5 = this.b.getParameter("ext_col_order");
                if (var5 == null) {
                    var5 = "";
                }

                String var6 = this.b.getParameter("ext_order_state");
                if (var6 == null) {
                    var6 = "";
                }

                this.a.println("<input type='hidden' name='serviceid' value='" + var2 + "'>");
                this.a.println("<input type='hidden' name='methodId' value='" + var3 + "'>");
                this.a.println("<input type='hidden' name='t_from_id' value='" + var4 + "'>");
                this.a.println("<input type='hidden' name='ext_col_order' value='" + var5 + "'>");
                this.a.println("<input type='hidden' name='ext_order_state' id='ext_order_state' value='" + var6 + "'>");
                this.a.println("<input type='hidden' name='dgexport'>");
            }
        }
    }

    public void buildEndMV(MVContext var1) {
        if (var1.isFromRef()) {
            if (!"true".equals(this.b.getParameter("_hideMVDiv")) && (var1.getHideMV() == null || !var1.getHideMV())) {
                this.a.print("</div>");
            }

        } else if (!var1.isShowForm()) {
            if (!"true".equals(this.b.getParameter("_hideMVDiv")) && (var1.getHideMV() == null || !var1.getHideMV())) {
                this.a.print("</div>");
            }

        } else if ("false".equalsIgnoreCase(this.b.getParameter("_showForm"))) {
            if (!"true".equals(this.b.getParameter("_hideMVDiv")) && (var1.getHideMV() == null || !var1.getHideMV())) {
                this.a.print("</div>");
            }

        } else {
            this.a.println("</form>");
            this.a.print("</div>");
            this.a.println("<script language='javascript'>");
            this.a.println("function checkRequire(ff){");
            MVContext var2 = RuleUtils.findCurMV(this.d);
            Map var3 = var2.getMvParams();
            if (var3 != null) {
                Iterator var5 = var3.entrySet().iterator();

                while(var5.hasNext()) {
                    Entry var4 = (Entry)var5.next();
                    InputField var6 = (InputField)var4.getValue();
                    if (var6.isRequire()) {
                        if (var6 instanceof MoreValue) {
                            String var7 = "radio";
                            if (var6 instanceof MultiSelectContext) {
                                var7 = "mselect";
                            }

                            this.a.println("if(!checkRadio(ff, '" + var6.getId() + "','" + var7 + "')){");
                            this.a.println("$.messager.alert(\"出错了\",\"请勾选数据!\", \"error\");");
                        } else if (var6 instanceof RadioContext) {
                            this.a.println("if(!checkRadio(ff, '" + var6.getId() + "', 'radio')){");
                            this.a.println("$.messager.alert('出错了','请勾选数据!','error')");
                        } else {
                            this.a.println("if(ff." + var6.getId() + ".value==''){");
                            this.a.println("$.messager.alert('出错了',' " + (var6.getDesc() != null ? var6.getDesc().replaceAll("&nbsp;", "") : "当前") + " 是必填项.', 'error', function(){");
                            this.a.println("try{");
                            this.a.println("ff." + var6.getId() + ".focus();");
                            this.a.println("}catch(err){}");
                            this.a.println("});");
                        }

                        this.a.println("return false;");
                        this.a.println("}");
                    }
                }
            }

            if (var1.getSubmitCheck() != null) {
                this.a.println(var1.getSubmitCheck().getText());
            }

            String var8 = var1.getFormId();
            this.a.println("if(v" + var8 + ".needConfirm){");
            this.a.println("return confirm('要执行的操作，你确认吗？');");
            this.a.println("}else{return true;}");
            this.a.println("}");
            this.a.println("</script>");
        }
    }

    public void buildDivStart(DivContext var1) {
        this.a.print("<div ");
        if (var1.getId() != null && var1.getId().length() > 0) {
            this.a.print(" id=\"" + var1.getId() + "\" ");
        }

        if (var1.getAlign() != null && var1.getAlign().length() > 0) {
            this.a.print(" align=\"" + var1.getAlign() + "\"");
        }

        if (var1.getStyleClass() != null && var1.getStyleClass().length() > 0) {
            this.a.print(" class=\"" + var1.getStyleClass() + "\" ");
        }

        if (var1.getStyle() != null && var1.getStyle().length() > 0) {
            this.a.print(" style=\"" + var1.getStyle() + "\" ");
        }

        this.a.println(">");
    }

    public void buildDivEnd(DivContext var1) {
        this.a.println("</div>");
    }

    public void buildFieldsetStart(FieldsetContext var1) {
        this.a.println("<fieldset>");
        this.a.println("<legend>" + var1.getTitle() + "</legend>");
    }

    public void buildFieldsetEnd(FieldsetContext var1) {
        this.a.println("</fieldset>");
    }

    public void buildJsChart(ChartContext var1) {
        String var2 = var1.getId();
        this.a.print("<div id=\"p" + var1.getId() + "\" class=\"chartUStyle\" label=\"" + var1.getLabel() + "\">");
        ChartTitleContext var3 = var1.getTitle();
        if (var3 != null) {
            this.a.println("<div align='center' class='chartTitle chartTitle2'>" + var3.getText() + "</div>");
        }

        if (var1.loadOptions().size() == 0) {
            this.a.print("<div id=\"" + var2 + "\" >无数据.</div>");
        } else {
            ShowJSCharts var4 = new ShowJSCharts(this.a, var1, this.b, this.d, false);
            try {
                var4.show();
            } catch (ExtConfigException e1) {
                e1.printStackTrace();
            }
        }

        this.a.println("</div>");
    }

    public void buildKpiDesc2(KpiDescContext var1) {
        this.a.println("<div align=\"left\" class=\"kpiDesc\">指标解释</div>");
        this.a.println("<table class=\"kpiDescTbs\">");
        List var2 = var1.loadOptions();

        for(int var3 = 0; var3 < var2.size(); ++var3) {
            Map var4 = (Map)var2.get(var3);
            this.a.print("<tr>");
            this.a.print("<td width=\"20%\" align=\"right\">" + var4.get("name") + "：</td>");
            this.a.print("<td width=\"5%\" align=\"center\">(");
            this.a.print((String)var4.get("unit"));
            this.a.print(")</td>");
            String var5 = (String)var4.get("name_desc");
            this.a.print("<td width=\"75%\">" + (var5 == null ? "" : var5) + "</td>");
            this.a.print("</tr>");
        }

        this.a.println("</table>");
    }

    public static void putData() {
        com.ruisi.ext.engine.view.emitter.html.a.b();
    }

    public static void removeData() {
        com.ruisi.ext.engine.view.emitter.html.a.c();
    }

    public static int getEndDate(ServletContext var0) throws ParseException {
        if (com.ruisi.ext.engine.view.emitter.html.a.d() == null) {
            com.ruisi.ext.engine.view.emitter.html.a.a(var0);
        }

        String var1 = com.ruisi.ext.engine.view.emitter.html.a.d();
        if (var1 != null && var1.length() != 0) {
            SimpleDateFormat var2 = new SimpleDateFormat("yyyyMMdd");
            Date var3 = var2.parse(var1);
            long var4 = var3.getTime();
            long var6 = (new Date()).getTime();
            return var6 > var4 ? 0 : Math.round((float)((var4 - var6) / 86400000L));
        } else {
            return 0;
        }
    }
}