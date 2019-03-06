//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.init;

import javax.servlet.ServletContext;
import org.apache.velocity.app.Velocity;

public class VelocityManager {
    public VelocityManager() {
    }

    public static void initVelocity(ServletContext var0) {
        String var1 = var0.getInitParameter("velocityResPath");
        String var2 = "";
        if (var1 != null && "tomcatTempDir".equals(var1)) {
            var2 = System.getProperty("java.io.tmpdir");
            var2 = var2 + "/" + var0.getServletContextName() + "/" + "WEB-INF/ext-temp/";
        } else {
            var2 = var0.getRealPath("/");
            var2 = var2 + "WEB-INF/ext-temp/";
        }

        Velocity.setProperty("runtime.log", System.getProperty("java.io.tmpdir") + "/velocity.log");
        Velocity.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogChute");
        Velocity.setProperty("file.resource.loader.path", var2);
        Velocity.setProperty("input.encoding", "UTF-8");
        Velocity.setProperty("output.encoding", "UTF-8");
        try {
            Velocity.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
