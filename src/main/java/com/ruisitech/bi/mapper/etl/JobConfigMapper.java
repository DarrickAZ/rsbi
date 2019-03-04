//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.mapper.etl;

import com.ruisitech.bi.entity.etl.JobConfig;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface JobConfigMapper {
    List<JobConfig> listJobs(@Param("sysUser") String var1);

    List<Map<String, Object>> listJobsteps(@Param("sysUser") String var1);

    void savejob(JobConfig var1);

    void updatejob(JobConfig var1);

    void deljob(@Param("sysUser") String var1, @Param("id") Integer var2);

    String getjobcfg(@Param("sysUser") String var1, @Param("id") Integer var2);

    Integer maxjobid(@Param("sysUser") String var1);

    List<JobConfig> listRuningJobs(@Param("sysUser") String var1);

    void startState(@Param("sysUser") String var1, @Param("id") Integer var2);

    void stopState(@Param("sysUser") String var1, @Param("id") Integer var2);
}
