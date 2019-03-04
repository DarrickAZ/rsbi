//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.etl;

import com.ruisitech.bi.service.etl.UpLoadFileService;
import com.ruisitech.bi.util.BaseController;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
@RequestMapping({"/etl"})
public class FileUploadController extends BaseController {
    @Autowired
    private UpLoadFileService service;

    public FileUploadController() {
    }

    @RequestMapping(
        value = {"/FileUpload.action"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Object upLoad(Integer cfgid, String filetype, HttpServletRequest request) {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            return this.service.upLoadFile(multiRequest, filetype, cfgid);
        } else {
            return super.buildSucces();
        }
    }
}
