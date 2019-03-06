//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.wrapper;

import java.text.DecimalFormat;

public class StringBufferWriterImpl implements ExtWriter {
    StringBuffer a = new StringBuffer();

    public StringBufferWriterImpl() {
    }

    public String toString() {
        return this.a.toString();
    }

    public void close() {
    }

    public void flush() {
    }

    public Object getProxy() {
        return null;
    }

    public void print(String var1) {
        this.a.append(var1);
    }

    public void println(String var1) {
        this.a.append(var1);
        this.a.append("\n");
    }

    public void print(double var1, String var3) {
        DecimalFormat var4 = new DecimalFormat(var3);
        this.print(var4.format(var1));
    }
}
