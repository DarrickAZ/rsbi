//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.etl;

import com.ruisitech.bi.service.bireport.TableCacheService;
import com.ruisitech.bi.util.RSBIUtils;
import javax.servlet.ServletContext;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ClearTableCacheJob implements Job {
    private ServletContext ctx;

    public ClearTableCacheJob() {
    }

    public ClearTableCacheJob(ServletContext ctx) {
        this.ctx = ctx;
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap data = context.getJobDetail().getJobDataMap();
        this.ctx = (ServletContext)data.get("ctx");
        TableCacheService ser = (TableCacheService)RSBIUtils.getBean(this.ctx, TableCacheService.class);
        ser.removeOutTimeCache();
    }
}
