//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.model;

import com.ruisitech.bi.entity.common.BaseEntity;

public class TableMetaCol extends BaseEntity {
    private Integer rid;
    private Integer tid;
    private String tname;
    private Integer colType;
    private Integer colId;
    private String col;
    private String alias;
    private Integer calc;
    private Integer ord;
    private Integer targetId;
    private String isupdate;
    private String fromCol;

    public TableMetaCol() {
    }

    public Integer getTid() {
        return this.tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getColType() {
        return this.colType;
    }

    public void setColType(Integer colType) {
        this.colType = colType;
    }

    public Integer getColId() {
        return this.colId;
    }

    public void setColId(Integer colId) {
        this.colId = colId;
    }

    public String getCol() {
        return this.col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getCalc() {
        return this.calc;
    }

    public void setCalc(Integer calc) {
        this.calc = calc;
    }

    public Integer getOrd() {
        return this.ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public Integer getRid() {
        return this.rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getTargetId() {
        return this.targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getIsupdate() {
        return this.isupdate;
    }

    public void setIsupdate(String isupdate) {
        this.isupdate = isupdate;
    }

    public String getTname() {
        return this.tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getFromCol() {
        return this.fromCol;
    }

    public void setFromCol(String fromCol) {
        this.fromCol = fromCol;
    }

    public void validate() {
    }
}
