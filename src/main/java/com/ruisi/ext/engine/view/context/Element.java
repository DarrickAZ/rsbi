//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import java.util.List;

public interface Element {
    Element getParent();

    void setParent(Element var1);

    List getChildren();

    void setChildren(List var1);

    AbstractBuilder createBuilder();

    boolean isGoOn();

    void setIsGoOn(Boolean var1);
}
