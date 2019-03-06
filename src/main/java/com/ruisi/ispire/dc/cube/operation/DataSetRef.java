//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube.operation;

import com.ruisi.ext.engine.util.ContextUtils;
import com.ruisi.ext.engine.view.builder.PageBuilder$JSObject;
import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import com.ruisi.ext.engine.view.context.dc.cube.DataSetDimContext;
import com.ruisi.ispire.dc.cube.DataCenter;
import com.ruisi.ispire.dc.cube.DataSet;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataSetRef extends BaseProcessor implements Processor, ScriptInvoke {
    private PageBuilder$JSObject a;
    private int b;
    private String c;

    @Override
    public void process() throws ScriptEnginerException {
        DataCenterContext var1 = this.ds.getConf();
        if (!this.a(var1)) {
            throw new ScriptEnginerException("需要进行 _ref 操作，但数据集中无账期维度. ");
        } else {
            this.a(var1, this.ds.getDataCenter());
            DataSet var2 = this.ds.getDataCenter().getDataSet(var1);
            DataSetHor2Ver var3 = new DataSetHor2Ver(var2, var1);
            var3.process();
            List var4 = var1.getDataSetConf().getHorizontalKpis();
            if (var4 != null && var4.size() > 0) {
                HorizontalKpiDeal var5 = new HorizontalKpiDeal(var2, var4);
                var5.process();
            }

            SysExtKpiComputer var7 = new SysExtKpiComputer(var2, this.ds.getDataCenter().getJsExtKpiFuncAndAlias());
            var7.setInvocable(this.a);
            var7.process((String)null);
            SysKpiComputer var6 = new SysKpiComputer(var2, var1.getDataSetConf().getComputeKpis(), this.ds.getDataCenter().getSysKpiComputeFNameAndParams());
            var6.setInvocable(this.a);
            var6.process();
            if (this.ds.getDataCenter().getRefDataSets() == null) {
                this.ds.getDataCenter().setRefDataSets(new HashMap());
            }

            this.ds.getDataCenter().getRefDataSets().put(this.c, var2);
        }
    }

    private boolean a(DataCenterContext var1) {
        boolean var2 = false;
        List var3 = var1.getDataSetConf().getDims();
        if (var3 == null) {
            return var2;
        } else {
            Iterator var5 = var3.iterator();

            while(var5.hasNext()) {
                DataSetDimContext var4 = (DataSetDimContext)var5.next();
                if ("month".equalsIgnoreCase(var4.getType()) || "day".equalsIgnoreCase(var4.getType())) {
                    var2 = true;
                    break;
                }
            }

            return var2;
        }
    }

    private void a(DataCenterContext var1, DataCenter var2) {
        List var3 = var1.getDataSetConf().getDims();
        DataSetDimContext var4 = null;
        String var5 = null;
        Iterator var7 = var3.iterator();

        while(var7.hasNext()) {
            DataSetDimContext var6 = (DataSetDimContext)var7.next();
            if ("month".equalsIgnoreCase(var6.getType()) || "day".equalsIgnoreCase(var6.getType())) {
                var4 = var6;
                var5 = var6.getType();
                break;
            }
        }

        String[] var15 = var4.getValues().split("@");
        ContextUtils var16 = new ContextUtils("HTML");
        Map var8 = var2.getInputParams();
        String[] var12 = var15;
        int var11 = var15.length;

        for(int var10 = 0; var10 < var11; ++var10) {
            String var9 = var12[var10];
            String var14;
            if (var9.startsWith("$")) {
                String var13 = var9.replaceAll("\\$", "");
                var14 = (String)var8.get(var13);
                if ("month".equalsIgnoreCase(var5)) {
                    var14 = var16.monthCompute(var14, -this.b);
                } else {
                    var14 = var16.dayCompute(var14, -this.b);
                }

                var8.put(var13, var14);
            } else {
                var14 = (String)var8.get(var9);
                if ("month".equalsIgnoreCase(var5)) {
                    var14 = var16.monthCompute(var14, -this.b);
                } else {
                    var14 = var16.dayCompute(var14, -this.b);
                }

                var8.put(var9, var14);
            }
        }

    }

    public DataSetRef(int var1, String var2) {
        this.c = var2;
        this.b = var1;
    }

    @Override
    public StringBuffer createJSFunc() {
        return new StringBuffer();
    }

    @Override
    public void setInvocable(PageBuilder$JSObject var1) {
        this.a = var1;
    }
}
