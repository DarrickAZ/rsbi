package com.ruisi.ext.engine.dao;

import com.ruisi.ext.engine.util.DaoUtils;
import com.ruisitech.bi.mapper.etl.EtlTableMetaMapper;
import com.ruisitech.bi.util.RSBIUtils;
import com.zcy.zcmorefun.dubbo.service.DubheMetaService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private String sysUser = RSBIUtils.getConstant("sysUser");

    private static ThreadLocal<String> daoRsbiThreadLocal = new ThreadLocal<>();

    @Autowired(required = false)
    DubheMetaService dubheMetaService;

    @Autowired
    EtlTableMetaMapper etlTableMetaMapper;

    @Override
    public List queryForList(String sql) {
        if (DaoUtils.showLogs) {
            LOGGER.info("SQL >>> "+sql);
            LOGGER.info(Thread.currentThread().getName()+"Tid >>> "+daoRsbiThreadLocal.get());
        }
        //TODO: 查询数据仓库
        String tableId = daoRsbiThreadLocal.get();
        etlTableMetaMapper.selectTabIdByTableId(Integer.parseInt(tableId),this.sysUser);
        return dubheMetaService.analysisBySql(sql, daoRsbiThreadLocal.get());
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
