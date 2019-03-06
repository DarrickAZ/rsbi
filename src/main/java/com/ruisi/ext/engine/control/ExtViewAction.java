//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.control;

import com.opensymphony.xwork2.ActionSupport;
import com.ruisi.ext.engine.control.sys.LoginSecurityAdapter;
import com.ruisi.ext.engine.control.sys.LoginSecurityAdapterImpl;
import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.wrapper.ExtRequestImpl;
import com.ruisi.ext.engine.wrapper.ExtResponseImpl;
import org.apache.struts2.ServletActionContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

public class ExtViewAction extends ActionSupport {
    private LoginSecurityAdapter a = new LoginSecurityAdapterImpl();

    public ExtViewAction() {
    }

    @Override
    public String execute() throws ServletException {
        ExtRequestImpl var1 = new ExtRequestImpl(ServletActionContext.getRequest());
        HttpServletResponse var2 = ServletActionContext.getResponse();
        ExtResponseImpl var3 = new ExtResponseImpl(var2);
        ServletContext var4 = ServletActionContext.getServletContext();
        String var5 = var1.getParameter("mvid");
        if (!this.a.loginChk(var1, var3, var4, (DaoHelper)null)) {
            return "nologin";
        } else {
            try {
                ActionProcess var6 = ActionProcess.getInstance();
                var6.processMV(var5, var1, var3, var4);
                return "success";
            } catch (Exception var7) {
                throw new ServletException(var7);
            }
        }
    }
}
