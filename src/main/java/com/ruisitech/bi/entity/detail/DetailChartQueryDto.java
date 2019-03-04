//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.detail;

import com.alibaba.fastjson.JSONObject;
import com.ruisitech.bi.entity.bireport.ChartQueryDto;
import com.ruisitech.bi.entity.bireport.KpiDto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DetailChartQueryDto extends ChartQueryDto {
    private List<KpiDto> useKpiJson;
    private JSONObject dset;

    public DetailChartQueryDto() {
    }

    public JSONObject getDset() {
        return this.dset;
    }

    public void setDset(JSONObject dset) {
        this.dset = dset;
    }

    public List<KpiDto> getKpiJson() {
        if (this.useKpiJson == null) {
            this.useKpiJson = new ArrayList();
            Iterator var1 = super.getKpiJson().iterator();

            while(var1.hasNext()) {
                KpiDto kpi = (KpiDto)var1.next();
                if (kpi != null) {
                    this.useKpiJson.add(kpi);
                }
            }
        }

        return this.useKpiJson;
    }
}
