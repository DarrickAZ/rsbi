//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruisitech.bi.util.RSBIUtils;

public class BaseEntity {
    @JsonIgnore
    private String sysUser = RSBIUtils.getConstant("sysUser");
    @JsonIgnore
    private String dbName = RSBIUtils.getConstant("dbName");
    @JsonIgnore
    private Integer idType;

    public BaseEntity() {
    }

    public String getSysUser() {
        return this.sysUser;
    }

    public void setSysUser(String sysUser) {
        this.sysUser = sysUser;
    }

    public String getDbName() {
        return this.dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public Integer getIdType() {
        if ("oracle".equals(this.dbName)) {
            this.idType = 2;
        } else {
            this.idType = 1;
        }

        return this.idType;
    }

    @JsonIgnore
    public String getDateString() {
        String key = "";
        if (this.dbName.equalsIgnoreCase("mysql")) {
            key = "now()";
        } else if (this.dbName.equalsIgnoreCase("oracle")) {
            key = "sysdate";
        } else if (this.dbName.equalsIgnoreCase("sqlser")) {
            key = "getdate()";
        } else if ("db2".equalsIgnoreCase(this.dbName)) {
            key = "current timestamp";
        }

        return key;
    }

    public void validate() {
    }
}
