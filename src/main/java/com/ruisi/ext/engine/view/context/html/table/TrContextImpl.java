//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.html.table;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.html.table.TrBuilder;
import com.ruisi.ext.engine.view.context.AbstractContext;

public class TrContextImpl extends AbstractContext implements TrContext {
    public TrContextImpl() {
    }

    public AbstractBuilder createBuilder() {
        return new TrBuilder(this);
    }
}
