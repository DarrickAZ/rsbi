//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.emitter.html;

import com.ruisi.ext.engine.view.context.tab.TabContext;
import com.ruisi.ext.engine.view.context.tab.TabViewContext;

public interface HtmlTabView {
    void buildTabEnd(TabContext var1);

    void buildTabEndAjax(TabContext var1);

    void buildTabStart(TabContext var1);

    void buildTabStartAjax(TabContext var1);

    void buildTabViewEnd(TabViewContext var1);

    void buildTabViewEndAjax(TabViewContext var1);

    void buildTabViewStart(TabViewContext var1);

    void buildTabViewStartAjax(TabViewContext var1);

    int findTabValuePos(TabViewContext var1, String var2);
}
