//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.cross;

import com.ruisi.ext.engine.view.context.cross.CrossReportContext;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtWriter;

public interface OlapWriterInterface {
    void writeColDims(ExtRequest var1, ExtWriter var2, CrossReportContext var3);

    void wirteRowDims(ExtRequest var1, ExtWriter var2, CrossReportContext var3);
}
