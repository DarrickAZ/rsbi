//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.etl;

import com.ruisitech.bi.service.frame.UserService;
import com.ruisitech.bi.util.RSBIUtils;
import javax.servlet.ServletContext;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ClearLoginErrCntJob implements Job {
    private ServletContext ctx;

    public ClearLoginErrCntJob() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap data = context.getJobDetail().getJobDataMap();
        this.ctx = (ServletContext)data.get("ctx");
        UserService ser = (UserService)RSBIUtils.getBean(this.ctx, UserService.class);
        ser.dealLockUsers();
    }
}
