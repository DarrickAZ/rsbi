//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.bireport;

import com.ruisitech.bi.entity.common.PageParam;
import java.util.Map;

public class TableDetailDto extends PageParam {
    private Map<String, String> pms;
    private Integer tid;

    public TableDetailDto() {
    }

    public Map<String, String> getPms() {
        return this.pms;
    }

    public void setPms(Map<String, String> pms) {
        this.pms = pms;
    }

    public Integer getTid() {
        return this.tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }
}
