//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.app;

import com.ruisitech.bi.entity.model.TableDimension;
import com.ruisitech.bi.mapper.model.TableMetaMapper;
import com.ruisitech.bi.service.model.TableDimensionService;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppCubeService {
    @Autowired
    private TableMetaMapper mapper;
    @Autowired
    private TableDimensionService dimService;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public AppCubeService() {
    }

    public List<Map<String, Object>> getKpi(Integer tableid) {
        List<Map<String, Object>> ls = this.mapper.listDsMeta(this.sysUser, tableid);
        List<Map<String, Object>> ret = new ArrayList();
        Iterator var4 = ls.iterator();

        while(true) {
            Map m;
            int col_type;
            do {
                if (!var4.hasNext()) {
                    return ret;
                }

                m = (Map)var4.next();
                col_type = new Integer(m.get("col_type").toString());
            } while(col_type != 2);

            m.remove("dateformat");
            ret.add(m);
            Iterator var7 = m.entrySet().iterator();

            while(var7.hasNext()) {
                Entry<String, Object> ment = (Entry)var7.next();
                Object ent = ment.getValue();
                if (ent == null) {
                    ment.setValue("");
                }
            }
        }
    }

    public List<Map<String, Object>> getDim(Integer tableid, String filterDateDim) {
        List<Map<String, Object>> ls = this.mapper.listDsMeta(this.sysUser, tableid);
        List<Map<String, Object>> ret = new ArrayList();
        Iterator var5 = ls.iterator();

        while(var5.hasNext()) {
            Map<String, Object> m = (Map)var5.next();
            m.put("tname", "");
            int col_type = new Integer(m.get("col_type").toString());
            if (col_type == 1) {
                String dtp = (String)m.get("dim_type");
                if ("y".equals(filterDateDim)) {
                    if (!"year".equals(dtp) && !"quarter".equals(dtp) && !"month".equals(dtp) && !"day".equals(dtp)) {
                        ret.add(m);
                    }
                } else {
                    ret.add(m);
                }
            }

            Iterator var11 = m.entrySet().iterator();

            while(var11.hasNext()) {
                Entry<String, Object> ment = (Entry)var11.next();
                Object ent = ment.getValue();
                if (ent == null) {
                    ment.setValue("");
                }
            }
        }

        return ret;
    }

    public Map<String, Object> dimFilter(Integer dimId, Integer tid) throws Exception {
        TableDimension d = this.dimService.queryDimCol(dimId, tid);
        List<Object> datas = this.dimService.paramFilter(d, (String)null);
        Map<String, Object> ret = new HashMap();
        String type = d.getType();
        if ("month".equalsIgnoreCase(type)) {
            ret.put("type", "month");
            Map start = new HashMap();
            start.put("name", "开始月份");
            start.put("options", datas);
            ret.put("startMonth", start);
            Map end = new HashMap();
            end.put("name", "结束月份");
            end.put("options", datas);
            ret.put("endMonth", end);
        } else if ("day".equalsIgnoreCase(type)) {
            Map firstData = (Map)datas.get(0);
            Map endData = (Map)datas.get(datas.size() - 1);
            Object min = firstData.get("id");
            Object max = (String)endData.get("id");
            ret.put("type", "day");
            Map start = new HashMap();
            start.put("name", "开始日期");
            start.put("min", min);
            ret.put("startDay", start);
            Map end = new HashMap();
            end.put("name", "结束日期");
            end.put("max", max);
            ret.put("endDay", end);
        } else {
            ret.put("type", "other");
            ret.put("name", "");
            ret.put("options", datas);
        }

        return ret;
    }
}
