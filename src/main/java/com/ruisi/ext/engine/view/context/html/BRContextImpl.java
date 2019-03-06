//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.html;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.html.BRBuilder;
import com.ruisi.ext.engine.view.context.AbstractContext;

public class BRContextImpl extends AbstractContext implements BRContext {
    public BRContextImpl() {
    }

    public AbstractBuilder createBuilder() {
        return new BRBuilder(this);
    }
}
