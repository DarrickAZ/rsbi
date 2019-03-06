//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.html;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.html.ImageBuilder;
import com.ruisi.ext.engine.view.context.AbstractContext;

public class ImageContextImpl extends AbstractContext implements ImageContext {
    private String a;
    private Integer b;
    private Integer c;
    private String d;
    private String e;
    private String f;

    public ImageContextImpl() {
    }

    public AbstractBuilder createBuilder() {
        return new ImageBuilder(this);
    }

    public String getUrl() {
        return this.a;
    }

    public void setUrl(String var1) {
        this.a = var1;
    }

    public Integer getWidth() {
        return this.b;
    }

    public Integer getHeight() {
        return this.c;
    }

    public void setWidth(Integer var1) {
        this.b = var1;
    }

    public void setHeight(Integer var1) {
        this.c = var1;
    }

    public String getAlign() {
        return this.d;
    }

    public void setAlign(String var1) {
        this.d = var1;
    }

    public String getType() {
        return this.e;
    }

    public void setType(String var1) {
        this.e = var1;
    }

    public String getPath() {
        return this.f;
    }

    public void setPath(String var1) {
        this.f = var1;
    }
}
