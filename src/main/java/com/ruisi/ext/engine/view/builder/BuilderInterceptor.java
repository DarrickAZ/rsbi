//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder;

import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.wrapper.ExtRequest;

public interface BuilderInterceptor {
    void start(Element var1, ExtRequest var2, DaoHelper var3);

    void end(Element var1, ExtRequest var2, DaoHelper var3);
}
