//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.detail;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.util.DaoUtils;
import com.ruisi.ext.engine.view.context.grid.PageInfo;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisitech.bi.entity.bireport.TableInfoVO;
import com.ruisitech.bi.entity.common.RequestStatus;
import com.ruisitech.bi.entity.common.Result;
import com.ruisitech.bi.service.bireport.BaseCompService;
import com.ruisitech.bi.service.bireport.TableCacheService;
import com.ruisitech.bi.util.RSBIUtils;
import com.ruisitech.ext.service.DataControlInterface;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrossDetailService extends BaseCompService {
    @Autowired
    private TableCacheService cahceService;
    @Autowired
    private DataControlInterface dataControl;
    @Autowired
    private DaoHelper daoHelper;

    public CrossDetailService() {
    }

    @PostConstruct
    public void init() {
    }

    @PreDestroy
    public void destory() {
    }

    public Result detailDatas(String json) throws ExtConfigException {
        JSONObject obj = (JSONObject)JSON.parse(json);
        String sql = this.createSql(obj);
        PageInfo page = new PageInfo();
        page.setCurtpage((long)(obj.getInteger("page") - 1));
        page.setPagesize(obj.getInteger("rows"));
        List<Map<String, Object>> ls = DaoUtils.calPage(sql, page, this.daoHelper);
        return new Result(RequestStatus.SUCCESS.getStatus(), "操作成功", ls, page.getAllsize());
    }

    private String createSql(JSONObject obj) {
        Map<TableInfoVO, String> tableAlias = new HashMap();
        JSONObject dset = obj.getJSONObject("dset");
        JSONArray cols = dset.getJSONArray("cols");
        JSONArray joinTabs = dset.getJSONArray("joininfo");
        Integer tid = dset.getInteger("tid");
        tableAlias.put(this.cahceService.getTableInfo(tid), "a0");

        for(int i = 0; i < joinTabs.size(); ++i) {
            JSONObject tab = joinTabs.getJSONObject(i);
            Integer subTid = tab.getInteger("tid");
            TableInfoVO ref = this.cahceService.getTableInfo(subTid);
            if (!tableAlias.containsKey(ref)) {
                tableAlias.put(ref, "a" + (i + 1));
            }
        }

        StringBuffer sb = new StringBuffer("select ");

        int idx;
        String ret;
        for(idx = 0; idx < cols.size(); ++idx) {
            JSONObject col = cols.getJSONObject(idx);
            ret = col.getString("expression");
            String name = col.getString("name");
            sb.append(ret != null && ret.length() != 0 ? ret : name);
            sb.append(" as ");
            sb.append("\"" + name + "\"");
            if (idx != cols.size() - 1) {
                sb.append(",");
            }
        }

        idx = 0;
        sb.append(" from ");
        Set<Entry<TableInfoVO, String>> set = tableAlias.entrySet();

        for(Iterator var25 = set.iterator(); var25.hasNext(); sb.append(" ")) {
            Entry<TableInfoVO, String> m = (Entry)var25.next();
            if (((TableInfoVO)m.getKey()).getSql() != null && ((TableInfoVO)m.getKey()).getSql().length() > 0) {
                sb.append("(" + ((TableInfoVO)m.getKey()).getSql() + ") ");
            } else {
                sb.append(((TableInfoVO)m.getKey()).getTname());
            }

            sb.append(" " + (String)m.getValue());
            ++idx;
            if (idx != set.size()) {
                sb.append(",");
            }
        }

        sb.append("where 1=1 ");
        if (this.dataControl != null) {
            ret = this.dataControl.process(RSBIUtils.getLoginUserInfo(), this.cahceService.getTableInfo(tid).getTname());
            if (ret != null) {
                sb.append(ret + " ");
            }
        }

        JSONObject pms;
        for(int i = 0; i < joinTabs.size(); ++i) {
            pms = joinTabs.getJSONObject(i);
            String ref = pms.getString("ref");
            sb.append("and a0." + pms.getString("col") + "=" + this.getTableAliasByTname(tableAlias, ref) + "." + pms.getString("refKey"));
            sb.append(" ");
        }

        Object o = dset.getJSONArray("param");
        String col;
        String val;
        String val2;
        if (o != null) {
            JSONArray params = (JSONArray)o;

            for(int i = 0; i < params.size(); ++i) {
                JSONObject p = (JSONObject)params.get(i);
                col = p.getString("col");
                String tname = p.getString("tname");
                String type = p.getString("type");
                val = p.getString("val");
                val2 = p.getString("val2");
                sb.append(" and " + this.getTableAliasByTname(tableAlias, tname) + "." + col);
                sb.append(type);
                if (!"String".equalsIgnoreCase(type)) {
                    sb.append(val);
                } else {
                    sb.append("'");
                    sb.append(val);
                    sb.append("'");
                }

                if ("between".equals(type)) {
                    sb.append(" and ");
                    if (!"String".equalsIgnoreCase(type)) {
                        sb.append(val2);
                    } else {
                        sb.append("'");
                        sb.append(val2);
                        sb.append("'");
                    }
                }

                sb.append(" ");
            }
        }

        pms = obj.getJSONObject("pms");
        Iterator var32 = pms.entrySet().iterator();

        while(var32.hasNext()) {
            Entry<String, Object> ent = (Entry)var32.next();
            col = (String)ent.getKey();
            Object val = ent.getValue();
            JSONObject col = this.findColByName(col, cols);
            val = col.getString("type");
            if ("String".equals(val) || "Date".equals(val) || "Datetime".equals(val)) {
                val = "'" + val + "'";
            }

            val2 = col.getString("expression");
            String name = col.getString("name");
            sb.append(" and ");
            sb.append(val2 != null && val2.length() != 0 ? val2 : name);
            sb.append(" = ");
            sb.append(val);
        }

        return sb.toString();
    }

    private JSONObject findColByName(String name, JSONArray cols) {
        JSONObject ret = null;

        for(int i = 0; i < cols.size(); ++i) {
            JSONObject col = cols.getJSONObject(i);
            String cname = col.getString("name");
            if (name.equals(cname)) {
                ret = col;
            }
        }

        return ret;
    }
}
