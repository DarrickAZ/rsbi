//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube.operation;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import java.util.ArrayList;
import java.util.Map;

public class DataSetKpiFilter extends BaseProcessor implements Processor {
    private String a;
    private String b;

    public DataSetKpiFilter(String var1, String var2) {
        this.a = var1;
        this.b = var2;
    }

    private void a() throws ScriptEnginerException {
        if (this.a != null && this.a.length() != 0) {
            String[] var1 = this.a.split(",");
            String[] var5 = var1;
            int var4 = var1.length;

            int var3;
            for(var3 = 0; var3 < var4; ++var3) {
                String var2 = var5[var3];
                String var6 = this.dsMetaData.getColumnType(var2);
                if (!var6.equals("kpi")) {
                    String var7 = ConstantsEngine.replace("_filterKpi 过滤字段错误，($0)", this.a);
                    throw new ScriptEnginerException(var7);
                }
            }

            ArrayList var8 = new ArrayList();
            var3 = this.ds.getDatas().size();

            for(var4 = 0; var4 < var3; ++var4) {
                Map var9 = (Map)this.ds.getDatas().get(var4);
                if (this.a(var9, var1)) {
                    var8.add(var9);
                }
            }

            this.ds.setDatas(var8);
            this.dsMetaData.removeKpisHoldInput(var1);
        }
    }

    private void b() throws ScriptEnginerException {
        if (this.b != null && this.b.length() != 0) {
            String[] var1 = this.b.split(",");
            String[] var5 = var1;
            int var4 = var1.length;

            int var3;
            for(var3 = 0; var3 < var4; ++var3) {
                String var2 = var5[var3];
                String var6 = this.dsMetaData.getColumnType(var2);
                if (!var6.equals("kpi")) {
                    String var7 = ConstantsEngine.replace("_filterKpi 过滤字段错误，($0)", this.b);
                    throw new ScriptEnginerException(var7);
                }
            }

            ArrayList var8 = new ArrayList();
            var3 = this.ds.getDatas().size();

            for(var4 = 0; var4 < var3; ++var4) {
                Map var9 = (Map)this.ds.getDatas().get(var4);
                if (this.a(var9, var1)) {
                    var8.add(var9);
                }
            }

            this.ds.getDatas().removeAll(var8);
            this.dsMetaData.removeKpis(var1);
        }
    }

    @Override
    public void process() {
        try {
            this.a();
        } catch (ScriptEnginerException e) {
            e.printStackTrace();
        }
        try {
            this.b();
        } catch (ScriptEnginerException e) {
            e.printStackTrace();
        }
    }

    private boolean a(Map var1, String[] var2) {
        boolean var3 = false;
        String var4 = (String)var1.get(this.dsMetaData.getZbKpiCol());

        for(int var5 = 0; var5 < var2.length; ++var5) {
            if (var4.equals(var2[var5])) {
                var3 = true;
                break;
            }
        }

        return var3;
    }
}
