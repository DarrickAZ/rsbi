//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube;

import com.ruisi.ext.engine.view.context.dc.cube.DataCenterContext;
import java.util.List;

public class DataSet {
    private String a;
    private List b;
    private DataSetMetaData c;
    private DataCenter d;
    private DataCenterContext e;

    public DataSet() {
    }

    public DataCenterContext getConf() {
        return this.e;
    }

    public void setConf(DataCenterContext var1) {
        this.e = var1;
    }

    public String getQuerySql() {
        return this.a;
    }

    public void setQuerySql(String var1) {
        this.a = var1;
    }

    public DataSetMetaData getDataSetMetaData() {
        return this.c;
    }

    public void setDataSetMetaData(DataSetMetaData var1) {
        this.c = var1;
    }

    public String toString() {
        return this.b == null ? null : this.b.toString();
    }

    public List getDatas() {
        return this.b;
    }

    public void setDatas(List var1) {
        this.b = var1;
    }

    public DataCenter getDataCenter() {
        return this.d;
    }

    public void setDataCenter(DataCenter var1) {
        this.d = var1;
    }
}
