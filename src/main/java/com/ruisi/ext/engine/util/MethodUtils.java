//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.util;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.control.interceptor.ServiceInterceptor;
import com.ruisi.ext.engine.control.interceptor.ServiceInvocationImpl;
import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.service.Service;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.view.exception.ServiceException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.servlet.ServletContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MethodUtils {
    private static Log a = LogFactory.getLog(MethodUtils.class);

    public MethodUtils() {
    }

    public static Object invokeMethod(Object var0, String var1, Object[] var2, Class[] var3) throws ExtConfigException, ServiceException, InvocationTargetException {
        try {
            Method var4 = var0.getClass().getMethod(var1, var3);
            try {
                return var4.invoke(var0, var2);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException var6) {
            String var8 = ConstantsEngine.replace("类 $0 指定的方法 $1 不存在.", var0.getClass().getName(), var1);
            throw new ExtConfigException(var8);
        } catch (InvocationTargetException var7) {
            Throwable var5 = var7.getTargetException();
            if (var5 instanceof ServiceException) {
                throw (ServiceException)var5;
            } else {
                a.error("方法调用出错.", var7);
                throw var7;
            }
        }
        return null;
    }

    public static boolean invokeInterceptor(String var0, String var1, String var2, InputOption var3, Service var4, ServletContext var5, DaoHelper var6) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ServiceInterceptor var7 = (ServiceInterceptor)Class.forName(var0).newInstance();
        ServiceInvocationImpl var8 = new ServiceInvocationImpl(var1, var2, var3, var4, var5, var6);
        return var7.intercept(var8);
    }
}
