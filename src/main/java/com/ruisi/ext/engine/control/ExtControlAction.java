//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.control;

import com.opensymphony.xwork2.ActionSupport;
import com.ruisi.ext.engine.control.sys.LoginSecurityAdapter;
import com.ruisi.ext.engine.control.sys.LoginSecurityAdapterImpl;
import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.util.DaoUtils;
import com.ruisi.ext.engine.view.exception.ServiceException;
import com.ruisi.ext.engine.wrapper.ExtRequestImpl;
import com.ruisi.ext.engine.wrapper.ExtResponseImpl;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.apache.struts2.ServletActionContext;

public class ExtControlAction extends ActionSupport {
    private LoginSecurityAdapter a = new LoginSecurityAdapterImpl();

    public ExtControlAction() {
    }

    @Override
    public String execute() throws ServletException {
        ExtRequestImpl var1 = new ExtRequestImpl(ServletActionContext.getRequest());
        ExtResponseImpl var2 = new ExtResponseImpl(ServletActionContext.getResponse());
        ServletContext var3 = ServletActionContext.getServletContext();
        DaoHelper var4 = DaoUtils.getDaoHelper(var3);
        if (!this.a.loginChk(var1, var2, var3, var4)) {
            return "nologin";
        } else {
            try {
                ActionProcess var5 = ActionProcess.getInstance();
                var5.process(var1, var2, var3, var4);
            } catch (Exception var7) {
                throw new ServletException(var7);
            }

            String var8 = (String)var1.getAttribute("ext.control.noResult");
            return "y".equals(var8) ? null : "success";
        }
    }
}
