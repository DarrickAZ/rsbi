//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.etl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruisitech.bi.entity.etl.JobConfig;
import com.ruisitech.bi.service.etl.JobConfigService;
import com.ruisitech.bi.service.etl.JobManagerService;
import com.ruisitech.bi.service.etl.Runner;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.RSBIUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/etl"})
public class JobController extends BaseController {
    @Autowired
    private JobConfigService service;
    @Autowired
    private JobManagerService jmService;

    public JobController() {
    }

    @RequestMapping({"/Job.action"})
    public String index() {
        return "etl/Job";
    }

    @RequestMapping({"/listJob.action"})
    @ResponseBody
    public Object list() {
        return this.service.listJobs();
    }

    @RequestMapping({"/listJobsteps.action"})
    @ResponseBody
    public Object listJobsteps() {
        return this.service.listJobsteps();
    }

    @RequestMapping(
        value = {"/saveJob.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object save(JobConfig job) {
        job.setCrtUser(RSBIUtils.getLoginUserInfo().getUserId());
        this.service.savejob(job);
        return super.buildSucces();
    }

    @RequestMapping(
        value = {"/updateJob.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object update(JobConfig job) {
        this.service.updatejob(job);
        return super.buildSucces();
    }

    @RequestMapping({"/delJob.action"})
    @ResponseBody
    public Object delete(Integer id) {
        this.service.deljob(id);
        return super.buildSucces();
    }

    @RequestMapping({"/getJob.action"})
    @ResponseBody
    public Object getJob(Integer id) {
        String str = this.service.getjobcfg(id);
        return str;
    }

    @RequestMapping({"/runJob.action"})
    @ResponseBody
    public Object runJob(Integer id, HttpServletRequest req) {
        this.jmService.runJob((JSONObject)null, id, req.getServletContext());
        return super.buildSucces();
    }

    @RequestMapping({"/stopJob.action"})
    @ResponseBody
    public Object stopJob(Integer id) {
        this.jmService.stopJob(id);
        return super.buildSucces();
    }

    @RequestMapping({"/execJob.action"})
    @ResponseBody
    public Object execJob(Integer id, HttpServletRequest req) {
        String cfg = this.service.getjobcfg(id);
        JSONObject json = (JSONObject)JSON.parse(cfg);
        Runner run = new Runner(json, String.valueOf(id), req.getServletContext());
        return run.startJob();
    }
}
