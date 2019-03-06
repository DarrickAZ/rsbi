//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.control;

import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.init.VelocityManager;
import com.ruisi.ext.engine.init.XmlParser;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExtContextListener implements ServletContextListener {
    private static Log a = LogFactory.getLog(ExtContextListener.class);

    public ExtContextListener() {
    }

    public void contextDestroyed(ServletContextEvent var1) {
    }

    public void contextInitialized(ServletContextEvent var1) {
        try {
            TemplateManager.initTemplate(var1.getServletContext());
            VelocityManager.initVelocity(var1.getServletContext());
            XmlParser.initParser(var1.getServletContext());
            XmlParser.getInstance().parserConfig();
        } catch (Exception var3) {
            a.error("初始化配置信息出错.", var3);
        }

    }
}
