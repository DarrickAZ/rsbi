//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.html;

public class TextProperty {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private Boolean i;
    private Integer j;

    public TextProperty() {
    }

    public boolean isEmpty() {
        return (this.a == null || this.a.length() == 0) && (this.b == null || this.b.length() == 0) && (this.c == null || this.c.length() == 0) && (this.d == null || this.d.length() == 0) && (this.e == null || this.e.length() == 0) && (this.g == null || this.g.length() == 0);
    }

    public String getHeight() {
        return this.c;
    }

    public void setHeight(String var1) {
        this.c = var1;
    }

    public String getSize() {
        return this.a;
    }

    public void setSize(String var1) {
        this.a = var1;
    }

    public String getAlign() {
        return this.b;
    }

    public void setAlign(String var1) {
        this.b = var1;
    }

    public String getWeight() {
        return this.d;
    }

    public void setWeight(String var1) {
        this.d = var1;
    }

    public String getColor() {
        return this.e;
    }

    public void setColor(String var1) {
        this.e = var1;
    }

    public String getId() {
        return this.f;
    }

    public void setId(String var1) {
        this.f = var1;
    }

    public String getText() {
        return this.h;
    }

    public void setText(String var1) {
        this.h = var1;
    }

    public String getStyleClass() {
        return this.g;
    }

    public void setStyleClass(String var1) {
        this.g = var1;
    }

    public Boolean getUnderLine() {
        return this.i;
    }

    public void setUnderLine(Boolean var1) {
        this.i = var1;
    }

    public Integer getLineHeight() {
        return this.j;
    }

    public void setLineHeight(Integer var1) {
        this.j = var1;
    }
}
