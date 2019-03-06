//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.form;

import com.ruisi.ext.engine.view.context.face.OptionsLoader;
import com.ruisi.ext.engine.view.context.face.RefChecker;
import com.ruisi.ext.engine.view.context.face.Template;

public interface RadioContext extends OptionsLoader, RefChecker, Template, InputField {
    String getOnClick();

    void setOnClick(String var1);

    String getRefDsource();

    void setRefDsource(String var1);

    Boolean getShowSpan();

    void setShowSpan(Boolean var1);

    String getRadioStyle();

    void setRadioStyle(String var1);

    String[] getTarget();

    void setTarget(String[] var1);
}
