//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.grid;

import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.view.builder.PageBuilder$JSObject;
import com.ruisi.ispire.dc.cube.operation.ScriptInvoke;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mozilla.javascript.Function;

public class GridCompute extends GridBaseProcessor implements ScriptInvoke {
    private String a;
    private String b;
    private PageBuilder$JSObject c;
    private String d;
    private List e;

    public GridCompute(String var1, String var2) {
        this.a = var1;
        this.b = var2;
    }

    @Override
    public List process() {
        for(int var1 = 0; var1 < this.datas.size(); ++var1) {
            Map var2 = (Map)this.datas.get(var1);
            Object[] var3 = new Object[this.e.size()];
            boolean var4 = false;

            for(int var5 = 0; var5 < this.e.size(); ++var5) {
                var3[var5] = var2.get(this.e.get(var5));
                if (var3[var5] == null) {
                    var4 = true;
                    break;
                }
            }

            if (!var4) {
                Function var8 = (Function)this.c.getScope().get(this.d, this.c.getScope());
                Object var6 = var8.call(this.c.getCt(), this.c.getScope(), this.c.getScope(), var3);
                GridDataUtils.putComputeData(var6, var2, this.b);
            } else {
                GridDataUtils.putComputeData((Object)null, var2, this.b);
            }
        }

        ColumnInfo var7 = new ColumnInfo();
        var7.setName(this.b.toUpperCase());
        var7.setType("Number");
        this.metaData.getQueryColumns().put(this.b.toUpperCase(), var7);
        return this.datas;
    }

    @Override
    public StringBuffer createJSFunc() {
        StringBuffer var1 = new StringBuffer();
        ArrayList var2 = new ArrayList();
        String var3 = "@(\\w+)@";
        Pattern var4 = Pattern.compile(var3);
        Matcher var5 = var4.matcher(this.a);

        while(var5.find()) {
            String var6 = var5.group(1);
            if (!var2.contains(var6)) {
                var2.add(var6);
            }
        }

        int var9 = IdCreater.create();
        this.e = var2;
        this.d = "CF" + var9;
        var1.append("function " + this.d + "(");

        for(int var7 = 0; var7 < var2.size(); ++var7) {
            String var8 = (String)var2.get(var7);
            var1.append(var8);
            if (var7 != var2.size() - 1) {
                var1.append(",");
            }
        }

        var1.append("){");
        var1.append(" return  " + this.a.replaceAll("@", ""));
        var1.append("}\n");
        return var1;
    }

    public void setInvocable(PageBuilder$JSObject var1) {
        this.c = var1;
    }
}
