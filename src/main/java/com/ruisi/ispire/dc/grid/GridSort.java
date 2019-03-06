//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.grid;

import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class GridSort extends GridBaseProcessor implements Comparator {
    private String[] a;
    private String[] b;
    private String[] c;
    private boolean d;
    private boolean e;

    public GridSort(String[] var1, String[] var2, boolean var3, boolean var4) {
        this.a = var1;
        this.b = var2;
        this.d = var3;
        this.e = var4;
        this.c = new String[var1.length];
    }

    @Override
    public List process() {
        for(int var1 = 0; var1 < this.a.length; ++var1) {
            this.c[var1] = ((ColumnInfo)this.metaData.getQueryColumns().get(this.a[var1].toUpperCase())).getType();
        }

        Object var9 = this.datas;
        int var2;
        if (!this.e) {
            var9 = new ArrayList();

            for(var2 = 0; var2 < this.datas.size(); ++var2) {
                ((List)var9).add((Map)this.datas.get(var2));
            }
        }

        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        Collections.sort((List)var9, this);
        if (this.d) {
            var2 = 0;
            String var3 = null;
            if (this.a.length - 1 > 0) {
                var3 = this.a[this.a.length - 2];
            }

            Object var4 = var3 != null && ((List)var9).size() != 0 ? ((Map)((List)var9).get(0)).get(var3) : null;
            String var5 = this.a[this.a.length - 1];

            for(int var6 = 0; var6 < ((List)var9).size(); ++var6) {
                ++var2;
                Map var7 = (Map)((List)var9).get(var6);
                if (var3 != null) {
                    Object var8 = var7.get(var3);
                    if (!var8.equals(var4)) {
                        var2 = 1;
                        var4 = var8;
                    }
                }

                var7.put(var5 + "_order", var2);
            }
        }

        return (List)(this.e ? var9 : this.datas);
    }


    public int compare(Map var1, Map var2) {
        Map var3 = var1;
        Map var4 = var2;
        int var5 = 0;

        for(int var6 = 0; var6 < this.a.length; ++var6) {
            if (this.c[var6].equals("Number")) {
                Object var7 = var3.get(this.a[var6]);
                Object var8 = var4.get(this.a[var6]);
                if (var7 == null) {
                    var5 = -1;
                } else if (var8 == null) {
                    var5 = 1;
                } else if (var7 instanceof BigDecimal) {
                    var5 = ((BigDecimal)var7).compareTo((BigDecimal)var8);
                } else if (var7 instanceof Integer) {
                    var5 = ((Integer)var7).compareTo((Integer)var8);
                } else if (var7 instanceof Double) {
                    var5 = ((Double)var7).compareTo((Double)var8);
                } else {
                    if (!(var7 instanceof Long)) {
                        throw new ExtRuntimeException("类型不支持。");
                    }

                    var5 = ((Long)var7).compareTo((Long)var8);
                }

                if ("desc".equalsIgnoreCase(this.b[var6])) {
                    var5 = -var5;
                }

                if (var5 != 0) {
                    break;
                }
            } else if (this.c[var6].equals("String")) {
                String var9 = (String)var3.get(this.a[var6]);
                String var10 = (String)var4.get(this.a[var6]);
                if (var9 == null) {
                    return -1;
                }

                if (var10 == null) {
                    return 1;
                }

                var5 = var9.compareTo(var10);
                if ("desc".equalsIgnoreCase(this.b[var6])) {
                    var5 = -var5;
                }

                if (var5 != 0) {
                    break;
                }
            } else {
                this.c[var6].equals("Date");
            }
        }

        return var5;
    }

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }
}
