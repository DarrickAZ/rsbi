//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.etl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.entity.common.DSColumn;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.Date;
import java.util.List;

public class EtlTableMeta extends BaseEntity {
    private Integer tableId;
    private String tableName;
    private String tableNote;
    private Integer crtUser;
    private String crtUserName;

    private String tbId;
    @JsonFormat(
        pattern = "yyyy-MM-dd"
    )
    private Date crtDate;
    private String income;
    private String tableDesc;
    private String tableSql;
    private String useEs;
    private String esEnable;
    private String dataControlCol;
    private String saveType;
    private List<DSColumn> cols;
    private List<EtlTableMetaCol> metaCols;

    public EtlTableMeta() {
    }

    public String getTbId() {
        return tbId;
    }

    public void setTbId(String tbId) {
        this.tbId = tbId;
    }

    public Integer getTableId() {
        return this.tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableNote() {
        return this.tableNote;
    }

    public void setTableNote(String tableNote) {
        this.tableNote = tableNote;
    }

    public Integer getCrtUser() {
        return this.crtUser;
    }

    public void setCrtUser(Integer crtUser) {
        this.crtUser = crtUser;
    }

    public Date getCrtDate() {
        return this.crtDate;
    }

    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }

    public String getIncome() {
        return this.income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getTableDesc() {
        return this.tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    public String getTableSql() {
        return this.tableSql;
    }

    public void setTableSql(String tableSql) {
        this.tableSql = tableSql;
    }

    public String getUseEs() {
        return this.useEs;
    }

    public void setUseEs(String useEs) {
        this.useEs = useEs;
    }

    public String getEsEnable() {
        return this.esEnable;
    }

    public void setEsEnable(String esEnable) {
        this.esEnable = esEnable;
    }

    public String getCrtUserName() {
        return this.crtUserName;
    }

    public void setCrtUserName(String crtUserName) {
        this.crtUserName = crtUserName;
    }

    public List<DSColumn> getCols() {
        return this.cols;
    }

    public void setCols(List<DSColumn> cols) {
        this.cols = cols;
    }

    public List<EtlTableMetaCol> getMetaCols() {
        return this.metaCols;
    }

    public void setMetaCols(List<EtlTableMetaCol> metaCols) {
        this.metaCols = metaCols;
    }

    public String getSaveType() {
        return this.saveType;
    }

    public void setSaveType(String saveType) {
        this.saveType = saveType;
    }

    public String getDataControlCol() {
        return this.dataControlCol;
    }

    public void setDataControlCol(String dataControlCol) {
        this.dataControlCol = dataControlCol;
    }

    public void validate() {
        this.tableName = RSBIUtils.htmlEscape(this.tableName);
        this.tableNote = RSBIUtils.htmlEscape(this.tableNote);

        for(int i = 0; this.metaCols != null && i < this.metaCols.size(); ++i) {
            ((EtlTableMetaCol)this.metaCols.get(i)).validate();
        }

    }
}
