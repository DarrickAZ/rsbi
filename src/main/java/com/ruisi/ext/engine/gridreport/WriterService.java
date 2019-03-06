//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.gridreport;

import com.ruisi.ext.engine.view.context.gridreport.GridReportContext;
import com.ruisi.ext.engine.view.emitter.html.ColFormater;
import java.math.BigDecimal;
import java.util.Map;

public class WriterService {
    private GridReportContext a;

    public WriterService(GridReportContext var1) {
        this.a = var1;
    }

    public String getDataType(int var1) {
        String var2 = this.a.getDetails()[0][var1].getAlias();
        Map var3 = (Map)this.a.loadOptions().get(0);
        Object var4 = var3.get(var2);
        if (var4 instanceof String) {
            return "String";
        } else {
            return var4 instanceof BigDecimal ? "Number" : "None";
        }
    }

    public Object findDynamicTextValue(String var1) {
        return this.a.getExtDatas() != null ? this.a.getExtDatas().get(var1) : null;
    }

    public String format(Object var1, String var2) {
        return ColFormater.format(var1, var2);
    }
}
