//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.app;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisitech.bi.entity.app.AppPushMsg;
import com.ruisitech.bi.entity.bireport.TableInfoVO;
import com.ruisitech.bi.service.bireport.TableCacheService;
import com.ruisitech.bi.util.RSBIUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.servlet.ServletContext;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class PushRunner implements Job {
    private JSONObject json;
    private ServletContext ctx;
    private DaoHelper daoHelper;
    private String channel;
    private Integer pushId;
    private Integer userId;
    private String pushType;
    private Logger log = Logger.getLogger(PushRunner.class);
    private String dataDate;
    private String kpiname;

    public PushRunner() {
    }

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        JobDataMap data = arg0.getJobDetail().getJobDataMap();
        this.json = (JSONObject)data.get("json");
        this.ctx = (ServletContext)data.get("ctx");
        this.channel = (String)data.get("channel");
        this.pushId = (Integer)data.get("pushId");
        this.userId = (Integer)data.get("userId");
        this.pushType = this.json.getString("pushType");
        this.daoHelper = (DaoHelper)RSBIUtils.getBean(this.ctx, DaoHelper.class);
        this.runJob();
    }

    public void runJob() {
        try {
            String sql = this.crtsql();
            List<Map<String, Object>> datas = this.daoHelper.queryForList(sql);
            AppPushMsg vo = new AppPushMsg();
            vo.setPushId(this.pushId);
            vo.setUserId(this.userId);
            vo.setDataDate(this.dataDate);
            vo.setTitle(this.dataDate + " 指标 " + this.kpiname + " 推送数据.");
            String msg = JSONArray.toJSONString(this.convertJson(datas));
            vo.setMsg(msg);
            vo.setPushType(this.pushType);
            vo.setChannel(this.channel);
            AppPushMsgService ser = (AppPushMsgService)RSBIUtils.getBean(this.ctx, AppPushMsgService.class);
            ser.addPushMsg(vo);
            if (this.channel != null && this.channel.length() > 0) {
                AndroidPushMsgToSingleDevice azdev = new AndroidPushMsgToSingleDevice();
                int ret = azdev.push(vo.getTitle(), this.channel);
                if (ret != 0) {
                    IOSPushNotificationToSingleDevice dev = new IOSPushNotificationToSingleDevice();
                    dev.push(vo.getTitle(), this.channel);
                }
            }
        } catch (Exception var9) {
            this.log.error("推送信息出错。", var9);
        }

    }

    public List<Object> convertJson(List<Map<String, Object>> datas) {
        List<Object> ret = new ArrayList();
        JSONObject dim = (JSONObject)this.json.get("dim");
        JSONArray kpis = (JSONArray)this.json.get("kpiJson");
        ArrayList data;
        String fmt;
        if (dim != null && !dim.isEmpty()) {
            data = new ArrayList();
            data.add("维度");
            data.add("指标");
            data.add("值");
            ret.add(data);

            for(int i = 0; i < datas.size(); ++i) {
                List<Object> data01 = new ArrayList();
                Map<String, Object> dt1 = (Map)datas.get(i);
                data01.add(dt1.get(dim.getString("alias")) == null ? "" : dt1.get(dim.getString("alias")));
                data01.add(this.kpiname);

                for(int j = 0; j < kpis.size(); ++j) {
                    JSONObject kpi = kpis.getJSONObject(j);
                    fmt = (String)kpi.get("alias");
                    if (fmt == null) {
                        fmt = "";
                    }

                    Double val = this.findKpiValue(dt1, fmt);
                    fmt = (String)kpi.get("fmt");
                    String unit = (String)kpi.get("unit");
                    if (fmt != null && fmt.length() > 0) {
                        DecimalFormat df = new DecimalFormat(fmt);
                        String v = df.format(val);
                        data01.add(v + (unit != null && unit.length() != 0 ? unit : ""));
                    } else {
                        data01.add(val + (unit != null && unit.length() != 0 ? unit : ""));
                    }
                }

                ret.add(data01);
            }
        } else {
            data = new ArrayList();
            data.add(this.kpiname);
            Map<String, Object> dt1 = null;
            if (datas != null && datas.size() > 0) {
                dt1 = (Map)datas.get(0);
            }

            for(int i = 0; i < kpis.size(); ++i) {
                JSONObject kpi = kpis.getJSONObject(i);
                String alias = (String)kpi.get("alias");
                if (alias == null) {
                    alias = "";
                }

                Double val = this.findKpiValue(dt1, alias);
                fmt = (String)kpi.get("fmt");
                String unit = (String)kpi.get("unit");
                if (fmt != null && fmt.length() > 0) {
                    DecimalFormat df = new DecimalFormat(fmt);
                    data.add((val == null ? "" : df.format(val)) + (unit != null && unit.length() != 0 ? unit : ""));
                } else {
                    data.add((val == null ? "" : val) + (unit != null && unit.length() != 0 ? unit : ""));
                }
            }

            ret.add(data);
        }

        return ret;
    }

    private Double findKpiValue(Map<String, Object> dt, String key) {
        if (dt == null) {
            return null;
        } else {
            Object kpi = dt.get(key);
            double kpiValue;
            if (kpi instanceof BigDecimal) {
                kpiValue = ((BigDecimal)kpi).doubleValue();
            } else if (kpi instanceof Integer) {
                kpiValue = ((Integer)kpi).doubleValue();
            } else if (kpi instanceof Long) {
                kpiValue = ((Long)kpi).doubleValue();
            } else if (kpi instanceof Float) {
                kpiValue = ((Float)kpi).doubleValue();
            } else {
                kpiValue = (Double)kpi;
            }

            return kpiValue;
        }
    }

    private String crtsql() {
        StringBuffer sb = new StringBuffer();
        JSONObject dim = (JSONObject)this.json.get("dim");
        JSONArray kpis = (JSONArray)this.json.get("kpiJson");
        sb.append("select ");
        if (dim != null && !dim.isEmpty()) {
            sb.append(dim.get("colname"));
            sb.append(" as  " + dim.get("alias"));
            sb.append(",");
        }

        for(int i = 0; i < kpis.size(); ++i) {
            JSONObject kpi = kpis.getJSONObject(i);
            sb.append(kpi.get("col_name"));
            this.kpiname = (String)kpi.get("kpi_name");
            sb.append(" as " + kpi.get("alias"));
            if (i != kpis.size() - 1) {
                sb.append(",");
            }
        }

        sb.append(" from " + this.getTable());
        sb.append(" where");
        Map<String, Object> param = new HashMap();
        param.put("tid", this.json.get("tid"));
        param.put("type", this.json.get("pushType"));
        param.put("sysUser", RSBIUtils.getConstant("sysUser"));
        AppPushMsgService ser = (AppPushMsgService)RSBIUtils.getBean(this.ctx, AppPushMsgService.class);
        Map<String, Object> dateDim = ser.getPushDateDim(param);
        Calendar cal = Calendar.getInstance();
        if ("month".equals(this.json.get("pushType"))) {
            cal.add(2, -1);
        } else if ("day".equals(this.json.get("pushType"))) {
            cal.add(6, -1);
        }

        String dateformat = (String)dateDim.get("dateformat");
        dateformat = dateformat.replaceAll("mm", "MM");
        this.dataDate = (new SimpleDateFormat(dateformat)).format(cal.getTime());
        sb.append(" " + dateDim.get("colname"));
        sb.append(" =");
        sb.append(" '" + this.dataDate + "'");
        if (dim != null && !dim.isEmpty()) {
            sb.append(" group by ");
            sb.append(dim.get("colname"));
        }

        String having = " having 1=1";

        for(int i = 0; i < kpis.size(); ++i) {
            JSONObject kpi = kpis.getJSONObject(i);
            String opt = (String)kpi.get("opt");
            if (opt != null && opt.length() > 0) {
                having = having + " and " + kpi.getString("alias") + opt;
                if ("between".equals(opt)) {
                    having = having + kpi.get("val1") + " and " + kpi.get("val2");
                } else {
                    having = having + kpi.get("val1");
                }
            }
        }

        return sb.toString() + having;
    }

    private String getTable() {
        TableCacheService cacheService = (TableCacheService)RSBIUtils.getBean(this.ctx, TableCacheService.class);
        TableInfoVO tinfo = cacheService.getTableInfo(this.json.getInteger("tid"));
        if (tinfo == null) {
            throw new ExtRuntimeException("无法找到数据立方体，分析主题对应的立方体可能已经被删除。");
        } else {
            String tname = tinfo.getTname();
            return tname;
        }
    }
}
