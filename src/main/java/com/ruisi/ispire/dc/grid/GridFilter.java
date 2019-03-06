//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.grid;

import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.view.test.TestUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GridFilter extends GridBaseProcessor {
    public static final String equal = "=";
    public static final String between = "between";
    public static final String in = "in";
    public static final String bigger = ">";
    public static final String less = "<";
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String[] f;
    private double g;
    private double h;

    public GridFilter(String var1, String var2, String var3, String var4, String var5) {
        this.a = var1;
        this.b = var2;
        this.c = var4;
        this.d = var5;
        this.e = var3;
    }

    public void initValue() {
        this.c = TestUtils.findValue(this.c, this.builder.getRequest(), this.builder.getVeloContext());
        this.d = TestUtils.findValue(this.d, this.builder.getRequest(), this.builder.getVeloContext());
        if ("in".equals(this.b)) {
            this.f = this.c.split(",");
        }

        if ("between".equals(this.b)) {
            if (this.e != null && this.e.length() > 0) {
                this.g = (double)this.convert(this.c, this.e);
                this.h = (double)this.convert(this.d, this.e);
            } else {
                this.g = Double.parseDouble(this.c);
                this.h = Double.parseDouble(this.d);
            }
        }

        if (">".equals(this.b) || "<".equals(this.b)) {
            this.g = Double.parseDouble(this.c);
        }

    }

    public List process() {
        this.initValue();
        ArrayList var1 = new ArrayList();

        for(int var2 = 0; var2 < this.datas.size(); ++var2) {
            Map var3 = (Map)this.datas.get(var2);
            if (this.chk(var3)) {
                var1.add(var3);
            }
        }

        return var1;
    }

    public int convert(String var1, String var2) {
        if ("yyyyMMdd".equalsIgnoreCase(var2)) {
            return Integer.parseInt(var1);
        } else if ("yyyy-MM-dd".equalsIgnoreCase(var2)) {
            return Integer.parseInt(var1.replaceAll("-", ""));
        } else if ("yyyy年MM月dd日".equalsIgnoreCase(var2)) {
            return Integer.parseInt(var1.substring(0, 4) + var1.substring(5, 7) + var1.substring(8, 10));
        } else if ("yyyyMM".equalsIgnoreCase(var2)) {
            return Integer.parseInt(var1);
        } else if ("yyyy-MM".equalsIgnoreCase(var2)) {
            return Integer.parseInt(var1.replaceAll("-", ""));
        } else if ("yyyy年MM月".equalsIgnoreCase(var2)) {
            return Integer.parseInt(var1.substring(0, 4) + var1.substring(5, 7));
        } else if ("yyyyMM".equalsIgnoreCase(var2)) {
            return Integer.parseInt(var1);
        } else if ("yyyy-MM".equalsIgnoreCase(var2)) {
            return Integer.parseInt(var1.replaceAll("-", ""));
        } else if ("yyyy年MM季度".equalsIgnoreCase(var2)) {
            return Integer.parseInt(var1.substring(0, 4) + var1.substring(5, 7));
        } else if ("yyyy".equalsIgnoreCase(var2)) {
            return Integer.parseInt(var1);
        } else if ("yyyy年".equalsIgnoreCase(var2)) {
            return Integer.parseInt(var1.substring(0, 4));
        } else {
            throw new RuntimeException("dateformat = " + var2 + "类型不支持。");
        }
    }

    public boolean chk(Map var1) {
        boolean var2 = false;
        Object var3 = var1.get(this.a);
        String var4 = var3 == null ? "" : var3.toString();
        if ("=".equals(this.b)) {
            if (this.c.equals(var4)) {
                var2 = true;
            }
        } else {
            double var5;
            if ("between".equals(this.b)) {
                if (this.e != null && this.e.length() > 0) {
                    var5 = (double)this.convert(var4, this.e);
                } else {
                    var5 = Double.parseDouble(var4);
                }

                if (var5 >= this.g && var5 <= this.h) {
                    var2 = true;
                }
            } else {
                if ("in".equals(this.b)) {
                    return this.a(var4);
                }

                if (">".equals(this.b)) {
                    var5 = Double.parseDouble(var4);
                    if (var5 > this.g) {
                        var2 = true;
                    }
                } else {
                    if (!"<".equals(this.b)) {
                        throw new ExtRuntimeException("过滤类型不支持...");
                    }

                    var5 = Double.parseDouble(var4);
                    if (var5 < this.g) {
                        var2 = true;
                    }
                }
            }
        }

        return var2;
    }

    private boolean a(String var1) {
        boolean var2 = false;
        String[] var6;
        int var5 = (var6 = this.f).length;

        for(int var4 = 0; var4 < var5; ++var4) {
            String var3 = var6[var4];
            if (var3.equals(var1)) {
                var2 = true;
                break;
            }

            if (var3.equals("NULLVAL") && (var1 == null || var1.length() == 0)) {
                var2 = true;
                break;
            }
        }

        return var2;
    }
}
