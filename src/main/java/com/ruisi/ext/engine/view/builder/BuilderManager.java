//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.view.builder;

import com.itextpdf.text.DocumentException;
import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.control.InputOptionFactory;
import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.init.ExtEnvirContextImpl;
import com.ruisi.ext.engine.util.DaoUtils;
import com.ruisi.ext.engine.view.context.Element;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.context.face.RefChecker;
import com.ruisi.ext.engine.view.emitter.ContextEmitter;
import com.ruisi.ext.engine.view.emitter.html.HTMLEmitter;
import com.ruisi.ext.engine.view.exception.AuthException;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.wrapper.ByteArrayWriterImpl;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtResponse;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import com.ruisi.ext.engine.wrapper.OutputStreamWriterImpl;
import com.ruisi.ext.engine.wrapper.TestRequestImpl;
import com.ruisi.ext.engine.wrapper.TestResponseImpl;
import com.ruisi.ext.engine.wrapper.TestSessionImpl;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;
import jxl.write.WriteException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.mozilla.javascript.Context;

public class BuilderManager {
    private static Log a = LogFactory.getLog(BuilderManager.class);

    public BuilderManager() {
    }

    public static void buildContextByEmitter(Element var0, ExtRequest var1, ExtEnvirContext var2, ExtResponse var3, DaoHelper var4, ContextEmitter var5, InputOption var6, ServletContext var7) throws AuthException, IOException, DocumentException, InstantiationException, ExtConfigException, IllegalAccessException, NoSuchMethodException, ServletException, InvalidFormatException, ScriptEnginerException, WriteException, InvocationTargetException {
        PageBuilder var8 = null;

        try {
            var8 = new PageBuilder(var0);
            var8.request = var1;
            var8.veloContext = var2;
            var8.response = var3;
            var8.daoHelper = var4;
            var8.emitter = var5;
            var8.option = var6;
            var8.servletContext = var7;
            var8.processStart();
            a(var0, var1, var5, var4, var2, var3, var6, var7);
            var8.processEnd();
        } catch (Exception var15) {
            a.error("构建界面出错.", var15);
            throw var15;
        } finally {
            if (var8 != null && var8.getCt() != null) {
                Context.exit();
            }

        }

    }

    public static void buildContext(Element var0, ExtRequest var1, ExtWriter var2, DaoHelper var3, ExtEnvirContext var4, ExtResponse var5, InputOption var6, ServletContext var7) throws AuthException, IOException, DocumentException, InstantiationException, ExtConfigException, IllegalAccessException, NoSuchMethodException, ServletException, InvalidFormatException, ScriptEnginerException, WriteException, InvocationTargetException {
        PageBuilder var8 = null;

        try {
            HTMLEmitter var9 = new HTMLEmitter();
            var9.initialize(var2, var1, var5, var4, var6, var7);
            var8 = new PageBuilder(var0);
            var8.request = var1;
            var8.veloContext = var4;
            var8.response = var5;
            var8.daoHelper = var3;
            var8.emitter = var9;
            var8.option = var6;
            var8.servletContext = var7;
            var8.processStart();
            a(var0, var1, var9, var3, var4, var5, var6, var7);
            var8.processEnd();
        } catch (Exception var15) {
            a.error("构建界面出错.", var15);
            throw var15;
        } finally {
            if (var8 != null && var8.getCt() != null) {
                Context.exit();
            }

        }

    }

    public static void buildContext(String var0, ExtRequest var1, ExtWriter var2, DaoHelper var3, ExtEnvirContext var4, ExtResponse var5, InputOption var6, ServletContext var7) throws ExtConfigException, AuthException, InstantiationException, IllegalAccessException, DocumentException, IOException, NoSuchMethodException, ServletException, InvalidFormatException, ScriptEnginerException, WriteException, InvocationTargetException {
        try {
            MVContext var8 = ExtContext.getInstance().getMVContext(var0);
            buildContext((Element)var8, var1, var2, var3, var4, var5, var6, var7);
        } catch (Exception var10) {
            a.error("构建界面出错.", var10);
            throw var10;
        }
    }

    public static void rebuild(Element var0, ExtRequest var1, ContextEmitter var2, DaoHelper var3, ExtEnvirContext var4, ExtResponse var5, InputOption var6, ServletContext var7) {
        AbstractBuilder var8 = var0.createBuilder();
        if (!(var8 instanceof Rebuilder)) {
            throw new ExtRuntimeException("对象不具有rebuild功能.");
        } else {
            var8.emitter = var2;
            var8.request = var1;
            var8.daoHelper = var3;
            var8.veloContext = var4;
            var8.response = var5;
            var8.option = var6;
            var8.servletContext = var7;
            Rebuilder var9 = (Rebuilder)var8;
            var9.rebuild();
        }
    }

    private static void a(Element var0, ExtRequest var1, ContextEmitter var2, DaoHelper var3, ExtEnvirContext var4, ExtResponse var5, InputOption var6, ServletContext var7) throws IOException, ServletException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InvalidFormatException, WriteException, ExtConfigException, ScriptEnginerException, DocumentException {
        AbstractBuilder var8 = var0.createBuilder();
        var8.emitter = var2;
        var8.request = var1;
        var8.daoHelper = var3;
        var8.veloContext = var4;
        var8.response = var5;
        var8.option = var6;
        var8.servletContext = var7;
        var8.processStart();
        if (var0.getChildren() != null) {
            if (var0.isGoOn()) {
                Iterator var10 = var0.getChildren().iterator();

                while(var10.hasNext()) {
                    Element var9 = (Element)var10.next();
                    a(var9, var1, var2, var3, var4, var5, var6, var7);
                }
            } else {
                var0.setIsGoOn(true);
            }
        }

        var8.processEnd();
    }

    public static void processAllowBuilder(Element var0, Element var1, ExtRequest var2, ContextEmitter var3, DaoHelper var4, ExtEnvirContext var5, ExtResponse var6, InputOption var7, ServletContext var8) throws AuthException, IOException, DocumentException, InstantiationException, ExtConfigException, IllegalAccessException, NoSuchMethodException, ServletException, InvalidFormatException, ScriptEnginerException, WriteException, InvocationTargetException {
        PageBuilder var9 = null;

        try {
            var9 = new PageBuilder(var0);
            var9.request = var2;
            var9.veloContext = var5;
            var9.response = var6;
            var9.daoHelper = var4;
            var9.emitter = var3;
            var9.option = var7;
            var9.servletContext = var8;
            var9.processStart();
            a(var0, var1, var2, var3, var4, var5, var6, var7, var8);
            var9.processEnd();
        } catch (Exception var16) {
            a.error("构建界面出错.", var16);
            throw var16;
        } finally {
            if (var9 != null && var9.getCt() != null) {
                Context.exit();
            }

        }

    }

    private static void a(Element var0, Element var1, ExtRequest var2, ContextEmitter var3, DaoHelper var4, ExtEnvirContext var5, ExtResponse var6, InputOption var7, ServletContext var8) throws IOException, ServletException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InvalidFormatException, WriteException, ExtConfigException, ScriptEnginerException, DocumentException {
        AbstractBuilder var9 = var0.createBuilder();
        var9.emitter = var3;
        var9.request = var2;
        var9.daoHelper = var4;
        var9.veloContext = var5;
        var9.response = var6;
        var9.option = var7;
        var9.servletContext = var8;
        if (var9 instanceof DataBuilder || var0 == var1) {
            var9.processStart();
        }

        if (var0.getChildren() != null) {
            Iterator var11 = var0.getChildren().iterator();

            while(var11.hasNext()) {
                Element var10 = (Element)var11.next();
                a(var10, var1, var2, var3, var4, var5, var6, var7, var8);
            }
        }

        if (var9 instanceof DataBuilder || var0 == var1) {
            var9.processEnd();
        }

    }

    public static void refCheckProcess(Element var0) throws ExtConfigException {
        if (var0 instanceof RefChecker) {
            RefChecker var1 = (RefChecker)var0;
            var1.check();
        }

        if (var0.getChildren() != null) {
            Iterator var2 = var0.getChildren().iterator();

            while(var2.hasNext()) {
                Element var3 = (Element)var2.next();
                refCheckProcess(var3);
            }
        }

    }

    public static String buildContext2String(Element var0, ExtRequest var1, ExtEnvirContext var2, ExtResponse var3, DaoHelper var4, ContextEmitter var5, InputOption var6, ServletContext var7) {
        ByteArrayOutputStream var8 = new ByteArrayOutputStream();
        ByteArrayWriterImpl var9 = new ByteArrayWriterImpl(var8);
        boolean var10 = false;
        String var11 = "";

        try {
            var5.initialize(var9, var1, var3, var2, var6, var7);
            buildContextByEmitter(var0, var1, var2, var3, var4, var5, var6, var7);
        } catch (AuthException var15) {
            var10 = true;
            var11 = var15.getMessage();
        } catch (ExtRuntimeException var16) {
            var10 = true;
            var11 = var16.getMessage();
            var16.printStackTrace();
        } catch (Exception var17) {
            var10 = true;
            var11 = var17.getMessage();
            var17.printStackTrace();
        }

        if (!var10) {
            String var12 = new String(var8.toByteArray());

            try {
                var8.close();
            } catch (IOException var14) {
                var14.printStackTrace();
            }

            return var12;
        } else {
            return " <ul><h4>出错了！ 错误信息 </h4><li>" + var11 + "</li></ul>";
        }
    }

    public static String buildContextNoHttp(String var0, Map var1, ContextEmitter var2, ServletContext var3) {
        try {
            MVContext var4 = ExtContext.getInstance().getMVContext(var0, true);
            TestSessionImpl var5 = new TestSessionImpl();
            TestRequestImpl var6 = new TestRequestImpl(var1, var5);
            TestResponseImpl var7 = new TestResponseImpl();
            DaoHelper var8 = DaoUtils.getDaoHelper(var3);
            ByteArrayOutputStream var9 = new ByteArrayOutputStream();
            OutputStreamWriterImpl var10 = new OutputStreamWriterImpl(var9);
            Map var11 = var4.getMvParams();
            InputOption var12 = InputOptionFactory.createInputOption(var6, var7, var11);
            ExtEnvirContextImpl var13 = new ExtEnvirContextImpl(var12);
            var6.setAttribute("ext.view.mvid", var0);
            var2.initialize(var10, var6, var7, var13, var12, var3);
            buildContextByEmitter(var4, var6, var13, var7, var8, var2, var12, var3);
            String var14 = new String(var9.toByteArray());
            return var14;
        } catch (AuthException var15) {
            a.error("License 不存在或已失效或超出最大访问限制。");
            return null;
        } catch (Exception var16) {
            a.error("执行NoHttp请求出错.", var16);
            throw new ExtRuntimeException(var16);
        }
    }
}
