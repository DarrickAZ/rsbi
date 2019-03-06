//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.init;

import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.face.Template;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.wrapper.ExtRequest;

import java.io.*;
import java.util.Iterator;
import javax.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;

public class TemplateManager {
    private ServletContext a;
    private static TemplateManager b;
    private static int c = 0;

    private synchronized int a() {
        if (c > 1000) {
            c = 0;
        } else {
            ++c;
        }

        return c;
    }

    private TemplateManager(ServletContext var1) {
        this.a = var1;
    }

    public static void initTemplate(ServletContext var0) {
        b = new TemplateManager(var0);
    }

    public static TemplateManager getInstance() {
        if (b == null) {
            throw new ExtRuntimeException("未初始化模板对象.");
        } else {
            return b;
        }
    }

    public static String buildTempldate(String var0, ExtRequest var1, ExtEnvirContext var2) {
        StringWriter var3 = new StringWriter();
        try {
            Velocity.mergeTemplate(var0, "UTF-8", (Context)var2, var3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return var3.toString();
    }

    public String createTemplate(String var1) {
        String var2 = this.a.getInitParameter("velocityResPath");
        String var3 = "";
        if (var2 != null && "tomcatTempDir".equals(var2)) {
            var3 = System.getProperty("java.io.tmpdir");
            var3 = var3 + "/" + this.a.getServletContextName() + "/" + "WEB-INF/ext-temp/";
        } else {
            var3 = this.a.getRealPath("/") + "WEB-INF/ext-temp/";
        }

        File var4 = new File(var3);
        if (!var4.exists()) {
            var4.mkdirs();
        }

        String var5 = "";
        var5 = this.a() + ".mv";
        var3 = var3 + var5;
        OutputStreamWriter var6 = null;
        try {
            var6 = new OutputStreamWriter(new FileOutputStream(var3), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            var6.write(var1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            var6.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return var5;
    }

    public void deleteTmp(Element var1) {
        String var2 = this.a.getRealPath("/") + "WEB-INF/ext-temp/";
        synchronized(this) {
            this.a(var1, var2);
        }
    }

    public String getTemplate(String var1) {
        String var2 = this.a.getRealPath("/") + "WEB-INF/ext-temp/";
        String var3 = var2 + var1;
        try {
            return FileUtils.readFileToString(new File(var3), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void a(Element var1, String var2) {
        if (var1 instanceof Template) {
            String var3 = ((Template)var1).getTemplateName();
            String var4 = var2 + var3;
            (new File(var4)).delete();
        }

        if (var1.getChildren() != null) {
            Iterator var6 = var1.getChildren().iterator();

            while(var6.hasNext()) {
                Element var5 = (Element)var6.next();
                this.a(var5, var2);
            }

        }
    }
}
