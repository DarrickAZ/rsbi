//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube.operation;

import com.ruisi.ispire.dc.cube.DataSetMetaData;
import com.ruisi.ispire.dc.cube.QueryDim;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.collections.map.ListOrderedMap;

public class DataOperUtils {
    private List a;
    private DataSetMetaData b;

    public DataOperUtils(List var1, DataSetMetaData var2) {
        this.a = var1;
        this.b = var2;
    }

    public List queryDataByKpiCode(String var1) {
        ArrayList var2 = new ArrayList();
        Iterator var4 = this.a.iterator();

        while(var4.hasNext()) {
            Map var3 = (Map)var4.next();
            String var5 = (String)var3.get(this.b.getZbKpiCol());
            if (var5.equals(var1)) {
                var2.add(var3);
            }
        }

        return var2;
    }

    public Map createQueryKey(Map var1) {
        HashMap var2 = new HashMap();
        Iterator var4 = this.b.getQueryDims().iterator();

        while(var4.hasNext()) {
            QueryDim var3 = (QueryDim)var4.next();
            String var5 = var3.getDimCol();
            Object var6 = var1.get(var5);
            var2.put(var5, var6);
        }

        return var2;
    }

    public Map getDataByKeysAndKpiCode(Map var1, String var2) {
        Map var3 = null;
        Iterator var5 = this.a.iterator();

        while(var5.hasNext()) {
            Map var4 = (Map)var5.next();
            if (this.isCheckData(var4, var1, var2)) {
                var3 = var4;
            }
        }

        return var3;
    }

    public Map getDataByKeysAndKpiCode(List var1, Map var2, String var3) {
        Map var4 = null;
        Iterator var6 = var1.iterator();

        while(var6.hasNext()) {
            Map var5 = (Map)var6.next();
            if (this.isCheckData(var5, var2, var3)) {
                var4 = var5;
            }
        }

        return var4;
    }

    public List queryDataByKeysAndKpiCode(Map var1, String var2) {
        ArrayList var3 = new ArrayList();
        Iterator var5 = this.a.iterator();

        while(var5.hasNext()) {
            Map var4 = (Map)var5.next();
            if (this.isCheckData(var4, var1, var2)) {
                var3.add(var4);
            }
        }

        return var3;
    }

    public boolean isCheckData(Map var1, Map var2, String var3) {
        boolean var4 = true;
        String var5 = (String)var1.get(this.b.getZbKpiCol());
        if (!var3.equals(var5)) {
            var4 = false;
            return var4;
        } else if (var2 == null) {
            return var4;
        } else {
            Iterator var7 = var2.entrySet().iterator();

            while(var7.hasNext()) {
                Entry var6 = (Entry)var7.next();
                String var8 = (String)var1.get(var6.getKey());
                if (!var8.equals(var6.getValue())) {
                    var4 = false;
                    break;
                }
            }

            return var4;
        }
    }

    public Map createData(Map var1) {
        ListOrderedMap var2 = new ListOrderedMap();
        List var3 = this.b.getQueryDims();
        Iterator var5 = var3.iterator();

        while(var5.hasNext()) {
            QueryDim var4 = (QueryDim)var5.next();
            String var6 = var4.getDimCol();
            String var7 = var4.getDimDescCol();
            String var8 = var4.getOrderCol();
            var2.put(var6, var1.get(var6));
            var2.put(var7, var1.get(var7));
            var2.put(var8, var1.get(var8));
        }

        return var2;
    }

    public Map createData(Map var1, List var2) {
        ListOrderedMap var3 = new ListOrderedMap();
        List var4 = this.b.getQueryDims();
        Iterator var6 = var4.iterator();

        while(var6.hasNext()) {
            QueryDim var5 = (QueryDim)var6.next();
            if (var2.contains(var5)) {
                String var7 = var5.getDimCol();
                String var8 = var5.getDimDescCol();
                String var9 = var5.getOrderCol();
                var3.put(var7, var1.get(var7));
                var3.put(var8, var1.get(var8));
                var3.put(var9, var1.get(var9));
            }
        }

        return var3;
    }

    public List queryRemainDim(String[] var1) {
        ArrayList var2 = new ArrayList();
        Iterator var4 = this.b.getQueryDims().iterator();

        while(var4.hasNext()) {
            QueryDim var3 = (QueryDim)var4.next();
            if (!this.a(var3.getDimCol(), var1)) {
                var2.add(var3);
            }
        }

        return var2;
    }

    private boolean a(String var1, String[] var2) {
        boolean var3 = false;
        String[] var7 = var2;
        int var6 = var2.length;

        for(int var5 = 0; var5 < var6; ++var5) {
            String var4 = var7[var5];
            if (var4.equals(var1)) {
                var3 = true;
                break;
            }
        }

        return var3;
    }

    public List getDimValues(String var1) {
        ArrayList var2 = new ArrayList();
        Iterator var4 = this.a.iterator();

        while(var4.hasNext()) {
            Map var3 = (Map)var4.next();
            String var5 = (String)var3.get(var1);
            if (!var2.contains(var5)) {
                var2.add(var5);
            }
        }

        return var2;
    }

    public void putKpiData(Object var1, Map var2, String var3) {
        if (var1 != null) {
            if (var1 instanceof BigDecimal) {
                var2.put(var3, var1);
            } else {
                Double var4 = (Double)var1;
                if (!Double.isNaN(var4) && !Double.isInfinite(var4)) {
                    var2.put(var3, new BigDecimal(var4));
                } else {
                    var2.put(var3, (Object)null);
                }
            }
        } else {
            var2.put(var3, (Object)null);
        }

    }
}
