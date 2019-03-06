//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.html;

import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.util.IdCreater;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.context.tab.TabContext;
import com.ruisi.ext.engine.view.context.tab.TabViewContext;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HtmlTabViewEnginer implements HtmlTabView {
    private ExtWriter a;
    private HTMLEmitter b;
    private ExtRequest c;
    private InputOption d;
    private String e;

    public HtmlTabViewEnginer(HTMLEmitter var1) {
        this.b = var1;
        this.a = var1.getOut();
        this.c = var1.getRequest();
        this.d = var1.getOption();
    }

    public void buildTabViewStart(TabViewContext var1) {
        String var2 = "div" + IdCreater.create();
        this.e = var2;
        this.a.println("<div id=\"" + var2 + "\" style=\"height:auto\" data-options=\"plain:true,border:false\">");
    }

    public int findTabValuePos(TabViewContext var1, String var2) {
        int var3 = 0;

        for(int var4 = 0; var4 < var1.getChildren().size(); ++var4) {
            TabContext var5 = (TabContext)var1.getChildren().get(var4);
            if (var2.equals(var5.getValue())) {
                var3 = var4;
            }
        }

        return var3;
    }

    public void buildTabViewStartAjax(TabViewContext var1) {
        String var2 = "div" + IdCreater.create();
        this.e = var2;
        this.a.println("<div id=\"" + var2 + "\" style=\"height:auto\" data-options=\"plain:true,border:false\">");
    }

    public void buildTabViewEnd(TabViewContext var1) {
        this.a.println("</div>");
        this.a.println("<script>jQuery('#" + this.e + "').tabs({onSelect:function(a, b){");
        this.a.print(" var tbs = [");

        for(int var2 = 0; var2 < var1.getChildren().size(); ++var2) {
            TabContext var3 = (TabContext)var1.getChildren().get(var2);
            this.a.print("'" + var3.getValue() + "'");
            if (var2 != var1.getChildren().size() - 1) {
                this.a.print(",");
            }
        }

        this.a.println("];");
        this.a.println("changeTabId('" + var1.getOutParameterName() + "', tbs[b]);");
        this.a.println("}});</script>");
    }

    public void buildTabViewEndAjax(TabViewContext var1) {
        this.a.println("</div>");
        this.a.println("<script>jQuery('#" + this.e + "').tabs({onSelect:function(a, b){");
        this.a.print(" var tbs = [");

        for(int var2 = 0; var2 < var1.getChildren().size(); ++var2) {
            TabContext var3 = (TabContext)var1.getChildren().get(var2);
            this.a.print("'" + var3.getValue() + "'");
            if (var2 != var1.getChildren().size() - 1) {
                this.a.print(",");
            }
        }

        this.a.println("];");
        this.a.println("changeTabId('" + var1.getOutParameterName() + "', tbs[b]);");
        this.a.println("}});</script>");
    }

    public void buildTabStart(TabContext var1) {
        TabViewContext var2 = (TabViewContext)var1.getParent();
        String var3 = RuleUtils.findCurMV(this.b.getCtx()).getMvid();
        boolean var4 = false;
        String var5 = this.c.getParameter(var2.getOutParameterName());
        if (var5 != null && var5.length() > 0 && var5.equals(var1.getValue())) {
            var4 = true;
        }

        this.a.print("<div title=\"" + var1.getTitle() + "\" data-options=\"" + (var4 ? "selected:true" : "") + "\">");
        this.a.print(var1.getContent());
        this.a.println("</div>");
    }

    public void buildTabStartAjax(TabContext var1) {
        TabViewContext var2 = (TabViewContext)var1.getParent();
        String var3 = RuleUtils.findCurMV(this.b.getCtx()).getMvid();
        boolean var4 = false;
        String var5 = this.c.getParameter(var2.getOutParameterName());
        if (var5 != null && var5.length() > 0 && var5.equals(var1.getValue())) {
            var4 = true;
        }

        this.a.print("<div title=\"" + var1.getTitle() + "\" data-options=\"" + (var4 ? "selected:true," : "") + "href:'");
        String var6 = RuleUtils.getResPath(this.c) + "control/";
        this.a.print(var6);
        this.a.print("extControl?");
        this.a.print("mvid=" + var1.getRef() + "&");
        this.a.print("t_from_id=" + var3 + "&");
        this.a.print("serviceid=ext.sys.tab.ajax&");
        Map var7 = this.d.getParams();
        Iterator var9 = var7.entrySet().iterator();

        while(var9.hasNext()) {
            Entry var8 = (Entry)var9.next();
            String var10 = (String)var8.getKey();
            Object var11 = var8.getValue();
            if (!var10.equals(var2.getOutParameterName()) && var11 instanceof String) {
                this.a.print(var10 + "=" + var11 + "&");
            }
        }

        this.a.print(var2.getOutParameterName() + "=" + var1.getValue());
        this.a.print("'\">");
        this.a.print("</div>");
    }

    public void buildTabEndAjax(TabContext var1) {
    }

    public void buildTabEnd(TabContext var1) {
    }
}
