//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.control;

import com.ruisi.ext.engine.ConstantsEngine;
import com.ruisi.ext.engine.view.exception.ExtRuntimeException;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtResponse;
import java.util.Map;
import org.apache.commons.fileupload.FileItem;

public class InputOptionImpl implements InputOption {
    private ExtRequest a;
    private ExtResponse b;
    private Map c;

    public void setParamValue(String var1, String var2) {
        this.c.put(var1, var2);
    }

    public void setParamValues(String var1, String[] var2) {
        this.c.put(var1, var2);
    }

    InputOptionImpl(ExtRequest var1, ExtResponse var2, Map var3) {
        this.a = var1;
        this.b = var2;
        this.c = var3;
    }

    public Map getParams() {
        return this.c;
    }

    public String getParamValue(String var1) {
        Object var2;
        String var4;
        if (!this.c.containsKey(var1)) {
            var2 = null;
            if (this.a.getMethod().equalsIgnoreCase("post")) {
                var4 = (String)this.a.getAttribute("ext.view.fromId");
            } else {
                var4 = (String)this.a.getAttribute("ext.view.mvid");
            }

            String var3 = ConstantsEngine.replace("id为 $0 的参数未绑定到MV中, mvid = $1", var1, var4);
            throw new ExtRuntimeException(var3);
        } else {
            var2 = this.c.get(var1);
            if (var2 == null) {
                return null;
            } else if (var2 instanceof String) {
                return (String)var2;
            } else {
                if (var2 instanceof String[]) {
                    var4 = ((String[])var2)[0];
                }

                throw new ExtRuntimeException("参数类型不对.");
            }
        }
    }

    public String[] getParamValues(String var1) {
        if (!this.c.containsKey(var1)) {
            String var4 = (String)this.a.getAttribute("ext.view.mvid");
            String var3 = ConstantsEngine.replace("id为 $0 的参数未绑定到MV中, mvid = $1", var1, var4);
            throw new ExtRuntimeException(var3);
        } else {
            Object var2 = this.c.get(var1);
            if (var2 == null) {
                return null;
            } else if (var2 instanceof String[]) {
                return (String[])var2;
            } else if (var2 instanceof String) {
                return new String[]{(String)var2};
            } else {
                throw new ExtRuntimeException("参数类型不对.");
            }
        }
    }

    public ExtRequest getRequest() {
        return this.a;
    }

    public ExtResponse getResponse() {
        return this.b;
    }

    public FileItem getUploadFile(String var1) {
        if (!this.c.containsKey(var1)) {
            String var3 = ConstantsEngine.replace("id为 $0 的参数未绑定到MV中, mvid = $1", var1);
            throw new ExtRuntimeException(var3);
        } else {
            FileItem var2 = (FileItem)this.c.get(var1);
            return var2;
        }
    }
}
