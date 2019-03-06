//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.form;

import com.itextpdf.text.DocumentException;
import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.context.form.TextFieldContext;
import com.ruisi.ext.engine.view.test.TestUtils;

public class TextFieldBuilder extends AbstractBuilder {
    private TextFieldContext a;

    public TextFieldBuilder(TextFieldContext var1) {
        this.a = var1;
    }

    @Override
    protected void processEnd() {
    }

    @Override
    protected void processStart() {
        String var1 = this.a.getValue();
        if (var1 != null && var1.length() > 0) {
            var1 = TestUtils.findValue(var1, this.request, this.veloContext);
        }

        this.a.setOutValue(var1);
        try {
            this.emitter.startTextField(this.a);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
