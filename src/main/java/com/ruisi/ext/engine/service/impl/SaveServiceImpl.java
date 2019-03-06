//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.service.impl;

import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.service.ServiceSupport;

public class SaveServiceImpl extends ServiceSupport {
    private String a;
    private String b;
    private String c;

    public SaveServiceImpl() {
    }

    public void execute(InputOption var1) {
        String[] var2 = this.c.split(",");
        String[] var3 = this.b.split(",");
        this.daoHelper.execute(this.a, new a(this, var2, var3, var1));
    }

    public String getSql() {
        return this.a;
    }

    public void setSql(String var1) {
        this.a = var1;
    }

    public String getTypes() {
        return this.b;
    }

    public void setTypes(String var1) {
        this.b = var1;
    }

    public String getCols() {
        return this.c;
    }

    public void setCols(String var1) {
        this.c = var1;
    }
}
