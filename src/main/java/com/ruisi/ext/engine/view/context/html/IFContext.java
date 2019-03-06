//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.html;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.test.TestAdapter;

public interface IFContext extends Element {
    void setTestAdapter(TestAdapter var1);

    TestAdapter getTestAdapter();

    String getJsFunc();

    void setJsFunc(String var1);
}
