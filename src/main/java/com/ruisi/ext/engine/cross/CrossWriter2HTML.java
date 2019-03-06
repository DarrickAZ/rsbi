//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.cross;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.util.DaoUtils;
import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.util.LabelUtils;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.builder.PageBuilder$JSObject;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.cross.ColLinkContext;
import com.ruisi.ext.engine.view.context.cross.CrossCols;
import com.ruisi.ext.engine.view.context.cross.CrossField;
import com.ruisi.ext.engine.view.context.cross.CrossFieldOther;
import com.ruisi.ext.engine.view.context.cross.CrossKpi;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.context.cross.CrossRows;
import com.ruisi.ext.engine.view.context.cross.RowDimContext;
import com.ruisi.ext.engine.view.context.cross.RowHeadContext;
import com.ruisi.ext.engine.view.context.cross.RowLinkContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtWriter;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.beanutils.PropertyUtils;
import org.mozilla.javascript.Function;

public class CrossWriter2HTML implements CrossWriter {
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
    private int k = 0;
    private List l = new ArrayList();

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

        var3 = this.l.iterator();

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
            this.b.println("new SortableTable(document.getElementById('T_" + this.a.getId() + "'),null,[" + var6 + "], " + "true".equalsIgnoreCase(this.a.getFirstSort()) + ");");
            this.b.println("});");
            this.b.println("</script>");
            return;
        }
    }

    @Override
    public void queryLastLevelCols() {
        this.h.setCrossFiledLastLevel(this.c.getCols(), this.c);
        CrossWriterService.findNodeByLastLevel(this.c.getMaxLevel() - 1, this.c.getCols(), this.l);
    }

    public CrossWriter2HTML(CrossBuilder var1, ExtWriter var2, ExtEnvirContext var3, InputOption var4) {
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
    }

    public void writeCols() {
        this.b.println("<thead>");

        for(int var1 = 0; var1 < this.c.getMaxLevel(); ++var1) {
            ArrayList var2 = new ArrayList();
            CrossWriterService.findNodeByLevel(var1, this.c.getCols(), var2);
            this.b.print("<tr class=\"scrollColThead\">");
            String var7;
            CrossField var8;
            if (var1 == 0) {
                if (this.d.getOverlapped() != null && this.d.getOverlapped()) {
                    var8 = this.h.getRow(this.a.getCrossRows().getTmpRows(), 0);
                    String var9 = var8.getDesc();
                    String var12 = var8.getStyleClass();
                    this.b.print("<th class=\"grid3-td " + var12 + "\" rowspan=\"" + this.c.getMaxLevel() + "\">" + var9 + " </th>");
                } else {
                    List var3 = this.a.getRowHeads();
                    int var4;
                    if (var3 != null) {
                        if (var3.size() == 1) {
                            this.b.print("<th id=\"xxhead\"  class=\"grid3-td" + (((RowHeadContext)var3.get(0)).getStyleClass() != null ? " " + ((RowHeadContext)var3.get(0)).getStyleClass() : "") + "\" colspan=\"" + this.d.getMaxLevel() + "\"  rowspan=\"" + this.c.getMaxLevel() + "\">");
                            if (var3 != null) {
                                this.b.print(((RowHeadContext)var3.get(0)).getDesc());
                            } else {
                                this.b.print("");
                            }

                            this.b.print("</th>");
                        } else {
                            for(var4 = 0; var4 < this.d.getMaxLevel(); ++var4) {
                                RowHeadContext var10 = (RowHeadContext)var3.get(var4);
                                this.b.print("<th class=\"grid3-td" + (var10.getStyleClass() == null ? "" : " " + var10.getStyleClass()) + "\"  rowspan=\"" + this.c.getMaxLevel() + "\">" + var10.getDesc() + " </th>");
                            }
                        }
                    } else {
                        for(var4 = 0; var4 < this.d.getMaxLevel(); ++var4) {
                            CrossField var5 = this.h.getRow(this.a.getCrossRows().getTmpRows(), var4);
                            String var6 = var5.getDesc();
                            var7 = var5.getStyleClass();
                            this.b.print("<th class=\"grid3-td crossHead" + var4 + (var7 != null && var7.length() > 0 ? " " + var7 : "") + "\"  rowspan=\"" + this.c.getMaxLevel() + "\">" + var6 + " </th>");
                        }
                    }
                }
            }

            for(Iterator var11 = var2.iterator(); var11.hasNext(); this.b.print("</th>")) {
                var8 = (CrossField)var11.next();
                int var13 = var8.getSpan();
                this.b.print("<th class=\"grid3-td" + (var8.getStyleClass() == null ? "" : " " + var8.getStyleClass()) + "\" colspan=\"" + var13 + "\"");
                int var14 = 0;
                if (var8.getSubs() != null) {
                    var14 = var8.getSubs().size();
                }

                if (var14 == 0) {
                    var8.setLastLevel(var8.getLevel());
                    if (var8.getWidth() != null && var8.getWidth().length() > 0) {
                        this.b.print(" width='" + var8.getWidth() + "' ");
                    }
                }

                if (var14 == 0 && var8.getLevel() != this.c.getMaxLevel() - 1) {
                    this.b.print(" rowspan = \"" + (this.c.getMaxLevel() - var8.getLevel()) + "\"");
                    var8.setLastLevel(this.c.getMaxLevel() - 1);
                }

                this.b.print(">");
                if (var8.getAlt() != null && var8.getAlt().length() > 0) {
                    var7 = var8.getAlt();
                }

                this.b.print(this.h.writerHeader(var8, "html"));
                if (var8.getAlt() != null && var8.getAlt().length() > 0) {
                    this.b.print("</p>");
                }
            }

            this.b.print("</tr>");
        }

        this.b.println("</thead>");
        CrossWriterService.findNodeByLastLevel(this.c.getMaxLevel() - 1, this.c.getCols(), this.l);
    }

    public void writeOverlappedRows(List var1) {
        for(int var2 = 0; var1 != null && var2 < var1.size(); ++var2) {
            CrossField var3 = (CrossField)var1.get(var2);
            String var4 = this.k % 2 == 0 ? "tr-row1" : "tr-row2";
            if (this.d.getLink() != null) {
                var4 = var4 + " row-link";
            }

            this.b.print("<tr  class=\"" + var4 + "\">");
            String var5 = "grid3-td";
            this.b.print("<td class=\"" + var5 + "\">");
            if (var3.getLevel() > 0) {
                this.b.print("<span style='display: inline-block;width:" + 40 * var3.getLevel() + "px;'>&nbsp;</span>");
            }

            this.b.print(this.h.writerHeader(var3, "html"));
            this.b.print("</td>");
            List var6 = this.h.querySubData(var3, this.f, 1, (String)null);

            for(int var7 = 0; var7 < this.l.size(); ++var7) {
                CrossField var8 = (CrossField)this.l.get(var7);
                this.writerKpi(var3, var6, var8, this.k, var7);
            }

            ++this.k;
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
            this.writeOverlappedRows(var3.getSubs());
        }

    }

    public void loopParent(CrossField var1) {
        this.b.print("<td class=\"grid3-td\">");
        this.b.print(this.h.writerHeader(var1, "html"));
        this.b.print("</td>");
        if (var1.getParent() != null) {
            this.loopParent(var1.getParent());
        }

    }

    public void writeRows() {
        if (this.d.getOverlapped() != null && this.d.getOverlapped()) {
            this.writeOverlappedRows(this.d.getRows());
        } else {
            ArrayList var1 = new ArrayList();
            CrossWriterService.loopRow(this.d.getRows(), var1);
            int var2 = 0;

            CrossField var3;
            for(Iterator var4 = this.d.getRows().iterator(); var4.hasNext(); var2 += var3.getSpan()) {
                var3 = (CrossField)var4.next();
            }

            boolean var24 = this.d.getUnmerge() != null && this.d.getUnmerge();
            int var25 = 0;

            for(int var5 = 0; var5 < var2; ++var5) {
                String var6 = var5 % 2 == 0 ? "tr-row1" : "tr-row2";
                if (this.d.getLink() != null) {
                    var6 = var6 + " row-link";
                }

                CrossField var7 = (CrossField)var1.get(var25);
                if (var7.getStyleToLine() != null && var7.getStyleToLine() && var7.getStyleClass() != null && var7.getStyleClass().length() > 0) {
                    var6 = var6 + " " + var7.getStyleClass();
                }

                this.b.print("<tr  class=\"" + var6 + "\">");
                CrossField var8 = null;

                int var9;
                for(var9 = 0; var9 < this.d.getMaxLevel(); ++var9) {
                    CrossField var10 = (CrossField)var1.get(var25);
                    ++var25;
                    if (var24 && var9 == 0 && var10.getParent() != null) {
                        this.loopParent(var10.getParent());
                    }

                    this.b.print("<td rowspan=\"" + (var24 ? "1" : var10.getSpan()) + "\"");
                    String var11 = "grid3-td";
                    if (var10.getStyleClass() != null && var10.getStyleClass().length() > 0 && (var10.getStyleToLine() == null || !var10.getStyleToLine())) {
                        var11 = var11 + " " + var10.getStyleClass();
                    }

                    this.b.print(" class=\"" + var11 + "\" ");
                    if (var10.getNote() != null && var10.getNote()) {
                        this.b.print(" colspan=\"" + (this.d.getMaxLevel() + this.l.size()) + "\">");
                        this.b.print(this.h.writerHeader(var10, "html"));
                        break;
                    }

                    if ((var10.getSubs() == null || var10.getSubs().size() == 0) && var10.getWidth() != null && var10.getWidth().length() > 0) {
                        this.b.print(" width='" + var10.getWidth() + "'");
                    }

                    if ((var10.getSubs() == null || var10.getSubs().size() == 0) && var10.getLevel() != this.d.getMaxLevel() - 1) {
                        this.b.print(" colspan = \"" + (var24 ? "1" : this.d.getMaxLevel() - var10.getLevel()) + "\"");
                    }

                    String var12 = CrossBuilder.getDrillDim(this.g);
                    if (this.a.getDims() != null && this.a.getDims().size() > 1) {
                        if (this.j == null) {
                            String var13 = this.g.getParameter("drillDimTId");
                            if (var13 == null) {
                                var13 = "T" + IdCreater.create();
                            }

                            this.j = var13;
                        }

                        this.b.print(" drillId='" + this.j + "' ");
                    }

                    this.b.print(">");
                    Integer var29 = var10.getSpaceNum();
                    if (var29 != null) {
                        for(int var14 = 0; var14 < var29; ++var14) {
                            this.b.print(" &nbsp; ");
                        }
                    }

                    String var30;
                    if (var10.getAlt() != null && var10.getAlt().length() > 0) {
                        var30 = var10.getAlt();
                    }

                    Iterator var18;
                    String var19;
                    String var21;
                    String var33;
                    RowDimContext var34;
                    if (this.a.getDims() != null && this.a.getDims().size() > 1) {
                        var30 = this.g.getParameter("dirrLevel");
                        if (var30 == null || var30.length() == 0) {
                            var30 = "0";
                        }

                        int var32 = Integer.parseInt(var30);
                        if (var32 > 0) {
                            this.b.println("<div style='width:" + var32 * 15 + "px' class=\"crossDirll-level\">&nbsp;</div>");
                        }

                        if (var32 + 1 != this.a.getDims().size()) {
                            var33 = "";
                            var18 = this.a.getDims().iterator();

                            while(var18.hasNext()) {
                                var34 = (RowDimContext)var18.next();
                                if (var34.getCode().equals(var12)) {
                                    break;
                                }

                                var19 = this.g.getParameter(var34.getCode());
                                if (var19 != null && var19.length() > 0) {
                                    var33 = var33 + var34.getCode() + "=" + var19 + "&";
                                }
                            }

                            var33 = var33 + var10.getAlias() + "=" + var10.getValue();
                            CrossFieldOther var37;
                            if (var10.getOther() != null) {
                                for(var18 = var10.getOther().iterator(); var18.hasNext(); var33 = var33 + "&" + var37.getAlias() + "=" + var37.getValue()) {
                                    var37 = (CrossFieldOther)var18.next();
                                }
                            }

                            var33 = var33 + "&drillDim=" + ((RowDimContext)this.a.getDims().get(var32 + 1)).getCode();
                            var33 = var33 + "&";
                            var33 = var33 + "drillDimTId=" + this.j + var10.getValue();
                            var33 = var33 + "&";
                            var33 = var33 + "dirrLevel=" + (var32 + 1);
                            boolean var38 = true;
                            String var39 = var10.getTestFunc();
                            if (var39 != null && var39.length() > 0) {
                                PageBuilder$JSObject var43 = (PageBuilder$JSObject)this.g.getAttribute("ext.script.engine");
                                Object var42 = var43.getScope().get(var39, var43.getScope());
                                if (var42 == null || !(var42 instanceof Function)) {
                                    var21 = ConstantsEngine.replace("定义的 testFunc 方法 $0 未找到.", var39);
                                    throw new ExtRuntimeException(var21);
                                }

                                Function var44 = (Function)var42;
                                Object[] var46 = new Object[]{var10};
                                Boolean var47 = (Boolean)var44.call(var43.getCt(), var43.getScope(), var43.getScope(), var46);
                                if (!var47) {
                                    var38 = false;
                                }
                            }

                            if (var38 && var10.getUselink() != null && var10.getUselink()) {
                                this.b.print("<a href=\"javascript:;\" pid=\"" + this.j + var10.getValue() + "\" class='crossDirll' parms='" + var33 + "' isopen='0'>" + this.h.writerHeader(var10, "html") + "</a>");
                            } else {
                                this.b.print(this.h.writerHeader(var10, "html"));
                            }
                        } else {
                            this.b.print(this.h.writerHeader(var10, "html"));
                        }
                    } else if (this.d.getLink() != null && var10.getUselink() != null && var10.getUselink()) {
                        RowLinkContext var31 = this.d.getLink();
                        String var15 = var10.getAlias() + "=" + var10.getValue();
                        CrossFieldOther var16;
                        if (var10.getOther() != null) {
                            for(Iterator var17 = var10.getOther().iterator(); var17.hasNext(); var15 = var15 + "&" + var16.getAlias() + "=" + var16.getValue()) {
                                var16 = (CrossFieldOther)var17.next();
                            }
                        }

                        var33 = "";
                        if (this.a.getDims() != null) {
                            var18 = this.a.getDims().iterator();

                            while(var18.hasNext()) {
                                var34 = (RowDimContext)var18.next();
                                var19 = this.g.getParameter(var34.getCode());
                                if (var19 != null && var19.length() > 0) {
                                    var33 = var33 + var34.getCode() + "=" + var19 + "&";
                                }
                            }
                        }

                        Map var35 = this.e.getParams();
                        StringBuffer var36 = new StringBuffer();
                        if (var35 != null) {
                            Iterator var20 = var35.entrySet().iterator();

                            label279:
                            while(true) {
                                Entry var40;
                                do {
                                    if (!var20.hasNext()) {
                                        break label279;
                                    }

                                    var40 = (Entry)var20.next();
                                    var21 = (String)var40.getKey();
                                } while(var21.equals(var10.getAlias()));

                                if (var10.getOther() != null) {
                                    Iterator var23 = var10.getOther().iterator();

                                    while(var23.hasNext()) {
                                        CrossFieldOther var22 = (CrossFieldOther)var23.next();
                                        if (var21.equals(var22.getAlias())) {
                                        }
                                    }
                                }

                                Object var45 = var40.getValue();
                                if (var45 instanceof String) {
                                    var36.append(var21);
                                    var36.append("=");
                                    var36.append(var45);
                                    var36.append("&");
                                }
                            }
                        }

                        if (var31.getMvId() != null && var31.getMvId().length() > 0) {
                            this.b.print("<a href='javascript:;' parms='" + var36 + var15 + "' class='lka'>" + this.h.writerHeader(var10, "html") + "</a>");
                        } else if (var31.getUrl() != null && var31.getUrl().length() > 0) {
                            var19 = var31.getUrl();
                            this.b.print("<a target='_blank' href='" + (var19.startsWith("http://") ? "" : "http://") + var19 + (var19.indexOf(63) > 0 ? "&" : "?") + var36 + var15 + "'>" + this.h.writerHeader(var10, "html") + "</a>");
                        } else {
                            this.b.print("<a href='javascript:;' class='lka' ");

                            for(int var41 = 0; var31.getTarget() != null && var41 < var31.getTarget().length; ++var41) {
                                this.b.print("parms" + var41 + "=\"" + var36 + var33 + var15 + "\"");
                            }

                            this.b.print(">");
                            this.b.print(this.h.writerHeader(var10, "html") + "</a>");
                        }
                    } else {
                        this.b.print(this.h.writerHeader(var10, "html"));
                    }

                    if (var10.getAlt() != null && var10.getAlt().length() > 0) {
                        this.b.print("</p>");
                    }

                    this.b.print("</td>");
                    if (var10.getSubs() == null || var10.getSubs().size() == 0) {
                        var8 = var10;
                        break;
                    }
                }

                if (var8 != null) {
                    if (this.a.isShowData()) {
                        List var26 = this.h.querySubData(var8, this.f, 1, (String)null);

                        for(int var27 = 0; var27 < this.l.size(); ++var27) {
                            CrossField var28 = (CrossField)this.l.get(var27);
                            this.writerKpi(var8, var26, var28, var5, var27);
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
                    } else {
                        for(var9 = 0; var9 < this.l.size(); ++var9) {
                            this.b.print("<td>&nbsp;</td>");
                        }
                    }
                } else {
                    this.b.print("</td>");
                }

                this.b.print("</tr>");
            }

        }
    }

    @Override
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

    @Override
    public void writerKpi(CrossField var1, List var2, CrossField var3, int var4, int var5) {
        List var6 = this.h.querySubData(var3, var2, 2, (String)null);
        CrossKpi var7 = this.h.currKpiNode;
        if (var7 == null && this.h.backKpiNode != null) {
            var7 = this.h.backKpiNode;
        }

        if (var7 == null) {
            var7 = this.h.baseKpiNode;
        } else if ("kpi".equals(var7.getType()) && this.h.baseKpiNode != null) {
            var7 = this.h.baseKpiNode;
        }

        String var8 = var4 % 2 == 0 ? "kpiData1" : "kpiData2";
        if (var3.getStyleClass() != null && var3.getStyleClass().length() > 0 && var3.getStyleToLine() != null && var3.getStyleToLine()) {
            var8 = var8 + " " + var3.getStyleClass();
        }

        if (var3.getDataClass() != null && var3.getDataClass().length() > 0) {
            var8 = var8 + " " + var3.getDataClass();
        } else if (var7 != null && var7.getDataClass() != null && var7.getDataClass().length() > 0) {
            var8 = var8 + " " + var7.getDataClass();
        }

        this.b.print("<td align='right' class='" + var8 + " grid3-td'>");
        Double var9 = this.h.findKipValue(var6, var7, var3, var1, (String)null);
        CrossKpi var10 = this.h.currFormula;
        if (var10 != null && var10.getFormula() != null && var10.getFormula().length() > 0) {
            var9 = this.h.findFormulaValue(var9, var3, var1);
        }

        String var11;
        if (var7 != null && var7.getJsFunc() != null && var7.getJsFunc().length() > 0) {
            var11 = var7.getJsFunc();
            PageBuilder$JSObject var20 = (PageBuilder$JSObject)this.g.getAttribute("ext.script.engine");
            Object var22 = var20.getScope().get(var11, var20.getScope());
            if (var22 == null || !(var22 instanceof Function)) {
                String var24 = ConstantsEngine.replace("定义的 jsFunc 方法 $0 未找到.", var11);
                throw new ExtRuntimeException(var24);
            }

            Function var23 = (Function)var22;
            Object[] var29 = new Object[]{var9, var3, var1, var6, "html"};
            var23.call(var20.getCt(), var20.getScope(), var20.getScope(), var29);
        } else {
            var11 = "";
            if (var9 == null) {
                var11 = "-";
            } else {
                String var12 = this.h.getCurrKpiFmt(var7);
                double var13 = var9;
                DecimalFormat var15;
                if (var12 != null && var12.length() > 0) {
                    var15 = new DecimalFormat(var12);
                    var11 = var15.format(var13);
                } else if (var13 > 0.0D) {
                    var15 = new DecimalFormat("0");
                    var11 = var15.format(var13);
                } else {
                    var15 = new DecimalFormat("0.###");
                    var11 = var15.format(var13);
                }

                var11 = this.writerFinanceFmt(var7, var13, var11);
            }

            ColLinkContext var19 = this.c.getColLink();
            if (var3.getUselink() != null && var3.getUselink() && var19 != null) {
                Map var21 = this.e.getParams();
                StringBuffer var14 = new StringBuffer();
                String var17;
                if (var21 != null) {
                    Iterator var16 = var21.entrySet().iterator();

                    while(var16.hasNext()) {
                        Entry var25 = (Entry)var16.next();
                        var17 = (String)var25.getKey();
                        if (!var17.equals(var1.getAlias()) && !var17.equals(var3.getAlias())) {
                            Object var18 = var25.getValue();
                            if (var18 instanceof String) {
                                var14.append(var17);
                                var14.append("=");
                                var14.append(var18);
                                var14.append("&");
                            }
                        }
                    }
                }

                String var26 = var1.getAlias() + "=" + var1.getValue();
                CrossFieldOther var27;
                if (var1.getOther() != null) {
                    for(Iterator var30 = var1.getOther().iterator(); var30.hasNext(); var26 = var26 + "&" + var27.getAlias() + "=" + var27.getValue()) {
                        var27 = (CrossFieldOther)var30.next();
                    }
                }

                StringBuffer var28 = new StringBuffer();
                CrossWriterService.loopColGetParams(var28, var3);
                if (var19.getMvId() != null && var19.getMvId().length() > 0) {
                    this.b.print("<a href='javascript:;' class='cell-link' parms='" + var14 + var26 + var28 + "'>");
                    this.b.print(var11);
                    this.b.print("</a>");
                } else if (var19.getUrl() != null && var19.getUrl().length() > 0) {
                    var17 = var19.getUrl();
                    this.b.print("<a href='" + var17 + (var17.indexOf(63) >= 0 ? "&" : "?") + var14 + var26 + var28 + "'>");
                    this.b.print(var11);
                    this.b.print("</a>");
                } else if (var19.getTarget() != null && var19.getTarget().length > 0) {
                    this.b.print("<a href='javascript:;' class='cell-link' ");

                    for(int var31 = 0; var19.getTarget() != null && var31 < var19.getTarget().length; ++var31) {
                        this.b.print("parms=\"" + var14 + var26 + var28 + "\"");
                    }

                    this.b.print(">");
                    this.b.print(var11);
                    this.b.print("</a>");
                }
            } else {
                this.b.print(var11);
            }
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
            if (this.c.getColLink().getMvId() != null && this.c.getColLink().getMvId().length() > 0) {
                String var9 = RuleUtils.getResPath(this.g) + "control/extView?mvid=" + this.c.getColLink().getMvId();
                if (this.c.getColLink().isUse()) {
                    this.b.println("tableCell2MV({id:'" + this.a.getId() + "', url:'" + var9 + "'})");
                } else {
                    String var10 = RuleUtils.findCurMV(this.i).getMvid();
                    this.b.println("cellLinkNotUse({id:'" + this.a.getId() + "', url:'" + var9 + "'}, '" + var10 + "')");
                }
            } else {
                MVContext var1 = RuleUtils.findCurMV(this.i);
                this.b.println("tableCellUpdateComp({id:'" + this.a.getId() + "', url:[");
                ColLinkContext var2 = this.c.getColLink();
                Object var3 = null;

                for(int var4 = 0; var2.getTarget() != null && var4 < var2.getTarget().length; ++var4) {
                    String var5 = var2.getTarget()[var4];
                    Object var6 = null;
                    try {
                        var6 = LabelUtils.findObjectByLabel(var1, var5, var2.getType()[var4]);
                    } catch (ExtConfigException e1) {
                        e1.printStackTrace();
                    }
                    if (var6 != null) {
                        String var7 = RuleUtils.getResPath(this.g) + "control/extControl?serviceid=$0&" + "t_from_id" + "=" + var1.getMvid() + "&id=$1";
                        String var8 = (String)PropertyUtils.getProperty(var6, "id");
                        try {
                            var7 = ConstantsEngine.replace(var7, LabelUtils.findServiceIdByType(var2.getType()[var4]), var8);
                        } catch (ExtConfigException e1) {
                            e1.printStackTrace();
                        }
                        this.b.print("{url:'" + var7 + "'");
                        this.b.print(", target:'" + var8 + "', type:'" + var2.getType()[var4] + "'}");
                        if (var4 != var2.getTarget().length - 1) {
                            this.b.print(",");
                        }
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
            if (this.d.getLink().getMvId() != null && this.d.getLink().getMvId().length() > 0) {
                int var10 = this.l.size() + this.d.getMaxLevel();
                if (this.d.getLink().isUse()) {
                    this.b.println("rowLinkFireTR({id:'" + this.a.getId() + "', url:'" + var1 + "', type:'" + this.d.getLink().getType() + "', colspan:'" + var10 + "'});");
                }
            } else if (this.d.getLink().getUrl() != null && this.d.getLink().getUrl().length() > 0) {
                String var9 = this.d.getLink().getUrl();
            } else {
                MVContext var2 = RuleUtils.findCurMV(this.i);
                this.b.println("tableUpdateComp({id:'" + this.a.getId() + "', url:[");
                RowLinkContext var3 = this.d.getLink();
                Object var4 = null;

                for(int var5 = 0; var3.getTarget() != null && var5 < var3.getTarget().length; ++var5) {
                    String var6 = var3.getTarget()[var5];
                    try {
                        var4 = LabelUtils.findObjectByLabel(var2, var6, var3.getType()[var5]);
                    } catch (ExtConfigException e1) {
                        e1.printStackTrace();
                    }
                    if (var4 != null) {
                        String var7 = RuleUtils.getResPath(this.g) + "control/extControl?serviceid=$0&" + "t_from_id" + "=" + var2.getMvid() + "&id=$1";
                        String var8 = (String)PropertyUtils.getProperty(var4, "id");
                        try {
                            var7 = ConstantsEngine.replace(var7, LabelUtils.findServiceIdByType(var3.getType()[var5]), var8);
                        } catch (ExtConfigException e1) {
                            e1.printStackTrace();
                        }
                        this.b.print("{url:'" + var7 + "',");
                        this.b.print("type:'" + var3.getType()[var5] + "',");
                        this.b.print("target:'" + var8 + "'}");
                    } else {
                        this.b.print("{url:null");
                        this.b.print(", target:null}");
                    }

                    if (var5 != var3.getTarget().length - 1) {
                        this.b.print(",");
                    }
                }

                this.b.println("]})");
            }

            this.b.println("</script>");
        }

    }
}
