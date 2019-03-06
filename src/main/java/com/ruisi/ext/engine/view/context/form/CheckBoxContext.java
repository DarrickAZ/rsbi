//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.form;

import com.ruisi.ext.engine.view.context.face.MoreValue;
import com.ruisi.ext.engine.view.context.face.OptionsLoader;
import com.ruisi.ext.engine.view.context.face.Template;

public interface CheckBoxContext extends MoreValue, OptionsLoader, Template, InputField {
    String getDataId();

    void setDataId(String var1);

    void setTarget(String[] var1);

    String[] getTarget();

    String getRefDsource();

    void setRefDsource(String var1);

    Boolean getShowSpan();

    void setShowSpan(Boolean var1);

    String getCheckboxStyle();

    void setCheckboxStyle(String var1);
}
