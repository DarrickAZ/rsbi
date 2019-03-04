//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.util;

import com.ruisitech.bi.entity.common.BaseEntity;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;

public class HandlerExecutionChainWrapper extends HandlerExecutionChain {
    private BeanFactory beanFactory;
    private HandlerMethod handlerWrapper;
    private byte[] lock = new byte[0];

    public HandlerExecutionChainWrapper(HandlerExecutionChain chain, HttpServletRequest request, BeanFactory beanFactory) {
        super(chain.getHandler(), chain.getInterceptors());
        this.beanFactory = beanFactory;
    }

    public Object getHandler() {
        if (this.handlerWrapper != null) {
            return this.handlerWrapper;
        } else {
            synchronized(this.lock) {
                if (this.handlerWrapper != null) {
                    return this.handlerWrapper;
                } else {
                    HandlerMethod superMethodHandler = (HandlerMethod)super.getHandler();
                    Object proxyBean = this.createProxyBean(superMethodHandler);
                    this.handlerWrapper = new HandlerMethod(proxyBean, superMethodHandler.getMethod());
                    return this.handlerWrapper;
                }
            }
        }
    }

    private Object createProxyBean(HandlerMethod handler) {
        try {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(handler.getBeanType());
            Object bean = handler.getBean();
            if (bean instanceof String) {
                bean = this.beanFactory.getBean((String)bean);
            }

            HandlerExecutionChainWrapper.ControllerXssInterceptor xss = new HandlerExecutionChainWrapper.ControllerXssInterceptor(bean);
            enhancer.setCallback(xss);
            return enhancer.create();
        } catch (Exception var5) {
            throw new IllegalStateException("为Controller创建代理失败:" + var5.getMessage(), var5);
        }
    }

    public static class ControllerXssInterceptor implements MethodInterceptor {
        private Object target;
        private List<String> objectMatchPackages;

        public ControllerXssInterceptor(Object target) {
            this.target = target;
            this.objectMatchPackages = new ArrayList();
            this.objectMatchPackages.add("com.ruisitech.bi.entity");
        }

        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            String methodName = method.getName();
            if (!"print".equals(methodName) && !"export".equals(methodName) && !"viewAppTable".equals(methodName) && !"viewAppChart".equals(methodName)) {
                if (args != null) {
                    for(int i = 0; i < args.length; ++i) {
                        if (args[i] != null) {
                            if (args[i] instanceof String) {
                                args[i] = this.stringXssReplace((String)args[i]);
                            } else {
                                Iterator var7 = this.objectMatchPackages.iterator();

                                while(var7.hasNext()) {
                                    String pk = (String)var7.next();
                                    if (args[i].getClass().getName().startsWith(pk)) {
                                        this.objectXssReplace(args[i]);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                return method.invoke(this.target, args);
            } else {
                return method.invoke(this.target, args);
            }
        }

        private String stringXssReplace(String argument) {
            return RSBIUtils.htmlEscape(argument);
        }

        private void objectXssReplace(Object argument) {
            if (argument != null) {
                if (argument instanceof BaseEntity) {
                    BaseEntity entity = (BaseEntity)argument;
                    entity.validate();
                }

            }
        }
    }
}
