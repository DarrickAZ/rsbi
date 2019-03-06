//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube.operation;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ispire.dc.cube.QueryExtKpi;
import com.ruisi.ispire.dc.cube.QueryKpi;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataSetSort extends BaseProcessor implements Processor {
    public static final String sortColumn = "SORT_NUMBER";
    private boolean a;
    private String b;
    private String c;
    private String d;

    public DataSetSort(String var1, String var2, String var3, boolean var4) {
        this.b = var2;
        this.c = var3;
        this.d = var1;
        this.a = var4;
    }

    @Override
    public void process() throws ScriptEnginerException {
        String var1;
        if (this.c != null && this.c.length() != 0) {
            if (!this.c.equalsIgnoreCase("asc") && !this.c.equalsIgnoreCase("desc")) {
                var1 = ConstantsEngine.replace("_sort 函数类型错误.($0)", this.c);
                throw new ScriptEnginerException(var1);
            } else {
                if (this.d != null && this.d.length() != 0) {
                    this.ds = this.ds.getDataCenter().getDataSetById(this.d);
                    this.dataOper = new DataOperUtils(this.ds.getDatas(), this.ds.getDataSetMetaData());
                    this.dsMetaData = this.ds.getDataSetMetaData();
                }

                var1 = this.dsMetaData.getColumnType(this.b);
                DataSetSortImpl var2 = new DataSetSortImpl(this.b, var1, this.c, this.dsMetaData);
                if (var1.equals("kpi") && this.dsMetaData.getQueryKpis().size() > 1) {
                    ArrayList var3 = new ArrayList();

                    List var6;
                    for(Iterator var5 = this.dsMetaData.getQueryKpis().iterator(); var5.hasNext(); var3.addAll(var6)) {
                        QueryKpi var4 = (QueryKpi)var5.next();
                        var6 = this.dataOper.queryDataByKpiCode(var4.getKpiCol());
                        if (var4.getKpiCol().equals(this.b)) {
                            Collections.sort(var6, var2);
                            this.a(var6);
                        }
                    }

                    this.ds.setDatas(var3);
                } else {
                    Collections.sort(this.ds.getDatas(), var2);
                    this.a(this.ds.getDatas());
                }

                if (this.a) {
                    QueryExtKpi var7 = new QueryExtKpi();
                    var7.setExtKpiCol("SORT_NUMBER");
                    if (this.dsMetaData.getQueryExtKpis() == null) {
                        this.dsMetaData.setQueryExtKpis(new ArrayList());
                    }

                    this.dsMetaData.getQueryExtKpis().add(var7);
                }

            }
        } else {
            var1 = ConstantsEngine.replace("_sort 函数类型错误.($0)", this.c);
            throw new ScriptEnginerException(var1);
        }
    }

    private void a(List var1) {
        if (this.a) {
            for(int var2 = 0; var2 < var1.size(); ++var2) {
                Map var3 = (Map)var1.get(var2);
                var3.put("SORT_NUMBER", new BigDecimal(var2 + 1));
            }
        }

    }
}
