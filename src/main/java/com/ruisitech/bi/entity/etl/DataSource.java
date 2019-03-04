//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.etl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruisi.ext.engine.dao.DatabaseHelper;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisitech.bi.entity.common.BaseEntity;
import com.ruisitech.bi.util.RSBIUtils;

public class DataSource extends BaseEntity {
    private String uname;
    private String psd;
    private String database;
    private String ipAddress;
    private Integer ipPort;
    private String linkType;
    private String name;
    private Integer id;

    public DataSource() {
    }

    @JsonIgnore
    public String getClazz() throws ExtConfigException {
        String linktype = this.linkType;
        DatabaseHelper db = ExtContext.getInstance().getDatabaseHelper(linktype);
        String clazz = db.getClazz();
        return clazz;
    }

    @JsonIgnore
    public String getLinkUrl() {
        String url = "";
        String ip = this.ipAddress;
        Integer port = this.ipPort;
        String dbname = this.database;
        if (this.linkType.equals("mysql")) {
            url = "jdbc:mysql://" + ip + ":" + port + "/" + dbname + "?useUnicode=true&characterEncoding=UTF8";
        } else if (this.linkType.equals("oracle")) {
            url = "jdbc:oracle:thin:@" + ip + ":" + port + "/" + dbname;
        } else if (this.linkType.equals("sqlser")) {
            url = "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + dbname;
        } else if (this.linkType.equals("db2")) {
            url = "jdbc:db2://" + ip + ":" + port + "/" + dbname;
        } else if (this.linkType.equals("hive")) {
            url = "jdbc:hive2://" + ip + ":" + port + "/" + dbname;
        } else if (this.linkType.equals("psql")) {
            url = "jdbc:postgresql://" + ip + ":" + port + "/" + dbname;
        } else if (this.linkType.equals("kylin")) {
            url = "jdbc:kylin://" + ip + ":" + port + "/" + dbname;
        }

        return url;
    }

    public String getUname() {
        return this.uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPsd() {
        return this.psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getIpPort() {
        return this.ipPort;
    }

    public void setIpPort(Integer ipPort) {
        this.ipPort = ipPort;
    }

    public String getLinkType() {
        return this.linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDatabase() {
        return this.database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void validate() {
        this.uname = RSBIUtils.htmlEscape(this.uname);
        this.database = RSBIUtils.htmlEscape(this.database);
        this.ipAddress = RSBIUtils.htmlEscape(this.ipAddress);
        this.linkType = RSBIUtils.htmlEscape(this.linkType);
        this.name = RSBIUtils.htmlEscape(this.name);
    }
}
