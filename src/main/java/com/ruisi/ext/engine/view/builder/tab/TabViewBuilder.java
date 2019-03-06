//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.tab;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.context.tab.TabViewContext;

public class TabViewBuilder extends AbstractBuilder {
    private TabViewContext a;

    public TabViewBuilder(TabViewContext var1) {
        this.a = var1;
    }

    protected void processEnd() {
        this.emitter.endTabView(this.a);
    }

    protected void processStart() {
        this.emitter.startTabView(this.a);
    }
}
