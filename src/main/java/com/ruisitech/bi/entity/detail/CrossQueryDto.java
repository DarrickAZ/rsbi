//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.detail;

import com.alibaba.fastjson.JSONObject;
import com.ruisitech.bi.entity.bireport.TableQueryDto;

public class CrossQueryDto extends TableQueryDto {
    private JSONObject dset;

    public CrossQueryDto() {
    }

    public JSONObject getDset() {
        return this.dset;
    }

    public void setDset(JSONObject dset) {
        this.dset = dset;
    }
}
