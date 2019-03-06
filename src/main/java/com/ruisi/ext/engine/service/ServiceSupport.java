//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.service;

import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.ServletContext;

public abstract class ServiceSupport implements Service {
    protected ServletContext servletContext;
    protected DaoHelper daoHelper;

    public ServiceSupport() {
    }

    public void initService(ServletContext var1, DaoHelper var2) {
        this.servletContext = var1;
        this.daoHelper = var2;
    }

    protected void setNoResult(ExtRequest var1) {
        var1.setAttribute("ext.control.noResult", "y");
    }

    protected void sendRedirect(InputOption var1, String var2, String var3, boolean var4) throws UnsupportedEncodingException {
        Map var5 = var1.getParams();
        StringBuffer var6 = new StringBuffer("extControl");
        var6.append("?serviceid=");
        var6.append(var2);
        var6.append("&methodId=");
        var6.append(var3);
        if (var4) {
            Iterator var8 = var5.entrySet().iterator();

            while(var8.hasNext()) {
                Entry var7 = (Entry)var8.next();
                if (var7.getValue() instanceof String) {
                    var6.append("&");
                    var6.append((String)var7.getKey());
                    var6.append("=");
                    var6.append(URLEncoder.encode((String)var7.getValue(), "UTF-8"));
                }
            }
        }

        String var9 = (String)var1.getRequest().getAttribute("ext.control.noResult");
        if (!"y".equalsIgnoreCase(var9)) {
            this.setNoResult(var1.getRequest());
        }

        ExtResponse var10 = var1.getResponse();
        var10.setStatus(302);
        var10.setHeader("location", var1.getRequest().getContextPath() + "/control/" + var6);
    }
}
