//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.cross;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.view.builder.PageBuilder$JSObject;
import com.ruisi.ext.engine.view.context.cross.CrossCols;
import com.ruisi.ext.engine.view.context.cross.CrossField;
import com.ruisi.ext.engine.view.context.cross.CrossKpi;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.context.cross.CrossRows;
import com.ruisi.ext.engine.view.context.cross.RowHeadContext;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.mozilla.javascript.Function;

public class CrossWriter2JAVA implements CrossWriter {
    private CrossReportContext a;
    private boolean b = false;
    private ExtWriter c;
    private CrossOutData$Header[][] d;
    private CrossCols e;
    private CrossRows f;
    private CrossWriterService g;
    private ExtRequest h;
    private CrossOutData i;
    private Integer j = 0;
    private Map k = new HashMap();
    private String l;
    private Object m;
    private String n;
    private int o = 0;
    private List p = new ArrayList();

    @Override
    public void writerPageInfo() {
    }

    @Override
    public void writerSort() {
    }

    public CrossOutData getOutData() {
        return this.i;
    }

    public CrossWriter2JAVA(CrossBuilder var1, ExtWriter var2) {
        this.a = var1.getCrossReport();
        this.c = var2;
        this.e = this.a.getCrossCols();
        this.f = this.a.getCrossRows();
        this.h = var1.getRequest();
        boolean var3 = false;
        CrossField var4 = var1.getLoader().loadFieldByKpiCode((String)null);
        this.g = new CrossWriterService(var4, this.h, this.a);
    }

    @Override
    public void queryLastLevelCols() {
    }

    @Override
    public void writeCols() {
        this.d = new CrossOutData$Header[this.e.getMaxLevel()][];
        int var1 = 0;

        for(int var2 = 0; var2 < this.e.getMaxLevel(); ++var2) {
            ArrayList var3 = new ArrayList();
            CrossWriterService.findNodeByLevel(var2, this.e.getCols(), var3);
            this.k.put(var2, var3);
            int var4 = this.f.getOverlapped() != null && this.f.getOverlapped() ? 1 : this.f.getMaxLevel();
            if (this.a.getRowHeads() != null) {
                var4 = this.a.getRowHeads().size();
            }

            CrossOutData$Header[] var5 = null;
            var5 = new CrossOutData$Header[var3.size() + var4 + var1];
            if (var2 == 0) {
                if (this.f.getOverlapped() != null && this.f.getOverlapped()) {
                    CrossField var13 = this.g.getRow(this.a.getCrossRows().getTmpRows(), 0);
                    var5[0] = new CrossOutData$Header(var13.getDesc());
                    var5[0].setRowSpan(this.e.getMaxLevel());
                } else {
                    List var6 = this.a.getRowHeads();
                    int var7;
                    if (var6 == null) {
                        for(var7 = 0; var7 < this.f.getMaxLevel(); ++var7) {
                            String var8 = this.g.getRow(this.a.getCrossRows().getTmpRows(), var7).getDesc();
                            var5[var7] = new CrossOutData$Header(var8);
                            var5[var7].setRowSpan(this.e.getMaxLevel());
                        }
                    } else if (var6.size() == 1) {
                        var5[0] = new CrossOutData$Header(var6 == null ? "" : ((RowHeadContext)var6.get(0)).getDesc());
                        var5[0].setRowSpan(this.e.getMaxLevel());
                        var5[0].setColSpan(this.f.getMaxLevel());
                    } else {
                        for(var7 = 0; var7 < this.f.getMaxLevel(); ++var7) {
                            var5[var7] = new CrossOutData$Header(((RowHeadContext)var6.get(var7)).getDesc());
                            var5[var7].setRowSpan(this.e.getMaxLevel());
                        }
                    }
                }
            }

            for(int var14 = 0; var14 < var3.size(); ++var14) {
                CrossField var15 = (CrossField)var3.get(var14);
                int var16 = var15.getSubs() == null ? 0 : var15.getSubs().size();
                if (var16 == 0) {
                    var15.setLastLevel(var15.getLevel());
                }

                String var9 = this.g.writerHeader(var15, "xls");
                int var10 = var4 + var14;
                if (var2 > 0) {
                    for(int var11 = var2; var11 > 0; --var11) {
                        int var12 = this.getLevelNodeCount((List)this.k.get(var11 - 1), var15.getParent());
                        var10 += var12;
                    }
                }

                CrossOutData$Header var17 = var5[var10] = new CrossOutData$Header(var9);
                var17.b = var15.getSpan();
                var17.d = var15.getLevel();
                if (var16 == 0 && var15.getLevel() != this.e.getMaxLevel() - 1) {
                    var15.setLastLevel(this.e.getMaxLevel() - 1);
                    var17.c = this.e.getMaxLevel() - var15.getLevel();
                    ++var1;
                }
            }

            this.d[var2] = var5;
        }

        CrossWriterService.findNodeByLastLevel(this.e.getMaxLevel() - 1, this.e.getCols(), this.p);
    }

    public int getLevelNodeCount(List var1, CrossField var2) {
        int var3 = 0;

        for(int var4 = 0; var4 < var1.size(); ++var4) {
            CrossField var5 = (CrossField)var1.get(var4);
            if (var5.getLastLevel() == this.e.getMaxLevel() - 1) {
                ++var3;
            }

            if (var5 == var2) {
                break;
            }
        }

        return var3;
    }

    @Override
    public void writerFieldDirll() {
    }

    @Override
    public void writerFieldFmts() {
    }

    @Override
    public void writerKpi(CrossField var1, List var2, CrossField var3, int var4, int var5) {
        String var6 = "";
        Double var7 = null;
        List var8 = this.g.querySubData(var3, var2, 2, (String)null);
        CrossKpi var9 = this.g.currKpiNode;
        if (var9 == null && this.g.backKpiNode != null) {
            var9 = this.g.backKpiNode;
        }

        if (var9 == null) {
            var9 = this.g.baseKpiNode;
        }

        if (var3.getType().equals("kpiOther") && (var3.getValue() == null || var3.getValue().length() == 0)) {
            var9 = var9.clone();
        }

        var7 = this.g.findKipValue(var8, var9, var3, var1, (String)null);
        CrossKpi var10 = this.g.currFormula;
        if (var10 != null && var10.getFormula() != null && var10.getFormula().length() > 0) {
            var7 = this.g.findFormulaValue(var7, var3, var1);
        }

        String var11 = var9 == null ? null : var9.getJsFunc();
        if (var11 != null && var11.length() > 0) {
            PageBuilder$JSObject var17 = (PageBuilder$JSObject)this.h.getAttribute("ext.script.engine");
            Object var18 = var17.getScope().get(var11, var17.getScope());
            if (var18 == null || !(var18 instanceof Function)) {
                String var20 = ConstantsEngine.replace("定义的 jsFunc 方法 $0 未找到.", var11);
                throw new ExtRuntimeException(var20);
            }

            Function var14 = (Function)var18;
            Object[] var15 = new Object[]{var7, var3, var1, var8, "xls"};
            var14.call(var17.getCt(), var17.getScope(), var17.getScope(), var15);
            ByteArrayOutputStream var19 = (ByteArrayOutputStream)this.c.getProxy();

            try {
                var19.flush();
            } catch (Exception var16) {
                var16.printStackTrace();
            }

            String var21 = new String(var19.toByteArray());
            var6 = var21;
            var19.reset();
            this.b = true;
        } else {
            String var12 = "";
            if (var9 != null && var9.getFormatPattern() != null) {
                if (var7 != null) {
                    DecimalFormat var13 = new DecimalFormat(var9.getFormatPattern());
                    var12 = var13.format(var7);
                }
            } else {
                var12 = String.valueOf(var7);
            }

            var6 = var12;
            this.b = false;
        }

        this.m = var7;
        if (var9 != null) {
            this.n = var9.getFormatPattern();
        }

        this.l = var6;
        if (this.g.whereIn == 2) {
            this.g.currKpiNode = null;
        }

        if (this.g.backKpiIn == 2) {
            this.g.backKpiNode = null;
        }

        if (this.g.formulaPos == 2) {
            this.g.currFormula = null;
        }

    }

    @Override
    public void writerLink() {
    }

    public void writeOverlappedRows(List var1, CrossOutData$Data[][] var2) {
        for(int var3 = 0; var1 != null && var3 < var1.size(); ++var3) {
            CrossOutData$Data[] var4 = new CrossOutData$Data[1 + this.p.size()];
            var2[this.o] = var4;
            CrossField var5 = (CrossField)var1.get(var3);
            String var6 = this.g.writerHeader(var5, "xls");
            Integer var7 = var5.getSpaceNum();
            int var8;
            if (var7 != null) {
                for(var8 = 0; var8 < var7; ++var8) {
                    var6 = " " + var6;
                }
            }

            for(var8 = 0; var8 < var5.getLevel(); ++var8) {
                var6 = "     " + var6;
            }

            CrossOutData$Data var13 = new CrossOutData$Data(var6);
            var4[0] = var13;
            var13.setRowSpan(1);
            var13.setType(2);
            List var9 = this.g.querySubData(var5, this.a.loadOptions(), 1, (String)null);

            for(int var10 = 0; var10 < this.p.size(); ++var10) {
                CrossField var11 = (CrossField)this.p.get(var10);
                this.writerKpi(var5, var9, var11, var3, var10);
                CrossOutData$Data var12 = var4[var10 + 1] = new CrossOutData$Data(this.l);
                if (this.b) {
                    var12.b = 3;
                } else {
                    var12.b = 1;
                }

                var12.d = this.m;
                var12.e = this.n;
            }

            ++this.o;
            if (this.g.whereIn == 1) {
                this.g.currKpiNode = null;
            }

            if (this.g.backKpiIn == 1) {
                this.g.backKpiNode = null;
            }

            if (this.g.formulaPos == 1) {
                this.g.currFormula = null;
            }

            this.writeOverlappedRows(var5.getSubs(), var2);
        }

    }

    private int a(List var1) {
        int var2 = 1;
        CrossField var3;
        if (var1 != null && var1.size() > 0) {
            for(Iterator var4 = var1.iterator(); var4.hasNext(); var2 += this.a(var3.getSubs())) {
                var3 = (CrossField)var4.next();
            }
        }

        return var2;
    }

    public void loopParent(CrossField var1, CrossOutData$Data[] var2) {
        CrossOutData$Data var3 = new CrossOutData$Data(this.g.writerHeader(var1, "html"));
        var2[this.j] = var3;
        this.j = this.j + 1;
        var3.setType(2);
        if (var1.getParent() != null) {
            this.loopParent(var1.getParent(), var2);
        }

    }

    public void writeRows() {
        if (this.f.getOverlapped() != null && this.f.getOverlapped()) {
            int var16 = this.a(this.f.getRows()) - 1;
            CrossOutData$Data[][] var17 = new CrossOutData$Data[var16][];
            this.writeOverlappedRows(this.f.getRows(), var17);
            this.i = new CrossOutData(this.d, var17);
            this.i.setColLevel(this.e.getMaxLevel());
            this.i.setRowLevel(this.f.getMaxLevel());
        } else {
            ArrayList var1 = new ArrayList();
            CrossWriterService.loopRow(this.f.getRows(), var1);
            int var2 = 0;

            CrossField var3;
            for(Iterator var4 = this.f.getRows().iterator(); var4.hasNext(); var2 += var3.getSpan()) {
                var3 = (CrossField)var4.next();
            }

            boolean var18 = this.f.getUnmerge() != null && this.f.getUnmerge();
            CrossOutData$Data[][] var19 = new CrossOutData$Data[var2][];
            CrossOutData$Data[] var5 = new CrossOutData$Data[this.f.getMaxLevel() + this.p.size()];
            int var6 = 0;
            int var7 = 0;

            for(int var8 = 0; var8 < var2; ++var8) {
                CrossField var9 = null;
                CrossOutData$Data[] var10 = null;

                int var11;
                for(var11 = 0; var11 < this.f.getMaxLevel(); ++var11) {
                    CrossField var12 = (CrossField)var1.get(var6);
                    ++var6;
                    if (var18 && var11 == 0 && var12.getParent() != null) {
                        this.j = 0;
                        this.loopParent(var12.getParent(), var5);
                    }

                    String var13 = this.g.writerHeader(var12, "xls");
                    Integer var14 = var12.getSpaceNum();
                    if (var14 != null) {
                        for(int var15 = 0; var15 < var14; ++var15) {
                            var13 = " " + var13;
                        }
                    }

                    CrossOutData$Data var24 = new CrossOutData$Data(var13);
                    var24.setLevel(var12.getLevel());
                    if (var18) {
                        var5[var11 + this.j] = var24;
                    } else {
                        var5[var11] = var24;
                        var24.setRowSpan(var12.getSpan());
                    }

                    var24.setType(2);
                    if (var12.getSubs() == null || var12.getSubs().size() == 0) {
                        var9 = var12;
                        var19[var7] = var5;
                        var10 = var5;
                        var5 = new CrossOutData$Data[this.f.getMaxLevel() + this.p.size()];
                        var24.setColSpan(this.f.getMaxLevel() - var12.getLevel());
                        ++var7;
                        break;
                    }
                }

                if (var18) {
                    this.j = 0;
                }

                if (var9 != null) {
                    if (!this.a.isShowData()) {
                        for(var11 = 0; var11 < this.p.size(); ++var11) {
                            var10[var11 + this.f.getMaxLevel()] = new CrossOutData$Data("");
                        }
                    } else {
                        List var20 = this.g.querySubData(var9, this.a.loadOptions(), 1, (String)null);

                        for(int var21 = 0; var21 < this.p.size(); ++var21) {
                            CrossField var22 = (CrossField)this.p.get(var21);
                            this.writerKpi(var9, var20, var22, var8, var21);
                            Object var23 = var10[var21 + this.f.getMaxLevel()] = new CrossOutData$Data(this.l);
                            if (this.b) {
                                ((CrossOutData$Data)var23).b = 3;
                            } else {
                                ((CrossOutData$Data)var23).b = 1;
                            }

                            ((CrossOutData$Data)var23).d = this.m;
                            ((CrossOutData$Data)var23).e = this.n;
                        }

                        if (this.g.whereIn == 1) {
                            this.g.currKpiNode = null;
                        }

                        if (this.g.backKpiIn == 1) {
                            this.g.backKpiNode = null;
                        }

                        if (this.g.formulaPos == 1) {
                            this.g.currFormula = null;
                        }
                    }
                }
            }

            this.i = new CrossOutData(this.d, var19);
            this.i.setColLevel(this.e.getMaxLevel());
            this.i.setRowLevel(this.f.getMaxLevel());
        }
    }

    @Override
    public void writerColLink() {
    }
}
