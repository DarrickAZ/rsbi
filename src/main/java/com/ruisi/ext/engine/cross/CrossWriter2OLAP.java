//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.cross;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.util.DaoUtils;
import com.ruisi.ext.engine.util.LabelUtils;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.builder.PageBuilder$JSObject;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.cross.*;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import com.ruisi.ext.runtime.tag.CalendarTag;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.mozilla.javascript.Function;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CrossWriter2OLAP implements CrossWriter {
    private CrossReportContext a;
    private ExtWriter b;
    private CrossCols c;
    private CrossRows d;
    private InputOption e;
    private List f;
    private ExtRequest g;
    private CrossWriterService h;
    private ExtEnvirContext i;
    private String j;
    private List k = new ArrayList();
    private List l = new ArrayList();
    private OlapWriterInterface m;
    private String n;
    private JSONObject o;
    private int p = 1;

    @Override
    public void writerFieldFmts() {
    }

    @Override
    public void writerPageInfo() {
        if (this.a.getPageInfo() != null) {
            Map var1 = this.e.getParams();
            StringBuffer var2 = new StringBuffer();
            if (var1 != null) {
                Iterator var4 = var1.entrySet().iterator();

                while(var4.hasNext()) {
                    Entry var3 = (Entry)var4.next();
                    String var5 = (String)var3.getKey();
                    Object var6 = var3.getValue();
                    if (var6 instanceof String) {
                        var2.append(var5);
                        var2.append("=");
                        var2.append(var6);
                        var2.append("&");
                    }
                }
            }

            this.b.println("<div align='right'>");
            DaoUtils.printPageInfoByAjax(this.a.getPageInfo(), this.b, this.a.getId(), var2.toString(), this.i);
            this.b.println("</div>");
        }

    }

    @Override
    public void writerSort() {
        this.b.println("<script>");
        this.b.println("jQuery(document).ready(function(){");
        StringBuffer var1 = new StringBuffer();
        Iterator var3;
        if (this.a.getRowHeads() == null) {
            for(int var2 = 0; var2 < this.d.getMaxLevel(); ++var2) {
                var1.append("'None',");
            }
        } else {
            var3 = this.a.getRowHeads().iterator();

            while(var3.hasNext()) {
                RowHeadContext var4 = (RowHeadContext)var3.next();
                var1.append("'None',");
            }
        }

        var3 = this.k.iterator();

        while(true) {
            while(var3.hasNext()) {
                CrossField var5 = (CrossField)var3.next();
                if (var5.getOrder() != null && var5.getOrder()) {
                    var1.append("'Number',");
                } else {
                    var1.append("'None',");
                }
            }

            String var6 = var1.substring(0, var1.length() - 1);
            this.b.println("new SortableTable(document.getElementById('T_" + this.a.getId() + "'),[" + var6 + "], " + "true".equalsIgnoreCase(this.a.getFirstSort()) + ");");
            this.b.println("});");
            this.b.println("</script>");
            return;
        }
    }

    @Override
    public void queryLastLevelCols() {
        this.h.setCrossFiledLastLevel(this.c.getCols(), this.c);
        CrossWriterService.findNodeByLastLevel(this.c.getMaxLevel() - 1, this.c.getCols(), this.k);
    }

    public CrossWriter2OLAP(CrossBuilder var1, ExtWriter var2, ExtEnvirContext var3, InputOption var4) {
        this.a = var1.getCrossReport();
        this.b = var2;
        this.f = var1.getCrossReport().loadOptions();
        this.c = this.a.getCrossCols();
        this.d = this.a.getCrossRows();
        this.g = var1.getRequest();
        this.i = var3;
        this.e = var4;
        CrossField var5 = var1.getLoader().loadFieldByKpiCode((String)null);
        this.h = new CrossWriterService(var5, this.g, this.a);
        this.m = null;
        String var6 = ExtContext.getInstance().getConstant("OlapWriter");
        if (var6 != null && var6.length() > 0) {
            try {
                this.m = (OlapWriterInterface)Class.forName(var6).newInstance();
            } catch (Exception var8) {
                var8.printStackTrace();
            }
        }

        this.n = (String)this.g.getAttribute("compId");
        this.o = (JSONObject)this.g.getAttribute("tablej");
    }

    private JSONObject a(String var1, String var2) {
        JSONObject var3 = null;
        JSONArray var4 = this.o.getJSONArray(var2.equals("row") ? "rows" : "cols");

        for(int var5 = 0; var5 < var4.size(); ++var5) {
            JSONObject var6 = var4.getJSONObject(var5);
            if (var6.getString("id").equals(var1)) {
                var3 = var6;
                break;
            }
        }

        return var3;
    }

    public void writeAll() {
        this.b.print("<div class=\"crossReport\">");
        this.b.print("<table class=\"d_table2\"><tbody>");
        this.b.print("<tr><td class=\"blank\" valign='bottom'>");
        if (this.m != null) {
            this.m.wirteRowDims(this.g, this.b, this.a);
        }

        this.b.print("</td><td><div id=\"d_colDims\">");
        if (this.m != null) {
            this.m.writeColDims(this.g, this.b, this.a);
        }

        this.writeCols();
        this.b.println("</div></td></tr>");
        this.b.println("<tr><td valign=\"top\"><div id=\"d_rowDims\">");
        this.writeRows();
        this.b.println("</div></td><td valign=\"top\"><div id=\"d_kpi\">");
        this.writeKpis();
        this.b.println("</div></td></tr>");
        this.b.println("</tbody>");
        this.b.println("</table>");
        this.b.print("</div>");
    }

    public void writeKpis() {
        this.b.println("<table class=\"grid5\"  cellpadding=\"0\" cellspacing=\"0\">");

        for(int var1 = 0; var1 < this.l.size(); ++var1) {
            this.b.print("<tr>");
            CrossField var2 = (CrossField)this.l.get(var1);
            List var3 = this.h.querySubData(var2, this.f, 1, (String)null);

            for(int var4 = 0; var4 < this.k.size(); ++var4) {
                CrossField var5 = (CrossField)this.k.get(var4);
                this.writerKpi(var2, var3, var5, var1, var4);
            }

            if (this.h.whereIn == 1) {
                this.h.currKpiNode = null;
            }

            if (this.h.backKpiIn == 1) {
                this.h.backKpiNode = null;
            }

            if (this.h.formulaPos == 1) {
                this.h.currFormula = null;
            }

            this.b.print("</tr>");
        }

        this.b.println("</table>");
    }

    public void writeCols() {
        this.b.println("<table class=\"grid5\"  cellpadding=\"0\" cellspacing=\"0\">");

        for(int var1 = 0; var1 < this.c.getMaxLevel(); ++var1) {
            ArrayList var2 = new ArrayList();
            CrossWriterService.findNodeByLevel(var1, this.c.getCols(), var2);
            this.b.print("<tr class=\"scrollColThead\">");

            for(Iterator var4 = var2.iterator(); var4.hasNext(); this.b.print("</th>")) {
                CrossField var3 = (CrossField)var4.next();
                int var5 = var3.getSpan();
                this.b.print("<th class=\"" + var3.getStyleClass() + "\" colspan=\"" + var5 + "\"");
                int var6 = 0;
                if (var3.getSubs() != null) {
                    var6 = var3.getSubs().size();
                }

                if (var6 == 0) {
                    var3.setLastLevel(var3.getLevel());
                    if (var3.getWidth() != null && var3.getWidth().length() > 0) {
                        this.b.print(" width='" + var3.getWidth() + "' ");
                    }
                }

                if (var6 == 0 && var3.getLevel() != this.c.getMaxLevel() - 1) {
                    this.b.print(" rowspan = \"" + (this.c.getMaxLevel() - var3.getLevel()) + "\"");
                    var3.setLastLevel(this.c.getMaxLevel() - 1);
                }

                this.b.print(">");
                if (var6 == 0) {
                    this.b.print("<span class=\"colkpi\"><span class=\"kpiname\" title=\"" + var3.getDesc() + "\">");
                } else {
                    this.b.print("<div class=\"coldim\">");
                }

                if (!var3.getType().equalsIgnoreCase("none") && !var3.getType().equalsIgnoreCase("kpiOther")) {
                    if (var3.getLevel() == this.c.getMaxLevel() - 2) {
                        if (this.p == 1) {
                            this.b.print("<a class=\"dimDrill\" onclick=\"drillDim(" + this.n + ", this, 'col', '" + var3.getValue() + "', '" + var3.getDesc() + "', '" + var3.getId() + "')\" style=\"opacity:0.5\">  </a>");
                        } else {
                            JSONObject var7 = this.a(var3.getId(), "col");
                            if ("false".equals(var7.get("endlvl"))) {
                                this.b.print("<a class=\"dimDrill\" onclick=\"openTheDim(" + this.n + ", 'col', '" + var3.getValue() + "', '" + var3.getDesc() + "', '" + var3.getId() + "')\" style=\"opacity:0.5\">  </a>");
                            }
                        }
                    } else {
                        this.b.print("<a class=\"dimgoup\"  onclick=\"goupDim(" + this.n + ", this, 'col','" + var3.getId() + "', true)\" style=\"opacity:0.5\">  </a>");
                    }
                }

                String var9 = var3.getDesc();
                if (var3.getType().equals("frd") && "month".equalsIgnoreCase(var3.getDateType()) && var9 != null && var9.length() == 6) {
                    var9 = var9.substring(0, 4) + "-" + var9.substring(4, 6);
                }

                if (var3.getType().equals("frd") && "day".equalsIgnoreCase(var3.getDateType()) && var9 != null && var9.length() == 8) {
                    var9 = var9.substring(0, 4) + "-" + var9.substring(4, 6) + "-" + var9.substring(6, 8);
                }

                if (var3.getType().equals("frd") && "day".equalsIgnoreCase(var3.getDateType())) {
                    String var8 = CalendarTag.getFestival(var3.getValue(), var3.getDateTypeFmt());
                    if (var8 != null && var8.length() > 0) {
                        var9 = var8;
                    }
                }

                if (var6 == 0) {
                    this.b.print(CrossWriterService.substringHeader(var9.trim(), 7, "html"));
                } else {
                    this.b.print("<span class=\"s_colDim\" title=\"" + var9 + "\" >");
                    if (var5 == 1 || var5 == 2) {
                        var9 = CrossWriterService.substringHeader(var9.trim(), 7, "html");
                    }

                    this.b.print(var9);
                    this.b.print("</span>");
                }

                if (var6 == 0) {
                    this.b.print("</span>");
                }

                if (var6 == 0 && var3.getType().equalsIgnoreCase("kpiOther")) {
                    if (var3.getId().startsWith("ext_")) {
                        this.b.print("<a class=\"extKpibtn dimoptbtn set\" href='javascript:;' onclick=\"delExtKpi(this," + var3.getId().split("_")[1] + ",'" + var3.getId().split("_")[2] + "');\"> &nbsp; </a>");
                    } else {
                        this.b.print("<a class=\"dimoptbtn set\" href='javascript:;' onclick=\"setKpiInfo(this,'" + var3.getId() + "');\"> &nbsp; </a>");
                    }
                }

                if (var6 == 0) {
                    this.b.print("</span>");
                } else {
                    this.b.print("</div>");
                }
            }

            this.b.print("</tr>");
        }

        this.b.println("</table>");
        CrossWriterService.findNodeByLastLevel(this.c.getMaxLevel() - 1, this.c.getCols(), this.k);
    }

    public void writeRows() {
        this.b.println("<table class=\"grid5\"  cellpadding=\"0\" cellspacing=\"0\">");
        ArrayList var1 = new ArrayList();
        CrossWriterService.loopRow(this.d.getRows(), var1);
        int var2 = 0;

        CrossField var3;
        for(Iterator var4 = this.d.getRows().iterator(); var4.hasNext(); var2 += var3.getSpan()) {
            var3 = (CrossField)var4.next();
        }

        int var14 = 0;

        for(int var15 = 0; var15 < var2; ++var15) {
            String var5 = var15 % 2 == 0 ? "tr-row1" : "tr-row2";
            if (this.d.getLink() != null) {
                var5 = var5 + " row-link";
            }

            CrossField var6 = (CrossField)var1.get(var14);
            if (var6.getStyleToLine() != null && var6.getStyleToLine() && var6.getStyleClass() != null && var6.getStyleClass().length() > 0) {
                var5 = var5 + " " + var6.getStyleClass();
            }

            this.b.print("<tr  class=\"" + var5 + "\">");
            CrossField var7 = null;

            for(int var8 = 0; var8 < this.d.getMaxLevel(); ++var8) {
                CrossField var9 = (CrossField)var1.get(var14);
                ++var14;
                this.b.print("<th align=\"left\" valign=\"top\" rowspan=\"" + var9.getSpan() + "\"");
                String var10 = "grid5-td";
                if (var9.getStyleClass() != null && var9.getStyleClass().length() > 0 && (var9.getStyleToLine() == null || !var9.getStyleToLine())) {
                    var10 = var10 + " " + var9.getStyleClass();
                }

                this.b.print(" class=\"" + var10 + "\" ");
                if (var9.getNote() != null && var9.getNote()) {
                    this.b.print(" colspan=\"" + (this.d.getMaxLevel() + this.k.size()) + "\">");
                    this.b.print(var9.getDesc());
                    break;
                }

                if ((var9.getSubs() == null || var9.getSubs().size() == 0) && var9.getWidth() != null && var9.getWidth().length() > 0) {
                    this.b.print(" width='" + var9.getWidth() + "'");
                }

                if ((var9.getSubs() == null || var9.getSubs().size() == 0) && var9.getLevel() != this.d.getMaxLevel() - 1) {
                    this.b.print(" colspan = \"" + (this.d.getMaxLevel() - var9.getLevel()) + "\"");
                }

                this.b.print(">");
                this.b.print("<span class=\"s_rowDim\"");
                int var11 = CrossWriterService.substringCount(var9.getDesc().trim());
                if (var11 > 0) {
                    if (var9.getSpan() == 2 && var11 >= 2) {
                        var11 = 1;
                    }

                    int var12 = (var11 + 1) * 16;
                    this.b.print("style=\"height:" + (var12 + 5) + "px;\"");
                }

                this.b.print(" title=\"" + var9.getDesc() + "\">");
                if (!var9.getType().equalsIgnoreCase("none")) {
                    if (var9.getLevel() == this.d.getMaxLevel() - 1) {
                        if (this.p == 1) {
                            this.b.print("<a class=\"dimDrill\"  onclick=\"drillDim(" + this.n + ", this, 'row', '" + var9.getValue() + "','" + var9.getDesc() + "', '" + var9.getId() + "')\" style=\"opacity:0.5\">  </a>");
                        } else {
                            JSONObject var16 = this.a(var9.getId(), "row");
                            if ("false".equals(var16.get("endlvl"))) {
                                this.b.print("<a class=\"dimDrill\"  onclick=\"openTheDim(" + this.n + ",  'row', '" + var9.getValue() + "','" + var9.getDesc() + "', '" + var9.getId() + "')\" style=\"opacity:0.5\">  </a>");
                            }
                        }
                    } else {
                        this.b.print("<a class=\"dimgoup\" onclick=\"goupDim(" + this.n + ", this, 'row','" + var9.getId() + "', true)\" style=\"opacity:0.5\">  </a>");
                    }
                }

                String var17 = var9.getDesc();
                if (var9.getType().equals("frd") && "month".equalsIgnoreCase(var9.getDateType()) && var17 != null && var17.length() == 6) {
                    var17 = var17.substring(0, 4) + "-" + var17.substring(4, 6);
                }

                if (var9.getType().equals("frd") && "day".equalsIgnoreCase(var9.getDateType()) && var17 != null && var17.length() == 8) {
                    var17 = var17.substring(0, 4) + "-" + var17.substring(4, 6) + "-" + var17.substring(6, 8);
                }

                if (var9.getType().equals("frd") && "day".equalsIgnoreCase(var9.getDateType())) {
                    String var13 = CalendarTag.getFestival(var9.getValue(), var9.getDateTypeFmt());
                    if (var13 != null && var13.length() > 0) {
                        var17 = var13;
                    }

                    var9.setDesc(var17);
                }

                var17 = CrossWriterService.substringHeader(var17.trim(), 7, "html");
                this.b.print(var17);
                this.b.print("</span>");
                this.b.print("</th>");
                if (var9.getSubs() == null || var9.getSubs().size() == 0) {
                    var7 = var9;
                    break;
                }
            }

            if (var7 != null) {
                this.l.add(var7);
            }

            this.b.print("</tr>");
        }

        this.b.println("</table>");
    }

    public void writerFieldDirll() {
        if (this.a.getDims() != null && this.a.getDims().size() > 1) {
            MVContext var1 = (MVContext)this.i.get("mvInfo");
            if (var1 != null) {
                String var3 = this.a.getDataUrl();
                String var2;
                if (var3 != null && var3.length() != 0) {
                    var2 = var3 + "?";
                } else {
                    var2 = RuleUtils.getResPath(this.g);
                    var2 = var2 + "control/extControl?serviceid=ext.sys.cross.loadData&t_from_id=" + var1.getMvid() + "&reportId=" + this.a.getId() + "&";
                }

                Map var4 = this.e.getParams();
                StringBuffer var5 = new StringBuffer();
                if (var4 != null) {
                    Iterator var7 = var4.entrySet().iterator();

                    while(var7.hasNext()) {
                        Entry var6 = (Entry)var7.next();
                        String var8 = (String)var6.getKey();
                        if (!this.h.paramIsInDirllDim(var8)) {
                            Object var9 = var6.getValue();
                            if (var9 instanceof String) {
                                var5.append(var8);
                                var5.append("=");
                                var5.append(var9);
                                var5.append("&");
                            }
                        }
                    }
                }

                var2 = var2 + var5;
                this.b.println("<script>");
                this.b.print("jQuery(function(){fieldDirll({");
                this.b.print("table: '" + this.a.getId() + "', pid:'" + this.j + "', url:'" + var2 + "', text:" + this.g.getParameter("text"));
                this.b.print("})});");
                this.b.print("</script>");
            }
        }

    }

    public void writerKpi(CrossField var1, List var2, CrossField var3, int var4, int var5) {
        String var6 = var4 % 2 == 0 ? "kpiData1" : "kpiData2";
        if (var3.getStyleClass() != null && var3.getStyleClass().length() > 0 && var3.getStyleToLine() != null && var3.getStyleToLine()) {
            var6 = var6 + " " + var3.getStyleClass();
        }

        this.b.print("<td align='right' class='" + var6 + " grid5-td'>");
        List var7 = this.h.querySubData(var3, var2, 2, (String)null);
        CrossKpi var8 = this.h.currKpiNode;
        if (var8 == null && this.h.backKpiNode != null) {
            var8 = this.h.backKpiNode;
        }

        if (var8 == null) {
            var8 = this.h.baseKpiNode;
        }

        Double var9 = this.h.findKipValue(var7, var8, var3, var1, (String)null);
        CrossKpi var10 = this.h.currFormula;
        if (var10 != null && var10.getFormula() != null && var10.getFormula().length() > 0) {
            var9 = this.h.findFormulaValue(var9, var3, var1);
        }

        String var11;
        if (var8 != null && var8.getJsFunc() != null && var8.getJsFunc().length() > 0) {
            var11 = var8.getJsFunc();
            PageBuilder$JSObject var17 = (PageBuilder$JSObject)this.g.getAttribute("ext.script.engine");
            Object var20 = var17.getScope().get(var11, var17.getScope());
            if (var20 == null || !(var20 instanceof Function)) {
                String var21 = ConstantsEngine.replace("定义的 jsFunc 方法 $0 未找到.", var11);
                throw new ExtRuntimeException(var21);
            }

            Function var19 = (Function)var20;
            Object[] var22 = new Object[]{var9, var3, var1, var7, "html"};
            var19.call(var17.getCt(), var17.getScope(), var17.getScope(), var22);
        } else {
            var11 = "";
            this.b.print("<span class=\"kpiValue\"");
            if (var9 == null) {
                var11 = "-";
            } else {
                String var12 = this.h.getCurrKpiFmt(var8);
                double var13 = var9;
                if (var12 != null && var12.length() > 0) {
                    DecimalFormat var15 = new DecimalFormat(var12);
                    var11 = var15.format(var13);
                } else {
                    var11 = var9.toString();
                }

                var11 = this.writerFinanceFmt(var8, var13, var11);
            }

            this.b.print(">");
            StringBuffer var16 = new StringBuffer("");
            this.a(var3, var16);
            this.a(var1, var16);
            String var18 = var16.toString();
            if (var18 != null && var18.length() > 0) {
                var18 = var18.substring(0, var18.length() - 1);
            }

            int var14 = this.a(var1);
            if (var14 >= 1) {
                this.b.print("<div style=\"height:" + ((var14 + 1) * 16 - 5) + "px;margin-top:10px;\">");
                this.b.print("<a href='javascript:linkdetail({" + var18 + "});'>");
                this.b.print(var11);
                this.b.print("</a>");
                this.b.print("</div>");
            } else {
                this.b.print("<a href='javascript:linkdetail({" + var18 + "});'>");
                this.b.print(var11);
                this.b.print("</a>");
            }

            this.b.print("</span>");
        }

        if (this.h.whereIn == 2) {
            this.h.currKpiNode = null;
        }

        if (this.h.backKpiIn == 2) {
            this.h.backKpiNode = null;
        }

        if (this.h.formulaPos == 2) {
            this.h.currFormula = null;
        }

        this.b.print("</td>");
    }

    private int a(CrossField var1) {
        int var2 = CrossWriterService.substringCount(var1.getDesc().trim());
        CrossField var3 = var1.getParent();
        if (var3 != null) {
            int var4 = this.a(var3);
            if (var3.getSpan() == 1 && var4 > var2) {
                var2 = var4;
            }
        }

        return var2;
    }

    public String writerFinanceFmt(CrossKpi var1, double var2, String var4) {
        if (var1.isFinanceFmt()) {
            if (var2 > 0.0D) {
                var4 = "<font class='kpi_up'>" + var4 + "</font>";
            } else if (var2 < 0.0D) {
                var4 = "<font class='kpi_down'>" + var4 + "</font>";
            } else {
                var4 = "<font class='kpi_noChg'>" + var4 + "</font>";
            }
        }

        return var4;
    }

    @Override
    public void writerColLink() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (this.c.getColLink() != null) {
            this.b.println("<script language='javascript'>");
            String var2;
            if (this.c.getColLink().getMvId() != null && this.c.getColLink().getMvId().length() > 0) {
                String var9 = RuleUtils.getResPath(this.g) + "control/extView?mvid=" + this.c.getColLink().getMvId();
                if (this.c.getColLink().isUse()) {
                    this.b.println("tableCell2MV({id:'" + this.a.getId() + "', url:'" + var9 + "'})");
                } else {
                    var2 = RuleUtils.findCurMV(this.i).getMvid();
                    this.b.println("cellLinkNotUse({id:'" + this.a.getId() + "', url:'" + var9 + "'}, '" + var2 + "')");
                }
            } else {
                MVContext var1 = RuleUtils.findCurMV(this.i);
                var2 = RuleUtils.getResPath(this.g) + "control/extControl?serviceid=$0&" + "t_from_id" + "=" + var1.getMvid() + "&id=$1";
                String var3 = "";
                this.b.println("tableCellUpdateComp({id:'" + this.a.getId() + "', url:[");
                ColLinkContext var4 = this.c.getColLink();
                Object var5 = null;

                for(int var6 = 0; var4.getTarget() != null && var6 < var4.getTarget().length; ++var6) {
                    String var7 = var4.getTarget()[var6];
                    var5 = LabelUtils.findChartBylabel(var1, var7);
                    var3 = "ext.sys.chart.rebuild";
                    if (var5 == null) {
                        var5 = LabelUtils.findDataGridBylabel(var1, var7);
                        var3 = "ext.sys.fenye.ajax";
                    }

                    String var8;
                    if (var5 == null) {
                        var8 = ConstantsEngine.replace("配置的target $0 在文件 $1 (xml)中未指向正确的组件.", var7, var1.getMvid());
                        throw new ExtRuntimeException(var8);
                    }

                    var8 = (String)PropertyUtils.getProperty(var5, "id");
                    this.b.print("{url:'" + ConstantsEngine.replace(var2, var3, var8) + "'");
                    this.b.print(", target:'" + var8 + "'}");
                    if (var6 != var4.getTarget().length - 1) {
                        this.b.print(",");
                    }
                }

                this.b.println("]})");
            }

            this.b.println("</script>");
        }

    }

    @Override
    public void writerLink() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (this.d.getLink() != null) {
            String var1 = RuleUtils.getResPath(this.g) + "control/" + "extView" + "?" + "mvid" + "=" + this.d.getLink().getMvId() + "&" + "_showForm" + "=false&" + "returnJsp" + "=false";
            this.b.println("<script language='javascript'>");
            String var3;
            if (this.d.getLink().getMvId() != null && this.d.getLink().getMvId().length() > 0) {
                int var10 = this.k.size() + this.d.getMaxLevel();
                if (this.d.getLink().isUse()) {
                    this.b.println("rowLinkFireTR({id:'" + this.a.getId() + "', url:'" + var1 + "', type:'" + this.d.getLink().getType() + "', colspan:'" + var10 + "'});");
                } else {
                    var3 = RuleUtils.findCurMV(this.i).getMvid();
                    this.b.println("rowLinkNotUse({id:'" + this.a.getId() + "', url:'" + var1 + "', type:'" + this.d.getLink().getType() + "',colspan:'" + var10 + "', oldUrl:'" + this.d.getLink().getUrl() + "'},'" + var3 + "');");
                }
            } else {
                MVContext var2 = RuleUtils.findCurMV(this.i);
                var3 = RuleUtils.getResPath(this.g) + "control/extControl?serviceid=$0&" + "t_from_id" + "=" + var2.getMvid() + "&id=$1";
                String var4 = "";
                this.b.println("tableUpdateComp({id:'" + this.a.getId() + "', url:[");
                RowLinkContext var5 = this.d.getLink();
                Object var6 = null;

                for(int var7 = 0; var5.getTarget() != null && var7 < var5.getTarget().length; ++var7) {
                    String var8 = var5.getTarget()[var7];
                    var6 = LabelUtils.findChartBylabel(var2, var8);
                    var4 = "ext.sys.chart.rebuild";
                    if (var6 == null) {
                        var6 = LabelUtils.findDataGridBylabel(var2, var8);
                        var4 = "ext.sys.fenye.ajax";
                    }

                    String var9;
                    if (var6 == null) {
                        var9 = ConstantsEngine.replace("配置的target $0 在文件 $1 (xml)中未指向正确的组件.", var8, var2.getMvid());
                        throw new ExtRuntimeException(var9);
                    }

                    var9 = (String)PropertyUtils.getProperty(var6, "id");
                    this.b.print("{url:'" + ConstantsEngine.replace(var3, var4, var9) + "'");
                    this.b.print(", target:'" + var9 + "'}");
                    if (var7 != var5.getTarget().length - 1) {
                        this.b.print(",");
                    }
                }

                this.b.println("]})");
            }

            this.b.println("</script>");
        }

    }

    private void a(CrossField var1, StringBuffer var2) {
        if (var1.getType() != "none" && var1.getType() != "kpiOther") {
            var2.append("\"" + var1.getAlias() + "\":\"" + var1.getValue() + "\",");
        }

        if (var1.getParent() != null) {
            this.a(var1.getParent(), var2);
        }

    }
}
