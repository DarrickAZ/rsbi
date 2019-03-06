//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.text;

import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.cross.CrossOutData;
import com.ruisi.ext.engine.cross.CrossOutData$Data;
import com.ruisi.ext.engine.cross.CrossOutData$Header;
import com.ruisi.ext.engine.gridreport.GridWriter2Text;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.view.context.form.MultiSelectContext;
import com.ruisi.ext.engine.view.context.form.SelectContext;
import com.ruisi.ext.engine.view.context.gridreport.GridReportContext;
import com.ruisi.ext.engine.view.context.html.TextContext;
import com.ruisi.ext.engine.view.emitter.excel.ExcelLayoutEnginer;
import com.ruisi.ext.engine.view.test.TestUtils;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtResponse;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TextLayoutEnginer {
    private ExtWriter a;
    private ExtRequest b;
    private List c = new ArrayList();
    private int d = 1;
    private ExtEnvirContext e;
    private InputOption f;

    public TextLayoutEnginer(ExtWriter var1, ExtRequest var2, ExtResponse var3, ExtEnvirContext var4, InputOption var5) {
        this.a = var1;
        this.b = var2;
        this.e = var4;
        this.f = var5;
    }

    public void buildText(TextContext var1) {
        String var2 = var1.getText();
        if (var2 != null && var2.indexOf("<style>") < 0) {
            this.c.add(var1.getText());
        }

    }

    public void buildGridReport(GridReportContext var1) {
        this.c.add(var1);
    }

    public void buildChart(ChartContext var1) {
        this.c.add(var1);
    }

    public void buildCrossReport(CrossOutData var1) {
        this.c.add(var1);
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

        this.c.add(var3);
    }

    public void buildSelect(SelectContext var1) {
        String var2 = var1.getOutValue();
        if (var2 == null || var2.length() == 0) {
            var2 = this.f.getParamValue(var1.getId());
        }

        String var3;
        if (var2 == null || var2.length() == 0) {
            var3 = var1.getDefaultValue();
            if (var3 != null && var3.length() > 0) {
                var2 = TestUtils.findValue(var1.getDefaultValue(), this.b, this.e);
            }
        }

        this.e.put(var1.getId(), var2);
        this.f.setParamValue(var1.getId(), var2);
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
                    if (var8 != null && var8.equals(var2)) {
                        var3 = var3 + var9;
                        var5 = true;
                    }
                }

                if (!var5 && var4.size() > 0) {
                    String var10 = (String)((Map)var4.get(0)).get("text");
                    String var11 = (String)((Map)var4.get(0)).get("value");
                    var3 = var3 + var10;
                    this.e.put(var1.getId(), var11);
                    this.f.setParamValue(var1.getId(), var11);
                }
            }

            this.c.add(var3);
        }
    }

    public void buildEndMv(Element var1) {
        try {
            for(int var2 = 0; var2 < this.c.size(); ++var2) {
                Object var3 = this.c.get(var2);
                if (var3 instanceof String) {
                    this.a.print("\"");
                    this.a.print((String)var3);
                    this.a.print("\"");
                    this.a.println("");
                }

                if (var3 instanceof GridReportContext) {
                    GridWriter2Text var4 = new GridWriter2Text((GridReportContext)var3, this.a, this.e, this.f);
                    var4.begin();
                    var4.writeHeader();
                    var4.writeDetail();
                    var4.writeFooter();
                    var4.end();
                }

                int var6;
                if (var3 instanceof ChartContext) {
                    ChartContext var14 = (ChartContext)var3;
                    List var5 = var14.loadOptions();

                    for(var6 = 0; var6 < var5.size(); ++var6) {
                        Map var7 = (Map)var5.get(var6);
                        Object var8 = var7.get(var14.getXcol());
                        this.a.print("\"");
                        this.a.print(var8 == null ? "" : var8.toString());
                        this.a.print("\"");
                        this.a.print(",");
                        Object var9 = var7.get(var14.getYcol());
                        this.a.print(var9 == null ? "" : var9.toString());
                        this.a.print(",");
                        Object var10 = var7.get(var14.getScol());
                        this.a.print("\"");
                        this.a.print(var10 == null ? "" : var10.toString());
                        this.a.print("\"");
                        this.a.println("");
                    }
                }

                if (var3 instanceof CrossOutData) {
                    CrossOutData var15 = (CrossOutData)var3;
                    CrossOutData$Header[][] var16 = var15.getHeaders();
                    if (var16.length == 0) {
                        return;
                    }

                    for(var6 = 0; var6 < var16.length; ++var6) {
                        CrossOutData$Header[] var18 = var16[var6];

                        for(int var20 = 0; var20 < var18.length; ++var20) {
                            CrossOutData$Header var22 = var18[var20];
                            if (var22 == null) {
                                this.a.print("\"");
                                this.a.print("\"");
                                this.a.print(",");
                            } else {
                                this.a.print("\"");
                                this.a.print(var22.getName());
                                this.a.print("\"");
                                if (var22.getColSpan() > 1) {
                                    for(var2 = 1; var2 < var22.getColSpan(); ++var2) {
                                        this.a.print(",");
                                        this.a.print("\"");
                                        this.a.print("\"");
                                    }
                                }

                                if (var20 != var18.length - 1) {
                                    this.a.print(",");
                                }
                            }
                        }

                        this.a.println("");
                    }

                    CrossOutData$Data[][] var17 = var15.getDatas();

                    for(int var19 = 0; var19 < var17.length; ++var19) {
                        CrossOutData$Data[] var21 = var17[var19];
                        int var23 = ExcelLayoutEnginer.compNullCount(var21);

                        int var24;
                        for(var24 = 0; var24 < var23; ++var24) {
                            this.a.print("\"");
                            this.a.print("\"");
                            this.a.print(",");
                        }

                        for(var24 = 0; var24 < var21.length; ++var24) {
                            CrossOutData$Data var11 = var21[var24];
                            if (var11 != null) {
                                Object var12 = var11.getTrueValue();
                                if (var11.getType() == 2) {
                                    this.a.print("\"");
                                }

                                this.a.print(var12 == null ? (var11.getValue() == null ? "" : var11.getValue().toString()) : var12.toString());
                                if (var11.getType() == 2) {
                                    this.a.print("\"");
                                }

                                if (var24 != var21.length - 1) {
                                    this.a.print(",");
                                }
                            }
                        }

                        this.a.println("");
                    }
                }
            }
        } catch (Exception var13) {
            var13.printStackTrace();
        }

    }
}
