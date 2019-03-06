//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.init.rule;

import com.ruisi.ext.engine.init.config.ServiceConfig;
import com.ruisi.ext.engine.service.Service;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

import java.lang.reflect.InvocationTargetException;

public class PropertyRule extends Rule {
    public PropertyRule() {
    }

    @Override
    public void begin(String var1, String var2, Attributes var3) {
        String var4 = var3.getValue("sql");
        String var5 = var3.getValue("col");
        String var6 = var3.getValue("type");
        ServiceConfig var7 = (ServiceConfig)this.digester.peek();
        Service var8 = null;
        try {
            var8 = ExtContext.getInstance().getService(var7.getServiceId());
        } catch (ExtConfigException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        try {
            BeanUtils.setProperty(var8, "sql", var4);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        try {
            BeanUtils.setProperty(var8, "col", var5);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        try {
            BeanUtils.setProperty(var8, "type", var6);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void end(String var1, String var2) {
    }
}
