//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.html.table;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.context.html.table.TableContext;

public class TableBuilder extends AbstractBuilder {
    private TableContext a;

    public TableBuilder(TableContext var1) {
        this.a = var1;
    }

    protected void processEnd() {
        this.emitter.endTable(this.a);
    }

    protected void processStart() {
        this.emitter.startTable(this.a);
    }
}
