//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.grid;

import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import java.util.List;
import java.util.Map;

public class GridJoin extends GridBaseProcessor {
    private String a;
    private String b;
    private String c;
    private String[] d;
    private String[] e;

    public GridJoin(String var1, String var2, String var3, String[] var4, String[] var5) {
        this.a = var1;
        this.b = var2;
        this.c = var3;
        this.d = var4;
        this.e = var5;
    }

    public List process() {
        List var1 = this.a();

        for(int var2 = 0; var2 < this.datas.size(); ++var2) {
            Map var3 = (Map)this.datas.get(var2);
            Object var4 = var3.get(this.b);
            if (var4 != null) {
                Map var5 = this.a(var1, var4);
                if (var5 != null && this.d != null && this.d.length > 0) {
                    for(int var6 = 0; var6 < this.d.length; ++var6) {
                        String var7 = this.d[var6];
                        var3.put(this.e[var6], var5.get(var7));
                    }
                }
            }
        }

        return this.datas;
    }

    private Map a(List var1, Object var2) {
        Map var3 = null;

        for(int var4 = 0; var4 < var1.size(); ++var4) {
            Map var5 = (Map)var1.get(var4);
            Object var6 = var5.get(this.c);
            if (var6 != null && var6.equals(var2)) {
                var3 = var5;
                break;
            }
        }

        return var3;
    }

    private List a() {
        List var1 = (List)this.builder.getVeloContext().get(this.a);
        if (var1 == null) {
            var1 = (List)this.builder.getRequest().getAttribute(this.a);
        }

        if (var1 == null) {
            throw new ExtRuntimeException("dataKey = " + this.a + " 未获取到数据。");
        } else {
            return var1;
        }
    }

    public String getDataKey() {
        return this.a;
    }

    public String getMasterCol() {
        return this.b;
    }

    public String getSlaveCol() {
        return this.c;
    }

    public void setDataKey(String var1) {
        this.a = var1;
    }

    public void setMasterCol(String var1) {
        this.b = var1;
    }

    public void setSlaveCol(String var1) {
        this.c = var1;
    }

    public String[] getAppendCol() {
        return this.d;
    }

    public String[] getAppendColAlias() {
        return this.e;
    }

    public void setAppendCol(String[] var1) {
        this.d = var1;
    }

    public void setAppendColAlias(String[] var1) {
        this.e = var1;
    }
}
