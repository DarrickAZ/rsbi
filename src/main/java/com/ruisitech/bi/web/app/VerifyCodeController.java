//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.web.app;

import com.ruisitech.bi.service.app.VerifyCodeService;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/app"})
public class VerifyCodeController {
    @Autowired
    private VerifyCodeService service;

    public VerifyCodeController() {
    }

    @RequestMapping({"/VerifyCode.action"})
    public void index(String guid, HttpServletRequest req, HttpServletResponse res) throws IOException {
        this.service.createPic(guid, res);
    }
}
