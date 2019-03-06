//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube.operation;

import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.builder.PageBuilder$JSObject;
import com.ruisi.ext.engine.view.context.dc.cube.DsComputeKpiContext;
import com.ruisi.ispire.dc.cube.DataSet;
import com.ruisi.ispire.dc.cube.DataSetMetaData;
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

public class SysKpiComputer {
    private DataSet a;
    private DataOperUtils b;
    private DataSetMetaData c;
    private List d;
    private Map e = new HashMap();
    private PageBuilder$JSObject f;
    private static Logger g = Logger.getLogger(SysKpiComputer.class);

    public SysKpiComputer(DataSet var1, List var2) {
        this.a = var1;
        this.b = new DataOperUtils(var1.getDatas(), var1.getDataSetMetaData());
        this.c = var1.getDataSetMetaData();
        this.d = var2;
    }

    public SysKpiComputer(DataSet var1, List var2, Map var3) {
        this.a = var1;
        this.b = new DataOperUtils(var1.getDatas(), var1.getDataSetMetaData());
        this.c = var1.getDataSetMetaData();
        this.d = var2;
        this.e = var3;
    }

    public void setInvocable(PageBuilder$JSObject var1) {
        this.f = var1;
    }

    public void process() throws ScriptEnginerException {
        if (this.d != null && this.d.size() != 0) {
            if (this.c.getUseDKPI() == null || !this.c.getUseDKPI() || this.c.getJsKpiInDKPI() != null && this.c.getJsKpiInDKPI()) {
                try {
                    for(int var1 = 0; var1 < this.d.size(); ++var1) {
                        DsComputeKpiContext var2 = (DsComputeKpiContext)this.d.get(var1);
                        List var3 = (List)this.e.get(var2.getName());
                        ArrayList var4 = new ArrayList();
                        Object[] var5 = new Object[var3.size()];
                        List var6 = this.b.queryDataByKpiCode((String)var3.get(0));

                        for(int var7 = 0; var7 < var6.size(); ++var7) {
                            Map var8 = (Map)var6.get(var7);
                            var5[0] = var8.get(this.c.getZbKpiValueCol());
                            Map var9 = this.b.createQueryKey(var8);
                            HashMap var10 = new HashMap();

                            for(int var11 = 1; var11 < var3.size(); ++var11) {
                                Map var12 = this.b.getDataByKeysAndKpiCode(var9, (String)var3.get(var11));
                                var5[var11] = var12.get(this.c.getZbKpiValueCol());
                                var10.put((String)var3.get(var11), var12);
                            }

                            String var28 = (String)this.e.get(var2.getName() + "FN");
                            Function var29 = (Function)this.f.getScope().get(var28, this.f.getScope());
                            Object var13 = var29.call(this.f.getCt(), this.f.getScope(), this.f.getScope(), var5);
                            Map var14 = this.b.createData(var8);
                            var14.put(this.c.getZbKpiCol(), var2.getName());
                            var14.put(this.c.getZbKpiColDesc(), (Object)null);
                            this.b.putKpiData(var13, var14, this.c.getZbKpiValueCol());
                            var4.add(var14);
                            List var15 = this.c.getQueryExtKpis();
                            int var18;
                            if (var15 != null) {
                                Iterator var17 = var15.iterator();

                                label90:
                                while(true) {
                                    QueryExtKpi var16;
                                    do {
                                        do {
                                            if (!var17.hasNext()) {
                                                break label90;
                                            }

                                            var16 = (QueryExtKpi)var17.next();
                                        } while(var16.isCV());
                                    } while(!var16.isBaseExtKpi(this.a.getDataCenter()) && !this.c.isHorizontalKpi(var16.getExtKpiCol()));

                                    var5[0] = var8.get(var16.getExtKpiCol());

                                    for(var18 = 1; var18 < var3.size(); ++var18) {
                                        Map var19 = (Map)var10.get(var3.get(var18));
                                        var5[var18] = var19.get(var16.getExtKpiCol());
                                    }

                                    Function var32 = (Function)this.f.getScope().get(var28, this.f.getScope());
                                    Object var33 = var32.call(this.f.getCt(), this.f.getScope(), this.f.getScope(), var5);
                                    this.b.putKpiData(var33, var14, var16.getExtKpiCol());
                                }
                            }

                            if (var15 != null) {
                                List var30 = this.a.getDataCenter().getBaseExtKpi();
                                Map var31 = this.a.getDataCenter().getExtKpiAndAlias();
                                var18 = this.c.getHorizontalKpis() == null ? 0 : this.c.getHorizontalKpis().size();
                                Object[] var34 = new Object[var30.size() + var18];

                                int var20;
                                for(var20 = 0; var20 < var18; ++var20) {
                                    String var21 = ((QueryKpi)this.c.getHorizontalKpis().get(var20)).getKpiCol();
                                    var34[var20] = var14.get(var21);
                                }

                                for(int var35 = 0; var35 < var30.size(); ++var35) {
                                    String var22 = (String)var30.get(var35);
                                    var34[var35 + var20] = var14.get(var31.get(var22));
                                }

                                List var36 = this.a.getDataCenter().getJsExtKpiFuncAndAlias();
                                Iterator var23 = var36.iterator();

                                while(var23.hasNext()) {
                                    String[] var37 = (String[])var23.next();
                                    Function var10000 = (Function)this.f.getScope().get(var37[0], this.f.getScope());
                                    Object var25 = var29.call(this.f.getCt(), this.f.getScope(), this.f.getScope(), var34);
                                    this.b.putKpiData(var25, var14, (String)var31.get(var37[1]));
                                }
                            }
                        }

                        if (this.c.getUseDKPI() != null && this.c.getUseDKPI()) {
                            this.a.getDatas().clear();
                        }

                        this.a.getDatas().addAll(var4);
                        QueryKpi var27 = new QueryKpi();
                        var27.setKpiCol(var2.getName());
                        this.c.getQueryKpis().add(var27);
                    }

                } catch (Exception var26) {
                    g.error("_kpiCompute 函数调用出错.", var26);
                    throw new ScriptEnginerException("_kpiCompute 函数调用出错.", var26);
                }
            }
        }
    }

    public StringBuffer createJSFunc() {
        StringBuffer var1 = new StringBuffer();
        if (this.d != null && this.d.size() != 0) {
            for(int var2 = 0; var2 < this.d.size(); ++var2) {
                DsComputeKpiContext var3 = (DsComputeKpiContext)this.d.get(var2);
                ArrayList var4 = new ArrayList();
                String var5 = var3.getFormula();
                String var6 = "@(\\w+)@";
                Pattern var7 = Pattern.compile(var6);
                Matcher var8 = var7.matcher(var5);

                while(var8.find()) {
                    String var9 = var8.group(1);
                    if (!var4.contains(var9)) {
                        var4.add(var9);
                    }
                }

                int var13 = IdCreater.create();
                this.e.put(var3.getName(), var4);
                this.e.put(var3.getName() + "FN", "sysKpiJS" + var13);
                var1.append("function sysKpiJS" + var13 + "(");

                for(int var10 = 0; var10 < var4.size(); ++var10) {
                    String var11 = (String)var4.get(var10);
                    var1.append(var11);
                    if (var10 != var4.size() - 1) {
                        var1.append(",");
                    }
                }

                var1.append("){");
                String var12 = var3.getFormula().replaceAll("@", "");
                if (var12.indexOf("return") >= 0) {
                    var1.append(var12);
                } else {
                    var1.append(" return  " + var12);
                }

                var1.append("}\n");
            }

            return var1;
        } else {
            return var1;
        }
    }

    public Map getFuncParamsAndNames() {
        return this.e;
    }
}
