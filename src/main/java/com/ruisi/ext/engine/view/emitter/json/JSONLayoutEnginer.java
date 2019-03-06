//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.json;

import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.cross.CrossOutData;
import com.ruisi.ext.engine.cross.CrossOutData$Header;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.util.ExtChartUtils;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.view.context.form.AbstractInputContext;
import com.ruisi.ext.engine.view.context.form.DateSelectContext;
import com.ruisi.ext.engine.view.context.form.SelectContext;
import com.ruisi.ext.engine.view.context.gridreport.GridReportContext;
import com.ruisi.ext.engine.view.context.html.TextContext;
import com.ruisi.ext.engine.view.test.TestUtils;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtResponse;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;

public class JSONLayoutEnginer {
    private ExtWriter a;
    private ExtRequest b;
    private ExtResponse c;
    private ExtEnvirContext d;
    private InputOption e;
    private Map f = new HashMap();

    public JSONLayoutEnginer(ExtWriter var1, ExtRequest var2, ExtResponse var3, ExtEnvirContext var4, InputOption var5) {
        this.a = var1;
        this.b = var2;
        this.c = var3;
        this.d = var4;
        this.e = var5;
    }

    public void buildStartMv(MVContext var1) {
        this.f.put("mvid", var1.getMvid());
        this.f.put("comps", new ArrayList());
    }

    public void buildText(TextContext var1) {
        String var2 = var1.getText();
        if (var2 != null) {
            var2.indexOf("<style>");
        }

    }

    public void buildGridReport(GridReportContext var1) {
    }

    public List buildPie(List var1, String var2, String var3) {
        ArrayList var4 = new ArrayList();
        int var5 = 1;

        for(Iterator var7 = var1.iterator(); var7.hasNext(); ++var5) {
            Map var6 = (Map)var7.next();
            Object var8 = var6.get(var3);
            HashMap var9 = new HashMap();
            var9.put("index", var5);
            var9.put("value", var8);
            var4.add(var9);
        }

        return var4;
    }

    public void buildChart(ChartContext var1) {
        HashMap var2 = new HashMap();
        var2.put("type", "chart");
        var2.put("chartType", var1.getShape());
        List var3 = var1.loadOptions();
        List var4 = ExtChartUtils.getXcol(var1, var3);
        var2.put("xVals", var4);
        List var5;
        if ("pie".equals(var1.getShape())) {
            var2.put("yVals", this.buildPie(var3, var1.getXcol(), var1.getYcol()));
        } else {
            var2.put("yVals", new ArrayList());
            var5 = ExtChartUtils.getScol(var1, var3);
            if (var5 != null && var5.size() != 0) {
                for(int var15 = 0; var15 < var5.size(); ++var15) {
                    HashMap var16 = new HashMap();
                    String var17 = (String)var5.get(var15);
                    var16.put("label", var17);
                    ArrayList var19 = new ArrayList();
                    var16.put("Entry", var19);
                    List var20 = ExtChartUtils.getOneScolDatas(var17, var1, var3);

                    for(int var21 = 0; var21 < var4.size(); ++var21) {
                        String var23 = (String)var4.get(var21);
                        Object var13 = ExtChartUtils.findYvalByXval(var23, var1.getXcol(), var1.getYcol(), var20);
                        HashMap var14 = new HashMap();
                        var14.put("value", var13 == null ? 0 : var13);
                        var14.put("xIndex", var21 + 1);
                        var19.add(var14);
                    }

                    List var22 = (List)var2.get("yVals");
                    var22.add(var16);
                }
            } else {
                HashMap var6 = new HashMap();
                String var7 = "合计";
                var6.put("label", var7);
                ArrayList var8 = new ArrayList();
                var6.put("Entry", var8);

                for(int var9 = 0; var9 < var4.size(); ++var9) {
                    String var10 = (String)var4.get(var9);
                    Object var11 = ExtChartUtils.findYvalByXval(var10, var1.getXcol(), var1.getYcol(), var3);
                    HashMap var12 = new HashMap();
                    var12.put("value", var11 == null ? 0 : var11);
                    var12.put("xIndex", var9 + 1);
                    var8.add(var12);
                }

                List var18 = (List)var2.get("yVals");
                var18.add(var6);
            }
        }

        var5 = (List)this.f.get("comps");
        var5.add(var2);
    }

    public void buildCrossReport(CrossOutData var1) {
        CrossOutData$Header[][] var3 = var1.getHeaders();
        if (var3.length != 0) {
            HashMap var4 = new HashMap();
            var4.put("type", "table");
            var4.put("head", var1.getHeaders());
            var4.put("data", var1.getDatas());
            List var5 = (List)this.f.get("comps");
            var5.add(var4);
        }
    }

    public void buildDateSelect(DateSelectContext var1) {
        HashMap var2 = new HashMap();
        var2.put("name", var1.getDesc() == null ? "" : var1.getDesc());
        var2.put("value", var1.getValue() == null ? "" : var1.getValue());
        var2.put("type", var1.getInputType());
        var2.put("min", var1.getMinDate() == null ? "" : var1.getMinDate());
        var2.put("max", var1.getMaxDate() == null ? "" : var1.getMaxDate());
        var2.put("dim", ((AbstractInputContext)var1).getTmpval());
        if (this.f.get("params") == null) {
            this.f.put("params", new ArrayList());
        }

        List var3 = (List)this.f.get("params");
        var3.add(var2);
    }

    public void buildSelect(SelectContext var1) {
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

        if (var1.loadOptions().size() > 0 && (var2 == null || var2.length() == 0)) {
            Map var5 = (Map)var1.loadOptions().get(0);
            String var4 = var5.get("value") == null ? "" : var5.get("value").toString();
            var2 = var4;
        }

        this.d.put(var1.getId(), var2);
        this.e.setParamValue(var1.getId(), var2);
        HashMap var6 = new HashMap();
        var6.put("name", var1.getDesc() == null ? "" : var1.getDesc());
        var6.put("value", var2);
        var6.put("type", var1.getInputType());
        var6.put("options", var1.loadOptions());
        var6.put("dim", ((AbstractInputContext)var1).getTmpval());
        if (this.f.get("params") == null) {
            this.f.put("params", new ArrayList());
        }

        List var7 = (List)this.f.get("params");
        var7.add(var6);
    }

    public void buildEndMv(Element var1) {
        String var2 = JSONObject.fromObject(this.f).toString();
        this.a.print(var2);
    }
}
