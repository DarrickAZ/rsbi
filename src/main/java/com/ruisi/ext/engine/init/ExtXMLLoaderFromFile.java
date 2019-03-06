//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.init;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.servlet.ServletContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExtXMLLoaderFromFile implements ExtXMLLoader {
    private static Log a = LogFactory.getLog(ExtXMLLoaderFromFile.class);

    public ExtXMLLoaderFromFile() {
    }

    @Override
    public InputStream load(String var1, String var2, String var3, ServletContext var4) throws ExtConfigException, FileNotFoundException {
        String var5 = var1.replace('.', '/');
        String var6 = var2 + var3 + var5 + ".xml";
        File var7 = new File(var6);
        if (!var7.exists()) {
            String var8 = ConstantsEngine.replace("id 为 $0 的mv不存在", var1) + "(" + var6 + ")";
            throw new ExtConfigException(var8);
        } else {
            if (a.isDebugEnabled()) {
                a.debug("parser file : " + var6);
            }

            return new FileInputStream(var7);
        }
    }
}
