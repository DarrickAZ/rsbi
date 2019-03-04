//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.bireport;

import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.entity.etl.EtlTableMetaCol;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TableInfoVO extends BaseEntity {
    private Integer tid;
    private String tname;
    private String dataControlCol;
    private String sql;
    private String esEnable;
    private String useEs;
    private Date createTime;
    private List<EtlTableMetaCol> cols;

    public TableInfoVO() {
    }

    public EtlTableMetaCol findColByAlias(String alias) {
        EtlTableMetaCol ret = null;
        Iterator var3 = this.cols.iterator();

        while(var3.hasNext()) {
            EtlTableMetaCol col = (EtlTableMetaCol)var3.next();
            if (col.getColName().equals(alias)) {
                ret = col;
            }
        }

        return ret;
    }

    public Integer getTid() {
        return this.tid;
    }

    public String getTname() {
        return this.tname;
    }

    public String getSql() {
        return this.sql;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<EtlTableMetaCol> getCols() {
        return this.cols;
    }

    public void setCols(List<EtlTableMetaCol> cols) {
        this.cols = cols;
    }

    public String getDataControlCol() {
        return this.dataControlCol;
    }

    public void setDataControlCol(String dataControlCol) {
        this.dataControlCol = dataControlCol;
    }

    public String getEsEnable() {
        return this.esEnable;
    }

    public void setEsEnable(String esEnable) {
        this.esEnable = esEnable;
    }

    public String getUseEs() {
        return this.useEs;
    }

    public void setUseEs(String useEs) {
        this.useEs = useEs;
    }

    public void validate() {
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
