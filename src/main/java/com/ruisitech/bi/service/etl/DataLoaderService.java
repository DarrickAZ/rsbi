//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.etl;

import com.csvreader.CsvReader;
import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.view.builder.dsource.DataSourceBuilder;
import com.ruisitech.bi.entity.etl.DataImpRestDto;
import com.ruisitech.bi.entity.etl.EtlTableMeta;
import com.ruisitech.bi.entity.etl.EtlTableMetaCol;
import com.ruisitech.bi.entity.etl.ImpConfigColDto;
import com.ruisitech.bi.entity.etl.ImpConfigDto;
import com.ruisitech.bi.util.RSBIUtils;
import com.ruisitech.bi.util.XLSX2CSV;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import oracle.sql.DATE;
import oracle.sql.TIMESTAMP;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class DataLoaderService extends EtlBaseService {
    @Autowired
    private DataSourceService dSource;
    @Autowired
    private ExcelService excelService;
    @Autowired
    private DaoHelper daoHelper;
    @Autowired
    private EtlTableMetaService tableService;
    private String sysUser = RSBIUtils.getConstant("sysUser");
    private int rowCount;
    private int curCount;
    private int errCount;
    private boolean stop = false;
    private String errInfo;
    private int dataLabelIndex = -1;
    private static Logger log = Logger.getLogger(DataLoaderService.class);

    public DataLoaderService() {
    }

    public String check(DataImpRestDto dto) {
        String tname = dto.getTableName();
        EtlTableMeta tb = this.tableService.getTableByTname(tname);
        if (tb == null) {
            return "表 " + tname + " 不存在。";
        } else {
            List<EtlTableMetaCol> cols = this.tableService.queryTableColumns(tb.getTableId(), false);
            Iterator var5 = dto.getCols().iterator();

            ImpConfigColDto col;
            do {
                if (!var5.hasNext()) {
                    return null;
                }

                col = (ImpConfigColDto)var5.next();
            } while(this.existCol(col.getTargetCol(), cols));

            return "字段 " + col.getTargetCol() + " 不存在。";
        }
    }

    private boolean existCol(String col, List<EtlTableMetaCol> cols) {
        boolean ret = false;
        Iterator var4 = cols.iterator();

        while(var4.hasNext()) {
            EtlTableMetaCol c = (EtlTableMetaCol)var4.next();
            if (c.getColName().equals(col)) {
                ret = true;
                break;
            }
        }

        return ret;
    }

    public int restRun(DataImpRestDto dto, ServletContext ctx) throws Exception {
        int ret = 0;
        Connection targetConn = null;
        PreparedStatement exeps = null;

        try {
            targetConn = this.dSource.getConnection(ctx);
            Boolean truncate = dto.getTruncate();
            String sql;
            if (truncate != null && truncate) {
                sql = dto.getTableName();
                this.clearTableData(sql, targetConn, dto.getDbName());
            }

            targetConn.setAutoCommit(false);
            sql = this.createRestInsertSql(dto);
            exeps = targetConn.prepareStatement(sql);
            List<Map<String, Object>> dts = dto.getDatas();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for(Iterator var10 = dts.iterator(); var10.hasNext(); ++this.curCount) {
                Map<String, Object> dt = (Map)var10.next();

                for(int i = 1; i <= dto.getCols().size(); ++i) {
                    ImpConfigColDto col = (ImpConfigColDto)dto.getCols().get(i - 1);
                    Object val = dt.get(col.getTargetCol());
                    String tp = col.getType();
                    if ("Date".equalsIgnoreCase(tp)) {
                        Date rq = null;
                        if (val != null) {
                            rq = new Date(sdf.parse(val.toString()).getTime());
                        }

                        exeps.setDate(i, rq);
                    } else {
                        exeps.setObject(i, val);
                    }
                }

                exeps.addBatch();
            }

            exeps.executeBatch();
            targetConn.commit();
        } catch (Exception var27) {
            var27.printStackTrace();
            int ret = true;
            throw var27;
        } finally {
            if (exeps != null) {
                try {
                    exeps.close();
                } catch (SQLException var26) {
                    var26.printStackTrace();
                }
            }

            if (targetConn != null) {
                try {
                    targetConn.setAutoCommit(true);
                    targetConn.close();
                } catch (SQLException var25) {
                    var25.printStackTrace();
                }
            }

        }

        return ret;
    }

    public int run(ImpConfigDto dto, ServletContext ctx) {
        int ret = 0;
        Connection conn = null;
        this.curCount = 0;
        Connection targetConn = null;
        PreparedStatement exeps = null;

        try {
            if (ctx == null) {
                targetConn = this.dSource.getConnection();
            } else {
                targetConn = this.dSource.getConnection(ctx);
            }

            boolean truncate = dto.getTruncate();
            String insert;
            if (truncate) {
                insert = dto.getTargettable();
                this.clearTableData(insert, targetConn, dto.getDbName());
            }

            targetConn.setAutoCommit(false);
            insert = this.createInsertSql(dto);
            exeps = targetConn.prepareStatement(insert);
            String impType = dto.getImpType();
            String dateLabel;
            int idx;
            java.util.Date val;
            if (impType != null && !"db".equals(impType)) {
                if ("xls".equals(impType)) {
                    if (dto.getRealPath().endsWith("xls")) {
                        List<Object> dts = this.excelService.readXls(dto, "data", -1);
                        this.rowCount = dts.size();
                        int start = 0;
                        if ("y".equals(dto.getNameinhead())) {
                            start = 1;
                        }

                        for(int i = start; i < dts.size(); ++i) {
                            if (this.stop) {
                                this.stop = false;
                                break;
                            }

                            Map dt = (Map)dts.get(i);
                            this.copydate(dto, (ResultSet)null, dt, insert, targetConn, exeps);
                            if (i != 0 && i % 1000 == 0) {
                                exeps.executeBatch();
                                targetConn.commit();
                            }
                        }
                    } else {
                        OPCPackage p = OPCPackage.open(dto.getRealPath(), PackageAccess.READ);
                        XLSX2CSV xlsx2csv = new XLSX2CSV(p, dto, targetConn, exeps, insert, this);
                        xlsx2csv.process(-1);
                        p.close();
                    }
                } else if ("csv".equals(impType)) {
                    dateLabel = dto.getRealPath();
                    FileInputStream input = null;
                    InputStreamReader read = null;
                    BufferedReader bufferedReader = null;
                    CsvReader reader = null;

                    try {
                        input = new FileInputStream(dateLabel);
                        read = new InputStreamReader(input, dto.getEncode());
                        bufferedReader = new BufferedReader(read);
                        char cr = true;
                        char cr;
                        if ("\t".equals(dto.getSplitWord())) {
                            cr = '\t';
                        } else if (",".equals(dto.getSplitWord())) {
                            cr = ',';
                        } else if ("@".equals(dto.getSplitWord())) {
                            cr = '@';
                        } else {
                            cr = dto.getSplitWord().toCharArray()[0];
                        }

                        reader = new CsvReader(bufferedReader, cr);
                        idx = 0;

                        label1107:
                        while(true) {
                            do {
                                if (!reader.readRecord()) {
                                    break label1107;
                                }

                                ++idx;
                            } while("y".equals(dto.getNameinhead()) && idx == 0);

                            Map<String, Object> m = new HashMap();
                            String[] cols = reader.getValues();

                            for(int l = 0; l < cols.length; ++l) {
                                m.put(String.valueOf(l), cols[l]);
                            }

                            this.copydate(dto, (ResultSet)null, m, insert, targetConn, exeps);
                            if (idx % 1000 == 0) {
                                exeps.executeBatch();
                                targetConn.commit();
                            }

                            if (this.stop) {
                                this.stop = false;
                                break;
                            }
                        }
                    } finally {
                        if (reader != null) {
                            reader.close();
                        }

                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }

                        if (read != null) {
                            read.close();
                        }

                        if (input != null) {
                            input.close();
                        }

                    }
                } else if ("hadoop".equals(impType)) {
                    Configuration conf = new Configuration();
                    conf.set("fs.defaultFS", dto.getHdfsAddress());
                    conf.set("fs.hdfs.impl", DistributedFileSystem.class.getName());
                    FileSystem fs = FileSystem.get(conf);
                    RemoteIterator files = fs.listFiles(new Path(dto.getHdfsAddress() + dto.getPath()), false);

                    while(files.hasNext()) {
                        FSDataInputStream is = null;

                        try {
                            LocatedFileStatus file = (LocatedFileStatus)files.next();
                            is = fs.open(file.getPath());
                            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                            BufferedReader br = new BufferedReader(isr);
                            val = null;

                            String line;
                            for(long curIdx = 1L; (line = br.readLine()) != null; ++curIdx) {
                                if (this.stop) {
                                    this.stop = false;
                                    break;
                                }

                                Map<String, Object> m = new HashMap();
                                String[] cols = line.split(dto.getSplitWord());

                                for(int l = 0; l < cols.length; ++l) {
                                    m.put(String.valueOf(l), cols[l]);
                                }

                                this.copydate(dto, (ResultSet)null, m, insert, targetConn, exeps);
                                exeps.addBatch();
                                ++this.curCount;
                                if (curIdx % 1000L == 0L) {
                                    exeps.executeBatch();
                                    targetConn.commit();
                                }
                            }

                            br.close();
                            isr.close();
                        } catch (Exception var66) {
                            var66.printStackTrace();
                        } finally {
                            is.close();
                        }
                    }
                }
            } else {
                conn = this.dSource.getConnection(dto.getDsource());
                dateLabel = dto.getDatelabel();
                java.util.Date labelVal = null;
                java.util.Date dt = null;
                if (dateLabel != null && dateLabel.length() > 0) {
                    dt = this.getServiceDatetime(dto.getDsource().getLinkType(), conn);
                    labelVal = this.getDateLabel(dto.getImpid(), dto.getDatelabel());
                }

                String sql = dto.getSql();
                PreparedStatement ps = conn.prepareStatement(sql, 1003, 1007);
                if (!"hive".equals(dto.getDsource().getLinkType())) {
                    ps.setFetchSize(10000);
                    ps.setFetchDirection(1000);
                }

                ResultSet rs = ps.executeQuery();
                idx = 0;

                while(rs.next()) {
                    if (this.stop) {
                        this.stop = false;
                        break;
                    }

                    if (dateLabel != null && dateLabel.length() > 0 && labelVal != null) {
                        val = (java.util.Date)DataSourceBuilder.getResultSetValue(rs, this.getColumnIndex(rs, dateLabel));
                        if (val.after(labelVal)) {
                            this.copydate(dto, rs, (Map)null, insert, targetConn, exeps);
                            ++idx;
                        }
                    } else {
                        this.copydate(dto, rs, (Map)null, insert, targetConn, exeps);
                        ++idx;
                    }

                    if (idx != 0 && idx % 1000 == 0) {
                        exeps.executeBatch();
                        targetConn.commit();
                    }
                }

                if (dateLabel != null && dateLabel.length() > 0) {
                    if (labelVal != null) {
                        this.updateDateLable(dto.getImpid(), dto.getDatelabel(), dt);
                    } else {
                        this.insertDateLabel(dto.getImpid(), dto.getDatelabel(), dt);
                    }
                }

                ps.close();
                rs.close();
            }

            exeps.executeBatch();
            targetConn.commit();
        } catch (Exception var68) {
            this.errInfo = var68.getMessage();
            var68.printStackTrace();
            ret = 1;
        } finally {
            try {
                targetConn.setAutoCommit(true);
            } catch (SQLException var64) {
                var64.printStackTrace();
            }

            if (exeps != null) {
                try {
                    exeps.close();
                } catch (SQLException var63) {
                    var63.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException var62) {
                    var62.printStackTrace();
                }
            }

            if (targetConn != null) {
                try {
                    targetConn.close();
                } catch (SQLException var61) {
                    var61.printStackTrace();
                }
            }

        }

        return ret;
    }

    private int getColumnIndex(ResultSet rs, String column) throws SQLException {
        int idx = this.dataLabelIndex;
        if (idx == -1) {
            int size = rs.getMetaData().getColumnCount();

            for(int i = 1; i <= size; ++i) {
                if (rs.getMetaData().getColumnName(i).equalsIgnoreCase(column)) {
                    idx = i;
                    this.dataLabelIndex = i;
                    break;
                }
            }
        }

        return idx;
    }

    public void insertDateLabel(final String dloadId, final String dateLabel, final java.util.Date dt) {
        this.daoHelper.execute("insert into " + this.sysUser + ".etl_date_label(dload_id, date_label, label_val) values(?,?,?)", new PreparedStatementCallback<Object>() {
            public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, dloadId);
                ps.setString(2, dateLabel);
                ps.setTimestamp(3, new Timestamp(dt.getTime()));
                ps.executeUpdate();
                return null;
            }
        });
    }

    public void updateDateLable(final String dloadId, final String dateLabel, final java.util.Date dt) {
        this.daoHelper.execute("update " + this.sysUser + ".etl_date_label set label_val = ? where dload_id = ? and date_label = ?", new PreparedStatementCallback<Object>() {
            public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setTimestamp(1, new Timestamp(dt.getTime()));
                ps.setString(2, dloadId);
                ps.setString(3, dateLabel);
                ps.executeUpdate();
                return null;
            }
        });
    }

    private String createInsertSql(ImpConfigDto dto) {
        String tname = dto.getTargettable();
        StringBuffer sb = new StringBuffer();
        sb.append("insert into ");
        sb.append(tname);
        sb.append("(");
        List<ImpConfigColDto> cols = dto.getCols();

        int i;
        ImpConfigColDto col;
        for(i = 0; i < cols.size(); ++i) {
            col = (ImpConfigColDto)cols.get(i);
            String zd = col.getCol();
            sb.append(zd);
            if (i != cols.size() - 1) {
                sb.append(",");
            }
        }

        sb.append(")");
        sb.append(" values(");

        for(i = 0; i < cols.size(); ++i) {
            col = (ImpConfigColDto)cols.get(i);
            sb.append("?");
            if (i != cols.size() - 1) {
                sb.append(",");
            }
        }

        sb.append(")");
        return sb.toString();
    }

    private String createRestInsertSql(DataImpRestDto dto) {
        String tname = dto.getTableName();
        StringBuffer sb = new StringBuffer();
        sb.append("insert into ");
        sb.append(tname);
        sb.append("(");
        List<ImpConfigColDto> cols = dto.getCols();

        int i;
        for(i = 0; i < cols.size(); ++i) {
            ImpConfigColDto col = (ImpConfigColDto)cols.get(i);
            String zd = col.getCol();
            sb.append(zd);
            if (i != cols.size() - 1) {
                sb.append(",");
            }
        }

        sb.append(")");
        sb.append(" values(");

        for(i = 0; i < cols.size(); ++i) {
            sb.append("?");
            if (i != cols.size() - 1) {
                sb.append(",");
            }
        }

        sb.append(")");
        return sb.toString();
    }

    public void copydate(ImpConfigDto dto, ResultSet rs, Map<String, Object> datas, String insertSql, Connection conn, PreparedStatement exeps) {
        if (rs != null || datas != null && datas.size() != 0) {
            List cols = dto.getCols();

            try {
                int index = 1;

                for(int i = 0; i < cols.size(); ++i) {
                    ImpConfigColDto col = (ImpConfigColDto)cols.get(i);
                    String link = col.getLink();
                    String tp = col.getType();
                    if (link != null && link.length() > 0) {
                        Object val;
                        if (rs != null && datas == null) {
                            val = getPreparedStatementValue(rs, new Integer(link) + 1, tp);
                            if (val != null && val instanceof String) {
                                val = ((String)val).trim();
                            }

                            setPreparedStatementValue(exeps, index, val);
                        } else if (rs == null && datas != null) {
                            val = datas.get(link);
                            if (val instanceof String && ("".equals(val) || "NULL".equalsIgnoreCase((String)val))) {
                                val = null;
                            }

                            if (val != null && val instanceof String) {
                                val = ((String)val).trim();
                            }

                            if (!"csv".equals(dto.getImpType()) && !"hadoop".equals(dto.getImpType())) {
                                setPreparedStatementValue(exeps, index, val);
                            } else {
                                setPreparedStatementValue(exeps, index, val, tp);
                            }
                        }
                    } else {
                        exeps.setObject(index, (Object)null);
                    }

                    ++index;
                }

                exeps.addBatch();
                ++this.curCount;
            } catch (Exception var14) {
                ++this.errCount;
                var14.printStackTrace();
            }

        }
    }

    public static Object getPreparedStatementValue(ResultSet rs, Integer idx, String tp) throws SQLException {
        try {
            Object ret = null;
            if ("Int".equalsIgnoreCase(tp)) {
                ret = rs.getLong(idx);
            } else if ("Double".equalsIgnoreCase(tp)) {
                ret = rs.getDouble(idx);
            } else if ("String".equalsIgnoreCase(tp)) {
                ret = rs.getString(idx);
            } else if ("date".equalsIgnoreCase(tp)) {
                ret = rs.getDate(idx);
            } else if ("datetime".equalsIgnoreCase(tp)) {
                ret = rs.getTimestamp(idx);
            }

            return ret;
        } catch (Exception var4) {
            log.error("字段 " + idx + "取值错误。" + var4.getMessage());
            return null;
        }
    }

    public static void setPreparedStatementValue(PreparedStatement ps, int index, Object value, String tp) throws SQLException {
        if (value == null) {
            ps.setObject(index, (Object)null);
        } else {
            if ("Int".equalsIgnoreCase(tp)) {
                ps.setInt(index, Integer.parseInt(value.toString()));
            } else if ("Double".equalsIgnoreCase(tp)) {
                ps.setDouble(index, Double.parseDouble(value.toString()));
            } else if ("String".equalsIgnoreCase(tp)) {
                ps.setString(index, value.toString());
            } else if ("date".equalsIgnoreCase(tp)) {
                ps.setString(index, value.toString());
            } else if ("datetime".equalsIgnoreCase(tp)) {
                ps.setString(index, value.toString());
            }

        }
    }

    public static void setPreparedStatementValue(PreparedStatement ps, int index, Object value) throws SQLException {
        if (value == null) {
            ps.setObject(index, (Object)null);
        } else {
            if (value instanceof Blob) {
                ps.setObject(index, (Object)null);
            } else if (value instanceof Clob) {
                ps.setObject(index, (Object)null);
            } else if (value instanceof java.util.Date) {
                ps.setTimestamp(index, new Timestamp(((java.util.Date)value).getTime()));
            } else if (value instanceof TIMESTAMP) {
                ps.setTimestamp(index, ((TIMESTAMP)value).timestampValue());
            } else if (value instanceof DATE) {
                ps.setDate(index, ((DATE)value).dateValue());
            } else if (value instanceof Timestamp) {
                ps.setTimestamp(index, (Timestamp)value);
            } else if (value instanceof Date) {
                ps.setDate(index, (Date)value);
            } else {
                ps.setObject(index, value);
            }

        }
    }

    private void clearTableData(String tname, Connection conn, String dbName) throws SQLException {
        String sql = null;
        if ("db2".equalsIgnoreCase(dbName)) {
            sql = "alter table " + tname + " activate not logged initially with empty table";
        } else {
            sql = "truncate table " + tname;
        }

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
    }

    public java.util.Date getDateLabel(String impId, String datalabel) {
        java.util.Date ret = null;
        String sql = "select label_val  from " + this.sysUser + ".etl_date_label where dload_id = '" + impId + "' and date_label = '" + datalabel + "'";
        ret = (java.util.Date)this.daoHelper.execute(sql, new PreparedStatementCallback<Object>() {
            public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ResultSet rs = ps.executeQuery();

                java.util.Date r;
                for(r = null; rs.next(); r = (java.util.Date)DataSourceBuilder.getResultSetValue(rs, 1)) {
                }

                rs.close();
                return r;
            }
        });
        return ret;
    }

    public String getErrInfo() {
        return this.errInfo;
    }

    public int getCurCount() {
        return this.curCount;
    }

    public void stopImport() {
        this.stop = true;
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public boolean isStop() {
        return this.stop;
    }
}
