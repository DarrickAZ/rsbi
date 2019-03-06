//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class PageBuilder$JSObject {
    private Context a;
    private Scriptable b;

    public PageBuilder$JSObject(Context var1, Scriptable var2) {
        this.a = var1;
        this.b = var2;
    }

    public Context getCt() {
        return this.a;
    }

    public Scriptable getScope() {
        return this.b;
    }
}
