//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.grid;

import com.ruisi.ext.engine.view.test.TestAdapter;
import java.util.ArrayList;
import java.util.List;

public class ColConfigContext {
    private DataGridContext a;
    private List b = new ArrayList();
    private ColsContext c;
    private TestAdapter d;

    public ColConfigContext(DataGridContext var1) {
        this.a = var1;
    }

    public List getColContexts() {
        return this.b;
    }

    public void putColContext(ColContext var1) {
        this.b.add(var1);
    }

    public DataGridContext getDataGridContext() {
        return this.a;
    }

    public ColsContext getColsContext() {
        return this.c;
    }

    public void setColsContext(ColsContext var1) {
        this.c = var1;
    }

    public TestAdapter getTestAdapter() {
        return this.d;
    }

    public void setTestAdapter(TestAdapter var1) {
        this.d = var1;
    }
}
