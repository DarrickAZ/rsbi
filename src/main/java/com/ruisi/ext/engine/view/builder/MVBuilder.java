//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder;

import com.ruisi.ext.engine.view.context.MVContext;

public class MVBuilder extends AbstractBuilder {
    private MVContext a;

    public MVBuilder(MVContext var1) {
        this.a = var1;
    }

    public void processEnd() {
        this.emitter.endMV(this.a);
    }

    public void processStart() {
        this.emitter.startMV(this.a);
    }
}
