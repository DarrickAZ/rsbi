//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.etl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruisitech.bi.entity.etl.JobConfig;
import com.ruisitech.bi.mapper.etl.JobConfigMapper;
import com.ruisitech.bi.service.app.PushRunner;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletContext;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobManagerService {
    @Autowired
    private JobConfigMapper jobMapper;
    @Autowired
    private JobConfigService service;
    private static SchedulerFactory sf = null;
    private static Scheduler sched = null;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public JobManagerService() {
    }

    public void shutdown() {
        try {
            if (sched != null) {
                sched.shutdown();
            }
        } catch (SchedulerException var2) {
            var2.printStackTrace();
        }

    }

    public void stopJob(Integer id) {
        String cfg = this.service.getjobcfg(id);
        JSONObject j = (JSONObject)JSON.parse(cfg);
        JobKey key = new JobKey(String.valueOf(id), j.get("id").toString());
        TriggerKey triggerKey = new TriggerKey(String.valueOf(id), j.get("id").toString());

        try {
            sched.deleteJob(key);
            sched.unscheduleJob(triggerKey);
        } catch (SchedulerException var7) {
            var7.printStackTrace();
        }

        this.jobMapper.stopState(this.sysUser, id);
    }

    public JobKey runPushJob(Integer pushID, String channel_id, JSONObject json, ServletContext ctx, Integer userId) {
        try {
            String id = "p" + pushID;
            String pushType = json.getString("pushType");
            JSONObject jobJson = (JSONObject)json.get("job");
            String period = this.createPushPeriod(jobJson, pushType);
            JobDetail exist = sched.getJobDetail(new JobKey(id, "appPushJob"));
            if (exist != null) {
                return exist.getKey();
            } else {
                JobDetail job = JobBuilder.newJob(PushRunner.class).withIdentity(id, "appPushJob").build();
                job.getJobDataMap().put("json", json);
                job.getJobDataMap().put("ctx", ctx);
                job.getJobDataMap().put("channel", channel_id);
                job.getJobDataMap().put("pushId", pushID);
                job.getJobDataMap().put("userId", userId);
                CronTrigger trigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity(id, "appPushTrigger").withSchedule(CronScheduleBuilder.cronSchedule(period)).build();
                sched.scheduleJob(job, trigger);
                JobKey key = job.getKey();
                return key;
            }
        } catch (SchedulerException var14) {
            var14.printStackTrace();
            return null;
        }
    }

    private String createPushPeriod(JSONObject job, String pushType) {
        String hour = job.getString("hour");
        String minute = job.getString("minute");
        if (pushType.equals("day")) {
            return "0 " + minute + " " + hour + " ? * * ";
        } else {
            return "month".equals(pushType) ? "0 " + minute + " " + hour + " " + job.getString("day") + " * ?" : null;
        }
    }

    public void stopPushJob(Integer id) {
        JobKey key = new JobKey("p" + id, "appPushJob");
        TriggerKey triggerKey = new TriggerKey("p" + id, "appPushTrigger");

        try {
            sched.deleteJob(key);
            sched.unscheduleJob(triggerKey);
        } catch (SchedulerException var5) {
            var5.printStackTrace();
        }

    }

    public JobKey runJob(JSONObject json, Integer jobId, ServletContext ctx) {
        try {
            if (json == null) {
                String cfg = this.service.getjobcfg(jobId);
                json = (JSONObject)JSON.parse(cfg);
            }

            JobDetail job = JobBuilder.newJob(Runner.class).withIdentity(String.valueOf(jobId), json.get("id").toString()).build();
            job.getJobDataMap().put("json", json);
            job.getJobDataMap().put("jobId", jobId);
            job.getJobDataMap().put("ctx", ctx);
            String period = this.createPeriod(json);
            CronTrigger trigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity(String.valueOf(jobId), json.get("id").toString()).withSchedule(CronScheduleBuilder.cronSchedule(period)).build();
            sched.scheduleJob(job, trigger);
            JobKey key = job.getKey();
            this.jobMapper.startState(this.sysUser, jobId);
            return key;
        } catch (SchedulerException var8) {
            var8.printStackTrace();
            return null;
        }
    }

    private String createPeriod(JSONObject job) {
        String period = job.getString("period");
        String hour = job.getString("hour");
        String minute = job.getString("minute");
        String hourstep = job.getString("hourstep");
        if (period.equals("day")) {
            return "0 " + minute + " " + hour + " ? * * ";
        } else if ("week".equals(period)) {
            return "0 " + minute + " " + hour + " ? * " + job.getString("week");
        } else if ("month".equals(period)) {
            return "0 " + minute + " " + hour + " " + job.getString("day") + " * ?";
        } else {
            return "hour".equals(period) ? "0 0 */" + hourstep + " * * ?" : null;
        }
    }

    public void startAllJob(ServletContext ctx) {
        List<JobConfig> ls = this.jobMapper.listRuningJobs(this.sysUser);

        for(int i = 0; i < ls.size(); ++i) {
            JobConfig job = (JobConfig)ls.get(i);
            String pctx = job.getCfg();
            Integer id = job.getId();
            JSONObject json = (JSONObject)JSON.parse(pctx);
            this.runJob(json, id, ctx);
        }

    }

    public void runClearTableCacheJob(ServletContext ctx) {
        try {
            String id = UUID.randomUUID().toString();
            JobDetail job = JobBuilder.newJob(ClearTableCacheJob.class).withIdentity(id, "sysjobs").build();
            job.getJobDataMap().put("ctx", ctx);
            String period = "0 */30 * * * ?";
            CronTrigger trigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity(id, "sysjobs").withSchedule(CronScheduleBuilder.cronSchedule(period)).build();
            sched.scheduleJob(job, trigger);
        } catch (SchedulerException var6) {
            var6.printStackTrace();
        }

    }

    public void runClearLoginErrCnt(ServletContext ctx) {
        try {
            String id = UUID.randomUUID().toString();
            JobDetail job = JobBuilder.newJob(ClearLoginErrCntJob.class).withIdentity(id, "sysjobs").build();
            job.getJobDataMap().put("ctx", ctx);
            String period = "0 */1 * * * ?";
            CronTrigger trigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity(id, "sysjobs").withSchedule(CronScheduleBuilder.cronSchedule(period)).build();
            sched.scheduleJob(job, trigger);
        } catch (SchedulerException var6) {
            var6.printStackTrace();
        }

    }

    static {
        if (sf == null) {
            sf = new StdSchedulerFactory();
        }

        try {
            if (sched == null) {
                sched = sf.getScheduler();
                sched.start();
            }
        } catch (SchedulerException var1) {
            var1.printStackTrace();
        }

    }
}
