//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.html;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.face.OptionsLoader;
import java.util.List;

public interface KpiDescContext extends Element, OptionsLoader {
    List getKpis();

    void setKpis(List var1);

    void setImpl(String var1);

    String getImpl();
}
