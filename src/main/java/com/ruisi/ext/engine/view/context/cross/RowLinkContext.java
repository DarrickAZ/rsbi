//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.cross;

import com.ruisi.ext.engine.control.ActionProcess;
import com.ruisi.ext.engine.view.context.face.RefChecker;
import com.ruisi.ext.engine.view.exception.ExtConfigException;

public class RowLinkContext implements RefChecker {
    private String a;
    private String[] b;
    private String[] c;
    private boolean d = true;
    private String e;
    private String f;
    private String g;

    public RowLinkContext() {
    }

    public String getUrl() {
        return this.e;
    }

    public void setUrl(String var1) {
        this.e = var1;
    }

    @Override
    public void check() throws ExtConfigException {
        if (this.a != null && this.a.length() > 0) {
            ActionProcess.findMVContextById(this.a, false);
        }

    }

    public boolean isUse() {
        return this.d;
    }

    public void setUse(boolean var1) {
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

    public String getAction() {
        return this.f;
    }

    public void setAction(String var1) {
        this.f = var1;
    }

    public String getParamName() {
        return this.g;
    }

    public void setParamName(String var1) {
        this.g = var1;
    }
}
