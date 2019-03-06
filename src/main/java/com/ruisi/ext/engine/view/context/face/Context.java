//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.face;

import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtResponse;

public class Context {
    private ExtRequest a;
    private ExtResponse b;
    private int c;

    public Context() {
    }

    public int getStartHeight() {
        return this.c;
    }

    public void setStartHeight(int var1) {
        this.c = var1;
    }

    public ExtRequest getRequest() {
        return this.a;
    }

    public ExtResponse getResponse() {
        return this.b;
    }

    public void setRequest(ExtRequest var1) {
        this.a = var1;
    }

    public void setResponse(ExtResponse var1) {
        this.b = var1;
    }
}
