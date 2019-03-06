//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.control;

import com.ruisi.ext.engine.init.TemplateManager;
import com.ruisi.ext.engine.init.VelocityManager;
import com.ruisi.ext.engine.init.XmlParser;
import com.ruisi.ext.engine.view.context.ExtContext;
import javax.servlet.ServletContextEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;

public class ExtContextLoaderListener extends ContextLoaderListener {
    private static Log a = LogFactory.getLog(ExtContextLoaderListener.class);
    private ContextListener b;

    public ExtContextLoaderListener() {
    }

    public void contextInitialized(ServletContextEvent var1) {
        try {
            TemplateManager.initTemplate(var1.getServletContext());
            VelocityManager.initVelocity(var1.getServletContext());
            XmlParser.initParser(var1.getServletContext());
            XmlParser.getInstance().parserConfig();
        } catch (Exception var5) {
            a.error("初始化配置信息出错.", var5);
        }

        String var2 = ExtContext.getInstance().getConstant("contextListener");
        if (var2 != null && var2.length() > 0) {
            try {
                this.b = (ContextListener)Class.forName(var2).newInstance();
                this.b.contextInit(var1);
            } catch (Exception var4) {
                a.error("初始化ContextListener出错.", var4);
            }
        }

        super.contextInitialized(var1);
    }

    public void contextDestroyed(ServletContextEvent var1) {
        if (this.b != null) {
            this.b.contextDest(var1);
        }

        super.contextDestroyed(var1);
    }
}
