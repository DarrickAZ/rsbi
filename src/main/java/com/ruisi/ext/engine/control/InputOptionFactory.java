//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.control;

import com.ruisi.ext.engine.view.context.face.MoreValue;
import com.ruisi.ext.engine.view.context.form.FileFieldContext;
import com.ruisi.ext.engine.view.context.form.InputField;
import com.ruisi.ext.engine.wrapper.ExtRequest;
import com.ruisi.ext.engine.wrapper.ExtResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.fileupload.FileItem;

public class InputOptionFactory {
    public InputOptionFactory() {
    }

    public static InputOption createInputOption(ExtRequest var0, ExtResponse var1, Map var2) {
        HashMap var3 = new HashMap();
        if (var2 != null) {
            Iterator var4 = var2.keySet().iterator();

            while(var4.hasNext()) {
                String var5 = (String)var4.next();
                InputField var6 = (InputField)var2.get(var5);
                if (var6 instanceof MoreValue) {
                    String[] var7 = var0.getParameterValues(var6.getId());
                    var3.put(var5, var7);
                } else if (var6 instanceof FileFieldContext) {
                    if (var0.getMethod().equalsIgnoreCase("post")) {
                        FileItem var9 = var0.getUploadFile(var5);
                        var3.put(var5, var9);
                    }
                } else {
                    String var10 = var0.getParameter(var6.getId());
                    if (var10 == null) {
                        var10 = "";
                    }

                    var3.put(var5, var10);
                }
            }
        }

        InputOptionImpl var8 = new InputOptionImpl(var0, var1, var3);
        return var8;
    }
}
