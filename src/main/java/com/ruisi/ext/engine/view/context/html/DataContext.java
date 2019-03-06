//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.html;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.face.Template;

public interface DataContext extends Element, Template {
    String getKey();

    void setKey(String var1);

    boolean isMulti();

    void setMulti(boolean var1);

    String getRefDsource();

    void setRefDsource(String var1);

    String[] getOutKey();

    String[] getOutVal();

    void setOutKey(String[] var1);

    void setOutVal(String[] var1);

    String getRefDataCenter();

    void setRefDataCenter(String var1);
}
