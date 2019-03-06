//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.excel;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.cross.CrossOutData;
import com.ruisi.ext.engine.cross.CrossOutData$Data;
import com.ruisi.ext.engine.cross.CrossOutData$Header;
import com.ruisi.ext.engine.gridreport.GridWriter2Excel;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.view.builder.PageBuilder$JSObject;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.form.DateSelectContext;
import com.ruisi.ext.engine.view.context.form.MultiSelectContext;
import com.ruisi.ext.engine.view.context.form.SelectContext;
import com.ruisi.ext.engine.view.context.form.TextFieldContext;
import com.ruisi.ext.engine.view.context.grid.ColConfigContext;
import com.ruisi.ext.engine.view.context.grid.ColContext;
import com.ruisi.ext.engine.view.context.grid.DataGridContext;
import com.ruisi.ext.engine.view.context.gridreport.GridReportContext;
import com.ruisi.ext.engine.view.context.html.CustomContext;
import com.ruisi.ext.engine.view.context.html.ImageContext;
import com.ruisi.ext.engine.view.context.html.KpiDescContext;
import com.ruisi.ext.engine.view.context.html.TextContext;
import com.ruisi.ext.engine.view.context.html.TextProperty;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.view.test.TestUtils;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtResponse;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import jxl.write.Number;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mozilla.javascript.Function;
import sun.misc.BASE64Decoder;

public class ExcelLayoutEnginer {
    private static Log a = LogFactory.getLog(ExcelLayoutEnginer.class);
    private ExtWriter b;
    private ExtRequest c;
    private ExtResponse d;
    private InputOption e;
    private List f = new ArrayList();
    private ExtEnvirContext g;
    private int h = 1;
    private SimpleDateFormat i = new SimpleDateFormat("yyyy-MM-dd");
    private boolean j = false;

    public ExcelLayoutEnginer(ExcelEmitter var1) {
        this.b = var1.getOut();
        this.c = var1.getRequest();
        this.d = var1.getResponse();
        this.e = var1.getOption();
        this.g = var1.getEnvirCtx();
    }

    public static int getColPos(int var0, CrossOutData$Header[] var1) {
        int var2 = 0;

        for(int var3 = 0; var3 < var0; ++var3) {
            if (var1[var3] == null) {
                ++var2;
            } else {
                var2 += var1[var3].getColSpan();
            }
        }

        return var2;
    }

    public void buildText(TextContext var1) {
        String var2;
        if (var1.getTextProperty() != null && !var1.getTextProperty().isEmpty()) {
            var2 = var1.getText();
            if (var2 != null && var2.indexOf("<style>") < 0 && var2.indexOf("<script") < 0) {
                this.f.add(var1);
            }
        } else {
            var2 = var1.getText();
            if (var2 != null && var2.indexOf("<style>") < 0 && var2.indexOf("<script") < 0) {
                this.f.add(var2);
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

        this.f.add(var3);
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
                var2 = TestUtils.findValue(var1.getDefaultValue(), this.c, this.g);
            }
        }

        this.g.put(var1.getId(), var2);
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
                    String var10 = (String)((Map)var4.get(0)).get("text");
                    String var11 = (String)((Map)var4.get(0)).get("value");
                    var3 = var3 + var10;
                    this.g.put(var1.getId(), var11);
                    this.e.setParamValue(var1.getId(), var11);
                }
            }

            this.f.add(var3);
        }
    }

    public void buildDateSelect(DateSelectContext var1) {
        String var2 = var1.getOutValue();
        if (var2 == null || var2.length() == 0) {
            var2 = this.e.getParamValue(var1.getId());
        }

        if (var2 == null || var2.length() == 0) {
            String var3 = var1.getDefaultValue();
            if (var3 != null && var3.length() > 0) {
                var2 = TestUtils.findValue(var1.getDefaultValue(), this.c, this.g);
            }
        }

        this.g.put(var1.getId(), var2);
        this.e.setParamValue(var1.getId(), var2);
        this.f.add(var1.getDesc() + "：" + var2);
    }

    public void buildTextfield(TextFieldContext var1) {
        String var2 = var1.getOutValue();
        if (var2 == null || var2.length() == 0) {
            var2 = this.e.getParamValue(var1.getId());
        }

        if (var2 == null || var2.length() == 0) {
            String var3 = var1.getDefaultValue();
            if (var3 != null && var3.length() > 0) {
                var2 = TestUtils.findValue(var1.getDefaultValue(), this.c, this.g);
            }
        }

        this.g.put(var1.getId(), var2);
        this.e.setParamValue(var1.getId(), var2);
        this.f.add(var1.getDesc() + "：" + var2);
    }

    public void buildChart(ChartData var1) {
        String var2 = var1.getTitle();
        if (var2 != null && var2.length() > 0) {
            this.f.add(var2);
        }

        this.f.add(var1);
        int var3 = var1.getWidth() / (this.j ? 106 : 58);
        if (var3 - 1 > this.h) {
            this.h = var3 - 1;
        }

    }

    public void buildChart(Object[][] var1) {
        this.f.add(var1);
    }

    public void buildCrossReport(CrossOutData var1) {
        this.f.add(var1);
        if (var1.getDatas().length > 0 && var1.getDatas()[0].length - 1 > this.h) {
            this.h = var1.getDatas()[0].length - 1;
        }

        this.j = true;
    }

    public void buildEndMv(Element var1) {
        try {
            ServletOutputStream var2 = null;
            var2 = this.d.getOutputStream();
            WritableWorkbook var3 = Workbook.createWorkbook(var2);
            WritableSheet var4 = var3.createSheet("页1", 0);
            int var5 = 0;

            for(int var6 = 0; var6 < this.f.size(); ++var6) {
                Object var7 = this.f.get(var6);
                if (var7 instanceof String) {
                    Label var42 = new Label(0, var5, (String)var7);
                    var4.addCell(var42);
                    var4.mergeCells(0, var5, this.h, var5);
                    ++var5;
                } else if (var7 instanceof TextContext) {
                    TextContext var40 = (TextContext)var7;
                    TextProperty var37 = var40.getTextProperty();
                    Label var48 = new Label(0, var5, var40.getText());
                    WritableFont var57 = new WritableFont(WritableFont.ARIAL, var37.getSize() != null && var37.getSize().length() != 0 ? Integer.parseInt(var37.getSize()) : 10, "bold".equals(var37.getWeight()) ? WritableFont.BOLD : WritableFont.NO_BOLD, false, var37.getUnderLine() != null && var37.getUnderLine() ? UnderlineStyle.SINGLE : UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
                    WritableCellFormat var59 = new WritableCellFormat(var57);
                    if ("left".equalsIgnoreCase(var37.getAlign())) {
                        var59.setAlignment(Alignment.LEFT);
                    }

                    if ("center".equalsIgnoreCase(var37.getAlign())) {
                        var59.setAlignment(Alignment.CENTRE);
                    }

                    if ("right".equalsIgnoreCase(var37.getAlign())) {
                        var59.setAlignment(Alignment.RIGHT);
                    }

                    var48.setCellFormat(var59);
                    var4.addCell(var48);
                    var4.mergeCells(0, var5, this.h, var5);
                    ++var5;
                } else {
                    int var10;
                    int var41;
                    WritableImage var53;
                    if (var7 instanceof ChartData) {
                        ChartData var39 = (ChartData)var7;
                        byte[] var36 = loadChart(var39.getJson(), var39.getPicinfo(), var39.getWidth(), var39.getHeight());
                        var10 = var39.getWidth() / (this.j ? 106 : 58);
                        var41 = var39.getHeight() / 18;
                        var53 = new WritableImage(0.0D, (double)var5, (double)var10, (double)var41, var36);
                        var4.addImage(var53);
                        var5 += var41;
                    } else if (var7 instanceof Object[][]) {
                        for(int var35 = 0; var35 < 3; ++var35) {
                            var4.setColumnView(var35, 20);
                        }

                        Object[][] var38 = (Object[][])var7;

                        for(int var34 = 0; var34 < var38.length; ++var34) {
                            Object[] var45 = var38[var34];

                            for(var41 = 0; var41 < var45.length; ++var41) {
                                WritableCellFormat var50;
                                Label var60;
                                if (var34 == 0) {
                                    WritableFont var55 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
                                    var50 = new WritableCellFormat(var55);
                                    var50.setBackground(Colour.GRAY_25);
                                    var50.setAlignment(Alignment.CENTRE);
                                    var50.setBorder(Border.ALL, BorderLineStyle.THIN);
                                    var50.setVerticalAlignment(VerticalAlignment.CENTRE);
                                    var50.setWrap(true);
                                    var60 = new Label(var41, var34 + var5, (String)var45[var41]);
                                    var60.setCellFormat(var50);
                                    var4.addCell(var60);
                                } else {
                                    Object var56 = var45[var41];
                                    if (var56 instanceof String) {
                                        var50 = new WritableCellFormat();
                                        var50.setWrap(true);
                                        var60 = new Label(var41, var34 + var5, var56 == null ? "" : var56.toString());
                                        var50.setVerticalAlignment(VerticalAlignment.CENTRE);
                                        var50.setBorder(Border.ALL, BorderLineStyle.THIN);
                                        var50.setAlignment(Alignment.LEFT);
                                        var60.setCellFormat(var50);
                                        var4.addCell(var60);
                                    } else {
                                        var50 = new WritableCellFormat();
                                        var50.setAlignment(Alignment.RIGHT);
                                        Object var62 = var56 == null ? new Label(var41, var34 + var5, "-") : new Number(var41, var34 + var5, (Double)var56);
                                        var50.setVerticalAlignment(VerticalAlignment.CENTRE);
                                        var50.setBorder(Border.ALL, BorderLineStyle.THIN);
                                        ((WritableCell)var62).setCellFormat(var50);
                                        var4.addCell((WritableCell)var62);
                                    }
                                }
                            }
                        }
                    } else if (var7 instanceof GridReportContext) {
                        GridWriter2Excel var33 = new GridWriter2Excel((GridReportContext)var7, var4, this.g, this.e, var5);
                        var33.begin();
                        var33.writeHeader();
                        var33.writeDetail();
                        var33.writeFooter();
                        var33.end();
                        var5 += var33.getDataHeight();
                    } else {
                        CrossOutData var8;
                        if (!(var7 instanceof CrossOutData)) {
                            if (var7 instanceof DataGridContext) {
                                this.a((DataGridContext)var7, var3, var4);
                            } else if (var7 instanceof ImageContext) {
                                var8 = null;
                                ImageContext var32 = (ImageContext)var7;
                                byte[] var31;
                                if ("local".equals(var32.getType())) {
                                    String var43 = ExtContext.getInstance().getConstant("upFilePath");
                                    var31 = FileUtils.readFileToByteArray(new File(var43 + var32.getPath()));
                                } else {
                                    URL var44 = new URL(var32.getUrl());
                                    InputStream var47 = null;

                                    try {
                                        HttpURLConnection var52 = (HttpURLConnection)var44.openConnection();
                                        var47 = var52.getInputStream();
                                        var31 = IOUtils.toByteArray(var47);
                                    } catch (Exception var28) {
                                        throw var28;
                                    } finally {
                                        IOUtils.closeQuietly(var47);
                                    }
                                }

                                var10 = var32.getWidth() / (this.j ? 106 : 58);
                                var41 = var32.getHeight() / 18;
                                var53 = new WritableImage(0.0D, (double)var5, (double)var10, (double)var41, var31);
                                var4.addImage(var53);
                                var5 += var41;
                            }
                        } else {
                            var8 = (CrossOutData)var7;
                            CrossOutData$Header[][] var9 = var8.getHeaders();
                            if (var9.length == 0) {
                                return;
                            }

                            int var16;
                            for(var10 = 0; var10 < var9.length; ++var10) {
                                CrossOutData$Header[] var11 = var9[var10];

                                for(int var12 = 0; var12 < var11.length; ++var12) {
                                    CrossOutData$Header var13 = var11[var12];
                                    if (var13 != null) {
                                        WritableFont var14 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
                                        WritableCellFormat var15 = new WritableCellFormat(var14);
                                        var15.setBackground(Colour.GRAY_25);
                                        var15.setAlignment(Alignment.CENTRE);
                                        var15.setBorder(Border.ALL, BorderLineStyle.THIN);
                                        var15.setVerticalAlignment(VerticalAlignment.CENTRE);
                                        var15.setWrap(true);
                                        var16 = getColPos(var12, var11);
                                        Label var17 = new Label(var16, var10 + var5, var13.getName());
                                        var17.setCellFormat(var15);
                                        var4.addCell(var17);
                                        if (var13.getColSpan() > 1 || var13.getRowSpan() > 1) {
                                            var4.mergeCells(var16, var10 + var5, var16 + var13.getColSpan() - 1, var10 + var5 + var13.getRowSpan() - 1);
                                        }
                                    }
                                }
                            }

                            var10 = 0;

                            for(var41 = 0; var41 < var9[0].length; ++var41) {
                                if (var9[0][var41] == null) {
                                    ++var10;
                                } else {
                                    var10 += var9[0][var41].getColSpan();
                                }
                            }

                            for(var41 = 0; var41 < var10; ++var41) {
                                byte var46 = 15;
                                if (var41 < var8.getRowLevel()) {
                                    var46 = 20;
                                }

                                var4.setColumnView(var41, var46);
                            }

                            var41 = var9.length;
                            CrossOutData$Data[][] var51 = var8.getDatas();
                            if (var51.length == 0) {
                                break;
                            }

                            HashMap var49 = new HashMap();

                            int var54;
                            for(var54 = 0; var54 < var51[0].length; ++var54) {
                                CrossOutData$Data var58 = var51[0][var54];
                                if (var58 != null && var58.getType() == 1) {
                                    String var63 = var58.getFmt();
                                    var49.put(String.valueOf(var54), var63 == null ? null : new NumberFormat(var63));
                                }
                            }

                            for(var54 = 0; var54 < var51.length; ++var54) {
                                CrossOutData$Data[] var61 = var51[var54];
                                var16 = compNullCount(var61);

                                for(int var64 = 0; var64 < var61.length; ++var64) {
                                    CrossOutData$Data var18 = var61[var64];
                                    if (var18 != null) {
                                        Object var19 = var18.getValue();
                                        Double var20 = (Double)var18.getTrueValue();
                                        boolean var21 = false;
                                        if (var18.getRowSpan() > 1) {
                                            var4.mergeCells(var16, var54 + var41 + var5, var16, var54 + var41 + var5 + var18.getRowSpan() - 1);
                                        }

                                        WritableCellFormat var22;
                                        Label var23;
                                        if (var18.getColSpan() > 1) {
                                            var4.mergeCells(var16 - (var18.getColSpan() - 1), var54 + var41 + var5, var16, var54 + var41 + var5);
                                            var22 = new WritableCellFormat();
                                            var22.setWrap(true);
                                            var22.setVerticalAlignment(VerticalAlignment.CENTRE);
                                            var22.setBorder(Border.ALL, BorderLineStyle.THIN);
                                            var23 = new Label(var16 - (var18.getColSpan() - 1), var54 + var41 + var5, var19 == null ? "" : var19.toString());
                                            var23.setCellFormat(var22);
                                            var4.addCell(var23);
                                            var21 = true;
                                        }

                                        if (var18.getType() == 1) {
                                            NumberFormat var65 = (NumberFormat)var49.get(String.valueOf(var64));
                                            WritableCellFormat var66;
                                            if (var65 != null && var20 != null) {
                                                var66 = new WritableCellFormat(var65);
                                            } else {
                                                var66 = new WritableCellFormat();
                                            }

                                            var66.setAlignment(Alignment.RIGHT);
                                            Object var24 = var20 == null ? new Label(var16, var54 + var41 + var5, "-") : new Number(var16, var54 + var41 + var5, var20);
                                            var66.setVerticalAlignment(VerticalAlignment.CENTRE);
                                            var66.setBorder(Border.ALL, BorderLineStyle.THIN);
                                            ((WritableCell)var24).setCellFormat(var66);
                                            var4.addCell((WritableCell)var24);
                                        } else {
                                            var22 = new WritableCellFormat();
                                            var22.setWrap(true);
                                            var23 = new Label(var16, var54 + var41 + var5, var19 == null ? "" : var19.toString());
                                            var22.setVerticalAlignment(VerticalAlignment.CENTRE);
                                            var22.setBorder(Border.ALL, BorderLineStyle.THIN);
                                            if (var18.getType() == 1) {
                                                var22.setAlignment(Alignment.RIGHT);
                                            }

                                            var23.setCellFormat(var22);
                                            if (!var21) {
                                                var4.addCell(var23);
                                            }
                                        }

                                        ++var16;
                                    }
                                }
                            }

                            var5 = var5 + var51.length + var9.length;
                        }
                    }
                }
            }

            var3.write();
            var3.close();
        } catch (Exception var30) {
            var30.printStackTrace();
        }

    }

    public static int compNullCount(CrossOutData$Data[] var0) {
        int var1 = 0;
        CrossOutData$Data[] var5 = var0;
        int var4 = var0.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            CrossOutData$Data var2 = var5[var3];
            if (var2 == null) {
                ++var1;
            }
        }

        return var1;
    }

    public void buildKpiDesc(KpiDescContext var1) {
        this.f.add("");
        this.f.add("指标解释：");
        List var2 = var1.loadOptions();

        for(int var3 = 0; var3 < var2.size(); ++var3) {
            StringBuffer var4 = new StringBuffer();
            Map var5 = (Map)var2.get(var3);
            var4.append("  ");
            var4.append(var5.get("name"));
            var4.append("：(");
            var4.append(var5.get("unit"));
            var4.append(") ");
            String var6 = (String)var5.get("name_desc");
            var4.append(var6 == null ? "" : var6);
            this.f.add(var4.toString());
        }

    }

    public void buildDataGrid(DataGridContext var1) {
        this.f.add(var1);
    }

    public void buildGridReport(GridReportContext var1) {
        int var2 = var1.getDetails()[0].length - 1;
        if (var2 > this.h) {
            this.h = var2;
        }

        this.f.add(var1);
    }

    private void a(DataGridContext var1, WritableWorkbook var2, WritableSheet var3) throws WriteException {
        ColConfigContext var4 = var1.getColConfigContext();
        List var5 = var4.getColContexts();
        List var6 = var1.loadOptions();

        int var7;
        for(var7 = 0; var7 < var5.size(); ++var7) {
            var3.setColumnView(var7, 20);
        }

        this.a(var5, var3);
        var7 = 0;
        int var8 = 1;
        short var9 = 20000;

        for(int var10 = 0; var10 < var6.size(); ++var10) {
            ++var7;
            if (var7 >= var9) {
                var7 = 1;
                var3 = var2.createSheet("页" + (var8 + 1), var8);
                ++var8;

                for(int var11 = 0; var11 < var5.size(); ++var11) {
                    var3.setColumnView(var11, 20);
                }

                this.a(var5, var3);
            }

            Map var20 = (Map)var6.get(var10);

            for(int var12 = 0; var12 < var5.size(); ++var12) {
                ColContext var13 = (ColContext)var5.get(var12);
                String var14 = var13.getJsFunc();
                if (var14 != null && var14.length() != 0) {
                    PageBuilder$JSObject var21 = (PageBuilder$JSObject)this.c.getAttribute("ext.script.engine");
                    Object var16 = var21.getScope().get(var14, var21.getScope());
                    if (var16 == null || !(var16 instanceof Function)) {
                        String var23 = ConstantsEngine.replace("定义的 jsFunc 方法 $0 未找到.", var14);
                        throw new ExtRuntimeException(var23);
                    }

                    Function var17 = (Function)var16;
                    Object[] var18 = new Object[]{var20, var13.getAlias(), "xls"};
                    var17.call(var21.getCt(), var21.getScope(), var21.getScope(), var18);
                    ByteArrayOutputStream var22 = (ByteArrayOutputStream)this.b.getProxy();

                    try {
                        var22.flush();
                    } catch (Exception var19) {
                        var19.printStackTrace();
                    }

                    String var24 = new String(var22.toByteArray());
                    this.a(var24, var3, var7, var12, var13);
                    var22.reset();
                } else {
                    Object var15 = var20.get(var13.getAlias());
                    this.a(var15, var3, var7, var12, var13);
                }
            }
        }

    }

    private void a(List var1, WritableSheet var2) throws WriteException {
        for(int var3 = 0; var3 < var1.size(); ++var3) {
            ColContext var4 = (ColContext)var1.get(var3);
            WritableFont var5 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat var6 = new WritableCellFormat(var5);
            Label var7 = new Label(var3, 0, var4.getDesc());
            var7.setCellFormat(var6);
            var2.addCell(var7);
        }

    }

    private void a(Object var1, WritableSheet var2, int var3, int var4, ColContext var5) throws WriteException {
        Object var6 = null;
        if (var1 instanceof Date) {
            Date var7 = (Date)var1;
            var6 = new Label(var4, var3, var7 == null ? "" : this.i.format(var7));
        } else if (var1 instanceof BigDecimal) {
            var6 = new Number(var4, var3, ((BigDecimal)var1).doubleValue());
        } else {
            var6 = new Label(var4, var3, var1 == null ? "" : var1.toString());
        }

        var2.addCell((WritableCell)var6);
    }

    public static byte[] loadChart(String var0, String var1, int var2, int var3) {
        if (var0 != null) {
            String var20 = ExtContext.getInstance().getConstant("highchartExport");
            HttpURLConnection var5 = null;

            try {
                String var7 = "content=options&options=" + URLEncoder.encode(var0, "UTF-8") + "&width=&scale=";
                URL var6 = new URL(var20);
                var5 = (HttpURLConnection)var6.openConnection();
                var5.setRequestMethod("POST");
                var5.setConnectTimeout(10000);
                var5.setReadTimeout(10000);
                var5.setDoOutput(true);
                var5.setDoInput(true);
                OutputStream var8 = var5.getOutputStream();
                IOUtils.write(var7, var8, "UTF-8");
                var8.flush();
                var8.close();
                int var9 = var5.getResponseCode();
                if (var9 != 200) {
                    throw new Exception("highchart 服务器连接异常。 code = " + var9);
                }

                InputStream var10 = var5.getInputStream();
                byte[] var11 = IOUtils.toByteArray(var10);
                var10.close();
                byte[] var13 = var11;
                return var13;
            } catch (Exception var18) {
                a.error("获取highchart图形出错。", var18);
            } finally {
                var5.disconnect();
            }

            return null;
        } else {
            byte[] var4 = null;

            try {
                var4 = (new BASE64Decoder()).decodeBuffer(var1);
            } catch (IOException var17) {
                var17.printStackTrace();
            }

            return var4;
        }
    }

    public static byte[] createChartInProcess(String var0, ServletContext var1) throws Exception {
        Object var2 = null;
        String var3 = "";
        String var4 = var1.getRealPath("WEB-INF/bin/hctoimage");
        String var5 = var4 + "/phantomjs.exe \"" + var4 + "/highcharts-convert.js\" ";
        String var6 = var4 + "/tmp/" + UUID.randomUUID().toString();
        File var7 = new File(var6 + ".json");
        File var8 = new File(var6 + ".jpg");
        FileUtils.writeStringToFile(var7, var0, "UTF-8");
        var3 = " -infile \"" + var7 + "\"" + " -outfile \"" + var8 + "\"";
        String var9 = var5 + " " + var3;
        short var10 = 30000;
        Process var11 = null;

        byte[] var14;
        try {
            var11 = Runtime.getRuntime().exec(var9);

            for(int var12 = 0; var12 * 100 < var10 && !var8.exists(); ++var12) {
                Thread.sleep(50L);
            }

            if (!var8.exists()) {
                throw new Exception("生成图片失败");
            }

            byte[] var23 = FileUtils.readFileToByteArray(var8);
            var14 = var23;
        } catch (Exception var21) {
            var21.printStackTrace();
            throw var21;
        } finally {
            try {
                var11.destroy();
                var7.delete();
                var8.delete();
            } catch (Exception var20) {
                var20.printStackTrace();
            }

        }

        return var14;
    }

    public void buildCustom(CustomContext var1) {
        this.f.add(var1);
    }

    public void buildImage(ImageContext var1) {
        this.f.add(var1);
    }
}
