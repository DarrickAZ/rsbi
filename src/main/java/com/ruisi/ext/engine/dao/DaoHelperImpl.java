//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.dao;

import com.ruisi.ext.engine.util.DaoUtils;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

public class DaoHelperImpl implements DaoHelper {
    protected JdbcTemplate jdbcTemplate;
    private static Log a = LogFactory.getLog(DaoHelperImpl.class);

    @Override
    public List queryForList(String var1, Object[] var2) {
        if (DaoUtils.showLogs) {
            a.info(var1);
        }

        return this.jdbcTemplate.queryForList(var1, var2);
    }

    @Override
    public Object execute(ConnectionCallback var1) {
        return this.jdbcTemplate.execute(var1);
    }

    @Override
    public void execute(String var1) {
        if (DaoUtils.showLogs) {
            a.info(var1);
        }

        this.jdbcTemplate.execute(var1);
    }

    public DaoHelperImpl(JdbcTemplate var1) {
        this.jdbcTemplate = var1;
    }

    public DaoHelperImpl() {
    }

    @Override
    public int queryForInt(String var1) {
        if (DaoUtils.showLogs) {
            a.info(var1);
        }

        return (Integer)this.jdbcTemplate.queryForObject(var1, Integer.class);
    }

    @Override
    public List queryForList(String var1) {
        if (DaoUtils.showLogs) {
            a.info(var1);
        }

        return this.jdbcTemplate.queryForList(var1);
    }



    @Override
    public Map queryForMap(String var1) {
        if (DaoUtils.showLogs) {
            a.info(var1);
        }

        return this.jdbcTemplate.queryForMap(var1);
    }

    @Override
    public Object queryForObject(String var1, Class var2) {
        if (DaoUtils.showLogs) {
            a.info(var1);
        }

        return this.jdbcTemplate.queryForObject(var1, var2);
    }

    @Override
    public Object execute(String var1, PreparedStatementCallback var2) {
        if (DaoUtils.showLogs) {
            a.info(var1);
        }

        return this.jdbcTemplate.execute(var1, var2);
    }

    @Override
    public Long queryForLong(String var1) {
        return (Long)this.jdbcTemplate.queryForObject(var1, Long.class);
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate var1) {
        this.jdbcTemplate = var1;
    }
}
