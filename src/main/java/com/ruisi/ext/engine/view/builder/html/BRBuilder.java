//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.html;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.context.html.BRContext;

public class BRBuilder extends AbstractBuilder {
    private BRContext a;

    public BRBuilder(BRContext var1) {
        this.a = var1;
    }

    protected void processEnd() {
    }

    protected void processStart() {
        this.emitter.startBR(this.a);
    }
}
