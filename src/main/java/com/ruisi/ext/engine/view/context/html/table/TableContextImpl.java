//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.html.table;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.html.table.TableBuilder;
import com.ruisi.ext.engine.view.context.AbstractContext;

public class TableContextImpl extends AbstractContext implements TableContext {
    private String a;
    private String b;
    private String c;

    public TableContextImpl() {
    }

    public AbstractBuilder createBuilder() {
        return new TableBuilder(this);
    }

    public String getStyleClass() {
        return this.a;
    }

    public void setStyleClass(String var1) {
        this.a = var1;
    }

    public String getStyle() {
        return this.b;
    }

    public void setStyle(String var1) {
        this.b = var1;
    }

    public String getAlign() {
        return this.c;
    }

    public void setAlign(String var1) {
        this.c = var1;
    }
}
