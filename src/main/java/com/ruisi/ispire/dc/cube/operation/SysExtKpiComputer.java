//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube.operation;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.builder.PageBuilder$JSObject;
import com.ruisi.ispire.dc.cube.DataSet;
import com.ruisi.ispire.dc.cube.DataSetMetaData;
import com.ruisi.ispire.dc.cube.QueryExtKpi;
import com.ruisi.ispire.dc.cube.QueryKpi;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.mozilla.javascript.Function;

public class SysExtKpiComputer {
    private DataSet a;
    private DataOperUtils b;
    private DataSetMetaData c;
    private PageBuilder$JSObject d;
    private List e = new ArrayList();
    private static Logger f = Logger.getLogger(SysExtKpiComputer.class);

    public List getCreaterFuncNameAndAlias() {
        return this.e;
    }

    public SysExtKpiComputer(DataSet var1) {
        this.a = var1;
        this.b = new DataOperUtils(var1.getDatas(), var1.getDataSetMetaData());
        this.c = var1.getDataSetMetaData();
    }

    public SysExtKpiComputer(DataSet var1, List var2) {
        this.a = var1;
        this.b = new DataOperUtils(var1.getDatas(), var1.getDataSetMetaData());
        this.c = var1.getDataSetMetaData();
        this.e = var2;
    }

    public void setInvocable(PageBuilder$JSObject var1) {
        this.d = var1;
    }

    public void process(String var1) throws ScriptEnginerException {
        try {
            List var2 = this.a.getDataCenter().getBaseExtKpi();
            List var16 = this.a.getDataCenter().getExtKpiFormula();
            Map var4 = this.a.getDataCenter().getExtKpiAndAlias();
            if (var2 != null && var16 != null) {
                int var5 = this.c.getHorizontalKpis() == null ? 0 : this.c.getHorizontalKpis().size();
                Object[] var6 = new Object[var2.size() + var5];
                List var7 = this.a.getDatas();

                int var8;
                for(var8 = 0; var8 < var7.size(); ++var8) {
                    Map var9 = (Map)var7.get(var8);

                    int var10;
                    for(var10 = 0; var10 < var5; ++var10) {
                        String var11 = ((QueryKpi)this.c.getHorizontalKpis().get(var10)).getKpiCol();
                        var6[var10] = var9.get(var11);
                    }

                    int var19;
                    for(var19 = 0; var19 < var2.size(); ++var19) {
                        String var12 = (String)var2.get(var19);
                        var6[var19 + var10] = var9.get(var4.get(var12));
                    }

                    for(var19 = 0; var19 < this.e.size(); ++var19) {
                        String[] var20 = (String[])this.e.get(var19);
                        Function var13 = (Function)this.d.getScope().get(var20[0], this.d.getScope());
                        Object var14 = var13.call(this.d.getCt(), this.d.getScope(), this.d.getScope(), var6);
                        this.b.putKpiData(var14, var9, (String)var4.get(var20[1]));
                    }
                }

                for(var8 = 0; var8 < var2.size(); ++var8) {
                    String var17 = (String)var4.get(var2.get(var8));
                    if (!this.c.isExtKpi(var17)) {
                        QueryExtKpi var18 = new QueryExtKpi();
                        var18.setExtKpiCol(var17);
                        var18.setExpress((String)var2.get(var8));
                        this.c.getQueryExtKpis().add(var18);
                    }
                }

            }
        } catch (Exception var15) {
            String var3 = ConstantsEngine.replace("衍生指标计算出错. $0", var1);
            f.error(var3, var15);
            throw new ScriptEnginerException(var3, var15);
        }
    }

    public String createExtKpiJsFunc() throws ScriptEnginerException {
        List var1 = this.a.getDataCenter().getExtKpiFormula();
        List var2 = this.a.getDataCenter().getBaseExtKpi();
        if (var1 != null && var2 != null) {
            String var3 = "(?:CVA|CV)(?:(?:\\.\\d+[MDY])|LY)?";
            Pattern var4 = Pattern.compile(var3);
            String var5 = "@(\\w+)@";
            Pattern var6 = Pattern.compile(var5);
            StringBuffer var7 = new StringBuffer();

            for(Iterator var9 = var1.iterator(); var9.hasNext(); var7.append("}\n")) {
                String var8 = (String)var9.next();
                int var10 = IdCreater.create();
                var7.append(" function sysExtKpiJS" + var10 + "(");
                Matcher var11 = var6.matcher(var8);

                String var13;
                while(var11.find()) {
                    String var12 = var11.group(1);
                    if (!this.c.isHorizontalKpi(var12)) {
                        var13 = ConstantsEngine.replace("需要计算的 horizontalKpi不存在， horizontalKpi=$0", var12);
                        throw new ScriptEnginerException(var13);
                    }
                }

                if (this.c.getHorizontalKpis() != null) {
                    Iterator var17 = this.c.getHorizontalKpis().iterator();

                    while(var17.hasNext()) {
                        QueryKpi var15 = (QueryKpi)var17.next();
                        var7.append(var15.getKpiCol());
                        var7.append(",");
                    }
                }

                this.e.add(new String[]{"sysExtKpiJS" + var10, var8});

                for(int var16 = 0; var16 < var2.size(); ++var16) {
                    var13 = (String)var2.get(var16);
                    var7.append((String)this.a.getDataCenter().getExtKpiAndAlias().get(var13));
                    if (var16 != var2.size() - 1) {
                        var7.append(",");
                    }
                }

                var7.append("){");
                Matcher var18 = var4.matcher(var8);
                StringBuffer var19 = new StringBuffer();

                while(var18.find()) {
                    String var14 = var18.group();
                    var18.appendReplacement(var19, (String)this.a.getDataCenter().getExtKpiAndAlias().get(var14));
                }

                var18.appendTail(var19);
                var8 = var19.toString();
                var8 = var8.replaceAll("@", "");
                if (var8.indexOf("return") >= 0) {
                    var7.append(var8);
                } else {
                    var7.append(" return " + var8);
                }
            }

            return var7.toString();
        } else {
            return "";
        }
    }
}
