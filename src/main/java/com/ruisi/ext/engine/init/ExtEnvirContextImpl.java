//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.init;

import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.service.loginuser.LoginUser;
import com.ruisi.ext.engine.service.loginuser.LoginUserFactory;
import com.ruisi.ext.engine.util.ContextUtils;
import com.ruisi.ext.engine.view.context.ExtContext;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.velocity.VelocityContext;

public class ExtEnvirContextImpl extends VelocityContext implements ExtEnvirContext {
    public ExtEnvirContextImpl() {
    }

    public ExtEnvirContextImpl(InputOption var1) {
        Map var2 = ExtContext.getInstance().getConstants();
        Iterator var4 = var2.entrySet().iterator();

        while(var4.hasNext()) {
            Entry var3 = (Entry)var4.next();
            this.put((String)var3.getKey(), var3.getValue());
        }

        String var19 = (String)var1.getRequest().getAttribute("ext.isExport");
        String var20 = "html";
        if ("true".equalsIgnoreCase(var19) || "y".equalsIgnoreCase(var19)) {
            var20 = "xls";
        }

        String var5 = ExtContext.getInstance().getConstant("dbName");
        this.put("extUtils", new ContextUtils(var20, var5));
        String var6 = ExtContext.getInstance().getConstant("userUtils");
        if (var6 != null && var6.length() > 0) {
            try {
                UserEnvirContext var7 = (UserEnvirContext)Class.forName(var6).newInstance();
                this.put(var7.getContextName(), var7.getUserEnvirContext());
            } catch (InstantiationException var16) {
                var16.printStackTrace();
            } catch (IllegalAccessException var17) {
                var17.printStackTrace();
            } catch (ClassNotFoundException var18) {
                var18.printStackTrace();
            }
        }

        if (var1 != null) {
            LoginUser var21 = LoginUserFactory.getInstance().getLoginUser(var1.getRequest());
            Map var8;
            if (var21 != null) {
                var8 = var21.getUserProperties();
                HashMap var9 = new HashMap();
                this.put("s", var9);
                Iterator var11 = var8.entrySet().iterator();

                while(var11.hasNext()) {
                    Entry var10 = (Entry)var11.next();
                    var9.put((String)var10.getKey(), var10.getValue());
                }
            }

            var8 = var1.getParams();
            if (var8 != null) {
                Iterator var23 = var8.entrySet().iterator();

                while(true) {
                    while(var23.hasNext()) {
                        Entry var22 = (Entry)var23.next();
                        String var24 = (String)var22.getKey();
                        Object var12 = var22.getValue();
                        if (var12 instanceof String[]) {
                            String[] var13 = (String[])var12;
                            StringBuffer var14 = new StringBuffer();

                            for(int var15 = 0; var15 < var13.length; ++var15) {
                                var14.append(var13[var15]);
                                if (var15 != var13.length - 1) {
                                    var14.append(",");
                                }
                            }

                            this.put(var24, var14.toString());
                        } else {
                            this.put(var24, var12);
                        }
                    }

                    return;
                }
            }
        }
    }

    public ExtEnvirContextImpl(InputOption var1, ExtEnvirInfoLoader var2) {
        this(var1);
        Map var3 = var2.load();
        if (var3 != null) {
            Iterator var5 = var3.entrySet().iterator();

            while(var5.hasNext()) {
                Entry var4 = (Entry)var5.next();
                this.put((String)var4.getKey(), var4.getValue());
            }
        }

    }
}
