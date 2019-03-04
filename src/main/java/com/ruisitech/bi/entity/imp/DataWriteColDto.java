//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.imp;

import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.util.RSBIUtils;

public class DataWriteColDto extends BaseEntity {
    private String colName;
    private String colType;
    private String value;

    public DataWriteColDto() {
    }

    public String getColName() {
        return this.colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getColType() {
        return this.colType;
    }

    public void setColType(String colType) {
        this.colType = colType;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void validate() {
        this.value = RSBIUtils.htmlEscape(this.value);
    }
}
