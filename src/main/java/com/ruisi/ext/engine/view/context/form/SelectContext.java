//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.form;

import com.ruisi.ext.engine.view.context.face.OptionsLoader;
import com.ruisi.ext.engine.view.context.face.RefChecker;
import com.ruisi.ext.engine.view.context.face.Template;

public interface SelectContext extends OptionsLoader, RefChecker, Template, InputField {
    void setDataId(String var1);

    String getDataId();

    String getCascade();

    void setCascade(String var1);

    String getRefDsource();

    void setRefDsource(String var1);

    Boolean getAddEmptyValue();

    void setAddEmptyValue(Boolean var1);
}
