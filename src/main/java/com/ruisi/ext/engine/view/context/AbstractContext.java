//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context;

import java.util.List;

public abstract class AbstractContext implements Element {
    private Element a;
    private Boolean b;
    private List c;

    public AbstractContext() {
    }

    @Override
    public Element getParent() {
        return this.a;
    }

    @Override
    public void setParent(Element var1) {
        this.a = var1;
    }

    @Override
    public List getChildren() {
        return this.c;
    }

    @Override
    public void setChildren(List var1) {
        this.c = var1;
    }

    @Override
    public boolean isGoOn() {
        return this.b == null || this.b;
    }

    @Override
    public void setIsGoOn(Boolean var1) {
        this.b = var1;
    }
}
