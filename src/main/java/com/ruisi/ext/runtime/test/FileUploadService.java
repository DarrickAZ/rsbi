//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.runtime.test;

import com.ruisi.ext.engine.control.InputOption;
import com.ruisi.ext.engine.service.ServiceSupport;
import org.apache.commons.fileupload.FileItem;

import java.io.IOException;

public class FileUploadService extends ServiceSupport {
    public FileUploadService() {
    }

    @Override
    public void execute(InputOption var1) {
    }

    public void upload(InputOption var1) throws IOException {
        FileItem var2 = var1.getUploadFile("myfile");
        String var3 = var1.getParamValue("name");
        System.out.println(var3 + " = " + var2.getInputStream());
    }
}
