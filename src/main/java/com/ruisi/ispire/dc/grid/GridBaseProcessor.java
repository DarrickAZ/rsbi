//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.grid;

import com.ruisi.ext.engine.view.builder.dc.GridDataCenterBuilder;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;

import java.text.ParseException;
import java.util.List;

public abstract class GridBaseProcessor {
    protected List datas;
    protected GridDataMetaData metaData;
    protected GridDataCenterBuilder builder;

    public GridBaseProcessor() {
    }

    public void init(List var1, GridDataMetaData var2, GridDataCenterBuilder var3) {
        this.datas = var1;
        this.metaData = var2;
        this.builder = var3;
    }

    public void initDatas(List var1) {
        this.datas = var1;
    }

    public abstract List process() throws ScriptEnginerException, ParseException;
}
