//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.html.table;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.context.html.table.TdContext;

public class TdBuilder extends AbstractBuilder {
    private TdContext a;

    public TdBuilder(TdContext var1) {
        this.a = var1;
    }

    protected void processEnd() {
        this.emitter.endTd(this.a);
    }

    protected void processStart() {
        this.emitter.startTd(this.a);
    }
}
