//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.detail;

import com.alibaba.fastjson.JSONObject;
import com.ruisitech.bi.entity.bireport.ParamDto;
import com.ruisitech.bi.entity.portal.GridQuery;
import java.util.List;

public class GridQueryDto extends GridQuery {
    private JSONObject dset;
    private List<ParamDto> params;

    public GridQueryDto() {
    }

    public JSONObject getDset() {
        return this.dset;
    }

    public void setDset(JSONObject dset) {
        this.dset = dset;
    }

    public List<ParamDto> getParams() {
        return this.params;
    }

    public void setParams(List<ParamDto> params) {
        this.params = params;
    }
}
