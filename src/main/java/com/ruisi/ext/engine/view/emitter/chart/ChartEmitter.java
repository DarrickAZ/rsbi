//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.chart;

import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtWriter;

public interface ChartEmitter {
    void initData(ChartConfigVO var1, ExtWriter var2, ChartContext var3, ExtRequest var4);

    int createChartJS(boolean var1);
}
