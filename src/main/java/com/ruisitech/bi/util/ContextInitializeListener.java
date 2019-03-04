//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.util;

import com.ruisi.ext.runtime.tag.CalendarTag;
import com.ruisitech.bi.entity.bireport.Calendar;
import com.ruisitech.bi.mapper.bireport.CalendarMapper;
import com.ruisitech.bi.service.app.PushCenterService;
import com.ruisitech.bi.service.etl.JobManagerService;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.ibatis.session.SqlSession;

public class ContextInitializeListener implements ServletContextListener {
    public ContextInitializeListener() {
    }

    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext sctx = sce.getServletContext();
        this.destoryJobs(sctx);
        this.destoryDrivers();
    }

    private void destoryJobs(ServletContext sctx) {
        JobManagerService ser = (JobManagerService)RSBIUtils.getBean(sctx, JobManagerService.class);
        ser.shutdown();

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException var4) {
            var4.printStackTrace();
        }

    }

    private void destoryDrivers() {
        Enumeration drivers = DriverManager.getDrivers();

        while(drivers.hasMoreElements()) {
            Driver driver = (Driver)drivers.nextElement();

            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException var4) {
                var4.printStackTrace();
            }
        }

    }

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sct = sce.getServletContext();
        this.initFestival(sct);
        this.initJobs(sct);
        this.initAppPush(sct);
        this.runSysJobs(sct);
    }

    private void initFestival(ServletContext sct) {
        SqlSession s = null;

        try {
            s = RSBIUtils.getSqlSession(sct);
            CalendarMapper mapper = (CalendarMapper)s.getMapper(CalendarMapper.class);
            String sysUser = RSBIUtils.getConstant("sysUser");
            List<Calendar> festivals = mapper.listFestival(sysUser);
            Iterator var6 = festivals.iterator();

            while(var6.hasNext()) {
                Calendar cal = (Calendar)var6.next();
                CalendarTag.pushFestival(new String[]{cal.getDay(), cal.getFestival()});
            }
        } finally {
            RSBIUtils.closeSqlSession(s);
        }

    }

    private void initJobs(ServletContext sct) {
        JobManagerService ser = (JobManagerService)RSBIUtils.getBean(sct, JobManagerService.class);
        ser.startAllJob(sct);
    }

    private void initAppPush(ServletContext sct) {
        PushCenterService ser = (PushCenterService)RSBIUtils.getBean(sct, PushCenterService.class);
        ser.initPushConfig(sct);
    }

    private void runSysJobs(ServletContext sct) {
        JobManagerService ser = (JobManagerService)RSBIUtils.getBean(sct, JobManagerService.class);
        ser.runClearTableCacheJob(sct);
        ser.runClearLoginErrCnt(sct);
    }
}
