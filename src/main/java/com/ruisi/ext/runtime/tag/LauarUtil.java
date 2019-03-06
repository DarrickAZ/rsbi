//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.runtime.tag;

import java.util.Calendar;
import java.util.Date;

public class LauarUtil {
    private static String[] b = new String[]{"初", "十", "廿", "卅", " "};
    private static String[] c = new String[]{"", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
    static final long[] a = new long[]{19416L, 19168L, 42352L, 21717L, 53856L, 55632L, 91476L, 22176L, 39632L, 21970L, 19168L, 42422L, 42192L, 53840L, 119381L, 46400L, 54944L, 44450L, 38320L, 84343L, 18800L, 42160L, 46261L, 27216L, 27968L, 109396L, 11104L, 38256L, 21234L, 18800L, 25958L, 54432L, 59984L, 28309L, 23248L, 11104L, 100067L, 37600L, 116951L, 51536L, 54432L, 120998L, 46416L, 22176L, 107956L, 9680L, 37584L, 53938L, 43344L, 46423L, 27808L, 46416L, 86869L, 19872L, 42448L, 83315L, 21200L, 43432L, 59728L, 27296L, 44710L, 43856L, 19296L, 43748L, 42352L, 21088L, 62051L, 55632L, 23383L, 22176L, 38608L, 19925L, 19152L, 42192L, 54484L, 53840L, 54616L, 46400L, 46496L, 103846L, 38320L, 18864L, 43380L, 42160L, 45690L, 27216L, 27968L, 44870L, 43872L, 38256L, 19189L, 18800L, 25776L, 29859L, 59984L, 27480L, 21952L, 43872L, 38613L, 37600L, 51552L, 55636L, 54432L, 55888L, 30034L, 22176L, 43959L, 9680L, 37584L, 51893L, 43344L, 46240L, 47780L, 44368L, 21977L, 19360L, 42416L, 86390L, 21168L, 43312L, 31060L, 27296L, 44368L, 23378L, 19296L, 42726L, 42208L, 53856L, 60005L, 54576L, 23200L, 30371L, 38608L, 19415L, 19152L, 42192L, 118966L, 53840L, 54560L, 56645L, 46496L, 22224L, 21938L, 18864L, 42359L, 42160L, 43600L, 111189L, 27936L, 44448L};

    public LauarUtil() {
    }

    public static final int lYearDays(int var0) {
        int var2 = 348;

        for(int var1 = 32768; var1 > 8; var1 >>= 1) {
            if ((a[var0 - 1900] & (long)var1) != 0L) {
                ++var2;
            }
        }

        return var2 + leapDays(var0);
    }

    public static final int leapDays(int var0) {
        if (leapMonth(var0) != 0) {
            return (a[var0 - 1900] & 65536L) != 0L ? 30 : 29;
        } else {
            return 0;
        }
    }

    public static final int leapMonth(int var0) {
        return (int)(a[var0 - 1900] & 15L);
    }

    public static final int monthDays(int var0, int var1) {
        return (a[var0 - 1900] & (long)(65536 >> var1)) == 0L ? 29 : 30;
    }

    public static final String AnimalsYear(int var0) {
        String[] var1 = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
        return var1[(var0 - 4) % 12];
    }

    public static final String cyclicalm(int var0) {
        String[] var1 = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
        String[] var2 = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
        return var1[var0 % 10] + var2[var0 % 12];
    }

    public static final String cyclical(int var0) {
        int var1 = var0 - 1900 + 36;
        return cyclicalm(var1);
    }

    public final long[] Lunar(int var1, int var2) {
        int[] var3 = new int[]{1, 4, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1};
        int[] var4 = new int[]{0, 3, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0};
        int[] var5 = new int[]{0, 3, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1};
        long[] var6 = new long[7];
        boolean var7 = false;
        int var8 = 0;
        boolean var9 = false;
        Date var10 = new Date(1900, 1, 31);
        Date var11 = new Date(var1, var2, 1);
        long var12 = (var11.getTime() - var10.getTime()) / 86400000L;
        if (var1 < 2000) {
            var12 += (long)var4[var2 - 1];
        }

        if (var1 > 2000) {
            var12 += (long)var3[var2 - 1];
        }

        if (var1 == 2000) {
            var12 += (long)var5[var2 - 1];
        }

        var6[5] = var12 + 40L;
        var6[4] = 14L;

        int var14;
        for(var14 = 1900; var14 < 2050 && var12 > 0L; ++var14) {
            var8 = lYearDays(var14);
            var12 -= (long)var8;
            var6[4] += 12L;
        }

        if (var12 < 0L) {
            var12 += (long)var8;
            --var14;
            var6[4] -= 12L;
        }

        var6[0] = (long)var14;
        var6[3] = (long)(var14 - 1864);
        int var15 = leapMonth(var14);
        var6[6] = 0L;

        int var10002;
        for(var14 = 1; var14 < 13 && var12 > 0L; ++var14) {
            if (var15 > 0 && var14 == var15 + 1 && var6[6] == 0L) {
                --var14;
                var6[6] = 1L;
                var8 = leapDays((int)var6[0]);
            } else {
                var8 = monthDays((int)var6[0], var14);
            }

            if (var6[6] == 1L && var14 == var15 + 1) {
                var6[6] = 0L;
            }

            var12 -= (long)var8;
            if (var6[6] == 0L) {
                var10002 = (int) var6[4]++;
            }
        }

        if (var12 == 0L && var15 > 0 && var14 == var15 + 1) {
            if (var6[6] == 1L) {
                var6[6] = 0L;
            } else {
                var6[6] = 1L;
                --var14;
                var10002 = (int) var6[4]--;
            }
        }

        if (var12 < 0L) {
            var12 += (long)var8;
            --var14;
            var10002 = (int) var6[4]--;
        }

        var6[1] = (long)var14;
        var6[2] = var12 + 1L;
        return var6;
    }

    public static final long[] calElement(int var0, int var1, int var2) {
        long[] var3 = new long[7];
        boolean var4 = false;
        int var5 = 0;
        boolean var6 = false;
        Date var7 = new Date(0, 0, 31);
        Date var8 = new Date(var0 - 1900, var1 - 1, var2);
        long var9 = (var8.getTime() - var7.getTime()) / 86400000L;
        var3[5] = var9 + 40L;
        var3[4] = 14L;

        int var11;
        for(var11 = 1900; var11 < 2050 && var9 > 0L; ++var11) {
            var5 = lYearDays(var11);
            var9 -= (long)var5;
            var3[4] += 12L;
        }

        if (var9 < 0L) {
            var9 += (long)var5;
            --var11;
            var3[4] -= 12L;
        }

        var3[0] = (long)var11;
        var3[3] = (long)(var11 - 1864);
        int var12 = leapMonth(var11);
        var3[6] = 0L;

        int var10002;
        for(var11 = 1; var11 < 13 && var9 > 0L; ++var11) {
            if (var12 > 0 && var11 == var12 + 1 && var3[6] == 0L) {
                --var11;
                var3[6] = 1L;
                var5 = leapDays((int)var3[0]);
            } else {
                var5 = monthDays((int)var3[0], var11);
            }

            if (var3[6] == 1L && var11 == var12 + 1) {
                var3[6] = 0L;
            }

            var9 -= (long)var5;
            if (var3[6] == 0L) {
                var10002 = (int) var3[4]++;
            }
        }

        if (var9 == 0L && var12 > 0 && var11 == var12 + 1) {
            if (var3[6] == 1L) {
                var3[6] = 0L;
            } else {
                var3[6] = 1L;
                --var11;
                var10002 = (int) var3[4]--;
            }
        }

        if (var9 < 0L) {
            var9 += (long)var5;
            --var11;
            var10002 = (int) var3[4]--;
        }

        var3[1] = (long)var11;
        var3[2] = var9 + 1L;
        return var3;
    }

    public static String getchina(int var0) {
        if (var0 == 10) {
            return "初十";
        } else if (var0 == 20) {
            return "二十";
        } else if (var0 == 30) {
            return "三十";
        } else {
            String var1 = b[var0 / 10];
            var1 = var1 + c[var0 % 10];
            return var1;
        }
    }

    public static String getLauar2(Calendar var0) {
        int var1 = var0.get(1);
        int var2 = var0.get(2) + 1;
        int var3 = var0.get(5);
        long[] var4 = calElement(var1, var2, var3);
        String var5 = String.valueOf(var4[1] < 10L ? "0" + var4[1] : var4[1]);
        return var5 + (var4[2] < 10L ? "0" + var4[2] : var4[2]);
    }

    public static String getLauar(Calendar var0) {
        int var1 = var0.get(1);
        int var2 = var0.get(2) + 1;
        int var3 = var0.get(5);
        long[] var4 = calElement(var1, var2, var3);
        String var5 = c[(int)var4[1]];
        return "　农历" + var5 + "-" + getchina((int)var4[2]);
    }

    public static void main(String[] var0) {
        Calendar var1 = Calendar.getInstance();
        var1.add(5, -10);
        System.out.println(getLauar2(var1));
    }
}
