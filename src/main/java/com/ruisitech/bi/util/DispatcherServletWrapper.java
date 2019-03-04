//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.util;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;

public class DispatcherServletWrapper extends DispatcherServlet {
    private static final long serialVersionUID = 3257687397327300752L;

    public DispatcherServletWrapper() {
    }

    protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        HandlerExecutionChain chain = super.getHandler(request);
        Object handler = chain.getHandler();
        if (!(handler instanceof HandlerMethod)) {
            return chain;
        } else {
            HandlerMethod hm = (HandlerMethod)handler;
            return (HandlerExecutionChain)(!hm.getBeanType().isAnnotationPresent(Controller.class) ? chain : new HandlerExecutionChainWrapper(chain, request, this.getWebApplicationContext()));
        }
    }
}
