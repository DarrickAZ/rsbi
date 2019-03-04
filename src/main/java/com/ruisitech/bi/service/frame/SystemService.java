//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.frame;

import com.ruisitech.bi.service.etl.ElasticService;
import com.ruisitech.bi.util.RSBIUtils;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SystemService {
    @Value("${sso.url.userInfo.bytoken}")
    private String ssoUrl;
    private String dbName = RSBIUtils.getConstant("dbName");
    @Value("${mail.host}")
    private String mailHost;
    @Value("${mail.port}")
    private String mailPort;
    @Value("${mail.userName}")
    private String mailUsername;
    @Value("${rsbi.name}")
    private String rsbiName;
    @Value("${rsbi.version}")
    private String rsbiVersion;
    @Value("${rsbi.versionNumber}")
    private String rsbiVersionNumber;
    @Value("${rsbi.lastupdate}")
    private String rsbiLastupdate;
    @Value("${rsbi.net}")
    private String rsbiNet;
    @Value("${elasticsearch.url}")
    private String elasticsearch;
    @Autowired
    private ElasticService esService;

    public SystemService() {
    }

    public Map<String, Object> getSystemInfo() {
        Map<String, Object> ret = new HashMap();
        ret.put("dbName", this.dbName);
        ret.put("ssoUrl", this.ssoUrl);
        ret.put("mailHost", this.mailHost);
        ret.put("mailPort", this.mailPort);
        ret.put("mailUsername", this.mailUsername);
        ret.put("rsbiName", this.rsbiName);
        ret.put("rsbiVersion", this.rsbiVersion);
        ret.put("rsbiVersionNumber", this.rsbiVersionNumber);
        ret.put("rsbiLastupdate", this.rsbiLastupdate);
        ret.put("rsbiNet", this.rsbiNet);
        ret.put("jdk", System.getProperty("java.version"));
        ret.put("elasticsearch", this.elasticsearch);
        ret.put("esVersion", this.esService.getVersion());
        return ret;
    }
}
