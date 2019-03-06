//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.html.table;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.context.html.table.TrContext;

public class TrBuilder extends AbstractBuilder {
    private TrContext a;

    public TrBuilder(TrContext var1) {
        this.a = var1;
    }

    protected void processEnd() {
        this.emitter.endTr(this.a);
    }

    protected void processStart() {
        this.emitter.startTr(this.a);
    }
}
