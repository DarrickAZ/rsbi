//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.init.config;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.util.HashMap;
import java.util.Map;

public class ServiceConfig {
    private Class a;
    private String b;
    private Map c = new HashMap();

    public ServiceConfig() {
    }

    public String[] getInterRefs(String var1) {
        if (var1 == null || var1.length() == 0) {
            var1 = "execute";
        }

        return ((ResultConfig)this.c.get(var1)).getInteRefs();
    }

    public String getMVID(String var1) throws ExtConfigException {
        if (var1 == null || var1.length() == 0) {
            var1 = "execute";
        }

        ResultConfig var2 = (ResultConfig)this.c.get(var1);
        String var3;
        if (var2 == null) {
            var3 = ConstantsEngine.replace("类 $0 中 method为 $1 的方法不存在, serviceid = $2", this.a.getName(), var1, this.b);
            throw new ExtConfigException(var3);
        } else {
            var3 = var2.getMvid();
            if (var3 == null) {
                String var4 = ConstantsEngine.replace("类 $0 中 method为 $1 的方法不存在, serviceid = $2", this.a.getName(), var1, this.b);
                throw new ExtConfigException(var4);
            } else {
                return var3;
            }
        }
    }

    public void putResultConfig(String var1, ResultConfig var2) {
        this.c.put(var1, var2);
    }

    public String getServiceId() {
        return this.b;
    }

    public void setServiceId(String var1) {
        this.b = var1;
    }

    public Class getService() {
        return this.a;
    }

    public void setService(Class var1) {
        this.a = var1;
    }
}
