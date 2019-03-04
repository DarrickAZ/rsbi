//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.etl;

import com.ruisitech.bi.entity.common.BaseEntity;
import java.util.List;
import java.util.Map;

public class DataImpRestDto extends BaseEntity {
    private String tableName;
    private Boolean truncate;
    private List<ImpConfigColDto> cols;
    private List<Map<String, Object>> datas;

    public DataImpRestDto() {
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Boolean getTruncate() {
        return this.truncate;
    }

    public void setTruncate(Boolean truncate) {
        this.truncate = truncate;
    }

    public List<ImpConfigColDto> getCols() {
        return this.cols;
    }

    public void setCols(List<ImpConfigColDto> cols) {
        this.cols = cols;
    }

    public List<Map<String, Object>> getDatas() {
        return this.datas;
    }

    public void setDatas(List<Map<String, Object>> datas) {
        this.datas = datas;
    }

    public void validate() {
    }
}
