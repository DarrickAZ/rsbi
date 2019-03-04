//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.etl;

import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.util.RSBIUtils;

public class EtlTableMetaCol extends BaseEntity {
    private Integer colId;
    private String colName;
    private String colType;
    private Integer colLength;
    private Integer colScale;
    private String colNote;
    private Integer colOrd;
    private Integer companyId;
    private Integer tableId;
    private String tableName;
    private String incomeTname;
    private String expression;
    private String inputType;
    private String defvalue;
    private String colDesc;
    private String options;
    private String valuestype;
    private String matchTable;
    private String matchCol;
    private String useCol;
    private String updateCol;
    private String tCondition;
    private String matchColText;
    private String tmpid;
    private String fromCol;

    public EtlTableMetaCol() {
    }

    public Integer getColId() {
        return this.colId;
    }

    public void setColId(Integer colId) {
        this.colId = colId;
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

    public Integer getColLength() {
        return this.colLength;
    }

    public void setColLength(Integer colLength) {
        this.colLength = colLength;
    }

    public String getColNote() {
        return this.colNote;
    }

    public void setColNote(String colNote) {
        this.colNote = colNote;
    }

    public Integer getColOrd() {
        return this.colOrd;
    }

    public void setColOrd(Integer colOrd) {
        this.colOrd = colOrd;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getTableId() {
        return this.tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getInputType() {
        return this.inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getDefvalue() {
        return this.defvalue;
    }

    public void setDefvalue(String defvalue) {
        this.defvalue = defvalue;
    }

    public String getColDesc() {
        return this.colDesc;
    }

    public void setColDesc(String colDesc) {
        this.colDesc = colDesc;
    }

    public String getOptions() {
        return this.options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getValuestype() {
        return this.valuestype;
    }

    public void setValuestype(String valuestype) {
        this.valuestype = valuestype;
    }

    public String getMatchTable() {
        return this.matchTable;
    }

    public void setMatchTable(String matchTable) {
        this.matchTable = matchTable;
    }

    public String getMatchCol() {
        return this.matchCol;
    }

    public void setMatchCol(String matchCol) {
        this.matchCol = matchCol;
    }

    public String getUseCol() {
        return this.useCol;
    }

    public void setUseCol(String useCol) {
        this.useCol = useCol;
    }

    public String getUpdateCol() {
        return this.updateCol;
    }

    public void setUpdateCol(String updateCol) {
        this.updateCol = updateCol;
    }

    public String gettCondition() {
        return this.tCondition;
    }

    public void settCondition(String tCondition) {
        this.tCondition = tCondition;
    }

    public String getMatchColText() {
        return this.matchColText;
    }

    public void setMatchColText(String matchColText) {
        this.matchColText = matchColText;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTmpid() {
        return this.tmpid;
    }

    public void setTmpid(String tmpid) {
        this.tmpid = tmpid;
    }

    public String getIncomeTname() {
        return this.incomeTname;
    }

    public void setIncomeTname(String incomeTname) {
        this.incomeTname = incomeTname;
    }

    public Integer getColScale() {
        return this.colScale;
    }

    public void setColScale(Integer colScale) {
        this.colScale = colScale;
    }

    public String getFromCol() {
        return this.fromCol;
    }

    public void setFromCol(String fromCol) {
        this.fromCol = fromCol;
    }

    public void validate() {
        this.colName = RSBIUtils.htmlEscape(this.colName);
        this.colType = RSBIUtils.htmlEscape(this.colType);
        this.colNote = RSBIUtils.htmlEscape(this.colNote);
    }
}
