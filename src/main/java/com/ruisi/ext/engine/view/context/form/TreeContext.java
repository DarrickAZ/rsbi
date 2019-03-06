//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.form;

import com.ruisi.ext.engine.view.context.face.OptionsLoader;
import com.ruisi.ext.engine.view.context.face.Template;

public interface TreeContext extends OptionsLoader, Template, InputField {
    String getRefDsource();

    void setRefDsource(String var1);

    String getValueId();

    String getValueText();

    String getValuePid();

    void setValueId(String var1);

    void setValueText(String var1);

    void setValuePid(String var1);

    String getDefRootId();

    void setDefRootId(String var1);
}
