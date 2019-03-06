//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.control;

import com.ruisi.ext.engine.control.sys.LoginSecurityAdapter;
import com.ruisi.ext.engine.control.sys.LoginSecurityAdapterImpl;
import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.service.loginuser.LoginUserFactory;
import com.ruisi.ext.engine.service.loginuser.LoginUserInfoLoader;
import com.ruisi.ext.engine.service.loginuser.LoginUserInfoLoaderImpl;
import com.ruisi.ext.engine.util.DaoUtils;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.exception.AuthException;
import com.ruisi.ext.engine.wrapper.ExtRequestImpl;
import com.ruisi.ext.engine.wrapper.ExtResponseImpl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExtViewServlet extends HttpServlet {
    private LoginSecurityAdapter a;
    private LoginUserInfoLoader b;
    private LoginUserFactory c;
    private static Log d = LogFactory.getLog(ExtViewServlet.class);

    public ExtViewServlet() {
    }

    @Override
    public void init(ServletConfig var1) throws ServletException {
        super.init(var1);
        this.c = LoginUserFactory.getInstance();

        try {
            String var2 = ExtContext.getInstance().getConstant("securityClass");
            String var3 = ExtContext.getInstance().getConstant("loginUserClass");
            if (var2 != null && var2.length() != 0) {
                this.a = (LoginSecurityAdapter)Class.forName(var2).newInstance();
            } else {
                this.a = new LoginSecurityAdapterImpl();
            }

            if (var3 != null && var3.length() != 0) {
                this.b = (LoginUserInfoLoader)Class.forName(var3).newInstance();
            } else {
                this.b = new LoginUserInfoLoaderImpl();
            }
        } catch (Exception var4) {
            d.error("初始化 extViewServlet 出错", var4);
        }

    }

    @Override
    protected void doGet(HttpServletRequest var1, HttpServletResponse var2) throws ServletException, IOException {
        ExtRequestImpl var3 = new ExtRequestImpl(var1);
        ServletContext var4 = this.getServletContext();
        DaoHelper var5 = DaoUtils.getDaoHelper(var4);
        ExtResponseImpl var6 = new ExtResponseImpl(var2);
        String var7 = var3.getParameter("mvid");
        if (!this.a.loginChk(var3, var6, var4, var5)) {
            var1.getRequestDispatcher("/pages/control/NoLogin.jsp").forward(var1, var2);
        } else {
            this.c.createLoginUser(var3, var6, var5, this.b);

            try {
                ActionProcess var8 = ActionProcess.getInstance();
                var8.processMV(var7, var3, var6, var4);
            } catch (Exception var11) {
                d.error("extView请求出错.", var11);
                var3.setAttribute("ext.service.err", var11.getMessage());
                var3.getRequestDispatcher("/pages/control/ServiceError.jsp").forward(var1, var2);
                return;
            }

            String var12 = (String)var3.getAttribute("ext.control.noResult");
            if (!"y".equals(var12)) {
                var1.getRequestDispatcher("/pages/control/ExtView.jsp").forward(var1, var2);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest var1, HttpServletResponse var2) throws ServletException, IOException {
        this.doGet(var1, var2);
    }
}
