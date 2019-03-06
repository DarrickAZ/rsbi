//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.test;

import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import java.util.Map;

public class TestNotEqualAdapterImpl implements TestAdapter {
    private String a;
    private String b;
    private String c;
    private String d;

    public TestNotEqualAdapterImpl() {
    }

    public boolean test(Map var1, ExtEnvirContext var2, ExtRequest var3) {
        Object var4 = TestUtils.findData(var1, var2, var3, this.a, this.b);
        Object var5 = TestUtils.findData(var1, var2, var3, this.c, this.d);
        if (var4 != null && var5 != null) {
            return !var4.equals(var5);
        } else {
            return false;
        }
    }

    public String getPrefix() {
        return this.a;
    }

    public void setPrefix(String var1) {
        this.a = var1;
    }

    public String getPrefixType() {
        return this.b;
    }

    public void setPrefixType(String var1) {
        this.b = var1;
    }

    public String getSuffix() {
        return this.c;
    }

    public void setSuffix(String var1) {
        this.c = var1;
    }

    public String getSuffixType() {
        return this.d;
    }

    public void setSuffixType(String var1) {
        this.d = var1;
    }
}
