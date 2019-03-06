//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.grid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GridShift extends GridBaseProcessor {
    private String a;
    private String b;
    private String c;
    private String[] d;
    private String e;
    private String f;
    private String[] g;
    private static SimpleDateFormat h = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat i = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat j = new SimpleDateFormat("yyyy年MM月dd日");

    public GridShift(String var1, String var2, String var3, String[] var4, String var5, String[] var6) {
        this.a = var1;
        this.b = var2;
        this.d = var4;
        this.f = var5;
        this.c = var3;
        if (!this.f.equals("sq") && !this.f.equals("hb") && !"zje".equals(this.f)) {
            this.e = "tq";
        } else {
            this.e = "sq";
        }

        this.g = var6;
    }

    @Override
    public List process() throws ParseException {
        HashMap var1 = new HashMap();

        for(int var2 = 0; var2 < this.datas.size(); ++var2) {
            Map var3 = (Map)this.datas.get(var2);
            String var4 = (String)var3.get(this.a);
            String var5;
            if (var1.get(var4) == null) {
                var5 = getDateShiftValue(var4, this.b, this.c, this.e);
                var1.put(var4, var5);
            } else {
                var5 = (String)var1.get(var4);
            }

            if (var5 == null) {
                this.compute(var3, (Map)null);
            } else {
                Map var6 = this.createKeys(var5, var3);
                Map var7 = this.queryShiftData(var6);
                this.compute(var3, var7);
            }
        }

        return this.datas;
    }

    public void compute(Map var1, Map var2) {
        if (this.d != null) {
            String[] var6;
            int var5 = (var6 = this.d).length;

            for(int var4 = 0; var4 < var5; ++var4) {
                String var3 = var6[var4];
                Double var7 = GridDataUtils.getKpiData(var2, var3);
                if (var7 != null) {
                    Double var8;
                    if ("hb".equals(this.f)) {
                        var8 = GridDataUtils.getKpiData(var1, var3);
                        var7 = (var8 - var7) / var7;
                    } else if ("tb".equals(this.f)) {
                        var8 = GridDataUtils.getKpiData(var1, var3);
                        var7 = (var8 - var7) / var7;
                    } else if ("zje".equals(this.f)) {
                        var8 = GridDataUtils.getKpiData(var1, var3);
                        var7 = var8 - var7;
                    }

                    var1.put(var3 + "_" + this.f, var7);
                } else {
                    var1.put(var3 + "_" + this.f, (Object)null);
                }
            }

        }
    }

    public Map createKeys(String var1, Map var2) {
        HashMap var3 = new HashMap();
        var3.put(this.a, var1);

        for(int var4 = 0; this.g != null && var4 < this.g.length; ++var4) {
            String var5 = this.g[var4];
            var3.put(var5, var2.get(var5));
        }

        return var3;
    }

    public Map queryShiftData(Map var1) {
        Map var2 = null;

        for(int var3 = 0; var3 < this.datas.size(); ++var3) {
            Map var4 = (Map)this.datas.get(var3);
            if (this.a(var4, var1)) {
                var2 = var4;
                break;
            }
        }

        return var2;
    }

    private boolean a(Map var1, Map var2) {
        boolean var3 = true;
        Iterator var5 = var2.entrySet().iterator();

        while(var5.hasNext()) {
            Entry var4 = (Entry)var5.next();
            if (var4.getValue() != null && !var4.getValue().equals(var1.get(var4.getKey()))) {
                var3 = false;
                break;
            }
        }

        return var3;
    }

    public static String getDateShiftValue(String var0, String var1, String var2, String var3) throws ParseException {
        if (var0 != null && var0.length() != 0) {
            if (var0.equals("NULLVAL")) {
                return var0;
            } else {
                String var4;
                if (var1.equals("year")) {
                    var0 = var0.substring(0, 4);
                    var4 = String.valueOf(Integer.parseInt(var0) - 1);
                    if ("yyyy年".equalsIgnoreCase(var2)) {
                        var4 = var4 + "年";
                    }

                    return var4;
                } else {
                    String var5;
                    SimpleDateFormat var8;
                    Calendar var9;
                    if (var1.equals("quarter")) {
                        if ("tq".equals(var3)) {
                            if ("yyyyMM".equalsIgnoreCase(var2)) {
                                var4 = var0.substring(0, 4);
                                var5 = var0.substring(4, 6);
                                return String.valueOf(Integer.parseInt(var4) - 1) + var5;
                            } else {
                                var4 = var0.substring(0, 4);
                                var5 = var0.substring(5, 7);
                                return "yyyy-MM".equalsIgnoreCase(var2) ? String.valueOf(Integer.parseInt(var4) - 1) + "-" + var5 : String.valueOf(Integer.parseInt(var4) - 1) + "年" + var5 + "季度";
                            }
                        } else {
                            var8 = new SimpleDateFormat(var2);
                            var9 = Calendar.getInstance();
                            var9.setTime(var8.parse(var0));
                            var9.add(2, -3);
                            return var8.format(var9.getTime());
                        }
                    } else {
                        int var10;
                        if (var1.equals("month")) {
                            if ("tq".equalsIgnoreCase(var3)) {
                                if ("yyyyMM".equalsIgnoreCase(var2)) {
                                    var4 = var0.substring(0, 4);
                                    var5 = var0.substring(4, 6);
                                    return String.valueOf(Integer.parseInt(var4) - 1) + var5;
                                } else {
                                    var4 = var0.substring(0, 4);
                                    var5 = var0.substring(5, 7);
                                    return "yyyy-MM".equalsIgnoreCase(var2) ? String.valueOf(Integer.parseInt(var4) - 1) + "-" + var5 : String.valueOf(Integer.parseInt(var4) - 1) + "年" + var5 + "月";
                                }
                            } else if ("yyyyMM".equalsIgnoreCase(var2)) {
                                var4 = var0.substring(0, 4);
                                var5 = var0.substring(4, 6);
                                var10 = Integer.parseInt(var5) - 1;
                                if (var10 == 0) {
                                    var5 = "12";
                                    return String.valueOf(Integer.parseInt(var4) - 1) + var5;
                                } else {
                                    return var4 + (var10 < 10 ? "0" : "") + var10;
                                }
                            } else {
                                var4 = var0.substring(0, 4);
                                var5 = var0.substring(5, 7);
                                var10 = Integer.parseInt(var5) - 1;
                                if (var10 == 0) {
                                    var5 = "12";
                                    return "yyyy-MM".equalsIgnoreCase(var2) ? String.valueOf(Integer.parseInt(var4) - 1) + "-" + var5 : String.valueOf(Integer.parseInt(var4) - 1) + "年" + var5 + "月";
                                } else {
                                    return "yyyy-MM".equalsIgnoreCase(var2) ? var4 + "-" + (var10 < 10 ? "0" : "") + var10 : var4 + "年" + (var10 < 10 ? "0" : "") + var10 + "月";
                                }
                            }
                        } else if (var1.equals("week")) {
                            var8 = new SimpleDateFormat(var2);
                            var9 = Calendar.getInstance();
                            var9.setTime(var8.parse(var0));
                            if ("tq".equals(var3)) {
                                var10 = var9.get(8);
                                var9.add(2, -1);
                                var9.set(8, var10);
                            } else {
                                var9.add(5, -7);
                            }

                            return var8.format(var9.getTime());
                        } else if (var1.equals("day")) {
                            String var6;
                            if ("yyyyMMdd".equalsIgnoreCase(var2)) {
                                var4 = var0.substring(0, 4);
                                var5 = var0.substring(4, 6);
                                var6 = var0.substring(6, 8);
                            } else {
                                var4 = var0.substring(0, 4);
                                var5 = var0.substring(5, 7);
                                var6 = var0.substring(8, 10);
                            }

                            GregorianCalendar var7;
                            if ("tq".equals(var3)) {
                                var7 = new GregorianCalendar(Integer.parseInt(var4), Integer.parseInt(var5) - 1, Integer.parseInt(var6));
                                var7.add(2, -1);
                                if ("yyyyMMdd".equalsIgnoreCase(var2)) {
                                    return h.format(var7.getTime());
                                } else {
                                    return "yyyy-MM-dd".equalsIgnoreCase(var2) ? i.format(var7.getTime()) : j.format(var7.getTime());
                                }
                            } else {
                                var7 = new GregorianCalendar(Integer.parseInt(var4), Integer.parseInt(var5) - 1, Integer.parseInt(var6));
                                var7.add(5, -1);
                                if ("yyyyMMdd".equalsIgnoreCase(var2)) {
                                    return h.format(var7.getTime());
                                } else {
                                    return "yyyy-MM-dd".equalsIgnoreCase(var2) ? i.format(var7.getTime()) : j.format(var7.getTime());
                                }
                            }
                        } else {
                            return null;
                        }
                    }
                }
            }
        } else {
            return null;
        }
    }
}
