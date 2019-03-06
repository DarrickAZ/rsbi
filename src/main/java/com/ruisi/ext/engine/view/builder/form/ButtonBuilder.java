//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder.form;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.context.form.ButtonContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;

import java.lang.reflect.InvocationTargetException;

public class ButtonBuilder extends AbstractBuilder {
    private ButtonContext a;

    public ButtonBuilder(ButtonContext var1) {
        this.a = var1;
    }

    @Override
    protected void processEnd() {
    }

    @Override
    protected void processStart() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, ExtConfigException {
        this.emitter.startButton(this.a);
    }
}
