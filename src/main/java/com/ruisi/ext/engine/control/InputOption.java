//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.control;

import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtResponse;
import java.util.Map;
import org.apache.commons.fileupload.FileItem;

public interface InputOption {
    ExtRequest getRequest();

    ExtResponse getResponse();

    Map getParams();

    String getParamValue(String var1);

    String[] getParamValues(String var1);

    void setParamValue(String var1, String var2);

    void setParamValues(String var1, String[] var2);

    FileItem getUploadFile(String var1);
}
