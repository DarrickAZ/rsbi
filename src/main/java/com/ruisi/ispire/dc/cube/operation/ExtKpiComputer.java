//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube.operation;

import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.builder.PageBuilder$JSObject;
import com.ruisi.ispire.dc.cube.QueryExtKpi;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.mozilla.javascript.Function;

public class ExtKpiComputer extends BaseProcessor implements Processor, ScriptInvoke {
    private String[] a;
    private String[] b;
    private Map c = new HashMap();
    private PageBuilder$JSObject d;
    private static Logger e = Logger.getLogger(ExtKpiComputer.class);

    public ExtKpiComputer(String[] var1, String[] var2) {
        this.a = var1;
        this.b = var2;
    }

    @Override
    public void setInvocable(PageBuilder$JSObject var1) {
        this.d = var1;
    }

    @Override
    public void process() throws ScriptEnginerException {
        if (this.a.length != this.b.length) {
            throw new ScriptEnginerException("_extKpiCompute 函数传入参数不对应.");
        } else {
            try {
                for(int var1 = 0; var1 < this.a.length; ++var1) {
                    List var2 = (List)this.c.get(this.b[var1]);
                    Object[] var3 = new Object[var2.size()];
                    List var4 = this.ds.getDatas();

                    for(int var5 = 0; var5 < var4.size(); ++var5) {
                        Map var6 = (Map)var4.get(var5);

                        for(int var7 = 0; var7 < var2.size(); ++var7) {
                            var3[var7] = var6.get(var2.get(var7));
                        }

                        String var12 = (String)this.c.get(this.b[var1] + "FN");
                        Function var8 = (Function)this.d.getScope().get(var12, this.d.getScope());
                        Object var9 = var8.call(this.d.getCt(), this.d.getScope(), this.d.getScope(), var3);
                        this.dataOper.putKpiData(var9, var6, this.b[var1]);
                    }

                    QueryExtKpi var11 = new QueryExtKpi();
                    var11.setExtKpiCol(this.b[var1]);
                    if (this.dsMetaData.getQueryExtKpis() == null) {
                        this.dsMetaData.setQueryExtKpis(new ArrayList());
                    }

                    this.dsMetaData.getQueryExtKpis().add(var11);
                }

            } catch (Exception var10) {
                e.error("_extKpiCompute 函数调用出错.", var10);
                throw new ScriptEnginerException("_extKpiCompute 函数调用出错.", var10);
            }
        }
    }

    @Override
    public StringBuffer createJSFunc() {
        StringBuffer var1 = new StringBuffer();

        for(int var2 = 0; var2 < this.a.length; ++var2) {
            ArrayList var3 = new ArrayList();
            String var4 = "@\\w+@";
            Pattern var5 = Pattern.compile(var4);
            Matcher var6 = var5.matcher(this.a[var2]);

            while(var6.find()) {
                String var7 = var6.group();
                var7 = var7.replaceAll("@", "");
                if (!var3.contains(var7)) {
                    var3.add(var7);
                }
            }

            int var10 = IdCreater.create();
            this.c.put(this.b[var2], var3);
            this.c.put(this.b[var2] + "FN", "extKpiJS" + var10 + var2);
            var1.append("function extKpiJS" + var10 + var2 + "(");

            for(int var8 = 0; var8 < var3.size(); ++var8) {
                String var9 = (String)var3.get(var8);
                var1.append(var9);
                if (var8 != var3.size() - 1) {
                    var1.append(",");
                }
            }

            var1.append("){");
            var1.append(" return  " + this.a[var2].replaceAll("@", ""));
            var1.append("}\n");
        }

        return var1;
    }
}
