//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.etl;

import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;

public class ImpConfigDto extends BaseEntity {
    private String sql;
    private String impType;
    private DataSource dsource;
    private String path;
    private String encode;
    private String splitWord;
    private String hdfsAddress;
    private Integer sheetIndex;
    private String nameinhead;
    private String impid;
    private Integer targettableid;
    private String targettable;
    private Boolean truncate;
    private String datelabel;
    private List<ImpConfigColDto> cols;

    public ImpConfigDto() {
    }

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getImpType() {
        return this.impType;
    }

    public void setImpType(String impType) {
        this.impType = impType;
    }

    public DataSource getDsource() {
        return this.dsource;
    }

    public void setDsource(DataSource dsource) {
        this.dsource = dsource;
    }

    public String getPath() {
        return this.path;
    }

    public String getRealPath() {
        String up = RSBIUtils.getUploadFilePath();
        return up + this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getEncode() {
        return this.encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public String getSplitWord() {
        return this.splitWord;
    }

    public void setSplitWord(String splitWord) {
        this.splitWord = splitWord;
    }

    public Integer getSheetIndex() {
        return this.sheetIndex;
    }

    public void setSheetIndex(Integer sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public String getNameinhead() {
        return this.nameinhead;
    }

    public void setNameinhead(String nameinhead) {
        this.nameinhead = nameinhead;
    }

    public String getImpid() {
        return this.impid;
    }

    public void setImpid(String impid) {
        this.impid = impid;
    }

    public Integer getTargettableid() {
        return this.targettableid;
    }

    public void setTargettableid(Integer targettableid) {
        this.targettableid = targettableid;
    }

    public String getTargettable() {
        return this.targettable;
    }

    public void setTargettable(String targettable) {
        this.targettable = targettable;
    }

    public Boolean getTruncate() {
        return this.truncate;
    }

    public void setTruncate(Boolean truncate) {
        this.truncate = truncate;
    }

    public String getDatelabel() {
        return this.datelabel;
    }

    public void setDatelabel(String datelabel) {
        this.datelabel = datelabel;
    }

    public List<ImpConfigColDto> getCols() {
        return this.cols;
    }

    public void setCols(List<ImpConfigColDto> cols) {
        this.cols = cols;
    }

    public String getHdfsAddress() {
        return this.hdfsAddress;
    }

    public void setHdfsAddress(String hdfsAddress) {
        this.hdfsAddress = hdfsAddress;
    }

    public void validate() {
    }
}
