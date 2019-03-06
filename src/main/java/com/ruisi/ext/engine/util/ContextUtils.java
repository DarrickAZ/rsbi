//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.util;

import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ContextUtils {
    private String a;
    private String b;

    public ContextUtils(String var1) {
        if (var1 == null || var1.length() == 0) {
            var1 = "html";
        }

        this.a = var1;
    }

    public ContextUtils(String var1, String var2) {
        if (var1 == null || var1.length() == 0) {
            var1 = "html";
        }

        this.a = var1;
        this.b = var2;
    }

    public String monthAdd(String var1, int var2) {
        SimpleDateFormat var3 = new SimpleDateFormat("yyyyMM");
        int var4 = Integer.parseInt(var1.substring(0, 4));
        int var5 = Integer.parseInt(var1.substring(4, 6)) - 1;
        GregorianCalendar var6 = new GregorianCalendar(var4, var5, 1);
        var6.add(2, var2);
        String var7 = var3.format(var6.getTime());
        return var2 > 0 ? " between " + var1 + " and " + var7 : " between " + var7 + " and " + var1;
    }

    public String dayAdd(String var1, int var2) {
        SimpleDateFormat var3 = new SimpleDateFormat("yyyyMMdd");
        int var4 = Integer.parseInt(var1.substring(0, 4));
        int var5 = Integer.parseInt(var1.substring(4, 6)) - 1;
        int var6 = Integer.parseInt(var1.substring(6, 8));
        GregorianCalendar var7 = new GregorianCalendar(var4, var5, var6);
        var7.add(5, var2);
        String var8 = var3.format(var7.getTime());
        return var2 > 0 ? " between " + var1 + " and " + var8 : " between " + var8 + " and " + var1;
    }

    public int dateBetween(Date var1, Date var2) {
        Calendar var3 = Calendar.getInstance();
        var3.setTime(var1);
        long var4 = var3.getTimeInMillis();
        var3.setTime(var2);
        long var6 = var3.getTimeInMillis();
        long var8 = (var6 - var4) / 86400000L;
        return Integer.parseInt(String.valueOf(var8));
    }

    public int dateBetweenNow(Date var1) {
        Date var2 = new Date();
        return this.dateBetween(var2, var1);
    }

    public String numberFmt(Object var1, String var2) {
        if (var1 == null) {
            return "";
        } else if (var2 != null && var2.length() != 0) {
            DecimalFormat var3 = new DecimalFormat(var2);
            return var3.format(var1);
        } else {
            return var1.toString();
        }
    }

    public String dateFmt(Date var1, String var2) {
        SimpleDateFormat var3 = new SimpleDateFormat(var2);
        return var3.format(var1);
    }

    public String printJH() {
        return "##";
    }

    public String printSpace(int var1) {
        StringBuffer var2 = new StringBuffer();
        int var3;
        if ("xls".equalsIgnoreCase(this.a)) {
            for(var3 = 0; var3 < var1; ++var3) {
                var2.append(" ");
            }
        }

        if ("html".equalsIgnoreCase(this.a)) {
            for(var3 = 0; var3 < var1; ++var3) {
                var2.append("&nbsp;");
            }
        }

        return var2.toString();
    }

    public int getArraySize(String[] var1) {
        return var1 != null ? var1.length : 0;
    }

    public boolean valueExist(Object var1, Object[] var2) {
        if (var2 == null) {
            return false;
        } else {
            boolean var3 = false;
            Object[] var7 = var2;
            int var6 = var2.length;

            for(int var5 = 0; var5 < var6; ++var5) {
                Object var4 = var7[var5];
                if (var4.equals(var1)) {
                    var3 = true;
                    break;
                }
            }

            return var3;
        }
    }

    public String monthCompute(String var1, int var2) {
        SimpleDateFormat var3 = new SimpleDateFormat("yyyyMM");
        int var4 = Integer.parseInt(var1.substring(0, 4));
        int var5 = Integer.parseInt(var1.substring(4, 6)) - 1;
        GregorianCalendar var6 = new GregorianCalendar(var4, var5, 1);
        var6.add(2, var2);
        return var3.format(var6.getTime());
    }

    public String dayCompute(String var1, int var2) {
        SimpleDateFormat var3 = new SimpleDateFormat("yyyyMMdd");
        int var4 = Integer.parseInt(var1.substring(0, 4));
        int var5 = Integer.parseInt(var1.substring(4, 6)) - 1;
        int var6 = Integer.parseInt(var1.substring(6, 8));
        GregorianCalendar var7 = new GregorianCalendar(var4, var5, var6);
        var7.add(5, var2);
        return var3.format(var7.getTime());
    }

    public String getCurrDate(int var1) {
        SimpleDateFormat var2 = new SimpleDateFormat("yyyyMMdd");
        Date var3 = null;
        try {
            var3 = var2.parse("20130910");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        var3.setDate(var3.getDate() + var1);
        return var2.format(var3);
    }

    public String getCurrMonth(int var1) {
        SimpleDateFormat var2 = new SimpleDateFormat("yyyyMM");
        Date var3 = null;
        try {
            var3 = var2.parse("201308");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        var3.setMonth(var3.getMonth() + var1);
        return var2.format(var3);
    }

    public int dateCompare(String var1, String var2, String var3) {
        SimpleDateFormat var4 = new SimpleDateFormat(var3);
        Date var5 = null;
        try {
            var5 = var4.parse(var1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date var6 = null;
        try {
            var6 = var4.parse(var2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return var5.compareTo(var6);
    }

    public String concatFunc(Object var1, Object var2, Object var3, Object var4) {
        if ("mysql".equalsIgnoreCase(this.b)) {
            return "concat(" + var1 + "," + var2 + "," + var3 + "," + var4 + ")";
        } else if ("oracle".equalsIgnoreCase(this.b)) {
            return var1 + "||" + var2 + "||" + var3 + "||" + var4;
        } else {
            throw new ExtRuntimeException("您未配置 dbName ...");
        }
    }

    public String printVals(String var1, String var2) {
        if (var1 == null) {
            return "";
        } else if ("String".equalsIgnoreCase(var2)) {
            String[] var3 = var1.split(",");
            StringBuffer var4 = new StringBuffer();

            for(int var5 = 0; var5 < var3.length; ++var5) {
                var4.append("'" + var3[var5] + "'");
                if (var5 != var3.length - 1) {
                    var4.append(",");
                }
            }

            return var4.toString();
        } else {
            return var1;
        }
    }
}
