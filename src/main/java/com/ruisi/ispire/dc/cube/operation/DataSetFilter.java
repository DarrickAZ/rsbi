//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube.operation;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.builder.PageBuilder$JSObject;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.mozilla.javascript.Function;

public class DataSetFilter extends BaseProcessor implements Processor, ScriptInvoke {
    public static final String funcName = "dimFilter";
    public static final Logger log = Logger.getLogger(DataSetFilter.class);
    private String a;
    private List b = new ArrayList();
    private PageBuilder$JSObject c;
    private String d;

    public DataSetFilter(String var1) {
        this.a = var1;
    }

    public void setInvocable(PageBuilder$JSObject var1) {
        this.c = var1;
    }

    @Override
    public void process() throws ScriptEnginerException {
        Iterator var2 = this.b.iterator();

        while(var2.hasNext()) {
            String var1 = (String)var2.next();
            if (!this.dsMetaData.isDim(var1) && !this.dsMetaData.isKpi(var1) && !this.dsMetaData.isExtKpi(var1) && !this.dsMetaData.isCKP_VALUE(var1)) {
                String var3 = ConstantsEngine.replace("_filterDim 过滤字段错误，($0)", var1);
                throw new ScriptEnginerException(var3);
            }
        }

        try {
            ArrayList var10 = new ArrayList();
            int var12 = this.ds.getDatas().size();

            for(int var13 = 0; var13 < var12; ++var13) {
                Map var4 = (Map)this.ds.getDatas().get(var13);
                Object[] var5 = new Object[this.b.size()];
                boolean var6 = false;

                for(int var7 = 0; var7 < this.b.size(); ++var7) {
                    String var8 = (String)this.b.get(var7);
                    if (this.dsMetaData.isDim(var8)) {
                        var5[var7] = var4.get(this.b.get(var7));
                    } else if (this.dsMetaData.isCKP_VALUE(var8)) {
                        var5[var7] = var4.get(this.dsMetaData.getZbKpiValueCol());
                    } else if (this.dsMetaData.isKpi(var8)) {
                        if (!var4.get(this.dsMetaData.getZbKpiCol()).equals(var8)) {
                            var10.add(var4);
                            var6 = true;
                            break;
                        }

                        var5[var7] = var4.get(this.dsMetaData.getZbKpiValueCol());
                    } else {
                        var5[var7] = var4.get(this.b.get(var7));
                    }
                }

                if (!var6) {
                    Function var14 = (Function)this.c.getScope().get(this.d, this.c.getScope());
                    Boolean var15 = (Boolean)var14.call(this.c.getCt(), this.c.getScope(), this.c.getScope(), var5);
                    if (var15 != null && var15) {
                        var10.add(var4);
                    }
                }
            }

            this.ds.setDatas(var10);
        } catch (Exception var9) {
            String var11 = ConstantsEngine.replace("_filter 表达式错误. ($0)", this.a);
            log.error(var11, var9);
            throw new ScriptEnginerException(var11);
        }
    }

    @Override
    public StringBuffer createJSFunc() {
        StringBuffer var1 = new StringBuffer();
        String var2 = "@(\\w+)@";
        Pattern var3 = Pattern.compile(var2);
        Matcher var4 = var3.matcher(this.a);

        while(var4.find()) {
            String var5 = var4.group(1);
            if (!this.b.contains(var5)) {
                this.b.add(var5);
            }
        }

        this.d = "dimFilter" + IdCreater.create();
        var1.append("function " + this.d + "(");

        for(int var7 = 0; var7 < this.b.size(); ++var7) {
            String var6 = (String)this.b.get(var7);
            var1.append(var6);
            if (var7 != this.b.size() - 1) {
                var1.append(",");
            }
        }

        var1.append("){");
        var1.append(" return  " + this.a.replaceAll("@", ""));
        var1.append("} \n");
        return var1;
    }
}
