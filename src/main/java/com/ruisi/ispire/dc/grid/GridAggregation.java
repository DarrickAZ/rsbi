//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.grid;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.builder.PageBuilder$JSObject;
import com.ruisi.ext.engine.view.context.dc.grid.AggreVO;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import com.ruisi.ispire.dc.cube.operation.DataSetAggregation;
import com.ruisi.ispire.dc.cube.operation.ScriptInvoke;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mozilla.javascript.Function;

public class GridAggregation extends GridBaseProcessor implements ScriptInvoke {
    private String[] a;
    private AggreVO[] b;
    private boolean c;
    private PageBuilder$JSObject d;
    private String e = "aggre";

    public GridAggregation(String[] var1, AggreVO[] var2, boolean var3) {
        this.a = var1;
        this.c = var3;
        this.b = var2;
    }

    public List process() throws ScriptEnginerException {
        AggreVO[] var4;
        int var3 = (var4 = this.b).length;

        int var2;
        String var5;
        String var6;
        for(var2 = 0; var2 < var3; ++var2) {
            AggreVO var1 = var4[var2];
            var5 = var1.getType();
            if (var5 != null && var5.length() > 0 && !this.a(var5)) {
                var6 = ConstantsEngine.replace("_aggregation 聚合类型错误, ($0)", var5);
                throw new ScriptEnginerException(var6);
            }
        }

        String var7;
        if (this.a != null) {
            String[] var16;
            var3 = (var16 = this.a).length;

            for(var2 = 0; var2 < var3; ++var2) {
                String var11 = var16[var2];
                if (var11 != null) {
                    var5 = var11.toUpperCase();
                    ColumnInfo var21 = (ColumnInfo)this.metaData.getQueryColumns().get(var5);
                    if (var21 == null) {
                        var7 = ConstantsEngine.replace("aggregation函数出错，column不存在. column=$0", var11);
                        throw new ScriptEnginerException(var7);
                    }
                }
            }
        }

        LinkedHashMap var12 = new LinkedHashMap();

        for(var2 = 0; var2 < this.datas.size(); ++var2) {
            Map var13 = (Map)this.datas.get(var2);
            StringBuffer var17 = new StringBuffer("");
            if (this.a != null) {
                for(int var20 = 0; var20 < this.a.length; ++var20) {
                    var6 = this.a[var20];
                    if (var6 != null) {
                        Object var24 = var13.get(var6);
                        var17.append(var24);
                        if (var20 != this.a.length - 1) {
                            var17.append("_");
                        }
                    }
                }
            }

            var5 = var17.toString();
            if (!var12.containsKey(var5)) {
                var12.put(var5, new ArrayList());
            }

            ((List)var12.get(var5)).add(var13);
        }

        ArrayList var14 = new ArrayList();
        Iterator var18 = var12.entrySet().iterator();

        while(var18.hasNext()) {
            Entry var15 = (Entry)var18.next();
            HashMap var22 = new HashMap();
            if (this.a != null) {
                Map var23 = (Map)((List)var15.getValue()).get(0);
                String[] var10;
                int var9 = (var10 = this.a).length;

                for(int var8 = 0; var8 < var9; ++var8) {
                    var7 = var10[var8];
                    var22.put(var7, var23.get(var7));
                }
            }

            this.a((List)var15.getValue(), var22);
            this.a((Map)var22);
            var14.add(var22);
        }

        if (this.c) {
            for(var3 = 0; var3 < this.b.length; ++var3) {
                String var19 = this.b[var3].getName();
                if (this.b[var3].getAlias() != null && this.b[var3].getAlias().length() > 0) {
                    var19 = this.b[var3].getAlias();
                }

                if (var14.size() > 0) {
                    this.builder.getExtDatas().put(var19, ((Map)var14.get(0)).get(var19));
                }
            }

            return this.datas;
        } else {
            return var14;
        }
    }

    private boolean a(String var1) {
        boolean var2 = false;
        String[] var6;
        int var5 = (var6 = DataSetAggregation.aggs).length;

        for(int var4 = 0; var4 < var5; ++var4) {
            String var3 = var6[var4];
            if (var3.equalsIgnoreCase(var1)) {
                var2 = true;
                break;
            }
        }

        return var2;
    }

    @Override
    public StringBuffer createJSFunc() {
        StringBuffer var1 = new StringBuffer();
        ArrayList var2 = new ArrayList();
        ArrayList var3 = new ArrayList();
        String var4 = "([max|min|avg|count|sum]+)\\((\\w+)\\)";
        Pattern var5 = Pattern.compile(var4);
        AggreVO[] var9;
        int var8 = (var9 = this.b).length;

        for(int var7 = 0; var7 < var8; ++var7) {
            AggreVO var6 = var9[var7];
            if (var6.getExpression() != null && var6.getExpression()) {
                Matcher var10 = var5.matcher(var6.getName());

                while(var10.find()) {
                    String var11 = var10.group(2);
                    String var12 = var10.group(1);
                    if (!var2.contains(var11)) {
                        var2.add(var11);
                        var3.add(var12);
                    }
                }

                int var14 = IdCreater.create();
                var6.setParams(var2);
                var6.setParamAggre(var3);
                var6.setFuncName(this.e + var14);
                var1.append("function " + var6.getFuncName() + "(");

                for(int var15 = 0; var15 < var2.size(); ++var15) {
                    String var13 = (String)var2.get(var15);
                    var1.append(var13);
                    if (var15 != var2.size() - 1) {
                        var1.append(",");
                    }
                }

                var1.append("){");
                var1.append(" return  " + var6.getName().replaceAll("[max|min|avg|count|sum]", "").replaceAll("[\\(|\\)]", ""));
                var1.append(";}\n");
            }
        }

        return var1;
    }

    @Override
    public void setInvocable(PageBuilder$JSObject var1) {
        this.d = var1;
    }

    private void a(List var1, Map var2) {
        int var3 = 0;
        AggreVO[] var7;
        int var6 = (var7 = this.b).length;

        int var5;
        for(var5 = 0; var5 < var6; ++var5) {
            AggreVO var4 = var7[var5];
            Boolean var8 = var4.getExpression();
            if (var8 != null && var8) {
                var3 += var4.getParams().size();
            } else {
                ++var3;
            }
        }

        List[] var13 = new List[var3];

        for(var5 = 0; var5 < var13.length; ++var5) {
            var13[var5] = new ArrayList();
        }

        Iterator var14 = var1.iterator();

        int var17;
        while(var14.hasNext()) {
            Map var15 = (Map)var14.next();
            int var16 = 0;

            for(var17 = 0; var17 < this.b.length; ++var17) {
                Boolean var9 = this.b[var17].getExpression();
                Double var12;
                if (var9 != null && var9) {
                    for(int var21 = 0; var21 < this.b[var17].getParams().size(); ++var21) {
                        List var22 = var13[var16];
                        var12 = GridDataUtils.getKpiData(var15, (String)this.b[var17].getParams().get(var21));
                        if (var12 != null) {
                            var22.add(var12);
                        }

                        ++var16;
                    }
                } else {
                    List var10 = var13[var16];
                    String var11 = this.b[var17].getName();
                    var12 = GridDataUtils.getKpiData(var15, var11);
                    if (var12 != null) {
                        var10.add(var12);
                    }

                    ++var16;
                }
            }
        }

        var5 = 0;

        for(var6 = 0; var6 < this.b.length; ++var6) {
            Boolean var18 = this.b[var6].getExpression();
            if (var18 != null && var18) {
                for(var17 = 0; var17 < this.b[var6].getParams().size(); ++var17) {
                    List var20 = var13[var5];
                    this.a(var20, (String)this.b[var6].getParamAggre().get(var17), (String)this.b[var6].getParams().get(var17), (String)null, var2);
                    ++var5;
                }
            } else {
                List var19 = var13[var5];
                this.a(var19, this.b[var6].getType(), this.b[var6].getName(), this.b[var6].getAlias(), var2);
                ++var5;
            }
        }

    }

    private void a(Map var1) {
        AggreVO[] var5;
        int var4 = (var5 = this.b).length;

        for(int var3 = 0; var3 < var4; ++var3) {
            AggreVO var2 = var5[var3];
            Boolean var6 = var2.getExpression();
            if (var6 != null && var6) {
                String var7 = var2.getFuncName();
                Object[] var8 = new Object[var2.getParams().size()];

                for(int var9 = 0; var9 < var2.getParams().size(); ++var9) {
                    var8[var9] = var1.get(var2.getParams().get(var9));
                }

                Function var11 = (Function)this.d.getScope().get(var7, this.d.getScope());
                Object var10 = var11.call(this.d.getCt(), this.d.getScope(), this.d.getScope(), var8);
                GridDataUtils.putComputeData(var10, var1, var2.getAlias());
            }
        }

    }

    private void a(List var1, String var2, String var3, String var4, Map var5) {
        if (var2.equalsIgnoreCase("count")) {
            var5.put(var4 != null && var4.length() != 0 ? var4 : var3, new Double((double)var1.size()));
        } else {
            Double var6 = null;
            if (var1.size() != 0) {
                var6 = new Double(0.0D);
            }

            if (var2.equalsIgnoreCase("sum") || var2.equalsIgnoreCase("avg")) {
                Double var7;
                for(Iterator var8 = var1.iterator(); var8.hasNext(); var6 = var6 + var7) {
                    var7 = (Double)var8.next();
                }

                if (var2.equalsIgnoreCase("avg")) {
                    var6 = var6 / (double)var1.size();
                }
            }

            if (var2.equalsIgnoreCase("max")) {
                var6 = (Double)Collections.max(var1);
            }

            if (var2.equalsIgnoreCase("min")) {
                var6 = (Double)Collections.min(var1);
            }

            var5.put(var4 != null && var4.length() != 0 ? var4 : var3, var6);
        }

    }
}
