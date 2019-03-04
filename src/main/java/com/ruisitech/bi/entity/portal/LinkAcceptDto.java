//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.portal;

import com.ruisitech.bi.entity.common.BaseEntity;

public class LinkAcceptDto extends BaseEntity {
    private Integer id;
    private String col;
    private String tableColKey;
    private String alias;
    private String type;
    private String dftval;
    private String valType;
    private Integer calc;
    private String fromCol;

    public LinkAcceptDto() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCol() {
        return this.col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getTableColKey() {
        return this.tableColKey;
    }

    public void setTableColKey(String tableColKey) {
        this.tableColKey = tableColKey;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDftval() {
        return this.dftval;
    }

    public void setDftval(String dftval) {
        this.dftval = dftval;
    }

    public String getValType() {
        return this.valType;
    }

    public void setValType(String valType) {
        this.valType = valType;
    }

    public Integer getCalc() {
        return this.calc;
    }

    public void setCalc(Integer calc) {
        this.calc = calc;
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
