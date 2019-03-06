//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.word;

import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.cross.CrossOutData;
import com.ruisi.ext.engine.cross.CrossOutData$Data;
import com.ruisi.ext.engine.cross.CrossOutData$Header;
import com.ruisi.ext.engine.gridreport.GridWriter2Word;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.view.builder.html.parser.ParserUtils;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.form.DateSelectContext;
import com.ruisi.ext.engine.view.context.form.MultiSelectContext;
import com.ruisi.ext.engine.view.context.form.SelectContext;
import com.ruisi.ext.engine.view.context.form.TextFieldContext;
import com.ruisi.ext.engine.view.context.gridreport.GridReportContext;
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
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.apache.xmlbeans.XmlToken.Factory;
import org.htmlparser.util.ParserException;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc.Enum;

public class WordLayoutEnginer {
    private ExtWriter a;
    private ExtRequest b;
    private ExtResponse c;
    private ExtEnvirContext d;
    private InputOption e;
    private XWPFDocument f;

    public WordLayoutEnginer(ExtWriter var1, ExtRequest var2, ExtResponse var3, ExtEnvirContext var4, InputOption var5) {
        this.a = var1;
        this.b = var2;
        this.c = var3;
        this.d = var4;
        this.e = var5;
    }

    public void buildChart(ChartData var1) throws InvalidFormatException {
        byte[] var3 = ExcelLayoutEnginer.loadChart(var1.getJson(), var1.getPicinfo(), var1.getWidth(), var1.getHeight());
        this.f.addPictureData(var3, 5);
        updatePicture(this.f, (XWPFParagraph)null, 0, var1.getWidth(), var1.getHeight());
    }

    public void buildTextfield(TextFieldContext var1) {
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
        XWPFParagraph var5 = this.f.createParagraph();
        XWPFRun var4 = var5.createRun();
        var4.setTextPosition(5);
        var4.setText(var1.getDesc() + "：" + var2);
    }

    public void buildDateSelect(DateSelectContext var1) {
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
        XWPFParagraph var5 = this.f.createParagraph();
        XWPFRun var4 = var5.createRun();
        var4.setTextPosition(5);
        var4.setText(var1.getDesc() + "：" + var2);
    }

    public static void updatePicture(XWPFDocument var0, XWPFParagraph var1, int var2, int var3, int var4) {
        if (var2 == 0) {
            var2 = var0.getAllPictures().size() - 1;
        }

        boolean var5 = true;
        var3 *= 9525;
        var4 *= 9525;
        String var6 = ((XWPFPictureData)var0.getAllPictures().get(var2)).getPackageRelationship().getId();
        CTInline var7 = null;
        if (var1 == null) {
            var7 = var0.createParagraph().createRun().getCTR().addNewDrawing().addNewInline();
        } else {
            var7 = var1.createRun().getCTR().addNewDrawing().addNewInline();
        }

        String var8 = "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">         <pic:nvPicPr>            <pic:cNvPr id=\"" + var2 + "\" name=\"Generated\"/>" + "            <pic:cNvPicPr/>" + "         </pic:nvPicPr>" + "         <pic:blipFill>" + "            <a:blip r:embed=\"" + var6 + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>" + "            <a:stretch>" + "               <a:fillRect/>" + "            </a:stretch>" + "         </pic:blipFill>" + "         <pic:spPr>" + "            <a:xfrm>" + "               <a:off x=\"0\" y=\"0\"/>" + "               <a:ext cx=\"" + var3 + "\" cy=\"" + var4 + "\"/>" + "            </a:xfrm>" + "            <a:prstGeom prst=\"rect\">" + "               <a:avLst/>" + "            </a:prstGeom>" + "         </pic:spPr>" + "      </pic:pic>" + "   </a:graphicData>" + "</a:graphic>";
        var7.addNewGraphic().addNewGraphicData();
        XmlToken var9 = null;

        try {
            var9 = Factory.parse(var8);
        } catch (XmlException var12) {
            var12.printStackTrace();
        }

        var7.set(var9);
        var7.setDistT(0L);
        var7.setDistB(0L);
        var7.setDistL(0L);
        var7.setDistR(0L);
        CTPositiveSize2D var10 = var7.addNewExtent();
        var10.setCx((long)var3);
        var10.setCy((long)var4);
        CTNonVisualDrawingProps var11 = var7.addNewDocPr();
        var11.setId((long)var2);
        var11.setName("IMG_" + var2);
        var11.setDescr("IMG_" + var2);
    }

    private XWPFTable a(CrossOutData var1) {
        int var2 = var1.getHeaders().length + var1.getDatas().length;
        int var3 = 0;
        CrossOutData$Header[] var4 = var1.getHeaders()[0];
        CrossOutData$Header[] var8 = var4;
        int var7 = var4.length;

        for(int var6 = 0; var6 < var7; ++var6) {
            CrossOutData$Header var5 = var8[var6];
            var3 += var5.getColSpan();
        }

        XWPFTable var10 = this.f.createTable(var2, var3);
        CTTbl var11 = var10.getCTTbl();
        CTTblPr var12 = var11.getTblPr() == null ? var11.addNewTblPr() : var11.getTblPr();
        CTTblWidth var13 = var12.isSetTblW() ? var12.getTblW() : var12.addNewTblW();
        CTJc var9 = var12.addNewJc();
        var9.setVal(Enum.forString("center"));
        var13.setW(new BigInteger("8000"));
        var13.setType(STTblWidth.DXA);
        return var10;
    }

    public void buildCrossReport(CrossOutData var1) {
        CrossOutData$Header[][] var2 = var1.getHeaders();
        if (var2.length != 0) {
            int var3 = 0;
            XWPFTable var4 = this.a(var1);

            int var8;
            int var10;
            for(int var5 = 0; var5 < var2.length; ++var5) {
                CrossOutData$Header[] var6 = var2[var5];
                XWPFTableRow var7 = var4.getRow(var3);
                var7.setHeight(500);

                for(var8 = 0; var8 < var6.length; ++var8) {
                    CrossOutData$Header var9 = var6[var8];
                    if (var9 != null) {
                        var10 = ExcelLayoutEnginer.getColPos(var8, var6);
                        XWPFTableCell var11 = var7.getCell(var10);
                        CTTcPr var12 = var11.getCTTc().addNewTcPr();
                        CTShd var13 = var12.isSetShd() ? var12.getShd() : var12.addNewShd();
                        var12.addNewTcW().setW(BigInteger.valueOf(3000L));
                        var11.setVerticalAlignment(XWPFVertAlign.CENTER);
                        CTTc var14 = var11.getCTTc();
                        ((CTP)var14.getPList().get(0)).addNewPPr().addNewJc().setVal(STJc.CENTER);
                        if (var9.getRowSpan() > 1) {
                            GridWriter2Word.mergeCellsVertically(var4, var10, var3, var3 + var9.getRowSpan() - 1);
                        }

                        if (var9.getColSpan() > 1) {
                            GridWriter2Word.mergeCellsHorizontal(var4, var3, var10, var10 + var9.getColSpan() - 1);
                        }

                        var13.setFill("EFEFEF");
                        var11.setText(var9.getName());
                    }
                }

                ++var3;
            }

            CrossOutData$Data[][] var18 = var1.getDatas();
            if (var18.length != 0) {
                for(int var19 = 0; var19 < var18.length; ++var19) {
                    CrossOutData$Data[] var20 = var18[var19];
                    var8 = ExcelLayoutEnginer.compNullCount(var20);
                    XWPFTableRow var21 = var4.getRow(var3);
                    var21.setHeight(500);

                    for(var10 = 0; var10 < var20.length; ++var10) {
                        CrossOutData$Data var22 = var20[var10];
                        if (var22 != null) {
                            Object var23 = var22.getValue();
                            int var24 = var8;
                            if (var22.getColSpan() > 1) {
                                int var25 = var1.getRowLevel() - 1 - var22.getLevel();
                                var24 = var8 - var25;
                            }

                            XWPFTableCell var26 = var21.getCell(var24);
                            CTTcPr var15 = var26.getCTTc().addNewTcPr();
                            var15.addNewTcW().setW(BigInteger.valueOf(3000L));
                            var26.setVerticalAlignment(XWPFVertAlign.CENTER);
                            CTTc var16 = var26.getCTTc();
                            if (var22.getType() == 1) {
                                ((CTP)var16.getPList().get(0)).addNewPPr().addNewJc().setVal(STJc.RIGHT);
                            } else {
                                ((CTP)var16.getPList().get(0)).addNewPPr().addNewJc().setVal(STJc.LEFT);
                            }

                            if (var22.getRowSpan() > 1) {
                                GridWriter2Word.mergeCellsVertically(var4, var8, var3, var3 + var22.getRowSpan() - 1);
                            }

                            if (var22.getColSpan() > 1) {
                                int var17 = var1.getRowLevel() - 1 - var22.getLevel();
                                GridWriter2Word.mergeCellsHorizontal(var4, var3, var8 - var17, var8 + var22.getColSpan() - 1 - var17);
                            }

                            var26.setText(var23 == null ? "-" : var23.toString());
                            ++var8;
                        }
                    }

                    ++var3;
                }

            }
        }
    }

    public void buildGridReport(GridReportContext var1) {
        GridWriter2Word var2 = new GridWriter2Word(var1, this.f, this.a, this.d, this.e);
        var2.begin();
        var2.writeHeader();
        var2.writeDetail();
        var2.writeFooter();
        var2.end();
    }

    public void buildText(TextContext var1) {
        String var2 = var1.getText();
        if (var2 != null && var2.indexOf("<style>") < 0) {
            XWPFParagraph var3 = this.f.createParagraph();
            var3.setWordWrap(true);
            TextProperty var4 = var1.getTextProperty();
            if (var4 != null) {
                String var5 = var4.getAlign();
                if (var5 != null && var5.length() > 0) {
                    if ("center".equals(var5)) {
                        var3.setAlignment(ParagraphAlignment.CENTER);
                    } else if ("left".equals(var5)) {
                        var3.setAlignment(ParagraphAlignment.LEFT);
                    } else {
                        var3.setAlignment(ParagraphAlignment.RIGHT);
                    }
                }
            }

            if (var1.getFormatHtml() != null && var1.getFormatHtml()) {
                List var15 = null;
                try {
                    var15 = ParserUtils.parserHtml(var1.getText());
                } catch (ParserException e1) {
                    e1.printStackTrace();
                }
                Iterator var17 = var15.iterator();

                while(var17.hasNext()) {
                    TextProperty var16 = (TextProperty)var17.next();
                    XWPFRun var18 = var3.createRun();
                    String var9 = var16.getSize();
                    if (var9 != null && var9.length() > 0) {
                        int var10 = Integer.parseInt(var9);
                        var18.setFontSize(var10 * 3 + 5);
                    }

                    String var19 = var16.getColor();
                    if (var19 != null && var19.length() > 0) {
                        var18.setColor(var19.replaceFirst("#", ""));
                    }

                    String[] var11 = var16.getText().split("\n");

                    for(int var12 = 0; var12 < var11.length; ++var12) {
                        String var13 = var11[var12];
                        var18.setText(var13);
                        if (var12 != var11.length - 1) {
                            var18.addBreak();
                        }
                    }
                }
            } else {
                XWPFRun var14 = var3.createRun();
                if (var4 != null) {
                    String var6 = var4.getSize();
                    if (var6 != null && var6.length() > 0) {
                        var14.setFontSize(Integer.parseInt(var6));
                    }

                    String var7 = var4.getWeight();
                    if ("bold".equals(var7)) {
                        var14.setBold(true);
                    }

                    String var8 = var4.getColor();
                    if (var8 != null && var8.length() > 0) {
                        var14.setColor(var8.replaceFirst("#", ""));
                    }
                }

                var14.setTextPosition(5);
                var14.setText(var1.getText());
            }

        }
    }

    public void buildStart(Element var1) {
        this.f = new XWPFDocument();
    }

    public void buildEnd(Element var1) throws IOException {
        ServletOutputStream var2 = this.c.getOutputStream();
        this.f.write(var2);
        if (var2 != null) {
            try {
                var2.close();
            } catch (IOException var4) {
                var4.printStackTrace();
            }
        }

    }

    public void buildMultitSelect(MultiSelectContext var1, String var2) {
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

        XWPFParagraph var11 = this.f.createParagraph();
        XWPFRun var12 = var11.createRun();
        var12.setTextPosition(5);
        var12.setText(var3);
    }

    public void buildSelect(SelectContext var1) {
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
                    String var13 = (String)((Map)var4.get(0)).get("value");
                    var3 = var3 + var11;
                    this.d.put(var1.getId(), var13);
                    this.e.setParamValue(var1.getId(), var13);
                }
            }

            XWPFParagraph var10 = this.f.createParagraph();
            XWPFRun var12 = var10.createRun();
            var12.setTextPosition(5);
            var12.setText(var3);
        }
    }

    public void buildImage(ImageContext var1) throws IOException, InvalidFormatException {
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

        this.f.addPictureData(var11, 5);
        updatePicture(this.f, (XWPFParagraph)null, 0, var1.getWidth(), var1.getHeight());
    }
}
