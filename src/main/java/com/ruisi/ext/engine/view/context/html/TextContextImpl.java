//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.html;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.html.TextBuilder;
import com.ruisi.ext.engine.view.context.AbstractContext;

public class TextContextImpl extends AbstractContext implements TextContext {
    private String a;
    private String b;
    private TextProperty c;
    private Boolean d;
    private Boolean e;

    public TextContextImpl() {
    }

    public TextProperty getTextProperty() {
        return this.c;
    }

    public void setTextProperty(TextProperty var1) {
        this.c = var1;
    }

    public String getText() {
        return this.a;
    }

    public void setText(String var1) {
        this.a = var1;
    }

    public AbstractBuilder createBuilder() {
        return new TextBuilder(this);
    }

    public String getTemplateName() {
        return this.b;
    }

    public void setTemplateName(String var1) {
        this.b = var1;
    }

    public Boolean getFormatEnter() {
        return this.d;
    }

    public void setFormatEnter(Boolean var1) {
        this.d = var1;
    }

    public Boolean getFormatHtml() {
        return this.e;
    }

    public void setFormatHtml(Boolean var1) {
        this.e = var1;
    }
}
