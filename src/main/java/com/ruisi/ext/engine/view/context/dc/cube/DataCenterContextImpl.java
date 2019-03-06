//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.cube;

import java.util.ArrayList;
import java.util.List;

public class DataCenterContextImpl implements DataCenterContext {
    private String a;
    private String b;
    private DataSetConfContext c = new DataSetConfContext();
    private List d = new ArrayList();

    public DataCenterContextImpl() {
    }

    public List getProcess() {
        return this.d;
    }

    public DataSetConfContext getDataSetConf() {
        return this.c;
    }

    public String getScript() {
        return this.b;
    }

    public void setScript(String var1) {
        this.b = var1;
    }

    public String getId() {
        return this.a;
    }

    public void setId(String var1) {
        this.a = var1;
    }
}
