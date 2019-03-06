//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.cross;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.view.builder.PageBuilder$JSObject;
import com.ruisi.ext.engine.view.context.cross.*;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import org.mozilla.javascript.Function;

import java.util.*;
import java.util.Map.Entry;

public class CrossWriter2EASYUI implements CrossWriter {
    private CrossReportContext a;
    private CrossBuilder b;
    private ExtWriter c;
    private CrossCols d;
    private CrossRows e;
    private List f;
    private ExtRequest g;
    private CrossWriterService h;
    private InputOption i;
    private ExtEnvirContext j;
    public static final String rowPrefix = "h";
    private List k = new ArrayList();
    private Map l = new HashMap();

    @Override
    public void writerSort() {
    }

    public CrossWriter2EASYUI(CrossBuilder var1, ExtWriter var2, InputOption var3) {
        this.a = var1.getCrossReport();
        this.c = var2;
        this.f = var1.getCrossReport().loadOptions();
        this.d = this.a.getCrossCols();
        this.e = this.a.getCrossRows();
        this.g = var1.getRequest();
        this.b = var1;
        this.j = var1.getCtx();
        this.i = var3;
        CrossField var4 = var1.getLoader().loadFieldByKpiCode((String)null);
        this.h = new CrossWriterService(var4, this.g, this.a);
    }

    @Override
    public void writeCols() {
        this.c.println("<table id=\"" + this.a.getId() + "\"> ");
        this.c.println("<thead>  ");

        for(int var1 = 0; var1 < this.d.getMaxLevel(); ++var1) {
            ArrayList var2 = new ArrayList();
            CrossWriterService.findNodeByLevel(var1, this.d.getCols(), var2);
            this.c.print("<tr>");
            if (var1 == 0) {
                for(int var3 = 0; var3 < this.e.getMaxLevel(); ++var3) {
                    String var4 = this.h.getRow(this.a.getCrossRows().getTmpRows(), var3).getDesc();
                    this.c.print("<th data-options=\"field:'h_" + var3 + "',width:100\" rowspan=\"" + this.d.getMaxLevel() + "\">" + var4 + " </th>");
                }
            }

            Iterator var8 = var2.iterator();

            while(var8.hasNext()) {
                CrossField var7 = (CrossField)var8.next();
                int var5 = var7.getSpan();
                this.c.print("<th ");
                if (var5 > 1) {
                    this.c.print("colspan=\"" + var5 + "\" ");
                }

                int var6 = 0;
                if (var7.getSubs() != null) {
                    var6 = var7.getSubs().size();
                }

                if (var6 == 0) {
                    var7.setLastLevel(var7.getLevel());
                    this.c.print(" data-options=\"field:'" + var7.getJsonId() + "',width:100,align:'right',halign:'center',formatter:formatCol" + (var7.isFinanceFmt() ? ",finfmt:true" : "") + "\"");
                }

                if (var6 == 0 && var7.getLevel() != this.d.getMaxLevel() - 1) {
                    this.c.print(" rowspan = \"" + (this.d.getMaxLevel() - var7.getLevel()) + "\"");
                    var7.setLastLevel(this.d.getMaxLevel() - 1);
                }

                this.c.print(">");
                this.c.print(this.h.writerHeader(var7, "html"));
                this.c.print("</th> \n");
            }

            this.c.print("</tr>");
        }

        this.c.println("</thead>");
        this.c.println("</table>");
        CrossWriterService.findNodeByLastLevel(this.d.getMaxLevel() - 1, this.d.getCols(), this.k);
    }

    @Override
    public void writerPageInfo() {
    }

    @Override
    public void writerKpi(CrossField var1, List var2, CrossField var3, int var4, int var5) {
        List var6 = this.h.querySubData(var3, var2, 2, (String)null);
        CrossKpi var7 = this.h.currKpiNode;
        if (var7 == null) {
            var7 = this.h.baseKpiNode;
        }

        Double var8 = this.h.findKipValue(var6, var7, var3, var1, (String)null);
        CrossKpi var9 = this.h.currFormula;
        if (var9 != null && var9.getFormula() != null && var9.getFormula().length() > 0) {
            var8 = this.h.findFormulaValue(var8, var3, var1);
        }

        String var10 = var7.getJsFunc();
        if (var10 != null && var10.length() > 0) {
            this.c.print("'" + var3.getJsonId() + "': '");
            PageBuilder$JSObject var15 = (PageBuilder$JSObject)this.g.getAttribute("ext.script.engine");
            Object var16 = var15.getScope().get(var10, var15.getScope());
            if (var16 == null || !(var16 instanceof Function)) {
                String var17 = ConstantsEngine.replace("定义的 jsFunc 方法 $0 未找到.", var10);
                throw new ExtRuntimeException(var17);
            }

            Function var13 = (Function)var16;
            Object[] var14 = new Object[]{var8, var3, var1, var6, "html"};
            var13.call(var15.getCt(), var15.getScope(), var15.getScope(), var14);
            this.c.print("'");
            if (!this.l.containsKey(var3.getJsonId())) {
                this.l.put(var3.getJsonId(), "");
            }
        } else {
            String var11 = this.h.getCurrKpiFmt(var7);
            if (var11 != null && var11.length() > 0) {
                String var12 = this.h.whereIn == 1 ? var1.getJsonId() : var3.getJsonId();
                this.l.put(var12, var11);
            }

            this.h.kpiPos = this.h.whereIn;
            if (var8 == null) {
                this.c.print("'" + var3.getJsonId() + "': '-'");
            } else {
                this.c.print("'" + var3.getJsonId() + "': " + var8);
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

    }

    private void a(CrossField var1, ExtWriter var2, int var3) {
        if (var1.getParent() != null) {
            CrossField var4 = var1.getParent();
            --var3;
            String var6 = var4.getDesc();
            var2.print(" 'h_" + var3 + "': '',");
            this.a(var4, var2, var3);
        }

    }

    public void writeRows() {
        ArrayList var1 = new ArrayList();
        CrossWriterService.loopRow(this.e.getRows(), var1);
        this.c.println("<script>");
        int var2 = 0;

        CrossField var3;
        for(Iterator var4 = this.e.getRows().iterator(); var4.hasNext(); var2 += var3.getSpan()) {
            var3 = (CrossField)var4.next();
        }

        this.c.println(" var data = [");
        int var10 = 0;

        for(int var11 = 0; var11 < var2; ++var11) {
            this.c.print("{");
            CrossField var5 = null;

            int var6;
            CrossField var7;
            for(var6 = 0; var6 < this.e.getMaxLevel(); ++var6) {
                var7 = (CrossField)var1.get(var10);
                int var8 = this.h.loopFieldParent(var7);
                if (var8 > var6) {
                    this.a(var7, this.c, var8);
                }

                String var9 = var7.getDesc();
                this.c.print("'h_" + var8 + "': \"" + var9 + "\",");
                ++var10;
                if (var7.getSubs() == null || var7.getSubs().size() == 0) {
                    var5 = var7;
                    break;
                }
            }

            if (!this.a.isShowData()) {
                for(var6 = 0; var6 < this.k.size(); ++var6) {
                    var7 = (CrossField)this.k.get(var11);
                    this.c.print("'" + var7.getValue() + "': ''");
                    if (var6 != this.k.size() - 1) {
                        this.c.print(",");
                    }
                }
            } else {
                List var14 = this.h.querySubData(var5, this.f, 1, (String)null);

                for(int var15 = 0; var15 < this.k.size(); ++var15) {
                    CrossField var19 = (CrossField)this.k.get(var15);
                    this.writerKpi(var5, var14, var19, var11, var15);
                    if (var15 != this.k.size() - 1) {
                        this.c.print(",");
                    }
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
            }

            this.c.print("}");
            if (var11 != var2 - 1) {
                this.c.println(",");
            }
        }

        this.c.println("];");
        this.writerFieldFmts();
        this.c.println(" jQuery(function(){");
        this.c.println(" \tjQuery('#" + this.a.getId() + "').datagrid({");
        this.c.println("  \t\tdata: data");
        if (this.a.getConfCallBack() != null && this.a.getConfCallBack().length() > 0) {
            PageBuilder$JSObject var12 = (PageBuilder$JSObject)this.g.getAttribute("ext.script.engine");
            Object var13 = var12.getScope().get(this.a.getConfCallBack(), var12.getScope());
            if (var13 == null || !(var13 instanceof Function)) {
                String var17 = ConstantsEngine.replace("定义的 testFunc 方法 $0 未找到.", this.a.getConfCallBack());
                throw new ExtRuntimeException(var17);
            }

            Function var16 = (Function)var13;
            Object[] var18 = new Object[0];
            var16.call(var12.getCt(), var12.getScope(), var12.getScope(), var18);
        }

        this.c.println(" \t});");
        this.c.println(" });");
        this.c.println("</script>");
    }

    @Override
    public void writerLink() {
    }

    @Override
    public void queryLastLevelCols() {
        this.h.setCrossFiledLastLevel(this.d.getCols(), this.d);
        CrossWriterService.findNodeByLastLevel(this.d.getMaxLevel() - 1, this.d.getCols(), this.k);
    }

    @Override
    public void writerFieldFmts() {
        this.c.print("var crtfmt = {");
        int var1 = this.l.size();
        Iterator var2 = this.l.entrySet().iterator();

        for(int var3 = 1; var2.hasNext(); ++var3) {
            Entry var4 = (Entry)var2.next();
            if (var4.getValue() == null) {
                this.c.print("'" + (String)var4.getKey() + "': ''");
            } else {
                String var5 = (String)var4.getValue();
                this.c.print("'" + (String)var4.getKey() + "': '" + var5 + "'");
            }

            if (var3 != var1) {
                this.c.print(",");
            }
        }

        this.c.println("};");
    }

    @Override
    public void writerFieldDirll() {
    }

    @Override
    public void writerColLink() {
    }
}
