//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.etl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruisitech.bi.entity.bireport.TableInfoVO;
import com.ruisitech.bi.entity.common.DSColumn;
import com.ruisitech.bi.entity.etl.DataSource;
import com.ruisitech.bi.service.bireport.TableCacheService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSetService extends EtlBaseService {
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private MetaService metaService;
    @Autowired
    private TableCacheService cacheService;
    private static Logger log = Logger.getLogger(DataSetService.class);

    public DataSetService() {
    }

    public List<Object> queryTopN(String sql, DataSource rsds, int n) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            List<Object> ret = new ArrayList();
            if (rsds == null) {
                conn = this.dataSourceService.getConnection();
            } else {
                conn = this.dataSourceService.getConnection(rsds);
            }

            ps = conn.prepareStatement(sql);
            ps.setMaxRows(n);
            rs = ps.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            List<String> cols = new ArrayList();

            int idx;
            for(idx = 0; idx < meta.getColumnCount(); ++idx) {
                String name = meta.getColumnLabel(idx + 1);
                cols.add(name);
            }

            ret.add(cols);

            for(idx = 0; rs.next() && idx <= n; ++idx) {
                Map<String, Object> m = new HashMap();
                Iterator var12 = cols.iterator();

                while(var12.hasNext()) {
                    String s = (String)var12.next();
                    m.put(s, rs.getString(s));
                }

                ret.add(m);
            }

            ArrayList var20 = ret;
            return var20;
        } catch (Exception var17) {
            log.error("sql:" + sql, var17);
            throw var17;
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (ps != null) {
                ps.close();
            }

            if (conn != null) {
                conn.close();
            }

        }
    }

    public List<DSColumn> queryMetaAndIncome(JSONObject dataset, DataSource rsds) throws Exception {
        List<String> tables = new ArrayList();
        JSONArray joinTabs = (JSONArray)dataset.get("joininfo");
        StringBuffer sb = new StringBuffer("select a0.* ");
        if (joinTabs != null && joinTabs.size() != 0) {
            sb.append(",'' a$idx ");
        }

        List<String> tabIds = new ArrayList();

        int i;
        for(i = 0; joinTabs != null && i < joinTabs.size(); ++i) {
            JSONObject join = joinTabs.getJSONObject(i);
            String tid = (String)join.get("tid");
            if (!tabIds.contains(tid)) {
                tabIds.add(tid);
            }
        }

        for(i = 0; i < tabIds.size(); ++i) {
            sb.append(", a" + (i + 1) + ".* ");
            if (i != tabIds.size() - 1) {
                sb.append(",'' a$idx");
            }
        }

        sb.append("from ");
        String masterId = (String)dataset.get("tid");
        TableInfoVO tinfo = this.cacheService.getTableInfo(new Integer(masterId));
        if (tinfo.getSql() != null && tinfo.getSql().length() > 0) {
            sb.append("(" + tinfo.getSql() + ") a0 ");
        } else {
            sb.append(tinfo.getTname() + " a0 ");
        }

        tables.add(tinfo.getTname());

        String tabId;
        TableInfoVO subTinfo;
        int i;
        for(i = 0; i < tabIds.size(); ++i) {
            tabId = (String)tabIds.get(i);
            subTinfo = this.cacheService.getTableInfo(new Integer(tabId));
            if (subTinfo.getSql() != null && subTinfo.getSql().length() > 0) {
                sb.append(", (" + subTinfo.getSql() + ")");
            } else {
                sb.append(", " + subTinfo.getTname());
            }

            sb.append(" a" + (i + 1) + " ");
            tables.add(subTinfo.getTname());
        }

        sb.append("where 1=2 ");

        for(i = 0; i < tabIds.size(); ++i) {
            tabId = (String)tabIds.get(i);
            subTinfo = this.cacheService.getTableInfo(new Integer(tabId));
            List<JSONObject> refs = this.getJoinInfoByTname(subTinfo.getTname(), joinTabs);

            for(int k = 0; k < refs.size(); ++k) {
                JSONObject t = (JSONObject)refs.get(k);
                sb.append("and a0." + t.getString("col") + "=a" + (i + 1) + "." + t.getString("refKey"));
                sb.append(" ");
            }
        }

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList var34;
        try {
            if (rsds == null) {
                conn = this.dataSourceService.getConnection();
            } else {
                conn = this.dataSourceService.getConnection(rsds);
            }

            ps = conn.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            List<DSColumn> cols = new ArrayList();
            String tname = (String)tables.get(0);
            int idx = 1;

            for(int i = 0; i < meta.getColumnCount(); ++i) {
                String name = meta.getColumnLabel(i + 1);
                String tp = meta.getColumnTypeName(i + 1);
                if ("a$idx".equalsIgnoreCase(name)) {
                    tname = (String)tables.get(idx);
                    ++idx;
                } else {
                    tp = this.columnType2java(tp);
                    DSColumn col = new DSColumn();
                    col.setDispName(name);
                    col.setName(name);
                    col.setType(tp);
                    col.setTname(tname);
                    col.setIsshow(true);
                    col.setIdx(i);
                    col.setExpression("");
                    cols.add(col);
                }
            }

            var34 = cols;
        } catch (SQLException var23) {
            log.error("sql:" + sb.toString(), var23);
            throw new RuntimeException(var23.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (ps != null) {
                ps.close();
            }

            if (conn != null) {
                conn.close();
            }

        }

        return var34;
    }

    private List<JSONObject> getJoinInfoByTname(String tname, JSONArray joins) {
        List<JSONObject> ret = new ArrayList();

        for(int i = 0; joins != null && i < joins.size(); ++i) {
            JSONObject join = joins.getJSONObject(i);
            String ref = join.getString("ref");
            if (ref.equals(tname)) {
                ret.add(join);
            }
        }

        return ret;
    }
}
