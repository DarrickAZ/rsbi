//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.gridreport;

import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.view.context.gridreport.GridCell;
import com.ruisi.ext.engine.view.context.gridreport.GridReportContext;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import jxl.write.Number;

public class GridWriter2Excel implements GridWriter {
    private GridReportContext a;
    private ExtEnvirContext b;
    private InputOption c;
    private WriterService d;
    private WritableSheet e;
    private int f;
    private int g = 0;

    public GridWriter2Excel(GridReportContext var1, WritableSheet var2, ExtEnvirContext var3, InputOption var4, int var5) {
        this.a = var1;
        this.e = var2;
        this.b = var3;
        this.c = var4;
        this.d = new WriterService(var1);
        this.f = var5;
    }

    @Override
    public void begin() {
        int var1 = this.a.getDetails()[0].length;

        for(int var2 = 0; var2 < var1; ++var2) {
            this.e.setColumnView(var2, 20);
        }

    }

    @Override
    public void writeDetail() throws WriteException {
        GridCell[][] var1 = this.a.getDetails();
        List var2 = this.a.loadOptions();
        HashMap var3 = new HashMap();
        int var4 = 0;

        for(int var5 = 0; var5 < var1.length; ++var5) {
            GridCell[] var6 = var1[var5];

            for(int var7 = 0; var7 < var2.size(); ++var7) {
                for(int var8 = 0; var8 < var6.length; ++var8) {
                    GridCell var9 = var6[var8];
                    WritableCellFormat var10 = null;
                    NumberFormat var11;
                    if (var9.getFormatPattern() != null && var9.getFormatPattern().length() > 0) {
                        var11 = (NumberFormat)var3.get(var9.getAlias());
                        if (var11 == null) {
                            NumberFormat var12 = new NumberFormat(var9.getFormatPattern());
                            var3.put(var9.getAlias(), var12);
                            var11 = var12;
                        }

                        var10 = new WritableCellFormat(var11);
                    } else {
                        var10 = new WritableCellFormat();
                    }

                    if (var9.getAlign() != null && var9.getAlign().length() > 0) {
                        if ("center".equals(var9.getAlign())) {
                            var10.setAlignment(Alignment.CENTRE);
                        } else if ("left".equals(var9.getAlign())) {
                            var10.setAlignment(Alignment.LEFT);
                        } else if ("right".equals(var9.getAlign())) {
                            var10.setAlignment(Alignment.RIGHT);
                        }
                    } else {
                        var10.setAlignment(Alignment.LEFT);
                    }

                    var10.setBorder(Border.ALL, BorderLineStyle.THIN);
                    var10.setVerticalAlignment(VerticalAlignment.CENTRE);
                    var11 = null;
                    Map var14 = (Map)var2.get(var7);
                    Object var13 = var14.get(var9.getAlias());
                    Object var15;
                    if (var13 != null) {
                        if (var13 instanceof BigDecimal) {
                            var15 = new Number(var8, var4 + this.f, ((BigDecimal)var13).doubleValue());
                        } else if (var13 instanceof Double) {
                            var15 = new Number(var8, var4 + this.f, (Double)var13);
                        } else if (var13 instanceof Integer) {
                            var15 = new Number(var8, var4 + this.f, ((Integer)var13).doubleValue());
                        } else if (var13 instanceof Date) {
                            var15 = new Label(var8, var4 + this.f, this.d.format(var13, var9.getFormatPattern()));
                        } else {
                            var15 = new Label(var8, var4 + this.f, var13.toString());
                        }
                    } else {
                        var15 = new Label(var8, var4 + this.f, "");
                    }

                    ((WritableCell)var15).setCellFormat(var10);
                    this.e.addCell((WritableCell)var15);
                }

                ++var4;
            }
        }

        this.g += var4;
        this.f += var4;
    }

    public void writeFooter() throws WriteException {
        GridCell[][] var1 = this.a.getFooters();
        if (var1 != null && var1.length != 0 && var1[0].length != 0) {
            int var2 = getTableCols(var1[0]);
            int[] var3 = new int[var1.length];
            HashMap var4 = new HashMap();

            for(int var5 = 0; var5 < var1.length; ++var5) {
                GridCell[] var6 = var1[var5];

                for(int var7 = 0; var7 < var6.length; ++var7) {
                    GridCell var8 = var6[var7];
                    WritableCellFormat var9 = null;
                    if (var8.getDynamicText() != null && var8.getDynamicText() && var8.getFormatPattern() != null && var8.getFormatPattern().length() > 0) {
                        var9 = new WritableCellFormat(new NumberFormat(var8.getFormatPattern()));
                    } else {
                        var9 = new WritableCellFormat();
                    }

                    if (var8.getAlign() != null && var8.getAlign().length() > 0) {
                        if ("center".equals(var8.getAlign())) {
                            var9.setAlignment(Alignment.CENTRE);
                        } else if ("left".equals(var8.getAlign())) {
                            var9.setAlignment(Alignment.LEFT);
                        } else if ("right".equals(var8.getAlign())) {
                            var9.setAlignment(Alignment.RIGHT);
                        }
                    } else {
                        var9.setAlignment(Alignment.LEFT);
                    }

                    var9.setBorder(Border.ALL, BorderLineStyle.THIN);
                    var9.setVerticalAlignment(VerticalAlignment.CENTRE);
                    Object var10 = null;
                    if (var8.getDynamicText() != null && var8.getDynamicText()) {
                        Object var11 = this.d.findDynamicTextValue(var8.getAlias());
                        if (var11 == null) {
                            var10 = new Label(var3[var5], var5 + this.f, "");
                        } else {
                            double var12 = 0.0D;
                            if (var11 instanceof Integer) {
                                var12 = ((Integer)var11).doubleValue();
                            } else if (var11 instanceof Double) {
                                var12 = (Double)var11;
                            } else if (var11 instanceof BigDecimal) {
                                var12 = ((BigDecimal)var11).doubleValue();
                            }

                            var10 = new Number(var3[var5], var5 + this.f, var12);
                        }
                    } else {
                        var10 = new Label(var3[var5], var5 + this.f, var8.getDesc());
                    }

                    ((WritableCell)var10).setCellFormat(var9);
                    this.e.addCell((WritableCell)var10);
                    if (var8.getColSpan() > 1 || var8.getRowSpan() > 1) {
                        int var17 = var3[var5];
                        int var19 = var5 + this.f;
                        this.e.mergeCells(var17, var19, var17 + var8.getColSpan() - 1, var19 + var8.getRowSpan() - 1);
                        if (var8.getRowSpan() > 1) {
                            for(int var13 = 1; var13 < var8.getRowSpan(); ++var13) {
                                boolean var14 = false;

                                for(int var15 = 0; var15 < var7; ++var15) {
                                    int var16 = var6[var15].getRowSpan() - 1;
                                    if (var16 < var13) {
                                        var4.put(var5 + var13 + "," + (var17 - 1), var8.getColSpan());
                                        var14 = true;
                                        break;
                                    }
                                }

                                if (!var14) {
                                    var3[var5 + var13] += var8.getColSpan();
                                }
                            }
                        }

                        if (var8.getColSpan() > 1) {
                            var3[var5] = var3[var5] + var8.getColSpan() - 1;
                        }
                    }

                    if (var3[var5] < var2 - 1) {
                        Integer var18 = (Integer)var4.get(var5 + "," + var3[var5]);
                        if (var18 != null) {
                            var3[var5] += var18;
                        }

                        int var10002 = var3[var5]++;
                    }
                }
            }

            this.g += var1.length;
        }
    }

    public static int getTableCols(GridCell[] var0) {
        int var1 = 0;

        for(int var2 = 0; var2 < var0.length; ++var2) {
            var1 += var0[var2].getColSpan();
        }

        return var1;
    }

    public void writeHeader() throws WriteException {
        GridCell[][] var1 = this.a.getHeaders();
        if (var1 != null && var1.length != 0 && var1[0].length != 0) {
            int var2 = getTableCols(var1[0]);
            int[] var3 = new int[var1.length];
            HashMap var4 = new HashMap();

            for(int var5 = 0; var5 < var1.length; ++var5) {
                GridCell[] var6 = var1[var5];

                for(int var7 = 0; var7 < var6.length; ++var7) {
                    GridCell var8 = var6[var7];
                    WritableFont var9 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
                    WritableCellFormat var10 = new WritableCellFormat(var9);
                    var10.setBackground(Colour.GRAY_25);
                    if (var8.getAlign() != null && var8.getAlign().length() > 0) {
                        if ("center".equals(var8.getAlign())) {
                            var10.setAlignment(Alignment.CENTRE);
                        } else if ("left".equals(var8.getAlign())) {
                            var10.setAlignment(Alignment.LEFT);
                        } else if ("right".equals(var8.getAlign())) {
                            var10.setAlignment(Alignment.RIGHT);
                        }
                    } else {
                        var10.setAlignment(Alignment.CENTRE);
                    }

                    var10.setBorder(Border.ALL, BorderLineStyle.THIN);
                    var10.setVerticalAlignment(VerticalAlignment.CENTRE);
                    String var11 = var8.getDesc();
                    if (var8.getDynamicText() != null && var8.getDynamicText()) {
                        Object var12 = this.d.findDynamicTextValue(var8.getAlias());
                        var11 = var11 + this.d.format(var12, var8.getFormatPattern());
                    }

                    Label var19 = new Label(var3[var5], var5 + this.f, var11);
                    var19.setCellFormat(var10);
                    this.e.addCell(var19);
                    if (var8.getColSpan() > 1 || var8.getRowSpan() > 1) {
                        int var13 = var3[var5];
                        int var14 = var5 + this.f;
                        this.e.mergeCells(var13, var14, var13 + var8.getColSpan() - 1, var14 + var8.getRowSpan() - 1);
                        if (var8.getRowSpan() > 1) {
                            for(int var15 = 1; var15 < var8.getRowSpan(); ++var15) {
                                boolean var16 = false;

                                for(int var17 = 0; var17 < var7; ++var17) {
                                    int var18 = var6[var17].getRowSpan() - 1;
                                    if (var18 < var15) {
                                        var4.put(var5 + var15 + "," + (var13 - 1), var8.getColSpan());
                                        var16 = true;
                                        break;
                                    }
                                }

                                if (!var16) {
                                    var3[var5 + var15] += var8.getColSpan();
                                }
                            }
                        }

                        if (var8.getColSpan() > 1) {
                            var3[var5] = var3[var5] + var8.getColSpan() - 1;
                        }
                    }

                    if (var3[var5] < var2 - 1) {
                        Integer var20 = (Integer)var4.get(var5 + "," + var3[var5]);
                        if (var20 != null) {
                            var3[var5] += var20;
                        }

                        int var10002 = var3[var5]++;
                    }
                }
            }

            this.g += var1.length;
            this.f += this.g;
        }
    }

    public void end() {
    }

    public int getDataHeight() {
        return this.g;
    }
}
