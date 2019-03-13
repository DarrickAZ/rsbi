package com.ruisi.ext.engine.dao;

import com.ruisi.ext.engine.util.DaoUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.PreparedStatementCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Darrick
 * @Date: 2019/3/12 15:41
 * @Description:
 */
public class DaoRsbiHelperImpl implements DaoHelper{

    private static Log LOGGER = LogFactory.getLog(DaoRsbiHelperImpl.class);

    private static ThreadLocal<String> daoRsbiThreadLocal = new ThreadLocal<>();

    @Override
    public List queryForList(String var1) {
        if (DaoUtils.showLogs) {
            LOGGER.info("DaoRsbiHelper queryForList ========================");
            LOGGER.info("Thread --- "+Thread.currentThread().getName());
            String s = daoRsbiThreadLocal.get();
            LOGGER.info("Tid >>> "+s);



        }
        return new ArrayList();
    }

    @Override
    public Map queryForMap(String var1) {
        return null;
    }

    @Override
    public Object queryForObject(String var1, Class var2) {
        return null;
    }

    @Override
    public Object execute(String var1, PreparedStatementCallback var2) {
        return null;
    }

    @Override
    public Long queryForLong(String var1) {
        return null;
    }

    @Override
    public void execute(String var1) {

    }

    @Override
    public int queryForInt(String var1) {
        return 0;
    }

    @Override
    public List queryForList(String var1, Object[] var2) {
        return null;
    }

    @Override
    public Object execute(ConnectionCallback var1) {
        return null;
    }

    public static ThreadLocal getDaoRsbiThreadLocal() {
        return daoRsbiThreadLocal;
    }

    public static void setDaoRsbiThreadLocal(ThreadLocal daoRsbiThreadLocal) {
        DaoRsbiHelperImpl.daoRsbiThreadLocal = daoRsbiThreadLocal;
    }
}
