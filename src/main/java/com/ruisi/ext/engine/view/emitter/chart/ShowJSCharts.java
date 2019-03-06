//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.chart;

import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.util.ExtChartUtils;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.view.context.chart.ChartKeyContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.JspException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

public class ShowJSCharts {
    private Logger a = Logger.getLogger(ShowJSCharts.class);
    private ExtWriter b;
    private ChartContext c;
    private ExtRequest d;
    private boolean e = false;
    private ChartConfigVO f = new ChartConfigVO();

    public ShowJSCharts(ExtWriter var1, ChartContext var2, ExtRequest var3, ExtEnvirContext var4, boolean var5) {
        this.b = var1;
        this.c = var2;
        this.d = var3;
        this.e = var5;
    }

    public void init() {
        List var1 = this.c.getProperties();

        for(int var2 = 0; var2 < var1.size(); ++var2) {
            ChartKeyContext var3 = (ChartKeyContext)var1.get(var2);
            String var4 = var3.getName();
            String var5 = var3.getValue();
            if (var5 != null && var5.length() != 0) {
                try {
                    Object var6;
                    if (var5.equalsIgnoreCase("true")) {
                        var6 = true;
                    } else if (var5.equalsIgnoreCase("false")) {
                        var6 = false;
                    } else {
                        var6 = var5;
                    }

                    PropertyUtils.setProperty(this.f, var4, var6);
                } catch (IllegalAccessException var7) {
                    var7.printStackTrace();
                } catch (InvocationTargetException var8) {
                    var8.printStackTrace();
                } catch (NoSuchMethodException var9) {
                    this.a.error("错误！没有该属性：" + var4);
                }
            }
        }

    }

    public Object[][] showData() {
        this.init();
        List var1 = this.c.loadOptions();
        List var2 = ExtChartUtils.getScol(this.c, var1);
        String var3 = this.c.getShape();
        Object[][] var4;
        if (var2.size() <= 1) {
            var4 = new Object[var1.size() + 1][];
            int var5;
            Map var6;
            Double var7;
            Double var8;
            if ("bubble".equals(var3)) {
                var4[0] = new Object[]{this.f.getXdesc(), this.f.getYdesc(), "气泡大小"};

                for(var5 = 0; var5 < var1.size(); ++var5) {
                    var6 = (Map)var1.get(var5);
                    var7 = ChartUtils.getKpiValue(var6.get(this.c.getY2col()));
                    var8 = ChartUtils.getKpiValue(var6.get(this.c.getYcol()));
                    Double var9 = ChartUtils.getKpiValue(var6.get(this.c.getY3col()));
                    var4[var5 + 1] = new Object[]{var7, var8, var9};
                }
            } else if ("scatter".equals(var3)) {
                var4[0] = new Object[]{this.f.getXdesc(), this.f.getYdesc()};

                for(var5 = 0; var5 < var1.size(); ++var5) {
                    var6 = (Map)var1.get(var5);
                    var7 = ChartUtils.getKpiValue(var6.get(this.c.getY2col()));
                    var8 = ChartUtils.getKpiValue(var6.get(this.c.getYcol()));
                    var4[var5 + 1] = new Object[]{var7, var8};
                }
            } else {
                var4[0] = new Object[]{"", this.f.getYdesc()};

                for(var5 = 0; var5 < var1.size(); ++var5) {
                    var6 = (Map)var1.get(var5);
                    var7 = ChartUtils.getKpiValue(var6.get(this.c.getYcol()));
                    var4[var5 + 1] = new Object[]{var6.get(this.c.getXcol()), var7};
                }
            }
        } else {
            List var14 = ExtChartUtils.getXcol(this.c, var1);
            var4 = new Object[var14.size() + 1][];
            if ("scatter".equals(var3)) {
                int var15 = 0;
                Object[] var16 = var4[0] = new Object[var2.size() * 2];

                int var18;
                for(var18 = 0; var18 < var2.size(); ++var18) {
                    String var20 = (String)var2.get(var18);
                    var16[var15] = "";
                    ++var15;
                    var16[var15] = var20;
                    ++var15;
                }

                for(var18 = 0; var18 < var14.size(); ++var18) {
                    var15 = 0;
                    Object[] var21 = var4[var18 + 1] = new Object[var2.size() * 2];

                    for(int var10 = 0; var10 < var2.size(); ++var10) {
                        Object var11 = ExtChartUtils.findYvalByXval(var14.get(var18), this.c.getXcol(), this.c.getYcol(), this.c.getScol(), var2.get(var10), var1);
                        Double var12 = ChartUtils.getKpiValue(var11);
                        Object var13 = ExtChartUtils.findYvalByXval(var14.get(var18), this.c.getXcol(), this.c.getY2col(), this.c.getScol(), var2.get(var10), var1);
                        var21[var15] = var13;
                        ++var15;
                        var21[var15] = var12;
                        ++var15;
                    }
                }
            } else {
                Object[] var19 = var4[0] = new Object[var2.size() + 1];
                var19[0] = "";

                int var17;
                for(var17 = 0; var17 < var2.size(); ++var17) {
                    var19[var17 + 1] = var2.get(var17);
                }

                for(var17 = 0; var17 < var14.size(); ++var17) {
                    Object[] var22 = var4[var17 + 1] = new Object[var2.size() + 1];
                    var22[0] = var14.get(var17);

                    for(int var23 = 0; var23 < var2.size(); ++var23) {
                        Object var24 = ExtChartUtils.findYvalByXval(var14.get(var17), this.c.getXcol(), this.c.getYcol(), this.c.getScol(), var2.get(var23), var1);
                        Double var25 = ChartUtils.getKpiValue(var24);
                        var22[var23 + 1] = var25;
                    }
                }
            }
        }

        return var4;
    }

    public void show() throws ExtConfigException {
        this.init();
        ChartEmitter var1 = ExtContext.getInstance().getChartEmitter(this.c.getShape());
        if (var1 == null) {
            throw new ExtConfigException("shape = " + this.c.getShape() + " 的图形类型不存在。");
        } else {
            var1.initData(this.f, this.b, this.c, this.d);

            var1.createChartJS(this.e);

        }
    }
}
