//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.html;

import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.tab.TabContext;
import com.ruisi.ext.engine.view.context.tab.TabViewContext;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class HtmlTabViewByJQuery implements HtmlTabView {
    private ExtWriter a;
    private HTMLEmitter b;
    private ExtRequest c;
    private InputOption d;
    private Random e;

    public HtmlTabViewByJQuery(HTMLEmitter var1) {
        this.b = var1;
        this.a = var1.getOut();
        this.c = var1.getRequest();
        this.d = var1.getOption();
        this.e = new Random();
    }

    public void buildTabViewStart(TabViewContext var1) {
    }

    public int findTabValuePos(TabViewContext var1, String var2) {
        int var3 = 0;
        if (var2 == null) {
            return this.findActiveTab(var1);
        } else {
            for(int var4 = 0; var4 < var1.getChildren().size(); ++var4) {
                TabContext var5 = (TabContext)var1.getChildren().get(var4);
                if (var2.equals(var5.getValue())) {
                    var3 = var4;
                }
            }

            return var3;
        }
    }

    public int findActiveTab(TabViewContext var1) {
        int var2 = 0;

        for(int var3 = 0; var3 < var1.getChildren().size(); ++var3) {
            TabContext var4 = (TabContext)var1.getChildren().get(var3);
            if (var4.isActive()) {
                var2 = var3;
            }
        }

        return var2;
    }

    public void buildTabViewStartAjax(TabViewContext var1) {
        String var2 = this.c.getParameter(var1.getOutParameterName());
        this.a.println("<script>");
        this.a.println("jQuery(function(){");
        this.a.println("jQuery('ul.tabs').tabs('> .pane');");
        this.a.println("jQuery('.wrap > .tabs').each(function(i,dom){");
        this.a.println("var api=jQuery(dom).data('tabs');");
        this.a.println("api.prev();");
        this.a.println("api.onClick(function(){");
        this.a.println("changeTabId('" + var1.getOutParameterName() + "', api.getCurrentTab().parent().attr('value'));");
        this.a.println("var curp=api.getCurrentPane();");
        this.a.println("if(curp.find('ul').size()>0){return false;}");
        this.a.println("if(api.getCurrentTab().attr('loaded')=='loaded'){return false;}");
        this.a.println("jQuery.ajax({");
        this.a.println("type:'post',");
        this.a.println("url:api.getCurrentTab().attr('href'),");
        this.a.println("data:jQuery.param(jQuery('input:hidden')),");
        this.a.println("success:function(rel){");
        this.a.println("curPane=api.getCurrentPane();");
        this.a.println("curPane.html(rel);");
        this.a.println("api.getCurrentTab().attr('loaded','loaded');");
        this.a.println("}");
        this.a.println("});");
        this.a.println("return false;");
        this.a.println("});");
        this.a.println("api.click(" + this.findTabValuePos(var1, var2) + ");");
        this.a.println("})");
        this.a.println("})");
        this.a.println("</script>");
        this.a.println("<div class=\"wrap\">");
        this.a.println("<ul class=\"tabs\">");
    }

    public void buildTabViewEnd(TabViewContext var1) {
    }

    public void buildTabViewEndAjax(TabViewContext var1) {
        this.a.println("</ul>");
        Iterator var3 = var1.getChildren().iterator();

        while(var3.hasNext()) {
            Element var2 = (Element)var3.next();
            this.a.println("<div class=\"pane\"></div>");
        }

        this.a.println("</div>");
    }

    public void buildTabStart(TabContext var1) {
    }

    public void buildTabStartAjax(TabContext var1) {
        TabViewContext var2 = (TabViewContext)var1.getParent();
        String var3 = (String)this.c.getAttribute("ext.view.mvid");
        this.a.print("<li value=\"" + var1.getValue() + "\"><a href=\"");
        String var4 = RuleUtils.getResPath(this.c) + "control/";
        this.a.print(var4);
        this.a.print("extControl?");
        this.a.print("mvid=" + var1.getRef() + "&");
        this.a.print("t_from_id=" + var3 + "&");
        this.a.print("serviceid=ext.sys.tab.ajax&");
        Map var5 = this.d.getParams();
        Iterator var7 = var5.entrySet().iterator();

        while(var7.hasNext()) {
            Entry var6 = (Entry)var7.next();
            String var8 = (String)var6.getKey();
            Object var9 = var6.getValue();
            if (!var8.equals(var2.getOutParameterName()) && var9 instanceof String) {
                this.a.print(var8 + "=" + var9 + "&");
            }
        }

        this.a.print(var2.getOutParameterName() + "=" + var1.getValue());
        this.a.print("\">" + var1.getTitle() + "</a></li>");
    }

    public void buildTabEndAjax(TabContext var1) {
    }

    public void buildTabEnd(TabContext var1) {
    }
}
