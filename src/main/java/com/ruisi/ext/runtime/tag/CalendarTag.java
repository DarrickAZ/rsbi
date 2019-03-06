//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.runtime.tag;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class CalendarTag extends TagSupport {
    private SimpleDateFormat a = new SimpleDateFormat("yyyyMMdd");
    private String b;
    private String c;
    private static List d = new ArrayList();
    private static List e;
    public static String[] dateFormat;
    private static List f;

    static {
        d.add(new String[]{"0101", "元旦节"});
        d.add(new String[]{"0214", "情人节"});
        d.add(new String[]{"0305", "学雷锋"});
        d.add(new String[]{"0308", "妇女节"});
        d.add(new String[]{"0312", "植树节"});
        d.add(new String[]{"0315", "消费者日"});
        d.add(new String[]{"0401", "愚人节"});
        d.add(new String[]{"0501", "劳动节"});
        d.add(new String[]{"0504", "青年节"});
        d.add(new String[]{"0601", "儿童节"});
        d.add(new String[]{"0701", "建党节"});
        d.add(new String[]{"0801", "建军节"});
        d.add(new String[]{"0910", "教师节"});
        d.add(new String[]{"1001", "国庆节"});
        d.add(new String[]{"1111", "光棍节"});
        d.add(new String[]{"1224", "平安夜"});
        d.add(new String[]{"1225", "圣诞节"});
        e = new ArrayList();
        e.add(new String[]{"0101", "春节"});
        e.add(new String[]{"0115", "元宵节"});
        e.add(new String[]{"0505", "端午节"});
        e.add(new String[]{"0707", "七夕节"});
        e.add(new String[]{"0815", "中秋节"});
        e.add(new String[]{"0909", "重阳节"});
        e.add(new String[]{"1208", "腊八节"});
        e.add(new String[]{"1230", "除夕"});
        dateFormat = new String[]{"yyyymmdd", "yyyy-mm-dd", "yyyy年mm月dd日", "yyyymm", "yyyy-mm", "yyyy年mm月", "yyyyqq", "yyyy-qq", "yyyy年qq季度", "yyyy", "yyyy年"};
        f = new ArrayList();
    }

    public CalendarTag() {
    }

    public static void pushFestival(String[] var0) {
        f.add(var0);
    }

    public static void removeFestival(String var0) {
        ArrayList var1 = new ArrayList();
        Iterator var3 = f.iterator();

        while(var3.hasNext()) {
            String[] var2 = (String[])var3.next();
            if (var0.equals(var2[0])) {
                var1.add(var2);
            }
        }

        f.removeAll(var1);
    }

    public static String getFestival(String var0, String var1) {
        if (var0 != null && var0.length() != 0 && !var0.equals("NULLVAL")) {
            CalendarTag var2 = new CalendarTag();
            String var4 = var1.length() == 8 ? var0.substring(4, 8) : var0.substring(5, 7) + var0.substring(8, 10);
            String var5 = var2.a(var4);
            String var6 = var2.a(var0, var1);
            List var7 = var2.b(var0, var4);
            String var8 = (var5 == null ? "" : var5) + (var6 == null ? "" : var6);

            String var9;
            for(Iterator var10 = var7.iterator(); var10.hasNext(); var8 = var8 + var9) {
                var9 = (String)var10.next();
            }

            return var8;
        } else {
            return "";
        }
    }

    private String a(String var1) {
        String var2 = null;
        Iterator var4 = d.iterator();

        while(var4.hasNext()) {
            String[] var3 = (String[])var4.next();
            if (var1.equals(var3[0])) {
                var2 = var3[1];
                break;
            }
        }

        if (var2 != null && var2.length() > 4) {
            var2 = var2.substring(0, 4);
        }

        return var2;
    }

    private String a(Calendar var1) {
        String var2 = LauarUtil.getLauar2(var1);
        String var3 = null;
        Iterator var5 = e.iterator();

        while(var5.hasNext()) {
            String[] var4 = (String[])var5.next();
            if (var2.equals(var4[0])) {
                var3 = var4[1];
                break;
            }
        }

        if (var3 != null && var3.length() > 4) {
            var3 = var3.substring(0, 4);
        }

        return var3;
    }

    private String a(String var1, String var2) {
        int var3;
        int var4;
        int var5;
        if (var2.length() == 8) {
            var3 = Integer.parseInt(var1.substring(0, 4));
            var4 = Integer.parseInt(var1.substring(4, 6)) - 1;
            var5 = Integer.parseInt(var1.substring(6, 8));
        } else {
            var3 = Integer.parseInt(var1.substring(0, 4));
            var4 = Integer.parseInt(var1.substring(5, 7)) - 1;
            var5 = Integer.parseInt(var1.substring(8, 10));
        }

        GregorianCalendar var6 = new GregorianCalendar(var3, var4, var5);
        return this.a((Calendar)var6);
    }

    private List b(String var1, String var2) {
        ArrayList var3 = new ArrayList();
        Iterator var5 = f.iterator();

        while(true) {
            String[] var4;
            do {
                if (!var5.hasNext()) {
                    return var3;
                }

                var4 = (String[])var5.next();
            } while(!var1.equals(var4[0]) && !var2.equals(var4[0]));

            String var6 = var4[1];
            if (var6 != null && var6.length() > 4) {
                var6 = var6.substring(0, 4);
            }

            var3.add(var6);
        }
    }

    private Calendar b(String var1) {
        if (var1 != null && var1.length() != 0) {
            GregorianCalendar var2 = null;
            int var3 = Integer.parseInt(var1.substring(0, 4));
            int var4 = Integer.parseInt(var1.substring(4, 6)) - 1;
            int var5 = Integer.parseInt(var1.substring(6, 8));
            var2 = new GregorianCalendar(var3, var4, var5);
            return var2;
        } else {
            return null;
        }
    }

    public int doEndTag() throws JspException {
        JspWriter var1 = this.pageContext.getOut();
        String var2 = this.pageContext.getRequest().getParameter("dt");
        if (var2 == null || var2.length() == 0) {
            var2 = this.a.format(new Date());
        }

        String var3 = this.pageContext.getRequest().getParameter("max");
        String var4 = this.pageContext.getRequest().getParameter("min");
        if (var3 == null || var3.length() == 0) {
            var3 = "20300101";
        }

        if (var4 == null || var4.length() == 0) {
            var4 = "19000101";
        }

        Calendar var5 = this.b(var3);
        Calendar var6 = this.b(var4);
        Calendar var7 = this.b(var2);

        try {
            GregorianCalendar var8 = null;
            int var9 = Integer.parseInt(var2.substring(0, 4));
            int var10 = Integer.parseInt(var2.substring(4, 6)) - 1;
            byte var11 = 1;
            var8 = new GregorianCalendar(var9, var10, var11);
            var1.println("<div align='center' style='font-size:14px; height:22px; padding-top:3px;' id=\"caleDiv\">");
            Calendar var12 = (Calendar)var7.clone();
            var12.add(2, -1);
            var12.set(5, var12.getActualMaximum(5));
            if (var12.getTimeInMillis() >= var6.getTimeInMillis()) {
                var1.print("<button class=\"btn btn-primary btn-xs\" id=\"c_gbf\" type=\"button\"><i class=\"fa fa-chevron-left\"></i></button> ");
                var1.print("<script>$(function(){$('#c_gbf').click(function(){ getCalendar('" + this.b + "','" + this.a.format(var12.getTime()) + "', '" + var4 + "','" + var3 + "') });})</script>");
            }

            var1.print("<a href='javascript:;' onclick='selectyearmonth()'>");
            var1.print(var9 + "年" + (var10 + 1) + "月");
            var1.print("</a>");
            Calendar var13 = (Calendar)var7.clone();
            var13.add(2, 1);
            var13.set(5, 1);
            if (var13.getTimeInMillis() <= var5.getTimeInMillis()) {
                var1.print(" <button type=\"button\" class=\"btn btn-primary btn-xs\" id=\"c_gaft\"><i class=\"fa fa-chevron-right\"></i></button>");
                var1.print("<script>$(function(){$('#c_gaft').click(function(){ getCalendar('" + this.b + "','" + this.a.format(var13.getTime()) + "','" + var4 + "','" + var3 + "') });})</script>");
            }

            var1.println("</div>");
            var1.print("<div id=\"selyearmonth\" style=\"display:none\">");
            var1.print("<select id=\"chgyear\" class=\"inputform\">");

            int var14;
            for(var14 = 2020; var14 >= 2000; --var14) {
                var1.print("<option value=\"" + var14 + "\" " + (var9 == var14 ? "selected = \"selected\"" : "") + ">" + var14 + "年</option>");
            }

            var1.print("</select> ");
            var1.print("<select id=\"chgmonth\" class=\"inputform\">");

            for(var14 = 1; var14 <= 12; ++var14) {
                var1.print("<option value=\"" + (var14 < 10 ? "0" + var14 : var14) + "\" " + (var10 == var14 - 1 ? "selected=\"selected\"" : "") + ">" + var14 + "月</option>");
            }

            var1.print("</select> ");
            var1.print("<input type='button' value='GO' class=\"btn btn-primary btn-xs\" onclick=\"getCalendar('" + this.b + "',$('#selyearmonth #chgyear').val()+$('#selyearmonth #chgmonth').val() + '01', '" + var4 + "', '" + var3 + "')\"> ");
            var1.print("<input type='button' value='Now' class=\"btn btn-primary btn-xs\"  onclick=\"getCalendar('" + this.b + "','" + this.a.format(new Date()) + "', '" + var4 + "', '" + var3 + "')\"> ");
            var1.print("</div>");
            var1.println("<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" class=\"calen\">");
            var1.println("<tr>");

            for(var14 = 1; var14 <= 7; ++var14) {
                var1.println("<th height='35' width='14%' align='center'><b style='font-size:14px;'>");
                if (var14 == 7 || var14 == 6) {
                    var1.print("<font color='#C60B02'>");
                }

                var1.println("周" + (var14 == 7 ? "日" : var14));
                if (var14 == 7 || var14 == 6) {
                    var1.print("</font>");
                }

                var1.println("</b></th>");
            }

            var1.println("</tr>");
            var14 = var8.get(7) - 1;
            if (var14 == 0) {
                var14 = 7;
            }

            var1.println("<tr>");

            for(int var15 = 1; var15 < var14; ++var15) {
                var1.println("<td align='center' height='42'> &nbsp; </td>");
            }

            Calendar var23 = (Calendar)var8.clone();
            var23.add(2, 1);

            for(; !var8.equals(var23); var8.add(5, 1)) {
                int var16 = var8.get(7) - 1;
                if (var16 == 0) {
                    var16 = 7;
                }

                String var17 = this.a.format(var8.getTime());
                var1.println("<td height='42' valign='top' align='center' " + (var17.equals(var2) ? "class='curdt'" : "") + ">");
                if (var8.getTimeInMillis() >= var6.getTimeInMillis() && var8.getTimeInMillis() <= var5.getTimeInMillis()) {
                    var1.print("<a href='javascript:;' onclick=\"");
                    var1.print("calendarPost(event,this,'" + var17 + "'" + (this.c != null && this.c.length() > 0 ? "," + this.c : "") + ");");
                    var1.print("\">");
                    if (var16 == 7 || var16 == 6) {
                        var1.print("<font color='#C60B02'>");
                    }

                    var1.print("<div style='font:normal 14px arial; margin-top:3px;'>" + var8.get(5));
                    String var18 = this.a(var17.substring(4, 8));
                    if (var18 != null) {
                        var1.print("<font style='font-size:12px; color:#0066FF'>(" + var18 + ")</font>");
                    }

                    String var19 = this.a((Calendar)var8);
                    if (var19 != null) {
                        var1.print("<font style='font-size:12px; color:#0066FF'>(" + var19 + ")</font>");
                    }

                    List var20 = this.b(var17, var17.substring(4, 8));
                    if (var20 != null && var20.size() > 0) {
                        var1.print("<font style='font-size:12px; color:#0066FF'>(");

                        for(int var21 = 0; var21 < var20.size(); ++var21) {
                            var1.print((String)var20.get(var21));
                            if (var21 != var20.size() - 1) {
                                var1.print(",");
                            }
                        }

                        var1.print(")</font>");
                    }

                    var1.print("</div>");
                    if (var16 == 7 || var16 == 6) {
                        var1.print("</font>");
                    }

                    var1.print("</a>");
                } else {
                    var1.print("<div class='overdate'>");
                    var1.print(var8.get(5));
                    var1.print("</div>");
                }

                var1.print(" </td>");
                if (var16 == 7) {
                    var1.print("</tr><tr>");
                }
            }

            var1.println("</tr>");
            var1.println("</table>");
        } catch (IOException var22) {
            var22.printStackTrace();
        }

        return super.doEndTag();
    }

    public String getDivId() {
        return this.b;
    }

    public void setDivId(String var1) {
        this.b = var1;
    }

    public String getCallback() {
        return this.c;
    }

    public void setCallback(String var1) {
        this.c = var1;
    }
}
