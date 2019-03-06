//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.face;

import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import java.util.List;
import javax.servlet.ServletContext;

public interface UserKpiDesc {
    List kpis();

    void init(ServletContext var1, DaoHelper var2, ExtEnvirContext var3);
}
