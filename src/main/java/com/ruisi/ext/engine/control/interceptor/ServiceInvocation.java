//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.control.interceptor;

import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.service.Service;
import javax.servlet.ServletContext;

public interface ServiceInvocation {
    InputOption getInputOption();

    Service getService();

    ServletContext getServletContext();

    DaoHelper getDaoHelper();

    String getMethod();

    String getServiceId();
}
