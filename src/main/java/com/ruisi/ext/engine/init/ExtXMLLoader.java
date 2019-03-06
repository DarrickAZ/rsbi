//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.init;

import com.ruisi.ext.engine.view.exception.ExtConfigException;

import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.servlet.ServletContext;

public interface ExtXMLLoader {
    InputStream load(String var1, String var2, String var3, ServletContext var4) throws ExtConfigException, FileNotFoundException;
}
