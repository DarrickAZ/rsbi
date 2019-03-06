//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.scan;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.init.config.ResultConfig;
import com.ruisi.ext.engine.init.config.ServiceConfig;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ScanUtils {
    private static Log a = LogFactory.getLog(ScanUtils.class);

    public ScanUtils() {
    }

    public void startScan(String var1) throws ExtConfigException {
        String var2 = var1.replace('.', '/');
        URL var3 = Thread.currentThread().getContextClassLoader().getResource(var2);
        String var4;
        if (var3 == null) {
            var4 = ConstantsEngine.replace("配置的包 $0 未找到.", var1);
            throw new ExtConfigException(var4);
        } else {
            if ("file".equalsIgnoreCase(var3.getProtocol())) {
                var4 = var3.getFile();
                try {
                    var4 = URLDecoder.decode(var4, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                File var5 = new File(var4);
                this.scanFile(var5, var1, var1 + ".");
            } else if ("jar".equalsIgnoreCase(var3.getProtocol())) {
                JarFile var6 = null;
                try {
                    var6 = ((JarURLConnection)var3.openConnection()).getJarFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.scanJar(var6, var2, var1 + ".");
            } else if ("zip".equalsIgnoreCase(var3.getProtocol())) {
                var4 = var3.getFile();
                this.scanZip(var4, var2, var1 + ".");
            }

        }
    }

    public void scanZip(String var1, String var2, String var3) {
        int var4 = var1.indexOf("!/");
        ZipFile var5 = null;
        if (var4 != -1) {
            String var6 = var1.substring(0, var4).replace('/', File.separatorChar);
            File var7 = new File(var6);
            if (!((File)var7).exists()) {
                File var8 = null;
                try {
                    var8 = new File(URLDecoder.decode(var6, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (var8.exists()) {
                    var7 = var8;
                }
            }

            try {
                var5 = new ZipFile((File)var7);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (var4 == -1 && var1.endsWith(".jar")) {
            try {
                var5 = new ZipFile(var1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Enumeration var10 = var5.entries();

        while(var10.hasMoreElements()) {
            ZipEntry var11 = (ZipEntry)var10.nextElement();
            if (!var11.isDirectory()) {
                String var12 = var11.getName();
                if (var12.startsWith(var2) && var12.endsWith("Service.class")) {
                    var12 = var12.replace('/', '.');
                    var12 = var12.substring(0, var12.length() - 6);
                    this.createConfigInfo(var12, var3);
                }
            }
        }

        try {
            var5.close();
        } catch (IOException var9) {
            var9.printStackTrace();
        }

    }

    public void scanJar(JarFile var1, String var2, String var3) {
        Enumeration var4 = var1.entries();

        while(var4.hasMoreElements()) {
            JarEntry var5 = (JarEntry)var4.nextElement();
            String var6 = var5.getName();
            if (var6.startsWith(var2) && var6.endsWith("Service.class")) {
                var6 = var6.replace('/', '.');
                var6 = var6.substring(0, var6.length() - 6);
                this.createConfigInfo(var6, var3);
            }
        }

        try {
            var1.close();
        } catch (IOException var7) {
            var7.printStackTrace();
        }

    }

    public void createConfigInfo(String var1, String var2) {
        Class var3 = null;
        try {
            var3 = Class.forName(var1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String var4 = var1.replaceAll(var2, "").replaceAll("Service", "");
        String var5 = var4;
        ServiceConfig var6 = new ServiceConfig();
        ExtContext.getInstance().putServiceConfig(var4, var6);
        var6.setService(var3);
        var6.setServiceId(var4);
        Method[] var10;
        int var9 = (var10 = var3.getMethods()).length;

        for(int var8 = 0; var8 < var9; ++var8) {
            Method var7 = var10[var8];
            Class[] var11 = var7.getParameterTypes();
            Class var12 = var7.getReturnType();
            if (var11 != null && var11.length == 1 && var11[0] == InputOption.class && var7.getReturnType() != null && var12 == Void.TYPE) {
                ResultConfig var13 = new ResultConfig();
                String var14 = var7.getName();
                var13.setMethod(var14);
                ResultRef var15 = (ResultRef)var7.getAnnotation(ResultRef.class);
                if (var15 != null) {
                    var13.setMvid(var15.value());
                } else if (var14.equals("execute")) {
                    var13.setMvid(var4);
                } else {
                    var13.setMvid(var4 + "-" + var14);
                }

                InterRef var16 = (InterRef)var7.getAnnotation(InterRef.class);
                if (var16 != null) {
                    var13.setInteRefs(var16.value().split(","));
                }

                var6.putResultConfig(var14, var13);
                if (a.isDebugEnabled()) {
                    a.debug("scan :  sid = " + var5 + ", method=" + var13.getMethod() + ", mvid = " + var13.getMvid() + "," + (var16 == null ? "" : "inter-ref = " + var16.value()));
                }
            }
        }

    }

    public void scanFile(File var1, String var2, String var3) {
        File[] var4 = var1.listFiles(new a(this));
        File[] var8 = var4;
        int var7 = var4.length;

        for(int var6 = 0; var6 < var7; ++var6) {
            File var5 = var8[var6];
            if (var5.isDirectory()) {
                this.scanFile(var5, var2 + '.' + var5.getName(), var3);
            } else {
                String var9 = var5.getName();
                String var10 = var9.substring(0, var9.length() - 6);
                if (var10.endsWith("Service")) {
                    this.createConfigInfo(var2 + "." + var10, var3);
                }
            }
        }

    }
}
