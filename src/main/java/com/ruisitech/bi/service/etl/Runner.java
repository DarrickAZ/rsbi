//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.etl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruisitech.bi.entity.common.Result;
import com.ruisitech.bi.entity.etl.EtlJoblogs;
import com.ruisitech.bi.entity.etl.EtlTransform;
import com.ruisitech.bi.entity.etl.ImpConfigDto;
import com.ruisitech.bi.mapper.etl.ConfigMapper;
import com.ruisitech.bi.mapper.etl.EtlJoblogsMapper;
import com.ruisitech.bi.mapper.etl.EtlTransformMapper;
import com.ruisitech.bi.util.RSBIUtils;
import javax.servlet.ServletContext;
import org.apache.ibatis.session.SqlSession;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

public class Runner implements Job {
    private JSONObject json;
    private String jobId;
    private ServletContext ctx;
    private String sysUser = RSBIUtils.getConstant("sysUser");

    public Runner() {
    }

    public Runner(JSONObject json, String jobId, ServletContext ctx) {
        this.json = json;
        this.jobId = jobId;
        this.ctx = ctx;
    }

    public void execute(JobExecutionContext arg0) {
        JobDataMap data = arg0.getJobDetail().getJobDataMap();
        this.json = (JSONObject)data.get("json");
        this.jobId = String.valueOf((Integer)data.get("jobId"));
        this.ctx = (ServletContext)data.get("ctx");
        this.startJob();
    }

    public Result startJob() {
        Result rt = new Result();
        long st = System.currentTimeMillis();
        int rowcnt = 0;
        StringBuffer result = new StringBuffer("");
        JSONObject job = this.json;
        SqlSession session = null;

        Result var9;
        try {
            session = RSBIUtils.getSqlSession(this.ctx);
            JSONArray nodes = job.getJSONArray("nodes");

            String name;
            for(int i = 0; i < nodes.size(); ++i) {
                JSONObject node = nodes.getJSONObject(i);
                name = node.getString("name");
                String id = node.getString("id");
                String tp = (String)node.get("type");
                if (tp == null) {
                    tp = "dataLoad";
                }

                String cfg;
                if ("dataLoad".equals(tp)) {
                    ConfigMapper mapper = (ConfigMapper)session.getMapper(ConfigMapper.class);
                    cfg = mapper.getConfig(new Integer(id), this.sysUser);
                    if (cfg == null) {
                        result.append(name + " 数据导入不存在！ \n");
                        break;
                    }

                    JSONObject load = (JSONObject)JSON.parse(cfg);
                    DataLoaderService ser = (DataLoaderService)RSBIUtils.getBean(this.ctx, DataLoaderService.class);
                    int ret = ser.run((ImpConfigDto)JSONObject.toJavaObject(load, ImpConfigDto.class), this.ctx);
                    if (ret != 0) {
                        result.append(name + " 执行失败！ \n");
                    }

                    rowcnt = ser.getCurCount();
                } else {
                    EtlTransformMapper mapper;
                    Result ret;
                    if ("aggre".equals(tp)) {
                        mapper = (EtlTransformMapper)session.getMapper(EtlTransformMapper.class);
                        cfg = mapper.getTfConfig(this.sysUser, new Integer(id));
                        if (cfg == null) {
                            result.append(name + " 表关联不存在！ \n");
                            break;
                        }

                        TransformService ser = (TransformService)RSBIUtils.getBean(this.ctx, TransformService.class);
                        EtlTransform record = new EtlTransform();
                        record.setCfg(cfg);
                        ret = ser.runTf(record, this.ctx);
                        if (ret.getResult() == 0) {
                            result.append(name + " 执行失败！" + ret.getMsg() + " \n");
                        }
                    } else {
                        EtlTransform record;
                        if ("sql".equals(tp)) {
                            mapper = (EtlTransformMapper)session.getMapper(EtlTransformMapper.class);
                            cfg = mapper.getTfConfig(this.sysUser, new Integer(id));
                            if (cfg == null) {
                                result.append(name + " SQL脚本不存在！ \n");
                                break;
                            }

                            record = new EtlTransform();
                            record.setCfg(cfg);
                            SqlService ser = (SqlService)RSBIUtils.getBean(this.ctx, SqlService.class);
                            ret = ser.runSql(record);
                            if (ret.getResult() == 0) {
                                result.append(name + " 执行失败！ \n" + ret.getMsg());
                            }
                        } else if ("es".equals(tp)) {
                            ElasticService serv = (ElasticService)RSBIUtils.getBean(this.ctx, ElasticService.class);
                            serv.setRowCount(0);
                            Result ret = serv.run(new Integer(id), this.ctx);
                            if (ret.getResult() == 0) {
                                result.append(name + " 执行失败！ \n" + ret.getMsg());
                            }

                            rowcnt = serv.getRowCount();
                        } else if ("r2c".equals(tp)) {
                            mapper = (EtlTransformMapper)session.getMapper(EtlTransformMapper.class);
                            cfg = mapper.getTfConfig(this.sysUser, new Integer(id));
                            record = new EtlTransform();
                            record.setCfg(cfg);
                            R2CService serv = (R2CService)RSBIUtils.getBean(this.ctx, R2CService.class);
                            ret = serv.runTf(record, this.ctx);
                            if (ret.getResult() == 0) {
                                result.append(name + " 执行失败！ \n" + ret.getMsg());
                            }
                        }
                    }
                }
            }

            long end = System.currentTimeMillis();
            name = result.toString();
            int jobstate = 0;
            if (name.length() == 0) {
                name = "执行成功！";
                jobstate = 1;
            }

            long costtime = (end - st) / 1000L;
            EtlJoblogs log = new EtlJoblogs();
            log.setJobId(new Integer(this.jobId));
            log.setPeriod(job.getString("period"));
            log.setJobResult(name);
            log.setCosttime((int)costtime);
            log.setRowcnt(rowcnt);
            log.setJobState(Integer.valueOf(jobstate));
            EtlJoblogsMapper mapper = (EtlJoblogsMapper)session.getMapper(EtlJoblogsMapper.class);
            mapper.insert(log);
            if (name.length() == 0) {
                rt.setResult(0);
            } else {
                rt.setMsg(name);
                rt.setResult(1);
            }

            Result var38 = rt;
            return var38;
        } catch (Exception var22) {
            var22.printStackTrace();
            rt.setResult(0);
            rt.setMsg(var22.getMessage());
            var9 = rt;
        } finally {
            RSBIUtils.closeSqlSession(session);
        }

        return var9;
    }
}
