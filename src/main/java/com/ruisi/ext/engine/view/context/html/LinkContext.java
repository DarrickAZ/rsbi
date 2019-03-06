//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.html;

import com.ruisi.ext.engine.view.context.Element;

public interface LinkContext extends Element {
    String getAction();

    void setAction(String var1);

    String getText();

    void setText(String var1);

    String getMethod();

    void setMethod(String var1);

    String getOnClick();

    void setOnClick(String var1);

    void setStyleClass(String var1);

    String getStyleClass();

    void setSrc(String var1);

    String getSrc();

    boolean isAllowParam();

    void setAllowParam(boolean var1);

    String getWindowState();

    void setWindowState(String var1);

    void setOtherParam(String var1);

    String getOtherParam();
}
