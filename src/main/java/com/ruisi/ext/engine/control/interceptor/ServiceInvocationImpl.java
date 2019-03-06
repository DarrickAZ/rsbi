//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.control.interceptor;

import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.service.Service;
import javax.servlet.ServletContext;

public class ServiceInvocationImpl implements ServiceInvocation {
    private InputOption a;
    private Service b;
    private ServletContext c;
    private DaoHelper d;
    private String e;
    private String f;

    public ServiceInvocationImpl(String var1, String var2, InputOption var3, Service var4, ServletContext var5, DaoHelper var6) {
        this.a = var3;
        this.b = var4;
        this.c = var5;
        this.d = var6;
        this.e = var2;
        this.f = var1;
    }

    public InputOption getInputOption() {
        return this.a;
    }

    public Service getService() {
        return this.b;
    }

    public ServletContext getServletContext() {
        return this.c;
    }

    public DaoHelper getDaoHelper() {
        return this.d;
    }

    public String getMethod() {
        return this.e;
    }

    public String getServiceId() {
        return this.f;
    }
}
