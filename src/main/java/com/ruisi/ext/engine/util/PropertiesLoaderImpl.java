//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoaderImpl implements ResourceLoader {
    public PropertiesLoaderImpl() {
    }

    @Override
    public Properties loadResource(String var1, Class var2) throws IOException {
        InputStream var3 = var2.getResourceAsStream(var1);
        Properties var4 = new Properties();
        var4.load(var3);
        var3.close();
        return var4;
    }
}
