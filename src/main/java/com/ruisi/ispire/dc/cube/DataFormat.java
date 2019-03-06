//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class DataFormat {
    public DataFormat() {
    }

    public BigDecimal getDataValue(DataSet var1) {
        List var2 = var1.getDatas();
        Map var3 = (Map)var2.get(0);
        BigDecimal var4 = (BigDecimal)var3.get(var1.getDataSetMetaData().getZbKpiValueCol());
        return var4;
    }
}
