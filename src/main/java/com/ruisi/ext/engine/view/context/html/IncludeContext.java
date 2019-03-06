//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.html;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.face.RefChecker;

public interface IncludeContext extends Element, RefChecker {
    void setPage(String var1);

    String getPage();

    String getMvid();

    void setMvid(String var1);

    void setContent(String var1);

    String getContent();
}
