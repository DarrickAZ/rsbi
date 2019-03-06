//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.util;

import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.dao.DatabaseHelper;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.grid.PageInfo;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import java.util.List;
import javax.servlet.ServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class DaoUtils {
    public static boolean showLogs = true;

    public DaoUtils() {
    }

    public static DaoHelper getDaoHelper(ServletContext var0) {
        WebApplicationContext var1 = WebApplicationContextUtils.getRequiredWebApplicationContext(var0);
        String var2 = ExtContext.getInstance().getConstant("daoName");
        if (var2 == null || var2.length() == 0) {
            var2 = "daoHelper";
        }

        if (!var1.containsBean(var2)) {
            return null;
        } else {
            DaoHelper var3 = (DaoHelper)var1.getBean(var2);
            return var3;
        }
    }

    public static List calPage(String var0, PageInfo var1, DaoHelper var2) throws ExtConfigException {
        String var3 = ExtContext.getInstance().getConstant("dbName");
        if (var3 == null || var3.length() == 0) {
            var3 = "mysql";
        }

        String var4 = var0;
        if ("sqlser".equalsIgnoreCase(var3)) {
            int var5 = var0.lastIndexOf("order by");
            if (var5 >= 0) {
                var4 = var0.substring(0, var5);
            }
        }

        String var9 = "select count(*) from (" + var4 + ") ttt";
        long var6 = var2.queryForLong(var9);
        var1.setAllsize(var6);
        DatabaseHelper var8 = ExtContext.getInstance().getDatabaseHelper(var3);
        var0 = var8.getQueryPageSql(var0, var1);
        return var2.queryForList(var0);
    }

    public static void printPageInfoByAjax(PageInfo var0, ExtWriter var1, String var2, String var3, ExtEnvirContext var4) {
        MVContext var5 = RuleUtils.findCurMV(var4);
        String var6 = "v" + var5.getFormId();
        String var7 = var5.getMvid();
        boolean var8 = var5.isShowForm();
        var1.print("<div class=\"pagesizeLeft\">");
        if (var0.getCurtpage() <= 0L) {
            var1.print("<button type=\"button\" class=\"btn btn-link btn-xs\"><i class=\"fa fa-angle-double-left\"></i></button>");
            var1.print("<button type=\"button\" class=\"btn btn-link btn-xs\"><i class=\"fa fa-angle-left\"></i></button>");
        } else {
            var1.print("<button type=\"button\" class=\"btn btn-link btn-xs\" onclick=\"gotobyajax(" + (var8 ? var6 : null) + ",'" + var2 + "'," + var0.getPagesize() + ",0,'" + var3 + "','" + var7 + "')\"><i class=\"fa fa-angle-double-left\"></i></button>");
            var1.print("<button type=\"button\" class=\"btn btn-link btn-xs\" onclick=\"gotobyajax(" + (var8 ? var6 : null) + ",'" + var2 + "'," + var0.getPagesize() + "," + (var0.getCurtpage() - 1L) + ",'" + var3 + "','" + var7 + "')\"><i class=\"fa fa-angle-left\"></i></button>");
        }

        if (var0.getCurtpage() >= var0.getAllpage() - 1L) {
            var1.print("<button class=\"btn btn-link btn-xs\" type=\"button\"><i class=\"fa fa-angle-right\"></i></button>");
            var1.print("<button type=\"button\" class=\"btn btn-link btn-xs\"><i class=\"fa fa-angle-double-right\"></i></button>");
        } else {
            var1.print("<button type=\"button\" class=\"btn btn-link btn-xs\" onclick=\"gotobyajax(" + (var8 ? var6 : null) + ",'" + var2 + "'," + var0.getPagesize() + "," + (var0.getCurtpage() + 1L) + ",'" + var3 + "','" + var7 + "')\"><i class=\"fa fa-angle-right\"></i></button>");
            var1.print("<button type=\"button\" class=\"btn btn-link btn-xs\" onclick=\"gotobyajax(" + (var8 ? var6 : null) + ",'" + var2 + "'," + var0.getPagesize() + "," + (var0.getAllpage() - 1L) + ",'" + var3 + "','" + var7 + "')\"><i class=\"fa fa-angle-double-right\"></i></button>");
        }

        var1.print("到");
        var1.print("<input type=\"text\" name=\"extCurrPage\" id=\"extCurrPage\" class=\"pg-inputform\" style=\"width:40px;height:22px;\" value=\"" + (var0.getCurtpage() + 1L) + "\" >");
        var1.print("/<font color='red'>" + var0.getAllpage() + "</font>页 &nbsp; ");
        var1.print("<button class=\"btn btn-link btn-xs\" type=\"button\" onclick=\"gotobyajax(" + (var8 ? var6 : null) + ",'" + var2 + "'," + var0.getPagesize() + "," + "$('#extCurrPage').val() - 1" + ",'" + var3 + "','" + var7 + "')\">GO</button>");
        var1.print("</div>");
        var1.print("<div class=\"pagesizeRight\">");
        var1.print("共<font color='red'>" + var0.getAllsize() + "</font>/" + var0.getPagesize() + "条记录");
        var1.print("</div>");
    }

    public static void printPageInfo(PageInfo var0, String var1, ExtWriter var2, String var3, ExtEnvirContext var4) {
        String var5 = "\"" + var3 + "\"";
        MVContext var6 = RuleUtils.findCurMV(var4);
        String var7 = "v" + var6.getFormId();
        var2.print("<div class=\"pagesizeLeft\">");
        if (var0.getCurtpage() <= 0L) {
            var2.print("<button type=\"button\" class=\"btn btn-link btn-xs\"><i class=\"fa fa-angle-double-left\"></i></button>");
            var2.print("<button type=\"button\" class=\"btn btn-link btn-xs\"><i class=\"fa fa-angle-left\"></i></button>");
        } else {
            var2.print("<button type=\"button\" class=\"btn btn-link btn-xs\" onclick='gotopage(" + var7 + ",0," + var5 + ",\"" + var6.getMvid() + "\")'><i class=\"fa fa-angle-double-left\"></i></button>");
            var2.print("<button type=\"button\" class=\"btn btn-link btn-xs\" onclick='gotopage(" + var7 + "," + (var0.getCurtpage() - 1L) + "," + var5 + ",\"" + var6.getMvid() + "\")'><i class=\"fa fa-angle-left\"></i></button>");
        }

        if (var0.getCurtpage() >= var0.getAllpage() - 1L) {
            var2.print("<button class=\"btn btn-link btn-xs\" type=\"button\"><i class=\"fa fa-angle-right\"></i></button>");
            var2.print("<button class=\"btn btn-link btn-xs\" type=\"button\"><i class=\"fa fa-angle-double-right\"></i></button>");
        } else {
            var2.print("<button class=\"btn btn-link btn-xs\" type=\"button\" onclick='gotopage(" + var7 + "," + (var0.getCurtpage() + 1L) + "," + var5 + ",\"" + var6.getMvid() + "\")'><i class=\"fa fa-angle-right\"></i></button>");
            var2.print("<button class=\"btn btn-link btn-xs\" type=\"button\" onclick='gotopage(" + var7 + "," + (var0.getAllpage() - 1L) + "," + var5 + ",\"" + var6.getMvid() + "\")'><i class=\"fa fa-angle-double-right\"></i></button>");
        }

        var2.print("到");
        var2.print("<input type=\"text\" name=\"extCurrPage\" id=\"extCurrPage\" class=\"pg-inputform\" style=\"width:40px;height:22px;\" value=\"" + (var0.getCurtpage() + 1L) + "\">");
        var2.print("/<font color='red'>" + var0.getAllpage() + "</font>页 ");
        var2.print("<button class=\"btn btn-link btn-xs\" type=\"button\" onclick='gotopage(" + var7 + ",$(\"#extCurrPage\").val() - 1," + var5 + ",\"" + var6.getMvid() + "\")'>GO</button> ");
        var2.print("</div>");
        var2.print("<div class=\"pagesizeRight\">");
        var2.print("共<font color='red'>" + var0.getAllsize() + "</font>条记录, ");
        var2.print("每页显示" + var0.getPagesize() + "条 ");
        var2.print("</div>");
    }
}
