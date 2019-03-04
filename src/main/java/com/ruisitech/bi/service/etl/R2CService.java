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
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class R2CService extends EtlBaseService {
    private static Logger log = Logger.getLogger(R2CService.class);
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
    private String sysUser = RSBIUtils.getConstant("sysUser");
    private String dbtp = RSBIUtils.getConstant("dbName");

    public R2CService() {
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void insert(EtlTransform tf) {
        EtlTableMeta table = this.saveOrUpdateTable(tf, (Integer)null, (Integer)null);
        if (tf.getIdType() == 2) {
            tf.setId(this.mapper.maxtfid(this.sysUser));
        }

        tf.setTableMetaId(table.getTableId());
        this.mapper.insert(tf);
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void update(EtlTransform tf) {
        this.saveOrUpdateTable(tf, tf.getId(), tf.getTableMetaId());
        this.mapper.update(tf);
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public EtlTableMeta saveOrUpdateTable(EtlTransform record, Integer transformId, Integer tableId) {
        JSONObject json = (JSONObject)JSON.parse(record.getCfg());
        String tname = json.getString("tartable");
        String tnote = json.getString("tartablename");
        EtlTableMeta table = new EtlTableMeta();
        table.setTableName(tname);
        table.setTableNote(tnote);
        table.setCrtUser(RSBIUtils.getLoginUserInfo().getUserId());
        table.setIncome(record.getIncome());
        Integer maxId;
        if (transformId == null && tableId == null) {
            if (table.getIdType() == 2) {
                maxId = this.tableMapper.maxTableId(this.sysUser);
                table.setTableId(maxId);
            }
        } else {
            table.setTableId(tableId);
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

        JSONArray groups = json.getJSONArray("groups");

        for(int i = 0; i < groups.size(); ++i) {
            JSONObject group = groups.getJSONObject(i);
            EtlTableMetaCol mcol = new EtlTableMetaCol();
            mcol.setColName(group.getString("col"));
            mcol.setColType(group.getString("type"));
            mcol.setTableId(table.getTableId());
            mcol.setColOrd(i);
            if (mcol.getIdType() == 2) {
                mcol.setColId(maxId);
                maxId = maxId + 1;
            }

            this.colMapper.insertMetaTableCol(mcol);
        }

        JSONArray tfcols = json.getJSONArray("tfcols");

        for(int i = 0; i < tfcols.size(); ++i) {
            JSONObject col = tfcols.getJSONObject(i);
            EtlTableMetaCol mcol = new EtlTableMetaCol();
            mcol.setColName(col.getString("alias"));
            mcol.setColType("Double");
            mcol.setTableId(table.getTableId());
            mcol.setColOrd(i);
            if (mcol.getIdType() == 2) {
                mcol.setColId(maxId);
                maxId = maxId + 1;
            }

            this.colMapper.insertMetaTableCol(mcol);
        }

        return table;
    }

    public Result runTf(EtlTransform record, ServletContext ctx) {
        Result res = new Result();
        JSONObject json = (JSONObject)JSON.parse(record.getCfg());
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

    public String createSql(String savetype, JSONObject json, boolean isInsertSql, Integer tableMetaId) {
        String tartable = json.getString("tartable");
        String primaryTable = json.getString("primaryTable");
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
        JSONArray groups = json.getJSONArray("groups");

        for(int i = 0; i < groups.size(); ++i) {
            JSONObject group = groups.getJSONObject(i);
            sql.append(group.get("col"));
            sql.append(",");
        }

        JSONArray tfcols = json.getJSONArray("tfcols");

        JSONObject group;
        int i;
        for(i = 0; i < tfcols.size(); ++i) {
            group = tfcols.getJSONObject(i);
            sql.append(group.get("aggre") + "(");
            sql.append("case when ");
            sql.append(group.get("col"));
            sql.append(" " + group.getString("logic") + " ");
            if ("String".equalsIgnoreCase(group.getString("valtype"))) {
                sql.append("'");
                sql.append(group.get("val"));
                sql.append("'");
            } else {
                sql.append(group.get("val"));
            }

            sql.append(" then ");
            sql.append(group.get("kpicol"));
            sql.append(" else 0 end");
            sql.append(") as ");
            sql.append(group.get("alias"));
            if (i != tfcols.size() - 1) {
                sql.append(",\n");
            }
        }

        sql.append("\nfrom ");
        sql.append(primaryTable);
        sql.append("\ngroup by ");

        for(i = 0; i < groups.size(); ++i) {
            group = groups.getJSONObject(i);
            sql.append(group.get("col"));
            if (i != groups.size() - 1) {
                sql.append(",");
            }
        }

        if ("temptable".equals(savetype) && "db2".equalsIgnoreCase(this.dbtp) && !isInsertSql) {
            sql.append(") definition only");
        }

        return sql.toString();
    }
}
