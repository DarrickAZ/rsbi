//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.form;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.form.FileFieldBuilder;

public class FileFieldContextImpl extends AbstractInputContext implements FileFieldContext {
    public FileFieldContextImpl() {
    }

    public AbstractBuilder createBuilder() {
        return new FileFieldBuilder(this);
    }

    public String getInputType() {
        return "file";
    }
}
