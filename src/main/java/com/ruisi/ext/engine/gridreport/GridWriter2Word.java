//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.gridreport;

import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.view.context.gridreport.GridCell;
import com.ruisi.ext.engine.view.context.gridreport.GridReportContext;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc.Enum;

public class GridWriter2Word implements GridWriter {
    private GridReportContext a;
    private ExtWriter b;
    private ExtEnvirContext c;
    private InputOption d;
    private WriterService e;
    private XWPFDocument f;
    private XWPFTable g;
    private int h = 0;

    public GridWriter2Word(GridReportContext var1, XWPFDocument var2, ExtWriter var3, ExtEnvirContext var4, InputOption var5) {
        this.a = var1;
        this.b = var3;
        this.c = var4;
        this.d = var5;
        this.e = new WriterService(var1);
        this.f = var2;
    }

    public void begin() {
        int var1 = this.a.getHeaders().length + this.a.getDetails().length * this.a.loadOptions().size();
        if (this.a.getFooters() != null) {
            var1 += this.a.getFooters().length;
        }

        int var2 = this.a.getDetails()[0].length;
        this.g = this.f.createTable(var1, var2);
        CTTbl var3 = this.g.getCTTbl();
        CTTblPr var4 = var3.getTblPr() == null ? var3.addNewTblPr() : var3.getTblPr();
        CTTblWidth var5 = var4.isSetTblW() ? var4.getTblW() : var4.addNewTblW();
        CTJc var6 = var4.addNewJc();
        var6.setVal(Enum.forString("center"));
        var5.setW(new BigInteger("8000"));
        var5.setType(STTblWidth.DXA);
    }

    public void end() {
    }

    public void writeDetail() {
        GridCell[][] var1 = this.a.getDetails();
        List var2 = this.a.loadOptions();

        for(int var3 = 0; var3 < var1.length; ++var3) {
            GridCell[] var4 = var1[var3];

            for(int var5 = 0; var5 < var2.size(); ++var5) {
                XWPFTableRow var6 = this.g.getRow(this.h);
                var6.setHeight(400);

                for(int var7 = 0; var7 < var4.length; ++var7) {
                    GridCell var8 = var4[var7];
                    Map var9 = (Map)var2.get(var5);
                    Object var10 = var9.get(var8.getAlias());
                    String var11 = "";
                    if (var10 != null) {
                        if (var8.getFormatPattern() != null && var8.getFormatPattern().length() > 0) {
                            var11 = this.e.format(var10, var8.getFormatPattern());
                        } else {
                            var11 = var10.toString();
                        }
                    }

                    XWPFTableCell var12 = var6.getCell(var7);
                    CTTc var13 = var12.getCTTc();
                    String var14 = var8.getAlign();
                    if (var14 != null && var14.length() != 0 && !"left".equals(var14)) {
                        if ("center".equals(var14)) {
                            ((CTP)var13.getPList().get(0)).addNewPPr().addNewJc().setVal(STJc.CENTER);
                        } else {
                            ((CTP)var13.getPList().get(0)).addNewPPr().addNewJc().setVal(STJc.RIGHT);
                        }
                    } else {
                        ((CTP)var13.getPList().get(0)).addNewPPr().addNewJc().setVal(STJc.LEFT);
                    }

                    var12.setVerticalAlignment(XWPFVertAlign.CENTER);
                    var12.setText(var11);
                }

                ++this.h;
            }
        }

    }

    public static void mergeCellsHorizontal(XWPFTable var0, int var1, int var2, int var3) {
        for(int var4 = var2; var4 <= var3; ++var4) {
            XWPFTableCell var5 = var0.getRow(var1).getCell(var4);
            if (var4 == var2) {
                var5.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                var5.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }

    }

    public static void mergeCellsVertically(XWPFTable var0, int var1, int var2, int var3) {
        for(int var4 = var2; var4 <= var3; ++var4) {
            XWPFTableCell var5 = var0.getRow(var4).getCell(var1);
            if (var4 == var2) {
                var5.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                var5.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }

    }

    public void writeFooter() {
        GridCell[][] var1 = this.a.getFooters();
        if (var1 != null && var1.length != 0) {
            int var2 = GridWriter2Excel.getTableCols(var1[0]);
            int[] var3 = new int[var1.length];
            HashMap var4 = new HashMap();

            for(int var5 = 0; var5 < var1.length; ++var5) {
                GridCell[] var6 = var1[var5];
                XWPFTableRow var7 = this.g.getRow(this.h);
                var7.setHeight(400);

                for(int var8 = 0; var8 < var6.length; ++var8) {
                    GridCell var9 = var6[var8];
                    String var10 = var9.getDesc();
                    if (var9.getDynamicText() != null && var9.getDynamicText()) {
                        Object var11 = this.e.findDynamicTextValue(var9.getAlias());
                        var10 = var10 + this.e.format(var11, var9.getFormatPattern());
                    }

                    XWPFTableCell var18 = var7.getCell(var3[var5]);
                    CTTc var12 = var18.getCTTc();
                    String var13 = var9.getAlign();
                    if (var13 != null && var13.length() != 0 && !"left".equals(var13)) {
                        if ("center".equals(var13)) {
                            ((CTP)var12.getPList().get(0)).addNewPPr().addNewJc().setVal(STJc.CENTER);
                        } else {
                            ((CTP)var12.getPList().get(0)).addNewPPr().addNewJc().setVal(STJc.RIGHT);
                        }
                    } else {
                        ((CTP)var12.getPList().get(0)).addNewPPr().addNewJc().setVal(STJc.LEFT);
                    }

                    if (var9.getColSpan() > 1 || var9.getRowSpan() > 1) {
                        int var14;
                        if (var9.getRowSpan() > 1) {
                            for(var14 = 0; var14 < var9.getColSpan(); ++var14) {
                                mergeCellsVertically(this.g, var3[var5] + var14, this.h, this.h + var9.getRowSpan() - 1);
                            }
                        }

                        if (var9.getColSpan() > 1) {
                            for(var14 = 0; var14 < var9.getRowSpan(); ++var14) {
                                mergeCellsHorizontal(this.g, this.h + var14, var3[var5], var3[var5] + var9.getColSpan() - 1);
                            }
                        }

                        if (var9.getRowSpan() > 1) {
                            for(var14 = 1; var14 < var9.getRowSpan(); ++var14) {
                                boolean var15 = false;

                                for(int var16 = 0; var16 < var8; ++var16) {
                                    int var17 = var6[var16].getRowSpan() - 1;
                                    if (var17 < var14) {
                                        var4.put(var5 + var14 + "," + (var3[var5] - 1), var9.getColSpan());
                                        var15 = true;
                                        break;
                                    }
                                }

                                if (!var15) {
                                    var3[var5 + var14] += var9.getColSpan();
                                }
                            }
                        }

                        if (var9.getColSpan() > 1) {
                            var3[var5] = var3[var5] + var9.getColSpan() - 1;
                        }
                    }

                    var18.setVerticalAlignment(XWPFVertAlign.CENTER);
                    var18.setText(var10);
                    if (var3[var5] < var2 - 1) {
                        Integer var19 = (Integer)var4.get(var5 + "," + var3[var5]);
                        if (var19 != null) {
                            var3[var5] += var19;
                        }

                        int var10002 = var3[var5]++;
                    }
                }

                ++this.h;
            }

        }
    }

    public void writeHeader() {
        GridCell[][] var1 = this.a.getHeaders();
        if (var1 != null && var1.length != 0) {
            int var2 = GridWriter2Excel.getTableCols(var1[0]);
            int[] var3 = new int[var1.length];
            HashMap var4 = new HashMap();

            for(int var5 = 0; var5 < var1.length; ++var5) {
                GridCell[] var6 = var1[var5];
                XWPFTableRow var7 = this.g.getRow(this.h);
                var7.setHeight(500);

                for(int var8 = 0; var8 < var6.length; ++var8) {
                    GridCell var9 = var6[var8];
                    String var10 = var9.getDesc();
                    if (var9.getDynamicText() != null && var9.getDynamicText()) {
                        Object var11 = this.e.findDynamicTextValue(var9.getAlias());
                        var10 = var10 + this.e.format(var11, var9.getFormatPattern());
                    }

                    XWPFTableCell var20 = var7.getCell(var3[var5]);
                    CTTcPr var12 = var20.getCTTc().addNewTcPr();
                    CTShd var13 = var12.isSetShd() ? var12.getShd() : var12.addNewShd();
                    var12.addNewTcW().setW(BigInteger.valueOf(3000L));
                    var20.setVerticalAlignment(XWPFVertAlign.CENTER);
                    CTTc var14 = var20.getCTTc();
                    String var15 = var9.getAlign();
                    if (var15 != null && var15.length() != 0 && !"left".equals(var15)) {
                        if ("center".equals(var15)) {
                            ((CTP)var14.getPList().get(0)).addNewPPr().addNewJc().setVal(STJc.CENTER);
                        } else {
                            ((CTP)var14.getPList().get(0)).addNewPPr().addNewJc().setVal(STJc.RIGHT);
                        }
                    } else {
                        ((CTP)var14.getPList().get(0)).addNewPPr().addNewJc().setVal(STJc.LEFT);
                    }

                    if (var9.getColSpan() > 1 || var9.getRowSpan() > 1) {
                        int var16;
                        if (var9.getRowSpan() > 1) {
                            for(var16 = 0; var16 < var9.getColSpan(); ++var16) {
                                mergeCellsVertically(this.g, var3[var5] + var16, this.h, this.h + var9.getRowSpan() - 1);
                            }
                        }

                        if (var9.getColSpan() > 1) {
                            for(var16 = 0; var16 < var9.getRowSpan(); ++var16) {
                                mergeCellsHorizontal(this.g, this.h + var16, var3[var5], var3[var5] + var9.getColSpan() - 1);
                            }
                        }

                        if (var9.getRowSpan() > 1) {
                            for(var16 = 1; var16 < var9.getRowSpan(); ++var16) {
                                boolean var17 = false;

                                for(int var18 = 0; var18 < var8; ++var18) {
                                    int var19 = var6[var18].getRowSpan() - 1;
                                    if (var19 < var16) {
                                        var4.put(var5 + var16 + "," + (var3[var5] - 1), var9.getColSpan());
                                        var17 = true;
                                        break;
                                    }
                                }

                                if (!var17) {
                                    var3[var5 + var16] += var9.getColSpan();
                                }
                            }
                        }

                        if (var9.getColSpan() > 1) {
                            var3[var5] = var3[var5] + var9.getColSpan() - 1;
                        }
                    }

                    var13.setFill("EFEFEF");
                    var20.setText(var10);
                    if (var3[var5] < var2 - 1) {
                        Integer var21 = (Integer)var4.get(var5 + "," + var3[var5]);
                        if (var21 != null) {
                            var3[var5] += var21;
                        }

                        int var10002 = var3[var5]++;
                    }
                }

                ++this.h;
            }

        }
    }
}
