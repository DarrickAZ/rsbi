//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.cross;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class CrossUtils {
    public CrossUtils() {
    }

    public static List getDayBySize(String var0, int var1, Boolean var2) {
        Object var3 = null;
        if (var0 != null && var0.length() != 0) {
            int var4 = Integer.parseInt(var0.substring(0, 4));
            int var5 = Integer.parseInt(var0.substring(4, 6)) - 1;
            int var6 = Integer.parseInt(var0.substring(6, 8));
            var3 = new GregorianCalendar(var4, var5, var6);
        } else {
            var3 = Calendar.getInstance();
            ((Calendar)var3).add(5, -1);
        }

        SimpleDateFormat var10 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat var11 = new SimpleDateFormat("M月d日");
        ArrayList var12 = new ArrayList();

        for(int var7 = 0; var7 < var1; ++var7) {
            String var8 = "";
            var8 = var11.format(((Calendar)var3).getTime());
            if (var2 != null && var2) {
                int var9 = ((Calendar)var3).get(7) - 1;
                var8 = var8 + "<span class='tweek'>(周" + (var9 == 0 ? "日" : String.valueOf(var9)) + ")</span>";
            }

            var12.add(new CrossUtils$DayOrMonth(var10.format(((Calendar)var3).getTime()), var8));
            ((Calendar)var3).add(5, -1);
        }

        return var12;
    }

    public static List getMonthBySize(String var0, int var1) {
        Object var2 = null;
        if (var0 != null && var0.length() != 0) {
            int var3 = Integer.parseInt(var0.substring(0, 4));
            int var4 = Integer.parseInt(var0.substring(4, 6)) - 1;
            var2 = new GregorianCalendar(var3, var4, 1);
        } else {
            var2 = Calendar.getInstance();
            ((Calendar)var2).add(2, -1);
        }

        SimpleDateFormat var9 = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat var10 = new SimpleDateFormat("yyyy年M月");
        SimpleDateFormat var5 = new SimpleDateFormat("M月");
        ArrayList var6 = new ArrayList();

        for(int var7 = 0; var7 < var1; ++var7) {
            String var8 = "";
            if (var7 != 0 && ((Calendar)var2).get(2) != ((Calendar)var2).getActualMaximum(2)) {
                var8 = var5.format(((Calendar)var2).getTime());
            } else {
                var8 = var10.format(((Calendar)var2).getTime());
            }

            var6.add(new CrossUtils$DayOrMonth(var9.format(((Calendar)var2).getTime()), var8));
            ((Calendar)var2).add(2, -1);
        }

        return var6;
    }
}
