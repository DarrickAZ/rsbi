//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube.operation;

import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import com.ruisi.ispire.dc.cube.DataSet;
import com.ruisi.ispire.dc.cube.DataSetMetaData;
import com.ruisi.ispire.dc.cube.QueryKpi;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataSetHor2Ver {
    private DataSet a;
    private DataOperUtils b;
    private DataSetMetaData c;
    private DataCenterContext d;

    public DataSetHor2Ver(DataSet var1, DataCenterContext var2) {
        this.a = var1;
        this.b = new DataOperUtils(var1.getDatas(), var1.getDataSetMetaData());
        this.c = var1.getDataSetMetaData();
        this.d = var2;
    }

    public void process() throws ScriptEnginerException {
        ArrayList var1 = new ArrayList();
        Iterator var3 = this.a.getDatas().iterator();

        while(var3.hasNext()) {
            Map var2 = (Map)var3.next();
            Iterator var5 = this.c.getQueryKpis().iterator();

            while(var5.hasNext()) {
                QueryKpi var4 = (QueryKpi)var5.next();
                Map var6 = this.b.createData(var2);
                this.a(var6, var4, var2);
                this.b(var6, var4, var2);
                var1.add(var6);
            }
        }

        this.a.setDatas(var1);
        if (this.d.getDataSetConf().isKpiOrder()) {
            DataSetSort var7 = new DataSetSort((String)null, this.c.getZbKpiCol(), "asc", false);
            var7.initDataSet(this.a);
            var7.process();
        }

    }

    private void a(Map var1, QueryKpi var2, Map var3) {
        var1.put(this.c.getZbKpiCol(), var2.getKpiCol());
        var1.put(this.c.getZbKpiValueCol(), var3.get(var2.getKpiCol() + "_" + this.c.getZbKpiValueCol()));
    }

    private void b(Map var1, QueryKpi var2, Map var3) {
        List var4 = this.a.getDataCenter().getBaseExtKpi();
        if (var4 != null && var4.size() != 0) {
            Map var5 = this.a.getDataCenter().getExtKpiAndAlias();
            Iterator var7 = var4.iterator();

            while(var7.hasNext()) {
                String var6 = (String)var7.next();
                if (!var6.equals("CV")) {
                    String var8 = (String)var5.get(var6);
                    var1.put(var8, var3.get(var2.getKpiCol() + var8 + "_" + var8));
                }
            }

        }
    }
}
