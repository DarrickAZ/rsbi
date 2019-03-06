//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.form;

import com.ruisi.ext.engine.view.context.Element;

public interface InputField extends Element {
    void setDesc(String var1);

    String getDesc();

    void setSize(String var1);

    String getSize();

    String getId();

    void setId(String var1);

    boolean isRequire();

    void setRequire(boolean var1);

    void setValue(String var1);

    String getValue();

    void setOutValue(String var1);

    String getOutValue();

    String getDefaultValue();

    void setDefaultValue(String var1);

    boolean isShow();

    void setShow(boolean var1);

    String getInputType();

    Boolean getOutBox();

    void setOutBox(Boolean var1);
}
