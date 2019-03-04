//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.imp;

import com.ruisitech.bi.entity.common.BaseEntity;
import java.util.List;

public class DataWriteDto extends BaseEntity {
    private String tableName;
    private Integer dataId;
    public List<DataWriteColDto> cols;

    public DataWriteDto() {
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getDataId() {
        return this.dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public List<DataWriteColDto> getCols() {
        return this.cols;
    }

    public void setCols(List<DataWriteColDto> cols) {
        this.cols = cols;
    }

    public void validate() {
        for(int i = 0; this.cols != null && i < this.cols.size(); ++i) {
            DataWriteColDto col = (DataWriteColDto)this.cols.get(i);
            col.validate();
        }

    }
}
