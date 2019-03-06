//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.html;

import com.ruisi.ext.engine.view.builder.AbstractBuilder;
import com.ruisi.ext.engine.view.builder.html.IFBuilder;
import com.ruisi.ext.engine.view.context.AbstractContext;
import com.ruisi.ext.engine.view.test.TestAdapter;

public class IFContextImpl extends AbstractContext implements IFContext {
    private String a;
    private TestAdapter b;

    public IFContextImpl() {
    }

    public String getJsFunc() {
        return this.a;
    }

    public void setJsFunc(String var1) {
        this.a = var1;
    }

    public AbstractBuilder createBuilder() {
        return new IFBuilder(this);
    }

    public TestAdapter getTestAdapter() {
        return this.b;
    }

    public void setTestAdapter(TestAdapter var1) {
        this.b = var1;
    }
}
