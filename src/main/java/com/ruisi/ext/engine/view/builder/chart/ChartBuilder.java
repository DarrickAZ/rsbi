//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.chart;

import com.itextpdf.text.DocumentException;
import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.util.ExtChartUtils;
import com.ruisi.ext.engine.util.LabelUtils;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.PageBuilder$JSObject;
import com.ruisi.ext.engine.view.builder.dc.GridDataCenterBuilder;
import com.ruisi.ext.engine.view.builder.dsource.DataSourceBuilder;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.view.context.chart.ChartDrillContext;
import com.ruisi.ext.engine.view.context.chart.ChartLinkContext;
import com.ruisi.ext.engine.view.context.chart.ChartTitleContext;
import com.ruisi.ext.engine.view.context.chart.ChartYcolContext;
import com.ruisi.ext.engine.view.context.dc.grid.GridDataCenterContext;
import com.ruisi.ext.engine.view.context.dsource.DataSourceContext;
import com.ruisi.ext.engine.view.context.grid.PageInfo;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.view.test.TestUtils;
import com.ruisi.ext.engine.wrapper.ExtRequest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class ChartBuilder extends AbstractBuilder {
    private ChartContext a;

    public ChartBuilder(ChartContext var1) {
        this.a = var1;
    }

    @Override
    protected void processEnd() {
    }

    private List a(ChartContext var1) throws ScriptEnginerException, ExtConfigException {
        String var2 = var1.getRefDataCenter();
        MVContext var3 = RuleUtils.findCurMV(this.veloContext);
        GridDataCenterContext var4 = (GridDataCenterContext)var3.getGridDataCenters().get(var2);
        GridDataCenterBuilder var5 = new GridDataCenterBuilder(var4, this.request, this.veloContext, this.daoHelper);
        List var6 = var5.buildByXML((PageInfo)null);
        return var6;
    }

    public static boolean isDrill(ExtRequest var0) {
        String var1 = var0.getParameter("drillDim");
        return var1 != null && var1.length() != 0;
    }

    protected void processStart() throws ExtConfigException, ScriptEnginerException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException, DocumentException, InvalidFormatException {
        ChartContext var1 = (ChartContext)this.a.clone();
        String var2;
        String var3;
        String var23;
        if (isDrill(this.request)) {
            this.veloContext.put(var1.getXcolDesc(), this.request.getParameter(var1.getXcolDesc()));
            var2 = var1.getDrill().getRefDataCenter();
            MVContext var14 = RuleUtils.findCurMV(this.veloContext);
            GridDataCenterContext var16 = (GridDataCenterContext)var14.getGridDataCenters().get(var2);
            GridDataCenterBuilder var22 = new GridDataCenterBuilder(var16, this.request, this.veloContext, this.daoHelper);
            List var21 = var22.buildByXML((PageInfo)null);
            var1.setOptions(var21);
        } else {
            var2 = var1.getRefDataCenter();
            if (var2 != null && var2.length() > 0) {
                List var13 = this.a(var1);
                var1.setOptions(var13);
            } else {
                var3 = var1.getRef();
                String var4;
                String var5;
                if (var3 != null && var3.length() > 0) {
                    var5 = (String)this.request.getAttribute("ext.view.mvid");
                    MVContext var6 = ExtContext.getInstance().getMVContext(var5);
                    if (var6.getSqls() == null || var6.getSqls().get(var3) == null) {
                        var23 = ConstantsEngine.replace("ref 为 $0 的sql不存在, mvId = $1 .", var3);
                        throw new ExtConfigException(var23);
                    }

                    var4 = TemplateManager.buildTempldate((String)var6.getSqls().get(var3), this.request, this.veloContext);
                } else {
                    var4 = TemplateManager.buildTempldate(var1.getTemplateName(), this.request, this.veloContext);
                }

                var5 = null;
                List var18;
                if (this.a.getRefDsource() != null && this.a.getRefDsource().length() > 0) {
                    DataSourceBuilder var19 = new DataSourceBuilder();
                    DataSourceContext var7 = var19.findDataSource(this.a.getRefDsource(), RuleUtils.findCurMV(this.veloContext));
                    var18 = (new DataSourceBuilder()).queryForList(var4, var7);
                } else {
                    var18 = this.daoHelper.queryForList(var4);
                }

                if (var18.size() > 0 && var1.getFormula() != null) {
                    PageBuilder$JSObject var20 = (PageBuilder$JSObject)this.request.getAttribute("ext.script.engine");
                    var18 = ExtChartUtils.createChartData(var18, var1, var20);
                }

                var1.setOptions(var18);
            }
        }

        if (var1.getYcols() != null && var1.getYcols().size() > 0) {
            var1.setYcol("kpiValue");
            var1.setScol("ser");
            this.mergeYcolsData(var1);
        }

        this.a(var1.loadOptions());
        var1.setWidth(TestUtils.findValue(var1.getWidth(), this.request, this.veloContext));
        var1.setHeight(TestUtils.findValue(var1.getHeight(), this.request, this.veloContext));
        ChartTitleContext var12 = this.a.getTitle();
        if (var12 != null && var12.getType().equals("template")) {
            var3 = var12.getTemplateName();
            var12.setText(TemplateManager.buildTempldate(var3, this.request, this.veloContext));
        }

        if (var1.getLink() != null || var1.getDrill() != null) {
            Map var15 = this.option.getParams();
            StringBuffer var17 = new StringBuffer();
            if (var15 != null) {
                Iterator var24 = var15.entrySet().iterator();

                while(var24.hasNext()) {
                    Entry var26 = (Entry)var24.next();
                    var23 = (String)var26.getKey();
                    if (!var23.equals(var1.getXcolDesc())) {
                        Object var8 = var26.getValue();
                        if (var8 instanceof String) {
                            var17.append(var23);
                            var17.append("=");
                            var17.append(var8);
                            var17.append("&");
                        }
                    }
                }
            }

            MVContext var27 = RuleUtils.findCurMV(this.veloContext);
            String var29;
            if (var1.getDrill() != null) {
                ChartDrillContext var25 = var1.getDrill();
                var25.setParams(var17.toString());
                var23 = RuleUtils.getResPath(this.request) + "control/extControl?serviceid=$0&" + "t_from_id" + "=" + var27.getMvid() + "&id=$1";
                var29 = this.a.getId();
                var23 = ConstantsEngine.replace(var23, LabelUtils.findServiceIdByType("chart"), var29);
                var25.setUrl(var23);
            }

            if (var1.getLink() != null) {
                ChartLinkContext var28 = var1.getLink();
                if (var28.getLinkUrl() == null || var28.getLinkUrl().length() == 0) {
                    var28.setUrl(new String[var28.getTarget().length]);
                    var28.setTargetId(new String[var28.getTarget().length]);
                    var28.setParams(var17.toString());

                    for(int var30 = 0; var30 < var28.getTarget().length; ++var30) {
                        var29 = var28.getTarget()[var30];
                        Object var9 = LabelUtils.findObjectByLabel(var27, var29, var28.getType()[var30]);
                        if (var9 != null) {
                            String var10 = RuleUtils.getResPath(this.request) + "control/extControl?serviceid=$0&" + "t_from_id" + "=" + var27.getMvid() + "&id=$1";
                            String var11 = (String)PropertyUtils.getProperty(var9, "id");
                            var10 = ConstantsEngine.replace(var10, LabelUtils.findServiceIdByType(var28.getType()[var30]), var11);
                            var28.getUrl()[var30] = var10;
                            var28.getTargetId()[var30] = var11;
                        }
                    }
                }
            }
        }

        this.emitter.startChart(var1);
    }

    private void a(List var1) {
        if (this.a.getRate() != null || this.a.getRate2() != null || this.a.getRate3() != null) {
            for(int var2 = 0; var2 < var1.size(); ++var2) {
                Map var3 = (Map)var1.get(var2);
                Object var4 = this.a(var3.get(this.a.getYcol()), 1);
                Object var5 = this.a(var3.get(this.a.getY2col()), 2);
                Object var6 = this.a(var3.get(this.a.getY3col()), 3);
                if (var4 != null) {
                    var3.put(this.a.getYcol(), var4);
                }

                if (var5 != null) {
                    var3.put(this.a.getY2col(), var5);
                }

                if (var6 != null) {
                    var3.put(this.a.getY3col(), var6);
                }
            }

        }
    }

    private Object a(Object var1, int var2) {
        Integer var3 = null;
        if (var2 == 1) {
            var3 = this.a.getRate();
        } else if (var2 == 2) {
            var3 = this.a.getRate2();
        } else if (var2 == 3) {
            var3 = this.a.getRate3();
        }

        if (var1 != null && var3 != null) {
            if (var1 instanceof Integer) {
                var1 = (Integer)var1 / var3;
            } else if (var1 instanceof Double) {
                var1 = (Double)var1 / (double)var3;
            } else if (var1 instanceof Long) {
                var1 = (Long)var1 / (long)var3;
            } else {
                if (!(var1 instanceof BigDecimal)) {
                    throw new ExtRuntimeException("类型未支持...");
                }

                var1 = ((BigDecimal)var1).doubleValue() / (double)var3;
            }

            return var1;
        } else {
            return null;
        }
    }

    public void mergeYcolsData(ChartContext var1) {
        ArrayList var2 = new ArrayList();

        for(int var3 = 0; var3 < var1.loadOptions().size(); ++var3) {
            Map var4 = (Map)var1.loadOptions().get(var3);
            Iterator var6 = var1.getYcols().iterator();

            while(var6.hasNext()) {
                ChartYcolContext var5 = (ChartYcolContext)var6.next();
                CaseInsensitiveMap var7 = new CaseInsensitiveMap();
                if (var1.getXcol() != null && var1.getXcol().length() > 0) {
                    var7.put(var1.getXcol(), var4.get(var1.getXcol()));
                }

                if (var1.getXcolDesc() != null && var1.getXcolDesc().length() > 0) {
                    var7.put(var1.getXcolDesc(), var4.get(var1.getXcolDesc()));
                }

                var7.put("kpiValue", var4.get(var5.getName()));
                var7.put("ser", var5.getDesc());
                var2.add(var7);
            }
        }

        var1.setOptions(var2);
    }
}
