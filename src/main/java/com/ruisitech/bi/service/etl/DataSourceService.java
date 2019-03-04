//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.etl;

import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisitech.bi.entity.common.RequestStatus;
import com.ruisitech.bi.entity.common.Result;
import com.ruisitech.bi.entity.etl.DataSource;
import com.ruisitech.bi.mapper.etl.DataSourceMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Service
public class DataSourceService {
    public static final String showTables_mysql = "select table_name from information_schema.tables where table_schema='$0'";
    public static final String showTables_oracle = "select table_name from all_tables t where t.owner='$0' union all select view_name from all_views t where t.OWNER = '$1' ";
    public static final String showTables_sqlser = "select name from sysobjects where xtype='U' order by name";
    public static final String showTables_db2 = "select name from sysibm.systables where type='T' and creator='$0'";
    public static final String showTables_hive = "show tables";
    public static final String showTables_psql = "select tablename from pg_tables where tableowner='$0'";
    public static final String[] dataTypes = new String[]{"String", "Int", "Double", "Date", "Datetime"};
    private Logger log = Logger.getLogger(DataSourceService.class);
    private String sysUser = RSBIUtils.getConstant("sysUser");
    @Autowired
    private DataSourceMapper mapper;

    public DataSourceService() {
    }

    public List<DataSource> listDataSource() {
        return this.mapper.listDataSource(this.sysUser);
    }

    public void insertDataSource(DataSource ds) {
        ds.setId(this.maxDataSourceid());
        this.mapper.insertDataSource(ds);
    }

    public void deleteDataSource(DataSource ds) {
        this.mapper.delDataSource(ds);
    }

    public Integer maxDataSourceid() {
        return this.mapper.maxDataSourceid(this.sysUser);
    }

    public Connection getConnection(DataSource ds) throws Exception {
        try {
            Connection conn = null;
            Class.forName(ds.getClazz()).newInstance();
            conn = DriverManager.getConnection(ds.getLinkUrl(), ds.getUname(), ds.getPsd());
            return conn;
        } catch (Exception var3) {
            throw var3;
        }
    }

    public Connection getConnection(ServletContext ctx) throws SQLException {
        javax.sql.DataSource ds = (javax.sql.DataSource)WebApplicationContextUtils.getWebApplicationContext(ctx).getBean("dataSource");
        return ds.getConnection();
    }

    public Connection getConnection() throws SQLException {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        ServletContext ctx = request.getServletContext();
        javax.sql.DataSource ds = (javax.sql.DataSource)WebApplicationContextUtils.getWebApplicationContext(ctx).getBean("dataSource");
        return ds.getConnection();
    }

    public Result testDataSource(DataSource ds) throws ExtConfigException {
        Result ret = new Result();
        String clazz = ExtContext.getInstance().getDatabaseHelper(ds.getLinkType()).getClazz();
        Connection conn = null;

        try {
            Class.forName(clazz).newInstance();
            conn = DriverManager.getConnection(ds.getLinkUrl(), ds.getUname(), ds.getPsd());
            if (conn != null) {
                ret.setResult(RequestStatus.SUCCESS.getStatus());
            } else {
                ret.setResult(RequestStatus.FAIL_FIELD.getStatus());
            }
        } catch (Exception var14) {
            this.log.error("JDBC测试出错。", var14);
            ret.setResult(RequestStatus.FAIL_FIELD.getStatus());
            ret.setMsg(var14.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException var13) {
                    var13.printStackTrace();
                }
            }

        }

        return ret;
    }
}
