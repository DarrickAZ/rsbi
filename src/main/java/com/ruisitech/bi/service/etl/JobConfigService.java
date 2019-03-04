//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.etl;

import com.ruisitech.bi.entity.etl.JobConfig;
import com.ruisitech.bi.mapper.etl.EtlJoblogsMapper;
import com.ruisitech.bi.mapper.etl.JobConfigMapper;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobConfigService {
    private String sysUser = RSBIUtils.getConstant("sysUser");
    @Autowired
    private JobConfigMapper mapper;
    @Autowired
    private EtlJoblogsMapper logMapper;

    public JobConfigService() {
    }

    public List<JobConfig> listJobs() {
        return this.mapper.listJobs(this.sysUser);
    }

    public List<Map<String, Object>> listJobsteps() {
        return this.mapper.listJobsteps(this.sysUser);
    }

    public void savejob(JobConfig job) {
        if (job.getIdType() == 2) {
            job.setId(this.mapper.maxjobid(this.sysUser));
        }

        this.mapper.savejob(job);
    }

    public void updatejob(JobConfig job) {
        this.mapper.updatejob(job);
    }

    @Transactional(
        rollbackFor = {Exception.class}
    )
    public void deljob(Integer id) {
        this.mapper.deljob(this.sysUser, id);
        this.logMapper.deleteByJobId(id, this.sysUser);
    }

    public String getjobcfg(Integer id) {
        return this.mapper.getjobcfg(this.sysUser, id);
    }
}
