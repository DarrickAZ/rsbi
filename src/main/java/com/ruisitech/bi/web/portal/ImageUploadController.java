//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.portal;

import com.ruisitech.bi.service.portal.ImageUploadService;
import com.ruisitech.bi.util.BaseController;
import com.ruisitech.bi.util.RSBIUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
@RequestMapping({"/portal"})
public class ImageUploadController extends BaseController {
    private static Logger log = Logger.getLogger(ImageUploadController.class);
    @Autowired
    private ImageUploadService service;

    public ImageUploadController() {
    }

    @RequestMapping(
        value = {"/ImgUpload.action"},
        method = {RequestMethod.POST}
    )
    public String upLoad(String compId, ModelMap model, HttpServletRequest request) {
        try {
            model.addAttribute("compId", compId);
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
                String path = this.service.upload(multiRequest);
                model.addAttribute("path", path);
                return "portal/ImgUpload";
            } else {
                return null;
            }
        } catch (Exception var7) {
            log.error("上传图片出错。", var7);
            return "control/SpringmvcError";
        }
    }

    @RequestMapping({"/img/{pic}.action"})
    public void view(@PathVariable("pic") String pic, HttpServletResponse response) throws IOException {
        String path = RSBIUtils.getUploadFilePath();
        OutputStream os = response.getOutputStream();
        FileInputStream is = null;

        try {
            File f = new File(path + pic);
            if (!f.exists()) {
                return;
            }

            is = new FileInputStream(f);
            IOUtils.copy(is, os);
        } finally {
            if (is != null) {
                is.close();
            }

        }

    }
}
