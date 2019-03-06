//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter;

import com.ruisi.ext.engine.cross.CrossOutData;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.view.context.chart.ChartContext;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtResponse;
import com.ruisi.ext.engine.wrapper.ExtWriter;

public class UserJavaEmitterImpl implements UserJavaEmitter {
    public UserJavaEmitterImpl() {
    }

    public void startCrossReport(CrossOutData var1) {
        System.out.println(var1);
    }

    public void initialize(ExtWriter var1, ExtRequest var2, ExtResponse var3, ExtEnvirContext var4) {
    }

    public void startChart(ChartContext var1) {
    }
}
