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

public class CrossWriter2LockUI implements CrossWriter {
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
            this.b.println("new SortableTable(document.getElementById('head-" + this.a.getId() + "'),document.getElementById('body-" + this.a.getId() + "'),[" + var6 + "], " + "true".equalsIgnoreCase(this.a.getFirstSort()) + ");");
            this.b.println("});");
            this.b.println("</script>");
            return;
        }
    }

    @Override
    public void writerFieldFmts() {
    }

    @Override
    public void queryLastLevelCols() {
        this.h.setCrossFiledLastLevel(this.c.getCols(), this.c);
        CrossWriterService.findNodeByLastLevel(this.c.getMaxLevel() - 1, this.c.getCols(), this.k);
    }

    public CrossWriter2LockUI(CrossBuilder var1, ExtWriter var2, ExtEnvirContext var3, InputOption var4) {
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

    public void writeAll() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, ExtConfigException {
        String var1 = (String)this.g.getAttribute("ext_fromAjax");
        if ("true".equals(var1) && this.g.getParameter("currPage") == null) {
            this.queryLastLevelCols();
            this.writeRows();
            this.writerFieldDirll();
        } else {
            String var2 = this.a.getWidth();
            this.b.print("<div id=\"" + this.a.getId() + "\" class=\"lock-dg\" style=\"" + (var2 != null && var2.length() > 0 ? "width:" + var2 + "px;" : "") + "\">");
            this.b.print("<div class=\"lock-dg-header\">");
            this.b.println("<table class=\"lockgrid " + this.a.getStyle() + "\" id=\"head-" + this.a.getId() + "\" cellpadding=\"0\" cellspacing=\"0\">");
            this.writeCols();
            this.b.print("</table>");
            this.b.println("</div>");
            String var3 = this.a.getHeight();
            this.b.print("<div class=\"lock-dg-body " + this.a.getStyle() + "\" style=\"" + (var3 != null && var3.length() > 0 ? "height:" + var3 + "px;" : "") + "\">");
            this.b.println("<table class=\"lockgrid " + this.a.getStyle() + "\" id=\"body-" + this.a.getId() + "\" cellpadding=\"0\" cellspacing=\"0\">");
            this.writeRows();
            this.b.print("</table>");
            this.b.println("</div>");
            this.b.print("</div>");
            this.a();
            this.writerSort();
            this.writerFieldDirll();
            this.writerLink();
        }

    }

    private void a() {
        this.b.println("<script language=\"javascript\">");
        this.b.println("$(function(){");
        this.b.println("tableBodyscroll(\"" + this.a.getId() + "\");");
        this.b.println("})");
        this.b.println("</script>");
    }

    @Override
    public void writeCols() {
        this.b.println("<thead>");

        for(int var1 = 0; var1 < this.c.getMaxLevel(); ++var1) {
            ArrayList var2 = new ArrayList();
            CrossWriterService.findNodeByLevel(var1, this.c.getCols(), var2);
            this.b.print("<tr class=\"scrollColThead\">");
            String var7;
            if (var1 == 0) {
                List var3 = this.a.getRowHeads();
                int var4;
                String var6;
                if (var3 == null) {
                    for(var4 = 0; var4 < this.d.getMaxLevel(); ++var4) {
                        CrossField var11 = this.h.getRow(this.a.getCrossRows().getTmpRows(), var4);
                        var6 = var11.getDesc();
                        var7 = var11.getStyleClass();
                        String var8 = var11.getWidth();
                        this.b.print("<th class=\"grid3-td crossHead" + var4 + (var7 != null && var7.length() > 0 ? " " + var7 : "") + "\"  rowspan=\"" + this.c.getMaxLevel() + "\">");
                        this.b.print("<div class=\"dg-cell\" " + (var8 != null && var8.length() != 0 ? "style=\"width:" + var8 + "px;\"" : "") + " >" + var6 + "</div>");
                        this.b.print("</th>");
                    }
                } else if (var3.size() == 1) {
                    String var10 = ((RowHeadContext)var3.get(0)).getWidth();
                    this.b.print("<th id=\"xxhead\"  class=\"grid3-td" + (((RowHeadContext)var3.get(0)).getStyleClass() != null ? " " + ((RowHeadContext)var3.get(0)).getStyleClass() : "") + "\" colspan=\"" + this.d.getMaxLevel() + "\"  rowspan=\"" + this.c.getMaxLevel() + "\"><div style=\"" + (var10 != null && var10.length() != 0 ? "width:" + var10 + "px;" : "") + "\" class=\"dg-cell\">");
                    this.b.print(((RowHeadContext)var3.get(0)).getDesc());
                    this.b.print("</div></th>");
                } else {
                    for(var4 = 0; var4 < this.d.getMaxLevel(); ++var4) {
                        RowHeadContext var5 = (RowHeadContext)var3.get(var4);
                        var6 = var5.getWidth();
                        this.b.print("<th style=\"" + (var6 != null && var6.length() != 0 ? "width:" + var6 + "px;" : "") + "\" class=\"grid3-td" + (var5.getStyleClass() == null ? "" : " " + var5.getStyleClass()) + "\"  rowspan=\"" + this.c.getMaxLevel() + "\"><div class=\"dg-cell\">" + var5.getDesc() + "</div></th>");
                    }
                }
            }

            for(Iterator var12 = var2.iterator(); var12.hasNext(); this.b.print("</th>")) {
                CrossField var9 = (CrossField)var12.next();
                int var13 = var9.getSpan();
                this.b.print("<th class=\"grid3-td" + (var9.getStyleClass() == null ? "" : " " + var9.getStyleClass()) + "\" colspan=\"" + var13 + "\"");
                int var14 = 0;
                if (var9.getSubs() != null) {
                    var14 = var9.getSubs().size();
                }

                if (var14 == 0) {
                    var9.setLastLevel(var9.getLevel());
                }

                if (var14 == 0 && var9.getLevel() != this.c.getMaxLevel() - 1) {
                    this.b.print(" rowspan = \"" + (this.c.getMaxLevel() - var9.getLevel()) + "\"");
                    var9.setLastLevel(this.c.getMaxLevel() - 1);
                }

                this.b.print(">");
                if (var9.getAlt() != null && var9.getAlt().length() > 0) {
                    var7 = var9.getAlt();
                }

                if (var14 == 0) {
                    var7 = var9.getWidth();
                    this.b.print("<div style=\"" + (var7 != null && var7.length() > 0 ? "width:" + var7 + "px;" : "") + "\" class=\"dg-cell\">");
                }

                this.b.print(this.h.writerHeader(var9, "html"));
                if (var14 == 0) {
                    this.b.print("</div>");
                }

                if (var9.getAlt() != null && var9.getAlt().length() > 0) {
                    this.b.print("</p>");
                }
            }

            this.b.print("</tr>");
        }

        this.b.println("</thead>");
        CrossWriterService.findNodeByLastLevel(this.c.getMaxLevel() - 1, this.c.getCols(), this.k);
    }

    public void loopParent(CrossField var1) {
        this.b.print("<td class=\"lockgrid-td\">");
        String var2 = var1.getWidth();
        this.b.print("<div style=\"" + (var2 != null && var2.length() != 0 ? "width:" + var2 + "px;" : "") + "\"  class=\"dg-cell\">");
        this.b.print(this.h.writerHeader(var1, "html"));
        this.b.print("</div>");
        this.b.print("</td>");
        if (var1.getParent() != null) {
            this.loopParent(var1.getParent());
        }

    }

    public void writeRows() {
        ArrayList var1 = new ArrayList();
        CrossWriterService.loopRow(this.d.getRows(), var1);
        int var2 = 0;

        CrossField var3;
        for(Iterator var4 = this.d.getRows().iterator(); var4.hasNext(); var2 += var3.getSpan()) {
            var3 = (CrossField)var4.next();
        }

        boolean var25 = this.d.getUnmerge() != null && this.d.getUnmerge();
        int var26 = 0;

        for(int var5 = 0; var5 < var2; ++var5) {
            String var6 = var5 % 2 == 0 ? "tr-row1" : "tr-row2";
            if (this.d.getLink() != null) {
                var6 = var6 + " row-link";
            }

            CrossField var7 = (CrossField)var1.get(var26);
            if (var7.getStyleToLine() != null && var7.getStyleToLine() && var7.getStyleClass() != null && var7.getStyleClass().length() > 0) {
                var6 = var6 + " " + var7.getStyleClass();
            }

            this.b.print("<tr  class=\"" + var6 + "\">");
            CrossField var8 = null;

            int var9;
            for(var9 = 0; var9 < this.d.getMaxLevel(); ++var9) {
                CrossField var10 = (CrossField)var1.get(var26);
                ++var26;
                if (var25 && var9 == 0 && var10.getParent() != null) {
                    this.loopParent(var10.getParent());
                }

                this.b.print("<td rowspan=\"" + (var25 ? "1" : var10.getSpan()) + "\"");
                String var11 = "lockgrid-td";
                if (var10.getStyleClass() != null && var10.getStyleClass().length() > 0 && (var10.getStyleToLine() == null || !var10.getStyleToLine())) {
                    var11 = var11 + " " + var10.getStyleClass();
                }

                this.b.print(" class=\"" + var11 + "\" ");
                if (var10.getNote() != null && var10.getNote()) {
                    this.b.print(" colspan=\"" + (this.d.getMaxLevel() + this.k.size()) + "\">");
                    this.b.print(this.h.writerHeader(var10, "html"));
                    break;
                }

                if ((var10.getSubs() == null || var10.getSubs().size() == 0) && var10.getLevel() != this.d.getMaxLevel() - 1) {
                    this.b.print(" colspan = \"" + (var25 ? "1" : this.d.getMaxLevel() - var10.getLevel()) + "\"");
                }

                String var12 = CrossBuilder.getDrillDim(this.g);
                String var13;
                if (this.a.getDims() != null && this.a.getDims().size() > 1) {
                    if (this.j == null) {
                        var13 = this.g.getParameter("drillDimTId");
                        if (var13 == null) {
                            var13 = "T" + IdCreater.create();
                        }

                        this.j = var13;
                    }

                    this.b.print(" drillId='" + this.j + "' ");
                }

                this.b.print(">");
                var13 = var10.getWidth();
                this.b.print("<div style=\"" + (var13 != null && var13.length() != 0 ? "width:" + var13 + "px;" : "") + "\"  class=\"dg-cell\">");
                Integer var14 = var10.getSpaceNum();
                if (var14 != null) {
                    for(int var15 = 0; var15 < var14; ++var15) {
                        this.b.print(" &nbsp; ");
                    }
                }

                String var30;
                if (var10.getAlt() != null && var10.getAlt().length() > 0) {
                    var30 = var10.getAlt();
                }

                Iterator var19;
                String var20;
                String var22;
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
                        var19 = this.a.getDims().iterator();

                        while(var19.hasNext()) {
                            var34 = (RowDimContext)var19.next();
                            if (var34.getCode().equals(var12)) {
                                break;
                            }

                            var20 = this.g.getParameter(var34.getCode());
                            if (var20 != null && var20.length() > 0) {
                                var33 = var33 + var34.getCode() + "=" + var20 + "&";
                            }
                        }

                        var33 = var33 + var10.getAlias() + "=" + var10.getValue();
                        CrossFieldOther var37;
                        if (var10.getOther() != null) {
                            for(var19 = var10.getOther().iterator(); var19.hasNext(); var33 = var33 + "&" + var37.getAlias() + "=" + var37.getValue()) {
                                var37 = (CrossFieldOther)var19.next();
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
                                var22 = ConstantsEngine.replace("定义的 testFunc 方法 $0 未找到.", var39);
                                throw new ExtRuntimeException(var22);
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
                    String var16 = (var31.getParamName() == null ? var10.getAlias() : var31.getParamName()) + "=" + var10.getValue();
                    CrossFieldOther var17;
                    if (var10.getOther() != null) {
                        for(Iterator var18 = var10.getOther().iterator(); var18.hasNext(); var16 = var16 + "&" + var17.getAlias() + "=" + var17.getValue()) {
                            var17 = (CrossFieldOther)var18.next();
                        }
                    }

                    var33 = "";
                    if (this.a.getDims() != null) {
                        var19 = this.a.getDims().iterator();

                        while(var19.hasNext()) {
                            var34 = (RowDimContext)var19.next();
                            var20 = this.g.getParameter(var34.getCode());
                            if (var20 != null && var20.length() > 0) {
                                var33 = var33 + var34.getCode() + "=" + var20 + "&";
                            }
                        }
                    }

                    Map var35 = this.e.getParams();
                    StringBuffer var36 = new StringBuffer();
                    if (var35 != null) {
                        Iterator var21 = var35.entrySet().iterator();

                        label290:
                        while(true) {
                            Entry var40;
                            do {
                                do {
                                    if (!var21.hasNext()) {
                                        break label290;
                                    }

                                    var40 = (Entry)var21.next();
                                    var22 = (String)var40.getKey();
                                } while(var22.equals(var10.getAlias()));
                            } while(var22.equals(var31.getParamName()));

                            if (var10.getOther() != null) {
                                Iterator var24 = var10.getOther().iterator();

                                while(var24.hasNext()) {
                                    CrossFieldOther var23 = (CrossFieldOther)var24.next();
                                    if (var22.equals(var23.getAlias())) {
                                    }
                                }
                            }

                            Object var45 = var40.getValue();
                            if (var45 instanceof String) {
                                var36.append(var22);
                                var36.append("=");
                                var36.append(var45);
                                var36.append("&");
                            }
                        }
                    }

                    if (var31.getMvId() != null && var31.getMvId().length() > 0) {
                        this.b.print("<a href='javascript:;' parms='" + var36 + var16 + "' class='lka'>" + this.h.writerHeader(var10, "html") + "</a>");
                    } else if (var31.getUrl() != null && var31.getUrl().length() > 0) {
                        var20 = var31.getUrl();
                        this.b.print("<a target='_blank' href='" + (!var20.startsWith("http://") && !var20.startsWith("https://") ? "http://" : "") + var20 + (var20.indexOf(63) > 0 ? "&" : "?") + var36 + var16 + "'>" + this.h.writerHeader(var10, "html") + "</a>");
                    } else if (var31.getAction() != null && var31.getAction().length() > 0) {
                        this.b.print("<a href='javascript:;' name='" + var10.getAlias() + "' nameDesc='" + var10.getHeader() + "' value='" + var10.getValue() + "' parms='" + var36 + var16 + "' class='lka'>" + this.h.writerHeader(var10, "html") + "</a>");
                    } else {
                        this.b.print("<a href='javascript:;' class='lka' ");

                        for(int var41 = 0; var31.getTarget() != null && var41 < var31.getTarget().length; ++var41) {
                            this.b.print("parms" + var41 + "=\"" + var36 + var33 + var16 + "\"");
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

                this.b.print("</div>");
                this.b.print("</td>");
                if (var10.getSubs() == null || var10.getSubs().size() == 0) {
                    var8 = var10;
                    break;
                }
            }

            if (var8 != null) {
                if (this.a.isShowData()) {
                    List var27 = this.h.querySubData(var8, this.f, 1, (String)null);

                    for(int var28 = 0; var28 < this.k.size(); ++var28) {
                        CrossField var29 = (CrossField)this.k.get(var28);
                        this.writerKpi(var8, var27, var29, var5, var28);
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
                    for(var9 = 0; var9 < this.k.size(); ++var9) {
                        this.b.print("<td>&nbsp;</td>");
                    }
                }
            } else {
                this.b.print("</td>");
            }

            this.b.print("</tr>");
        }

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

        this.b.print("<td align='right' class='" + var8 + " lockgrid-td'>");
        String var9 = var3.getWidth();
        this.b.print("<div style=\"" + (var9 != null && var9.length() > 0 ? "width:" + var9 + "px;" : "") + "\" class=\"dg-cell\">");
        Double var10 = this.h.findKipValue(var6, var7, var3, var1, (String)null);
        CrossKpi var11 = this.h.currFormula;
        if (var11 != null && var11.getFormula() != null && var11.getFormula().length() > 0) {
            var10 = this.h.findFormulaValue(var10, var3, var1);
        }

        String var12;
        if (var7 != null && var7.getJsFunc() != null && var7.getJsFunc().length() > 0) {
            var12 = var7.getJsFunc();
            PageBuilder$JSObject var21 = (PageBuilder$JSObject)this.g.getAttribute("ext.script.engine");
            Object var23 = var21.getScope().get(var12, var21.getScope());
            if (var23 == null || !(var23 instanceof Function)) {
                String var25 = ConstantsEngine.replace("定义的 jsFunc 方法 $0 未找到.", var12);
                throw new ExtRuntimeException(var25);
            }

            Function var24 = (Function)var23;
            Object[] var30 = new Object[]{var10, var3, var1, var6, "html"};
            var24.call(var21.getCt(), var21.getScope(), var21.getScope(), var30);
        } else {
            var12 = "";
            if (var10 == null) {
                var12 = "-";
            } else {
                String var13 = this.h.getCurrKpiFmt(var7);
                double var14 = var10;
                DecimalFormat var16;
                if (var13 != null && var13.length() > 0) {
                    var16 = new DecimalFormat(var13);
                    var12 = var16.format(var14);
                } else if (var14 > 0.0D) {
                    var16 = new DecimalFormat("0");
                    var12 = var16.format(var14);
                } else {
                    var16 = new DecimalFormat("0.###");
                    var12 = var16.format(var14);
                }

                var12 = this.writerFinanceFmt(var7, var14, var12);
            }

            ColLinkContext var20 = this.c.getColLink();
            if (var3.getUselink() != null && var3.getUselink() && var20 != null) {
                Map var22 = this.e.getParams();
                StringBuffer var15 = new StringBuffer();
                String var18;
                if (var22 != null) {
                    Iterator var17 = var22.entrySet().iterator();

                    while(var17.hasNext()) {
                        Entry var26 = (Entry)var17.next();
                        var18 = (String)var26.getKey();
                        if (!var18.equals(var1.getAlias()) && !var18.equals(var3.getAlias())) {
                            Object var19 = var26.getValue();
                            if (var19 instanceof String) {
                                var15.append(var18);
                                var15.append("=");
                                var15.append(var19);
                                var15.append("&");
                            }
                        }
                    }
                }

                String var27 = var1.getAlias() + "=" + var1.getValue();
                CrossFieldOther var28;
                if (var1.getOther() != null) {
                    for(Iterator var31 = var1.getOther().iterator(); var31.hasNext(); var27 = var27 + "&" + var28.getAlias() + "=" + var28.getValue()) {
                        var28 = (CrossFieldOther)var31.next();
                    }
                }

                StringBuffer var29 = new StringBuffer();
                CrossWriterService.loopColGetParams(var29, var3);
                if (var20.getMvId() != null && var20.getMvId().length() > 0) {
                    this.b.print("<a href='javascript:;' class='cell-link' parms='" + var15 + var27 + var29 + "'>");
                    this.b.print(var12);
                    this.b.print("</a>");
                } else if (var20.getUrl() != null && var20.getUrl().length() > 0) {
                    var18 = var20.getUrl();
                    this.b.print("<a href='" + var18 + (var18.indexOf(63) >= 0 ? "&" : "?") + var15 + var27 + var29 + "'>");
                    this.b.print(var12);
                    this.b.print("</a>");
                } else if (var20.getTarget() != null && var20.getTarget().length > 0) {
                    this.b.print("<a href='javascript:;' class='cell-link' ");

                    for(int var32 = 0; var20.getTarget() != null && var32 < var20.getTarget().length; ++var32) {
                        this.b.print("parms=\"" + var15 + var27 + var29 + "\"");
                    }

                    this.b.print(">");
                    this.b.print(var12);
                    this.b.print("</a>");
                }
            } else {
                this.b.print(var12);
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

        this.b.print("</div>");
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
    public void writerColLink() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, ExtConfigException {
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
                    Object var6 = LabelUtils.findObjectByLabel(var1, var5, var2.getType()[var4]);
                    if (var6 != null) {
                        String var7 = RuleUtils.getResPath(this.g) + "control/extControl?serviceid=$0&" + "t_from_id" + "=" + var1.getMvid() + "&id=$1";
                        String var8 = (String)PropertyUtils.getProperty(var6, "id");
                        var7 = ConstantsEngine.replace(var7, LabelUtils.findServiceIdByType(var2.getType()[var4]), var8);
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
    public void writerLink() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, ExtConfigException {
        if (this.d.getLink() != null) {
            String var1 = RuleUtils.getResPath(this.g) + "control/" + "extView" + "?" + "mvid" + "=" + this.d.getLink().getMvId() + "&" + "_showForm" + "=false&" + "returnJsp" + "=false";
            this.b.println("<script language='javascript'>");
            if (this.d.getLink().getMvId() != null && this.d.getLink().getMvId().length() > 0) {
                int var9 = this.k.size() + this.d.getMaxLevel();
                if (this.d.getLink().isUse()) {
                    this.b.println("rowLinkFireTR({id:'" + this.a.getId() + "', url:'" + var1 + "', type:'" + this.d.getLink().getType() + "', colspan:'" + var9 + "'});");
                }
            } else if (this.d.getLink().getAction() != null && this.d.getLink().getAction().length() > 0) {
                this.b.print("rowActionFireTR({id:'" + this.a.getId() + "',cb:function(a,b,c,d,e){ " + this.d.getLink().getAction() + "(a, b, c, d, e); }})");
            } else {
                MVContext var2 = RuleUtils.findCurMV(this.i);
                RowLinkContext var3 = this.d.getLink();
                this.b.println("tableUpdateComp({id:'" + this.a.getId() + "',linkParamName:\"" + var3.getParamName() + "\", url:[");
                Object var4 = null;

                for(int var5 = 0; var3.getTarget() != null && var5 < var3.getTarget().length; ++var5) {
                    String var6 = var3.getTarget()[var5];
                    var4 = LabelUtils.findObjectByLabel(var2, var6, var3.getType()[var5]);
                    if (var4 != null) {
                        String var7 = RuleUtils.getResPath(this.g) + "control/extControl?serviceid=$0&" + "t_from_id" + "=" + var2.getMvid() + "&id=$1";
                        String var8 = (String)PropertyUtils.getProperty(var4, "id");
                        var7 = ConstantsEngine.replace(var7, LabelUtils.findServiceIdByType(var3.getType()[var5]), var8);
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
