//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ispire.dc.cube.engine.ScriptRuntimeException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataSetMetaData {
    public static final String colType1 = "kpi";
    public static final String colType2 = "extKpi";
    public static final String colType3 = "dim";
    public static final String colType4 = "CKP_CODE_COLUMN";
    public static final String[] colTYPEs = new String[]{"kpi", "extKpi", "dim", "CKP_CODE_COLUMN"};
    private List a;
    private List b;
    private List c;
    private List d;
    private Boolean e;
    private Boolean f;
    public static String zbKpiCol = "CKP_CODE";
    public static String zbKpiColDesc = "CKP_NAME";
    public static String zbKpiValueCol = "CKP_VALUE";

    public DataSetMetaData() {
    }

    public String getColumnType(String var1) {
        if (this.isDim(var1)) {
            return "dim";
        } else if (this.isKpi(var1)) {
            return "kpi";
        } else if (this.isExtKpi(var1)) {
            return "extKpi";
        } else if (this.getZbKpiCol().equals(var1)) {
            return "CKP_CODE_COLUMN";
        } else {
            String var2 = ConstantsEngine.replace("字段不存在. ($0)", var1);
            throw new ScriptRuntimeException(var2);
        }
    }

    public boolean isCKP_VALUE(String var1) {
        return this.getZbKpiValueCol().equalsIgnoreCase(var1);
    }

    public boolean isDim(String var1) {
        boolean var2 = false;
        if (this.a == null) {
            return var2;
        } else {
            Iterator var4 = this.a.iterator();

            while(var4.hasNext()) {
                QueryDim var3 = (QueryDim)var4.next();
                if (var3.getDimCol().equals(var1)) {
                    var2 = true;
                    break;
                }
            }

            return var2;
        }
    }

    public boolean isHorizontalKpi(String var1) {
        boolean var2 = false;
        if (this.d == null) {
            return var2;
        } else {
            Iterator var4 = this.d.iterator();

            while(var4.hasNext()) {
                QueryKpi var3 = (QueryKpi)var4.next();
                if (var3.getKpiCol().equals(var1)) {
                    var2 = true;
                    break;
                }
            }

            return var2;
        }
    }

    public boolean isKpi(String var1) {
        boolean var2 = false;
        Iterator var4 = this.b.iterator();

        while(var4.hasNext()) {
            QueryKpi var3 = (QueryKpi)var4.next();
            if (var3.getKpiCol().equals(var1)) {
                var2 = true;
                break;
            }
        }

        return var2;
    }

    public boolean isExtKpi(String var1) {
        boolean var2 = false;
        if (this.c == null) {
            return var2;
        } else {
            Iterator var4 = this.c.iterator();

            while(var4.hasNext()) {
                QueryExtKpi var3 = (QueryExtKpi)var4.next();
                if (var3.getExtKpiCol().equals(var1)) {
                    var2 = true;
                    break;
                }
            }

            return var2;
        }
    }

    public List getQueryDims() {
        return this.a;
    }

    public void setQueryDims(List var1) {
        this.a = var1;
    }

    public List getQueryKpis() {
        return this.b;
    }

    public void setQueryKpis(List var1) {
        this.b = var1;
    }

    public List getQueryExtKpis() {
        return this.c;
    }

    public void setQueryExtKpis(List var1) {
        this.c = var1;
    }

    public String getZbKpiCol() {
        return zbKpiCol;
    }

    public String getZbKpiColDesc() {
        return zbKpiColDesc;
    }

    public String getZbKpiValueCol() {
        return zbKpiValueCol;
    }

    public void removeKpis(String[] var1) {
        ArrayList var2 = new ArrayList();
        String[] var6 = var1;
        int var5 = var1.length;

        for(int var4 = 0; var4 < var5; ++var4) {
            String var3 = var6[var4];
            QueryKpi var7 = this.getQueryKpi(var3);
            if (var7 != null) {
                var2.add(var7);
            }
        }

        this.b.removeAll(var2);
    }

    public void removeKpisHoldInput(String[] var1) {
        ArrayList var2 = new ArrayList();
        String[] var6 = var1;
        int var5 = var1.length;

        for(int var4 = 0; var4 < var5; ++var4) {
            String var3 = var6[var4];
            QueryKpi var7 = this.getQueryKpi(var3);
            if (var7 != null) {
                var2.add(var7);
            }
        }

        this.b.clear();
        this.b.addAll(var2);
    }

    public void removeDims(String[] var1) {
        String[] var5 = var1;
        int var4 = var1.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            String var2 = var5[var3];
            QueryDim var6 = this.getQueryDim(var2);
            if (var6 != null) {
                this.a.remove(var6);
            }
        }

    }

    public QueryKpi getQueryKpi(String var1) {
        QueryKpi var2 = null;
        Iterator var4 = this.b.iterator();

        while(var4.hasNext()) {
            QueryKpi var3 = (QueryKpi)var4.next();
            if (var3.getKpiCol().equals(var1)) {
                var2 = var3;
                break;
            }
        }

        return var2;
    }

    public QueryExtKpi getQueryExtKpi(String var1) {
        QueryExtKpi var2 = null;
        Iterator var4 = this.c.iterator();

        while(var4.hasNext()) {
            QueryExtKpi var3 = (QueryExtKpi)var4.next();
            if (var3.getExtKpiCol().equals(var1)) {
                var2 = var3;
                break;
            }
        }

        return var2;
    }

    public QueryDim getQueryDim(String var1) {
        QueryDim var2 = null;
        Iterator var4 = this.a.iterator();

        while(var4.hasNext()) {
            QueryDim var3 = (QueryDim)var4.next();
            if (var3.getDimCol().equals(var1)) {
                var2 = var3;
                break;
            }
        }

        return var2;
    }

    public List getHorizontalKpis() {
        return this.d;
    }

    public void setHorizontalKpis(List var1) {
        this.d = var1;
    }

    public Boolean getUseDKPI() {
        return this.e;
    }

    public void setUseDKPI(Boolean var1) {
        this.e = var1;
    }

    public Boolean getJsKpiInDKPI() {
        return this.f;
    }

    public void setJsKpiInDKPI(Boolean var1) {
        this.f = var1;
    }
}
