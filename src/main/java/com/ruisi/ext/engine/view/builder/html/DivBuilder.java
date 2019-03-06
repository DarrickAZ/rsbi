//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.html;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.context.html.DivContext;

public class DivBuilder extends AbstractBuilder {
    private DivContext a;

    public DivBuilder(DivContext var1) {
        this.a = var1;
    }

    protected void processEnd() {
        this.emitter.endDiv(this.a);
    }

    protected void processStart() {
        this.emitter.startDiv(this.a);
    }
}
