//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisi.ext.engine.control;

import com.ruisi.ext.engine.control.sys.LoginSecurityAdapter;
import com.ruisi.ext.engine.control.sys.LoginSecurityAdapterImpl;
import com.ruisi.ext.engine.dao.DaoHelper;
import com.ruisi.ext.engine.scan.ScanUtils;
import com.ruisi.ext.engine.service.loginuser.LoginUserFactory;
import com.ruisi.ext.engine.service.loginuser.LoginUserInfoLoader;
import com.ruisi.ext.engine.service.loginuser.LoginUserInfoLoaderImpl;
import com.ruisi.ext.engine.util.DaoUtils;
import com.ruisi.ext.engine.view.context.ExtContext;
import com.ruisi.ext.engine.view.exception.AuthException;
import com.ruisi.ext.engine.view.exception.ForbidException;
import com.ruisi.ext.engine.view.exception.ServiceException;
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

public class ExtControlServlet extends HttpServlet {
    private static Log a = LogFactory.getLog(ExtControlServlet.class);
    private LoginSecurityAdapter b;
    private LoginUserInfoLoader c;
    private LoginUserFactory d;

    public ExtControlServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest var1, HttpServletResponse var2) throws ServletException, IOException {
        ExtRequestImpl var3 = new ExtRequestImpl(var1);
        ExtResponseImpl var4 = new ExtResponseImpl(var2);
        ServletContext var5 = this.getServletContext();
        DaoHelper var6 = DaoUtils.getDaoHelper(var5);
        if (!this.b.loginChk(var3, var4, var5, var6)) {
            var3.getRequestDispatcher("/pages/control/NoLogin.jsp").forward(var1, var2);
        } else {
            this.d.createLoginUser(var3, var4, var6, this.c);

            try {
                ActionProcess var7 = ActionProcess.getInstance();
                var7.process(var3, var4, var5, var6);
            } catch (ForbidException var11) {
                var3.getRequestDispatcher("/pages/control/Forbid.jsp").forward(var1, var2);
                return;
            } catch (Exception var13) {
                a.error("extControl请求出错.", var13);
                var3.setAttribute("ext.service.err", var13.getMessage());
                var3.getRequestDispatcher("/pages/control/ServiceError.jsp").forward(var1, var2);
                return;
            }

            String var14 = (String)var3.getAttribute("ext.control.noResult");
            if (!"y".equals(var14)) {
                var3.getRequestDispatcher("/pages/control/ExtView.jsp").forward(var1, var2);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest var1, HttpServletResponse var2) throws ServletException, IOException {
        this.doGet(var1, var2);
    }

    @Override
    public void init(ServletConfig var1) throws ServletException {
        super.init(var1);
        this.d = LoginUserFactory.getInstance();

        String var2;
        try {
            var2 = ExtContext.getInstance().getConstant("securityClass");
            String var3 = ExtContext.getInstance().getConstant("loginUserClass");
            if (var2 != null && var2.length() != 0) {
                this.b = (LoginSecurityAdapter)Class.forName(var2).newInstance();
            } else {
                this.b = new LoginSecurityAdapterImpl();
            }

            if (var3 != null && var3.length() != 0) {
                this.c = (LoginUserInfoLoader)Class.forName(var3).newInstance();
            } else {
                this.c = new LoginUserInfoLoaderImpl();
            }
        } catch (Exception var10) {
            a.error("初始化 extControlServlet 出错", var10);
        }

        try {
            var2 = var1.getInitParameter("servicePackages");
            if (var2 != null && var2.length() > 0) {
                ScanUtils var11 = new ScanUtils();
                String[] var4 = var2.split(",");
                String[] var8 = var4;
                int var7 = var4.length;

                for(int var6 = 0; var6 < var7; ++var6) {
                    String var5 = var8[var6];
                    var11.startScan(var5.trim());
                }
            }
        } catch (Exception var9) {
            a.error("扫描包出错.", var9);
        }

    }
}
