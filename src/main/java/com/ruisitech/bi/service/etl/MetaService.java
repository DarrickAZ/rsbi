//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.etl;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisitech.bi.entity.common.DSColumn;
import com.ruisitech.bi.entity.etl.DataSource;
import com.ruisitech.bi.entity.etl.ImpConfigDto;
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
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetaService extends EtlBaseService {
    @Autowired
    private DataSourceService dSource;
    @Autowired
    private DataSetService dsetService;
    @Autowired
    private ExcelService excelService;
    @Autowired
    private CsvService csvService;
    @Autowired
    private HadoopService hadoopService;
    private String dbName = RSBIUtils.getConstant("dbName");

    public MetaService() {
    }

    public List<Map<String, Object>> queryTables(String schema, DataSource dsource) throws Exception {
        List<Map<String, Object>> ret = null;
        Connection conn = null;

        try {
            if (dsource == null) {
                conn = this.dSource.getConnection();
            } else {
                conn = this.dSource.getConnection(dsource);
            }

            if (dsource != null && "kylin".equalsIgnoreCase(dsource.getLinkType())) {
                ret = this.queryTablesByMetadata(schema, dsource != null ? dsource.getLinkType() : this.dbName, conn);
            } else {
                ret = this.queryTablesBySql(schema, dsource != null ? dsource.getLinkType() : this.dbName, conn);
            }
        } catch (SQLException var9) {
            var9.printStackTrace();
            throw new RuntimeException("sql 执行报错.");
        } finally {
            if (conn != null) {
                conn.close();
            }

        }

        return ret;
    }

    public List<Map<String, Object>> queryTablesBySql(String schema, String dbName, Connection conn) {
        List<Map<String, Object>> ret = new ArrayList();
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            String qsql = null;
            if ("mysql".equals(dbName)) {
                qsql = ConstantsEngine.replace("select table_name from information_schema.tables where table_schema='$0'", schema == null ? this.getCurrentSchema(conn, dbName) : schema);
            } else if ("oracle".equals(dbName)) {
                String s = schema == null ? this.getCurrentSchema(conn, dbName) : schema;
                qsql = ConstantsEngine.replace("select table_name from all_tables t where t.owner='$0' union all select view_name from all_views t where t.OWNER = '$1' ", s, s);
            } else if ("sqlser".equals(dbName)) {
                qsql = "select name from sysobjects where xtype='U' order by name";
            } else if ("db2".equals(dbName)) {
                qsql = ConstantsEngine.replace("select name from sysibm.systables where type='T' and creator='$0'", schema == null ? this.getCurrentSchema(conn, dbName) : schema);
            } else if ("hive".equals(dbName)) {
                qsql = "show tables";
            } else if ("psql".equals(dbName)) {
                qsql = ConstantsEngine.replace("select tablename from pg_tables where tableowner='$0'", schema == null ? this.getCurrentSchema(conn, dbName) : schema);
            }

            ps = conn.prepareStatement(qsql);
            rs = ps.executeQuery();

            while(rs.next()) {
                Map<String, Object> m = new HashMap();
                String tname = rs.getString(1);
                m.put("id", tname);
                m.put("text", tname);
                m.put("iconCls", "icon-table");
                ret.add(m);
            }
        } catch (SQLException var17) {
            var17.printStackTrace();
            throw new RuntimeException("sql 执行报错.");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException var16) {
                    var16.printStackTrace();
                }
            }

        }

        return ret;
    }

    public List<Map<String, Object>> queryTablesByMetadata(String schema, String dbName, Connection conn) {
        List<Map<String, Object>> ret = new ArrayList();
        Object rs = null;

        try {
            ResultSet tbs = conn.getMetaData().getTables((String)null, schema, "%", new String[]{"TABLE"});

            while(tbs.next()) {
                Map<String, Object> m = new HashMap();
                String tname = tbs.getString("TABLE_NAME");
                m.put("id", tname);
                m.put("text", tname);
                m.put("iconCls", "icon-table");
                ret.add(m);
            }
        } catch (SQLException var16) {
            var16.printStackTrace();
            throw new RuntimeException("sql 执行报错.");
        } finally {
            if (rs != null) {
                try {
                    ((ResultSet)rs).close();
                } catch (SQLException var15) {
                    var15.printStackTrace();
                }
            }

        }

        return ret;
    }

    public String getCurrentSchema() {
        String dbName = RSBIUtils.getConstant("dbName");
        Connection conn = null;

        String var3;
        try {
            conn = this.dSource.getConnection();
            var3 = this.getCurrentSchema(conn, dbName);
        } catch (Exception var12) {
            var12.printStackTrace();
            throw new RuntimeException("sql 执行报错.");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException var11) {
                    var11.printStackTrace();
                }
            }

        }

        return var3;
    }

    public List<DSColumn> queryTableCols(String sql, DataSource ds, boolean filterRepeat) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List var8;
        try {
            if (ds == null) {
                conn = this.dSource.getConnection();
            } else {
                conn = this.dSource.getConnection(ds);
            }

            ps = conn.prepareStatement(sql);
            ps.setMaxRows(1);
            rs = ps.executeQuery();
            List<DSColumn> cols = this.copyValue(rs, filterRepeat);
            var8 = cols;
        } catch (SQLException var12) {
            var12.printStackTrace();
            throw new RuntimeException("sql 执行报错.");
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

        return var8;
    }

    public List<DSColumn> copyValue(ResultSet rs, boolean filterRepeat) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        List<DSColumn> cols = new ArrayList();

        for(int i = 0; i < meta.getColumnCount(); ++i) {
            String name = meta.getColumnLabel(i + 1);
            String tp = meta.getColumnTypeName(i + 1);
            tp = this.columnType2java(tp);
            DSColumn col = new DSColumn();
            col.setName(name);
            col.setType(tp);
            col.setIsshow(true);
            col.setIdx(i + 1);
            if (!"Date".equals(tp)) {
                col.setLength(meta.getPrecision(i + 1));
                if ("Double".equals(tp)) {
                    int scale = meta.getScale(i + 1);
                    col.setScale(scale == 0 ? null : scale);
                }
            }

            if (filterRepeat) {
                if (!this.colExist(name, cols)) {
                    cols.add(col);
                }
            } else {
                cols.add(col);
            }
        }

        return cols;
    }

    private boolean colExist(String colName, List<DSColumn> cols) {
        boolean exist = false;
        Iterator var4 = cols.iterator();

        while(var4.hasNext()) {
            DSColumn col = (DSColumn)var4.next();
            if (col.getName().equals(colName)) {
                exist = true;
                break;
            }
        }

        return exist;
    }

    public List<DSColumn> showImportColumns(ImpConfigDto dto) throws Exception {
        return this.showImportColumns(dto, false);
    }

    public List<DSColumn> showImportColumns(ImpConfigDto dto, boolean filterRepeat) throws Exception {
        if ("db".equals(dto.getImpType())) {
            return this.queryTableCols(dto.getSql(), dto.getDsource(), filterRepeat);
        } else if ("xls".equals(dto.getImpType())) {
            return this.excelService.listColumns(dto);
        } else if ("csv".equals(dto.getImpType())) {
            List<DSColumn> ret = this.csvService.listColumns(dto);
            if (dto.getNameinhead().equals("n")) {
                ret = this.csvService.createEmptyHead(ret.size());
            }

            return ret;
        } else if ("hadoop".equals(dto.getImpType())) {
            return this.hadoopService.readHdfsFileHead(dto);
        } else {
            throw new RuntimeException("类型不支持");
        }
    }

    public List<Object> showImportDatas(ImpConfigDto dto) throws Exception {
        List<Object> ls = null;
        if ("db".equals(dto.getImpType())) {
            ls = this.dsetService.queryTopN(dto.getSql(), dto.getDsource(), 100);
            if (ls.size() == 0) {
                throw new Exception("数据库表为空。");
            }
        } else if ("xls".equals(dto.getImpType())) {
            ls = this.excelService.queryTop100(dto);
            if (ls.size() == 0) {
                throw new Exception("文件为空。");
            }
        } else if ("csv".equals(dto.getImpType())) {
            ls = this.csvService.queryTopN(dto, 20);
            if (ls.size() == 0) {
                throw new Exception("文件为空。");
            }
        } else if ("hadoop".equals(dto.getImpType())) {
            ls = this.hadoopService.queryTopN(dto, 20);
        }

        return ls;
    }

    public List<Object> showImportOneData(ImpConfigDto dto) throws Exception {
        List<Object> ls = null;
        if ("db".equals(dto.getImpType())) {
            ls = this.dsetService.queryTopN(dto.getSql(), dto.getDsource(), 1);
        } else if ("xls".equals(dto.getImpType())) {
            ls = this.excelService.readXls(dto, "data", 1);
        } else if ("csv".equals(dto.getImpType())) {
            ls = this.csvService.readCsv(dto, "data", 1);
            if (ls.size() == 0) {
                throw new Exception("文件为空。");
            }
        }

        return ls;
    }

    public List<String> queryDbSchemas() {
        String dbName = RSBIUtils.getConstant("dbName");
        Connection conn = null;

        List var3;
        try {
            conn = this.dSource.getConnection();
            var3 = this.queryDbSchemas(conn, dbName);
        } catch (Exception var12) {
            var12.printStackTrace();
            throw new RuntimeException("sql 执行报错.");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException var11) {
                    var11.printStackTrace();
                }
            }

        }

        return var3;
    }

    public List<String> queryDbSchemas(Connection conn, String dbName) {
        List<String> ret = new ArrayList();
        String sql = null;
        if ("db2".equalsIgnoreCase(dbName)) {
            sql = "select schemaname from syscat.schemata";
        } else if ("mysql".equalsIgnoreCase(dbName)) {
            sql = "select schema_name from information_schema.schemata";
        } else if ("oracle".equals(dbName)) {
            sql = "select username from all_users";
        }

        try {
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery(sql);

            while(rs.next()) {
                ret.add(rs.getString(1));
            }

            rs.close();
            ps.close();
        } catch (SQLException var7) {
            var7.printStackTrace();
        }

        return ret;
    }
}
