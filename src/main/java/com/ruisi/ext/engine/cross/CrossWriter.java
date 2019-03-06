//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.cross;

import com.ruisi.ext.engine.view.context.cross.CrossField;
import com.ruisi.ext.engine.view.exception.ExtConfigException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface CrossWriter {
    void queryLastLevelCols();

    void writeCols();

    void writeRows();

    void writerKpi(CrossField var1, List var2, CrossField var3, int var4, int var5);

    void writerFieldFmts();

    void writerFieldDirll();

    void writerLink() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, ExtConfigException;

    void writerColLink() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, ExtConfigException;

    void writerPageInfo();

    void writerSort();
}
