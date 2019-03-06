//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube.operation;

import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.builder.PageBuilder$JSObject;
import com.ruisi.ispire.dc.cube.QueryExtKpi;
import com.ruisi.ispire.dc.cube.QueryKpi;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.mozilla.javascript.Function;

public class KpiComputer extends BaseProcessor implements Processor, ScriptInvoke {
    private String a;
    private String[] b;
    private String[] c;
    private Map d = new HashMap();
    private PageBuilder$JSObject e;
    private static Logger f = Logger.getLogger(KpiComputer.class);

    public KpiComputer(String var1, String[] var2, String[] var3) {
        this.b = var2;
        this.c = var3;
        this.a = var1;
    }

    @Override
    public void setInvocable(PageBuilder$JSObject var1) {
        this.e = var1;
    }

    public void process() throws ScriptEnginerException {
        if (this.dsMetaData.getUseDKPI() == null || !this.dsMetaData.getUseDKPI() || this.dsMetaData.getJsKpiInDKPI() != null && this.dsMetaData.getJsKpiInDKPI()) {
            if (this.b.length != this.c.length) {
                throw new ScriptEnginerException(" _kpiCompute 函数传入参数不对应.");
            } else {
                if (this.a != null && this.a.length() != 0) {
                    this.ds = this.ds.getDataCenter().getDataSetById(this.a);
                    this.dataOper = new DataOperUtils(this.ds.getDatas(), this.ds.getDataSetMetaData());
                    this.dsMetaData = this.ds.getDataSetMetaData();
                }

                try {
                    for(int var1 = 0; var1 < this.b.length; ++var1) {
                        List var2 = (List)this.d.get(this.c[var1]);
                        ArrayList var3 = new ArrayList();
                        Object[] var4 = new Object[var2.size()];
                        List var5 = this.dataOper.queryDataByKpiCode((String)var2.get(0));

                        for(int var6 = 0; var6 < var5.size(); ++var6) {
                            Map var7 = (Map)var5.get(var6);
                            var4[0] = var7.get(this.dsMetaData.getZbKpiValueCol());
                            Map var8 = this.dataOper.createQueryKey(var7);
                            HashMap var9 = new HashMap();

                            for(int var10 = 1; var10 < var2.size(); ++var10) {
                                Map var11 = this.dataOper.getDataByKeysAndKpiCode(var8, (String)var2.get(var10));
                                var4[var10] = var11.get(this.dsMetaData.getZbKpiValueCol());
                                var9.put((String)var2.get(var10), var11);
                            }

                            String var27 = (String)this.d.get(this.c[var1] + "FN");
                            Function var28 = (Function)this.e.getScope().get(var27, this.e.getScope());
                            Object var12 = var28.call(this.e.getCt(), this.e.getScope(), this.e.getScope(), var4);
                            Map var13 = this.dataOper.createData(var7);
                            var13.put(this.dsMetaData.getZbKpiCol(), this.c[var1]);
                            var13.put(this.dsMetaData.getZbKpiColDesc(), (Object)null);
                            this.dataOper.putKpiData(var12, var13, this.dsMetaData.getZbKpiValueCol());
                            var3.add(var13);
                            List var14 = this.dsMetaData.getQueryExtKpis();
                            int var17;
                            if (var14 != null) {
                                Iterator var16 = var14.iterator();

                                label94:
                                while(true) {
                                    QueryExtKpi var15;
                                    do {
                                        do {
                                            if (!var16.hasNext()) {
                                                break label94;
                                            }

                                            var15 = (QueryExtKpi)var16.next();
                                        } while(var15.isCV());
                                    } while(!var15.isBaseExtKpi(this.ds.getDataCenter()) && !this.dsMetaData.isHorizontalKpi(var15.getExtKpiCol()));

                                    var4[0] = var7.get(var15.getExtKpiCol());

                                    for(var17 = 1; var17 < var2.size(); ++var17) {
                                        Map var18 = (Map)var9.get(var2.get(var17));
                                        var4[var17] = var18.get(var15.getExtKpiCol());
                                    }

                                    Function var31 = (Function)this.e.getScope().get(var27, this.e.getScope());
                                    Object var32 = var28.call(this.e.getCt(), this.e.getScope(), this.e.getScope(), var4);
                                    this.dataOper.putKpiData(var32, var13, var15.getExtKpiCol());
                                }
                            }

                            if (var14 != null) {
                                List var29 = this.ds.getDataCenter().getBaseExtKpi();
                                Map var30 = this.ds.getDataCenter().getExtKpiAndAlias();
                                var17 = this.dsMetaData.getHorizontalKpis() == null ? 0 : this.dsMetaData.getHorizontalKpis().size();
                                Object[] var33 = new Object[var29.size() + var17];

                                int var19;
                                for(var19 = 0; var19 < var17; ++var19) {
                                    String var20 = ((QueryKpi)this.dsMetaData.getHorizontalKpis().get(var19)).getKpiCol();
                                    var33[var19] = var13.get(var20);
                                }

                                for(int var34 = 0; var34 < var29.size(); ++var34) {
                                    String var21 = (String)var29.get(var34);
                                    var33[var34 + var19] = var13.get(var30.get(var21));
                                }

                                List var35 = this.ds.getDataCenter().getJsExtKpiFuncAndAlias();
                                Iterator var22 = var35.iterator();

                                while(var22.hasNext()) {
                                    String[] var36 = (String[])var22.next();
                                    Function var23 = (Function)this.e.getScope().get(var36[0], this.e.getScope());
                                    Object var24 = var23.call(this.e.getCt(), this.e.getScope(), this.e.getScope(), var33);
                                    this.dataOper.putKpiData(var24, var13, (String)var30.get(var36[1]));
                                }
                            }
                        }

                        if (this.dsMetaData.getUseDKPI() != null && this.dsMetaData.getUseDKPI()) {
                            this.ds.getDatas().clear();
                        }

                        this.ds.getDatas().addAll(var3);
                        QueryKpi var26 = new QueryKpi();
                        var26.setKpiCol(this.c[var1]);
                        this.dsMetaData.getQueryKpis().add(var26);
                    }

                } catch (Exception var25) {
                    f.error("_kpiCompute 函数调用出错.", var25);
                    throw new ScriptEnginerException("_kpiCompute 函数调用出错.", var25);
                }
            }
        }
    }

    @Override
    public StringBuffer createJSFunc() {
        StringBuffer var1 = new StringBuffer();

        for(int var2 = 0; var2 < this.b.length; ++var2) {
            ArrayList var3 = new ArrayList();
            String var4 = this.b[var2];
            String var5 = "@(\\w+)@";
            Pattern var6 = Pattern.compile(var5);
            Matcher var7 = var6.matcher(var4);

            while(var7.find()) {
                String var8 = var7.group(1);
                if (!var3.contains(var8)) {
                    var3.add(var8);
                }
            }

            int var11 = IdCreater.create();
            this.d.put(this.c[var2], var3);
            this.d.put(this.c[var2] + "FN", "kpiJS" + var11);
            var1.append("function kpiJS" + var11 + "(");

            for(int var9 = 0; var9 < var3.size(); ++var9) {
                String var10 = (String)var3.get(var9);
                var1.append(var10);
                if (var9 != var3.size() - 1) {
                    var1.append(",");
                }
            }

            var1.append("){");
            String var12 = this.b[var2].replaceAll("@", "");
            if (var12.indexOf("return") >= 0) {
                var1.append(var12);
            } else {
                var1.append(" return  " + var12);
            }

            var1.append("}\n");
        }

        return var1;
    }
}
