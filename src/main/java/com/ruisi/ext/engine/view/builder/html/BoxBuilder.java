//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.html;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.context.html.BoxContext;

public class BoxBuilder extends AbstractBuilder {
    private BoxContext a;

    public BoxBuilder(BoxContext var1) {
        this.a = var1;
    }

    protected void processEnd() {
        this.emitter.endBox(this.a);
    }

    protected void processStart() {
        this.emitter.startBox(this.a);
    }
}
