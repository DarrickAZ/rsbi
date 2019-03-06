//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.control;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.init.XmlParser;
import com.ruisi.ext.engine.init.config.ServiceConfig;
import com.ruisi.ext.engine.service.Service;
import com.ruisi.ext.engine.service.ServiceSupport;
import com.ruisi.ext.engine.util.MethodUtils;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.view.exception.ForbidException;
import com.ruisi.ext.engine.view.exception.ServiceException;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import javax.servlet.ServletContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ActionProcess {
    private static Log a = LogFactory.getLog(ActionProcess.class);
    private static ActionProcess b = new ActionProcess();
    private static Object c = new Object();

    private ActionProcess() {
    }

    public static MVContext findMVContextById(String var0) throws ExtConfigException {
        MVContext var1 = null;
        synchronized(c) {
            String var3 = ExtContext.getInstance().getConstant("devMode");
            if ("true".equalsIgnoreCase(var3)) {
                ExtContext.getInstance().removeMV(var0);
                var1 = XmlParser.getInstance().parserPageAndChkRef(var0);
            } else if (!ExtContext.getInstance().chkMvExist(var0)) {
                var1 = XmlParser.getInstance().parserPageAndChkRef(var0);
            } else {
                var1 = ExtContext.getInstance().getMVContext(var0);
            }

            return var1;
        }
    }

    public static MVContext findMVContextById(String var0, boolean var1) throws ExtConfigException {
        if (var1) {
            return findMVContextById(var0);
        } else {
            MVContext var2 = null;
            synchronized(c) {
                if (!ExtContext.getInstance().chkMvExist(var0)) {
                    var2 = XmlParser.getInstance().parserPageAndChkRef(var0);
                } else {
                    var2 = ExtContext.getInstance().getMVContext(var0);
                }

                return var2;
            }
        }
    }

    public static ActionProcess getInstance() {
        if (b == null) {
            throw new ExtRuntimeException("未初始化 actionProcess 对象.");
        } else {
            return b;
        }
    }

    public void processMV(String var1, ExtRequest var2, ExtResponse var3, ServletContext var4) throws ExtConfigException {
        MVContext var6 = findMVContextById(var1);
        Map var7 = var6.getMvParams();
        InputOption var8 = InputOptionFactory.createInputOption(var2, var3, var7);
        var2.setAttribute("ext.view.mvid", var1);
        var2.setAttribute("ext.view.option", var8);
    }

    public void process(ExtRequest var1, ExtResponse var2, ServletContext var3, DaoHelper var4) throws ForbidException, IOException, ExtConfigException, InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException, ServiceException {
        String var5 = var1.getParameter("serviceid");
        Service var6 = ExtContext.getInstance().getService(var5);
        ServiceConfig var7 = ExtContext.getInstance().getServiceConfig(var5);
        String var8 = var1.getParameter("methodId");
        String var9 = var7.getMVID(var8);
        findMVContextById(var9);
        String var10 = var1.getParameter("t_from_id");
        if (var10 != null && var10.length() > 0) {
            findMVContextById(var10, false);
        }

        if (a.isDebugEnabled()) {
            a.debug(var1.getMethod() + " serviceId" + " = " + var5 + "(" + var6.getClass().getName() + "), methodId = " + var8);
            a.debug("fromMV = " + var10 + ", returnMv = " + var9);
        }

        var1.setAttribute("ext.view.serviceId", var5);
        var1.setAttribute("ext.view.methodId", var8);
        var1.setAttribute("ext.view.mvid", var9);
        var1.setAttribute("ext.view.fromId", var10);
        ServiceSupport var11;
        if (var6 instanceof ServiceSupport) {
            var11 = (ServiceSupport)var6;
            var11.initService(var3, var4);
        }

        var11 = null;
        Map var20;
        if (var1.getMethod().equalsIgnoreCase("POST")) {
            if (var10 == null || var10.length() == 0) {
                String var21 = ConstantsEngine.replace("请求方法为POST,但 $0 却为null", "t_from_id");
                throw new ExtRuntimeException(var21);
            }

            var20 = ExtContext.getInstance().getMVContext(var10).getMvParams();
        } else {
            var20 = ExtContext.getInstance().getMVContext(var9).getMvParams();
        }

        InputOption var12 = InputOptionFactory.createInputOption(var1, var2, var20);
        var1.setAttribute("ext.view.option", var12);
        if (a.isDebugEnabled()) {
            a.debug("params = " + var12.getParams());
        }

        boolean var13 = true;
        String[] var14 = var7.getInterRefs(var8);
        if (var14 != null && var14.length > 0) {
            String[] var18 = var14;
            int var17 = var14.length;

            for(int var16 = 0; var16 < var17; ++var16) {
                String var15 = var18[var16];
                String var19 = ExtContext.getInstance().getInterceptor(var15).getClazz();
                if (a.isDebugEnabled()) {
                    a.debug("invoke拦截器: " + var19 + ", method = " + var8);
                }

                var13 = MethodUtils.invokeInterceptor(var19, var7.getServiceId(), var8, var12, var6, var3, var4);
            }
        }

        if (!var13) {
            throw new ForbidException("禁止访问");
        } else {
            if (var8 != null && var8.length() != 0) {
                Class[] var22 = new Class[]{InputOption.class};
                MethodUtils.invokeMethod(var6, var8, new Object[]{var12}, var22);
            } else {
                var6.execute(var12);
            }

        }
    }
}
