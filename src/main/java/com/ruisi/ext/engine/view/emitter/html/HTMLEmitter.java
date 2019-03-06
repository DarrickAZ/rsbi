//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.html;

import com.itextpdf.text.DocumentException;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.cross.CrossBuilder;
import com.ruisi.ext.engine.cross.CrossWriter2EASYUI;
import com.ruisi.ext.engine.cross.CrossWriter2HTML;
import com.ruisi.ext.engine.cross.CrossWriter2JSON;
import com.ruisi.ext.engine.cross.CrossWriter2LockUI;
import com.ruisi.ext.engine.cross.CrossWriter2OLAP;
import com.ruisi.ext.engine.gridreport.GridWriter;
import com.ruisi.ext.engine.gridreport.GridWriter2HTML;
import com.ruisi.ext.engine.gridreport.GridWriter2LockUI;
import com.ruisi.ext.engine.gridreport.GridWriter2Query;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.context.form.ButtonContext;
import com.ruisi.ext.engine.view.context.form.CheckBoxContext;
import com.ruisi.ext.engine.view.context.form.DateSelectContext;
import com.ruisi.ext.engine.view.context.form.FileFieldContext;
import com.ruisi.ext.engine.view.context.form.RadioContext;
import com.ruisi.ext.engine.view.context.form.SelectContext;
import com.ruisi.ext.engine.view.context.form.TextFieldContext;
import com.ruisi.ext.engine.view.context.form.TreeContext;
import com.ruisi.ext.engine.view.context.grid.DataGridContext;
import com.ruisi.ext.engine.view.context.gridreport.GridReportContext;
import com.ruisi.ext.engine.view.context.html.BRContext;
import com.ruisi.ext.engine.view.context.html.BoxContext;
import com.ruisi.ext.engine.view.context.html.CustomContext;
import com.ruisi.ext.engine.view.context.html.DivContext;
import com.ruisi.ext.engine.view.context.html.FieldsetContext;
import com.ruisi.ext.engine.view.context.html.ImageContext;
import com.ruisi.ext.engine.view.context.html.IncludeContext;
import com.ruisi.ext.engine.view.context.html.KpiDescContext;
import com.ruisi.ext.engine.view.context.html.LinkContext;
import com.ruisi.ext.engine.view.context.html.TextContext;
import com.ruisi.ext.engine.view.context.html.TextProperty;
import com.ruisi.ext.engine.view.context.html.table.TableContext;
import com.ruisi.ext.engine.view.context.html.table.TdContext;
import com.ruisi.ext.engine.view.context.html.table.TrContext;
import com.ruisi.ext.engine.view.context.tab.TabContext;
import com.ruisi.ext.engine.view.context.tab.TabViewContext;
import com.ruisi.ext.engine.view.emitter.ContextEmitter;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtResponse;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.WriteException;
import org.apache.commons.io.FileUtils;

public class HTMLEmitter implements ContextEmitter {
    private ExtWriter a;
    private ExtRequest b;
    private HtmlLayoutEnginer c;
    private HtmlInputEnginer d;
    private HtmlTabView e;
    private ExtResponse f;
    private ExtEnvirContext g;
    private ServletContext h;
    private InputOption i;

    public HTMLEmitter() {
    }

    public ServletContext getServletContext() {
        return this.h;
    }

    @Override
    public void end(Element var1) {
    }

    @Override
    public void start(Element var1) {
    }

    @Override
    public ExtWriter getWriter() {
        return this.a;
    }

    @Override
    public void startRadio(RadioContext var1) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        this.d.buildRadio(var1);
    }

    @Override
    public void startCrossReport(CrossReportContext var1) throws InvocationTargetException, NoSuchMethodException, ExtConfigException, IllegalAccessException {
        String var2 = (String)this.b.getAttribute("ext_fromAjax");
        Map var4;
        String var12;
        if (!"true".equals(var2) && var1.getExport() != null && var1.getExport()) {
            StringBuffer var3 = new StringBuffer();
            var4 = this.i.getParams();
            Iterator var6 = var4.entrySet().iterator();

            while(var6.hasNext()) {
                Entry var5 = (Entry)var6.next();
                String var7 = (String)var5.getKey();
                Object var8 = var5.getValue();
                if (var8 instanceof String) {
                    var3.append(var7);
                    var3.append("=");
                    var3.append(var8);
                    var3.append("&");
                }
            }

            var12 = RuleUtils.findCurMV(this.getCtx()).getMvid();
            this.a.print("<div align=right class=exportXLS><a id=\"crsexport\" href='javascript:exportXLS(\"extControl?serviceid=ext.sys.cross.export&r_st=export&t_from_id=" + var12 + "&" + var3 + "reportId=" + var1.getId() + "\")'>导出</a></div>");
            this.a.print("<script>$(function(){$(\"#crsexport\").linkbutton({iconCls:'icon-export',plain:true})})</script>");
        }

        CrossBuilder var9 = new CrossBuilder(var1, this.b, this.g);
        var9.init();
        var4 = null;
        if ("easyUI".equalsIgnoreCase(var1.getOut())) {
            CrossWriter2EASYUI var10 = new CrossWriter2EASYUI(var9, this.a, this.i);
            var10.writeCols();
            var10.writeRows();
        } else if ("olap".equalsIgnoreCase(var1.getOut())) {
            CrossWriter2OLAP var11 = new CrossWriter2OLAP(var9, this.a, this.g, this.i);
            CrossWriter2OLAP var14 = (CrossWriter2OLAP)var11;
            var14.writeAll();
        } else if ("lockUI".equalsIgnoreCase(var1.getOut())) {
            CrossWriter2LockUI var16 = new CrossWriter2LockUI(var9, this.a, this.g, this.i);
            var16.writeAll();
        } else if ("json".equalsIgnoreCase(var1.getOut())) {
            CrossWriter2JSON var13 = new CrossWriter2JSON(var9, this.a, this.g, this.i);
            var13.writeCols();
            var13.writeRows();
        } else {
            CrossWriter2HTML var15 = new CrossWriter2HTML(var9, this.a, this.g, this.i);
            if ("true".equals(var2) && this.b.getParameter("currPage") == null) {
                var15.queryLastLevelCols();
                var15.writeRows();
            } else {
                this.a.print("<div id='" + var1.getId() + "' class=\"crossReport\"");
                this.a.print(" style=\"overflow:auto;");
                var12 = var1.getWidth();
                String var17 = var1.getHeight();
                if (var12 != null && var12.length() > 0) {
                    this.a.print("width:" + var12 + "px;");
                }

                if (var17 != null && var17.length() > 0) {
                    this.a.print("height:" + var17 + "px;");
                }

                this.a.print("\"");
                this.a.print(">");
                this.a.println("<table class=\"grid3\" id=\"T_" + var1.getId() + "\" cellpadding=\"0\" cellspacing=\"0\">");
                var15.writeCols();
                var15.writeRows();
                this.a.println("</table>");
                var15.writerPageInfo();
                this.a.print("</div>");
                if (this.b.getParameter("currPage") == null) {
                    var15.writerLink();
                    var15.writerColLink();
                }

                var15.writerSort();
            }

            var15.writerFieldDirll();
        }

    }

    @Override
    public void startKpiDesc(KpiDescContext var1) {
        this.c.buildKpiDesc2(var1);
    }

    @Override
    public void startFileField(FileFieldContext var1) {
        this.d.buildFileField(var1);
    }

    @Override
    public void startFieldset(FieldsetContext var1) {
        this.c.buildFieldsetStart(var1);
    }

    @Override
    public void startTabView(TabViewContext var1) {
        String var2 = var1.getOut();
        if (var2 != null && !"extjs".equalsIgnoreCase(var2)) {
            this.e = new HtmlTabViewByJQuery(this);
        } else {
            this.e = new HtmlTabViewEnginer(this);
        }

        if (var1.isAjax()) {
            this.e.buildTabViewStartAjax(var1);
        } else {
            this.e.buildTabViewStart(var1);
        }

    }

    @Override
    public void startDateSelect(DateSelectContext var1) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        this.d.buildDateSelect(var1);
    }

    @Override
    public void endTable(TableContext var1) {
        this.a.println("</table>");
    }

    @Override
    public void endTabView(TabViewContext var1) {
        if (var1.isAjax()) {
            this.e.buildTabViewEndAjax(var1);
        } else {
            this.e.buildTabViewEnd(var1);
        }

    }

    @Override
    public void startTab(TabContext var1) {
        TabViewContext var2 = (TabViewContext)var1.getParent();
        if (var2.isAjax()) {
            this.e.buildTabStartAjax(var1);
        } else {
            this.e.buildTabStart(var1);
        }

    }

    @Override
    public void endTd(TdContext var1) {
        this.a.print("</td>");
    }

    @Override
    public void endTr(TrContext var1) {
        this.a.println("</tr>");
    }

    @Override
    public void startTable(TableContext var1) {
        this.a.print("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"");
        if (var1.getStyleClass() != null && var1.getStyleClass().length() > 0) {
            this.a.print(" class=\"" + var1.getStyleClass() + "\"");
        }

        if (var1.getStyle() != null && var1.getStyle().length() > 0) {
            this.a.print("\tstyle=\"" + var1.getStyle() + "\"");
        }

        if (var1.getAlign() != null && var1.getAlign().length() > 0) {
            this.a.print("\talign=\"" + var1.getAlign() + "\"");
        }

        this.a.println(">");
    }

    @Override
    public void startTd(TdContext var1) {
        String var2 = var1.getWidth();
        this.a.print("<td valign=\"top\" ");
        if (var2 != null && var2.length() > 0) {
            this.a.print(" width=" + var2);
        }

        String var3 = var1.getHeight();
        if (var3 != null && var3.length() > 0) {
            this.a.print(" height=\"" + var3 + "\"");
        }

        String var4 = var1.getStyle();
        if (var4 != null && var4.length() > 0) {
            this.a.print(" style=\"" + var4 + "\"");
        }

        String var5 = var1.getStyleClass();
        if (var5 != null && var5.length() > 0) {
            this.a.print(" class=\"" + var5 + "\"");
        }

        String var6 = var1.getColspan();
        if (var6 != null && var6.length() > 0) {
            this.a.print(" colspan=\"" + var6 + "\"");
        }

        String var7 = var1.getRowspan();
        if (var7 != null && var7.length() > 0) {
            this.a.print(" rowspan=\"" + var7 + "\" ");
        }

        this.a.print(">");
    }

    @Override
    public void startTr(TrContext var1) {
        this.a.println("<tr>");
    }

    @Override
    public void endDiv(DivContext var1) {
        this.c.buildDivEnd(var1);
    }

    @Override
    public void startDiv(DivContext var1) {
        this.c.buildDivStart(var1);
    }

    @Override
    public void startCheckBox(CheckBoxContext var1) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        this.d.buildCheckBox(var1);
    }

    @Override
    public void startInclude(IncludeContext var1) throws ServletException, IOException {
        if (var1.getContent() != null) {
            this.a.print(var1.getContent());
            var1.setContent((String)null);
        } else {
            this.a.flush();
        }

        if (var1.getPage() != null && var1.getPage().length() > 0) {
            String var2 = var1.getPage();
            if (!var2.endsWith("js") && !var2.endsWith("css")) {
                this.b.getRequestDispatcher(var2).include((HttpServletRequest)this.b.getProxy(), (HttpServletResponse)this.f.getProxy());
            } else {
                String var3 = this.h.getRealPath("/") + var2;
                String var4 = FileUtils.readFileToString(new File(var3), "UTF-8");
                this.a.print(var4);
            }
        }

    }

    @Override
    public void startLink(LinkContext var1) throws UnsupportedEncodingException {
        this.c.buildLink(var1);
    }

    @Override
    public void startSelect(SelectContext var1) throws UnsupportedEncodingException {
        this.d.buildSelect(var1);
    }

    @Override
    public void startBR(BRContext var1) {
        this.c.buildBRStart(var1);
    }

    @Override
    public void startButton(ButtonContext var1) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, ExtConfigException {
        this.d.buildButton(var1);
    }

    @Override
    public void startTextField(TextFieldContext var1) {
        String var2 = var1.getType();
        if ("text".equalsIgnoreCase(var2)) {
            this.d.buildText(var1);
        } else if ("hidden".equalsIgnoreCase(var2)) {
            this.d.buildHidden(var1);
        } else if ("password".equalsIgnoreCase(var2)) {
            this.d.buildPassword(var1);
        } else if ("textarea".equalsIgnoreCase(var2)) {
            this.d.buildTextarea(var1);
        } else {
            this.d.buildText(var1);
        }

    }

    @Override
    public void startTree(TreeContext var1) {
        this.d.buildTree(var1);
    }

    @Override
    public void endBox(BoxContext var1) {
        this.c.buildBoxEnd(var1);
    }

    @Override
    public void startBox(BoxContext var1) {
        this.c.buildBoxStart(var1);
    }

    public ExtEnvirContext getCtx() {
        return this.g;
    }

    public void setCtx(ExtEnvirContext var1) {
        this.g = var1;
    }

    public InputOption getOption() {
        return this.i;
    }

    public void setOption(InputOption var1) {
        this.i = var1;
    }

    @Override
    public void endMV(MVContext var1) {
        this.c.buildEndMV(var1);
    }

    @Override
    public void initialize(ExtWriter var1, ExtRequest var2, ExtResponse var3, ExtEnvirContext var4, InputOption var5, ServletContext var6) {
        this.a = var1;
        this.b = var2;
        this.f = var3;
        this.g = var4;
        this.i = var5;
        this.h = var6;
        this.c = new HtmlLayoutEnginer(this);
        this.d = new HtmlInputEnginer(this);
        this.e = new HtmlTabViewEnginer(this);
        var4.put("outType", "html");
    }

    @Override
    public void startDataGrid(DataGridContext var1) throws UnsupportedEncodingException, ExtConfigException {
        String var2 = var1.getDataRef();
        if (var2 != null && var2.length() > 0) {
            Object var3 = this.b.getAttribute(var2);
            if (var3 instanceof List) {
                var1.setOptions((List)var3);
            }
        }

        if ("json".equalsIgnoreCase(var1.getOut())) {
            this.c.buildTableJson(var1);
        } else if (var1.isAjax()) {
            String var4 = (String)this.b.getAttribute("ext_fromAjax");
            if ("true".equalsIgnoreCase(var4)) {
                this.c.buildTableStart(var1);
                this.c.buildTableHead(var1.getColConfigContext());
                this.c.buildTableBody(var1.loadOptions(), var1, var1.getColConfigContext());
                this.c.buildTableEnd(var1);
            } else {
                this.c.buildTabelAjax(var1);
            }
        } else {
            this.c.buildTableStart(var1);
            this.c.buildTableHead(var1.getColConfigContext());
            this.c.buildTableBody(var1.loadOptions(), var1, var1.getColConfigContext());
            this.c.buildTableEnd(var1);
        }

    }

    @Override
    public void startMV(MVContext var1) {
        this.c.buildStartMV(var1);
    }

    @Override
    public void startText(TextContext var1) {
        if (var1.getTextProperty() == null) {
            String var2 = var1.getText();
            if (var1.getFormatEnter() != null && var1.getFormatEnter()) {
                var2 = var2.replaceAll("\n", "<br/>");
            }

            this.a.println(var2);
        } else {
            TextProperty var4 = var1.getTextProperty();
            this.a.print("<div " + (var4.getId() != null && var4.getId().length() > 0 ? "id=\"" + var4.getId() + "\"" : "") + " " + "class=\"" + (var4.getStyleClass() != null && var4.getStyleClass().length() > 0 ? var4.getStyleClass() : "") + "\"" + " style=\"");
            if (var4.getAlign() != null && var4.getAlign().length() > 0) {
                this.a.print("text-align: " + var4.getAlign() + ";");
            }

            if (var4.getSize() != null && var4.getSize().length() > 0) {
                this.a.print("font-size:" + var4.getSize() + "px;");
            }

            if (var4.getWeight() != null && var4.getWeight().length() > 0) {
                this.a.print("font-weight:" + var4.getWeight() + ";");
            }

            if (var4.getHeight() != null && var4.getHeight().length() > 0) {
                this.a.print("height:" + var4.getHeight() + "px;");
            }

            if (var4.getColor() != null && var4.getColor().length() > 0) {
                this.a.print("color:" + var4.getColor() + ";");
            }

            if (var4.getLineHeight() != null) {
                this.a.println("line-height:" + var4.getLineHeight() + "px;");
            }

            this.a.print("\">");
            String var3 = var1.getText();
            if (var1.getFormatEnter() != null && var1.getFormatEnter()) {
                var3 = var3.replaceAll("\n", "<br/>");
            }

            this.a.print(var3);
            this.a.print("</div>");
        }

    }

    public ExtWriter getOut() {
        return this.a;
    }

    public ExtRequest getRequest() {
        return this.b;
    }

    @Override
    public void endTab(TabContext var1) {
        TabViewContext var2 = (TabViewContext)var1.getParent();
        if (var2.isAjax()) {
            this.e.buildTabEndAjax(var1);
        } else {
            this.e.buildTabEnd(var1);
        }

    }

    @Override
    public void endFieldset(FieldsetContext var1) {
        this.c.buildFieldsetEnd(var1);
    }

    @Override
    public void startChart(ChartContext var1) {
        if (var1.getShape() != null && var1.getShape().length() > 0) {
            this.c.buildJsChart(var1);
        }

    }

    @Override
    public void rebuildSelect(SelectContext var1) {
        this.d.rebuildSelect(var1);
    }

    public ExtResponse getResponse() {
        return this.f;
    }

    @Override
    public void startGridReport(GridReportContext var1) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException, DocumentException, ExtConfigException, WriteException {
        Object var2 = null;
        String var3 = var1.getOut();
        if ("lockUI".equals(var3)) {
            var2 = new GridWriter2LockUI(var1, this.a, this.g, this.i);
        } else if ("query".equals(var3)) {
            var2 = new GridWriter2Query(var1, this.a, this.g, this.i);
        } else {
            var2 = new GridWriter2HTML(var1, this.a, this.g, this.i);
        }

        ((GridWriter)var2).begin();
        ((GridWriter)var2).writeHeader();
        ((GridWriter)var2).writeDetail();
        ((GridWriter)var2).writeFooter();
        ((GridWriter)var2).end();
    }

    @Override
    public void startCustom(CustomContext var1) {
    }

    @Override
    public void startImage(ImageContext var1) {
        Integer var2 = var1.getWidth();
        Integer var3 = var1.getHeight();
        String var4 = var1.getAlign();
        String var5 = "";
        if ("local".equals(var1.getType())) {
            String var6 = this.b.getContextPath();
            String var7 = this.b.getScheme() + "://" + this.b.getServerName() + ":" + this.b.getServerPort() + var6 + "/portal/img/" + var1.getPath() + ".action";
            var5 = var7;
        } else {
            var5 = var1.getUrl();
        }

        this.a.print("<img class=\"img-responsive\" src=\"" + var5 + "\" style=\"" + (var2 != null ? "width:" + var2 + "px;" : "") + (var3 != null ? "height:" + var3 + "px;" : "") + "\" " + (var4 != null && var4.length() > 0 ? "align=\"" + var4 + "\"" : "") + ">");
    }
}
