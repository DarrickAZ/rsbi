//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.grid;

import java.util.ArrayList;
import java.util.List;

public class GridDataCenterContextImpl implements GridDataCenterContext {
    private String a;
    private List b = new ArrayList();
    private GridSetConfContext c;

    public GridDataCenterContextImpl() {
    }

    public GridSetConfContext getConf() {
        return this.c;
    }

    public void setConf(GridSetConfContext var1) {
        this.c = var1;
    }

    public String getId() {
        return this.a;
    }

    public void setId(String var1) {
        this.a = var1;
    }

    public List getProcess() {
        return this.b;
    }

    public void setProcess(List var1) {
        this.b = var1;
    }
}
