//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.gridreport;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.view.context.gridreport.GridCell;
import com.ruisi.ext.engine.view.context.gridreport.GridReportContext;
import com.ruisi.ext.engine.wrapper.ExtWriter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GridWriter2PDF implements GridWriter {
    private GridReportContext a;
    private ExtWriter b;
    private ExtEnvirContext c;
    private InputOption d;
    private WriterService e;
    private Document f;
    private BaseFont g;
    private Font h;
    private PdfPTable i;

    public GridWriter2PDF(GridReportContext var1, Document var2, ExtWriter var3, ExtEnvirContext var4, InputOption var5) {
        this.a = var1;
        this.b = var3;
        this.c = var4;
        this.d = var5;
        this.e = new WriterService(var1);
        this.f = var2;
    }

    @Override
    public void begin() throws IOException, DocumentException {
        this.g = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
        this.h = new Font(this.g, 12.0F, 0);
        GridCell[][] var1 = this.a.getHeaders();
        int var2 = 0;

        for(int var3 = 0; var3 < var1[0].length; ++var3) {
            var2 += var1[0][var3].getColSpan();
        }

        this.i = new PdfPTable(var2);
        this.i.setSpacingAfter(10.0F);
        this.i.setWidthPercentage(100.0F);
    }

    @Override
    public void end() throws DocumentException {
        this.f.add(this.i);
    }

    @Override
    public void writeDetail() {
        GridCell[][] var1 = this.a.getDetails();
        List var2 = this.a.loadOptions();

        for(int var3 = 0; var3 < var1.length; ++var3) {
            GridCell[] var4 = var1[var3];

            for(int var5 = 0; var5 < var2.size(); ++var5) {
                for(int var6 = 0; var6 < var4.length; ++var6) {
                    GridCell var7 = var4[var6];
                    Map var8 = (Map)var2.get(var5);
                    Object var9 = var8.get(var7.getAlias());
                    String var10 = "";
                    if (var9 != null) {
                        if (var7.getFormatPattern() != null && var7.getFormatPattern().length() > 0) {
                            var10 = this.e.format(var9, var7.getFormatPattern());
                        } else {
                            var10 = var9.toString();
                        }
                    }

                    PdfPCell var11 = new PdfPCell(new Paragraph(var10, this.h));
                    if (var7.getAlign() != null && var7.getAlign().length() > 0) {
                        if ("center".equals(var7.getAlign())) {
                            var11.setHorizontalAlignment(1);
                        } else if ("left".equals(var7.getAlign())) {
                            var11.setHorizontalAlignment(0);
                        } else if ("right".equals(var7.getAlign())) {
                            var11.setHorizontalAlignment(2);
                        }
                    }

                    this.i.addCell(var11);
                }
            }
        }

    }

    @Override
    public void writeFooter() {
        GridCell[][] var1 = this.a.getFooters();
        if (var1 != null && var1.length != 0) {
            for(int var2 = 0; var2 < var1.length; ++var2) {
                GridCell[] var3 = var1[var2];

                for(int var4 = 0; var4 < var3.length; ++var4) {
                    GridCell var5 = var3[var4];
                    String var6 = var5.getDesc();
                    if (var5.getDynamicText() != null && var5.getDynamicText()) {
                        Object var7 = this.e.findDynamicTextValue(var5.getAlias());
                        var6 = var6 + this.e.format(var7, var5.getFormatPattern());
                    }

                    PdfPCell var8 = new PdfPCell(new Paragraph(var6, this.h));
                    if (var5.getAlign() != null && var5.getAlign().length() > 0) {
                        if ("center".equals(var5.getAlign())) {
                            var8.setHorizontalAlignment(1);
                        } else if ("left".equals(var5.getAlign())) {
                            var8.setHorizontalAlignment(0);
                        } else if ("right".equals(var5.getAlign())) {
                            var8.setHorizontalAlignment(2);
                        }
                    }

                    var8.setColspan(var5.getColSpan());
                    var8.setRowspan(var5.getRowSpan());
                    this.i.addCell(var8);
                }
            }

        }
    }

    @Override
    public void writeHeader() {
        GridCell[][] var1 = this.a.getHeaders();
        if (var1 != null && var1.length != 0) {
            for(int var2 = 0; var2 < var1.length; ++var2) {
                GridCell[] var3 = var1[var2];

                for(int var4 = 0; var4 < var3.length; ++var4) {
                    GridCell var5 = var3[var4];
                    String var6 = var5.getDesc();
                    if (var5.getDynamicText() != null && var5.getDynamicText()) {
                        Object var7 = this.e.findDynamicTextValue(var5.getAlias());
                        var6 = var6 + this.e.format(var7, var5.getFormatPattern());
                    }

                    PdfPCell var8 = new PdfPCell(new Paragraph(var6, this.h));
                    if (var5.getAlign() != null && var5.getAlign().length() > 0) {
                        if ("center".equals(var5.getAlign())) {
                            var8.setHorizontalAlignment(1);
                        } else if ("left".equals(var5.getAlign())) {
                            var8.setHorizontalAlignment(0);
                        } else if ("right".equals(var5.getAlign())) {
                            var8.setHorizontalAlignment(2);
                        }
                    } else {
                        var8.setHorizontalAlignment(1);
                    }

                    var8.setColspan(var5.getColSpan());
                    var8.setRowspan(var5.getRowSpan());
                    var8.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    this.i.addCell(var8);
                }
            }

        }
    }
}
