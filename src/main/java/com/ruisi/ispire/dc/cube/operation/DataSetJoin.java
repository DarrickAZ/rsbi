//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube.operation;

import com.ruisi.ispire.dc.cube.DataSet;
import com.ruisi.ispire.dc.cube.QueryDim;
import com.ruisi.ispire.dc.cube.QueryExtKpi;
import com.ruisi.ispire.dc.cube.QueryKpi;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataSetJoin extends BaseProcessor implements Processor {
    private String a;
    private String b;
    private String c;

    public DataSetJoin(String var1, String var2, String var3) {
        this.b = var1;
        this.a = var3;
        this.c = var2;
    }

    @Override
    public void process() throws ScriptEnginerException {
        DataSet var1 = this.ds.getDataCenter().getDataSetById(this.b);
        if (!this.dimIsSame(var1)) {
            throw new ScriptEnginerException(" _join 函数出错, 数据集维度不同, 不能JOIN.");
        } else if (!this.kpiIsSame(var1)) {
            throw new ScriptEnginerException(" _join 函数出错，两数据集指标不一致，不能链接.");
        } else {
            this.a(var1, this.c);
            Object var2 = var1.getDataSetMetaData().getQueryExtKpis();
            QueryExtKpi var3;
            if (var2 != null) {
                Iterator var4 = ((List)var2).iterator();

                while(var4.hasNext()) {
                    var3 = (QueryExtKpi)var4.next();
                    var3.setExtKpiCol(this.c + "_" + var3.getExtKpiCol());
                }
            }

            if (var2 == null) {
                var2 = new ArrayList();
            }

            var3 = new QueryExtKpi();
            var3.setExtKpiCol(this.c + "_" + this.dsMetaData.getZbKpiValueCol());
            ((List)var2).add(var3);
            if (this.dsMetaData.getQueryExtKpis() == null) {
                this.dsMetaData.setQueryExtKpis((List)var2);
            } else {
                this.dsMetaData.getQueryExtKpis().addAll((Collection)var2);
            }

        }
    }

    private void a(DataSet var1, String var2) {
        ArrayList var3 = new ArrayList();
        List var4 = this.ds.getDatas();

        for(int var5 = 0; var5 < var4.size(); ++var5) {
            Map var6 = (Map)var4.get(var5);
            Map var7 = this.a(var6);
            String var8 = (String)var6.get(var1.getDataSetMetaData().getZbKpiCol());
            Map var9 = this.dataOper.getDataByKeysAndKpiCode(var1.getDatas(), var7, var8);
            if (var9 != null) {
                Map var10 = this.a(var6, var9, this.dsMetaData.getQueryExtKpis(), var2);
                var10.put(var2 + "_" + this.dsMetaData.getZbKpiValueCol(), var9.get(this.dsMetaData.getZbKpiValueCol()));
                var3.add(var10);
            }
        }

        this.ds.setDatas(var3);
    }

    private Map a(Map var1, Map var2, List var3, String var4) {
        if (var3 == null) {
            return var1;
        } else {
            Iterator var6 = var3.iterator();

            while(var6.hasNext()) {
                QueryExtKpi var5 = (QueryExtKpi)var6.next();
                String var7 = var5.getExtKpiCol();
                Object var8 = var2.get(var7);
                var1.put(var4 + "_" + var7, var8);
            }

            return var1;
        }
    }

    private Map a(Map var1) {
        HashMap var2 = new HashMap();
        Iterator var4 = this.dsMetaData.getQueryDims().iterator();

        while(var4.hasNext()) {
            QueryDim var3 = (QueryDim)var4.next();
            if (!var3.getDimCol().equalsIgnoreCase(this.a)) {
                var2.put(var3.getDimCol(), var1.get(var3.getDimCol()));
            }
        }

        return var2;
    }

    public boolean dimIsSame(DataSet var1) {
        boolean var2 = true;
        List var3 = var1.getDataSetMetaData().getQueryDims();
        List var4 = this.dsMetaData.getQueryDims();
        if (var3.size() != var4.size()) {
            var2 = false;
            return var2;
        } else {
            Iterator var6 = var3.iterator();

            while(var6.hasNext()) {
                QueryDim var5 = (QueryDim)var6.next();
                if (!this.a(var5, var4)) {
                    var2 = false;
                    break;
                }
            }

            return var2;
        }
    }

    public boolean kpiIsSame(DataSet var1) {
        boolean var2 = true;
        List var3 = var1.getDataSetMetaData().getQueryKpis();
        List var4 = this.dsMetaData.getQueryKpis();
        if (var3.size() != var4.size()) {
            var2 = false;
            return var2;
        } else {
            Iterator var6 = var3.iterator();

            while(var6.hasNext()) {
                QueryKpi var5 = (QueryKpi)var6.next();
                if (!this.a(var5, var4)) {
                    var2 = false;
                    break;
                }
            }

            return var2;
        }
    }

    private boolean a(QueryDim var1, List var2) {
        boolean var3 = false;
        Iterator var5 = var2.iterator();

        while(var5.hasNext()) {
            QueryDim var4 = (QueryDim)var5.next();
            if (var4.getDimCol().equals(var1.getDimCol())) {
                var3 = true;
                break;
            }
        }

        return var3;
    }

    private boolean a(QueryKpi var1, List var2) {
        boolean var3 = false;
        Iterator var5 = var2.iterator();

        while(var5.hasNext()) {
            QueryKpi var4 = (QueryKpi)var5.next();
            if (var4.getKpiCol().equals(var1.getKpiCol())) {
                var3 = true;
                break;
            }
        }

        return var3;
    }
}
