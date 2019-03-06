//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context.dc.grid;

import com.ruisi.ext.engine.view.context.face.Template;
import com.ruisi.ispire.dc.grid.DatasetProvider;
import java.util.List;

public class GridSetConfContext implements Template {
    private String a;
    private String b;
    private List c;
    private DatasetProvider d;
    private String e;
    private boolean f = true;

    public GridSetConfContext() {
    }

    public String getTemplateName() {
        return this.a;
    }

    public void setTemplateName(String var1) {
        this.a = var1;
    }

    public String getRefDsource() {
        return this.b;
    }

    public void setRefDsource(String var1) {
        this.b = var1;
    }

    public List getMultiDsContext() {
        return this.c;
    }

    public void setMultiDsContext(List var1) {
        this.c = var1;
    }

    public DatasetProvider getDatasetProvider() {
        return this.d;
    }

    public void setDatasetProvider(DatasetProvider var1) {
        this.d = var1;
    }

    public String getMaster() {
        return this.e;
    }

    public void setMaster(String var1) {
        this.e = var1;
    }

    public boolean isAgg() {
        return this.f;
    }

    public void setAgg(boolean var1) {
        this.f = var1;
    }
}
