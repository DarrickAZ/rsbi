//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private String a;
    private String b;
    private String c;
    private boolean d = false;
    private int e;
    private String f;
    private boolean g = false;
    private List h = new ArrayList();

    public TreeNode() {
    }

    public String getId() {
        return this.a;
    }

    public void setId(String var1) {
        this.a = var1;
    }

    public String getPid() {
        return this.b;
    }

    public void setPid(String var1) {
        this.b = var1;
    }

    public String getName() {
        return this.c;
    }

    public void setName(String var1) {
        this.c = var1;
    }

    public boolean isLeaf() {
        return this.d;
    }

    public void setLeaf(boolean var1) {
        this.d = var1;
    }

    public List getChilden() {
        return this.h;
    }

    public void putChilden(TreeNode var1) {
        this.h.add(var1);
    }

    public int getLevel() {
        return this.e;
    }

    public void setLevel(int var1) {
        this.e = var1;
    }

    public String getUrl() {
        return this.f;
    }

    public void setUrl(String var1) {
        this.f = var1;
    }

    public boolean isIsopen() {
        return this.g;
    }

    public void setIsopen(boolean var1) {
        this.g = var1;
    }
}
