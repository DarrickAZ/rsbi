//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.test;

import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import java.util.Map;

public interface TestAdapter {
    boolean test(Map var1, ExtEnvirContext var2, ExtRequest var3);

    void setPrefix(String var1);

    void setPrefixType(String var1);

    void setSuffix(String var1);

    void setSuffixType(String var1);
}
