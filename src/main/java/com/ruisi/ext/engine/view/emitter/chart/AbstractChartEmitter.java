//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.chart;

import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractChartEmitter implements ChartEmitter {
    protected ExtWriter out;
    protected ChartContext chart;
    protected ExtRequest request;
    protected ChartConfigVO config;
    protected List xcolList;
    protected List dataInfoList;
    protected List dataList;
    protected Integer chartColorIndex = 1;

    public AbstractChartEmitter() {
    }

    @Override
    public void initData(ChartConfigVO var1, ExtWriter var2, ChartContext var3, ExtRequest var4) {
        this.config = var1;
        this.request = var4;
        this.chart = var3;
        this.out = var2;
        this.dataList = var3.loadOptions();
        this.xcolList = new ArrayList();
        this.dataInfoList = new ArrayList();
        this.chartColorIndex = 1;
    }

    protected String getKpiForamtString() {
        String var1 = (String)((Map)this.dataList.get(0)).get(this.config.getFormatCol());
        if (var1 == null) {
            var1 = this.config.getFormatCol();
        }

        return var1 == null ? "" : var1;
    }

    protected String getKpi2ForamtString() {
        String var1 = (String)((Map)this.dataList.get(0)).get(this.config.getFormatCol2());
        if (var1 == null) {
            var1 = this.config.getFormatCol2();
        }

        return var1 == null ? "" : var1;
    }

    protected String getKpiUnitString() {
        Object var1 = ((Map)this.dataList.get(0)).get(this.config.getUnitCol());
        String var2 = var1 == null ? null : var1.toString();
        if (var2 == null) {
            var2 = this.config.getUnitCol();
        }

        if ("%".equals(var2)) {
            var2 = "";
        }

        return ChartUtils.writerUnit(this.chart.getRate()) + (var2 == null ? "" : var2);
    }

    protected void initConfg() {
        if (this.chart.getWidth() == null || this.chart.getWidth().equals("")) {
            this.chart.setWidth("800");
        }

        if (this.chart.getHeight() == null || this.chart.getHeight().equals("")) {
            this.chart.setHeight("400");
        }

        if (this.config.getLegendLayout() == null || this.config.getLegendLayout().equals("")) {
            this.config.setLegendLayout("horizontal");
        }

        if (this.config.getLegendPosition() == null || this.config.getLegendPosition().equals("")) {
            this.config.setLegendPosition("righttop");
        }

    }
}
