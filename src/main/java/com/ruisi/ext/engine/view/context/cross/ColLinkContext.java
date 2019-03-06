//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.cross;

public class ColLinkContext {
    private String a;
    private String[] b;
    private String[] c;
    private String d;
    private boolean e = true;

    public ColLinkContext() {
    }

    public boolean isUse() {
        return this.e;
    }

    public void setUse(boolean var1) {
        this.e = var1;
    }

    public String getUrl() {
        return this.d;
    }

    public void setUrl(String var1) {
        this.d = var1;
    }

    public String[] getTarget() {
        return this.b;
    }

    public void setTarget(String[] var1) {
        this.b = var1;
    }

    public String getMvId() {
        return this.a;
    }

    public void setMvId(String var1) {
        this.a = var1;
    }

    public String[] getType() {
        return this.c;
    }

    public void setType(String[] var1) {
        this.c = var1;
    }
}
