//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube.operation;

import com.ruisi.ispire.dc.cube.DataSet;
import com.ruisi.ispire.dc.cube.DataSetMetaData;

public abstract class BaseProcessor {
    protected DataSet ds;
    protected DataOperUtils dataOper;
    protected DataSetMetaData dsMetaData;

    public BaseProcessor() {
    }

    public void initDataSet(DataSet var1) {
        this.ds = var1;
        this.dsMetaData = var1.getDataSetMetaData();
        this.dataOper = new DataOperUtils(var1.getDatas(), var1.getDataSetMetaData());
    }
}
