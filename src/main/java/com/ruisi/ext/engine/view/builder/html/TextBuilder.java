//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.html;

import com.itextpdf.text.DocumentException;
import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.context.html.TextContext;

public class TextBuilder extends AbstractBuilder {
    private TextContext a;

    @Override
    protected void processEnd() {
    }

    @Override
    protected void processStart() throws DocumentException {
        String var1 = this.a.getTemplateName();
        if (var1 != null && var1.length() > 0) {
            this.a.setText(TemplateManager.buildTempldate(var1, this.request, this.veloContext));
        }

        this.emitter.startText(this.a);
    }

    public TextBuilder(TextContext var1) {
        this.a = var1;
    }
}
