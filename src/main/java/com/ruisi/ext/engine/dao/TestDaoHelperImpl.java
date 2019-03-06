//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.dao;

import com.ruisi.ext.engine.util.DaoUtils;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

public class TestDaoHelperImpl implements DaoHelper {
    private JdbcTemplate a;
    private static Log b = LogFactory.getLog(DaoHelperImpl.class);

    public TestDaoHelperImpl(DataSource var1) {
        JdbcTemplate var2 = new JdbcTemplate();
        var2.setDataSource(var1);
        this.a = var2;
    }

    public List queryForList(String var1, Object[] var2) {
        if (DaoUtils.showLogs) {
            b.info(var1);
        }

        return this.a.queryForList(var1, var2);
    }

    public Object execute(ConnectionCallback var1) {
        return this.a.execute(var1);
    }

    public void execute(String var1) {
        if (DaoUtils.showLogs) {
            b.info(var1);
        }

        this.a.execute(var1);
    }

    public int queryForInt(String var1) {
        if (DaoUtils.showLogs) {
            b.info(var1);
        }

        return (Integer)this.a.queryForObject(var1, Integer.class);
    }

    public List queryForList(String var1) {
        if (DaoUtils.showLogs) {
            b.info(var1);
        }

        return this.a.queryForList(var1);
    }

    public Map queryForMap(String var1) {
        if (DaoUtils.showLogs) {
            b.info(var1);
        }

        return this.a.queryForMap(var1);
    }

    public Object queryForObject(String var1, Class var2) {
        if (DaoUtils.showLogs) {
            b.info(var1);
        }

        return this.a.queryForObject(var1, var2);
    }

    public Object execute(String var1, PreparedStatementCallback var2) {
        if (DaoUtils.showLogs) {
            b.info(var1);
        }

        return this.a.execute(var1, var2);
    }

    public Long queryForLong(String var1) {
        return (Long)this.a.queryForObject(var1, Long.class);
    }
}
