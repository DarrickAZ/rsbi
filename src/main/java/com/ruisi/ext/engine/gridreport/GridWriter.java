//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.gridreport;

import com.itextpdf.text.DocumentException;
import com.ruisi.ext.engine.view.exception.ExtConfigException;
import jxl.write.WriteException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

public interface GridWriter {
    void begin() throws IOException, DocumentException;

    void writeHeader() throws WriteException;

    void writeDetail() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, ExtConfigException, WriteException;

    void writeFooter() throws WriteException;

    void end() throws UnsupportedEncodingException, DocumentException;
}
