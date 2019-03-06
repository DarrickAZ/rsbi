//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.grid;

import com.ruisi.ext.engine.view.test.TestAdapter;
import java.util.ArrayList;
import java.util.List;

public class ColContext {
    private TestAdapter a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private int i;
    private DataGridContext j;
    private boolean k = false;
    private String l;
    private String m;
    private List n = new ArrayList();
    private String o;
    private boolean p = false;

    public ColContext(DataGridContext var1) {
        this.j = var1;
    }

    public String getAlias() {
        return this.b;
    }

    public void setAlias(String var1) {
        this.b = var1;
    }

    public String getDesc() {
        return this.c;
    }

    public void setDesc(String var1) {
        this.c = var1;
    }

    public String getFormatPattern() {
        return this.d;
    }

    public void setFormatPattern(String var1) {
        this.d = var1;
    }

    public String getType() {
        return this.e;
    }

    public void setType(String var1) {
        this.e = var1;
    }

    public String getId() {
        return this.f;
    }

    public void setId(String var1) {
        this.f = var1;
    }

    public DataGridContext getDataGridContext() {
        return this.j;
    }

    public String getWidth() {
        return this.g;
    }

    public void setWidth(String var1) {
        this.g = var1;
    }

    public List getLinkCtxs() {
        return this.n;
    }

    public TestAdapter getTestAdapter() {
        return this.a;
    }

    public void setTestAdapter(TestAdapter var1) {
        this.a = var1;
    }

    public boolean isOrder() {
        return this.k;
    }

    public void setOrder(boolean var1) {
        this.k = var1;
    }

    public int getLength() {
        return this.i;
    }

    public void setLength(int var1) {
        this.i = var1;
    }

    public String getAlign() {
        return this.h;
    }

    public void setAlign(String var1) {
        this.h = var1;
    }

    public String getStyleClass() {
        return this.l;
    }

    public void setStyleClass(String var1) {
        this.l = var1;
    }

    public String getAlt() {
        return this.m;
    }

    public void setAlt(String var1) {
        this.m = var1;
    }

    public boolean isFinanceFmt() {
        return this.p;
    }

    public void setFinanceFmt(boolean var1) {
        this.p = var1;
    }

    public String getJsFunc() {
        return this.o;
    }

    public void setJsFunc(String var1) {
        this.o = var1;
    }
}
