//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.cross;

import com.ruisi.ext.engine.cross.CrossFieldLoader;
import com.ruisi.ext.engine.view.context.cross.CrossField;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.view.context.cross.RowDimContext;
import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ispire.dc.cube.DataCenter;
import com.ruisi.ispire.dc.cube.DataSet;
import com.ruisi.ispire.dc.cube.DataSetMetaData;
import com.ruisi.ispire.dc.cube.QueryDim;
import com.ruisi.ispire.dc.cube.QueryExtKpi;
import com.ruisi.ispire.dc.cube.QueryKpi;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BackGroundAggDataCenter extends DataCenter {
    private CrossReportContext a;
    private ExtRequest b;

    public BackGroundAggDataCenter(CrossReportContext var1, ExtRequest var2) {
        this.a = var1;
        this.b = var2;
    }

    public DataSet getDataSet(DataCenterContext var1) {
        DataSet var2 = new DataSet();
        DataSetMetaData var3 = this.a();
        var2.setDataSetMetaData(var3);
        var2.setDataCenter(this);
        List var4 = this.a.loadOptions();
        var2.setDatas(var4);
        return var2;
    }

    private DataSetMetaData a() {
        ArrayList var1 = new ArrayList();
        ArrayList var2 = new ArrayList();
        ArrayList var3 = new ArrayList();
        this.a(this.a.getCrossCols().getCols(), var2, var1, var3);
        this.a(this.a.getCrossRows().getRows(), var2, var1, var3);
        if (var2.size() == 0 && var1.size() == 0 && this.a.getBaseKpi() != null) {
            String var4 = this.a.getBaseKpi().getType();
            if ("kpi".equalsIgnoreCase(var4)) {
                QueryKpi var5 = new QueryKpi();
                var5.setKpiCol(this.a.getBaseKpi().getAlias());
                var5.setAggregation(this.a.getBaseKpi().getAggregation());
                var2.add(var5);
            }

            if ("kpiOther".equalsIgnoreCase(var4)) {
                QueryExtKpi var9 = new QueryExtKpi();
                var9.setExpress((String)null);
                var9.setExtKpiCol(this.a.getBaseKpi().getAlias());
                var9.setAggregation(this.a.getBaseKpi().getAggregation());
                var1.add(var9);
            }
        }

        if (var2.size() == 0) {
            CrossFieldLoader var7 = (CrossFieldLoader)this.b.getAttribute("ext.view.fieldLoader");
            CrossField var11 = var7.loadFieldByKpiCode((String)null);
            QueryKpi var6 = new QueryKpi();
            var6.setKpiCol(var11.getAlias());
            var6.setAggregation(var11.getAggregation());
            var2.add(var6);
            this.a(var6.getKpiCol());
        }

        if (this.a.getDims() != null) {
            for(int var8 = 1; var8 < this.a.getDims().size(); ++var8) {
                RowDimContext var12 = (RowDimContext)this.a.getDims().get(var8);
                QueryDim var13 = new QueryDim();
                var13.setDimCol(var12.getCode());
                var13.setDimDescCol(var12.getCodeDesc());
                var13.setType(var12.getType());
                var3.add(var13);
            }
        }

        DataSetMetaData var10 = new DataSetMetaData();
        var10.setQueryKpis(var2);
        var10.setQueryExtKpis(var1);
        var10.setQueryDims(var3);
        return var10;
    }

    private void a(String var1) {
        List var2 = this.a.loadOptions();

        for(int var3 = 0; var3 < var2.size(); ++var3) {
            Map var4 = (Map)var2.get(var3);
            var4.put(var1, new BigDecimal(1));
        }

    }

    private void a(List var1, List var2, List var3, List var4) {
        if (var1 != null && var1.size() != 0) {
            CrossField var5;
            for(Iterator var6 = var1.iterator(); var6.hasNext(); this.a(var5.getSubs(), var2, var3, var4)) {
                var5 = (CrossField)var6.next();
                String var7 = var5.getType();
                if ("kpi".equalsIgnoreCase(var7)) {
                    QueryKpi var8 = new QueryKpi();
                    var8.setKpiCol(var5.getAlias());
                    var8.setAggregation(var5.getAggregation());
                    var2.add(var8);
                } else if ("kpiOther".equalsIgnoreCase(var7)) {
                    QueryExtKpi var9 = new QueryExtKpi();
                    var9.setExpress((String)null);
                    var9.setExtKpiCol(var5.getAlias());
                    var9.setAggregation(var5.getAggregation());
                    var3.add(var9);
                } else {
                    QueryDim var10 = new QueryDim();
                    var10.setDimCol(var5.getAlias());
                    var10.setDimDescCol(var5.getAliasDesc());
                    var10.setType(var5.getType());
                    var4.add(var10);
                }
            }

        }
    }
}
