//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.cross.CrossOutData;
import com.ruisi.ext.engine.cross.CrossOutData$Data;
import com.ruisi.ext.engine.cross.CrossOutData$Header;
import com.ruisi.ext.engine.gridreport.GridWriter2PDF;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.util.ColorUtils;
import com.ruisi.ext.engine.view.builder.html.parser.ParserUtils;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.form.DateSelectContext;
import com.ruisi.ext.engine.view.context.form.MultiSelectContext;
import com.ruisi.ext.engine.view.context.form.SelectContext;
import com.ruisi.ext.engine.view.context.form.TextFieldContext;
import com.ruisi.ext.engine.view.context.gridreport.GridReportContext;
import com.ruisi.ext.engine.view.context.html.CustomContext;
import com.ruisi.ext.engine.view.context.html.ImageContext;
import com.ruisi.ext.engine.view.context.html.TextContext;
import com.ruisi.ext.engine.view.context.html.TextProperty;
import com.ruisi.ext.engine.view.emitter.excel.ChartData;
import com.ruisi.ext.engine.view.emitter.excel.ExcelLayoutEnginer;
import com.ruisi.ext.engine.view.test.TestUtils;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtResponse;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.htmlparser.util.ParserException;

public class PdfLayoutEnginer {
    private ExtWriter a;
    private ExtRequest b;
    private ExtResponse c;
    private ExtEnvirContext d;
    private InputOption e;
    private Document f;
    private PdfWriter g;
    private BaseFont h;
    private Font i;

    public void init() throws IOException, DocumentException {
        this.h = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
        this.i = new Font(this.h, 12.0F, 0);
    }

    public PdfLayoutEnginer(ExtWriter var1, ExtRequest var2, ExtResponse var3, ExtEnvirContext var4, InputOption var5) {
        this.a = var1;
        this.b = var2;
        this.c = var3;
        this.d = var4;
        this.e = var5;
    }

    public void buildCustom(CustomContext var1) {
    }

    public void buildChart(ChartData var1) throws IOException, DocumentException {
        if (var1.getTitle() != null && var1.getTitle().length() > 0) {
            Paragraph var2 = new Paragraph(var1.getTitle(), this.i);
            var2.setSpacingAfter(10.0F);
            var2.setAlignment(1);
            this.f.add(var2);
        }

        byte[] var3 = ExcelLayoutEnginer.loadChart(var1.getJson(), var1.getPicinfo(), var1.getWidth(), var1.getHeight());
        Image var4 = Image.getInstance(var3);
        var4.setAlignment(1);
        var4.scaleAbsolute((float)((int)((double)var1.getWidth() * 0.7D)), (float)((int)((double)var1.getHeight() * 0.7D)));
        var4.setSpacingAfter(10.0F);
        this.f.add(var4);
    }

    public void buildDataSelect(DateSelectContext var1) throws DocumentException {
        String var2 = var1.getOutValue();
        if (var2 == null || var2.length() == 0) {
            var2 = this.e.getParamValue(var1.getId());
        }

        if (var2 == null || var2.length() == 0) {
            String var3 = var1.getDefaultValue();
            if (var3 != null && var3.length() > 0) {
                var2 = TestUtils.findValue(var1.getDefaultValue(), this.b, this.d);
            }
        }

        this.d.put(var1.getId(), var2);
        this.e.setParamValue(var1.getId(), var2);
        Paragraph var4 = new Paragraph(var1.getDesc() + "：" + var2, this.i);
        var4.setSpacingAfter(10.0F);
        this.f.add(var4);
    }

    public void buildTextfield(TextFieldContext var1) throws DocumentException {
        String var2 = var1.getOutValue();
        if (var2 == null || var2.length() == 0) {
            var2 = this.e.getParamValue(var1.getId());
        }

        if (var2 == null || var2.length() == 0) {
            String var3 = var1.getDefaultValue();
            if (var3 != null && var3.length() > 0) {
                var2 = TestUtils.findValue(var1.getDefaultValue(), this.b, this.d);
            }
        }

        this.d.put(var1.getId(), var2);
        this.e.setParamValue(var1.getId(), var2);
        Paragraph var4 = new Paragraph(var1.getDesc() + "：" + var2, this.i);
        var4.setSpacingAfter(10.0F);
        this.f.add(var4);
    }

    public void buildCrossReport(CrossOutData var1) throws DocumentException {
        int var2 = 0;

        for(int var3 = 0; var3 < var1.getHeaders()[0].length; ++var3) {
            var2 += var1.getHeaders()[0][var3].getColSpan();
        }

        PdfPTable var14 = new PdfPTable(var2);
        var14.setWidthPercentage(100.0F);
        var14.setSpacingAfter(10.0F);
        CrossOutData$Header[][] var4 = var1.getHeaders();

        for(int var5 = 0; var5 < var4.length; ++var5) {
            CrossOutData$Header[] var6 = var4[var5];

            for(int var7 = 0; var7 < var6.length; ++var7) {
                CrossOutData$Header var8 = var6[var7];
                if (var8 != null) {
                    PdfPCell var9 = new PdfPCell(new Paragraph(var8.getName(), this.i));
                    var9.setHorizontalAlignment(1);
                    var9.setColspan(var8.getColSpan());
                    var9.setRowspan(var8.getRowSpan());
                    var9.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    var14.addCell(var9);
                }
            }
        }

        CrossOutData$Data[][] var15 = var1.getDatas();

        for(int var16 = 0; var16 < var15.length; ++var16) {
            CrossOutData$Data[] var17 = var15[var16];

            for(int var18 = 0; var18 < var17.length; ++var18) {
                CrossOutData$Data var19 = var17[var18];
                if (var19 != null) {
                    if (var19.getType() == 1) {
                        Double var10 = (Double)var19.getTrueValue();
                        String var11 = var19.getFmt();
                        String var12 = var10 == null ? "-" : var10.toString();
                        if (var10 != null && var11 != null && var11.length() > 0) {
                            DecimalFormat var13 = new DecimalFormat(var11);
                            var12 = var13.format(var10);
                        }

                        PdfPCell var21 = new PdfPCell(new Paragraph(var12, this.i));
                        var21.setHorizontalAlignment(2);
                        var14.addCell(var21);
                    } else {
                        PdfPCell var20 = new PdfPCell(new Paragraph(var19.getValue() == null ? "" : var19.getValue().toString(), this.i));
                        var20.setColspan(var19.getColSpan());
                        var20.setRowspan(var19.getRowSpan());
                        var20.setHorizontalAlignment(0);
                        var14.addCell(var20);
                    }
                }
            }
        }

        this.f.add(var14);
    }

    public void buildGridReport(GridReportContext var1) throws IOException, DocumentException {
        GridWriter2PDF var2 = new GridWriter2PDF(var1, this.f, this.a, this.d, this.e);
        var2.begin();
        var2.writeHeader();
        var2.writeDetail();
        var2.writeFooter();
        var2.end();
    }

    public void buildText(TextContext var1) throws DocumentException {
        String var2 = var1.getText();
        if (var2 != null && var2.indexOf("<style>") < 0) {
            TextProperty var3 = var1.getTextProperty();
            Font var4 = new Font(this.h, 12.0F, 0);
            Paragraph var5 = new Paragraph();
            if (var3 != null && var3.getAlign() != null && var3.getAlign().length() > 0) {
                if ("left".equals(var3.getAlign())) {
                    var5.setAlignment(0);
                } else if ("right".equals(var3.getAlign())) {
                    var5.setAlignment(2);
                } else {
                    var5.setAlignment(1);
                }
            }

            if (var1.getFormatHtml() != null && var1.getFormatHtml()) {
                List var14 = null;
                try {
                    var14 = ParserUtils.parserHtml(var1.getText());
                } catch (ParserException e1) {
                    e1.printStackTrace();
                }
                Iterator var8 = var14.iterator();

                while(var8.hasNext()) {
                    TextProperty var15 = (TextProperty)var8.next();
                    Font var9 = new Font(this.h, 12.0F, 0);
                    Chunk var10 = new Chunk(var15.getText());
                    String var11 = var15.getSize();
                    if (var11 != null && var11.length() > 0) {
                        int var12 = Integer.parseInt(var11);
                        var9.setSize((float)(var12 * 2 + 6));
                    }

                    String var16 = var15.getColor();
                    if (var16 != null && var16.length() > 0) {
                        int[] var13 = ColorUtils.HEX2RGB(var16);
                        var9.setColor(var13[0], var13[1], var13[2]);
                    }

                    var10.setFont(var9);
                    var5.add(var10);
                }

                var5.setSpacingAfter(10.0F);
                this.f.add(var5);
            } else {
                Chunk var6 = new Chunk(var1.getText());
                var6.setFont(var4);
                var5.add(var6);
                if (var3 != null && var3.getSize() != null && var3.getSize().length() > 0) {
                    var4.setSize(Float.parseFloat(var3.getSize()));
                }

                if (var3 != null && var3.getWeight() != null && "bold".equals(var3.getWeight())) {
                    var4.setStyle(1);
                }

                if (var3 != null && var3.getColor() != null && var3.getColor().length() > 0) {
                    int[] var7 = ColorUtils.HEX2RGB(var3.getColor());
                    var4.setColor(var7[0], var7[1], var7[2]);
                }

                var5.setSpacingAfter(10.0F);
                this.f.add(var5);
            }
        }

    }

    public void buildStart(Element var1) throws DocumentException, IOException {
        this.f = new Document(PageSize.A4, 10.0F, 10.0F, 10.0F, 10.0F);
        this.g = PdfWriter.getInstance(this.f, this.c.getOutputStream());
        this.f.open();
    }

    public void buildEnd(Element var1) {
        if (this.f != null) {
            this.f.close();
        }

        if (this.g != null) {
            this.g.close();
        }

    }

    public void buildMultitSelect(MultiSelectContext var1, String var2) throws DocumentException {
        String var3 = var1.getDesc() + "：";
        List var4 = null;
        List var5 = var1.loadOptions();
        if (var2 != null && var2.length() > 0) {
            String[] var6 = var2.split(",");
            var4 = Arrays.asList(var6);
        }

        for(int var10 = 0; var5 != null && var10 < var5.size(); ++var10) {
            Map var7 = (Map)var5.get(var10);
            String var8 = var7.get("value") == null ? "" : var7.get("value").toString();
            String var9 = (String)var7.get("text");
            if (var4 != null && var8 != null && var4.contains(var8)) {
                var3 = var3 + var9 + ",";
            }
        }

        Paragraph var11 = new Paragraph(var3, this.i);
        var11.setSpacingAfter(10.0F);
        this.f.add(var11);
    }

    public void buildSelect(SelectContext var1) throws DocumentException {
        String var2 = var1.getOutValue();
        if (var2 == null || var2.length() == 0) {
            var2 = this.e.getParamValue(var1.getId());
        }

        String var3;
        if (var2 == null || var2.length() == 0) {
            var3 = var1.getDefaultValue();
            if (var3 != null && var3.length() > 0) {
                var2 = TestUtils.findValue(var1.getDefaultValue(), this.b, this.d);
            }
        }

        this.d.put(var1.getId(), var2);
        this.e.setParamValue(var1.getId(), var2);
        if (var1 instanceof MultiSelectContext) {
            this.buildMultitSelect((MultiSelectContext)var1, var2);
        } else {
            var3 = var1.getDesc() + "：";
            List var4 = var1.loadOptions();
            if (var4 != null) {
                boolean var5 = false;

                for(int var6 = 0; var6 < var4.size(); ++var6) {
                    Map var7 = (Map)var4.get(var6);
                    String var8 = (String)var7.get("value");
                    String var9 = (String)var7.get("text");
                    if (var8.equals(var2)) {
                        var3 = var3 + var9;
                        var5 = true;
                    }
                }

                if (!var5 && var4.size() > 0) {
                    String var11 = (String)((Map)var4.get(0)).get("text");
                    String var12 = (String)((Map)var4.get(0)).get("value");
                    var3 = var3 + var11;
                    this.d.put(var1.getId(), var12);
                    this.e.setParamValue(var1.getId(), var12);
                }
            }

            Paragraph var10 = new Paragraph(var3, this.i);
            var10.setSpacingAfter(10.0F);
            this.f.add(var10);
        }
    }

    public void buildImage(ImageContext var1) throws IOException, DocumentException {
        Object var2 = null;
        byte[] var11;
        if ("local".equals(var1.getType())) {
            String var3 = ExtContext.getInstance().getConstant("upFilePath");
            var11 = FileUtils.readFileToByteArray(new File(var3 + var1.getPath()));
        } else {
            URL var12 = new URL(var1.getUrl());
            InputStream var4 = null;

            try {
                HttpURLConnection var5 = (HttpURLConnection)var12.openConnection();
                var4 = var5.getInputStream();
                var11 = IOUtils.toByteArray(var4);
            } catch (Exception var9) {
                throw var9;
            } finally {
                IOUtils.closeQuietly(var4);
            }
        }

        Image var13 = Image.getInstance(var11);
        if ("left".equals(var1.getAlign())) {
            var13.setAlignment(0);
        } else if ("right".equals(var1.getAlign())) {
            var13.setAlignment(2);
        } else if ("center".equals(var1.getAlign())) {
            var13.setAlignment(1);
        }

        if (var1.getWidth() != null) {
            var13.scaleAbsoluteWidth((float)var1.getWidth());
        }

        if (var1.getHeight() != null) {
            var13.scaleAbsoluteHeight((float)var1.getHeight());
        }

        var13.setSpacingAfter(10.0F);
        this.f.add(var13);
    }
}
