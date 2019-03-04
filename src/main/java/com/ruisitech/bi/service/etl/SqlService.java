//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.etl;

import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisitech.bi.entity.common.DSColumn;
import com.ruisitech.bi.entity.common.Result;
import com.ruisitech.bi.entity.etl.DataSource;
import com.ruisitech.bi.entity.etl.EtlTableMeta;
import com.ruisitech.bi.entity.etl.EtlTableMetaCol;
import com.ruisitech.bi.entity.etl.EtlTransform;
import com.ruisitech.bi.mapper.etl.EtlTableMetaColMapper;
import com.ruisitech.bi.mapper.etl.EtlTableMetaMapper;
import com.ruisitech.bi.mapper.etl.EtlTransformMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SqlService extends EtlBaseService {
    private static Logger log = Logger.getLogger(SqlService.class);
    @Autowired
    private EtlTransformMapper mapper;
    @Autowired
    private EtlTableMetaMapper tableMapper;
    @Autowired
    private EtlTableMetaColMapper colMapper;
    @Autowired
    private MetaService metaService;
    @Autowired
    private DaoHelper daoHelper;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public SqlService() {
    }

    public void testsql(final String sql) {
        this.daoHelper.execute(new ConnectionCallback<Object>() {
            public Object doInConnection(Connection conn) throws SQLException, DataAccessException {
                Statement st = conn.createStatement();
                boolean r = false;

                try {
                    st.execute(sql);
                    r = true;
                } catch (SQLException var8) {
                    throw var8;
                } finally {
                    st.close();
                }

                return r;
            }
        });
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void save(EtlTransform tf) throws Exception {
        tf.setIncome("sql");
        tf.setCrtuser(RSBIUtils.getLoginUserInfo().getUserId());
        EtlTableMeta table = this.saveOrUpdateTable(tf, JSONObject.fromObject(tf.getCfg()), (Integer)null);
        tf.setTableMetaId(table.getTableId());
        if (tf.getIdType() == 2) {
            tf.setId(this.mapper.maxtfid(this.sysUser));
        }

        this.mapper.insert(tf);
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void update(EtlTransform record) throws Exception {
        this.saveOrUpdateTable(record, JSONObject.fromObject(record.getCfg()), record.getTableMetaId());
        this.mapper.update(record);
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public EtlTableMeta saveOrUpdateTable(EtlTransform tf, JSONObject obj, Integer tid) throws Exception {
        String tname = obj.getString("tartable");
        String tnote = obj.getString("tartablename");
        EtlTableMeta table = new EtlTableMeta();
        table.setTableName(tname);
        table.setTableNote(tnote);
        table.setCrtUser(RSBIUtils.getLoginUserInfo().getUserId());
        table.setIncome("sql");
        if (tid == null) {
            if (table.getIdType() == 2) {
                table.setTableId(this.tableMapper.maxTableId(this.sysUser));
            }
        } else {
            table.setTableId(tid);
        }

        if ("sql".equals(tf.getSaveType())) {
            table.setTableSql(obj.getString("sql"));
        }

        if (tid == null) {
            this.tableMapper.insertMetaTable(table);
            if (table.getIdType() == 1) {
                table.setTableId(this.tableMapper.maxTableId(this.sysUser) - 1);
            }
        } else {
            this.tableMapper.updateMetaTable(table);
        }

        this.colMapper.delTableColByTableId(table);
        Integer maxId = null;
        if (table.getIdType() == 2) {
            maxId = this.colMapper.maxColId(this.sysUser);
        }

        String sql = obj.getString("sql");
        List<DSColumn> cols = this.metaService.queryTableCols(sql, (DataSource)null, false);

        for(int i = 0; i < cols.size(); ++i) {
            DSColumn col = (DSColumn)cols.get(i);
            EtlTableMetaCol mcol = new EtlTableMetaCol();
            mcol.setColName(col.getName());
            mcol.setColType(col.getType());
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

    public Result runSql(EtlTransform record) {
        Result res = new Result();

        try {
            JSONObject json = JSONObject.fromObject(record.getCfg());
            final String savetype = (String)json.get("savetype");
            String tname = json.getString("tartable");
            String sql = json.getString("sql");
            final String dbName = record.getDbName();
            super.droptable(tname, this.daoHelper, dbName);
            super.dropview(tname, this.daoHelper, dbName);
            String str1;
            if ("view".equals(savetype)) {
                sql = "CREATE VIEW " + tname + " as \n" + sql;
            } else if ("temptable".equals(savetype)) {
                if (!"mysql".equals(dbName) && !"oracle".equals(dbName) && !"db2".equals(dbName)) {
                    if ("sqlser".equals(dbName) || "sqlserver".equals(dbName)) {
                        int idx = sql.indexOf("from");
                        str1 = sql.substring(0, idx);
                        String str2 = sql.substring(idx, sql.length());
                        sql = str1 + " into " + tname + " " + str2;
                    }
                } else {
                    sql = "create table " + tname + " as \n" + ("db2".equals(dbName) ? "(" : "") + sql;
                }
            } else if ("sql".equals(savetype)) {
                res.setResult(1);
                return res;
            }

            if ("temptable".equals(savetype) && "db2".equals(dbName)) {
                sql = sql + " ) definition only";
            }

            log.info(sql);
            str1 = "insert into " + tname + " \n" + json.getString("sql");
            String finalSql = sql;
            String finalStr = str1;
            this.daoHelper.execute(new ConnectionCallback<Object>() {
                @Override
                public Object doInConnection(Connection conn) throws SQLException, DataAccessException {
                    try {
                        Statement st = conn.createStatement();
                        st.executeUpdate(finalSql);
                        st.close();
                        if ("db2".equals(dbName) && "temptable".equals(savetype)) {
                            Statement insertst = conn.createStatement();
                            insertst.executeUpdate(finalStr);
                            insertst.close();
                        }
                    } catch (SQLException var4) {
                        SqlService.log.error("sql执行出错。", var4);
                    }

                    return null;
                }
            });
            res.setResult(1);
        } catch (Exception var11) {
            log.error("执行出错。 ", var11);
            res.setResult(0);
            res.setMsg(var11.getMessage());
        }

        return res;
    }
}
