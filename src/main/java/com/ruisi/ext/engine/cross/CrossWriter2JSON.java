//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.cross;

import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.init.ExtEnvirContext;
import com.ruisi.ext.engine.view.context.cross.CrossField;
import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.wrapper.ExtWriter;
import java.util.List;

public class CrossWriter2JSON implements CrossWriter {
    private ExtWriter a;
    private CrossReportContext b;

    public void writerFieldFmts() {
    }

    public void writerPageInfo() {
    }

    public void writerSort() {
    }

    public void queryLastLevelCols() {
    }

    public CrossWriter2JSON(CrossBuilder var1, ExtWriter var2, ExtEnvirContext var3, InputOption var4) {
        this.a = var2;
        this.b = var1.getCrossReport();
    }

    public void writeCols() {
    }

    public void writeRows() {
    }

    public void writerFieldDirll() {
    }

    public void writerKpi(CrossField var1, List var2, CrossField var3, int var4, int var5) {
    }

    public void writerColLink() {
    }

    public void writerLink() {
    }
}
