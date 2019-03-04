//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.etl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisitech.bi.entity.common.Result;
import com.ruisitech.bi.entity.etl.EtlTableMeta;
import com.ruisitech.bi.entity.etl.EtlTableMetaCol;
import com.ruisitech.bi.entity.etl.EtlTransform;
import com.ruisitech.bi.mapper.etl.EtlTableMetaColMapper;
import com.ruisitech.bi.mapper.etl.EtlTableMetaMapper;
import com.ruisitech.bi.mapper.etl.EtlTransformMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.ServletContext;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransformService extends EtlBaseService {
    private static final String splitWord = "a$idx";
    private static Logger log = Logger.getLogger(TransformService.class);
    @Autowired
    private EtlTransformMapper mapper;
    @Autowired
    private EtlTableMetaMapper tableMapper;
    @Autowired
    private EtlTableMetaColMapper colMapper;
    @Autowired
    private DaoHelper daoHelper;
    @Autowired
    private DataSourceService dsService;
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private ElasticService elaService;
    private String sysUser = RSBIUtils.getConstant("sysUser");
    private String dbtp = RSBIUtils.getConstant("dbName");

    public TransformService() {
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void deleteByPrimaryKey(Integer id) {
        EtlTransform tf = this.mapper.selectByPrimaryKey(this.sysUser, id);
        this.mapper.deleteByPrimaryKey(this.sysUser, id);
        EtlTableMeta meta = this.tableMapper.getTable(tf.getTableMetaId(), this.sysUser);
        if (meta != null) {
            this.tableMapper.deleteTable(meta);
            this.colMapper.delTableColByTableId(meta);
            if ("view".equals(tf.getSaveType())) {
                super.dropview(meta.getTableName(), this.daoHelper, this.dbtp);
            } else if ("temptable".equals(tf.getSaveType())) {
                super.droptable(meta.getTableName(), this.daoHelper, this.dbtp);
            }
        }

    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void insert(EtlTransform record) {
        EtlTableMeta table = this.saveOrUpdateTable(record, (Integer)null, (Integer)null);
        if (record.getIdType() == 2) {
            record.setId(this.mapper.maxtfid(this.sysUser));
        }

        record.setTableMetaId(table.getTableId());
        this.mapper.insert(record);
    }

    public String getTfConfig(Integer id) {
        return this.mapper.getTfConfig(this.sysUser, id);
    }

    public EtlTransform selectByPrimaryKey(Integer id) {
        return this.mapper.selectByPrimaryKey(this.sysUser, id);
    }

    public List<EtlTransform> listTf(String income) {
        return this.mapper.listTf(this.sysUser, income);
    }

    public List<EtlTransform> listEsTables() {
        List<EtlTransform> ls = this.mapper.listEsTables(this.sysUser);
        JSONArray indexs = this.elaService.listIndexs();
        if (indexs != null) {
            Iterator var3 = ls.iterator();

            while(var3.hasNext()) {
                EtlTransform t = (EtlTransform)var3.next();
                String tname = t.getPrimaryTable();
                t.setDataCount(this.findDocCount(tname, indexs));
            }
        }

        return ls;
    }

    private Long findDocCount(String idx, JSONArray indexs) {
        idx = idx.toLowerCase();
        Long ret = 0L;

        for(int i = 0; i < indexs.size(); ++i) {
            JSONObject obj = indexs.getJSONObject(i);
            if (obj.getString("index").equals(idx)) {
                ret = obj.getLong("docs.count");
                break;
            }
        }

        return ret;
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void update(EtlTransform record) {
        this.saveOrUpdateTable(record, record.getId(), record.getTableMetaId());
        this.mapper.update(record);
    }

    public Result runTf(EtlTransform record, ServletContext ctx) {
        Result res = new Result();
        JSONObject json = JSON.parseObject(record.getCfg());
        String tname = json.getString("tartable");
        super.droptable(tname, this.daoHelper, record.getDbName());
        super.dropview(tname, this.daoHelper, record.getDbName());
        String savetype = (String)json.get("savetype");
        if ("sql".equals(savetype)) {
            String sql = this.createSql(savetype, json, false, record.getTableMetaId());
            EtlTableMeta tb = new EtlTableMeta();
            tb.setTableId(record.getTableMetaId());
            tb.setTableSql(sql);
            this.tableMapper.updateMetaTable(tb);
            res.setResult(1);
            return res;
        } else {
            Connection conn = null;
            PreparedStatement ps = null;

            Result var10;
            try {
                conn = this.dsService.getConnection(ctx);
                String sql = this.createSql(savetype, json, false, record.getTableMetaId());
                log.info("\n" + sql);
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                if ("db2".equals(record.getDbName()) && "temptable".equals(savetype)) {
                    String isql = this.createSql(savetype, json, true, record.getTableMetaId());
                    Statement insertst = conn.createStatement();
                    insertst.executeUpdate(isql);
                    insertst.close();
                }

                res.setResult(1);
                var10 = res;
                return var10;
            } catch (SQLException var24) {
                log.error("数据转换出错。", var24);
                res.setResult(0);
                res.setMsg(var24.getMessage());
                var10 = res;
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException var23) {
                        var23.printStackTrace();
                    }
                }

                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException var22) {
                        var22.printStackTrace();
                    }
                }

            }

            return var10;
        }
    }

    public Integer maxtfid() {
        return this.mapper.maxtfid(this.sysUser);
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public EtlTableMeta saveOrUpdateTable(EtlTransform record, Integer transformId, Integer tableId) {
        JSONObject obj = JSON.parseObject(record.getCfg());
        String tname = obj.getString("tartable");
        String tnote = obj.getString("tartablename");
        EtlTableMeta table = new EtlTableMeta();
        table.setTableName(tname);
        table.setTableNote(tnote);
        table.setCrtUser(RSBIUtils.getLoginUserInfo().getUserId());
        table.setIncome("tf");
        Integer maxId;
        if (transformId == null && tableId == null) {
            if (table.getIdType() == 2) {
                maxId = this.tableMapper.maxTableId(this.sysUser);
                table.setTableId(maxId);
            }
        } else {
            table.setTableId(tableId);
        }

        if ("sql".equals(record.getSaveType())) {
            table.setTableSql((String)null);
        }

        if (transformId == null && tableId == null) {
            this.tableMapper.insertMetaTable(table);
            if (table.getIdType() == 1) {
                table.setTableId(this.tableMapper.maxTableId(this.sysUser) - 1);
            }
        } else {
            this.tableMapper.updateMetaTable(table);
        }

        this.colMapper.delTableColByTableId(table);
        maxId = null;
        if (table.getIdType() == 2) {
            maxId = this.colMapper.maxColId(this.sysUser);
        }

        JSONArray cols = obj.getJSONArray("dims");
        JSONArray kpis = obj.getJSONArray("kpis");
        boolean aggreData = true;
        if ((cols == null || cols.size() == 0) && (kpis == null || kpis.size() == 0)) {
            aggreData = false;
        }

        if (aggreData) {
            cols.addAll(kpis);

            for(int i = 0; i < cols.size(); ++i) {
                EtlTableMetaCol mcol = new EtlTableMetaCol();
                JSONObject col = cols.getJSONObject(i);
                String alias = (String)col.get("alias");
                mcol.setColName(alias != null && alias.length() != 0 ? alias : (String)col.get("col"));
                mcol.setValuestype(col.getString("vtype"));
                mcol.setTableId(table.getTableId());
                mcol.setColType(col.getString("vtype"));
                mcol.setColOrd(i);
                if (mcol.getIdType() == 2) {
                    mcol.setColId(maxId);
                    maxId = maxId + 1;
                }

                this.colMapper.insertMetaTableCol(mcol);
            }
        } else {
            Map<String, String> tables = this.initTables(obj);
            JSONArray joinInfo = (JSONArray)obj.get("joininfo");
            String master = obj.getString("master");
            StringBuffer sql = new StringBuffer("select ");
            Map<String, String> dynaMap = new CaseInsensitiveMap();
            JSONArray dynas = obj.getJSONArray("dynamic");

            int index;
            String tableName;
            for(index = 0; index < dynas.size(); ++index) {
                JSONObject dyna = dynas.getJSONObject(index);
                String expression = dyna.getString("expression");
                tableName = dyna.getString("name");
                sql.append(expression);
                sql.append(" as ");
                sql.append(tableName);
                sql.append(",");
                dynaMap.put(tableName, expression);
            }

            sql.append(" t0.* ");
            if (tables.size() > 1) {
                sql.append(",'' as a$idx ");
            }

            index = -1;
            Iterator var49 = tables.entrySet().iterator();

            Entry ent;
            while(var49.hasNext()) {
                ent = (Entry)var49.next();
                ++index;
                if (!master.equals(ent.getKey())) {
                    sql.append(", t" + index + ".* ");
                    if (index != tables.size() - 1) {
                        sql.append(",'' a$idx");
                    }
                }
            }

            sql.append("\n from ");
            sql.append(master + " " + (String)tables.get(master));
            sql.append(" ");
            var49 = tables.entrySet().iterator();

            while(var49.hasNext()) {
                ent = (Entry)var49.next();
                if (!master.equals(ent.getKey())) {
                    tableName = (String)ent.getKey();
                    sql.append(",");
                    sql.append(tableName);
                    sql.append(" ");
                    sql.append((String)ent.getValue());
                    sql.append(" ");
                }
            }

            sql.append("\n where 1=1 ");
            var49 = tables.entrySet().iterator();

            while(var49.hasNext()) {
                ent = (Entry)var49.next();
                List<JSONObject> joins = this.findJoinInfoById(joinInfo, (String)ent.getKey());

                for(int i = 0; joins != null && i < joins.size(); ++i) {
                    JSONObject join = (JSONObject)joins.get(i);
                    String col = join.getString("col");
                    String refKey = join.getString("refKey");
                    sql.append(" and t0." + col + " = " + (String)ent.getValue() + "." + refKey);
                }
            }

            String querysql = sql.toString().replaceAll("@", "'");
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                conn = this.dataSourceService.getConnection();
                ps = conn.prepareStatement(querysql);
                ps.setMaxRows(1);
                rs = ps.executeQuery();
                Map<String, Object> existCols = new HashMap();
                ResultSetMetaData meta = rs.getMetaData();
                Iterator<Entry<String, String>> it = tables.entrySet().iterator();
                String incomeTname = (String)((Entry)it.next()).getKey();

                for(int i = 0; i < meta.getColumnCount(); ++i) {
                    String name = meta.getColumnLabel(i + 1);
                    if ("a$idx".equalsIgnoreCase(name) || !existCols.containsKey(name)) {
                        existCols.put(name, "");
                        String tp = meta.getColumnTypeName(i + 1);
                        tp = this.columnType2java(tp);
                        EtlTableMetaCol mcol = new EtlTableMetaCol();
                        mcol.setColName(name);
                        mcol.setColType(tp);
                        mcol.setTableId(table.getTableId());
                        if ("a$idx".equalsIgnoreCase(name)) {
                            incomeTname = (String)((Entry)it.next()).getKey();
                        } else {
                            mcol.setIncomeTname(incomeTname);
                            if (dynaMap.get(name) != null) {
                                mcol.setExpression((String)dynaMap.get(name));
                            }

                            mcol.setColOrd(i);
                            if (mcol.getIdType() == 2) {
                                mcol.setColId(maxId);
                                maxId = maxId + 1;
                            }

                            this.colMapper.insertMetaTableCol(mcol);
                        }
                    }
                }

                rs.close();
            } catch (Exception var43) {
                throw new RuntimeException(var43);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException var42) {
                        var42.printStackTrace();
                    }
                }

                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException var41) {
                        var41.printStackTrace();
                    }
                }

            }
        }

        return table;
    }

    public Map<String, String> initTables(JSONObject json) {
        Map<String, String> tables = new LinkedHashMap();
        tables.put(json.getString("master"), "t0");
        JSONArray joins = (JSONArray)json.get("joininfo");

        for(int i = 0; joins != null && i < joins.size(); ++i) {
            JSONObject join = joins.getJSONObject(i);
            String tname = join.getString("ref");
            if (!tables.containsKey(tname)) {
                tables.put(tname, "t" + (i + 1));
            }
        }

        return tables;
    }

    public String createSql(String savetype, JSONObject json, boolean isInsertSql, Integer tableMetaId) {
        boolean aggreData = true;
        JSONArray dims = json.getJSONArray("dims");
        JSONArray kpis = json.getJSONArray("kpis");
        if ((dims == null || dims.size() == 0) && (kpis == null || kpis.size() == 0)) {
            aggreData = false;
        }

        Map<String, String> tables = this.initTables(json);
        JSONArray joinInfo = (JSONArray)json.get("joininfo");
        String tartable = json.getString("tartable");
        String master = json.getString("master");
        StringBuffer sql = new StringBuffer("");
        if (!isInsertSql) {
            if ("view".equals(savetype)) {
                sql.append("CREATE VIEW " + tartable + " as \n");
            } else if ("temptable".equals(savetype) && ("mysql".equals(this.dbtp) || "oracle".equals(this.dbtp) || "db2".equals(this.dbtp))) {
                sql.append("create table " + tartable + " as \n");
                if ("db2".equalsIgnoreCase(this.dbtp)) {
                    sql.append("(");
                }
            }
        } else {
            sql.append("insert into " + tartable);
            sql.append("\n");
        }

        sql.append(" select \n");
        int i;
        JSONObject dim;
        String tname;
        String col;
        String expression;
        String col;
        String refKey;
        if (aggreData) {
            for(i = 0; i < dims.size(); ++i) {
                dim = dims.getJSONObject(i);
                tname = dim.getString("tname");
                col = dim.getString("col");
                expression = (String)dim.get("alias");
                col = (String)dim.get("expression");
                if (col != null && col.length() != 0) {
                    sql.append(col);
                } else {
                    sql.append((String)tables.get(tname) + "." + col);
                }

                sql.append(" ");
                if (expression != null && expression.length() > 0) {
                    sql.append(expression);
                } else {
                    sql.append(col);
                }

                if (i != dims.size() - 1 || kpis.size() != 0) {
                    sql.append(",");
                }
            }

            for(i = 0; i < kpis.size(); ++i) {
                dim = kpis.getJSONObject(i);
                tname = dim.getString("tname");
                col = dim.getString("col");
                expression = (String)dim.get("alias");
                col = (String)dim.get("aggre");
                refKey = (String)dim.get("expression");
                if (refKey != null && refKey.length() != 0) {
                    sql.append(col + "(" + refKey + ") ");
                } else {
                    sql.append(col + "(" + (String)tables.get(tname) + "." + col + ") ");
                }

                if (expression != null && expression.length() > 0) {
                    sql.append(expression);
                } else {
                    sql.append(col);
                }

                if (i != kpis.size() - 1) {
                    sql.append(",");
                }
            }
        } else {
            List<EtlTableMetaCol> cols = this.colMapper.queryTableColumns(tableMetaId, (Integer)null, this.sysUser);

            for(int i = 0; i < cols.size(); ++i) {
                EtlTableMetaCol col = (EtlTableMetaCol)cols.get(i);
                if (col.getExpression() != null && col.getExpression().length() > 0) {
                    sql.append(col.getExpression());
                    sql.append(" as ");
                    sql.append(col.getColName());
                } else {
                    sql.append((String)tables.get(col.getIncomeTname()));
                    sql.append(".");
                    sql.append(col.getColName());
                }

                if (i != cols.size() - 1) {
                    sql.append(",");
                }
            }
        }

        if ("temptable".equals(savetype) && ("sqlser".equals(this.dbtp) || "sqlserver".equals(this.dbtp))) {
            sql.append(" into " + tartable);
        }

        sql.append("\n from ");
        sql.append(master + " " + (String)tables.get(master));
        sql.append(" ");
        Iterator var22 = tables.entrySet().iterator();

        Entry ent;
        while(var22.hasNext()) {
            ent = (Entry)var22.next();
            if (!master.equals(ent.getKey())) {
                tname = (String)ent.getKey();
                sql.append(",");
                sql.append(tname);
                sql.append(" ");
                sql.append((String)ent.getValue());
                sql.append(" ");
            }
        }

        sql.append("\n where 1=1 ");
        var22 = tables.entrySet().iterator();

        while(var22.hasNext()) {
            ent = (Entry)var22.next();
            List<JSONObject> joins = this.findJoinInfoById(joinInfo, (String)ent.getKey());

            for(int i = 0; joins != null && i < joins.size(); ++i) {
                JSONObject join = (JSONObject)joins.get(i);
                col = join.getString("col");
                refKey = join.getString("refKey");
                sql.append(" and t0." + col + " = " + (String)ent.getValue() + "." + refKey);
            }
        }

        if (aggreData && dims.size() > 0) {
            sql.append("\n group by ");

            for(i = 0; i < dims.size(); ++i) {
                dim = dims.getJSONObject(i);
                tname = dim.getString("tname");
                col = dim.getString("col");
                expression = (String)dim.get("expression");
                if (expression != null && expression.length() != 0) {
                    sql.append(expression);
                } else {
                    sql.append((String)tables.get(tname) + "." + col);
                }

                if (i != dims.size() - 1) {
                    sql.append(",");
                }
            }
        }

        if ("temptable".equals(savetype) && "db2".equalsIgnoreCase(this.dbtp) && !isInsertSql) {
            sql.append(") definition only");
        }

        return sql.toString().replaceAll("@", "'");
    }

    private List<JSONObject> findJoinInfoById(JSONArray joins, String tname) {
        List<JSONObject> ret = new ArrayList();

        for(int i = 0; joins != null && i < joins.size(); ++i) {
            JSONObject join = (JSONObject)joins.get(i);
            String ref = join.getString("ref");
            if (ref.equals(tname)) {
                ret.add(join);
            }
        }

        return ret;
    }
}
