//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube.operation;

import com.ruisi.ext.engine.ConstantsEngine;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataSetTop extends BaseProcessor implements Processor {
    private int a;
    private String b;
    private static Log c = LogFactory.getLog(DataSetTop.class);

    public DataSetTop(int var1, String var2) {
        this.a = var1;
        this.b = var2;
    }

    public void process() {
        String var1 = this.b;
        String var2;
        if (this.dsMetaData.isExtKpi(var1)) {
            var2 = ConstantsEngine.replace("_top函数输入类型错误，需要一个KPI, 但传入却不是, input=$0", var1);
            c.error(var2);
            var1 = null;
        }

        var2 = null;
        List var5;
        if (var1 != null && var1.length() != 0) {
            var5 = this.dataOper.queryDataByKpiCode(var1);
        } else {
            var5 = this.ds.getDatas();
        }

        ArrayList var3 = new ArrayList();

        for(int var4 = 0; var4 < this.a && var4 < var5.size(); ++var4) {
            var3.add(var5.get(var4));
        }

        this.ds.setDatas(var3);
    }
}
