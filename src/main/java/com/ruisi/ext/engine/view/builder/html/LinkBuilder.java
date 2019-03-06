//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.html;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.context.html.LinkContext;

import java.io.UnsupportedEncodingException;

public class LinkBuilder extends AbstractBuilder {
    private LinkContext a;

    public LinkBuilder(LinkContext var1) {
        this.a = var1;
    }

    @Override
    protected void processEnd() {
    }

    @Override
    protected void processStart() throws UnsupportedEncodingException {
        this.emitter.startLink(this.a);
    }
}
