//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.init;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.init.rule.ChartEmitterRule;
import com.ruisi.ext.engine.init.rule.ConstantConfigRule;
import com.ruisi.ext.engine.init.rule.DatabaseRule;
import com.ruisi.ext.engine.init.rule.IncludeConfigRule;
import com.ruisi.ext.engine.init.rule.InterceptorRule;
import com.ruisi.ext.engine.init.rule.PropertyRule;
import com.ruisi.ext.engine.init.rule.ResultConfigRule;
import com.ruisi.ext.engine.init.rule.ServiceConfigRule;
import com.ruisi.ext.engine.util.PropertiesLoaderImpl;
import com.ruisi.ext.engine.util.RuleUtils;
import com.ruisi.ext.engine.view.builder.BuilderManager;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisi.ext.engine.view.emitter.html.HtmlLayoutEnginer;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.view.rules.MVAutoRule;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.servlet.ServletContext;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.digester.Rules;
import org.apache.commons.digester.RulesBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

public class XmlParser {
    private Digester a;
    private ServletContext b;
    private String c;
    private Rules d;
    private MVAutoRule e;
    private static Log f = LogFactory.getLog(XmlParser.class);
    private static XmlParser g;
    private String h = null;
    private List i = new ArrayList();
    private ExtXMLLoader j;
    private ExtXMLLoader k = new ExtXMLLoaderFromFile();

    public static void putXmlData() {
        HtmlLayoutEnginer.putData();
    }

    public static void removeXmlData() {
        HtmlLayoutEnginer.removeData();
    }

    public static int getEndDate(ServletContext var0) throws ParseException {
        return HtmlLayoutEnginer.getEndDate(var0);
    }

    public static synchronized void initParser(ServletContext var0) {
        if (g != null) {
            throw new ExtRuntimeException("xmlParser 已初始化");
        } else {
            g = new XmlParser(var0);
        }
    }

    public static XmlParser getInstance() {
        if (g == null) {
            throw new ExtRuntimeException("xmlParser 未初始化");
        } else {
            return g;
        }
    }

    private XmlParser(ServletContext var1) {
        this.b = var1;
        this.c = this.b.getRealPath("/");
        this.a = new Digester();
        this.a.setValidating(false);
        this.e = new MVAutoRule(this.a);
        this.d = new RulesBase();
        Properties var2 = null;
        try {
            var2 = (new PropertiesLoaderImpl()).loadResource("/com/ruisi/ext/engine/init/mv.properties", XmlParser.class);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Iterator var3 = var2.keySet().iterator();

        String var4;
        while(var3.hasNext()) {
            var4 = (String)var3.next();
            String var5 = var2.getProperty(var4);
            Rule var6 = null;
            try {
                var6 = (Rule)Class.forName(var5).newInstance();
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            this.d.add(var4, var6);
        }

        var4 = var1.getRealPath("/") + "WEB-INF/ext-temp/";
    }

    public void parserConfig() throws ExtConfigException {
        RulesBase var1 = new RulesBase();
        var1.add("*/constant", new ConstantConfigRule());
        var1.add("*/service", new ServiceConfigRule());
        var1.add("*/service/result", new ResultConfigRule());
        var1.add("*/property", new PropertyRule());
        var1.add("*/interceptor", new InterceptorRule());
        var1.add("*/include", new IncludeConfigRule(this.i));
        var1.add("*/chart", new ChartEmitterRule());
        var1.add("*/db", new DatabaseRule());
        this.a.setRules(var1);
        String var2 = this.c + "/WEB-INF/ext2/ext-config/ext-config.xml";
        if (f.isDebugEnabled()) {
            f.debug(" parser ext-config : " + var2);
        }

        try {
            this.a.parse(new File(var2));
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (SAXException e1) {
            e1.printStackTrace();
        }

        String var4;
        while(!RuleUtils.isEmpty(this.i)) {
            for(int var3 = 0; var3 < this.i.size(); ++var3) {
                var4 = (String)this.i.get(var3);
                if (var4 != null) {
                    this.parserIncludeFile(var4);
                    this.i.set(var3, (Object)null);
                }
            }
        }

        this.i.clear();
        this.a.clear();
        var1.clear();
        String var5 = ExtContext.getInstance().getConstant("xmlResource");
        if (var5 == null) {
            var4 = ConstantsEngine.replace("ext-config中未配置 $0", "xmlResource");
            throw new ExtConfigException(var4);
        } else {
            this.h = var5;
            var4 = ExtContext.getInstance().getConstant("xmlLoader");
            if (var4 != null && var4.length() > 0) {
                try {
                    this.j = (ExtXMLLoader)Class.forName(var4).newInstance();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }

    public void parserIncludeFile(String var1) {
        if (f.isDebugEnabled()) {
            f.debug(" parser include file : " + this.c + var1);
        }

        try {
            this.a.parse(this.c + var1);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (SAXException e1) {
            e1.printStackTrace();
        }
    }

    public MVContext parserPageAndChkRef(String var1) {
        MVContext var2 = null;
        try {
            var2 = this.a(var1);
        } catch (ExtConfigException e1) {
            e1.printStackTrace();
        } catch (SAXException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            BuilderManager.refCheckProcess(var2);
        } catch (ExtConfigException e1) {
            e1.printStackTrace();
        }
        return var2;
    }

    private MVContext a(String var1) throws ExtConfigException, SAXException, IOException {
        if (this.h == null) {
            throw new ExtRuntimeException("还未 parser ext-config.xml 文件.");
        } else {
            MVContext var2 = null;
            InputStream var3 = null;

            MVContext var6;
            try {
                this.a.setRules(this.d);
                this.e.setMvid(var1);
                var2 = this.e.start();
                ExtXMLLoader var4 = null;
                if (var1.startsWith("usave")) {
                    var4 = this.j;
                    if (var4 == null) {
                        throw new ExtConfigException("usave开头的MV,未配置 自定义 的xml加载器。");
                    }
                } else {
                    var4 = this.k;
                }

                var3 = var4.load(var1, this.c, this.h, this.b);
                this.a.parse(var3);
                var6 = var2;
            } catch (Exception var9) {
                ExtContext.getInstance().removeMV(var1);
                throw var9;
            } finally {
                this.a.clear();
                if (var3 != null) {
                    var3.close();
                }

            }

            return var6;
        }
    }
}
