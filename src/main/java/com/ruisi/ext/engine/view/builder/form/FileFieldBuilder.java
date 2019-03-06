//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.form;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.context.form.FileFieldContext;

public class FileFieldBuilder extends AbstractBuilder {
    private FileFieldContext a;

    public FileFieldBuilder(FileFieldContext var1) {
        this.a = var1;
    }

    protected void processEnd() {
    }

    protected void processStart() {
        this.emitter.startFileField(this.a);
    }
}
