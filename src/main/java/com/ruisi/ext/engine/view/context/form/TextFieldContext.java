//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.form;

import com.ruisi.ext.engine.view.context.face.RefChecker;

public interface TextFieldContext extends RefChecker, InputField {
    String getType();

    void setType(String var1);

    String getReadOnly();

    void setReadOnly(String var1);
}
