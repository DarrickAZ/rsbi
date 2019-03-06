//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.grid;

import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.collections.map.ListOrderedMap;

public class GridDataUtils {
    public GridDataUtils() {
    }

    public static void putComputeData(Object var0, Map var1, String var2) {
        if (var0 != null) {
            if (var0 instanceof BigDecimal) {
                var1.put(var2, ((BigDecimal)var0).doubleValue());
            } else {
                Double var3 = (Double)var0;
                if (!Double.isNaN(var3) && !Double.isInfinite(var3)) {
                    var1.put(var2, var3);
                } else {
                    var1.put(var2, (Object)null);
                }
            }
        } else {
            var1.put(var2, (Object)null);
        }

    }

    public static boolean columnIsExist(String var0, GridDataMetaData var1) {
        ColumnInfo var2 = (ColumnInfo)var1.getQueryColumns().get(var0);
        return var2 != null;
    }

    public static Map createNewData(Map var0) {
        ListOrderedMap var1 = new ListOrderedMap();
        Iterator var2 = var0.keySet().iterator();

        while(var2.hasNext()) {
            String var3 = (String)var2.next();
            var1.put(var3, var0.get(var3));
        }

        return var1;
    }

    public static Double getKpiData(Map var0, String var1) {
        if (var0 == null) {
            return null;
        } else {
            Object var2 = var0.get(var1);
            if (var2 == null) {
                return null;
            } else {
                Double var3 = null;
                if (var2 instanceof BigDecimal) {
                    var3 = ((BigDecimal)var2).doubleValue();
                } else if (var2 instanceof Double) {
                    var3 = (Double)var2;
                } else if (var2 instanceof Long) {
                    var3 = ((Long)var2).doubleValue();
                } else if (var2 instanceof Integer) {
                    var3 = ((Integer)var2).doubleValue();
                } else {
                    if (!(var2 instanceof Float)) {
                        throw new ExtRuntimeException("类型不支持。");
                    }

                    var3 = ((Float)var2).doubleValue();
                }

                return var3;
            }
        }
    }
}
