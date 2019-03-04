//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.portal;

import com.ruisitech.bi.util.RSBIUtils;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class ImageUploadService {
    private String path = RSBIUtils.getUploadFilePath();

    public ImageUploadService() {
    }

    public String upload(MultipartHttpServletRequest multiRequest) throws IllegalStateException, IOException {
        String ret = null;
        Iterator iter = multiRequest.getFileNames();

        while(iter.hasNext()) {
            MultipartFile file = multiRequest.getFile(((String)iter.next()).toString());
            if (file != null) {
                String filename = file.getOriginalFilename();
                String type = file.getContentType();
                if (type.indexOf("image") != -1) {
                    String extName = filename.substring(filename.lastIndexOf("."));
                    String id = UUID.randomUUID().toString().replace("-", "");
                    filename = id + extName;
                    ret = filename;
                    file.transferTo(new File(this.path + filename));
                }
            }
        }

        return ret;
    }
}
