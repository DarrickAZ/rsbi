//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ispire.dc.cube.operation;

import com.ruisi.ispire.dc.cube.DataSet;
import com.ruisi.ispire.dc.cube.engine.ScriptEnginerException;

public interface Processor {
    void process() throws ScriptEnginerException;

    void initDataSet(DataSet var1);
}
