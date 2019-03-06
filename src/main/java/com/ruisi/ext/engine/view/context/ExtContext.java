//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.context;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.control.ActionProcess;
import com.ruisi.ext.engine.dao.DatabaseHelper;
import com.ruisi.ext.engine.init.config.InterceptorConfig;
import com.ruisi.ext.engine.init.config.ServiceConfig;
import com.ruisi.ext.engine.service.Service;
import com.ruisi.ext.engine.view.emitter.chart.ChartEmitter;
import com.ruisi.ext.engine.view.exception.ExtConfigException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ExtContext {
    private Map a = Collections.synchronizedMap(new HashMap());
    private Map b = Collections.synchronizedMap(new HashMap());
    private Map c = Collections.synchronizedMap(new HashMap());
    private Map d = Collections.synchronizedMap(new HashMap());
    private Map e = Collections.synchronizedMap(new HashMap());
    private Map f = Collections.synchronizedMap(new HashMap());
    private static ExtContext g = null;

    public void removeMV(String var1) {
        this.b.remove(var1);
    }

    public static synchronized ExtContext getInstance() {
        if (g == null) {
            g = new ExtContext();
        }

        return g;
    }

    private ExtContext() {
    }

    public void putInterceptor(String var1, InterceptorConfig var2) throws ExtConfigException {
        if (this.d.containsKey(var1)) {
            String var3 = ConstantsEngine.replace("id为 $0 的拦截器已经存在.", var1);
            throw new ExtConfigException(var3);
        } else {
            this.d.put(var1, var2);
        }
    }

    public InterceptorConfig getInterceptor(String var1) throws ExtConfigException {
        InterceptorConfig var2 = (InterceptorConfig)this.d.get(var1);
        if (var2 == null) {
            String var3 = ConstantsEngine.replace("id为 $0 的拦截器不存在.", var1);
            throw new ExtConfigException(var3);
        } else {
            return var2;
        }
    }

    public void putServiceConfig(String var1, ServiceConfig var2) {
        this.c.put(var1, var2);
    }

    public ServiceConfig getServiceConfig(String var1) {
        return (ServiceConfig)this.c.get(var1);
    }

    public boolean chkMvExist(String var1) {
        MVContext var2 = (MVContext)this.b.get(var1);
        return var2 != null;
    }

    public MVContext getMVContext(String var1) throws ExtConfigException {
        MVContext var2 = (MVContext)this.b.get(var1);
        if (var2 == null) {
            String var3 = ConstantsEngine.replace("id 为 $0 的mv不存在", var1);
            throw new ExtConfigException(var3);
        } else {
            return var2;
        }
    }

    public Map getAllMV() {
        return this.b;
    }

    public MVContext getMVContext(String var1, boolean var2) throws ExtConfigException {
        return !var2 ? this.getMVContext(var1) : ActionProcess.findMVContextById(var1);
    }

    public Service getService(String var1) throws ExtConfigException, IllegalAccessException, InstantiationException {
        ServiceConfig var2 = (ServiceConfig)this.c.get(var1);
        if (var2 == null) {
            String var3 = ConstantsEngine.replace("id 为 $0 的service不存在.", var1);
            throw new ExtConfigException(var3);
        } else {
            return (Service)var2.getService().newInstance();
        }
    }

    public void putMVContext(MVContext var1) throws ExtConfigException {
        this.putMVContext(var1, true);
    }

    public void putMVContext(MVContext var1, boolean var2) throws ExtConfigException {
        if (var2 && this.chkMvExist(var1.getMvid())) {
            String var3 = ConstantsEngine.replace("id 为 $0 的mv已经存在.", var1.getMvid());
            throw new ExtConfigException(var3);
        } else {
            this.b.put(var1.getMvid(), var1);
        }
    }

    public Map getConstants() {
        return this.a;
    }

    public String getConstant(String var1) {
        return (String)this.a.get(var1);
    }

    public void putConstants(String var1, String var2) {
        this.a.put(var1, var2);
    }

    public void putChartEmitter(String var1, ChartEmitter var2) {
        this.e.put(var1, var2);
    }

    public ChartEmitter getChartEmitter(String var1) {
        return (ChartEmitter)this.e.get(var1);
    }

    public void putDatabaseHelper(String var1, DatabaseHelper var2) {
        this.f.put(var1, var2);
    }

    public DatabaseHelper getDatabaseHelper(String var1) throws ExtConfigException {
        DatabaseHelper var2 = (DatabaseHelper)this.f.get(var1);
        if (var2 == null) {
            throw new ExtConfigException("type = " + var1 + " 的db类型未配置。");
        } else {
            return var2;
        }
    }
}
