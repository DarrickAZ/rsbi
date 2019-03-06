//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.grid;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.view.context.dc.grid.GridDateFillContext;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GridDateFill extends GridBaseProcessor {
    private GridDateFillContext a;

    public GridDateFill(GridDateFillContext var1) {
        this.a = var1;
    }

    @Override
    public List process() throws ScriptEnginerException {
        if (this.datas.size() <= 1) {
            return this.datas;
        } else {
            String var1;
            if (!GridDataUtils.columnIsExist(this.a.getColumn(), this.metaData)) {
                var1 = ConstantsEngine.replace("dateFill 函数出错， column $0  不存在 或不是账期类型.", this.a.getColumn());
                throw new ScriptEnginerException(var1);
            } else {
                var1 = this.a.getColumn();
                String var2 = this.a.getDataColumn();
                String var3 = this.a.getDispColumn();
                ArrayList var4 = new ArrayList();
                String var5 = (String)((Map)this.datas.get(0)).get(var1);
                String var6 = (String)((Map)this.datas.get(this.datas.size() - 1)).get(var1);
                if (this.a.getSer() != null && this.a.getSer().length() > 0) {
                    String var7 = this.a.getSer();
                    List var8 = this.a(var7);
                    Iterator var10 = var8.iterator();

                    while(var10.hasNext()) {
                        String var9 = (String)var10.next();
                        List var11 = this.c(var9, var7);
                        try {
                            var4.addAll(this.a(var1, var2, var3, var11, var5, var6));
                        } catch (ScriptEnginerException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        var4.addAll(this.a(var1, var2, var3, this.datas, var5, var6));
                    } catch (ScriptEnginerException e) {
                        e.printStackTrace();
                    }
                }

                return var4;
            }
        }
    }

    private List a(String var1, String var2, String var3, List var4, String var5, String var6) throws ScriptEnginerException {
        ArrayList var7 = new ArrayList();
        List var8 = null;
        if ("month".equalsIgnoreCase(this.a.getType())) {
            var8 = this.b(var5, var6);
        } else {
            if (!"day".equalsIgnoreCase(this.a.getType())) {
                String var13 = ConstantsEngine.replace("dateFill 函数出错，type $0 只能为 month/day ", this.a.getType());
                throw new ScriptEnginerException(var13);
            }

            var8 = this.a(var5, var6);
        }

        Map var9 = (Map)var4.get(0);

        Map var12;
        for(Iterator var11 = var8.iterator(); var11.hasNext(); var7.add(var12)) {
            String var10 = (String)var11.next();
            var12 = this.a(var10, var1, var4);
            if (var12 == null) {
                var12 = GridDataUtils.createNewData(var9);
                var12.put(var1, var10);
                var12.put(var2, new BigDecimal(0));
                if (var3 != null && var3.length() > 0) {
                    if ("month".equalsIgnoreCase(this.a.getType())) {
                        var12.put(var3, var10.substring(4, 6) + "月");
                    } else {
                        var12.put(var3, var10.substring(6, 8) + "号");
                    }
                }
            }
        }

        return var7;
    }

    private Map a(String var1, String var2, List var3) {
        Map var4 = null;

        for(int var5 = 0; var5 < var3.size(); ++var5) {
            Map var6 = (Map)var3.get(var5);
            if (((String)var6.get(var2)).equals(var1)) {
                var4 = var6;
                break;
            }
        }

        return var4;
    }

    private List a(String var1, String var2) {
        int var3 = Integer.parseInt(var1.substring(0, 4));
        int var4 = Integer.parseInt(var1.substring(4, 6)) - 1;
        int var5 = Integer.parseInt(var1.substring(6, 8));
        GregorianCalendar var6 = new GregorianCalendar(var3, var4, var5);
        ArrayList var7 = new ArrayList();
        SimpleDateFormat var8 = new SimpleDateFormat("yyyyMMdd");

        while(true) {
            String var9 = var8.format(var6.getTime());
            var7.add(var9);
            if (var9.equals(var2)) {
                return var7;
            }

            var6.add(5, 1);
        }
    }

    private List b(String var1, String var2) {
        int var3 = Integer.parseInt(var1.substring(0, 4));
        int var4 = Integer.parseInt(var1.substring(4, 6)) - 1;
        GregorianCalendar var5 = new GregorianCalendar(var3, var4, 1);
        ArrayList var6 = new ArrayList();
        SimpleDateFormat var7 = new SimpleDateFormat("yyyyMM");

        while(true) {
            String var8 = var7.format(var5.getTime());
            var6.add(var8);
            if (var8.equals(var2)) {
                return var6;
            }

            var5.add(2, 1);
        }
    }

    private List c(String var1, String var2) {
        ArrayList var3 = new ArrayList();

        for(int var4 = 0; var4 < this.datas.size(); ++var4) {
            Map var5 = (Map)this.datas.get(var4);
            String var6 = (String)var5.get(var2);
            if (var6.equals(var1)) {
                var3.add(var5);
            }
        }

        return var3;
    }

    private List a(String var1) {
        ArrayList var2 = new ArrayList();

        for(int var3 = 0; var3 < this.datas.size(); ++var3) {
            Map var4 = (Map)this.datas.get(var3);
            String var5 = (String)var4.get(var1);
            if (!var2.contains(var5)) {
                var2.add(var5);
            }
        }

        return var2;
    }
}
