//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube.operation;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ispire.dc.cube.QueryDim;
import com.ruisi.ispire.dc.cube.QueryExtKpi;
import com.ruisi.ispire.dc.cube.QueryKpi;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataSetAggregation extends BaseProcessor implements Processor {
    public static final String SUM = "sum";
    public static final String AVG = "avg";
    public static final String MAX = "max";
    public static final String MIN = "min";
    public static final String COUNT = "count";
    public static final String VAR = "var";
    public static final String SD = "sd";
    public static final String MIDDLE = "middle";
    public static final String[] aggs = new String[]{"sum", "avg", "max", "min", "count", "var", "sd", "middle"};
    private String a;
    private String b;
    private String c;
    private List d;

    public DataSetAggregation(String var1, String var2, String var3) {
        this.a = var1;
        this.b = var2;
        this.c = var3;
    }

    public DataSetAggregation(List var1, String var2) {
        this.b = var2;
        this.d = var1;
    }

    public void process() throws ScriptEnginerException {
        String var10;
        if (this.a != null && this.a.length() > 0 && !this.b(this.a)) {
            var10 = ConstantsEngine.replace("_aggregation 聚合类型错误, ($0)", this.a);
            throw new ScriptEnginerException(var10);
        } else {
            if (this.d != null) {
                Iterator var2 = this.d.iterator();

                while(var2.hasNext()) {
                    DataSetAggregation$KpiAggreType var1 = (DataSetAggregation$KpiAggreType)var2.next();
                    String var3;
                    if (!this.b(var1.getType())) {
                        var3 = ConstantsEngine.replace("_aggregation 聚合类型错误, ($0)", var1.getType());
                        throw new ScriptEnginerException(var3);
                    }

                    if (!this.dsMetaData.isKpi(var1.getKpi()) && !this.dsMetaData.isExtKpi(var1.getKpi())) {
                        var3 = ConstantsEngine.replace("_aggregation 聚合指标/衍生指标未找到, ($0)", var1.getKpi());
                        throw new ScriptEnginerException(var3);
                    }
                }
            }

            if (!this.dsMetaData.isDim(this.b)) {
                var10 = ConstantsEngine.replace("_aggregation 聚合维度不存在, ($0)", this.b);
                throw new ScriptEnginerException(var10);
            } else {
                List var9 = this.dataOper.queryRemainDim(new String[]{this.b});
                Iterator var13 = var9.iterator();

                while(var13.hasNext()) {
                    QueryDim var11 = (QueryDim)var13.next();
                    var11.setValues(this.dataOper.getDimValues(var11.getDimCol()));
                }

                ArrayList var12 = new ArrayList();
                List var14 = this.a(var9);
                Iterator var5 = this.dsMetaData.getQueryKpis().iterator();

                while(true) {
                    while(var5.hasNext()) {
                        QueryKpi var4 = (QueryKpi)var5.next();
                        if (var14 != null && !var14.isEmpty()) {
                            Iterator var7 = var14.iterator();

                            while(var7.hasNext()) {
                                Map var16 = (Map)var7.next();
                                List var8 = this.dataOper.queryDataByKeysAndKpiCode(var16, var4.getKpiCol());
                                this.a(var8, var9, var4, var12);
                            }
                        } else {
                            List var6 = this.dataOper.queryDataByKeysAndKpiCode((Map)null, var4.getKpiCol());
                            this.a(var6, var9, var4, var12);
                        }
                    }

                    if (this.c != null && this.c.length() != 0) {
                        QueryExtKpi var15 = new QueryExtKpi();
                        var15.setExtKpiCol(this.c);
                        if (this.dsMetaData.getQueryExtKpis() == null) {
                            this.dsMetaData.setQueryExtKpis(new ArrayList());
                        }

                        this.dsMetaData.getQueryExtKpis().add(var15);
                    } else {
                        this.ds.setDatas(var12);
                        this.dsMetaData.removeDims(new String[]{this.b});
                    }

                    return;
                }
            }
        }
    }

    private void a(List var1, List var2, QueryKpi var3, List var4) {
        if (var1.size() > 0) {
            BigDecimal var5 = this.a(var1, var2, var3);
            Map var6 = this.dataOper.createData((Map)var1.get(0), var2);
            var6.put(this.dsMetaData.getZbKpiValueCol(), var5);
            var4.add(var6);
            if (this.c != null && this.c.length() > 0) {
                for(int var7 = 0; var7 < var1.size(); ++var7) {
                    Map var8 = (Map)var1.get(var7);
                    var8.put(this.c, var5);
                }
            }
        }

    }

    private BigDecimal a(List var1, List var2, QueryKpi var3) {
        ArrayList var4 = new ArrayList();
        Iterator var6 = var1.iterator();

        BigDecimal var7;
        while(var6.hasNext()) {
            Map var5 = (Map)var6.next();
            var7 = (BigDecimal)var5.get(this.dsMetaData.getZbKpiValueCol());
            var4.add(var7);
        }

        String var9 = var3.getAggregation() == null ? this.a : var3.getAggregation();
        if (var9 == null || var9.length() == 0) {
            try {
                var9 = this.a(var3.getKpiCol());
            } catch (ScriptEnginerException e) {
                e.printStackTrace();
            }
        }

        BigDecimal var10 = new BigDecimal(0);
        if (!var9.equalsIgnoreCase("sum") && !var9.equalsIgnoreCase("avg")) {
            if (var9.equalsIgnoreCase("max")) {
                return (BigDecimal)Collections.max(var4);
            } else {
                return var9.equalsIgnoreCase("min") ? (BigDecimal)Collections.min(var4) : null;
            }
        } else {
            for(Iterator var8 = var4.iterator(); var8.hasNext(); var10 = var10.add(var7)) {
                var7 = (BigDecimal)var8.next();
            }

            if (var9.equalsIgnoreCase("avg")) {
                var10 = var10.divide(new BigDecimal(var4.size()), 8, 6);
            }

            return var10;
        }
    }

    private String a(String var1) throws ScriptEnginerException {
        if (this.d != null && this.d.size() != 0) {
            String var2 = null;
            Iterator var4 = this.d.iterator();

            while(var4.hasNext()) {
                DataSetAggregation$KpiAggreType var3 = (DataSetAggregation$KpiAggreType)var4.next();
                if (var3.getKpi().equals(var1)) {
                    var2 = var3.getType();
                }
            }

            if (var2 == null) {
                throw new ScriptEnginerException("_aggregation 未设置聚合类型.");
            } else {
                return var2;
            }
        } else {
            throw new ScriptEnginerException("_aggregation 未设置聚合类型.");
        }
    }

    private List a(List var1) {
        ArrayList var2 = new ArrayList();
        if (var1 != null && var1.size() != 0) {
            for(int var3 = 0; var3 < 1; ++var3) {
                QueryDim var4 = (QueryDim)var1.get(var3);

                for(int var5 = 0; var5 < var4.getValues().size(); ++var5) {
                    String var6 = (String)var4.getValues().get(var5);
                    String var7 = var4.getDimCol();
                    HashMap var8 = new HashMap();
                    var2.add(var8);
                    var8.put(var7, var6);

                    for(int var9 = 0; var9 < var1.size(); ++var9) {
                        QueryDim var10 = (QueryDim)var1.get(var9);
                        if (!var10.getDimCol().equals(var7)) {
                            for(int var11 = 0; var11 < var10.getValues().size(); ++var11) {
                                String var12 = (String)var10.getValues().get(var11);
                                String var13 = var10.getDimCol();
                                var8.put(var13, var12);
                            }
                        }
                    }
                }
            }

            return var2;
        } else {
            return var2;
        }
    }

    public List getAggreTypes() {
        return this.d;
    }

    public void setAggreTypes(List var1) {
        this.d = var1;
    }

    private boolean b(String var1) {
        boolean var2 = false;
        String[] var6;
        int var5 = (var6 = aggs).length;

        for(int var4 = 0; var4 < var5; ++var4) {
            String var3 = var6[var4];
            if (var3.equalsIgnoreCase(var1)) {
                var2 = true;
                break;
            }
        }

        return var2;
    }

    public static double getVariance(List var0) {
        double var1 = 0.0D;

        for(int var3 = 0; var3 < var0.size(); ++var3) {
            var1 += (Double)var0.get(var3);
        }

        var1 /= (double)var0.size();
        double var6 = 0.0D;

        for(int var5 = 0; var5 < var0.size(); ++var5) {
            var6 += ((Double)var0.get(var5) - var1) * ((Double)var0.get(var5) - var1);
        }

        double var10000 = var6 / (double)var0.size();
        return var1;
    }

    public static double getStandardDiviation(List var0) {
        return Math.sqrt(Math.abs(getVariance(var0)));
    }
}
