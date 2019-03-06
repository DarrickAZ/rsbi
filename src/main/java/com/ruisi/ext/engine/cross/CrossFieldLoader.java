//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.cross;

import com.ruisi.ext.engine.view.context.cross.CrossField;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import java.util.List;
import java.util.Map;

public interface CrossFieldLoader {
    String loadFieldName(String var1, String var2);

    List loadField(String var1, String var2, Map var3);

    CrossField loadFieldByKpiCode(String var1);

    List loadUserCustomize(String var1, String var2);

    void setRequest(ExtRequest var1);
}
