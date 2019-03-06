//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.init;

import java.io.File;
import java.io.FileFilter;

class a implements FileFilter {
    private final XmlGetter a;

    a(XmlGetter var1) {
        this.a = var1;
    }

    @Override
    public boolean accept(File var1) {
        return var1.isDirectory() || var1.getName().toLowerCase().endsWith(".xml");
    }
}
