//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.bireport;

import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.entity.portal.LinkAcceptDto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TableQueryDto extends BaseEntity {
    private String id;
    private Integer tid;
    private String tname;
    private List<KpiDto> kpiJson;
    private List<DimDto> cols;
    private List<DimDto> rows;
    private List<ParamDto> params;
    private LinkAcceptDto linkAccept;
    private Map<String, Object> link;
    private List<Map<String, Object>> drillDim;

    public TableQueryDto() {
    }

    public List<KpiDto> getKpiJson() {
        return this.kpiJson;
    }

    public void setKpiJson(List<KpiDto> kpiJson) {
        this.kpiJson = kpiJson;
    }

    public List<DimDto> getCols() {
        return this.cols;
    }

    public void setCols(List<DimDto> cols) {
        this.cols = cols;
    }

    public List<DimDto> getRows() {
        return this.rows;
    }

    public void setRows(List<DimDto> rows) {
        this.rows = rows;
    }

    public List<ParamDto> getParams() {
        return this.params;
    }

    public void setParams(List<ParamDto> params) {
        this.params = params;
    }

    public int getKpiComputeType() {
        boolean sq = false;
        boolean tq = false;
        Iterator var3 = this.kpiJson.iterator();

        while(true) {
            String compute;
            do {
                do {
                    if (!var3.hasNext()) {
                        if (sq && tq) {
                            return 3;
                        }

                        if (sq) {
                            return 1;
                        }

                        if (tq) {
                            return 2;
                        }

                        return 0;
                    }

                    KpiDto kpi = (KpiDto)var3.next();
                    compute = kpi.getCompute();
                } while(compute == null);
            } while(compute.length() <= 0);

            String[] jss = compute.split(",");
            String[] var7 = jss;
            int var8 = jss.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                String js = var7[var9];
                if (!"sq".equals(js) && !"zje".equals(js) && !"hb".equals(js)) {
                    if ("tq".equals(js) || "tb".equals(js)) {
                        tq = true;
                    }
                } else {
                    sq = true;
                }
            }
        }
    }

    public Map<String, Object> getLink() {
        return this.link;
    }

    public void setLink(Map<String, Object> link) {
        this.link = link;
    }

    public List<Map<String, Object>> getDrillDim() {
        return this.drillDim;
    }

    public void setDrillDim(List<Map<String, Object>> drillDim) {
        this.drillDim = drillDim;
    }

    public List<DimDto> getDims() {
        List<DimDto> ret = new ArrayList();
        ret.addAll(this.cols);
        ret.addAll(this.rows);
        return ret;
    }

    public Integer getTid() {
        return this.tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkAcceptDto getLinkAccept() {
        return this.linkAccept;
    }

    public void setLinkAccept(LinkAcceptDto linkAccept) {
        this.linkAccept = linkAccept;
    }

    public String getTname() {
        return this.tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public void validate() {
    }
}
