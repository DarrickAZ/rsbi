//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.html;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.context.html.FieldsetContext;

public class FieldsetBuilder extends AbstractBuilder {
    private FieldsetContext a;

    public FieldsetBuilder(FieldsetContext var1) {
        this.a = var1;
    }

    protected void processEnd() {
        this.emitter.endFieldset(this.a);
    }

    protected void processStart() {
        this.emitter.startFieldset(this.a);
    }
}
