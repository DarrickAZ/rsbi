//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.etl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.Date;

public class EtlTransform extends BaseEntity {
    private Integer id;
    private String name;
    private Integer crtuser;
    private String crtUserName;
    @JsonFormat(
        pattern = "yyyy-MM-dd"
    )
    private Date crtdate;
    @JsonFormat(
        pattern = "yyyy-MM-dd"
    )
    private Date updatedate;
    private Integer companyId;
    private String primaryTable;
    private String targetTable;
    private Integer tableMetaId;
    private String income;
    private String saveType;
    private String cfg;
    private String esEnable;
    private Long dataCount;

    public EtlTransform() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getCrtuser() {
        return this.crtuser;
    }

    public void setCrtuser(Integer crtuser) {
        this.crtuser = crtuser;
    }

    public Date getCrtdate() {
        return this.crtdate;
    }

    public void setCrtdate(Date crtdate) {
        this.crtdate = crtdate;
    }

    public Date getUpdatedate() {
        return this.updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getPrimaryTable() {
        return this.primaryTable;
    }

    public void setPrimaryTable(String primaryTable) {
        this.primaryTable = primaryTable == null ? null : primaryTable.trim();
    }

    public String getTargetTable() {
        return this.targetTable;
    }

    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable == null ? null : targetTable.trim();
    }

    public Integer getTableMetaId() {
        return this.tableMetaId;
    }

    public void setTableMetaId(Integer tableMetaId) {
        this.tableMetaId = tableMetaId;
    }

    public String getIncome() {
        return this.income;
    }

    public void setIncome(String income) {
        this.income = income == null ? null : income.trim();
    }

    public String getSaveType() {
        return this.saveType;
    }

    public void setSaveType(String saveType) {
        this.saveType = saveType == null ? null : saveType.trim();
    }

    public String getCfg() {
        return this.cfg;
    }

    public void setCfg(String cfg) {
        this.cfg = cfg == null ? null : cfg.trim();
    }

    public String getCrtUserName() {
        return this.crtUserName;
    }

    public void setCrtUserName(String crtUserName) {
        this.crtUserName = crtUserName;
    }

    public String getEsEnable() {
        return this.esEnable;
    }

    public void setEsEnable(String esEnable) {
        this.esEnable = esEnable;
    }

    public Long getDataCount() {
        return this.dataCount;
    }

    public void setDataCount(Long dataCount) {
        this.dataCount = dataCount;
    }

    public void validate() {
        this.name = RSBIUtils.htmlEscape(this.name);
        this.primaryTable = RSBIUtils.htmlEscape(this.primaryTable);
        this.targetTable = RSBIUtils.htmlEscape(this.targetTable);
        this.income = RSBIUtils.htmlEscape(this.income);
        this.saveType = RSBIUtils.htmlEscape(this.saveType);
    }
}
